package com.ruoyi.system;

import com.ruoyi.common.core.utils.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.WeekFields;


@Component
public class ruoyiUtil {

    private final DataSource dataSource;

    @Autowired
    private ruoyiUtil(DataSource dataSource) {
        this.dataSource = dataSource;
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
