package com.training.codingstandards;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void equalsAndHashCodeUseEmployeeId() {
        Employee first = new Employee("1001", "A", "a@example.com", "IT", 1, 1, "IN", "m@example.com");
        Employee second = new Employee("1001", "B", "b@example.com", "HR", 2, 2, "US", "m2@example.com");
        Employee third = new Employee("1002", "B", "b@example.com", "HR", 2, 2, "US", "m2@example.com");

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
        assertNotEquals(first, third);
        assertNotEquals("1001", first);
        assertEquals(first, first);
    }

    @Test
    void gettersAndSettersWorkAndDateIsDefensivelyCopied() {
        Employee employee = new Employee();
        Date date = new Date(123456L);
        employee.setEmpId("1");
        employee.setName("Name");
        employee.setEmail("email");
        employee.setDepartment("IT");
        employee.setSalary(10);
        employee.setYearsOfService(2);
        employee.setCountry("IN");
        employee.setManagerEmail("manager");
        employee.setLastProcessed(date);

        assertEquals("1", employee.getEmpId());
        assertEquals("Name", employee.getName());
        assertEquals("email", employee.getEmail());
        assertEquals("IT", employee.getDepartment());
        assertEquals(10, employee.getSalary());
        assertEquals(2, employee.getYearsOfService());
        assertEquals("IN", employee.getCountry());
        assertEquals("manager", employee.getManagerEmail());
        assertEquals(date, employee.getLastProcessed());
        assertNotSame(date, employee.getLastProcessed());
    }
}
