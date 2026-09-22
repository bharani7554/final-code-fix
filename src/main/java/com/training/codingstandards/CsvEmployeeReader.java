package com.training.codingstandards;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Reads employee records from CSV files or the bundled sample CSV.
 */
public class CsvEmployeeReader {
    private static final Logger LOGGER = Logger.getLogger(CsvEmployeeReader.class.getName());

    public List<Employee> read(String csvPath) {
        try (Reader reader = openReader(csvPath);
             CSVParser parser = CSVFormat.DEFAULT.builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .build()
                     .parse(reader)) {
            List<Employee> employees = new ArrayList<>();
            for (CSVRecord csvRecord : parser) {
                employees.add(toEmployee(csvRecord));
            }
            LOGGER.info(() -> "Loaded " + employees.size() + " employees");
            return employees;
        } catch (IOException | IllegalArgumentException ex) {
            throw new IllegalArgumentException("Unable to read employee CSV", ex);
        }
    }

    private Reader openReader(String csvPath) throws IOException {
        if (csvPath == null || csvPath.isBlank()) {
            InputStream stream = CsvEmployeeReader.class.getResourceAsStream("/employees.csv");
            if (stream == null) {
                throw new IOException("Bundled employees.csv was not found");
            }
            return new InputStreamReader(stream, StandardCharsets.UTF_8);
        }
        return Files.newBufferedReader(Path.of(csvPath), StandardCharsets.UTF_8);
    }

    private Employee toEmployee(CSVRecord csvRecord) {
        Employee employee = new Employee();
        employee.setEmpId(csvRecord.get("empId"));
        employee.setName(csvRecord.get("name"));
        employee.setEmail(csvRecord.get("email"));
        employee.setDepartment(csvRecord.get("department"));
        employee.setSalary(Double.parseDouble(csvRecord.get("salary")));
        employee.setYearsOfService(Integer.parseInt(csvRecord.get("yearsOfService")));
        employee.setCountry(csvRecord.get("country"));
        employee.setManagerEmail(csvRecord.get("managerEmail"));
        return employee;
    }
}
