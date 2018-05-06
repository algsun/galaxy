package com.microwise.proxima.action.dvPlace;

import com.google.common.base.Strings;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.proxima.bean.DVPlaceBean;
import com.microwise.proxima.bean.Zone;
import com.microwise.proxima.service.DVPlaceService;
import com.microwise.proxima.service.ZoneService;
import com.microwise.proxima.sys.Proxima;
import com.microwise.proxima.sys.ProximaLoggerAction;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 查询摄像机
 *
 * @author xu.yuexi
 * @date 2014-4-3
 */
@Beans.Action
@Proxima
public class QueryDVPlaceAction extends ProximaLoggerAction {

    private static final Logger log = LoggerFactory.getLogger(QueryDVPlaceAction.class);
    /**
     * 光学摄像机service服务层
     */
    @Autowired
    private DVPlaceService dvPlaceService;

    /**
     * 监测点信息service
     */
    @Autowired
    private ZoneService zoneService;

    // input
    /**
     * zoneId 区域id
     */
    private String zoneId;

    /**
     * 摄像机id
     */
    private String dvPlaceId;

    /**
     * io端口
     */
    private int ioPort;

    // output
    /**
     * 有摄像机点位的区域列表
     */
    private List<Zone> zoneList;

    /**
     * 用于前台展示的List集合
     */
    private List<DVPlaceBean> dvPlaceList;

    /**
     * IO 端口是否已用
     * true:已使用
     * false:未使用
     */
    private boolean used;


    // Input && Output
    /**
     * 当前页
     */
    private int index;

    /**
     * 总页数
     */
    private int pageCount;

    /**
     * 获取摄像机列表
     *
     * @author xu.yuexi
     * @date 2014-4-3
     */
    public String execute() {

        try {
            // 获取当前站点id
            String siteId = Sessions.createByAction().currentSiteId();

            // 根据站点id 获取当前站点下有摄像机点位的区域
            zoneList = zoneService.findHasOptics(siteId);

            // 选择区域后按区域查找摄像机点位列表，否则查询全部
            pageCount = calcTotalPage(siteId, zoneId);
            index = (index == 0 ? 1 : index);
            if (Strings.isNullOrEmpty(zoneId)) {
                dvPlaceList = dvPlaceService.findAll(siteId, index, Constants.SIZE_PER_PAGE);
            } else {
                dvPlaceList = dvPlaceService.findDvPlacesByZoneId(zoneId, index, Constants.SIZE_PER_PAGE);
            }
            ActionMessage.createByAction().consume();
            log("摄像机管理", "摄像机列表");
            return Action.SUCCESS;
        } catch (Exception e) {
            log.error("摄像机管理", e);
            return Action.ERROR;
        }
    }

    /**
     * 计算总页数
     *
     * @param siteId 站点id
     * @param zoneId 区域id
     * @return 总页数
     */
    private int calcTotalPage(String siteId, String zoneId) {
        int totalPage = 0;
        int recordCount;
        try {
            if (Strings.isNullOrEmpty(zoneId)) {
                recordCount = dvPlaceService.findAll(siteId).size();
            } else {
                recordCount = dvPlaceService.findDvPlacesByZoneId(zoneId).size();
            }
            totalPage = recordCount / Constants.SIZE_PER_PAGE;
            if (recordCount % Constants.SIZE_PER_PAGE != 0) {
                totalPage = totalPage + 1;
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return totalPage;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getDvPlaceId() {
        return dvPlaceId;
    }

    public void setDvPlaceId(String dvPlaceId) {
        this.dvPlaceId = dvPlaceId;
    }

    public int getIoPort() {
        return ioPort;
    }

    public void setIoPort(int ioPort) {
        this.ioPort = ioPort;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public List<Zone> getZoneList() {
        return zoneList;
    }

    public void setZoneList(List<Zone> zoneList) {
        this.zoneList = zoneList;
    }

    public List<DVPlaceBean> getDvPlaceList() {
        return dvPlaceList;
    }

    public void setDvPlaceList(List<DVPlaceBean> dvPlaceList) {
        this.dvPlaceList = dvPlaceList;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
