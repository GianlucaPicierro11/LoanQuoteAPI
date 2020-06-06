package it.loanquote.services.quote;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

public class LoanQuoteApplicationTest extends LoanQuoteApplicationTestCase {
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private final PrintStream systemOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    void tearDown() {
        System.setOut(systemOut);
    }

    @Override
    protected List<String> runApplication(final String... args) {
        LoanQuoteApplication.main(args);

        return Arrays.asList(output.toString().split(System.getProperty("line.separator")));
    }
}
