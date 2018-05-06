package db.migration;


import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * m_device_表添加酸雨状态字段
 *
 * @author 李建飞
 * @date 2015-06-05
 */
public class V20150908_1700__change_orion_resources_folder_name implements JdbcMigration {

    /**
     * 藏品信息挂接文档上传子目录
     */
    private static final String FILE_UPLOAD_PATH = "file";

    /**
     * 文档编辑，图片上传子目录
     */
    private static final String IMAGE_UPLOAD_PATH = "images";

    private static final String RESOURCES_HOME = "/usr/local/apache-tomcat-8.0.21/webapps/galaxy-resources";

    @Override
    public void migrate(Connection connection) throws Exception {
        execute(connection);
    }
    private void execute(Connection connection) throws FileNotFoundException {
        File file = new File(RESOURCES_HOME);
        if (!file.exists()) {
            System.out.println("请找到该文件，在文件中配置正确的资源文件路径，路径配置完成后记得重新编译哦！！！");
//            throw new FileNotFoundException("未找到资源目录");
        } else {

            List<Relic> relics = getRelics(connection);
            for (Relic relic : relics) {
                String oldFileDirName = RESOURCES_HOME + "/orion" + "/" + FILE_UPLOAD_PATH + "/" + relic.siteId + "/" + relic.totalCode;
                String newFileDirName = RESOURCES_HOME + "/orion" + "/" + FILE_UPLOAD_PATH + "/" + relic.siteId + "/" + relic.relicId;
                File oldFileDir = new File(oldFileDirName);
                if (oldFileDir.exists()) {
                    boolean success = oldFileDir.renameTo(new File(newFileDirName));
                    if (success) {
                        System.out.println(RESOURCES_HOME + "/orion" + "/" + FILE_UPLOAD_PATH + "/" + relic.siteId + "的子目录【" + relic.totalCode + "】重命名为【" + relic.relicId + "】");
                    } else {
                        System.out.println("文件夹重命名失败");
                    }
                }


                String oldImageDirName = RESOURCES_HOME + "/orion" + "/" + IMAGE_UPLOAD_PATH + "/" + relic.siteId + "/" + relic.totalCode;
                String newImageDirName = RESOURCES_HOME + "/orion" + "/" + IMAGE_UPLOAD_PATH + "/" + relic.siteId + "/" + relic.relicId;
                File oldImageDir = new File(oldImageDirName);
                if (oldImageDir.exists()) {
                    boolean success = oldImageDir.renameTo(new File(newImageDirName));
                    if (success) {
                        System.out.println(RESOURCES_HOME + "/orion" + "/" + IMAGE_UPLOAD_PATH + "/" + relic.siteId + "的子目录【" + relic.totalCode + "】重命名为【" + relic.relicId + "】");
                    } else {
                        System.out.println("文件夹重命名失败");
                    }
                }
            }
        }
    }


    private List<Relic> getRelics(Connection connection) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Relic> relics = new ArrayList<Relic>();
        try {
            stmt = connection.prepareStatement("SELECT r.siteId,r.id AS relicId,r.totalCode FROM o_historical_relic r,t_logicgroup l WHERE r.siteId=l.siteId");
            rs = stmt.executeQuery();
            Relic relic;
            while (rs.next()) {
                relic = new Relic();
                relic.siteId = rs.getString(1);
                relic.relicId = rs.getInt(2);
                relic.totalCode = rs.getString(3);
                relics.add(relic);
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
        return relics;
    }

    public static void main(String[] args) throws SQLException, FileNotFoundException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.0.3:3306/galaxy_20131010?autoReconnect=true", "microwise", "microwise");
        V20150908_1700__change_orion_resources_folder_name test = new V20150908_1700__change_orion_resources_folder_name();
        test.execute(connection);

    }

    private class Relic {
        public String siteId;
        public int relicId;
        public String totalCode;
    }
}
