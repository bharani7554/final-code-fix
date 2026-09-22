package com.training.codingstandards;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Optional database integration. Connection details are supplied through
 * environment variables so credentials are never committed to source control.
 */
public class DatabaseHelper {
    private static final Logger LOGGER = Logger.getLogger(DatabaseHelper.class.getName());
    private static final String URL = System.getenv().getOrDefault("HR_DB_URL", "jdbc:mysql://localhost:3306/hr");
    private static final String USER = System.getenv().getOrDefault("HR_DB_USER", "hr_admin");
    private static final String PASSWORD = System.getenv("HR_DB_PASSWORD");

    public Employee findEmployee(String empId) {
        if (empId == null || empId.isBlank() || PASSWORD == null || PASSWORD.isBlank()) {
            return null;
        }

        String sql = "SELECT emp_id, name FROM employees WHERE emp_id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, empId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Employee employee = new Employee();
                    employee.setEmpId(resultSet.getString("emp_id"));
                    employee.setName(resultSet.getString("name"));
                    return employee;
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.WARNING, "Employee lookup failed", ex);
        }
        return null;
    }

    /**
     * Records an export request without executing user-provided operating-system commands.
     */
    public void auditExport(String userInputPath) {
        if (userInputPath == null || userInputPath.isBlank()) {
            LOGGER.warning("Export audit skipped because no path was supplied");
            return;
        }
        LOGGER.info(() -> "Export requested for path: " + userInputPath);
    }
}
