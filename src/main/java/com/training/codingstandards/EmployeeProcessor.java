package com.training.codingstandards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Calculates payroll values for employees.
 */
public class EmployeeProcessor {

    public List<PayrollRow> process(List<Employee> employees) {
        if (employees == null || employees.isEmpty()) {
            return Collections.emptyList();
        }

        List<PayrollRow> rows = new ArrayList<>(employees.size());
        for (Employee employee : employees) {
            if (employee == null) {
                continue;
            }
            rows.add(createPayrollRow(employee));
        }
        return rows;
    }

    private PayrollRow createPayrollRow(Employee employee) {
        PayrollRow row = new PayrollRow(
                employee.getEmpId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment(),
                employee.getSalary());

        row.setBonus(calculateBonus(employee));
        row.setTax(calculateTax(employee.getSalary(), employee.getCountry()));
        row.setNetPay(employee.getSalary() + row.getBonus() - row.getTax());
        row.setGrade(calculateGrade(employee.getSalary(), employee.getYearsOfService(),
                employee.getDepartment()));
        row.setHashedId(SecurityUtil.hashIdentifier(employee.getEmpId() + employee.getEmail()));
        row.setToken(SecurityUtil.sessionToken());
        return row;
    }

    private double calculateBonus(Employee employee) {
        String department = employee.getDepartment();
        double salary = employee.getSalary();
        int years = employee.getYearsOfService();

        if ("Engineering".equals(department)) {
            return engineeringBonus(salary, years, employee.getCountry());
        }
        if ("Finance".equals(department)) {
            return financeBonus(salary, years);
        }
        if ("Sales".equals(department)) {
            return salesBonus(salary, years);
        }
        return years > 3 ? salary * 0.05 : salary * 0.03;
    }

    private double engineeringBonus(double salary, int years, String country) {
        if (years > 10) {
            if (salary > 100000) {
                if ("JP".equals(country) || "SG".equals(country)) {
                    return salary * 0.18;
                }
                return salary * (salary > 110000 ? 0.15 : 0.12);
            }
            return salary * (years > 12 ? 0.14 : 0.10);
        }
        if (years > 5) {
            return salary * (salary > 90000 ? 0.10 : 0.08);
        }
        return salary * 0.05;
    }

    private double financeBonus(double salary, int years) {
        if (years > 5) {
            return salary * (salary > 80000 ? 0.09 : 0.07);
        }
        return salary * 0.04;
    }

    private double salesBonus(double salary, int years) {
        return salary * (years > 4 ? 0.11 : 0.06);
    }

    private double calculateTax(double salary, String country) {
        if ("IN".equals(country)) {
            return salary * indianTaxRate(salary);
        }
        if ("US".equals(country)) {
            return salary * usTaxRate(salary);
        }
        if ("SG".equals(country)) {
            return salary * 0.15;
        }
        if ("JP".equals(country)) {
            return salary * 0.20;
        }
        return salary * 0.10;
    }


    private double indianTaxRate(double salary) {
        if (salary > 100000) {
            return 0.30;
        }
        if (salary > 70000) {
            return 0.20;
        }
        return 0.10;
    }

    private double usTaxRate(double salary) {
        if (salary > 100000) {
            return 0.28;
        }
        if (salary > 70000) {
            return 0.18;
        }
        return 0.12;
    }

    private String calculateGrade(double salary, int years, String department) {
        if (salary > 100000) {
            return years > 8 && "Engineering".equals(department) ? "L5" : "L4";
        }
        if (salary > 80000) {
            return years > 5 ? "L3" : "L2";
        }
        return salary > 60000 ? "L2" : "L1";
    }

    public static final class PayrollRow {
        private final String empId;
        private final String name;
        private final String email;
        private final String department;
        private final double baseSalary;
        private double bonus;
        private double tax;
        private double netPay;
        private String grade;
        private String hashedId;
        private String token;

        public PayrollRow(String empId, String name, String email, String department, double baseSalary) {
            this.empId = empId;
            this.name = name;
            this.email = email;
            this.department = department;
            this.baseSalary = baseSalary;
        }

        public String getEmpId() { return empId; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getDepartment() { return department; }
        public double getBaseSalary() { return baseSalary; }
        public double getBonus() { return bonus; }
        public void setBonus(double bonus) { this.bonus = bonus; }
        public double getTax() { return tax; }
        public void setTax(double tax) { this.tax = tax; }
        public double getNetPay() { return netPay; }
        public void setNetPay(double netPay) { this.netPay = netPay; }
        public String getGrade() { return grade; }
        public void setGrade(String grade) { this.grade = grade; }
        public String getHashedId() { return hashedId; }
        public void setHashedId(String hashedId) { this.hashedId = hashedId; }
        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
    }
}
