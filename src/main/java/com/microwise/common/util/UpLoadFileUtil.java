package com.microwise.common.util;

import com.google.common.io.ByteStreams;
import com.microwise.blackhole.sys.ResourcePaths;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 通用上传下载工具
 *
 * @author: wanggeng
 * @date: 13-8-1 下午3:23
 */
public class UpLoadFileUtil {

    /**
     * 文件下载
     *
     * @param subdirectory 拼接的子目录
     * @param fileName     文件名
     * @return true 下载成功
     *         false 下载失败
     */
    public static boolean fileDownLoad(String subdirectory, String fileName, String systemCode) {
        HttpServletResponse response = ServletActionContext.getResponse();
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            String path = getUploadPath(systemCode) + File.separator + subdirectory;
            String filepath = path + File.separator + fileName;
            File f = new File(filepath);
            Long filelength = f.length();
            response.setHeader("Location", fileName);
            int cacheTime = 10;

            response.setHeader("Cache-Control", "max-age=" + cacheTime);
            response.setContentType("application/octet-stream; charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
            response.setContentLength(filelength.intValue());
            outputStream = response.getOutputStream();
            inputStream = new FileInputStream(f);
            ByteStreams.copy(inputStream, outputStream);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 文件上传
     *
     * @param srcFile
     * @param destFile
     * @return outOfLength 文件大小超过5M
     *         true 上传成功
     *         false 上传失败
     */
    public static String fileUpload(File srcFile, File destFile) {
        //判断上传文件是否大于5M
        if (srcFile.length() > 10485760) {
            return "outOfLength";
        }
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);
            ByteStreams.copy(fis, fos);
            return "true";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "false";
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除服务器上的对应的挂接文档
     *
     * @param fileName     文档名称
     * @param subdirectory 子目录
     */
    public static void deleteFileByFileName(String fileName, String subdirectory, String systemCode) throws Exception {
        String path = getUploadPath(systemCode) + subdirectory;
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.getName().equals(fileName)) {
                        f.delete();
                    }
                }
            }
        }
    }

    /**
     * 获取systemCode的上传目录，以备与下面拼
     * 接不同系统的上传资源子文件夹
     *
     * @return path
     */
    public static String getUploadPath(String systemCode) {
        return ResourcePaths.galaxyResourcesDir(systemCode);
    }
}
