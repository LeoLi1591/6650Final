package com.ruoyi.system;

import com.ruoyi.common.core.utils.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;


@Component
public class ruoyiUtil {

    private final DataSource dataSource;

    @Autowired
    private ruoyiUtil(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * check if table exist
     * @return true false
     */
    public boolean checkWeeklyTableExists(String tableName) {
        String sql = "SHOW TABLES LIKE ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tableName);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public LocalDate parseDate(String dateStr) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    /**
     * Calculate the table name by current time
     */
    public String getWeeklyTableName(String baseTableName, LocalDate date) {
        if (baseTableName == null || baseTableName.trim().isEmpty()) {
            throw new IllegalArgumentException("baseTableName cannot be null or empty");
        }
        if (date == null) {
            date = LocalDate.now();
        }

        int year = date.getYear();
        int week = date.get(WeekFields.ISO.weekOfWeekBasedYear());

        return baseTableName + "_" + year + "_" + week;
    }

    /**
     * Get current week table name and cloud
     */
    public String getCurrentWeekTableName(String baseTableName) {
        return getWeeklyTableName(baseTableName, LocalDate.now());
    }

    /**
     * Ensure the current table exist given the parameters
     *
     * @param baseTableName
     * @param date
     * @return
     */
    public String ensureWeeklyTable(String baseTableName, LocalDate date) {
        String shardTableName = getWeeklyTableName(baseTableName, date);

        String sql = "CREATE TABLE IF NOT EXISTS `" + shardTableName + "` LIKE `" + baseTableName + "`";

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Create failed: " + shardTableName, e);
        }

        return shardTableName;
    }

    /**
     *  Ensure the current week table exist
     */
    public String ensureCurrentWeekTable(String baseTableName) {
        return ensureWeeklyTable(baseTableName, LocalDate.now());
    }
}
