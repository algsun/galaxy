package com.microwise.cybertron.action.record;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.io.ByteStreams;
import com.microwise.blackhole.sys.ResourcePaths;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.Md5;
import com.microwise.cybertron.bean.Attachment;
import com.microwise.cybertron.service.AttachmentService;
import com.microwise.cybertron.sys.Cybertron;
import com.opensymphony.xwork2.Action;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 批量上传文件
 */
@Beans.Action
@Cybertron
@Route("/cybertron")
public class FileAction extends BlueplanetLoggerAction {

    public static final Logger log = LoggerFactory.getLogger(FileAction.class);

    @Autowired
    private AttachmentService attachmentService;

    private File file;

    private String fileFileName;

    private String fileContentType;

    private String attachmentId;

    /**
     * 上传文件
     */
    public String execute() {
        Map<String, Object> map = null;
        try {
            map = new HashMap<String, Object>();
            String path = uploadFile();
            //是否上传成功
            map.put("success", true);
            //上传时保存的名称
            map.put("pathName", path);
            //上传时的真实名称
            map.put("fileName", fileFileName);
//            map.put("userName", Sessions.createByAction().currentUser().getUserName());
        } catch (IOException e) {
            map.put("success", false);
        }
        return Results.json().asRoot(map).done();
    }

    //执行上传功能
    private String uploadFile() throws IOException {
        String dir = null;
        String fileName = null;
        try {
            InputStream in = new FileInputStream(file);
            dir = ResourcePaths.galaxyResourcesDir("cybertron" + File.separator + "file" + File.separator);
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
            //获取上传文件名，这里连路径一起获取了，做处理
            String filePreName = this.getFileFileName().substring(this.getFileFileName().lastIndexOf("\\") + 1);
            //获取上传文件后缀
            String fileNames[] = filePreName.split("\\.");
            //拼接以处理文件名和后缀
            fileName = Md5.getMd5(fileNames[0] + new Date()) + "." + fileNames[fileNames.length-1];
            File uploadFile = new File(dir, fileName);
            OutputStream out = new FileOutputStream(uploadFile);
            byte[] buffer = new byte[1024 * 1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            in.close();
            out.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return fileName;
    }

    @Route("attachments/{attachmentId}/download")
    public String downloadFile() {
        try {
            Attachment attachment = attachmentService.findAttachment(attachmentId);
            fileDownLoad("/file/", attachment.getPath(), attachment.getUploadName());
        } catch (Exception e) {
            log.error("下载附件", e);
        }
        return Action.NONE;
    }

    private boolean fileDownLoad(String subdirectory, String fileName, String uploadName) {
        HttpServletResponse response = ServletActionContext.getResponse();
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            String path = ResourcePaths.galaxyResourcesDir("/cybertron") + subdirectory;
            String filepath = path + File.separator + fileName;
            File f = new File(filepath);
            Long fileLength = f.length();
            response.setHeader("Location", fileName);
            int cacheTime = 10;

            response.setHeader("Cache-Control", "max-age=" + cacheTime);
            response.setContentType("application/octet-stream; charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(uploadName.getBytes("UTF-8"), "ISO-8859-1"));
            response.setContentLength(fileLength.intValue());
            outputStream = response.getOutputStream();
            inputStream = new FileInputStream(f);
            ByteStreams.copy(inputStream, outputStream);
            return true;
        } catch (Exception e) {
            log.error("下载附件", e);
            return false;
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                log.error("下载附件", e);
            }
        }
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }
}
