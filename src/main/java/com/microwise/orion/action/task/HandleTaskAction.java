package com.microwise.orion.action.task;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Strings;
import com.google.common.io.ByteStreams;
import com.google.common.io.Resources;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.UpLoadFileUtil;
import com.microwise.orion.bean.*;
import com.microwise.orion.service.*;
import com.microwise.orion.sys.Orion;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.struts2.views.freemarker.StrutsBeanWrapper;
import org.codehaus.xfire.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

/**
 * 处理任务action
 */

@Beans.Action
@Orion
@Route("/orion/handleTask")
public class HandleTaskAction {

    @Autowired
    private SchemeService schemeService;

    @Autowired
    private RepairRecordService repairRecordService;

    @Autowired
    private RelicService relicService;

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private InstitutionRoomService institutionRoomService;

    @Autowired
    private DrawRegisterService drawRegisterService;

    @Autowired
    private ImageDatumService imageDatumService;

    @Autowired
    private DetectionAnalysisService detectionAnalysisService;

    @Autowired
    private RepairAssessmentService repairAssessmentService;

    @Autowired
    private SituationService situationService;

    @Autowired
    private StatusQuoService statusQuoService;

    @Autowired
    private RepairPhotoService repairPhotoService;

    @Autowired
    private RepairLogService repairLogService;

    @Autowired
    private UserService userService;

    /**
     * 方案list
     */
    private List<Scheme> schemes;

    /**
     * 单位list
     */
    private List<Institution> institutions;

    /**
     * 用户
     */
    private List<User> users;

    /**
     * 任务list
     */
    private List<RepairRecord> repairRecords;

    /**
     * 文物bean
     */
    private Relic relic;

    /**
     * 单位bean
     */
    private Institution institution;

    /**
     * 任务bean
     */
    private RepairRecord repairRecord;

    /**
     * 单位库房
     */
    private InstitutionRoom institutionRoom;

    /**
     * 协助人
     */
    private List<String> userNames;

    /**
     * 因由bean
     */
    private List<RepairReason> repairReasons;

    private String notByReason;


    // 模板文物图片宽度
    public static final double TEMPLATE_PHOTO_WIDTH = 324.7 * 4 / 3; // pt to px

    // 现状相关图像宽度
    public static final double QUO_PHOTO_WIDTH = 166.4 * 4 / 3; // pt to px

    // 修复后照片
    public static final double REPAIR_PHOTO_WIDTH = 447.6 * 4 / 3; // pt to px

    @Route("index")
    public String index() {
        return Results.ftl("orion/pages/task/task.ftl");
    }

    @Route("to_assign_task")
    public String to_assign_task() {
        String siteId = Sessions.createByAction().currentSiteId();
        relic = relicService.findRelicByRelicId(relic.getId(), siteId);

        repairRecord = repairRecordService.findRepairRecordById(repairRecord.getId());

        // 查询收藏单位
        if (relic.getRelicPropertyMap().containsKey("institution")) {
            RelicProperty property = relic.getRelicPropertyMap().get("institution");
            int institutionId = Integer.parseInt(property.getPropertyValue());
            institution = institutionService.findById(institutionId);
        }


        // 查询库房
        if (relic.getRelicPropertyMap().containsKey("storehouse")) {
            RelicProperty property = relic.getRelicPropertyMap().get("storehouse");
            int institutionRoomId = Integer.parseInt(property.getPropertyValue());
            institutionRoom = institutionRoomService.findById(institutionRoomId);
        }

        repairRecords = repairRecordService.findBySiteId(siteId);
        schemes = schemeService.findAll(siteId);
        institutions = institutionService.findBySiteId(siteId);
        users = userService.findUserLists(Sessions.createByAction().currentLogicGroup().getId());
        return Results.ftl("orion/pages/task/assign-task.ftl");
    }

    @Route("do_assign_task")
    public String do_assign_task() {
        try {
            repairRecord.setState(RepairRecord.unAccepted);
            repairRecordService.update(repairRecord);
            ActionMessage.createByAction().success("分配任务成功!");
        } catch (Exception e) {
            ActionMessage.createByAction().fail("分配任务失败 !");
            e.printStackTrace();
        }
        return Results.redirect("../repairRecords");
    }

