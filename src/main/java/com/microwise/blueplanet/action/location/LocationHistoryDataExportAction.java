package com.microwise.blueplanet.action.location;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.io.Closeables;
import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 历史数据导出
 *
 * @author xuyuexi
 * @date 14-7-8
 */
@Beans.Action
@Blueplanet
public class LocationHistoryDataExportAction extends BlueplanetLoggerAction {
    public static final Logger logger = LoggerFactory.getLogger(LocationHistoryDataExportAction.class);

    @Autowired
    private LocationService locationService;

    //input
    /**
     * 设备id
     */
    private String locationId;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 查询历史数据数目
     */
    @Route("/blueplanet/location/{locationId}/history-data-count.json")
    public String count() {
        try {
            int count = locationService.findRecentDataListCount(locationId, startTime, endTime);
            Map<String, Object> json = new HashMap<String, Object>();
            json.put("count", count);
            ActionContext.getContext().put("json", json);
            log("位置点", "历史数据导出");
        } catch (Exception e) {
            logger.error("位置点历史数据导出", e);
        }
        return Results.json().root("json").done();
    }

    /**
     * 历史数据导出
     *
     * @throws java.io.IOException
     */
    public String export() throws IOException {
        String encoding = "ISO8859-1";  // 文件名编码，要不然乱码
        HttpServletResponse res = null;
        try {
            String fileName = locationService.getFileName(locationId, startTime, endTime);
            fileName = new String(fileName.getBytes(), encoding);
            res = ServletActionContext.getResponse();
            res.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"");
            res.setContentType("application/octet-stream;charset=" + encoding);
        } catch (UnsupportedEncodingException e) {
            logger.error("历史数据导出", e);
        }
        OutputStream out = res.getOutputStream();
        try {
            locationService.exportHistoryData(locationId, startTime, endTime, out);
        } catch (SocketException ie) {
            logger.error("历史数据导出时，客户端操作异常", ie);
        } catch (IOException se) {
            logger.error("历史数据导出时，客户端操作异常", se);
        } catch (Exception e) {
            logger.error("历史数据导出", e);
        }finally {
            out.flush();
            out.close();
        }
//        Closeables.close(out, true);

        return Action.NONE;
    }


    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
