package db.migration;


import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * m_sensorinfo
 * m_threshold_sensor
 * m_avgdata
 *
 * @author li.jianfei
 * @date 2014-10-30
 */
public class V20141210_0956__update_sensor_threshold_condition implements JdbcMigration {
    @Override
    public void migrate(Connection connection) throws Exception {
        modifyTable(connection);
    }

    private static void modifyTable(Connection connection) throws Exception {
        PreparedStatement stmt;

        // m_avgdata--complianceRate
        if (!isColumnExists(connection, "m_avgdata", "complianceRate")) {
            stmt = connection.prepareStatement("ALTER TABLE m_avgdata \n" +
                    "  ADD COLUMN complianceRate DOUBLE COMMENT '达标率：达标率=达标数据包/总数据包' AFTER waveValue ;");
            stmt.execute();
        }

        // m_threshold_sensor--conditionType
        if (!isColumnExists(connection, "m_threshold_sensor", "conditionType")) {
            stmt = connection.prepareStatement("ALTER TABLE m_threshold_sensor \n" +
                    "  ADD COLUMN conditionType INT (11) COMMENT '达标条件类型，1-范围；2-大于；3-小于；4-大于等于；5-小于等于。与目标值/浮动值有关' AFTER sensorPhysicalid ;");
            stmt.execute();
        }
        // m_threshold_sensor--target
        if (!isColumnExists(connection, "m_threshold_sensor", "target")) {
            stmt = connection.prepareStatement("ALTER TABLE m_threshold_sensor \n" +
                    "  ADD COLUMN target FLOAT COMMENT '文保行业监测调控预期目标值' AFTER conditionType ;");
            stmt.execute();
        }
        // m_threshold_sensor--floating
        if (!isColumnExists(connection, "m_threshold_sensor", "floating")) {
            stmt = connection.prepareStatement("ALTER TABLE m_threshold_sensor \n" +
                    "  ADD COLUMN floating FLOAT COMMENT '浮动值：以目标值为中心的浮动范围' AFTER target ;");
            stmt.execute();
        }


        // m_sensorinfo--conditionType
        if (!isColumnExists(connection, "m_sensorinfo", "conditionType")) {
            stmt = connection.prepareStatement("ALTER TABLE m_sensorinfo \n" +
                    "  ADD COLUMN conditionType INT (11) COMMENT '达标条件类型，1-范围；2-大于；3-小于；4-大于等于；5-小于等于。与目标值/浮动值有关' ;");
            stmt.execute();
        }
        // m_sensorinfo--target
        if (!isColumnExists(connection, "m_sensorinfo", "target")) {
            stmt = connection.prepareStatement("ALTER TABLE m_sensorinfo \n" +
                    "  ADD COLUMN target FLOAT COMMENT '文保行业监测调控预期目标值' ;");
            stmt.execute();
        }
        // m_sensorinfo--floating
        if (!isColumnExists(connection, "m_sensorinfo", "floating")) {
            stmt = connection.prepareStatement("ALTER TABLE m_sensorinfo \n" +
                    "  ADD COLUMN floating FLOAT COMMENT '浮动值：以目标值为中心的浮动范围' ;");
            stmt.execute();
        }
    }

    private static boolean isColumnExists(Connection connection, String table, String column) throws Exception {
        PreparedStatement stmt;
        ResultSet rs;
        stmt = connection.prepareStatement("  SELECT \n" +
                "    column_name \n" +
                "  FROM\n" +
                "    information_schema.`COLUMNS` c \n" +
                "  WHERE c.`TABLE_SCHEMA` = DATABASE() \n" +
                "    AND c.`TABLE_NAME` = '" + table + "' \n" +
                "    AND c.`COLUMN_NAME` = '" + column + "' ");

        rs = stmt.executeQuery();
        return rs.next();
    }

    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.0.3:3306/galaxy_20131010", "microwise", "microwise");
        modifyTable(connection);
    }
}
