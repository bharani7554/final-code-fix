package com.training.codingstandards;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExcelReportWriterTest {

    @TempDir
    Path tempDir;

    @Test
    void writesWorkbookWithHeaderAndRows() throws Exception {
        Employee employee = new Employee("1001", "Asha", "asha@example.com",
                "Engineering", 92000, 6, "IN", "lead@example.com");
        EmployeeProcessor.PayrollRow row = new EmployeeProcessor().process(List.of(employee)).get(0);
        Path output = tempDir.resolve("payroll.xlsx");

        new ExcelReportWriter().write(List.of(row), output.toString());

        assertTrue(Files.exists(output));
        try (InputStream input = Files.newInputStream(output);
             XSSFWorkbook workbook = new XSSFWorkbook(input)) {
            var sheet = workbook.getSheet("Payroll");
            assertNotNull(sheet);
            assertEquals("Employee Id", sheet.getRow(0).getCell(0).getStringCellValue());
            assertEquals("1001", sheet.getRow(1).getCell(0).getStringCellValue());
            assertEquals("Asha", sheet.getRow(1).getCell(1).getStringCellValue());
        }
    }

    @Test
    void rejectsInvalidArguments() {
        ExcelReportWriter writer = new ExcelReportWriter();
        assertThrows(IllegalArgumentException.class, () -> writer.write(null, "report.xlsx"));
        List<EmployeeProcessor.PayrollRow> emptyRows = List.of();
        assertThrows(IllegalArgumentException.class, () -> writer.write(emptyRows, ""));
    }
}
