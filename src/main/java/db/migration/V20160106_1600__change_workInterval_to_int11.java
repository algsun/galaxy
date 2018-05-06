package db.migration;


import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * m_device_表修改工作周期字段类型
 *
 * @author liuzhu
 * @date 2016-1-6
 */
public class V20160106_1600__change_workInterval_to_int11 implements JdbcMigration {

    public static final String QUERY_SQL = "SHOW TABLES LIKE 'm_device_%'";

    @Override
    public void migrate(Connection connection) throws Exception {
        done(connection);
    }


    private static void done(Connection connection) {
        List<String> devices = getDevices(connection, QUERY_SQL);
        for (String device : devices) {
             update(connection, getUpdateSql(device));
        }
    }


    private static String getUpdateSql(String tableName) {
        return "ALTER TABLE `" + tableName + "`\n" +
                " MODIFY workInterval INT(11) NOT NULL DEFAULT 600 COMMENT '工作周期'";
    }

    private static List<String> getDevices(Connection connection, String sql) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<String> devices = new ArrayList<String>();
        try {
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                devices.add(rs.getString(1));
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
        return devices;
    }

    private static void update(Connection connection, String sql) {
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
        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.0.168:3306/galaxy_20150804?autoReconnect=true", "root", "admin");
        done(connection);
    }
}
