package db.migration;


import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.*;

/**
 * 设备迁移到位置点
 *
 * @author xuyuexi
 * @date 2014-09-05
 */
public class V20141113_1553__add_privilege_of_post implements JdbcMigration {

    public static final String QUERY_POST = "SELECT COUNT(1) FROM  `t_role_privilege` WHERE  roleId IN (SELECT \n" +
            "  id \n" +
            " \n" +
            "FROM\n" +
            "  `t_roles`\n" +
            "WHERE isManager = 1) AND  privilegeId ='blackhole:post';";
    public static final String QUERY_POST_ADD = "SELECT COUNT(1) FROM  `t_role_privilege` WHERE  roleId IN (SELECT \n" +
            "  id \n" +
            " \n" +
            "FROM\n" +
            "  `t_roles`\n" +
            "WHERE isManager = 1) AND  privilegeId ='blackhole:post:add';";
    public static final String QUERY_POST_EDIT = "SELECT COUNT(1) FROM  `t_role_privilege` WHERE  roleId IN (SELECT \n" +
            "  id \n" +
            " \n" +
            "FROM\n" +
            "  `t_roles`\n" +
            "WHERE isManager = 1) AND  privilegeId ='blackhole:post:edit';";
    public static final String QUERY_POST_QUERY = "SELECT COUNT(1) FROM  `t_role_privilege` WHERE  roleId IN (SELECT \n" +
            "  id \n" +
            " \n" +
            "FROM\n" +
            "  `t_roles`\n" +
            "WHERE isManager = 1) AND  privilegeId ='blackhole:post:query';";

    public static final String INSERT_POST = "INSERT INTO `t_role_privilege` (roleId, privilegeId)\n" +
            "(SELECT\n" +
            "  id AS roldId,\n" +
            "  'blackhole:post' AS privilegeId\n" +
            "FROM\n" +
            "  `t_roles`\n" +
            "WHERE isManager = 1) ;";

    public static final String INSERT_POST_ADD = "INSERT INTO `t_role_privilege` (roleId, privilegeId)\n" +
            "(SELECT\n" +
            "  id AS roldId,\n" +
            "  'blackhole:post:add' AS privilegeId\n" +
            "FROM\n" +
            "  `t_roles`\n" +
            "WHERE isManager = 1) ;";
    public static final String INSERT_POST_EDIT = "INSERT INTO `t_role_privilege` (roleId, privilegeId)\n" +
            "(SELECT\n" +
            "  id AS roldId,\n" +
            "  'blackhole:post:edit' AS privilegeId\n" +
            "FROM\n" +
            "  `t_roles`\n" +
            "WHERE isManager = 1) ;";
    public static final String INSERT_POST_QUERY = "INSERT INTO `t_role_privilege` (roleId, privilegeId)\n" +
            "(SELECT\n" +
            "  id AS roldId,\n" +
            "  'blackhole:post:query' AS privilegeId\n" +
            "FROM\n" +
            "  `t_roles`\n" +
            "WHERE isManager = 1) ;";

    @Override
    public void migrate(Connection connection) throws Exception {
        addPost(connection);
        addPost_add(connection);
        addPost_edit(connection);
        addPost_query(connection);
    }

    private static void addPost(Connection connection) {
        if (!isDataExist(connection, QUERY_POST)) {
            insertData(connection, INSERT_POST);
        }
    }

    private static void addPost_add(Connection connection) {
        if (!isDataExist(connection, QUERY_POST_ADD)) {
            insertData(connection, INSERT_POST_ADD);
        }
    }

    private static void addPost_edit(Connection connection) {
        if (!isDataExist(connection, QUERY_POST_EDIT)) {
            insertData(connection, INSERT_POST_EDIT);
        }
    }

    private static void addPost_query(Connection connection) {
        if (!isDataExist(connection, QUERY_POST_QUERY)) {
            insertData(connection, INSERT_POST_QUERY);
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
        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.0.166:3306/galaxy_bmy2", "microwise", "microwise");
        addPost(connection);
        addPost_add(connection);
        addPost_edit(connection);
        addPost_query(connection);
    }
}
