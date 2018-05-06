package com.microwise.orion.action.relic;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Level;
import com.microwise.orion.service.LevelService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 编辑质地
 *
 * @author xuyuexi
 * @time 14-6-4
 */
@Beans.Action
@Orion
public class editLevelAction extends OrionLoggerAction {


    /**
     * 文物属性信息
     */
    @Autowired
    private LevelService levelService;

    private String levelName;

    @Route(value = "orion/saveLevel", params = "levelName")
    public String excute() {
        Map map = new HashMap();
        if (levelService.isLevelExist(levelName)) {
            map.put("success", null);
            return Results.json().asRoot(map).done();
        } else {
            Level level = new Level();
            level.setName(levelName);
            try {
                int i = levelService.save(level);
                map.put("levelId", i);
            } catch (Exception e) {
                e.printStackTrace();
                map.put("success", "error");
                return Results.json().asRoot(map).done();
            }
        }
        map.put("success", "success");
        return Results.json().asRoot(map).done();
    }


    @Route(value = "orion/deleteLevel", params = "levelName")
    public String delete() {
        Map map = new HashMap();
        try {
            int i = levelService.deleteByName(levelName);
            map.put("levelId", i);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", "error");
            return Results.json().asRoot(map).done();
        }
        map.put("success", "success");
        return Results.json().asRoot(map).done();
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}

