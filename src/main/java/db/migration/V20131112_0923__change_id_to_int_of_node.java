package db.migration;


import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author gaohui
 * @date 13-11-12 09:24
 */
public class V20131112_0923__change_id_to_int_of_node implements JdbcMigration {
    @Override
    public void migrate(Connection connection) throws Exception {
        Set<String> allTables = allTables(connection);
        Set<String> allDevices = allDeviceIds(connection);
        for(String device: allDevices){
            if(allTables.contains(device)){
                migrateDeviceTable(connection, device);
            }
        }
    }

    /**
     * 迁移一个设备的历史数据表
     *
     * @param connection
     * @param deviceTable
     */
    private static void migrateDeviceTable(Connection connection, String deviceTable){
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            System.out.println(new Date().toString() + "  " + deviceTable + " 开始");
            System.out.println(new Date() +  "   drop key");
            stmt.execute("ALTER TABLE `" + deviceTable + "` DROP PRIMARY KEY");
            System.out.println(new Date() +  "   set num");
            stmt.execute("SET @r=0");
            stmt.execute("UPDATE `" + deviceTable + "` SET id= @r:= (@r+1) ORDER BY createtime, sensorPhysicalid");
            System.out.println(new Date() +  "   add key");
            stmt.execute("ALTER TABLE `" + deviceTable + "` ADD PRIMARY KEY(id)");
            System.out.println(new Date() +  "   modify column");
            stmt.execute("ALTER TABLE `" + deviceTable + "` MODIFY id INT(11) AUTO_INCREMENT");
            System.out.println(new Date().toString() + "  " + deviceTable + " 完成");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(stmt != null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 返回所有设备ID
     *
     * @param connection
     * @return
     */
    private static Set<String> allDeviceIds(Connection connection) {
        Set<String> deviceIds = new LinkedHashSet<String>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement("select * from m_nodeinfo ");

            rs = stmt.executeQuery();
            while (rs.next()) {
                deviceIds.add(rs.getString("nodeid"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return deviceIds;
    }

    /**
     * 返回所有的表名
     */
    private static Set<String> allTables(Connection connection) {
        Set<String> tables = new LinkedHashSet<String>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement("SHOW FULL TABLES WHERE TABLE_TYPE LIKE \'BASE_TABLE\'");
            rs = stmt.executeQuery();
            while (rs.next()) {
                String tableName = rs.getString(1);
                tables.add(tableName);
            }
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
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return tables;
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/galaxy_migrate", "root", "1234");
        Set<String> tables = allTables(connection);
        System.out.println(tables);
        System.out.println(allDeviceIds(connection));
        for(String tableName :args){
            migrateDeviceTable(connection, tableName);
        }
    }
}
