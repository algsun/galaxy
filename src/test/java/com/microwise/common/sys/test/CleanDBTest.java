/**
 *
 */
package com.microwise.common.sys.test;

import com.google.common.io.Closeables;
import com.google.common.io.Resources;
import com.microwise.common.sys.Tests;
import com.microwise.common.util.cleanDB.DatabaseAnalyzer;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Dbunit测试工具类
 *
 * @author zhangpeng
 * @date 2012-12-13
 * @check 2012-12-13 xubaoji svn:805
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public abstract class CleanDBTest {
    public static final Logger log = LoggerFactory.getLogger("com.microwise.clean-db");

    private static List<String> ignoredTables;
    private static Map<String, Object> config;

    static {
        Yaml yaml = new Yaml();
        InputStream input = null;
        try {
            input = Resources.getResource(Tests.CLEAN_DB_CONFIG_PATH).openStream();
            config = (Map<String, Object>) yaml.load(input);
            ignoredTables = (List<String>) config.get(Tests.FILTE_TABLE);
        } catch (IOException e) {
            log.error("load config", e);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                log.error("", e);
            }
        }
        try {
            Class.forName((String) config.get(Tests.DB_DRIVER));
        } catch (ClassNotFoundException e) {
            log.error("", e);
        }
    }

    /**
     * 获取数据源
     */
    public static Connection getConn() throws SQLException,
            ClassNotFoundException {
        return DriverManager.getConnection(
                (String) config.get(Tests.DB_URL),
                (String) config.get(Tests.DB_USERNAME),
                (String) config.get(Tests.DB_PASSWORD));
    }

    /**
     * 后置方法，清库（排除字典表等关键信息表）
     */
    public static void tearDownOperation() throws Exception {
        log.info("cleanDB...>>>");
        Connection conn = getConn();

        log.debug("cleanDatabase >>>");
        DatabaseAnalyzer.cleanDatabase(conn, ignoredTables);
        conn.close();
        log.debug("cleanDatabase <<<");
        log.info("cleanDB...<<<");
    }

    @After
    public void cleanDB() throws Exception {
        tearDownOperation();
    }

    /**
     * 根据xml文件路径插库，准备数据
     *
     * @deprecated
     */
    @SuppressWarnings("deprecation")
    public static void xmlInsert(String xmlPath) throws Exception {
        Connection conn = getConn();
        DatabaseOperation.INSERT.execute(new DatabaseConnection(conn),
                new FlatXmlDataSet(new FileInputStream(xmlPath)));
        conn.close();
    }

    /**
     * 根据xml文件路径插库，准备数据
     *
     * @param xmlPath classpath 路径
     */
    @SuppressWarnings("deprecation")
    public static void xmlInsert2(String xmlPath) throws Exception {
        Connection conn = null;
        try {
            conn = getConn();
            InputStream inputStream = null;
            try {
                inputStream = findInClasspath(xmlPath);
                DatabaseOperation.INSERT.execute(new DatabaseConnection(conn), new FlatXmlDataSet(inputStream));
            } finally {
                Closeables.close(inputStream, true);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * 返回 classpath 下的资源 input
     *
     * @param path
     * @return
     * @throws IOException
     */
    private static InputStream findInClasspath(String path) throws IOException {
        return Resources.getResource(path).openStream();
    }
}
