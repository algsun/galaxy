package com.microwise.orion.action.relic;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Era;
import com.microwise.orion.service.EraService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 编辑年代
 *
 * @author xuyuexi
 * @time 14-6-4
 */
@Beans.Action
@Orion
public class editEraAction extends OrionLoggerAction {


    /**
     * 文物属性信息
     */
    @Autowired
    private EraService eraService;

    private String eraName;

    @Route(value = "orion/saveEra", params = "eraName")
    public String excute() {
        Map map = new HashMap();
        if (eraService.isEraExist(eraName)) {
            map.put("success", null);
            return Results.json().asRoot(map).done();
        } else {
            Era era = new Era();
            era.setName(eraName);
            try {
                int i = eraService.save(era);
                map.put("eraId", i);
            } catch (Exception e) {
                e.printStackTrace();
                map.put("success", "error");
                return Results.json().asRoot(map).done();
            }
        }
        map.put("success", "success");
        return Results.json().asRoot(map).done();
    }


    @Route(value = "orion/deleteEra", params = "eraName")
    public String delete() {
        Map map = new HashMap();
        try {
            int i = eraService.deleteByName(eraName);
            map.put("eraId", i);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", "error");
            return Results.json().asRoot(map).done();
        }
        map.put("success", "success");
        return Results.json().asRoot(map).done();
    }

    public EraService getEraService() {
        return eraService;
    }

    public void setEraService(EraService eraService) {
        this.eraService = eraService;
    }

    public String getEraName() {
        return eraName;
    }

    public void setEraName(String eraName) {
        this.eraName = eraName;
    }
}

