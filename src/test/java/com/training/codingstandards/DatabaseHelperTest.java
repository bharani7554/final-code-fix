package com.training.codingstandards;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;

class DatabaseHelperTest {

    @Test
    void lookupFailsClosedWithoutDatabasePassword() {
        assertNull(new DatabaseHelper().findEmployee(null));
        assertNull(new DatabaseHelper().findEmployee(""));
    }

    @Test
    void auditExportAcceptsMissingAndNormalPaths() {
        DatabaseHelper helper = new DatabaseHelper();
        assertDoesNotThrow(() -> {
            helper.auditExport(null);
            helper.auditExport("report.xlsx");
        });
    }
}
