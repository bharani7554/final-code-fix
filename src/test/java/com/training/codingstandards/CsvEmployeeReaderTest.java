package com.training.codingstandards;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvEmployeeReaderTest {

    @TempDir
    Path tempDir;

    @Test
    void readsBundledEmployeeCsv() {
        List<Employee> employees = new CsvEmployeeReader().read(null);

        assertEquals(8, employees.size());
        assertEquals("1001", employees.get(0).getEmpId());
        assertEquals("Asha Raman", employees.get(0).getName());
    }

    @Test
    void readsCsvFromFile() throws Exception {
        Path csv = tempDir.resolve("employees.csv");
        Files.writeString(csv, """
                empId,name,email,department,salary,yearsOfService,country,managerEmail
                2001,John Doe,john@example.com,Engineering,50000,2,IN,lead@example.com
                """);

        List<Employee> employees = new CsvEmployeeReader().read(csv.toString());

        assertEquals(1, employees.size());
        assertEquals("2001", employees.get(0).getEmpId());
        assertEquals(50000, employees.get(0).getSalary());
    }

    @Test
    void blankPathUsesBundledCsv() {
        assertEquals(8, new CsvEmployeeReader().read(" ").size());
    }

    @Test
    void invalidPathProducesUsefulException() {
        String missingPath = tempDir.resolve("missing.csv").toString();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> readCsv(missingPath));
        assertTrue(ex.getMessage().contains("Unable to read employee CSV"));
    }

    private List<Employee> readCsv(String path) {
        return new CsvEmployeeReader().read(path);
    }
}
