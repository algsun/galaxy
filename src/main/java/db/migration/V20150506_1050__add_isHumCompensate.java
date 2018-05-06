package db.migration;


import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * m_nodeinfo表添加是否有温度补偿
 *
 * @author xiedeng
 * @date 2014-09-05
 */
public class V20150506_1050__add_isHumCompensate implements JdbcMigration {

    public static final String TABLE_NAME = "m_nodeinfo";

    public static final String COLUMN_NAME = "isHumCompensate";

    @Override
    public void migrate(Connection connection) throws Exception {
        done(connection);
    }


    private static void done(Connection connection) {
        if (!isExists(connection, TABLE_NAME, COLUMN_NAME)) {
            update(connection, getUpdateSql(TABLE_NAME));
        }
    }


    private static String getUpdateSql(String tableName) {
        return "ALTER TABLE `" + tableName + "`\n" +
                "  ADD `isHumCompensate` int(1) NOT NULL DEFAULT '1' COMMENT '是否有温度补偿：0 否，1 是';";
    }

    private static boolean isExists(Connection connection, String tableName, String columnName) {
        String sql = "SELECT \n" +
                "  COUNT(*) \n" +
                "FROM\n" +
                "  information_schema.columns \n" +
                "  WHERE `TABLE_SCHEMA` = DATABASE() \n" +
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

}
