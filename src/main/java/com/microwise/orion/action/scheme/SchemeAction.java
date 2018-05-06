package com.microwise.orion.action.scheme;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.MethodType;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Institution;
import com.microwise.orion.bean.Scheme;
import com.microwise.orion.service.InstitutionService;
import com.microwise.orion.service.SchemeService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author 王耕
 *
 * @date 15-9-15
 */
@Beans.Action
@Orion
@Route("/orion/schemes")
public class SchemeAction extends OrionLoggerAction {

    public static final Logger log = LoggerFactory.getLogger(SchemeAction.class);

    @Autowired
    private SchemeService schemeService;

    @Autowired
    private InstitutionService institutionService;


    //input
    /**
     * 方案新增对象
     */
    private Scheme scheme;

    /**
     * 根据ID删除方案
     */
    private int id;

    //output
    /**
     * 方案实体集合
     */
    private List<Scheme> schemes;

    /**
     * 单位管理
     */
    private List<Institution> institutions;

    @Route("index")
    public String execute() {
        ActionMessage.createByAction().consume();
        try{
            String siteId = Sessions.createByAction().currentSiteId();
            schemes = schemeService.findAll(siteId);
            log("方案管理","资产管理");
        }catch (Exception e){
            log.error("方案管理",e);
        }
        return Results.ftl("orion/pages/scheme/scheme-list.ftl");
    }

    @Route("toAddScheme")
    public String toAddScheme(){
        try{
            String siteId = Sessions.createByAction().currentSiteId();
            institutions = institutionService.findInstitutions(siteId, Institution.DESIGN_UNIT);
            log("新增方案", "资产管理");
        }catch (Exception e){
            log.error("新增方案",e);
        }
        return Results.ftl("orion/pages/scheme/add-scheme.ftl");
    }

    @Route("toUpdateScheme")
    public String toUpdateScheme(){
        try{
            String siteId = Sessions.createByAction().currentSiteId();
            scheme = schemeService.findSchemeById(id);
            institutions = institutionService.findInstitutions(siteId, Institution.DESIGN_UNIT);
            log("修改方案","资产管理");
        } catch (Exception e){
            log.error("修改方案",e);
        }
        return Results.ftl("orion/pages/scheme/update-scheme.ftl");
    }

    @Route(value = "saveOrUpdateScheme", method = MethodType.POST)
    public String saveOrUpdateScheme(){
        try{
            String siteId = Sessions.createByAction().currentSiteId();
            scheme.setSiteId(siteId);
            schemeService.saveOrUpdateScheme(scheme);
            ActionMessage.createByAction().success("新增或修改方案成功");
            log("新增或修改方案", "资产管理");
        }catch (Exception e){
            ActionMessage.createByAction().fail("新增或修改方案失败");
            log.error("新增或修改方案",e);
        }
        return Results.redirect("index");
    }

    @Route("deleteScheme")
    public String deleteScheme(){
        try{
            schemeService.deleteScheme(id);
            ActionMessage.createByAction().success("删除方案成功");
        }catch (Exception e){
            ActionMessage.createByAction().fail("删除方案失败");
            log.error("删除方案",e);
        }
        return Results.redirect("index");
    }

    public List<Scheme> getSchemes() {
        return schemes;
    }

    public void setSchemes(List<Scheme> schemes) {
        this.schemes = schemes;
    }

    public Scheme getScheme() {
        return scheme;
    }

    public void setScheme(Scheme scheme) {
        this.scheme = scheme;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Institution> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(List<Institution> institutions) {
        this.institutions = institutions;
    }
}
