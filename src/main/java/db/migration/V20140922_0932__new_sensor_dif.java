package db.migration;


import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.*;

/**
 * 新增三个监测指标，X,Y,Z方向的差值
 * 如果已存在就不执行了
 *
 * @author xiedeng
 * @date 2014-09-05
 */
public class V20140922_0932__new_sensor_dif implements JdbcMigration {

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

    @Override
    public void migrate(Connection connection) throws Exception {
        addNewSensor(connection);
        addNewFormulaSensor(connection);
        addNewFormulaParam(connection);
    }

    private static void addNewSensor(Connection connection) {
        if (!isDateExist(connection, QUERY_SENSOR_INFO + 92)) {
            insertData(connection, INSERT_SENSOR_INFO + "  (92, 'SGRX-DIF', 'X方向裂隙差值', 5, 'mm', '0', 1, 0, 0, 1, 3, 1)");
        }
        if (!isDateExist(connection, QUERY_SENSOR_INFO + 93)) {
            insertData(connection, INSERT_SENSOR_INFO + "  (93, 'SGRY-DIF', 'Y方向裂隙差值', 5, 'mm', '0', 1, 0, 0, 1, 3, 1)");
        }
        if (!isDateExist(connection, QUERY_SENSOR_INFO + 94)) {
            insertData(connection, INSERT_SENSOR_INFO + "  (94, 'SGRZ-DIF', 'Z方向裂隙差值', 5, 'mm', '0', 1, 0, 0, 1, 3, 1)");
        }
    }

    private static void addNewFormulaSensor(Connection connection) {
        if (!isDateExist(connection, QUERY_FORMULA + 92)) {
            insertData(connection, convertFormulaSql(92));
        }
        if (!isDateExist(connection, QUERY_FORMULA + 93)) {
            insertData(connection, convertFormulaSql(93));
        }
        if (!isDateExist(connection, QUERY_FORMULA + 94)) {
            insertData(connection, convertFormulaSql(94));
        }
    }

    private static void addNewFormulaParam(Connection connection) {
        if (isNotExistFormulaParam(connection, 92)) {
            insertData(connection, convertFormulaParamSql(92));
        }
        if (isNotExistFormulaParam(connection, 93)) {
            insertData(connection, convertFormulaParamSql(93));
        }
        if (isNotExistFormulaParam(connection, 94)) {
            insertData(connection, convertFormulaParamSql(94));
        }
    }

    private static boolean isNotExistFormulaParam(Connection connection, int sensorId) {
        return isNotExistFormulaParam(connection, sensorId, "a")
                && isNotExistFormulaParam(connection, sensorId, "b");
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
                "  ('0', " + sensorId + ", 'a', '1'),\n" +
                "  ('0', " + sensorId + ", 'b', '0');";
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
                "VALUES ( " + sensorId + ", 1, 0, 0, 0, 0, 1, 3, 1)";
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

    public static void main(String[] args) {

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.0.58:3306/galaxy", "microwise", "microwise");
            addNewSensor(connection);
            addNewFormulaParam(connection);
            addNewFormulaSensor(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
