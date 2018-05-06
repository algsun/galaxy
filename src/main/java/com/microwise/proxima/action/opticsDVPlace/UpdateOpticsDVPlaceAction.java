package com.microwise.proxima.action.opticsDVPlace;

import com.microwise.blackhole.sys.MessageActionUtil;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.proxima.bean.*;
import com.microwise.proxima.service.FTPProfileService;
import com.microwise.proxima.service.OpticsDVPlaceService;
import com.microwise.proxima.service.PhotographScheduleService;
import com.microwise.proxima.service.ZoneService;
import com.microwise.proxima.sys.Proxima;
import com.microwise.proxima.sys.ProximaLoggerAction;
import com.microwise.proxima.util.DaemonAPIClients;
import com.microwise.proxima.util.DaemonInvokeException;
import com.opensymphony.xwork2.Action;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 更新光学摄像机点位
 *
 * @author li.jianfei
 * @date 2013-3-27
 * @check @wang yunlong 2013-3-29 #2400
 */
@Beans.Action
@Proxima
public class UpdateOpticsDVPlaceAction extends ProximaLoggerAction implements ServletRequestAware {

    private static final Logger log = LoggerFactory.getLogger(UpdateOpticsDVPlaceAction.class);
    /**
     * 光学摄像机服务层对象
     */
    @Autowired
    private OpticsDVPlaceService opticsDVPlaceService;

    /**
     * 区域服务层对象
     */
    @Autowired
    private ZoneService zoneService;

    /**
     * FTP 服务层对象
     */
    @Autowired
    private FTPProfileService ftpService;

    /**
     * 拍照计划服务层对象
     */
    @Autowired
    private PhotographScheduleService photographScheduleService;

    private HttpServletRequest request;

    /**
     * 摄像机点位信息对象
     */
    private OpticsDVPlaceBean dvPlace;

    /**
     * 拍照计划数据集
     */
    private List<PhotographScheduleBean> psbList;

    /**
     * 是否是外部控制
     */
    private String isLightOn;

    /**
     * 是否是每天还是星期
     */
    private String dayOfWeek;

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
     * 区域列表
     */
    private List<Zone> zoneList;

    /**
     * FTP 列表
     */
    private List<FTPProfile> ftpList;

    /**
     * 跳转到摄像机点位修改页面
     *
     * @author li.jianfei
     * @date 2013-3-27
     */
    public String toUpdateOpticsDVPlace() {

        try {
            // 根据摄像机点位id 查询摄像机点位信息
            String dvPlaceId = request.getParameter("id");
            dvPlace = opticsDVPlaceService.findById(dvPlaceId);

            // 获取当前站点id
            String siteId = Sessions.createByAction().currentLogicGroup().getSite().getSiteId();

            // 获取当前站点下的所有区域
            zoneList = zoneService.find(siteId);

            // 获取当前站点下的所有FTP
            ftpList = ftpService.findAll(siteId);

            // 获取当前站点的拍照计划
            psbList = photographScheduleService.findAllOfDVPlace(dvPlaceId);

            for (PhotographScheduleBean psBean : psbList) {

                if (psBean instanceof PhotographPointScheduleBean) {
                    DayType dayType = psBean.getDayOfWeek();
                    if (dayType.equals(DayType.ALL)) {
                        dayOfWeek = "point";// 每天时间点
                    } else {
                        dayOfWeek = "7day_point";// 7天时间点
                    }
                } else if (psBean instanceof PhotographIntervalScheduleBean) {
                    DayType dayType = psBean.getDayOfWeek();
                    if (dayType.equals(DayType.ALL)) {
                        dayOfWeek = "day";// 每天周期
                    } else {
                        dayOfWeek = "7day";// 7天周期
                    }
                }

            }
        } catch (Exception e) {
            log.error("", e);
        }
        return Action.SUCCESS;
    }

    /**
     * 修改摄像机点位信息
     *
     * @author li.jianfei
     * @date 2013-3-27
     */
    public String updateOpticsDVPlace() {
        try {
            OpticsDVPlaceBean dv = opticsDVPlaceService.findById(dvPlace.getId());
            dv.setPlaceName(dvPlace.getPlaceName());
            dv.setFtp(dvPlace.getFtp());
            dv.setRemark(dvPlace.getRemark());
            dv.setDvIp(dvPlace.getDvIp());
            dv.setIoVersion(dvPlace.getIoVersion());
            dv.setIoIp(dvPlace.getIoIp());
            dv.setIoPort(dvPlace.getIoPort());
            dv.setLightOffTime(dvPlace.getLightOffTime());
            dv.setLightOnTime(dvPlace.getLightOnTime());
            dv.setIoOn(dvPlace.isIoOn());
            dv.setIoDvRoute(dvPlace.getIoDvRoute());
            dv.setLightOn(dvPlace.isLightOn());
            dv.setIoLightRoute(dvPlace.getIoLightRoute());
            dv.setPhotographTime(dvPlace.getPhotographTime());
            dv.setZone(dvPlace.getZone());
            dv.setDvPort(dvPlace.getDvPort());

            // 保存拍照计划和IO模块控制参数
            opticsDVPlaceService.update(dv, everydayPeriod, everydayPoint,
                    sevendayPeriod, sevendayPoint, radioType);
            // 通知 守望者后台更改 图片扫描计划和拍照计划
            DaemonAPIClients.getClient().dvPlaceFtpChanged(dv.getId());
            DaemonAPIClients.getClient().photographScheduleChanged(dv.getId());

            MessageActionUtil.success("编辑成功，请对摄像机进行设置");
            log("摄像机管理", "编辑摄像机");
        } catch (DaemonInvokeException ex) {
            log.error("编辑摄像机点位", ex);
            MessageActionUtil.fail("系统出错，但编辑成功");
        } catch (Exception ex) {
            log.error("编辑摄像机点位", ex);
            MessageActionUtil.fail("编辑失败");
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

    public List<PhotographScheduleBean> getPsbList() {
        return psbList;
    }

    public void setPsbList(List<PhotographScheduleBean> psbList) {
        this.psbList = psbList;
    }

    public String getIsLightOn() {
        return isLightOn;
    }

    public void setIsLightOn(String isLightOn) {
        this.isLightOn = isLightOn;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
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

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String getLightOn() {
        return isLightOn;
    }

    public void setLightOn(String lightOn) {
        isLightOn = lightOn;
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