    @Route("accept_task")
    public String acceptTask() {
        String siteId = Sessions.createByAction().currentSiteId();
        relic = relicService.findRelicByRelicId(relic.getId(), siteId);

        repairRecord = repairRecordService.findRepairRecordById(repairRecord.getId());

        String[] userIds = repairRecord.getSecondaryUserId().split(",");

        userNames = new ArrayList<String>();
        for (String id : userIds) {
            int userId = Integer.parseInt(id.trim());
            User users = userService.findUserById(userId);
            userNames.add(users.getUserName());
        }

        // 查询收藏单位
        if (relic.getRelicPropertyMap().containsKey("institution")) {
            RelicProperty property = relic.getRelicPropertyMap().get("institution");
            int institutionId = Integer.parseInt(property.getPropertyValue());
            institution = institutionService.findById(institutionId);
        }

        // 查询库房
        if (relic.getRelicPropertyMap().containsKey("storehouse")) {
            RelicProperty property = relic.getRelicPropertyMap().get("storehouse");
            int institutionRoomId = Integer.parseInt(property.getPropertyValue());
            institutionRoom = institutionRoomService.findById(institutionRoomId);
        }

        return Results.ftl("orion/pages/task/accept-task.ftl");
    }

    @Route("do_accept_task")
    public String doAcceptTask() {
        try {
            repairRecord = repairRecordService.findRepairRecordById(repairRecord.getId());
            repairRecord.setState(RepairRecord.repairing);
            repairRecordService.update(repairRecord);
            ActionMessage.createByAction().success("接受任务成功");
        } catch (Exception e) {
            ActionMessage.createByAction().success("接受任务失败");
            e.printStackTrace();
        }
        return Results.redirect("../repairRecords");
    }

    @Route("assign_task_after_update")
    public String assignTaskAfterUpdate() {
        String siteId = Sessions.createByAction().currentSiteId();
        repairRecord = repairRecordService.findRepairRecordById(repairRecord.getId());
        relic = relicService.findRelicByRelicId(repairRecord.getRelic().getId(), siteId);

        schemes = schemeService.findAll(siteId);
        institutions = institutionService.findBySiteId(siteId);
        users = userService.findUserLists(Sessions.createByAction().currentLogicGroup().getId());
        // 查询收藏单位
        if (relic.getRelicPropertyMap().containsKey("institution")) {
            RelicProperty property = relic.getRelicPropertyMap().get("institution");
            int institutionId = Integer.parseInt(property.getPropertyValue());
            institution = institutionService.findById(institutionId);
        }
        return Results.ftl("orion/pages/task/edit-assign-task-after-repair-record.ftl");
    }

    @Route("do_assign_task_after_update")
    public String doAssignTaskAfterUpdate() {
        try {
            repairRecordService.update(repairRecord);
            ActionMessage.createByAction().success("修改成功!");
        } catch (Exception e) {
            ActionMessage.createByAction().success("修改失败!");
            e.printStackTrace();
        }
        return Results.redirect("../repairRecords");
    }

    @Route("secondaryUser.json")
    public String secondaryUser() {
        List<User> userList = userService.findUserLists(Sessions.createByAction().currentLogicGroup().getId());
        Map<Integer, String> users = new HashMap<Integer, String>();
        userList.iterator();
        for (Iterator<User> iterator = userList.iterator(); iterator.hasNext(); ) {
            User user = iterator.next();
            if (user.getId() == repairRecord.getMainUser().getId()) {
                iterator.remove();
            } else {
                users.put(user.getId(), user.getUserName());
            }

        }
        return Results.json().asRoot(users).done();
    }

    @Route("passBeforeCheck.json")
    public String passBeforeCheck() {
        RepairRecord record = repairRecordService.findRepairRecordById(repairRecord.getId());

        String repairInfo = record.getRepairInfo();
        int relicId = record.getRelic().getId();
        Relic r = relicService.findById(relicId);
        boolean flag = false;
        if (Strings.isNullOrEmpty(repairInfo)) {
            flag = true;
        }

        if (record.getExtractDate() == null) {
            flag = true;
        }

        if (record.getReturnDate() == null) {
            flag = true;
        }

        List<RepairPhoto> repairPhotos = repairPhotoService.findByRepairRecordId(record.getId(), 2);
        if (repairPhotos.isEmpty()) {
            flag = true;
        }

        StatusQuo statusQuo = statusQuoService.findStatusQuos(r.getId());
        if (Strings.isNullOrEmpty(statusQuo.getQuoInfo())) {
            flag = true;
        }

        List<RepairLog> repairLogs = repairLogService.findRepairLogs(repairRecord.getId());
        for (RepairLog repairLog : repairLogs) {
            if (repairLog.getRepairPerson() == null || repairLog.getStamp() == null) {
                flag = true;
                break;
            }
        }

        RepairAssessment repairAssessment = repairAssessmentService.findByRepairRecordId(repairRecord.getId());
        if (repairAssessment.getStamp() == null) {
            flag = true;
        }

        return Results.json().asRoot(flag).done();
    }

    @Route("submitCheck")
    public String submitCheck() {
        RepairRecord record = repairRecordService.findById(repairRecord.getId());
        record.setState(RepairRecord.unCheck);
        repairRecordService.update(record);
        return Results.redirect("../repairRecords");
    }

