package com.microwise.proxima.action.opticsDVPlace;

import com.google.common.base.Strings;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.proxima.bean.OpticsDVPlaceBean;
import com.microwise.proxima.bean.Zone;
import com.microwise.proxima.service.OpticsDVPlaceService;
import com.microwise.proxima.service.ZoneService;
import com.microwise.proxima.sys.Proxima;
import com.microwise.proxima.sys.ProximaLoggerAction;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 查询光学摄像机
 *
 * @author li.jianfei
 * @date 2013-3-27
 * @check @wang yunlong 2013-3-29 #2383
 */
@Beans.Action
@Proxima
public class QueryOpticsDVPlaceAction extends ProximaLoggerAction {

//    private static final Logger log = LoggerFactory.getLogger(QueryOpticsDVPlaceAction.class);
//    /**
//     * 光学摄像机service服务层
//     */
//    @Autowired
//    private OpticsDVPlaceService opticsDVPlaceService;
//
//    /**
//     * 监测点信息service
//     */
//    @Autowired
//    private ZoneService zoneService;
//
//    // input
//    /**
//     * zoneId 区域id
//     */
//    private String zoneId;
//
//    /**
//     * 摄像机id
//     */
//    private String dvPlaceId;
//
//    /**
//     * io端口
//     */
//    private int ioPort;
//
//    // output
//    /**
//     * 有摄像机点位的区域列表
//     */
//    private List<Zone> zoneList;
//
//    /**
//     * 用于前台展示的List集合
//     */
//    private List<OpticsDVPlaceBean> dvPlaceList;
//
//    /**
//     * IO 端口是否已用
//     * true:已使用
//     * false:未使用
//     */
//    private boolean used;
//
//
//    // Input && Output
//    /**
//     * 当前页
//     */
//    private int index;
//
//    /**
//     * 总页数
//     */
//    private int pageCount;
//
//    /**
//     * 获取光学摄像机列表
//     *
//     * @author li.jianfei
//     * @date 2013-3-27
//     */
//    public String execute() {
//
//        try {
//            // 获取当前站点id
//            String siteId = Sessions.createByAction().currentLogicGroup().getSite().getSiteId();
//
//            // 根据站点id 获取当前站点下有摄像机点位的区域
//            zoneList = zoneService.findHasOptics(siteId);
//
//            // 选择区域后按区域查找摄像机点位列表，否则查询全部
//            pageCount = calcTotalPage(siteId, zoneId);
//            index = (index == 0 ? 1 : index);
//            if (Strings.isNullOrEmpty(zoneId)) {
//                dvPlaceList = opticsDVPlaceService.findAll(siteId, index, Constants.SIZE_PER_PAGE);
//            } else {
//                dvPlaceList = opticsDVPlaceService.findByZoneId(zoneId, index, Constants.SIZE_PER_PAGE);
//            }
//            ActionMessage.createByAction().consume();
//            log("摄像机管理", "摄像机列表");
//        } catch (Exception e) {
//            log.error("", e);
//        }
//        return EventAction.SUCCESS;
//    }
//
//    /**
//     * 计算总页数
//     *
//     * @param siteId 站点id
//     * @param zoneId 区域id
//     * @return 总页数
//     */
//    public int calcTotalPage(String siteId, String zoneId) {
//        int totalPage = 0;
//        int recordCount;
//        try {
//            if (Strings.isNullOrEmpty(zoneId)) {
//                recordCount = opticsDVPlaceService.findAllCount(siteId);
//            } else {
//                recordCount = opticsDVPlaceService.findByZoneIdCount(zoneId);
//            }
//            totalPage = recordCount / Constants.SIZE_PER_PAGE;
//            if (recordCount % Constants.SIZE_PER_PAGE != 0) {
//                totalPage = totalPage + 1;
//            }
//        } catch (Exception e) {
//            log.error("", e);
//        }
//        return totalPage;
//    }
//
//    public String getZoneId() {
//        return zoneId;
//    }
//
//    public void setZoneId(String zoneId) {
//        this.zoneId = zoneId;
//    }
//
//    public String getDvPlaceId() {
//        return dvPlaceId;
//    }
//
//    public void setDvPlaceId(String dvPlaceId) {
//        this.dvPlaceId = dvPlaceId;
//    }
//
//    public int getIoPort() {
//        return ioPort;
//    }
//
//    public void setIoPort(int ioPort) {
//        this.ioPort = ioPort;
//    }
//
//    public boolean isUsed() {
//        return used;
//    }
//
//    public void setUsed(boolean used) {
//        this.used = used;
//    }
//
//    public List<Zone> getZoneList() {
//        return zoneList;
//    }
//
//    public void setZoneList(List<Zone> zoneList) {
//        this.zoneList = zoneList;
//    }
//
//    public List<OpticsDVPlaceBean> getDvPlaceList() {
//        return dvPlaceList;
//    }
//
//    public void setDvPlaceList(List<OpticsDVPlaceBean> dvPlaceList) {
//        this.dvPlaceList = dvPlaceList;
//    }
//
//    public int getIndex() {
//        return index;
//    }
//
//    public void setIndex(int index) {
//        this.index = index;
//    }
//
//    public int getPageCount() {
//        return pageCount;
//    }
//
//    public void setPageCount(int pageCount) {
//        this.pageCount = pageCount;
//    }
}
