package db.migration;


import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.*;

/**
 * 设备迁移到位置点
 *
 * @author xuyuexi
 * @date 2014-12-01
 */
public class V20141201_1439__add_privilege_of_phoenix_peak_value implements JdbcMigration {

    public static final String QUERY_PEAKVALUE = "SELECT COUNT(1) FROM  `t_system_privilege` WHERE  privilegeId = 'phoenix:blueplanet:peakValue';";

    public static final String INSERT_PEAKVALUE = "INSERT INTO `t_system_privilege`\n" +
            "(`subsystemId`,\n" +
            "`sequence`,\n" +
            "`privilegeId`,\n" +
            "`parentPrivilegeId`,\n" +
            "`privilegeName_CN`,\n" +
            "`url`,\n" +
            "`state`,\n" +
            "`isNavigation`)\n" +
            "VALUE\n" +
            "(6,\n" +
            "4,\n" +
            "'phoenix:blueplanet:peakValue',\n" +
            "'phoenix:blueplanet',\n" +
            "'区域监测指标峰值',\n" +
            "'blueplanet/peakValue',\n" +
            "122,\n" +
            "1);";

    public static final String INSERT_PEAKVALUE_PRIVILEGE = "INSERT INTO `t_role_privilege` (roleId, privilegeId)\n" +
            "\" +\n" +
            "            \"(SELECT\\n\" +\n" +
            "            \"  id AS roldId,\\n\" +\n" +
            "            \"  'phoenix:blueplanet:peakValue' AS privilegeId\\n\" +\n" +
            "            \"FROM\\n\" +\n" +
            "            \"  `t_roles`\\n\" +\n" +
            "            \"WHERE isManager = 1) ;";


    @Override
    public void migrate(Connection connection) throws Exception {
        addPeakValue(connection);
    }

    private static void addPeakValue(Connection connection) {
        if (!isDataExist(connection, QUERY_PEAKVALUE)) {
            insertData(connection, INSERT_PEAKVALUE);
            insertData(connection, INSERT_PEAKVALUE_PRIVILEGE);
        }
    }


    private static boolean isDataExist(Connection connection, String sql) {
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
        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.0.166:3306/galaxy_bmy2", "microwise", "microwise");
        addPeakValue(connection);
    }
}
