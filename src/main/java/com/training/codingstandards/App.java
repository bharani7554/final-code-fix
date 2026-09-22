package com.training.codingstandards;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

/**
 * Command-line entry point.
 */
public final class App {
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    private App() {
        // Utility class.
    }

    public static void main(String[] args) {
        String csvPath = args.length > 0 ? args[0] : null;
        String excelPath = args.length > 1 ? args[1] : "payroll-report.xlsx";

        CsvEmployeeReader reader = new CsvEmployeeReader();
        List<Employee> employees = reader.read(csvPath);

        EmployeeProcessor processor = new EmployeeProcessor();
        List<EmployeeProcessor.PayrollRow> rows = processor.process(employees);

        File output = new File(excelPath);
        new ExcelReportWriter().write(rows, output.getAbsolutePath());

        if (args.length > 2) {
            DatabaseHelper database = new DatabaseHelper();
            database.auditExport(args[2]);
            if (!employees.isEmpty()) {
                String empId = args.length > 3 ? args[3] : employees.get(0).getEmpId();
                Employee employee = database.findEmployee(empId);
                LOGGER.info(() -> employee == null
                        ? "No database employee found for " + empId
                        : "Database lookup found " + employee.getName());
            }
        }

        LOGGER.info(() -> "Processed " + rows.size() + " employees into " + excelPath);
    }
}
