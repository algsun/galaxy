package com.microwise.blueplanet.action.offline;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.bean.po.LocationSensorPO;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.service.OfflineService;
import com.microwise.blueplanet.service.OfflineDataService;
import com.microwise.blueplanet.sys.AppCacheHolder;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 读取excle
 *
 * @author liuzhu
 * @date 2016-5-9
 */
@Component
public class ReadExcel {

    /**
     * read the Excel file
     *
     * @param path the path of the Excel file
     * @return
     * @throws java.io.IOException
     */

    @Autowired
    private LocationService locationService;

    @Autowired
    private OfflineService offlineService;

    @Autowired
    private OfflineDataService offlineDataService;

    @Autowired
    private AppCacheHolder appCacheHolder;

    public void readExcel(String path) throws IOException {
        if (path != null) {
            String postfix = Util.getPostfix(path);
            if (!Common.EMPTY.equals(postfix)) {
                if (Common.OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
                    readXls(path);
                } else if (Common.OFFICE_EXCEL_2007_POSTFIX.equals(postfix)) {
                    readXlsx(path);
                }
            } else {
                System.out.println(path + Common.NOT_EXCEL_FILE);
            }
        }
    }

    /**
     * Read the Excel 2007
     *
     * @param path the path of the excel file
     * @return
     * @throws IOException
     */
    public void readXlsx(String path) throws IOException {
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

        if (xssfSheet != null) {
            // Read the Row
            XSSFRow xssfRow0 = xssfSheet.getRow(0);
            int sensorNum = xssfRow0.getPhysicalNumberOfCells() - 2;
            List<Integer> sensorIds = new ArrayList<Integer>();
            for (int i = 1; i <= sensorNum; i++) {
                String sensorStr = xssfRow0.getCell(i).toString();
                String sensorIdStr = sensorStr.substring(0, sensorStr.indexOf("-"));
                double sensorId = Double.parseDouble(sensorIdStr);
                sensorIds.add((int) sensorId);
            }
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    XSSFCell name = xssfRow.getCell(0);
                    String siteId = Sessions.createByAction().currentSiteId();
                    boolean isExist = locationService.isExistLocationName(name.toString().trim(), siteId);
                    LocationVO location = null;
                    if (!isExist) {
                        //组织位置点
                        location = new LocationVO();
                        location.setId(locationService.getNewLocationId(siteId));
                        location.setSiteId(siteId);
                        location.setLocationName(name.toString());
                        location.setZoneId(null);
                        location.setType(1);
                        try {
                            locationService.addLocation(location);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //添加监测指标
                        offlineService.addLocationSensor(location.getId(), sensorIds);
                    } else {
                        location = locationService.findOneLocationsBySiteIdAndLocationName(siteId, name.toString().trim());
                    }

                    //组织位置点数据
                    List<LocationSensorPO> locationSensors = new ArrayList<LocationSensorPO>();
                    for (int i = 0; i < sensorIds.size(); i++) {
                        LocationSensorPO locationSensor = new LocationSensorPO();
                        locationSensor.setLocationId(location.getId());
                        locationSensor.setSensorPhysicalid(sensorIds.get(i));
                        XSSFCell sensorValue = xssfRow.getCell(i + 1);
                        if (sensorValue != null) {
                            SensorinfoVO sensorinfoVO = null;
                            try {
                                sensorinfoVO = appCacheHolder.loadSensorinfo(sensorIds.get(i));
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                            String valueStr = round(Double.parseDouble(sensorValue.toString()), sensorinfoVO.getSensorPrecision());
                            locationSensor.setSensorValue(valueStr);
                            locationSensor.setState(1);
                            locationSensors.add(locationSensor);
                        }
                    }
                    Date date = xssfRow.getCell(1 + sensorIds.size()).getDateCellValue();
                    Timestamp stamp = new Timestamp(date.getTime());
                    for (LocationSensorPO locationSensor : locationSensors) {
                        locationSensor.setStamp(stamp);
                    }
                    offlineDataService.insert(locationSensors);
                }
            }
        }
    }

    /**
     * Read the Excel 2003
     *
     * @param path the path of the Excel
     * @return
     * @throws java.io.IOException
     */
    public void readXls(String path) throws IOException {
        InputStream is = new FileInputStream(path);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        if (hssfSheet != null) {
            HSSFRow hssfRow0 = hssfSheet.getRow(0);
            int sensorNum = hssfRow0.getPhysicalNumberOfCells() - 2;
            List<Integer> sensorIds = new ArrayList<Integer>();
            for (int i = 1; i <= sensorNum; i++) {
                String sensorStr = hssfRow0.getCell(i).toString();
                String sensorIdStr = sensorStr.substring(0, sensorStr.indexOf("-"));
                double sensorId = Double.parseDouble(sensorIdStr);
                sensorIds.add((int) sensorId);
            }
            // Read the Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    HSSFCell name = hssfRow.getCell(0);
                    String siteId = Sessions.createByAction().currentSiteId();
                    boolean isExist = locationService.isExistLocationName(name.toString().trim(), siteId);
                    LocationVO location = null;
                    if (!isExist) {
                        //组织位置点
                        location = new LocationVO();
                        location.setId(locationService.getNewLocationId(siteId));
                        location.setSiteId(siteId);
                        location.setLocationName(name.toString());
                        location.setZoneId(null);
                        location.setType(1);
                        try {
                            locationService.addLocation(location);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //添加监测指标
                        offlineService.addLocationSensor(location.getId(), sensorIds);
                    } else {
                        location = locationService.findOneLocationsBySiteIdAndLocationName(siteId, name.toString().trim());
                    }
                    //组织位置点数据
                    List<LocationSensorPO> locationSensors = new ArrayList<LocationSensorPO>();
                    for (int i = 0; i < sensorIds.size(); i++) {
                        LocationSensorPO locationSensor = new LocationSensorPO();
                        locationSensor.setLocationId(location.getId());
                        locationSensor.setSensorPhysicalid(sensorIds.get(i));
                        HSSFCell sensorValue = hssfRow.getCell(i + 1);
                        if (sensorValue != null) {
                            SensorinfoVO sensorinfoVO = null;
                            try {
                                sensorinfoVO = appCacheHolder.loadSensorinfo(sensorIds.get(i));
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                            String valueStr = round(Double.parseDouble(sensorValue.toString()), sensorinfoVO.getSensorPrecision());
                            locationSensor.setSensorValue(valueStr);
                            locationSensor.setState(1);
                            locationSensors.add(locationSensor);
                        }

                    }
                    Date date = hssfRow.getCell(1 + sensorIds.size()).getDateCellValue();
                    Timestamp stamp = new Timestamp(date.getTime());
                    for (LocationSensorPO locationSensor : locationSensors) {
                        locationSensor.setStamp(stamp);
                    }
                    offlineDataService.insert(locationSensors);
                }
            }
        }
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s
     * @return
     */
    public String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");// 去掉多余的0
            s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
        }
        return s;
    }

    /**
     * 监测指标精度
     *
     * @param value
     * @param scale
     * @return
     */
    public String round(double value, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }

        BigDecimal b = BigDecimal.valueOf(value);
        BigDecimal one = new BigDecimal("1");
        String strvalue = b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString();
        return subZeroAndDot(strvalue);
    }
}
