package com.training.codingstandards;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Writes processed payroll rows to an Excel workbook.
 */
public class ExcelReportWriter {
    private static final Logger LOGGER = Logger.getLogger(ExcelReportWriter.class.getName());

    private static final String[] HEADERS = {
            "Employee Id", "Name", "Email", "Department", "Base Salary",
            "Bonus", "Tax", "Net Pay", "Grade", "Hashed Id", "Session Token"
    };

    public void write(List<EmployeeProcessor.PayrollRow> rows, String outputPath) {
        if (rows == null) {
            throw new IllegalArgumentException("Payroll rows must not be null");
        }
        if (outputPath == null || outputPath.isBlank()) {
            throw new IllegalArgumentException("Output path must not be blank");
        }

        try (XSSFWorkbook workbook = new XSSFWorkbook();
             FileOutputStream output = new FileOutputStream(outputPath)) {
            Sheet sheet = workbook.createSheet(ReportConfig.OUTPUT_SHEET);
            writeHeader(sheet);
            writeRows(sheet, rows);
            workbook.write(output);
            LOGGER.info(() -> "Excel report written to " + outputPath);
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to write Excel report", ex);
        }
    }

    private void writeHeader(Sheet sheet) {
        Row header = sheet.createRow(0);
        for (int i = 0; i < HEADERS.length; i++) {
            header.createCell(i).setCellValue(HEADERS[i]);
        }
    }

    private void writeRows(Sheet sheet, List<EmployeeProcessor.PayrollRow> rows) {
        for (int i = 0; i < rows.size(); i++) {
            EmployeeProcessor.PayrollRow payroll = rows.get(i);
            Row row = sheet.createRow(i + 1);
            setString(row, 0, payroll.getEmpId());
            setString(row, 1, payroll.getName());
            setString(row, 2, payroll.getEmail());
            setString(row, 3, payroll.getDepartment());
            row.createCell(4).setCellValue(payroll.getBaseSalary());
            row.createCell(5).setCellValue(payroll.getBonus());
            row.createCell(6).setCellValue(payroll.getTax());
            row.createCell(7).setCellValue(payroll.getNetPay());
            setString(row, 8, payroll.getGrade());
            setString(row, 9, payroll.getHashedId());
            setString(row, 10, payroll.getToken());
        }
    }

    private void setString(Row row, int index, String value) {
        row.createCell(index).setCellValue(value == null ? "" : value);
    }
}
