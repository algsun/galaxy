package com.microwise.proxima.action.infraredDVPlace;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.proxima.bean.FTPProfile;
import com.microwise.proxima.bean.InfraredDVPlaceBean;
import com.microwise.proxima.bean.Zone;
import com.microwise.proxima.service.FTPProfileService;
import com.microwise.proxima.service.InfraredDVPlaceService;
import com.microwise.proxima.service.ZoneService;
import com.microwise.proxima.sys.Proxima;
import com.microwise.proxima.util.URLEncodes;
import com.opensymphony.xwork2.Action;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 更新红外摄像机点位
 *
 * @author zhangpeng
 * @date 2012-7-11
 * @check zhang.licong 2012-07-14
 * @check guo.tian li.jianfei 2012-09-18
 */
@Beans.Action
@Proxima
public class UpdateInfraredDVPlaceAction implements ServletRequestAware {

    /**
     * 摄像机服务层对象
     */
    @Autowired
    private InfraredDVPlaceService dvPlaceService;
    /**
     * FTP 服务层对象
     */
    @Autowired
    private FTPProfileService ftpService;

    @Autowired
    private InfraredDVPlaceService infraredDVPlaceService;
    /**
     * 区域服务层对象
     */
    @Autowired
    private ZoneService zoneService;

    private HttpServletRequest request;

    /**
     * 页面提示消息（操作结果）
     */
    private String message;

    private String id;
    /**
     * 摄像机点位信息对象
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
     * 准备修改红外热像仪点位信息
     *
     * @author zhangpeng
     * @date 2012-6-11
     */
    public String toUpdateInfraredDVPlace() {
        // 根据摄像机点位id 查询摄像机点位信息
        String dvPlaceId = request.getParameter("id");
        dvPlace = infraredDVPlaceService.findById(dvPlaceId);

        // 获取当前站点id
        String siteId = Sessions.createByAction().currentSiteId();

        // 获取当前站点下的所有区域
        zoneList = zoneService.find(siteId);

        // 获取当前站点下的所有FTP
        ftpList = ftpService.findAll(siteId);


        return Action.SUCCESS;
    }

    /**
     * 修改红外热像仪点位信息
     *
     * @author zhangpeng
     * @date 2012-7-11
     */
    public String updateInfraredDVPlace() {
        try {
            InfraredDVPlaceBean dv = dvPlaceService.findById(dvPlace.getId());
            dv.setPlaceName(dvPlace.getPlaceName());
            dv.setFtp(dvPlace.getFtp());
            dv.setDvIp(dvPlace.getDvIp());
            dv.setRemark(dvPlace.getRemark());
            dvPlaceService.update(dv);
            message = "编辑成功，请对摄像机进行设置。";
        } catch (Exception ex) {
            ex.printStackTrace();
            message = "编辑失败！";
        }
        message = URLEncodes.encodeUTF8(message);
        return Action.SUCCESS;
    }

    /**
     * 重新注入request
     */
    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public InfraredDVPlaceBean getDvPlace() {
        return dvPlace;
    }

    public void setDvPlace(InfraredDVPlaceBean dvPlace) {
        this.dvPlace = dvPlace;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
