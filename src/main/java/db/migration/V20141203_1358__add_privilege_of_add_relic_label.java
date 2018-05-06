package db.migration;


import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.*;

/**
 * 设备迁移到位置点
 *
 * @author xuyuexi
 * @date 2014-11-10
 */
public class V20141203_1358__add_privilege_of_add_relic_label implements JdbcMigration {

    public static final String QUERY_LABEL = "SELECT COUNT(1) FROM  `t_system_privilege` WHERE  privilegeId = 'orion:culturalRelic:label';";
    public static final String QUERY_LABEL_ADD ="SELECT COUNT(1) FROM  `t_system_privilege` WHERE  privilegeId ='orion:culturalRelic:label:add';";

    public static final String QUERY_LABEL_DELETE ="SELECT COUNT(1) FROM  `t_system_privilege` WHERE  privilegeId ='orion:culturalRelic:label:delete';";

    public static final String INSERT_LABEL = "INSERT INTO t_system_privilege (\n" +
            "  subsystemId,\n" +
            "  sequence,\n" +
            "  privilegeId,\n" +
            "  parentPrivilegeId,\n" +
            "  privilegeName_CN,\n" +
            "  url,\n" +
            "  state,\n" +
            "  isNavigation\n" +
            ")\n" +
            "VALUES\n" +
            "  (\n" +
            "    4,\n" +
            "    2,\n" +
            "    'orion:culturalRelic:label',\n" +
            "    'orion:culturalRelic',\n" +
            "    '自定义标签',\n" +
            "    '',\n" +
            "    50,\n" +
            "    0\n" +
            "  ) ;";

    public static final String INSERT_LABEL_ADD = "INSERT INTO t_system_privilege (\n" +
            "  subsystemId,\n" +
            "  sequence,\n" +
            "  privilegeId,\n" +
            "  parentPrivilegeId,\n" +
            "  privilegeName_CN,\n" +
            "  url,\n" +
            "  state,\n" +
            "  isNavigation\n" +
            ")\n" +
            "VALUES\n" +
            "  (\n" +
            "    4,\n" +
            "    2,\n" +
            "    'orion:culturalRelic:label:add',\n" +
            "    'orion:culturalRelic:label',\n" +
            "    '添加自定义标签',\n" +
            "    'addRelicLabel.action',\n" +
            "    50,\n" +
            "    0\n" +
            "  ) ;";

    public static final String INSERT_LABEL_DELETE = "INSERT INTO t_system_privilege (\n" +
            "  subsystemId,\n" +
            "  sequence,\n" +
            "  privilegeId,\n" +
            "  parentPrivilegeId,\n" +
            "  privilegeName_CN,\n" +
            "  url,\n" +
            "  state,\n" +
            "  isNavigation\n" +
            ")\n" +
            "VALUES\n" +
            "  (\n" +
            "    4,\n" +
            "    2,\n" +
            "    'orion:culturalRelic:label:delete',\n" +
            "    'orion:culturalRelic:label',\n" +
            "    '删除自定义标签',\n" +
            "    'deleteRelicLabel.action',\n" +
            "    50,\n" +
            "    0\n" +
            "  ) ;";

    public static final String INSERT_LABEL_PRIVILEGE = "INSERT INTO `t_role_privilege` (roleId, privilegeId)\n" +
            "(SELECT\n" +
            "  id AS roldId,\n" +
            "  'orion:culturalRelic:label' AS privilegeId\n" +
            "FROM\n" +
            "  `t_roles`\n" +
            "WHERE isManager = 1) ;";

    public static final String INSERT_LABEL_ADD_PRIVILEGE = "INSERT INTO `t_role_privilege` (roleId, privilegeId)\n" +
            "(SELECT\n" +
            "  id AS roldId,\n" +
            "  'orion:culturalRelic:label:add' AS privilegeId\n" +
            "FROM\n" +
            "  `t_roles`\n" +
            "WHERE isManager = 1) ;";

    public static final String INSERT_LABEL_DELETE_PRIVILEGE = "INSERT INTO `t_role_privilege` (roleId, privilegeId)\n" +
            "(SELECT\n" +
            "  id AS roldId,\n" +
            "  'orion:culturalRelic:label:delete' AS privilegeId\n" +
            "FROM\n" +
            "  `t_roles`\n" +
            "WHERE isManager = 1) ;";

    @Override
    public void migrate(Connection connection) throws Exception {
        addLabel(connection);
        addLabel_add(connection);
        addLabel_delete(connection);
    }

    private static void addLabel(Connection connection) {
        if (!isDataExist(connection, QUERY_LABEL)) {
            insertData(connection, INSERT_LABEL);
            insertData(connection, INSERT_LABEL_PRIVILEGE);
        }
    }

    private static void addLabel_add(Connection connection) {
        if (!isDataExist(connection, QUERY_LABEL_ADD)) {
            insertData(connection, INSERT_LABEL_ADD);
            insertData(connection, INSERT_LABEL_ADD_PRIVILEGE);
        }
    }

    private static void addLabel_delete(Connection connection) {
        if (!isDataExist(connection, QUERY_LABEL_DELETE)) {
            insertData(connection, INSERT_LABEL_DELETE);
            insertData(connection, INSERT_LABEL_DELETE_PRIVILEGE);
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
        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.0.58:3306/galaxy_20140902", "microwise", "microwise");
        addLabel(connection);
        addLabel_add(connection);
        addLabel_delete(connection);
    }
}
