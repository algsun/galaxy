package com.microwise.orion.action.institutionManager;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.MethodType;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Institution;
import com.microwise.orion.bean.InstitutionRoom;
import com.microwise.orion.service.InstitutionRoomService;
import com.microwise.orion.service.InstitutionService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 单位action
 *
 * @author liuzhu
 * @date 2015-9-8
 */

@Beans.Action
@Orion
@Route("/orion/institution")
public class InstitutionAction {

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private InstitutionRoomService institutionRoomService;

    private Institution institution;

    private List<Institution> institutions;

    /**
     * 单位ID
     */
    private int id;

    @Route("index")
    public String index() {
        institutions = institutionService.findBySiteId(Sessions.createByAction().currentSiteId());
        ActionMessage.createByAction().consume();
        return Results.ftl("orion/pages/institution/institution.ftl");
    }

    @Route(value = "to_add", method = MethodType.GET)
    public String toAdd() {
        return Results.ftl("orion/pages/institution/institution-add.ftl");
    }

    @Route(value = "do_add", method = MethodType.POST)
    public String do_add() {
        try {
            institution.setSiteId(Sessions.createByAction().currentSiteId());
            institution.setCreateDate(new Date());
            institutionService.save(institution);
            ActionMessage.createByAction().success("添加单位成功");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("添加单位失败");
            e.printStackTrace();
        }
        return Results.redirect("index");
    }

    @Route(value = "to_update", method = MethodType.GET)
    public String toUpdate() {
        institution = institutionService.findById(institution.getId());
        return Results.ftl("orion/pages/institution/institution-update.ftl");
    }

    @Route(value = "do_update", method = MethodType.POST)
    public String doUpdate() {
        try {
            institutionService.update(institution);
            ActionMessage.createByAction().success("修改单位成功");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("修改单位失败");
            e.printStackTrace();
        }
        return Results.redirect("index");
    }

    @Route(value = "delete", method = MethodType.GET)
    public String delete() {
        try {
            if (institution.isHasRoom()) {
                institutionRoomService.deleteByInstitutionId(institution.getId());
            }
            institutionService.delete(institution);
            ActionMessage.createByAction().success("删除单位成功");
        } catch (Exception e) {
            e.printStackTrace();
            ActionMessage.createByAction().fail("删除单位失败");
        }
        return Results.redirect("index");
    }

    @Route("findInstitutionRoom")
    public String findInstitutionRoom() {
        List<InstitutionRoom> institutionRooms = institutionRoomService.findByInstitutionId(institution.getId());
        return Results.json().asRoot(institutionRooms.size()).done();
    }

    @Route(value = "institutions.json")
    public String ajaxCollectionUnits() {
        try {
            institutions = institutionService.findInstitutions(Sessions.createByAction().currentSiteId(), Institution.COLLECTION_UNIT);
        } catch (Exception e) {
            ActionMessage.createByAction().fail("获取收藏单位失败");
            e.printStackTrace();
        }
        return Results.json().asRoot(institutions).done();
    }

    @Route(value = "institutions/{id}")
    public String ajaxInstitution() {
        Institution institution = null;
        try {
            institution = institutionService.findById(id);
        } catch (Exception e) {
            ActionMessage.createByAction().fail("获取收藏单位失败");
            e.printStackTrace();
        }
        return Results.json().asRoot(institution).done();
    }

    @Route(value = "institutions/{id}/storehouses.json")
    public String ajaxStoreHouses() {
        List<InstitutionRoom> institutionRooms = null;
        try {
            institutionRooms = institutionRoomService.findByInstitutionId(id);
        } catch (Exception e) {
            ActionMessage.createByAction().fail("获取库房失败");
            e.printStackTrace();
        }
        return Results.json().asRoot(institutionRooms).done();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public List<Institution> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(List<Institution> institutions) {
        this.institutions = institutions;
    }

}
