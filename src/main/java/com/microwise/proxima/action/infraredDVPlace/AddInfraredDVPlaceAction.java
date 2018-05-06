package com.microwise.proxima.action.infraredDVPlace;

import com.microwise.blackhole.sys.MessageActionUtil;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.GalaxyIdUtil;
import com.microwise.proxima.bean.FTPProfile;
import com.microwise.proxima.bean.InfraredDVPlaceBean;
import com.microwise.proxima.bean.Zone;
import com.microwise.proxima.service.FTPProfileService;
import com.microwise.proxima.service.InfraredDVPlaceService;
import com.microwise.proxima.service.SystemConfigService;
import com.microwise.proxima.service.ZoneService;
import com.microwise.proxima.sys.Proxima;
import com.microwise.proxima.sys.ProximaLoggerAction;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 * 添加红外热像仪
 * </pre>
 *
 * @author zhangpeng
 * @date 2012-7-10
 * @check zhang.licong 2012-07-14
 * @check guo.tian li.jianfei 2012-09-18
 */
@Beans.Action
@Proxima
public class AddInfraredDVPlaceAction extends ProximaLoggerAction {
    private static final Logger log = LoggerFactory.getLogger(AddInfraredDVPlaceAction.class);


    /**
     * 红外热像仪点位信息对象
     */
    private InfraredDVPlaceBean dvPlace;

    /**
     * 区域列表
     */
    private List<Zone> zoneList;

    /**
     * FTP 列表
     */
    private List<FTPProfile> ftpList;
    /**
     * 红外热像仪服务层对象
     */
    @Autowired
    private InfraredDVPlaceService infraredDVPlaceService;
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

    /**
     * 进入添加界面
     */
    public String toAddInfraredDVPlace() {
        try {
            String siteId = Sessions.createByAction().currentSiteId();
            // 查询当前站点下的所有区域
            zoneList = zoneService.find(siteId);
            // 查询当前站点下的所有 FTP
            ftpList = ftpService.findAll(siteId);
            dvPlace = new InfraredDVPlaceBean();
            log("摄像机管理", "添加红外摄像机");
            return Action.SUCCESS;
        } catch (Exception e) {
            log.error("添加红外摄像机", e);
            return Action.ERROR;
        }
    }

    /**
     * 添加红外热像仪点位信息
     *
     * @author zhangpeng
     * @date 2012-6-11
     */
    public String addInfraredDVPlace() {
        try {
            // 获取当前站点
            String siteId = Sessions.createByAction().currentSiteId();

            // 获取点位编码索引
            int index = systemConfigService.findDvPlaceCodeIndex();
            // 设置摄像机点位默认信息
            dvPlace.setId(GalaxyIdUtil.get64UUID());
            dvPlace.setCreateTime(new Date());
            dvPlace.setPlaceCode(siteId + index);
            dvPlace.setEnable(true);
            infraredDVPlaceService.save(dvPlace);

            // 更新点位编码索引
            systemConfigService.updateDvPlaceCodeIndex(index + 1);

            MessageActionUtil.success("添加成功，请对摄像机进行设置。");
        } catch (Exception ex) {
            MessageActionUtil.fail("添加失败");
            log.error("添加摄像机", ex);
        }
        return Action.SUCCESS;
    }

    public InfraredDVPlaceBean getDvPlace() {
        return dvPlace;
    }

    public void setDvPlace(InfraredDVPlaceBean dvPlace) {
        this.dvPlace = dvPlace;
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
