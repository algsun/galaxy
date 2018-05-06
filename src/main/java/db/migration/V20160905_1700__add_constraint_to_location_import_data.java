package db.migration;


import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 位置点历史数据表添加索引
 *
 * @author chen.yaofei
 * @date 2016-09-05
 */
public class V20160905_1700__add_constraint_to_location_import_data implements JdbcMigration {

    public static final String QUERY_SQL = "SELECT id FROM m_location ORDER BY id+0 ASC";

    @Override
    public void migrate(Connection connection) throws Exception {
        done(connection);
    }


    private static void done(Connection connection) throws SQLException {
        //获取位置点历史数据表明
        List<String> locationIds = getStringList(connection, QUERY_SQL);
        for (String locationId : locationIds) {
            //整理需要删除的冗余数据，每次获取一百万条数据，循环删除
            List<String> dataIds = getStringList(connection, getFindDeleteDataSql(locationId));
            while(dataIds.size() > 0){
                //删除冗余数据
                deleteData(locationId, dataIds, connection);
                //整理需要删除的冗余数据
                dataIds = getStringList(connection, getFindDeleteDataSql(locationId));
            }
            //添加监测指标和创建时间的唯一索引
            update(connection, getAddConstraintSql(locationId));
        }
    }

    private static String getFindDeleteDataSql(String tableName) {
        return "SELECT a.id FROM `" + tableName + "` a, " +
                "(SELECT MAX(id) AS id, sensorPhysicalid,createtime FROM `" + tableName + "` " +
                "GROUP BY sensorPhysicalid,createtime  HAVING COUNT(1) > 1) b " +
                " WHERE a.`sensorPhysicalid`=b.sensorPhysicalId AND a.createtime=b.createtime AND a.id <> b.id LIMIT 0,1000000";
    }

    private static void deleteData(String tableName, List<String> dataIds, Connection connection) throws SQLException {
        String idSet = "";
        int sqlSize = 0;
        for (String id : dataIds) {
            idSet = idSet + id + ",";
            sqlSize++;
            //每十万条数据删除一次
            if (sqlSize == 100000) {
                idSet = idSet.substring(0, idSet.length() - 1);
                String deleteSql = "DELETE FROM `" + tableName + "` WHERE id IN (" + idSet + ")";
                update(connection, deleteSql);
                idSet = "";
                sqlSize = 0;
            }
        }
        //当数据少于100000条时，最后一次删除数据
        if(!"".equals(idSet)){
            idSet = idSet.substring(0, idSet.length() - 1);
            String deleteSql = "DELETE FROM `" + tableName + "` WHERE id IN (" + idSet + ")";
            update(connection, deleteSql);
        }
    }

    private static String getAddConstraintSql(String tableName) {
        return "ALTER TABLE `" + tableName + "`" +
                " ADD CONSTRAINT " + tableName + "physicalIdTime UNIQUE(sensorPhysicalid,createtime)";
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

    private static void update(Connection connection, String sql) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.executeUpdate();
        stmt.close();
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.0.118:3306/galaxy_20160816?autoReconnect=true", "microwise", "microwise");
        done(connection);
    }
}
