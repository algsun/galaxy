/**
 *
 */
package com.microwise.proxima.action.dvPlace;

import com.google.common.base.Strings;
import com.microwise.blackhole.sys.MessageActionUtil;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.proxima.action.opticsDVPlace.QueryOpticsDVPlaceAction;
import com.microwise.proxima.service.DVPlaceService;
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

/**
 * <pre>
 * 摄像机action（公共信息）
 * </pre>
 *
 * @author li.jianfei
 * @date 2013-3-27
 */
@Beans.Action("dvPlaceAction")
@Proxima
public class DVPlaceAction extends ProximaLoggerAction implements ServletRequestAware {

    private static final Logger log = LoggerFactory.getLogger(QueryOpticsDVPlaceAction.class);
    /**
     * 摄像机服务层对象
     */
    @Autowired
    private DVPlaceService dvPlaceService;

    /**
     * request
     */
    private HttpServletRequest request;


    // Input
    /**
     * 区域id
     */
    private String zoneId;

    /**
     * 摄像机点位id
     */
    private String dvPlaceId;

    /**
     * 摄像机点位名称
     */
    private String dvPlaceName;

    // Output
    /**
     * 区域内点位是否唯一
     * true:唯一
     * false:不唯一
     */
    private boolean unique;

    /**
     * 更新摄像机工作状态
     *
     * @author li.jianfei
     * @date 2013-3-26
     */
    public String updateDVPlaceEnable() {

        try {
            // 根据摄像机点位id 查询摄像机点位信息
            String id = request.getParameter("id");
            dvPlaceService.changeEnable(id);

            // 守望者
            DaemonAPIClients.getClient().dvPlaceEnableChanged(id, dvPlaceService.findDvEnable(id));
            MessageActionUtil.success("更改摄像机状态成功");
            log("摄像机管理","更新摄像机状态");
        } catch (DaemonInvokeException e) {
            MessageActionUtil.fail("系统出错，但摄像机状态已更改");
            log.error("停用/启用", e);
        } catch (Exception e) {
            MessageActionUtil.fail("更改摄像机状态失败");
            log.error("停用/启用", e);
        }
        return Action.SUCCESS;
    }

    /**
     * 区域内点位名称是否唯一
     *
     * @author li.jianfei
     * @date 2013-3-26
     */
    public String isNameUnique() {
        unique = !dvPlaceService.hasSameName(zoneId, dvPlaceName, Strings.emptyToNull(dvPlaceId));
        return Action.SUCCESS;
    }

    /**
     * 重新注入request
     */
    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
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

    public String getDvPlaceName() {
        return dvPlaceName;
    }

    public void setDvPlaceName(String dvPlaceName) {
        this.dvPlaceName = dvPlaceName;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }
}
