package com.microwise.proxima.action.opticsDVPlace;

import com.microwise.blackhole.sys.MessageActionUtil;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.GalaxyIdUtil;
import com.microwise.proxima.bean.FTPProfile;
import com.microwise.proxima.bean.OpticsDVPlaceBean;
import com.microwise.proxima.bean.Zone;
import com.microwise.proxima.service.FTPProfileService;
import com.microwise.proxima.service.OpticsDVPlaceService;
import com.microwise.proxima.service.SystemConfigService;
import com.microwise.proxima.service.ZoneService;
import com.microwise.proxima.sys.Proxima;
import com.microwise.proxima.sys.ProximaLoggerAction;
import com.microwise.proxima.util.DaemonAPIClients;
import com.microwise.proxima.util.DaemonInvokeException;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 添加光学摄像机点位
 *
 * @author li.jianfei
 * @date 2013-3-27
 * @check @wang yunlong 2013-3-29 #2385
 */
@Beans.Action
@Proxima
public class AddOpticsDVPlaceAction extends ProximaLoggerAction implements ServletRequestAware {

    private static final Logger log = LoggerFactory.getLogger(QueryOpticsDVPlaceAction.class);
    /**
     * 注入光学摄像机服务层
     */
    @Autowired
    private OpticsDVPlaceService opticsDVPlaceService;

    /**
     * 区域服务层对象
     */
    @Autowired
    private ZoneService zoneService;

    /**
     * 系统配置 Service
     */
    @Autowired
    private SystemConfigService systemConfigService;


    /**
     * FTP 服务层对象
     */
    @Autowired
    private FTPProfileService ftpService;

    private HttpServletRequest request;

    /**
     * 用于前后台传参的光学dv对象
     */
    private OpticsDVPlaceBean dvPlace;

    /**
     * 每天周期变量
     */
    private String everydayPeriod;

    /**
     * 每天时间点变量
     */
    private String everydayPoint;

    /**
     * 7天周期变量
     */
    private String sevendayPeriod;

    /**
     * 7天时间点变量
     */
    private String sevendayPoint;

    /**
     * 单选类型 day 每天周期,point 每天时间点,7day 7天周期,7day_point7天时间点
     */
    private String radioType;

    /**
     * 监测点列表
     */
    private List<Zone> zoneList;

    /**
     * FTP 列表
     */
    private List<FTPProfile> ftpList;

    /**
     * 跳转到添加页面
     *
     * @author li.jianfei
     * @date 2013-3-27
     */
    public String toAddOpticsDVPlace() {
        // 获取当前站点id
        Sessions sessions = new Sessions(ActionContext.getContext());
        String siteId = sessions.currentLogicGroup().getSite().getSiteId();

        // 查询当前站点下的所有区域
        zoneList = zoneService.find(siteId);

        // 查询当前站点下的所有 FTP
        ftpList = ftpService.findAll(siteId);

        dvPlace = new OpticsDVPlaceBean();
        return Action.SUCCESS;
    }

    /**
     * 添加光学摄像机
     *
     * @author li.jianfei
     * @date 2013-3-27
     */
    public String addOpticsDVPlace() {
        String message;
        try {
            // 获取当前站点
            String siteId = Sessions.createByAction().currentLogicGroup().getSite().getSiteId();

            // 获取点位编码索引
            int index = systemConfigService.findDvPlaceCodeIndex();
            // 设置摄像机点位默认信息
            dvPlace.setId(GalaxyIdUtil.get64UUID());
            dvPlace.setCreateTime(new Date());
            dvPlace.setPlaceCode(siteId + index);
            dvPlace.setEnable(true);

            // 保存拍照计划和IO模块控制参数
            opticsDVPlaceService.save(dvPlace, everydayPeriod, everydayPoint,
                    sevendayPeriod, sevendayPoint, radioType);

            // 更新点位编码索引
            systemConfigService.updateDvPlaceCodeIndex(index + 1);

            // 守望者
            DaemonAPIClients.getClient().addDVPlace(dvPlace.getId());

            message = "添加成功，请对摄像机进行设置。";
            log("摄像机管理","添加摄像机");
            MessageActionUtil.success(message);

        } catch (DaemonInvokeException e) {
            MessageActionUtil.fail("系统出错，但添加成功");
            log.error("添加摄像机点位", e);
        } catch (Exception e) {
            message = "添加失败";
            MessageActionUtil.fail(message);
            log.error("添加摄像机点位", e);
        }
        return Action.SUCCESS;
    }

    /**
     * 重新注入request
     */
    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    public OpticsDVPlaceBean getDvPlace() {
        return dvPlace;
    }

    public void setDvPlace(OpticsDVPlaceBean dvPlace) {
        this.dvPlace = dvPlace;
    }

    public OpticsDVPlaceService getOpticsDVPlaceService() {
        return opticsDVPlaceService;
    }

    public void setOpticsDVPlaceService(
            OpticsDVPlaceService opticsDVPlaceService) {
        this.opticsDVPlaceService = opticsDVPlaceService;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String getEverydayPeriod() {
        return everydayPeriod;
    }

    public void setEverydayPeriod(String everydayPeriod) {
        this.everydayPeriod = everydayPeriod;
    }

    public String getEverydayPoint() {
        return everydayPoint;
    }

    public void setEverydayPoint(String everydayPoint) {
        this.everydayPoint = everydayPoint;
    }

    public String getSevendayPeriod() {
        return sevendayPeriod;
    }

    public void setSevendayPeriod(String sevendayPeriod) {
        this.sevendayPeriod = sevendayPeriod;
    }

    public String getSevendayPoint() {
        return sevendayPoint;
    }

    public void setSevendayPoint(String sevendayPoint) {
        this.sevendayPoint = sevendayPoint;
    }

    public String getRadioType() {
        return radioType;
    }

    public void setRadioType(String radioType) {
        this.radioType = radioType;
    }

    public List<Zone> getZoneList() {
        return zoneList;
    }

    public void setZoneList(List<Zone> zoneList) {
        this.zoneList = zoneList;
    }

    public List<FTPProfile> getFtpList() {
        return ftpList;
    }

    public void setFtpList(List<FTPProfile> ftpList) {
        this.ftpList = ftpList;
    }
}
