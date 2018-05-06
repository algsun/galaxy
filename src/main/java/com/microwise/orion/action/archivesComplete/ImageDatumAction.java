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
import com.microwise.orion.bean.ImageDatum;
import com.microwise.orion.service.ImageDatumService;
import com.microwise.orion.sys.Orion;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 影像资料action
 *
 * @author liuzhu
 * @date 2015-11-11
 */

@Beans.Action
@Orion
@Route("/orion/archives")
public class ImageDatumAction {

    @Autowired
    private ImageDatumService imageDatumService;

    @Autowired
    private UserService userService;

    /**
     * 影像资料bean
     */
    private ImageDatum imageDatum;

    /**
     * 用户list
     */
    private List<User> users;

    @Route("imageDatum/save.json")
    public String save() {
        imageDatumService.save(imageDatum);
        ImageDatum newImageDatum = imageDatumService.findNewImageDatum();
        return Results.json().asRoot(newImageDatum.getId()).done();
    }

    @Route("imageDatums.json")
    public String drawRegisters() {
        if (imageDatum == null) {
            return null;
        }
        List<ImageDatum> imageDatums = imageDatumService.findImageDatums(imageDatum.getRepairRecord().getId());

        /*//TODO 优化
        for (ImageDatum datum : imageDatums) {
            datum.setRepairRecord(null);
            datum.getSubmitPerson().setRoles(null);
            datum.getSubmitPerson().setLogicGroup(null);
            datum.getSubmitPerson().setLogicGroups(null);
        }*/
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new Hibernate4Module());
            Map<String, Object> data = Maps.newHashMap();
            data.put("imageDatums", imageDatums);
            String jsonString = objectMapper.writeValueAsString(data);
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("text/json;charset=UTF8");
            response.getWriter().print(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Route("imageDatums/to_update.json")
    public String toUpdate() {
        users = userService.findUserLists(Sessions.createByAction().currentLogicGroup().getId());
        imageDatum = imageDatumService.findImageDatum(imageDatum.getId());
        return Results.ftl("orion/pages/_common/archivesTables/update-image-datum.ftl");
    }

    @Route("imageDatums/do_update.json")
    public String doUpdate() {
        if (imageDatum.getFilePath().equals("")){
            imageDatum.setFilePath(null);
        }
        imageDatumService.update(imageDatum);
        return Results.json().asRoot(imageDatum.getId()).done();
    }

    @Route("imageDatum/delete.json")
    public String delete() {
        imageDatumService.delete(imageDatum);
        return Results.json().asRoot(true).done();
    }

    public ImageDatum getImageDatum() {
        return imageDatum;
    }

    public void setImageDatum(ImageDatum imageDatum) {
        this.imageDatum = imageDatum;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
