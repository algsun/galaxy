package com.microwise.orion.action.archivesComplete;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.google.common.collect.Maps;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.DrawRegister;
import com.microwise.orion.service.DrawRegisterService;
import com.microwise.orion.sys.Orion;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 绘图登记action
 *
 * @author liuzhu
 * @date 2015-11-11
 */

@Beans.Action
@Orion
@Route("/orion/archives")
public class DrawRegisterAction {

    public static final Logger logger = LoggerFactory.getLogger(DrawRegisterAction.class);

    @Autowired
    private UserService userService;

    private List<User> users;

    @Autowired
    private DrawRegisterService registerService;

    private DrawRegister drawRegister;

    private String imgPath;

    @Route("drawRegister/save.json")
    public String save() {
        registerService.save(drawRegister);
        DrawRegister draw = registerService.findNewDrawRegister(drawRegister.getRepairRecord().getId());
        return Results.json().asRoot(draw.getId()).done();
    }

    @Route("drawRegisters.json")
    public String drawRegisters() {
        try {
            List<DrawRegister> drawRegisters = registerService.findDrawRegisters(drawRegister.getRepairRecord().getId());

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new Hibernate4Module());
            Map<String, Object> data = Maps.newHashMap();
            data.put("drawRegisters",drawRegisters);
            String jsonString = objectMapper.writeValueAsString(data);

            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("text/json;charset=UTF8");
            response.getWriter().print(jsonString);
            logger.info("查询绘图成功");
        } catch (Exception e) {
            logger.error("查询绘图失败", e);
        }
        return null;
    }

    @Route("drawRegister/to_update")
    public String toUpdate() {
        imgPath = Sessions.createByAction().getGalaxyResourceURL();
        imgPath += "/orion/images/archives/";
        users = userService.findUserLists(Sessions.createByAction().currentLogicGroup().getId());
        drawRegister = registerService.findDrawRegister(drawRegister.getId());
        imgPath += drawRegister.getImgPath();
        return Results.ftl("orion/pages/_common/archivesTables/update-draw-register.ftl");
    }

    @Route("drawRegister/do_update.json")
    public String doUpdate() {
        DrawRegister draw = registerService.findById(drawRegister.getId());
        drawRegister.setImgPath(draw.getImgPath());
        registerService.update(drawRegister);
        return Results.json().asRoot(true).done();
    }

    @Route("drawRegister/delete.json")
    public String delete() {
        registerService.delete(drawRegister);
        return Results.json().asRoot(true).done();
    }

    public DrawRegister getDrawRegister() {
        return drawRegister;
    }

    public void setDrawRegister(DrawRegister drawRegister) {
        this.drawRegister = drawRegister;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
