package db.migration;


import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 位置点历史数据表中修改state注释
 *
 * @author liuzhu
 * @date 2016-6-20
 */
public class V20160620_1012__change_state_note implements JdbcMigration {

    public static final String QUERY_SQL = "SELECT id FROM `m_location` ORDER BY id";

    @Override
    public void migrate(Connection connection) throws Exception {
        done(connection);
    }


    private static void done(Connection connection) {
        List<String> locations = getLocations(connection, QUERY_SQL);
        for (String device : locations) {
             update(connection, getUpdateSql(device));
        }
    }


    private static String getUpdateSql(String tableName) {
        return "ALTER TABLE `" + tableName + "`" +
                " MODIFY COLUMN state INT(11) NOT NULL COMMENT '传感状态0：采样失败,1：采样正常,2：低于低阀值,3：超过高阀值,4：空数据,5：超时时间记录（系统判断超时添加的数据非设备上的数据）';";
    }

    private static List<String> getLocations(Connection connection, String sql) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<String> locations = new ArrayList<String>();
        try {
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                locations.add(rs.getString(1));
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
        return locations;
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
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/galaxy_20160616?autoReconnect=true", "root", "admin");
        done(connection);
    }
}
