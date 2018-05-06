package db.migration;

import com.google.common.base.Strings;
import com.microwise.common.sys.Constants;
import org.apache.commons.lang.StringUtils;
import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * 设备迁移到位置点
 *
 * @author li.jianfei
 * @date 2014-08-06
 */
public class V20140805_1036__device_migrate_location implements JdbcMigration {
    @Override
    public void migrate(Connection connection) throws Exception {
        deviceMigrateToLocation(connection);
    }

    /**
     * 返回所有设备ID
     *
     * @param connection
     * @return
     */
    private static void deviceMigrateToLocation(Connection connection) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String siteId;

        String locationId;
        try {
            // 查询所有设备信息
            stmt = connection.prepareStatement("SELECT \n" +
                    "  n.nodeId,\n" +
                    "  n.nodeName,\n" +
                    "  n.nodetype,\n" +
                    "  n.roomId AS zoneId,\n" +
                    "  nm.parentIP,\n" +
                    "  n.siteId,\n" +
                    "  IF(table_name IS NULL, 0, 1) hasDataTable \n" +
                    "FROM\n" +
                    "  m_nodeinfo n \n" +
                    "  LEFT JOIN m_nodeinfomemory nm\n" +
                    "    ON n.nodeid = nm.nodeid\n" +
                    "  LEFT JOIN information_schema.tables t \n" +
                    "    ON n.nodeid = t.table_name \n" +
                    "WHERE (\n" +
                    "    TABLE_SCHEMA = DATABASE() \n" +
                    "    OR table_schema IS NULL\n" +
                    "  ) \n" +
                    "ORDER BY nodeid ");

            rs = stmt.executeQuery();
            while (rs.next()) {

                siteId = rs.getString("siteId");
                boolean hasDataTable = rs.getBoolean("hasDataTable");
                // 计算位置点ID
                stmt = connection.prepareStatement("select max(id) as locationId from m_location where siteId=?");
                stmt.setString(1, siteId);
                ResultSet rsTmp = stmt.executeQuery();
                if (rsTmp.next()) {
                    locationId = rsTmp.getString("locationId");
                    if (StringUtils.isNotBlank(locationId)) {
                        locationId = String.valueOf(Long.parseLong(locationId) + 1);
                    } else {
                        locationId = siteId.concat(Constants.Blueplanet.DEFAULT_FIRST_LOCATIONID);
                    }
                } else {
                    locationId = siteId.concat(Constants.Blueplanet.DEFAULT_FIRST_LOCATIONID);
                }

                // 插入位置点数据
                // m_location
                String nodeId = rs.getString("nodeId");
                String nodeName = rs.getString("nodeName");
                int nodeType = rs.getInt("nodeType");
                String locationName = nodeName;
                if (Strings.isNullOrEmpty(nodeName)) {
                    locationName = nodeId.substring(8);
                }
                String zoneId = rs.getString("zoneId");

                // 从模块区域设置为主模块区域
                if (nodeType == 4) {
                    // 查询主模块区域id
                    stmt = connection.prepareStatement("SELECT roomId zoneId FROM m_nodeinfo n " +
                            "LEFT JOIN  m_nodeinfomemory nm ON n.nodeid=nm.nodeid WHERE childIP=?");
                    stmt.setInt(1, rs.getInt("parentIP"));
                    rsTmp = stmt.executeQuery();
                    if (rsTmp.next()) {
                        if (Strings.isNullOrEmpty(rsTmp.getString("zoneId"))) {
                            zoneId = rsTmp.getString("zoneId");
                        }
                    }
                }

                if (zoneId == null || zoneId.equals("0")) {
                    zoneId = null;
                }

                // 查询创建时间(以设备第一条数据时间作为位置点创建时间)
                Date createTime = new Date();
                if (hasDataTable) {
                    stmt = connection.prepareStatement("SELECT  min(createtime) as createTime FROM `" + nodeId + "`");
                    rsTmp = stmt.executeQuery();
                    if (rsTmp.next()) {
                        if (rsTmp.getDate("createTime") != null) {
                            createTime = rsTmp.getDate("createTime");
                        }
                    }
                }
                stmt = connection.prepareStatement("INSERT INTO m_location (id, locationName, nodeId, zoneId, siteId, createTime)" +
                        " VALUES (?,?,?,?,?,?)");
                stmt.setString(1, locationId);
                stmt.setString(2, locationName);
                stmt.setString(3, nodeId);
                stmt.setObject(4, zoneId);
                stmt.setString(5, siteId);
                stmt.setDate(6, new java.sql.Date(createTime.getTime()));
                stmt.execute();

                // m_location_history
                stmt = connection.prepareStatement("INSERT INTO m_location_history (id, locationId, nodeId, startTime)" +
                        " VALUES (uuid(), ?, ?, ?)");
                stmt.setString(1, locationId);
                stmt.setString(2, nodeId);
                stmt.setDate(3, new java.sql.Date(createTime.getTime()));
                stmt.execute();
                System.out.println("INSERT INTO m_location_history (id, locationId, nodeId, startTime)" +
                        " VALUES (" + UUID.randomUUID() + ", " + locationId + "," + nodeId + "," + new java.sql.Date(createTime.getTime()) + " )");

                // 调整位置点数据表
                if (hasDataTable) {
                    stmt.execute("ALTER TABLE `" + nodeId + "` RENAME TO `" + locationId + "`");
                    System.out.println("ALTER TABLE `" + nodeId + "` RENAME TO `" + locationId + "`");
                } else {
                    stmt.execute("CREATE TABLE IF NOT EXISTS `" + locationId + "` (\n" +
                            "                id INT(11) NOT NULL AUTO_INCREMENT,\n" +
                            "                nodeid VARCHAR(20) NOT NULL COMMENT '产品入网唯一标识',\n" +
                            "                sensorPhysicalid INT(11) NOT NULL COMMENT '传感标识',\n" +
                            "                sensorPhysicalvalue VARCHAR(30) NOT NULL COMMENT '传感值',\n" +
                            "                lowvoltage FLOAT NOT NULL DEFAULT 0 COMMENT '电压值(默认为0)',\n" +
                            "                createtime DATETIME NOT NULL COMMENT '创建时间',\n" +
                            "                state INT(11) NOT NULL COMMENT '传感状态0：采样失败,1：采样正常,2：低于低阀值,3：超过高阀值,4：空数据',\n" +
                            "                dataVersion INT(11) DEFAULT 0 COMMENT '数据版本',\n" +
                            "                anomaly INT(11) NOT NULL DEFAULT '0' COMMENT '-1：超时,0：正常,1：低压,2：掉电',\n" +
                            "                PRIMARY KEY  (id),\n" +
                            "                INDEX(createtime)\n" +
                            "               )ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='节点数据表';");
                    System.out.println("CREATE TABLE IF NOT EXISTS `" + locationId + "` (\n" +
                            "                id INT(11) NOT NULL AUTO_INCREMENT,\n" +
                            "                nodeid VARCHAR(20) NOT NULL COMMENT '产品入网唯一标识',\n" +
                            "                sensorPhysicalid INT(11) NOT NULL COMMENT '传感标识',\n" +
                            "                sensorPhysicalvalue VARCHAR(30) NOT NULL COMMENT '传感值',\n" +
                            "                lowvoltage FLOAT NOT NULL DEFAULT 0 COMMENT '电压值(默认为0)',\n" +
                            "                createtime DATETIME NOT NULL COMMENT '创建时间',\n" +
                            "                state INT(11) NOT NULL COMMENT '传感状态0：采样失败,1：采样正常,2：低于低阀值,3：超过高阀值,4：空数据',\n" +
                            "                dataVersion INT(11) DEFAULT 0 COMMENT '数据版本',\n" +
                            "                anomaly INT(11) NOT NULL DEFAULT '0' COMMENT '-1：超时,0：正常,1：低压,2：掉电',\n" +
                            "                PRIMARY KEY  (id),\n" +
                            "                INDEX(createtime)\n" +
                            "               )ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='节点数据表';");
                }

                // 更新坐标信息
                stmt = connection.prepareStatement("UPDATE m_coordinate SET locationId = ? WHERE locationId = ?");
                stmt.setString(1, locationId);
                stmt.setString(2, nodeId);
                stmt.execute();
            }
            // 外展   设备-->位置点
            stmt = connection.prepareStatement("UPDATE h_device SET deviceId=(SELECT id FROM m_location WHERE deviceId=nodeId)");
            stmt.execute();
        } catch (Exception e) {
            throw e;
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
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.0.184:3306/galaxy_bmy", "root", "admin");
        try {
            deviceMigrateToLocation(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
