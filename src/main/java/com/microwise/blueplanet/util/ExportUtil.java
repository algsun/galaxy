package com.microwise.blueplanet.util;

import com.microwise.blueplanet.action.offline.ExcelUtil;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.RecentDataVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.dao.LocationDao;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.common.sys.Constants;
import com.microwise.common.util.DateTimeUtil;
import com.microwise.common.util.PagingUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhangpeng
 * @date 2013-2-26
 */
@Component
public class ExportUtil {

    /**
     * 文件类型：excel
     */
    public static final int FILR_TYPE_EXCEL = 1;

    private static LocationDao locationDao;

    private static LocationService locationService;

    @Autowired
    public void setLocationDao(LocationDao locationDao) {
        ExportUtil.locationDao = locationDao;
    }

    @Autowired
    public void setLocationService(LocationService locationService) {
        ExportUtil.locationService = locationService;
    }

    /**
     * 获得文件文件名
     *
     * @param deviceName 设备名称
     * @param deviceId   设备对象
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @param isZip      是否是zip 文件名
     * @return string excel文件名称
     * @author 许保吉
     * @date 2013-2-5
     */
    public static String getFileName(String deviceName, String deviceId,
                                     Date startTime, Date endTime, boolean isZip) {
        String fileName;
        String minTime = DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD, startTime);
        String maxTime = DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD, endTime);
        fileName = (deviceName == null ? "" : deviceName) + "(" + deviceId
                + ")" + " "
                + (minTime.equals(maxTime) ? minTime : minTime + "至" + maxTime)
                + "历史数据" + (isZip ? ".zip" : ".xlsx");
        return fileName;
    }

    /**
     * 获得导出站点、区域历史数据的文件名称
     *
     * @param deviceName
     * @param startTime
     * @param endTime
     * @param isZip
     * @return
     */
    public static String getFileName(String deviceName,
                                     Date startTime, Date endTime, boolean isZip) {
        String fileName;
        String minTime = DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD, startTime);
        String maxTime = DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD, endTime);
        fileName = (deviceName == null ? "" : deviceName)
                + (minTime.equals(maxTime) ? minTime : minTime + "至" + maxTime)
                + "历史数据" + (isZip ? ".zip" : ".xlsx");
        return fileName;
    }

    /**
     * 获得excel文件的最小时间
     *
     * @param currentYear 当前年
     * @param startTime   开始时间
     * @return DateTime
     * @author 许保吉
     * @date 2013-2-26
     */
    public static DateTime getMinTime(Integer currentYear, Date startTime) {
        DateTime startDataTime = new DateTime(startTime);
        return currentYear == startDataTime.getYear() ? startDataTime
                : new DateTime(currentYear, 1, 1, 0, 0, 0);
    }

    /**
     * 获得excel文件的最大时间
     *
     * @param currentYear 当前年
     * @param endTime     结束时间
     * @return DateTime
     * @author 许保吉
     * @date 2013-2-26
     */
    public static DateTime getMaxTime(Integer currentYear, Date endTime) {
        DateTime endDateTime = new DateTime(endTime);
        return currentYear == endDateTime.getYear() ? endDateTime
                : new DateTime(currentYear, 12, 31, 23, 59, 59);
    }

    /**
     * 创建Excel Workbook
     *
     * @param deviceName
     * @param locationId
     * @param sensorinfoList
     * @param startTime
     * @param endTime
     * @return
     */
    public static Workbook createWorkbook(String deviceName, String locationId,
                                          List<SensorinfoVO> sensorinfoList, DateTime startTime,
                                          DateTime endTime) {
        Workbook workbook = new SXSSFWorkbook();
        int currentMonth = startTime.getMonthOfYear(); // 当前月份
        // 当当前月大于结束月时结束while循环
        while (endTime.getMonthOfYear() >= currentMonth) {
            createSheet(workbook, currentMonth, startTime, endTime, deviceName,
                    locationId, sensorinfoList);

            currentMonth++;
        }
        return workbook;
    }

    /**
     * 导出历史数据为excel
     *
     * @param location
     * @param yearList
     * @param startTime
     * @param endTime
     * @param sensorinfoList
     * @throws IOException
     */
    public static void exportExcel(LocationVO location, File tempDir,
                                   List<Integer> yearList, Date startTime, Date endTime,
                                   List<SensorinfoVO> sensorinfoList) throws IOException {

        for (Integer integer : yearList) {
            DateTime minTime = ExportUtil.getMinTime(integer, startTime);
            DateTime maxTime = ExportUtil.getMaxTime(integer, endTime);
            String locationId = location.getId();
            String locationName = location.getLocationName();
            Map<String, Date> dateMap = locationDao.findMaxAndMinTime(locationId,
                    minTime.toDate(), maxTime.toDate(), null, null);
            String fileName = ExportUtil.getFileName(locationName, locationId,
                    dateMap.get(Constants.Blueplanet.MAP_KEY_MIN_TIME),
                    dateMap.get(Constants.Blueplanet.MAP_KEY_MAX_TIME), false);
            Workbook workbook = createWorkbook(locationName, locationId,
                    sensorinfoList, minTime, maxTime);
            fileName = new String(fileName.getBytes("UTF-8"),System.getProperty("file.encoding"));
            File zipFile = new File(tempDir, fileName);
            if (!zipFile.exists()) {
                zipFile.createNewFile();
            }
            workbook.write(new BufferedOutputStream(new FileOutputStream(zipFile)));
        }
    }

    /**
     * 创建sheet
     *
     * @param workbook
     * @param currentMonth
     * @param startTime
     * @param endTime
     * @param locationName
     * @param locationId
     * @param sensorinfoList
     */
    public static void createSheet(Workbook workbook, int currentMonth,
                                   DateTime startTime, DateTime endTime, String locationName,
                                   String locationId, List<SensorinfoVO> sensorinfoList) {
        int year = startTime.getYear(); // 获得当前年份
        int startMonth = startTime.getMonthOfYear(); // 开始月份
        int endMonth = endTime.getMonthOfYear(); // 结束月份
        // 创建sheet,计算 每个sheet 中历史数据的开始时间和结束时间
        DateTime minTime = new DateTime(startTime.getYear(), currentMonth, 1,
                0, 0, 0);
        // 用来获得当前月的最大天
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateTimeUtil.parseUncheck(DateTimeUtil.YYYY_MM_DD, year + "-"
                + currentMonth + "-01"));

        DateTime maxTime = new DateTime(endTime.getYear(), currentMonth,
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
        if (currentMonth == startMonth) {
            minTime = startTime;
        }
        if (currentMonth == endMonth) {
            maxTime = endTime;
        }
        // 判断当前月有没有数据
        int count = locationDao.findRecentDataListCount(locationId, minTime.toDate(),
                maxTime.toDate());
        if (count > 0) {
            int sheets = PagingUtil.pagesCount(count, 60000);
            for (int i = 1; i < sheets + 1; i++) {
                Sheet sheet = workbook.createSheet(currentMonth + "月份" + (sheets > 1 ? "-" + i : ""));
                // 获得当前月的历史数据
                List<RecentDataVO> historyDataList = locationService.findRecentDataList(locationId,
                        minTime.toDate(), maxTime.toDate(), i, 60000);
                Map<String, Date> dateMap = locationDao.findMaxAndMinTime(locationId,
                        minTime.toDate(), maxTime.toDate(), null, null);
                String sheetTitle = ExportUtil.getFileName(locationName, locationId,
                        dateMap.get(Constants.Blueplanet.MAP_KEY_MIN_TIME),
                        dateMap.get(Constants.Blueplanet.MAP_KEY_MAX_TIME), false);
                new ExcelUtil().assembleLocationSheet(sensorinfoList, historyDataList, sheet,
                        sheetTitle.substring(0, sheetTitle.length() - 4));
            }
        }
    }

    private static final int BUFFER_SIZE = 2 * 1024;

    /**
     * 压缩成ZIP
     *
     * @param srcDir           压缩文件夹路径
     * @param out              压缩文件输出流
     * @param KeepDirStructure 是否保留原来的目录结构,true:保留目录结构;
     *                         false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void toZip(String srcDir, OutputStream out, boolean KeepDirStructure)
            throws RuntimeException {

        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            zos.setEncoding("gbk");
            File sourceFile = new File(srcDir);
            compress(sourceFile, zos, sourceFile.getName(), KeepDirStructure);
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) + " ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 递归压缩方法
     *
     * @param sourceFile       源文件
     * @param zos              zip输出流
     * @param name             压缩后的名称
     * @param KeepDirStructure 是否保留原来的目录结构,true:保留目录结构;
     *                         false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws Exception
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name,
                                 boolean KeepDirStructure) throws Exception {
        byte[] buf = new byte[BUFFER_SIZE];
        if (sourceFile.isFile()) {
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if (KeepDirStructure) {
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }

            } else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (KeepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compress(file, zos, name + "/" + file.getName(), KeepDirStructure);
                    } else {
                        compress(file, zos, file.getName(), KeepDirStructure);
                    }

                }
            }
        }
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     * If a deletion fails, the method stops attempting to
     * delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

}
