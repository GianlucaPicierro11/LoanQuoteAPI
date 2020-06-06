package it.loanquote.services.quote;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LoanQuoteApplicationIT extends LoanQuoteApplicationTestCase {
    private static File findJarFile() {
        final File[] files = new File("target").listFiles(
                file -> file.getName().startsWith("loan-quote-")
                        && file.getName().endsWith(".jar")
                        && !file.getName().endsWith("-sources.jar")
                        && !file.getName().endsWith("-javadoc.jar")
        );

        assertNotNull(files, "Jar file found");
        assertEquals(1, files.length, "There's only 1 jar file");

        final File result = files[0];

        assertTrue(result.isFile(), result.getAbsolutePath() + " is a valid file");

        return result;
    }

    @Override
    protected List<String> runApplication(final String... args) {
        try {
            final List<String> command = new ArrayList<>(Arrays.asList(
                    "java",
                    "-jar",
                    "target/" + findJarFile().getName()
            ));

            command.addAll(Arrays.asList(args));

            final Process process = Runtime.getRuntime().exec(command.toArray(new String[]{}));

            final BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            final BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            final List<String> output = new ArrayList<>();

            String line;

            while ((line = stdInput.readLine()) != null) {
                output.add(line);
            }

            final List<String> error = new ArrayList<>();

            while ((line = stdError.readLine()) != null) {
                error.add(line);
            }

            process.waitFor();

            assertEquals(Collections.emptyList(), error, "Error stream is empty");

            assertEquals(0, process.exitValue(), "Process exited with 0");

            return output;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
