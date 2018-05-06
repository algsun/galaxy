package com.microwise.blueplanet.service.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blueplanet.action.offline.ExcelUtil;
import com.microwise.blueplanet.bean.po.*;
import com.microwise.blueplanet.bean.vo.*;
import com.microwise.blueplanet.dao.*;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.util.BPHttpApiClient;
import com.microwise.blueplanet.util.ExportUtil;
import com.microwise.blueplanet.util.WindTools;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.DateTimeUtil;
import com.microwise.common.util.PagingUtil;
import com.microwise.orion.bean.Photo;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.dao.PhotoDao;
import com.microwise.orion.dao.RelicDao;
import com.microwise.uma.util.DateTypeGenerator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * 位置点 Service 实现
 *
 * @author li.jianfei
 * @date 2014-06-23
 */
@Beans.Service
@Transactional
@Blueplanet
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationDao locationDao;
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private ZoneDao zoneDao;
    @Autowired
    private PhotoDao photoDao;
    @Autowired
    private RelicDao relicDao;
    @Autowired
    private TextureThresholdDao textureThresholdDao;
    @Autowired
    private ThresholdDao thresholdDao;

    @Override
    public LocationVO findLocationById(String locationId) {
        LocationVO location = locationDao.findLocationById(locationId);
        if (location == null) return null;
        location.setSensorInfoList(locationDao.findSensorInfoList(locationId));
        location.setDevice(deviceDao.findDeviceById(location.getNodeId()));
        location.setZone(zoneDao.findZoneById(location.getZoneId()));
        return location;
    }

    @Override
    public LocationVO findLocationWithRelics(String locationId) {
        LocationVO location = findLocationById(locationId);
        if (location != null) {
            location.setRelics(locationDao.findRelics(locationId));
            // 查询文物照片信息
            for (Relic relic : location.getRelics()) {
                relic.setPhotos(photoDao.findByRelicId(relic.getId()));
            }
        }
        return location;
    }

    @Override
    public List<LocationVO> findLocationsByZoneId(String zoneId, boolean withRelic) {
        List<LocationVO> locationList = new ArrayList<LocationVO>();
        List<LocationVO> locations = locationDao.findLocationsByZoneId(zoneId);
        for (LocationVO location : locations) {
            if (!Strings.isNullOrEmpty(location.getNodeId())) {
                location.setDevice(deviceDao.findDeviceById(location.getNodeId()));
            }
            if(withRelic) {
                List<Relic> relics = locationDao.findRelics(location.getId());
                if (relics != null && relics.size() != 0) {
                    // 查询文物照片信息
                    for (Relic relic : relics) {
                        Set<Photo> photos = photoDao.findByRelicId(relic.getId());
                        Iterator<Photo> it = photos.iterator();
                        if (it.hasNext()) {
                            Photo photo = it.next();
                            String path = Sessions.createByAction().getGalaxyResourceURL() + File.separator + "orion" + File.separator + "images" + File.separator + Sessions.createByAction().currentSiteId() + File.separator + relic.getId() + File.separator + photo.getPath();
                            location.setPhoto(path);
                            break;
                        }
                    }
                } else {
                    if (!Strings.isNullOrEmpty(location.getPhoto())) {
                        String photo = Sessions.createByAction().getGalaxyResourceURL() + File.separator + "blueplanet" + File.separator + "images" + File.separator + "location" + File.separator + location.getPhoto();
                        location.setPhoto(photo);
                    }
                }
            }
            locationList.add(location);
        }
        return locationList;
    }

    @Override
    public List<LocationVO> findLocationsBySiteIdAndLocationName(String siteId, String locationName) {
        List<LocationVO> locations = locationDao.findLocationsBySiteIdAndLocationName(siteId, locationName);
        for (LocationVO location : locations) {
            location.setDevice(deviceDao.findDeviceById(location.getNodeId()));
            location.setSensorIdList(locationDao.findLocationSensorIdList(location.getId()));
        }
        return locations;
    }

    @Override
    public LocationVO findOneLocationsBySiteIdAndLocationName(String siteId, String locationName) {
        return locationDao.findOneLocationsBySiteIdAndLocationName(siteId, locationName);
    }

    @Override
    public List<LocationVO> findLocationsBySiteId(String siteId, int page, int pageSize) {
        List<LocationVO> locations = locationDao.findLocationsBySiteId(siteId, page, pageSize);
        for (LocationVO location : locations) {
            location.setSensorIdList(locationDao.findLocationSensorIdList(location.getId()));
        }
        return setZoneAndDevice(locations);
    }

    @Override
    public List<LocationVO> findLocationByNameAndZone(String locationName, String zoneId, String siteId, int page, int pageSize) {
        return setZoneAndDevice(locationDao.findLocationByNameAndZone(Strings.emptyToNull(locationName), Strings.emptyToNull(zoneId), siteId, page, pageSize));
    }

    @Override
    public List<SensorinfoVO> findSensorInfoList(String locationId) {
        return locationDao.findSensorInfoList(locationId);
    }

    @Override
    public int findLocationByNameAndZoneCount(String locationName, String zoneId, String siteId) {
        return locationDao.findLocationByNameAndZoneCount(Strings.emptyToNull(locationName), Strings.emptyToNull(zoneId), siteId);
    }

    @Override
    public int findLocationListCount(String siteId) {
        return locationDao.findLocationListCount(siteId);
    }

    private List<LocationVO> setZoneAndDevice(List<LocationVO> locations) {
        for (LocationVO location : locations) {
            location.setDevice(getDeviceById(location));
            location.setZone(getZoneById(location));
        }
        return locations;
    }

    @Override
    public String getNewLocationId(String siteId) {
        String locationId = locationDao.getMaxLocationId(siteId);
        if (StringUtils.isNotBlank(locationId)) {
            locationId = String.valueOf(Long.parseLong(locationId) + 1);
        } else {
            locationId = siteId.concat(Constants.Blueplanet.DEFAULT_FIRST_LOCATIONID);
        }
        return locationId;
    }

    @Override
    public Boolean isExistLocationName(String locationName, String siteId) {
        return locationDao.isExistLocationName(locationName, siteId);
    }

    @Override
    public Boolean isExistLocationName(String locationId, String locationName, String siteId) {
        return locationDao.isExistLocationName(locationId, locationName, siteId);
    }

    @Override
    public void addLocation(LocationPO locationPO) throws Exception {
        locationPO.setPhoto(Strings.emptyToNull(locationPO.getPhoto()));
        locationDao.addLocation(locationPO);
        locationDao.createLocationTable(locationPO.getId());
        if (StringUtils.isNotBlank(locationPO.getNodeId())) {
            locationDao.addLocationHistory(locationPO);
        }
    }

    @Override
    public void addLocationRelic(int relicId, String locationId) {
        locationDao.addLocationRelic(relicId, locationId);
    }

    @Override
    public List<Relic> findRelics(String locationId) {
        return locationDao.findRelics(locationId);
    }

    @Override
    public void deleteLocationRelic(int id) {
        locationDao.deleteLocationRelic(id);
    }

    @Override
    public void deleteLocation(String locationId) throws Exception {
        locationDao.deleteLocationStock(locationId);
        locationDao.deleteLocationSensor(locationId);
        locationDao.deleteLocationHistory(locationId);
        locationDao.deleteLocation(locationId);
        locationDao.deleteCoordinate(locationId);
        locationDao.deleteLocationRelic(locationId);
        thresholdDao.deleteThreshold(locationId, 0);
        thresholdDao.deleteThreshold(locationId, 1);
        thresholdDao.deleteThreshold(locationId, 2);
    }

    @Override
    public void dropTable(String locationId) {
        locationDao.deleteLocationTable(locationId);
    }

    @Override
    public void deployLocation(String[] locationIds, String zoneId) {
        locationDao.deployLocation(locationIds, zoneId);
        //todo @xie.deng  上传位置信息
    }

    @Override
    public void unDeployLocation(String locationId) {
        locationDao.unDeployLocation(locationId);
        locationDao.deleteCoordinate(locationId);
    }

    @Override
    public List<String> updateLocation(LocationPO location) {
        //返回需要通知中间件的设备编号
        List<String> deviceIds = new ArrayList<String>();
        locationDao.updateLocation(location);
        List<LocationHistoryVO> locationHistoryList = locationDao.findLocationHistoryList(location.getId());
        if (locationHistoryList.isEmpty()) {
            addLocationHistory(location, deviceIds);
        } else {
            editLocationHistory(location, deviceIds, locationHistoryList);
        }
        return deviceIds;
    }

    /**
     * 编辑位置点历史信息
     *
     * @param location            位置点信息
     * @param deviceIds           设备编号
     * @param locationHistoryList 位置点历史集合
     */
    private void editLocationHistory(LocationPO location, List<String> deviceIds, List<LocationHistoryVO> locationHistoryList) {
        // 最后一条历史记录
        LocationHistoryVO lastLocationHistory = locationHistoryList.get(0);
        if (Strings.isNullOrEmpty(location.getNodeId())
                && lastLocationHistory.getEndTime() == null) {// 解除绑定
            locationDao.updateLocationHistoryEndTime(location.getId(), lastLocationHistory.getNodeId());
            deviceIds.add(lastLocationHistory.getNodeId());
        } else if (!Strings.isNullOrEmpty(location.getNodeId())
                && !location.getNodeId().equals(lastLocationHistory.getNodeId())) {// 更改绑定关系
            locationDao.addLocationHistory(location);
            locationDao.updateLocationHistoryEndTime(location.getId(), lastLocationHistory.getNodeId());
            deviceIds.add(lastLocationHistory.getNodeId());
            deviceIds.add(location.getNodeId());
        } else if (lastLocationHistory.getEndTime() != null && StringUtils.isNotBlank(location.getNodeId()) && location.getNodeId().equals(lastLocationHistory.getNodeId())) {
            locationDao.addLocationHistory(location);
            deviceIds.add(location.getNodeId());
        }
    }

    /**
     * 添加位置点历史信息
     *
     * @param location  位置点信息
     * @param deviceIds 设备编号
     */
    private void addLocationHistory(LocationPO location, List<String> deviceIds) {
        if (!Strings.isNullOrEmpty(location.getNodeId())) {
            locationDao.addLocationHistory(location);
            deviceIds.add(location.getNodeId());
        }
    }

    @Override
    public List<LocationHistoryVO> findLocationHistoryList(String locationId) {
        return locationDao.findLocationHistoryList(locationId);

    }

    @Override
    public List<RecentDataVO> findRecentDataList(String locationId, Integer dataCount, Date startTime) {
        //根据位置点id获取12包数据，并且根据时间分组，排序，前台图表显示相同时间戳的数据
        List<RecentDataVO> recentDataList = null;
        if (startTime == null) {
            recentDataList = locationDao.findRecentDataList(locationId, dataCount);
        } else {
            recentDataList = locationDao.findRecentDataList(locationId, startTime);
        }
        Collections.sort(recentDataList, new Comparator<RecentDataVO>() {
            public int compare(RecentDataVO arg0, RecentDataVO arg1) {
                return arg0.getStamp().compareTo(arg1.getStamp());
            }
        });
        //无数据直接返回
        if (recentDataList.isEmpty()) {
            return recentDataList;
        }
        //组装数据
        assembRealtimeData(locationId, recentDataList);
        return recentDataList;
    }

    @Override
    public List<LocationDataVO> findLocationSensor(String locationId, List<Integer> sensorPhysicalidList) {
        return locationDao.findLocationSensor(locationId, sensorPhysicalidList);
    }

    @Override
    public LocationDataVO findLocationSensorNearBy(String locationId, int sensorId, Date stamp) {
        LocationDataVO pre = locationDao.findLocationSensorPre(locationId, sensorId, stamp);
        LocationDataVO next = locationDao.findLocationSensorNext(locationId, sensorId, stamp);

        if (pre == null && next == null) {
            return null;
        } else if (pre == null) {
            return next;
        } else if (next == null) {
            return pre;
        } else {
            long preMills = stamp.getTime() - pre.getStamp().getTime();
            long nextMills = next.getStamp().getTime() - stamp.getTime();
            if (preMills <= nextMills) {
                return pre;
            } else {
                return next;
            }
        }
    }

    /**
     * 根据站点编号查询未绑定的设备信息
     *
     * @param siteId
     * @return
     */
    public List<DeviceVO> findUnbindDevices(String siteId) {
        return locationDao.findUnbindDevices(siteId);
    }


    /**
     * 通知中间件位置点发生变化
     *
     * @param deviceId 设备编号
     */
    public void notifyLocationChanged(String deviceId) throws Exception {
        if (StringUtils.isBlank(deviceId)) {
            return;
        }
        BPHttpApiClient bpHttpApiClient = new BPHttpApiClient();
        Map<String, Object> map = bpHttpApiClient.notifyLocationChanged(deviceId);
        if (!(Boolean) map.get("success")) {
            throw new NotifyLocationChangedException("通知中间件位置点和设备关系变化时发生异常");
        }
    }

    /**
     * 通知中间件解析木卫一数据文件
     *
     * @param locationId 位置点编号
     * @throws Exception
     */
    public void notifyAnalysisDataFile(String locationId) throws Exception {
        if (StringUtils.isBlank(locationId)) {
            return;
        }
        BPHttpApiClient bpHttpApiClient = new BPHttpApiClient();
        Map<String, Object> map = bpHttpApiClient.notifyAnalysisDataFile(locationId);
        if (!(Boolean) map.get("success")) {
            throw new Exception("解析木卫一数据文件发生异常");
        }
    }

    public List<Integer> getDeviceTypes(List<DeviceVO> devices) {
        List<Integer> deviceTypes = new ArrayList<Integer>();
        for (DeviceVO device : devices) {
            if (!deviceTypes.contains(device.getNodeType())) {
                deviceTypes.add(device.getNodeType());
            }
        }
        return deviceTypes;
    }

    /**
     * 组装位置点实时数据
     *
     * @param locationId       位置点id
     * @param recentDataVOList 位置点实时数据对象列表
     * @return
     * @author liuzhu
     * @date 2016-5-5
     */
    private void assembRealtimeData(String locationId, List<RecentDataVO> recentDataVOList) {
        List<LocationDataVO> locationDataList;
        //根据位置点id、最小时间、最大时间获取位置点数据
        for (RecentDataVO recentData : recentDataVOList) {
            Date date = recentData.getStamp();
            locationDataList = locationDao.findLocationHistoryData(locationId, date);
            Map<Integer, LocationDataVO> locationDataMap = new HashMap<Integer, LocationDataVO>();
            for (LocationDataVO locationDataVO : locationDataList) {
                if (locationDataVO.getShowType() == Constants.Blueplanet.SENSORINFO_SHOWTYPE_IS_WIND_DIRECTION) {
                    locationDataVO.setSensorPhysicalValue(WindTools
                            .updateWindDirection(locationDataVO.getSensorPhysicalValue()));
                }
                locationDataMap.put(locationDataVO.getSensorPhysicalid(), locationDataVO);
            }
            recentData.setSensorInfoMap(locationDataMap);
        }
    }

    private ZoneVO getZoneById(LocationVO location) {
        return zoneDao.findZoneById(location.getZoneId());
    }

    private DeviceVO getDeviceById(LocationVO location) {
        return deviceDao.findDeviceById(location.getNodeId());
    }

    /**
     * 自定义通知中间件通讯异常
     */
    class NotifyLocationChangedException extends RuntimeException {
        public NotifyLocationChangedException(String message) {
            super(message);
        }
    }

    @Override
    public List<RecentDataVO> findRecentDataList(String locationId, Date startDate, Date endDate, int page, int pageSize) {
        List<RecentDataVO> recentDataList = locationDao.findRecentDataList(locationId, startDate, endDate, page, pageSize);
        //无数据直接返回
        if (recentDataList.isEmpty()) {
            return recentDataList;
        }

        assembRealtimeData(locationId, recentDataList);
        return recentDataList;
    }

    @Override
    public List<RecentDataVO> findHistoryDataList(String locationId, Date startDate, Date endDate, int page, int pageSize) {
        List<RecentDataVO> recentDataList = locationDao.findHistoryDataList(locationId, startDate, endDate, page, pageSize);
        //无数据直接返回
        if (recentDataList.isEmpty()) {
            return recentDataList;
        }

        //组装数据
        assembRealtimeData(locationId, recentDataList);
        return recentDataList;
    }

    @Override
    public List<RecentDataVO> findRecentDataList(String locationId, Date startDate, Date endDate) {
        List<RecentDataVO> recentDataList = locationDao.findRecentDataList(locationId, startDate, endDate);
        //无数据直接返回
        if (recentDataList.isEmpty()) {
            return recentDataList;
        }

        //组装数据
        assembRealtimeData(locationId, recentDataList);
        return recentDataList;
    }

    @Override
    public int findRecentDataListCount(String locationId, Date startDate, Date endDate) {
        return locationDao.findRecentDataListCount(locationId, startDate, endDate);
    }

    @Override
    public String getFileName(String locationId, Date startTime, Date endTime) {
        String fileName = "";
        int startYear = new DateTime(startTime).getYear();// 开始年
        int endYear = new DateTime(endTime).getYear(); // 结束年
        LocationVO location = locationDao.findLocationById(locationId);
        String locationName = location.getLocationName();
        // 判断开始年与结束年是否相等 相等直接生成excel文件名
        if (endYear == startYear) {
            fileName = ExportUtil.getFileName(locationName, locationId, startTime,
                    endTime, false);
        } else {
            List<Integer> yearList = locationDao.getExcelSum(locationId, startTime,
                    endTime);
            // 只有一年有数据
            if (yearList.size() == ExportUtil.FILR_TYPE_EXCEL) {
                // 获得有数据年的最大时间和最小时间
                Map<String, Date> dateMap = locationDao.findMaxAndMinTime(
                        locationId, new DateTime(yearList.get(0), 1, 1, 0, 0, 0)
                        .toDate(), new DateTime(yearList.get(0), 12,
                        31, 23, 59, 59).toDate(), null, null);
                fileName = ExportUtil.getFileName(locationName, locationId,
                        dateMap.get(Constants.Blueplanet.MAP_KEY_MIN_TIME),
                        dateMap.get(Constants.Blueplanet.MAP_KEY_MAX_TIME), false);
            } else {
                fileName = ExportUtil.getFileName(locationName, locationId,
                        startTime, endTime, true);
            }
        }
        return fileName;
    }

    @Override
    public String exportHistoryData(String locationId, Date startTime, Date endTime, OutputStream outputStream) throws Exception {
        LocationVO location = findLocationById(locationId);
        String locationName = location.getLocationName();
        // 获得excel 列头
        List<SensorinfoVO> sensorinfoList = location.getSensorInfoList();
        List<Integer> yearList = locationDao.getExcelSum(locationId, startTime,
                endTime);
        Workbook workbook;
        ZipOutputStream zos = null;
        // 如果只有一年有数据 生成workbook并 写入到流中
        if (yearList.size() == ExportUtil.FILR_TYPE_EXCEL) {
            workbook = createWorkbook(locationName, locationId, sensorinfoList,
                    ExportUtil.getMinTime(yearList.get(0), startTime),
                    ExportUtil.getMaxTime(yearList.get(0), endTime));
            workbook.write(outputStream);
        } else {
            // 多年有数据调用 导出zip 包的方法
            exportZip(outputStream, zos, yearList, startTime, endTime,
                    locationName, locationId, sensorinfoList);
        }
        return locationName;
    }

    @Override
    public List<AvgdataPO> findAverageAndPeakValue(String locationId, Date startDate, Date endDate) {
        return locationDao.findAverageAndPeakValue(locationId, startDate, endDate);
    }

    @Override
    public List<LocationAvgdataVO> assembleAverageAndPeakValue(String locationId, List<AvgdataPO> avgdataList) {
        //根据 位置点id获取相关监测指标数据，封装到LocationAvgdataVO中
        List<LocationAvgdataVO> locationAvgdataVOList = locationDao.findLocationSensorInfo(locationId);

        for (LocationAvgdataVO locationAvgdataVO : locationAvgdataVOList) {
            boolean hasData = false;
            List<AvgdataPO> avgdataPOList = new ArrayList<AvgdataPO>();
            for (AvgdataPO avgdataPO : avgdataList) {
                //获取相同监测指标的均峰值
                if (avgdataPO.getSensorPhysicalid() == locationAvgdataVO.getSensorPhysicalid()) {
                    avgdataPOList.add(avgdataPO);
                    hasData = true;
                }
            }
            locationAvgdataVO.setAvgdataList(avgdataPOList);
            locationAvgdataVO.setHasData(hasData);
        }
        return locationAvgdataVOList;
    }

    /**
     * 创建excel workbook
     *
     * @param locationId     位置点
     * @param sensorinfoList excel 列头数据
     * @param startTime      excel历史数据开始时间
     * @param endTime        excel 历史数据结束时间
     * @return workbook excel 工作簿
     * @author xuyuexi
     * @date 2014-7-8
     */
    private Workbook createWorkbook(String deviceName, String locationId,
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
     * 创建sheet
     *
     * @param
     * @return
     * @author xuyuexi
     * @date 2014-7-8
     */
    public void createSheet(Workbook workbook, int currentMonth,
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
                List<RecentDataVO> historyDataList = findRecentDataList(locationId,
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


    /**
     * 导出zip
     *
     * @param outputStream   输出流
     * @param zos            zip输出流
     * @param yearList       有数据的年份列表
     * @param startTime      开始时间
     * @param endTime        结束时间
     * @param locationName   位置点名称
     * @param locationId     位置点Id
     * @param sensorinfoList 监测指标表列
     * @author xuyuexi
     * @date 2014-7-8
     */
    public void exportZip(OutputStream outputStream, ZipOutputStream zos,
                          List<Integer> yearList, Date startTime, Date endTime,
                          String locationName, String locationId,
                          List<SensorinfoVO> sensorinfoList) throws IOException {
        for (Integer integer : yearList) {
            DateTime minTime = ExportUtil.getMinTime(integer, startTime);
            DateTime maxTime = ExportUtil.getMaxTime(integer, endTime);
            Workbook workbook = createWorkbook(locationName, locationId,
                    sensorinfoList, minTime, maxTime);
            if (zos == null) {
                zos = new ZipOutputStream(outputStream);
            }
            // 将workbook 写入流中
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);
            byte[] arrayByte = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            Map<String, Date> dateMap = locationDao.findMaxAndMinTime(locationId,
                    minTime.toDate(), maxTime.toDate(), null, null);
            ZipEntry ze = new ZipEntry(ExportUtil.getFileName(locationName,
                    locationId, dateMap.get(Constants.Blueplanet.MAP_KEY_MIN_TIME),
                    dateMap.get(Constants.Blueplanet.MAP_KEY_MAX_TIME), false));
            zos.putNextEntry(ze);
            zos.write(arrayByte);
        }
        zos.close();

    }

    @Override
    public void addUploadRecord(String filename) {
        DataFilePO dataFile = new DataFilePO();
        dataFile.setFilename(filename);
        dataFile.setAnalysisSign(0);
        dataFile.setUploadTime(new Date());
        if (locationDao.isExistFileRecord(filename)) {
            locationDao.updateUploadRecord(dataFile);
        } else {
            locationDao.addUploadRecord(dataFile);
        }
    }

    @Override
    public RealtimeDataVO findLocationData(String locationId) {
        RealtimeDataVO realtimeDataVO = locationDao.findLocationData(locationId);
        LocationVO locationVO = locationDao.findLocationById(locationId);
        if (realtimeDataVO != null) {
            Map<Integer, LocationDataVO> map = new HashMap<Integer, LocationDataVO>();
            List<LocationDataVO> realtimeDataList = locationDao.findLocationSensor(locationId, locationVO.getSensorIdList());
            for (LocationDataVO locationDataVO : realtimeDataList) {
                map.put(locationDataVO.getSensorPhysicalid(), locationDataVO);
            }
            realtimeDataVO.setLocationSensorInfoMap(map);
        }
        return realtimeDataVO;
    }

    @Override
    public Map<LocationPO, List<LocationDataPO>> findLocationSensorData(String zoneId, int sensorId, Date begin, Date end) {
        Map<LocationPO, List<LocationDataPO>> result = Maps.newLinkedHashMap();
        List<LocationPO> locations = locationDao.findLocations(zoneId, sensorId, begin, end);
        for (LocationPO location : locations) {
            List<LocationDataPO> locationSensorDatas = locationDao.findLocationSensorData(location.getId(), sensorId, begin, end);
            result.put(location, locationSensorDatas);
        }
        return result;
    }


    @Override
    public LocationVO findLocationByNodeId(String nodeId) {
        return locationDao.findLocationByNodeId(nodeId);
    }

    @Override
    public String findLocationIdBySensorIdAndSiteId(int sensorPhysicalId, String siteId) {
        return locationDao.findLocationIdBySensorIdAndSiteId(sensorPhysicalId, siteId);
    }

    @Override
    public String findLocationIdBySensorIdAndZoneId(int sensorPhysicalId, String zoneId) {
        return locationDao.findLocationIdbySensorIdAndZoneId(sensorPhysicalId, zoneId);
    }

    @Override
    public DeviceVO findDeviceByLocationId(String locationId) {
        return locationDao.findDeviceByLocationId(locationId);
    }

    @Override
    public List<Map<String, Object>> findWaterFlowChartData(String siteId, Date date, int sensorId, int dateType) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        // 获取查询时间段
        Date start = DateTypeGenerator.start(dateType, date);
        Date end = DateTypeGenerator.end(dateType, date);

        List<LocationVO> locations = locationDao.findLocationsBySensorIdAndSiteId(siteId, Constants.ChartConstants.SENSORINFO_WATER_FLOW);

        for (LocationVO location : locations) {
            String locationId = location.getId();
            //查询小时平均数据，展示为天数据
            List<HourAvgDataPO> hourAvgDatas = locationDao.findHourAvgDatas(locationId, sensorId, start, end);
            //查询日平均数据，展示为月数据，年数据有月数据组装
            List<AvgdataPO> avgdatas = locationDao.findAverageAndPeakValue(locationId, start, end);
            Map<String, Object> map = packageChartData(dateType, hourAvgDatas, avgdatas, date, sensorId, locationId);
            if (map == null) continue;
            map.put("name", location.getLocationName());
            map.put("dateType", dateType);
            map.put("locationId", locationId);
            mapList.add(map);
        }
        return mapList;
    }

    @Override
    public List<LocationVO> findLocationsBySensorIdAndSiteId(String siteId, int sensorId) {
        return locationDao.findLocationsBySensorIdAndSiteId(siteId, sensorId);
    }

    public void addTextureThreshold(String locationId, String[] relicIds) {
        //获取绑定文物融合后的阈值
        List<TextureThresholdVO> textureThresholds = relicManager(relicIds);
        //依次添加保存
        for (TextureThresholdVO textureThresholdVO : textureThresholds) {
            saveLocationThresholdByTextureThreshold(locationId, textureThresholdVO);
        }
    }

    public void editTextureThreshold(String locationId, String[] relicIds) {
        //删除旧的质地监测指标
        thresholdDao.deleteThreshold(locationId, 2);
        //获取位置点监测指标阈值集合
        List<ThresholdVO> locationThresholds = thresholdDao.findThresholdsByLocationId(locationId, 0);
        //获取区域监测指标阈值集合
        List<ThresholdVO> zoneThresholds = thresholdDao.findThresholdsByLocationId(locationId, 1);
        if (relicIds == null || relicIds.length < 0) {
            //如果位置点和区域的报警阈值都为空,那么添加该位置点所属区域的报警阈值
//            if ((locationThresholds.size() < 1) && (zoneThresholds.size() < 1)) {
//                LocationVO locationVO = findLocationById(locationId);
//                if (!Strings.isNullOrEmpty(locationVO.getZoneId())) {
//                    List<ZoneThresholdVO> zoneThresholdVOs = thresholdDao.findZoneThresholds(locationVO.getZoneId());
//                    if (zoneThresholdVOs.size() > 0) {
//                        for (ZoneThresholdVO zoneThresholdVO : zoneThresholdVOs) {
//                            ThresholdVO thresholdVO = new ThresholdVO();
//                            thresholdVO.setLocationId(locationId);
//                            thresholdVO.setThresholdType(1);
//                            thresholdVO.setSensorPhysicalId(zoneThresholdVO.getSensorPhysicalId());
//                            thresholdVO.setConditionType(zoneThresholdVO.getConditionType());
//                            thresholdVO.setTarget(zoneThresholdVO.getTarget());
//                            thresholdVO.setFloating(zoneThresholdVO.getFloating());
//                            thresholdDao.saveThreshold(thresholdVO);
//                        }
//                    }
//                }
//            }
        } else {
            //绑定的文物监测指标融合
            List<TextureThresholdVO> textureThresholds = relicManager(relicIds);
            for (TextureThresholdVO textureThresholdVO : textureThresholds) {
                //如果融合后的当前指标存在于位置点中，那么不做添加
                boolean flag = true;
                if (locationThresholds.size() > 0) {
                    for (ThresholdVO thresholdSensorVO : locationThresholds) {
                        if (textureThresholdVO.getSensorPhysicalId() == thresholdSensorVO.getSensorPhysicalId()) {
                            flag = false;
                            break;
                        }
                    }
                } else {
                    //如果当前位置点的位置点阈值为空，区域阈值不为空，则删除区域阈值。
                    if (zoneThresholds.size() > 0) {
                        thresholdDao.deleteThreshold(locationId, 1);
                    }
                }
                //将质地的检测指标添加到位置点检测指标，并且注明为质地类型
                if (flag) {
                    saveLocationThresholdByTextureThreshold(locationId, textureThresholdVO);
                }
            }
        }
    }

    private void saveLocationThresholdByTextureThreshold(String locationId, TextureThresholdVO textureThresholdVO) {
        //保存质地阈值到位置点
        ThresholdVO saveThresholdSensorVO = new ThresholdVO();
        saveThresholdSensorVO.setLocationId(locationId);
        saveThresholdSensorVO.setSensorPhysicalId(textureThresholdVO.getSensorPhysicalId());
        saveThresholdSensorVO.setConditionType(textureThresholdVO.getConditionType());
        saveThresholdSensorVO.setTarget(textureThresholdVO.getTarget());
        saveThresholdSensorVO.setFloating(textureThresholdVO.getFloating());
        saveThresholdSensorVO.setThresholdType(2);
        thresholdDao.saveThreshold(saveThresholdSensorVO);
    }

    private List<TextureThresholdVO> relicManager(String[] relicIds) {
        //获取第一个文物的质地。
        int textureId = relicDao.findById(Integer.parseInt(relicIds[0])).getTexture().getId();
        //获取第一个文物质地的阈值，向下融合。
        List<TextureThresholdVO> textureThresholds = textureThresholdDao.findTextureThresholds(textureId);
        if (relicIds.length > 1) {
            //第一个文物质地阈值依次与绑定的其它文物阈值融合
            for (int i = 1; i < relicIds.length; i++) {
                //获取第i个文物的阈值
                textureId = relicDao.findById(Integer.parseInt(relicIds[i])).getTexture().getId();
                List<TextureThresholdVO> thresholds = textureThresholdDao.findTextureThresholds(textureId);

                for (TextureThresholdVO textureThreshold : textureThresholds) {
                    //第一个文物阈值与第i个文物阈值监测指标进行比较，如果相同，进行融合
                    for (TextureThresholdVO thresholdVO : thresholds) {
                        if (textureThreshold.getSensorPhysicalId() == thresholdVO.getSensorPhysicalId()) {
                            //获取当前循环监测指标的达标类型
                            int conditionType = textureThreshold.getConditionType();
                            switch (conditionType) {
                                case 1:  //范围    如果是范围，那么最小值取两者最小，最大值取两者最大；
                                    float resourceMax = textureThreshold.getTarget() + textureThreshold.getFloating();
                                    float resourceMin = textureThreshold.getTarget() - textureThreshold.getFloating();
                                    float targetMax = thresholdVO.getTarget() + thresholdVO.getFloating();
                                    float targetMin = thresholdVO.getTarget() - thresholdVO.getFloating();
                                    if (resourceMax < targetMax) {
                                        resourceMax = targetMax;
                                    }
                                    if (resourceMin > targetMin) {
                                        resourceMin = targetMin;
                                    }
                                    float difference = (resourceMax - resourceMin) / 2;
                                    float target = resourceMax - difference;
                                    textureThreshold.setTarget(target);
                                    textureThreshold.setFloating(difference);
                                    break;
                                case 2: //大于      如果是大于或者大于等于，那么取两者最大
                                case 4: //大于等于
                                    if (textureThreshold.getTarget() < thresholdVO.getTarget()) {
                                        textureThreshold.setTarget(thresholdVO.getTarget());
                                    }
                                    break;
                                case 3: // 小于   如果是小于等于，那么取两者最小
                                case 5: //小于等于
                                    if (textureThreshold.getTarget() > thresholdVO.getTarget()) {
                                        textureThreshold.setTarget(thresholdVO.getTarget());
                                    }
                                    break;
                            }
                        }
                    }
                }

            }
        }
        return textureThresholds;
    }

    private Map<String, Object> packageChartData(int dateType, List<HourAvgDataPO> hourAvgDatas, List<AvgdataPO> avgdatas, Date date, int sensorId, String locationId) {

        Map<String, Object> map = new HashMap<String, Object>();
        String formatTime = "";
        if (dateType == Constants.FIND_TYPE_YEAR) {//年
            formatTime = DateFormatUtils.format(date, "yyyy年");
            map = packageYearData(date, sensorId, locationId);
        } else if (dateType == Constants.FIND_TYPE_MONTH) {//月
            formatTime = DateFormatUtils.format(date, "yyyy年MM月");
            map = packageMouthData(sensorId, avgdatas, date);
        } else if (dateType == Constants.FIND_TYPE_DAY) {//日
            formatTime = DateFormatUtils.format(date, "yyyy年MM月dd日");
            map = packageDayData(sensorId, hourAvgDatas);
        }

        String sensorName = "";

        if (sensorId == Constants.ChartConstants.SENSORINFO_WATER_FLOW) {
            sensorName = "水流量";
        } else if (sensorId == Constants.ChartConstants.SENSORINFO_PULSE) {
            sensorName = "水速";
        } else if (sensorId == Constants.ChartConstants.SENSORINFO_WATER_LEVEL) {
            sensorName = "水位";
        }
        for (AvgdataPO avgdata : avgdatas) {
            if (sensorId == avgdata.getSensorPhysicalid()) {
                map.put("avgValue", avgdata.getAvgValue());
                map.put("waveValue", avgdata.getWaveValue());
            }
        }

        map.put("title", formatTime + sensorName);
        return map;
    }

    private Map<String, Object> packageDayData(int sensorId, List<HourAvgDataPO> hourAvgDatas) {
        if (hourAvgDatas.size() == 0) return new HashMap<String, Object>();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String[] values = new String[24];
        for (HourAvgDataPO hourAvgData : hourAvgDatas) {
            if (hourAvgData.getSensorPhysicalid() == sensorId) {
                Date msDate = hourAvgData.getMsDatetime();
                String day = DateFormatUtils.format(msDate, "HH");
                Integer d = Integer.parseInt(day);
                String value = hourAvgData.getAvgValue() + "";
                values[d] = value;
            }
        }
        for (int i = 0; i < 24; i++) {
            String val = values[i];
            if (Strings.isNullOrEmpty(val)) {
                values[i] = "0";
            }
        }
        returnMap.put("data", values);
        return returnMap;
    }

    private Map<String, Object> packageMouthData(int sensorId, List<AvgdataPO> avgdatas, Date date) {
        if (avgdatas.size() == 0) return new HashMap<String, Object>();
        Map<String, Object> returnMap = new HashMap<String, Object>();

        //获取时间参数当月的天数
        int days = new DateTime(date.getTime()).dayOfMonth().withMaximumValue().getDayOfMonth();

        String[] values = new String[days];
        for (AvgdataPO avgdata : avgdatas) {
            if (avgdata.getSensorPhysicalid() == sensorId) {
                Date msDate = avgdata.getMsDate();
                String mouth = DateFormatUtils.format(msDate, "dd");
                Integer m = Integer.parseInt(mouth);
                String value = avgdata.getAvgValue() + "";
                values[m - 1] = value;
            }
        }
        for (int i = 0; i < days; i++) {
            String val = values[i];
            if (Strings.isNullOrEmpty(val)) {
                values[i] = "0";
            }
        }
        returnMap.put("data", values);
        returnMap.put("days", days);
        return returnMap;
    }

    private Map<String, Object> packageYearData(Date date, int sensorId, String locationId) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String year = DateFormatUtils.format(date, "yyyy");
        List<YearAvgDataVO> mapList = locationDao.findYearAvgData(year, sensorId, locationId);
        if (mapList.size() == 0) return new HashMap<String, Object>();
        String[] values = new String[12];
        for (YearAvgDataVO YearAvgData : mapList) {
            int m = Integer.parseInt(YearAvgData.getMouth());
            String value = YearAvgData.getVal() + "";
            values[m - 1] = value;
        }
        for (int i = 0; i < 12; i++) {
            String val = values[i];
            if (Strings.isNullOrEmpty(val)) {
                values[i] = "0";
            }
        }
        returnMap.put("data", values);
        return returnMap;
    }

    @Override
    public List<LocationVO> findLocationsBySiteId(String siteId) {
        List<LocationVO> locationVOs = locationDao.findLocationsBySiteId(siteId);
        for (LocationVO location : locationVOs) {
            location.setSensorIdList(locationDao.findLocationSensorIdList(location.getId()));
        }
        return setZoneAndDevice(locationVOs);
    }
}
