package com.training.codingstandards;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeProcessorTest {

    @Test
    void nullAndEmptyInputReturnEmptyList() {
        EmployeeProcessor processor = new EmployeeProcessor();
        assertTrue(processor.process(null).isEmpty());
        assertTrue(processor.process(Collections.emptyList()).isEmpty());
    }

    @Test
    void processCalculatesExpectedEngineeringPayroll() {
        Employee employee = new Employee("1001", "Asha Raman", "asha.raman@example.com",
                "Engineering", 92000, 6, "IN", "lead.eng@example.com");

        EmployeeProcessor.PayrollRow row = new EmployeeProcessor()
                .process(List.of(employee)).get(0);

        assertEquals("1001", row.getEmpId());
        assertEquals("Engineering", row.getDepartment());
        assertEquals(9200.0, row.getBonus());
        assertEquals(18400.0, row.getTax());
        assertEquals(82800.0, row.getNetPay());
        assertEquals("L3", row.getGrade());
        assertNotNull(row.getHashedId());
        assertNotNull(row.getToken());
    }

    @Test
    void processCoversDepartmentsAndCountries() {
        List<Employee> employees = Arrays.asList(
                employee("e1", "Engineering", 120000, 13, "JP"),
                employee("e2", "Engineering", 120000, 11, "IN"),
                employee("e3", "Engineering", 105000, 11, "IN"),
                employee("e4", "Engineering", 80000, 13, "US"),
                employee("e5", "Engineering", 85000, 7, "IN"),
                employee("e6", "Engineering", 70000, 5, "IN"),
                employee("e7", "Finance", 90000, 6, "US"),
                employee("e8", "Finance", 70000, 6, "US"),
                employee("e9", "Finance", 70000, 5, "US"),
                employee("e10", "Sales", 70000, 5, "SG"),
                employee("e11", "Sales", 70000, 4, "SG"),
                employee("e12", "HR", 70000, 4, "AE"),
                employee("e13", "HR", 50000, 2, "AE"),
                employee("e14", "Other", 105000, 9, "IN"),
                employee("e15", "Other", 80000, 5, "JP"),
                employee("e16", "Other", 60000, 1, "ZZ"),
                null
        );

        List<EmployeeProcessor.PayrollRow> rows = new EmployeeProcessor().process(employees);

        assertEquals(16, rows.size());
        assertEquals("L5", rows.get(0).getGrade());
        assertEquals("L4", rows.get(13).getGrade());
        assertEquals("L2", rows.get(14).getGrade());
        assertEquals("L1", rows.get(15).getGrade());
        assertTrue(rows.stream().allMatch(row -> row.getToken() != null));
    }

    private Employee employee(String id, String department, double salary, int years, String country) {
        return new Employee(id, "Test", id + "@example.com", department, salary, years, country, "manager@example.com");
    }
}
