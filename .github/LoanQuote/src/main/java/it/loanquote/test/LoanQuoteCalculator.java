package it.loanquote.test;

import com.github.hansonhsc.loan.AmortizedLoan;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.ROUND_UP;

/**
 * Given a list of lenders, produces quotes on demand using lenders with the lowest rates
 */
public class LoanQuoteCalculator {
    /**
     * Total number of repayment months over the entire loan
     */
    private static final int REPAYMENT_MONTHS = 36;

    /**
     * the lenders available for loans
     */
    private final List<Lender> lenders;

    /**
     * Constructs a calculator with the specified lenders
     *
     * @param lenders list of lenders. Note that this will be mutated: it will be sorted by rate in ascending order,
     *                then by amount in descending order
     */
    public LoanQuoteCalculator(final List<Lender> lenders) {
        // sort lender based on cheapest rate and the largest amount
        this.lenders = lenders;

        this.lenders.sort((lender1, lender2) -> {
            final int rateComparison = lender1.getRate().compareTo(lender2.getRate());

            return rateComparison != 0 ? rateComparison : lender2.getAmount() - lender1.getAmount();
        });
    }

    /**
     * Returns the lenders available for loans
     * @return the lenders available for loans
     */
    public Collection<Lender> getLenders() {
        return lenders;
    }

    /**
     * Returns a quote based on the specified loan amount, using the lowest rated lenders possible
     * @param loanAmount the loan amount requested in pounds sterling
     * @return the loan quote containing repayment information and the interest rate of the loan
     * @throws InsufficientLendersException thrown when there is not sufficient funding from the list of lenders
     * to satisfy the requested loan amount
     */
    public LoanQuote getQuote(final int loanAmount) throws InsufficientLendersException {
        final Map<Lender, Integer> loans = getLendersForLoan(loanAmount);

        // calculate monthly repayment for each individual lender
        final BigDecimal monthlyRepayment = loans.entrySet().stream()

                // calculate total monthly repayment by calculating monthly repayment towards each individual lender
                .map(individualLoan -> {
                    final Lender lender = individualLoan.getKey();
                    final Integer individualLoanAmount = individualLoan.getValue();

                    return getMonthlyRepayment(lender.getRate(), individualLoanAmount);
                })

                // add up each monthly repayment
                .reduce(BigDecimal::add)

                // there must be at least one lender, so this is impossible
                .orElseThrow(() -> new IllegalStateException("getLendersForLoan should never return empty map"));

        // calculate total repayment based on non-rounded monthly repayment
        final BigDecimal totalRepayment = monthlyRepayment.multiply(new BigDecimal(REPAYMENT_MONTHS));

        // estimate interest rate based on monthly repayment
        final double rate = getApproximateAnnualInterestRate(loanAmount, monthlyRepayment);

        return new LoanQuote(
                loanAmount,

                // round annual interest rate to nearest one decimal place
                new BigDecimal(rate).setScale(1, ROUND_HALF_UP),

                // round monthly payment to nearest penny
                // customer might pay fractional pennies more/less every month
                // but the last payment can be adjusted to reflect this
                monthlyRepayment.setScale(2, ROUND_HALF_UP),

                // round up to ensure we do not lose fractional pennies, better for the customers to lose out than us having a shortfall
                totalRepayment.setScale(2, ROUND_UP)
        );
    }

    /**
     * Calculates an approximate annual interest rate using only the principal, monthly repayment
     * @param loanAmount initial loan amount
     * @param monthlyRepayment amount of repayment per month
     * @return an approximation of the annual interest rate in percentage format
     */
    double getApproximateAnnualInterestRate(final int loanAmount, final BigDecimal monthlyRepayment) {
        return AmortizedLoan.getApproximateAnnualInterestRate(loanAmount, REPAYMENT_MONTHS, monthlyRepayment.doubleValue()) * 100;
    }

    /**
     * Calculates the monthly repayment required using amortized interest
     * @param rate annual interest rate of the loan
     * @param individualLoanAmount the initial loan amount
     * @return the repayment required to repay capital and interest every month
     */
    BigDecimal getMonthlyRepayment(final BigDecimal rate, final Integer individualLoanAmount) {
        return AmortizedLoan.getMonthlyRepayment(new BigDecimal(individualLoanAmount), rate, REPAYMENT_MONTHS);
    }

    /**
     * Retrieves a list of lender and loan amount pairs that represent how much the borrower is borrowing from each lender
     * @param loanAmount the total loan amount requested
     * @return list of lender and loan amount pairs that represent how much the borrower is borrowing from each lender
     * @throws InsufficientLendersException thrown when there is not sufficient funding from the list of lenders
     * to satisfy the requested loan amount
     */
    Map<Lender, Integer> getLendersForLoan(final int loanAmount) throws InsufficientLendersException {
        final Map<Lender, Integer> result = new HashMap<>();

        int remainingLoanAmount = loanAmount;

        for (final Lender lender : lenders) {
            // can this lender satisfy remaining loan required?
            if (lender.getAmount() >= remainingLoanAmount) {
                result.put(lender, remainingLoanAmount);

                return result;
            }

            // use up all of lender's quota
            result.put(lender, lender.getAmount());

            remainingLoanAmount -= lender.getAmount();
        }

        throw new InsufficientLendersException();
    }
}
