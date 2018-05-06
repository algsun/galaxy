package db.migration;


import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.*;

/**
 * 添加新传感
 *
 * @author xiedeng
 * @date 2014-09-05
 */
public class V20141204_1317__new_sensor_cond_hs implements JdbcMigration {

    public static final String QUERY_SENSOR_INFO = "SELECT COUNT(1) FROM m_sensorinfo m WHERE m.`sensorPhysicalid` = ";

    public static final String INSERT_SENSOR_INFO = "INSERT INTO `m_sensorinfo` (\n" +
            "  `sensorPhysicalid`,\n" +
            "  `en_name`,\n" +
            "  `cn_name`,\n" +
            "  `sensorPrecision`,\n" +
            "  `units`,\n" +
            "  `positions`,\n" +
            "  `isActive`,\n" +
            "  `showType`,\n" +
            "  `minValue`,\n" +
            "  `maxValue`,\n" +
            "  `rangeType`,\n" +
            "  `signType`\n" +
            ")\n" +
            "VALUES\n";

    public static final String QUERY_FORMULA = "SELECT COUNT(1) FROM `m_formula_sensor` m WHERE m.`sensor_id` = ";

    public V20141204_1317__new_sensor_cond_hs() {
    }

    @Override
    public void migrate(Connection connection) throws Exception {
        addNewSensor(connection);
        addNewFormulaSensor(connection);
        addNewFormulaParam(connection);
    }

    private static void addNewSensor(Connection connection) {
        if (!isDateExist(connection, QUERY_SENSOR_INFO + 95)) {
            insertData(connection, INSERT_SENSOR_INFO + "  (95, 'COND-HS', '电导率-高灵敏度', 0, 'us/cm', 0, 1, 0, 0, 0, 0, 0)");
        }
    }

    private static void addNewFormulaSensor(Connection connection) {
        if (!isDateExist(connection, QUERY_FORMULA + 95)) {
            insertData(connection, convertFormulaSql(95));
        }
    }

    private static void addNewFormulaParam(Connection connection) {
        if (isNotExistFormulaParam(connection, 95)) {
            insertData(connection, convertFormulaParamSql(95));
        }
    }

    private static boolean isNotExistFormulaParam(Connection connection, int sensorId) {
        return isNotExistFormulaParam(connection, sensorId, "a")
                && isNotExistFormulaParam(connection, sensorId, "b")
                && isNotExistFormulaParam(connection, sensorId, "c");
    }

    private static boolean isNotExistFormulaParam(Connection connection, int sensorId, String name) {
        return !isDateExist(connection, getQueryFormulaParamSql(sensorId, name));
    }

    private static String getQueryFormulaParamSql(int sensorId, String name) {
        return "SELECT COUNT(1) FROM `m_formula_param` m WHERE m.`sensor_id` = " + sensorId + " AND m.`name` = '" + name + "'";
    }

    private static String convertFormulaParamSql(int sensorId) {
        return "INSERT INTO `m_formula_param` (\n" +
                "  `device_id`,\n" +
                "  `sensor_id`,\n" +
                "  `name`,\n" +
                "  `value`\n" +
                ") \n" +
                "VALUES\n" +
                "  ('0', " + sensorId + ", 'a', '0'),\n" +
                "  ('0', " + sensorId + ", 'b', '1'),\n" +
                "  ('0', " + sensorId + ", 'c', '0');";
    }

    private static String convertFormulaSql(int sensorId) {
        return "INSERT INTO `m_formula_sensor`\n" +
                "            (`sensor_id`,\n" +
                "             `formula_id`,\n" +
                "             `min_x`,\n" +
                "             `max_x`,\n" +
                "             `x_range_type`,\n" +
                "             `min_y`,\n" +
                "             `max_y`,\n" +
                "             `y_range_type`,\n" +
                "             `sign_type`)\n" +
                "VALUES ( " + sensorId + ", 2, 0, 0, 0, 0, 0, 0, 0)";
    }

    private static boolean isDateExist(Connection connection, String sql) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0 ? true : false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    private static void insertData(Connection connection, String sql) {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/galaxy", "root", "admin");
        addNewSensor(connection);
        addNewFormulaSensor(connection);
        addNewFormulaParam(connection);
    }
}
