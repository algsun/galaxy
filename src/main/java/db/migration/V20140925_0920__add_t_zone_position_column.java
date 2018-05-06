package db.migration;


import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * t_zone 添加 position
 *
 * @author li.jianfei
 * @date 2014-09-25
 */
public class V20140925_0920__add_t_zone_position_column implements JdbcMigration {
    @Override
    public void migrate(Connection connection) throws Exception {
        PreparedStatement stmt;
        ResultSet rs;

        stmt = connection.prepareStatement("  SELECT \n" +
                "    column_name \n" +
                "  FROM\n" +
                "    information_schema.`COLUMNS` c \n" +
                "  WHERE c.`TABLE_SCHEMA` = DATABASE() \n" +
                "    AND c.`TABLE_NAME` = 't_zone' \n" +
                "    AND c.`COLUMN_NAME` = 'position' ");

        rs = stmt.executeQuery();
        if (!rs.next()) {
            stmt = connection.prepareStatement(
                    "ALTER TABLE t_zone ADD position INT(1) DEFAULT 1 NOT NULL " +
                            "COMMENT '1-室内；2-室外。默认为1' AFTER planImage");
            stmt.execute();
        }
    }
}