    @Route("pass")
    public String pass() {
        RepairRecord record = repairRecordService.findById(repairRecord.getId());
        record.setState(RepairRecord.unReviews);
        record.setCheckUser(Sessions.createByAction().currentUser());
        record.setCheckDate(new Date());
        repairRecordService.update(record);
        // 生成PDF
        new Thread(new Word2PDFThread()).run();
        return Results.redirect("../repairRecords");
    }

    @Route("noPass")
    public String noPass() {
        RepairRecord record = repairRecordService.findById(repairRecord.getId());
        record.setNotByReason(notByReason);
        record.setState(RepairRecord.repairing);
        repairRecordService.update(record);
        return Results.redirect("../repairRecords");
    }

    class Word2PDFThread implements Runnable {

        public void run() {

            try {
                String siteId = Sessions.createByAction().currentSiteId();
                Map<String, Object> data = new HashMap<String, Object>();
                int repairRecordId = getRepairRecord().getId();
                RepairRecord repairRecord = repairRecordService.findRepairRecordById(repairRecordId);
                // 获取文物扩展属性
                Relic relic = relicService.findRelicByRelicId(repairRecord.getRelic().getId(), siteId);

                String templatePath = URLDecoder.decode(Resources.getResource("orion/template").getFile(), "utf-8");
                String resourceDir = UpLoadFileUtil.getUploadPath("orion");
                String path = resourceDir + File.separator + "file" + File.separator + siteId + File.separator + relic.getId() + File.separator;

                String fileName = repairRecord.getIdentifier() + ".doc";
                Configuration configuration = new Configuration();

                configuration.setDirectoryForTemplateLoading(new File(templatePath));
                configuration.setDefaultEncoding("utf-8");
                // 注意，这里使用 Struts 封装的 ObjectWrapper, 同时提供了 map 的支持
                configuration.setObjectWrapper(new StrutsBeanWrapper(true));

                Template template = configuration.getTemplate("repair-archive.ftl");

                File dir = new File(path);
                if (!dir.exists()) dir.mkdir();
                String docFilePath = path + File.separator + fileName;
                File docFile = new File(docFilePath);
                if (docFile.exists()) {
                    docFile.delete();
                }
                Writer out = new OutputStreamWriter(new FileOutputStream(docFile), "UTF-8");


                // 组织数据

                // 处理扩展属性值
                for (RelicProperty relicProperty : relic.getRelicPropertielist()) {
                    String enName = relicProperty.getProperty().getEnName();
                    if (enName.equals("institution")) {
                        relicProperty.setPropertyValue(institutionService.findById(Integer.parseInt(relicProperty.getPropertyValue())).getName());
                    } else if (enName.equals("storehouse")) {
                        relicProperty.setPropertyValue(institutionRoomService.findById(Integer.parseInt(relicProperty.getPropertyValue())).getRoomName());
                    }
                }
                repairRecord.setRelic(relic);

                // 保存现状-取最新记录
                StatusQuo statusQuo = statusQuoService.findStatusQuos(relic.getId());

                // 绘图登记
                List<DrawRegister> drawRegisters = drawRegisterService.findDrawRegisters(repairRecordId);
                // 检测分析
                List<DetectionAnalysis> detectionAnalysisList = detectionAnalysisService.findByRepairRecordId(repairRecordId);
                // 影像资料
                List<ImageDatum> imageDatums = imageDatumService.findImageDatums(repairRecordId);
                // 自评估与验收表
                RepairAssessment repairAssessment = repairAssessmentService.findByRepairRecordId(repairRecordId);

                // 其他属性
                Situation situation = situationService.findByRepairRecordId(repairRecordId);

                // 文物现状相关图像
                List<RepairPhoto> quoPhotos = repairPhotoService.findByRepairRecordId(repairRecordId, RepairPhoto.QUO_PHOTO);

                // 文物修复记录
                List<RepairLog> repairLogs = repairLogService.findRepairLogs(repairRecordId);
                for (RepairLog log : repairLogs) {
                    User u = new User();
                    log.setRepairRecord(null);
                    if (!Strings.isNullOrEmpty(log.getRepairPerson())) {
                        String[] userIds = log.getRepairPerson().split(",");
                        List<String> userNames = new ArrayList<String>();
                        for (String id : userIds) {
                            int userId = Integer.parseInt(id.trim());
                            User users = userService.findUserById(userId);
                            userNames.add(users.getUserName());
                        }
                        log.setRepairPersonName(userNames);
                    }
                }
                List<RepairPhoto> repairPhotos = repairPhotoService.findByRepairRecordId(repairRecordId, RepairPhoto.REPAIR_PHOTO);

                // 修复协助人
                String[] ids = repairRecord.getSecondaryUserId().split(",");
                StringBuffer sb = new StringBuffer();
                for (String userId : ids) {
                    int id = Integer.parseInt(userId.trim());
                    String name = userService.findUserById(id).getUserName();
                    sb.append(" " + name);
                }
                String assistPersons = sb.toString();

                // 处理图片
                String imagePath = resourceDir + File.separator
                        + "images" + File.separator + siteId + File.separator + relic.getId() + File.separator;
                // 文物照片
                if (relic.getPhotos() != null) {
                    for (Photo photo : relic.getPhotos()) {
                        BufferedImage sourceImg = ImageIO.read(new FileInputStream(imagePath + photo.getPath()));
                        photo.setWidth(TEMPLATE_PHOTO_WIDTH);
                        photo.setHeight(TEMPLATE_PHOTO_WIDTH / sourceImg.getWidth() * sourceImg.getHeight());
                        photo.setBase64(encodeFile(imagePath + photo.getPath()));
                    }
                }
                // 现状相关图像
                for (RepairPhoto photo : quoPhotos) {
                    BufferedImage sourceImg = ImageIO.read(new FileInputStream(imagePath + photo.getPath()));
                    photo.setWidth(QUO_PHOTO_WIDTH);
                    photo.setHeight(QUO_PHOTO_WIDTH / sourceImg.getWidth() * sourceImg.getHeight());
                    photo.setBase64(encodeFile(imagePath + photo.getPath()));
                }

                // 修复后照片
                for (RepairPhoto photo : repairPhotos) {
                    BufferedImage sourceImg = ImageIO.read(new FileInputStream(imagePath + photo.getPath()));
                    photo.setWidth(REPAIR_PHOTO_WIDTH);
                    photo.setHeight(REPAIR_PHOTO_WIDTH / sourceImg.getWidth() * sourceImg.getHeight());
                    photo.setBase64(encodeFile(imagePath + photo.getPath()));
                }

                data.put("r", repairRecord);
                data.put("detections", detectionAnalysisList);
                data.put("drawRegisters", drawRegisters);
                data.put("imageDatums", imageDatums);
                data.put("repairAssessment", repairAssessment);
                data.put("situation", situation);
                data.put("statusQuo", statusQuo);
                data.put("quoPhotos", quoPhotos);
                data.put("repairPhotos", repairPhotos);
                data.put("assistPersons", assistPersons);
                data.put("logs", repairLogs);

                template.process(data, out);

                Client client = new Client(new URL("http://www.microwise-system.com:8080/WebService.asmx?wsdl"));
                String filestr = encodeFile(new FileInputStream(docFile));
                Object[] results = client.invoke("Word2pdf", new Object[]{filestr, "test.doc"});
                if (results != null) {
                    byte[] binaryData = new BASE64Decoder().decodeBuffer(results[0].toString());

                    String pdfFilePath = path + File.separator + repairRecord.getIdentifier() + ".pdf";
                    OutputStream outFile = new FileOutputStream(pdfFilePath);
                    outFile.write(binaryData);
                    outFile.close();
                }
            } catch (TemplateException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String encodeFile(String path) {
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(path);
            data = ByteStreams.toByteArray(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    private String encodeFile(InputStream in) {
        byte[] data = null;
        try {
            data = ByteStreams.toByteArray(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    public List<Scheme> getSchemes() {
        return schemes;
    }

    public void setSchemes(List<Scheme> schemes) {
        this.schemes = schemes;
    }

    public List<Institution> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(List<Institution> institutions) {
        this.institutions = institutions;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public RelicService getRelicService() {
        return relicService;
    }

    public void setRelicService(RelicService relicService) {
        this.relicService = relicService;
    }

    public Relic getRelic() {
        return relic;
    }

    public void setRelic(Relic relic) {
        this.relic = relic;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public List<RepairRecord> getRepairRecords() {
        return repairRecords;
    }

    public void setRepairRecords(List<RepairRecord> repairRecords) {
        this.repairRecords = repairRecords;
    }

    public RepairRecord getRepairRecord() {
        return repairRecord;
    }

    public void setRepairRecord(RepairRecord repairRecord) {
        this.repairRecord = repairRecord;
    }

    public InstitutionRoom getInstitutionRoom() {
        return institutionRoom;
    }

    public void setInstitutionRoom(InstitutionRoom institutionRoom) {
        this.institutionRoom = institutionRoom;
    }

    public List<String> getUserNames() {
        return userNames;
    }

    public void setUserNames(List<String> userNames) {
        this.userNames = userNames;
    }

    public List<RepairReason> getRepairReasons() {
        return repairReasons;
    }

    public void setRepairReasons(List<RepairReason> repairReasons) {
        this.repairReasons = repairReasons;
    }

    public String getNotByReason() {
        return notByReason;
    }

    public void setNotByReason(String notByReason) {
        this.notByReason = notByReason;
    }
}
