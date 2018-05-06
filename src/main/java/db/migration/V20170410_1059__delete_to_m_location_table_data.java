package db.migration;


import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 删除位置点表数据状态为5的数据
 *
 * @author bai.weixing
 * @date 2017-04-10
 */

public class V20170410_1059__delete_to_m_location_table_data implements JdbcMigration {

    public static final String QUERY_SQL = "SELECT  l.`id`  FROM m_location l LEFT JOIN information_schema.TABLES t ON l.id = t.TABLE_NAME  WHERE  t.TABLE_NAME IS NOT NULL";

    @Override
    public void migrate(Connection connection) throws Exception {
        done(connection);
    }


    private static void done(Connection connection) throws SQLException {
        //获取位置点历史数据表名
        List<String> locationIds = getStringList(connection, QUERY_SQL);
        PreparedStatement stmt = null;
        //删除位置点历史数据表里状态为5的数据
        for (String locationId : locationIds) {
            String sql = "DELETE FROM  `" + locationId + "` WHERE state=5";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
        }
        if (stmt != null) {
            stmt.close();
        }
    }


    private static List<String> getStringList(Connection connection, String sql) throws SQLException {
        PreparedStatement stmt;
        ResultSet rs;
        List<String> locationIds = new ArrayList<String>();
        stmt = connection.prepareStatement(sql);
        rs = stmt.executeQuery();
        while (rs.next()) {
            locationIds.add(rs.getString(1));
        }
        rs.close();
        stmt.close();
        return locationIds;
    }


    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/galaxy?autoReconnect=true", "microwise", "microwise");
        done(connection);
    }
}

