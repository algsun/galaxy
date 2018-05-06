package com.microwise.orion.action.repair;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Strings;
import com.google.common.io.ByteStreams;
import com.google.common.io.Resources;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.UserService;
import com.microwise.blackhole.sys.Sessions;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lijianfei on 15/9/17.
 */
@Beans.Action
@Orion
@Route(value = "orion")
public class PrintAction {
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

    @Autowired
    private CommentService commentService;

    InputStream inputStream;

    private int repairRecordId;

    // 模板文物图片宽度
    public static final double TEMPLATE_PHOTO_WIDTH = 324.7 * 4 / 3; // pt to px

    // 现状相关图像宽度
    public static final double QUO_PHOTO_WIDTH = 166.4 * 4 / 3; // pt to px

    // 修复后照片
    public static final double REPAIR_PHOTO_WIDTH = 447.6 * 4 / 3; // pt to px


    @Route(value = "repairs/process")
    public String process() {
        Relic relic = null;
        String filename = null;
        try {

            String siteId = Sessions.createByAction().currentSiteId();
            //TODO File.separator
            String templatePath = URLDecoder.decode(Resources.getResource("orion/template").getFile(), "utf-8");
            String resourceDir = UpLoadFileUtil.getUploadPath("orion");
            String path = resourceDir + File.separator + "1";

            //TODO test.doc
            String fileName = "temp" + ".doc";
            Configuration configuration = new Configuration();

            configuration.setDirectoryForTemplateLoading(new File(templatePath));
            configuration.setDefaultEncoding("utf-8");
            // 注意，这里使用 Struts 封装的 ObjectWrapper, 同时提供了 map 的支持
            configuration.setObjectWrapper(new StrutsBeanWrapper(true));

            Template template = configuration.getTemplate("repair-archive1.ftl");

            File dir = new File(path);
            if (!dir.exists()) dir.mkdir();
            String docFilePath = path + File.separator + fileName;
            File docFile = new File(docFilePath);
            if (docFile.exists()) {
                docFile.delete();
            }
            Writer out = new OutputStreamWriter(new FileOutputStream(docFile), "UTF-8");


            // 组织数据
            Map<String, Object> data = new HashMap<String, Object>();
            RepairRecord repairRecord = repairRecordService.findRepairRecordById(repairRecordId);
            // 获取文物扩展属性
            relic = relicService.findRelicByRelicId(repairRecord.getRelic().getId(), siteId);
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

            //专家点评
            Comment comment = commentService.findByRepairRecordId(repairRecordId);
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
            data.put("comment", comment);


            template.process(data, out);
            out.close();
            inputStream = new FileInputStream(docFile);
            String url = "http://www.microwise-system.com:8080/WebService.asmx?wsdl";
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            if(HttpURLConnection.HTTP_OK != connection.getResponseCode()){
                return Results.ftl("500-error.jsp");
            }
            Client c = new Client(new URL(url));
            String filestr = encodeFile(inputStream);
            //TODO 改正规代码
            Object[] results = c.invoke("Word2pdf", new Object[]{filestr, "temp.doc"});
            if (results != null) {
                byte[] binaryData = new BASE64Decoder().decodeBuffer(results[0].toString());

                String pdfFilePath = path + File.separator + "temp.pdf";
                OutputStream outFile = new FileOutputStream(pdfFilePath);
                outFile.write(binaryData);
                outFile.close();
                inputStream = new FileInputStream(pdfFilePath);
                filename ="("+repairRecord.getIdentifier()+")"+ java.net.URLEncoder.encode(relic.getName(), "UTF-8") + relic.getTotalCode();
            }else {
                return Results.ftl("500-error.jsp");
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
        return Results.stream().contentType("application/pdf; charset=UTF-8")
                .inputName("inputStream").contentDisposition("attachment;filename=" + filename + ".pdf").done();
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

    public int getRepairRecordId() {
        return repairRecordId;
    }

    public void setRepairRecordId(int repairRecordId) {
        this.repairRecordId = repairRecordId;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
