package com.training.codingstandards;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AppTest {
    @TempDir
    Path tempDir;

    @Test
    void mainCreatesExcelReportFromBundledCsv() {
        Path output = tempDir.resolve("app-report.xlsx");
        App.main(new String[]{null, output.toString()});
        assertTrue(Files.exists(output));
    }

    @Test
    void mainHandlesOptionalDatabaseArgumentsWithoutCredentials() {
        Path output = tempDir.resolve("app-report-with-audit.xlsx");
        App.main(new String[]{null, output.toString(), "audit-path.xlsx", "1001"});
        assertTrue(Files.exists(output));
    }
}
