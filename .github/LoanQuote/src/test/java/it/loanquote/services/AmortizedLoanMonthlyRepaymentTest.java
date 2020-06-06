//package it.loanquote.services;
//
//import com.opencsv.bean.CsvToBeanBuilder;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.math.BigDecimal;
//import java.util.List;
//
//import static java.math.BigDecimal.ROUND_HALF_UP;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//public class AmortizedLoanMonthlyRepaymentTest {
//    private String getMonthlyRepayment(final int principal, final double annualInterestRatePercent, final int numberOfPaymentPeriods) {
////        return AmortizedLoan.getMonthlyRepayment(
////                new BigDecimal(principal),
////                new BigDecimal(annualInterestRatePercent).divide(new BigDecimal(100), 10, BigDecimal.ROUND_HALF_UP),
////                numberOfPaymentPeriods
//////        ).setScale(2, ROUND_HALF_UP).toString();
//    }
//    @Autowired
//    private IAmortizedLoanService amortizedLoanService;
//    
//    @Test
//    void test0Principal() {
//        assertEquals("0.00", amortizedLoanService.getMonthlyRepayment(0, 1, 36));
//    }
//
//    @Test
//    void testNegativePrincipal() {
//        assertEquals("-0.03", getMonthlyRepayment(-1, 1, 36));
//        assertEquals("-32.27", getMonthlyRepayment(-1_000, 10, 36));
//    }
//
//    @Test
//    void test0InterestRate() {
//        assertEquals("27.78", getMonthlyRepayment(1000, 0, 36));
//    }
//
//    @Test
//    void testNegativeInterestRate() {
//        assertThrows(IllegalArgumentException.class, () -> getMonthlyRepayment(1_000, -10, 36));
//    }
//
//    @Test
//    void testInvalidPaymentPeriods() {
//        assertThrows(IllegalArgumentException.class, () -> getMonthlyRepayment(1_000, 10, 0));
//        assertThrows(IllegalArgumentException.class, () -> getMonthlyRepayment(1_000, 10, -1));
//    }
//
//    @Test
//    void testMonthlyRepayments() {
//        assertEquals("32.27", getMonthlyRepayment(1_000, 10, 36));
//        assertEquals("32267.19", getMonthlyRepayment(1_000_000, 10, 36));
//        assertEquals("0.32", getMonthlyRepayment(10, 10, 36));
//        assertEquals("88.28", getMonthlyRepayment(1_000, 100, 36));
//        assertEquals("833.33", getMonthlyRepayment(1_000, 1000, 36));
//        assertEquals("28.21", getMonthlyRepayment(1_000, 1, 36));
//        assertEquals("0.03", getMonthlyRepayment(1, 10, 36));
//    }
//
//    @Test
//    void testMonthlyRepaymentsWithGeneratedValues() throws FileNotFoundException {
//        // payments.csv is a generated CSV from using the PMT function in a spreadsheet
//        final FileReader paymentFileReader = new FileReader("src/test/resources/payments.csv");
//
//        //noinspection unchecked
//        final List<Payment> payments = new CsvToBeanBuilder(paymentFileReader)
//                .withType(Payment.class)
//                .build()
//                .parse();
//
//        for (final Payment payment : payments) {
//            assertEquals(
//                    payment.getPayment().toString(),
//                    getMonthlyRepayment(
//                            payment.getPrincipal(),
//                            payment.getRate().doubleValue() * 100,
//                            36
//                    ),
//                    payment.toString()
//            );
//        }
//    }
//}
