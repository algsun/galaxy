package db.migration;


import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * m_device_表添加酸雨状态字段
 *
 * @author 李建飞
 * @date 2015-06-05
 */
public class V20150605_0927__add_column_demarcate implements JdbcMigration {

    public static final String QUERY_SQL = "SHOW TABLES LIKE 'm_device_%'";

    @Override
    public void migrate(Connection connection) throws Exception {
        done(connection);
    }


    private static void done(Connection connection) {
        List<String> devices = getDevices(connection, QUERY_SQL);
        for (String device : devices) {
            if (isExists(connection, device, "content")) {
                update(connection, getUpdateSql(device));
            }
        }
    }


    private static String getUpdateSql(String tableName) {
        return "ALTER TABLE `" + tableName + "`\n" +
                " ADD demarcate INT DEFAULT 0 NOT NULL COMMENT '0-费标定模式；1-标定模式'";
    }

    private static boolean isExists(Connection connection, String tableName, String columnName) {
        String sql = "SELECT \n" +
                "  COUNT(*) \n" +
                "FROM\n" +
                "  information_schema.columns \n" +
                "  WHERE `TABLE_SCHEMA` = DATABASE()" +
                "  AND table_name = '" + tableName + "' \n" +
                "  AND column_name = '" + columnName + "'";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int count = 0;
        try {
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
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
        return count > 0;
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
        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.0.184:3306/galaxy?autoReconnect=true", "root", "admin");
        done(connection);
    }
}
