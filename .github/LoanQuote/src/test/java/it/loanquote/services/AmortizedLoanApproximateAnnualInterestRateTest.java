//package it.loanquote.services;
//
//import com.opencsv.bean.CsvToBeanBuilder;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//public class AmortizedLoanApproximateAnnualInterestRateTest {
//	
//	@Autowired
//	private IAmortizedLoanService amortizedLoanService;
//	
//    @Test
//    void testAnnualInterestRate() {
//        assertEquals(0.06787, amortizedLoanService.getApproximateAnnualInterestRate(1000, 36, 30.78), 0.0001);
//        assertEquals(6, amortizedLoanService.getApproximateAnnualInterestRate(1000, 36, 500), 0.0001);
//        assertEquals(0.001, amortizedLoanService.getApproximateAnnualInterestRate(1000, 36, 27.78), 0.001);
//    }
//
//    @Test
//    void testAnnualInterestRateWithGeneratedValues() throws FileNotFoundException {
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
//                    payment.getRate().doubleValue(),
//                    amortizedLoanService.getApproximateAnnualInterestRate(payment.getPrincipal(), 36, payment.getPayment().doubleValue()),
//                    0.001,
//                    payment.toString()
//            );
//        }
//    }
//
//    @Test
//    void testNonPositivePrincipal() {
//        assertThrows(IllegalArgumentException.class, () -> amortizedLoanService.getApproximateAnnualInterestRate(-1000, 36, 27.78));
//        assertThrows(IllegalArgumentException.class, () -> amortizedLoanService.getApproximateAnnualInterestRate(-1, 36, 27.78));
//        assertThrows(IllegalArgumentException.class, () -> amortizedLoanService.getApproximateAnnualInterestRate(0, 36, 27.78));
//    }
//
//    @Test
//    void test1Principal() {
//        assertEquals(0.4321, amortizedLoanService.getApproximateAnnualInterestRate(1, 36, 0.05), 0.0001);
//    }
//
//    @Test
//    void testMonthlyPaymentTooSmall() {
//        assertThrows(IllegalArgumentException.class, () -> amortizedLoanService.getApproximateAnnualInterestRate(1000, 36, 27.77));
//        assertThrows(IllegalArgumentException.class, () -> amortizedLoanService.getApproximateAnnualInterestRate(1000, 36, -27.78));
//        assertThrows(IllegalArgumentException.class, () -> amortizedLoanService.getApproximateAnnualInterestRate(1000, 36, 0));
//    }
//
//    @Test
//    void testInvalidTerm() {
//        assertThrows(IllegalArgumentException.class, () -> amortizedLoanService.getApproximateAnnualInterestRate(1000, 0, 27.77));
//        assertThrows(IllegalArgumentException.class, () -> amortizedLoanService.getApproximateAnnualInterestRate(1000, -1, 27.77));
//    }
//
//    @Test
//    void test1Term() {
//        assertEquals(0.06, amortizedLoanService.getApproximateAnnualInterestRate(1000, 1, 1005), 0.0001);
//    }
//}
