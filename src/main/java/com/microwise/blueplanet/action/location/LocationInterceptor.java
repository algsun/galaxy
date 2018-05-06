package com.microwise.blueplanet.action.location;

import com.microwise.blueplanet.service.LocationService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import org.apache.struts2.StrutsStatics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 位置点的拦截器
 *
 * @author li.jianfei
 * @date 2014-06-25
 */
@Component
@Blueplanet
public class LocationInterceptor implements Interceptor {
    @Autowired
    private LocationService locationService;

    @Override
    public void init() {
    }

    @Override
    public void destroy() {
    }

    /**
     * /location/{locationId} 请求下的拦截器，根据 locationId 加载位置点的监测指标放到 ActionContext 中， 供 layout.ftl 使用
     *
     * @param actionInvocation
     * @return
     * @throws Exception
     */
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        ActionContext actionContext = actionInvocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
        String servletPath = request.getServletPath();


        String pathSuffix = "/blueplanet/location/";
        // "/location/" 路径下，但不是 json 请求
        if (servletPath.startsWith(pathSuffix) && !servletPath.endsWith(".json")) {
            String locationPath = servletPath.substring(pathSuffix.length());
            int firstSlashIndex = locationPath.indexOf("/");

            String locationId;
            if (firstSlashIndex == -1) {
                locationId = locationPath;
            } else {
                locationId = locationPath.substring(0, firstSlashIndex);
            }
            // 返回设备的监测指标
            actionContext.put("_sensorinfoesOfLocation", locationService.findSensorInfoList(locationId));
        }

        return actionInvocation.invoke();
    }
}
