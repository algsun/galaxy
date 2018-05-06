package com.microwise.api;

import com.microwise.api.bean.Greeting;
import com.microwise.api.bean.HelloMessage;
import com.microwise.blackhole.bean.User;
import com.microwise.blueplanet.bean.vo.LocationVO;
import com.microwise.blueplanet.bean.vo.SensorinfoVO;
import com.microwise.blueplanet.bean.vo.SiteVO;
import com.microwise.blueplanet.bean.vo.ZoneVO;
import com.microwise.blueplanet.dao.LocationDao;
import com.microwise.blueplanet.dao.SiteDao;
import com.microwise.blueplanet.dao.ZoneDao;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.service.SiteService;
import com.microwise.blueplanet.service.ZoneService;
import com.microwise.blueplanet.util.ExportUtil;
import com.microwise.common.sys.Constants;
import com.microwise.common.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.support.incrementer.SybaseAnywhereMaxValueIncrementer;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.util.Date;
import java.util.List;

/**
 * Created by lijianfei on 2018/1/3.
 *
 * @author li.jianfei
 * @since 2018/1/3
 */
@Controller
public class WebSocketController {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);


    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private SiteService siteService;

    @Autowired
    private ZoneDao zoneDao;

    @Autowired
    private LocationDao locationDao;


    @Autowired
    private SiteDao siteDao;


    @RequestMapping("/websocket")
    public String websocket() {
        return "api/websocket";
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
//        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + message.getName() + "!");
    }


    @RequestMapping("/history-data-export")
    public void exportData(String siteId, String zoneId, String locationId, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
                           @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime, HttpServletResponse res, HttpSession session) throws IOException {
        String encoding = "ISO8859-1";  // 文件名编码，要不然乱码
        try {
            String fileName;
            if (!"全部".equals(locationId)) {
                fileName = locationService.getFileName(locationId, startTime, endTime);
            } else if (!"全部".equals(zoneId)) {
                fileName = zoneService.getFileName(zoneId, startTime, endTime);
            } else {
                fileName = siteService.getFileName(siteId, startTime, endTime);
            }
            fileName = new String(fileName.getBytes(), encoding);
            res.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"");
            res.setContentType("application/octet-stream;charset=" + encoding);
        } catch (UnsupportedEncodingException e) {
            logger.error("历史数据导出", e);
        }
        OutputStream out = res.getOutputStream();
        String userId = ((User) session.getAttribute(Constants.Session.USER_OF_SESSION)).getId() + "";
        try {
            if (!"全部".equals(locationId)) {
                String locationName = locationService.exportHistoryData(locationId, startTime, endTime, out);
                this.simpMessagingTemplate.convertAndSendToUser(userId, "/exportProgress", locationName);
                this.simpMessagingTemplate.convertAndSendToUser(userId, "/exportProgress", "complete");
            } else if (!"全部".equals(zoneId)) {
                ZoneVO zone = zoneDao.findZoneById(zoneId);
                List<LocationVO> locationVOList = locationDao.findLocationsByZoneId(zoneId);
                File tempDir;
                String tomcatPath = System.getProperty("catalina.home");
                String minTime = DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD, startTime);
                String maxTime = DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD, endTime);
                String suffix = "(" + minTime + "至" + maxTime + ")";
                String rootDir = tomcatPath + File.separator + zone.getZoneName() + suffix;
                for (LocationVO locationVO : locationVOList) {
                    int count = locationService.findRecentDataListCount(locationVO.getId(), startTime, endTime);
                    if (count < 1) {
                        continue;
                    }
                    LocationVO location = locationService.findLocationById(locationVO.getId());
                    // 获得excel 列头
                    List<SensorinfoVO> sensorinfoList = location.getSensorInfoList();
                    List<Integer> yearList = locationDao.getExcelSum(location.getId(), startTime,
                            endTime);
                    String fileName = tomcatPath + File.separator + zone.getZoneName() + suffix
                            + File.separator + location.getLocationName() + suffix;
                    fileName = new String (fileName.getBytes("UTF-8"), System.getProperty("file.encoding"));
                    tempDir = new File(fileName);
                    if (!tempDir.exists()) {
                        tempDir.mkdirs();
                    }
                    this.simpMessagingTemplate.convertAndSendToUser(userId, "/exportProgress", location.getLocationName());
                    ExportUtil.exportExcel(location, tempDir, yearList, startTime, endTime, sensorinfoList);
                }
                this.simpMessagingTemplate.convertAndSendToUser(userId, "/exportProgress", "complete");
                ExportUtil.toZip(rootDir, out, true);
                ExportUtil.deleteDir(new File(rootDir));
            } else {
                SiteVO site = siteDao.findSiteById(siteId);
                List<ZoneVO> zoneVOList = zoneDao.findZonesBySiteId(siteId);
                String tomcatPath = System.getProperty("catalina.home");
                String minTime = DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD, startTime);
                String maxTime = DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD, endTime);
                String suffix = "(" + minTime + "至" + maxTime + ")";
                String rootDir = tomcatPath + File.separator + site.getSiteName() + suffix;
                for (ZoneVO zone : zoneVOList) {
                    List<LocationVO> locationVOList = locationService.findLocationsByZoneId(zone.getZoneId(), false);
                    for (LocationVO locationVO : locationVOList) {
                        int count = locationService.findRecentDataListCount(locationVO.getId(), startTime, endTime);
                        if (count < 1) {
                            continue;
                        }
                        LocationVO location = locationService.findLocationById(locationVO.getId());
                        List<SensorinfoVO> sensorinfoList = location.getSensorInfoList();
                        List<Integer> yearList = locationDao.getExcelSum(location.getId(), startTime,
                                endTime);
                        String fileName = tomcatPath + File.separator + site.getSiteName() + suffix
                                + File.separator + zone.getZoneName() + suffix
                                + File.separator + location.getLocationName() + suffix;
                        fileName = new String (fileName.getBytes("UTF-8"),System.getProperty("file.encoding"));
                        File tempDir = new File(fileName);
                        if (!tempDir.exists()) {
                            tempDir.mkdirs();
                        }
                        this.simpMessagingTemplate.convertAndSendToUser(userId, "/exportProgress", location.getLocationName());
                        ExportUtil.exportExcel(location, tempDir, yearList, startTime, endTime, sensorinfoList);
                    }
                }
                this.simpMessagingTemplate.convertAndSendToUser(userId, "/exportProgress", "complete");
                ExportUtil.toZip(rootDir, out, true);
                ExportUtil.deleteDir(new File(rootDir));
            }
        } catch (SocketException ie) {
            logger.error("历史数据导出时，客户端操作异常", ie);
        } catch (IOException se) {
            logger.error("历史数据导出时，客户端操作异常", se);
        } catch (Exception e) {
            logger.error("历史数据导出", e);
        } finally {
            out.flush();
            out.close();
        }
    }
}
