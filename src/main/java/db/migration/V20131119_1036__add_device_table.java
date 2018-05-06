package db.migration;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 给每个设备补设备历史信息表
 *
 * @author gaohui
 * @date 13-11-19 10:37
 */
public class V20131119_1036__add_device_table implements JdbcMigration {
    @Override
    public void migrate(Connection connection) throws Exception {
        for(String deviceId: allDeviceIds(connection)){
            createDeviceTable(connection, deviceId);
        }
    }

    private static void createDeviceTable(Connection connection, String deviceId) {
        String sql = "CREATE TABLE IF NOT EXISTS `m_device_" + deviceId + "` (\n" +
                "    id INT (11) NOT NULL AUTO_INCREMENT,\n" +
                "    nodeId VARCHAR (15) NOT NULL COMMENT '产品入网唯一标识=接入点号（8位）+ip号（5位）',\n" +
                "    `timestamp` TIMESTAMP NOT NULL COMMENT '时间戳',\n" +
                "    nodeVersion TINYINT NOT NULL COMMENT '节点协议版本号',\n" +
                "    parentIP SMALLINT UNSIGNED NOT NULL COMMENT '父节点IP号',\n" +
                "    selfIP SMALLINT UNSIGNED NOT NULL COMMENT '当前节点IP号',\n" +
                "    `sequence` TINYINT UNSIGNED NOT NULL COMMENT '包序列号',\n" +
                "    workInterval SMALLINT NOT NULL DEFAULT 600 COMMENT '工作周期',\n" +
                "    deviceMode TINYINT NOT NULL DEFAULT 0 COMMENT '0：正常模式 1：巡检模式',\n" +
                "    rssi SMALLINT NOT NULL COMMENT '接收信号强度',\n" +
                "    lqi SMALLINT NOT NULL COMMENT '连接质量参数',\n" +
                "    voltage FLOAT NOT NULL DEFAULT - 1 COMMENT '电压：-1、无电压值，其他的、电压值',\n" +
                "    anomaly TINYINT NOT NULL DEFAULT 0 COMMENT '设备状态：-1、超时, 0、正常, 1、低电压, 2、掉电',\n" +
                "    sdCardState TINYINT NOT NULL DEFAULT 0 COMMENT 'SD卡状态：0未插卡或卡未插好 1卡已插好 2卡已写满',\n" +
                "    remoteHost VARCHAR (15) NOT NULL DEFAULT '192.168.0.1' COMMENT '网关IP',\n" +
                "    remotePort SMALLINT UNSIGNED NOT NULL DEFAULT 10000 COMMENT '网关数据监听端口',\n" +
                "    dataVersion INT (11) NOT NULL DEFAULT 0 COMMENT '数据同步版本',\n" +
                "    PRIMARY KEY (id),\n" +
                "    INDEX `timestamp` (`timestamp`)\n" +
                ") ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT = '设备信息历史表'";
        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
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

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/galaxy", "root", "1234");
        createDeviceTable(connection, "1234");
    }
}
