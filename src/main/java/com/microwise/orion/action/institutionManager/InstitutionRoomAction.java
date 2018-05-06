package com.microwise.orion.action.institutionManager;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.InstitutionRoom;
import com.microwise.orion.service.InstitutionRoomService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 单位库房action
 *
 * @author liuzhu
 * @date 2015-11-11
 */

@Beans.Action
@Orion
@Route("/orion/institutionRoom")
public class InstitutionRoomAction {

    @Autowired
    private InstitutionRoomService institutionRoomService;

    /**
     * 单位库房bean
     */
    private InstitutionRoom institutionRoom;

    /**
     * 库房list
     */
    private List<InstitutionRoom> institutionRooms;

    /**
     * 库房ID
     */
    private int id;

    @Route("index")
    public String index() {
        institutionRooms = institutionRoomService.findByInstitutionId(institutionRoom.getInstitutionId());
        ActionMessage.createByAction().consume();
        return Results.ftl("orion/pages/institution/institution-room.ftl");
    }

    @Route("to_add")
    public String to_add() {
        return Results.ftl("orion/pages/institution/institution-room-add.ftl");
    }

    @Route("do_add")
    public String do_add() {
        try {
            institutionRoomService.add(institutionRoom);
            ActionMessage.createByAction().success("添加单位成功");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("添加单位失败");
            e.printStackTrace();
        }
        return Results.redirect("index?institutionRoom.institutionId=" + institutionRoom.getInstitutionId());
    }

    @Route("delete")
    public String delete() {
        try {
            institutionRoomService.deleteInstitutionRoom(institutionRoom);
            ActionMessage.createByAction().success("删除单位库房成功");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("删除单位库房失败");
            e.printStackTrace();
        }
        return Results.redirect("index?institutionRoom.institutionId="+institutionRoom.getInstitutionId());
    }

    @Route(value = "institutionRooms/{id}")
    public String ajaxinstitutionRoom() {
        InstitutionRoom institutionRoom = null;
        try {
            institutionRoom = institutionRoomService.findById(id);
        } catch (Exception e) {
            ActionMessage.createByAction().fail("获取库房信息失败");
            e.printStackTrace();
        }
        return Results.json().asRoot(institutionRoom).done();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public InstitutionRoom getInstitutionRoom() {
        return institutionRoom;
    }

    public void setInstitutionRoom(InstitutionRoom institutionRoom) {
        this.institutionRoom = institutionRoom;
    }

    public List<InstitutionRoom> getInstitutionRooms() {
        return institutionRooms;
    }

    public void setInstitutionRooms(List<InstitutionRoom> institutionRooms) {
        this.institutionRooms = institutionRooms;
    }
}
