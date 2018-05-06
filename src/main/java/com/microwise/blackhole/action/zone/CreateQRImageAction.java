package com.microwise.blackhole.action.zone;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blueplanet.sys.BlueplanetLoggerAction;
import com.microwise.common.sys.annotation.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * 二维码生成图片
 *
 * @author xu.yuexi
 * @date 14-1-23
 */
@Beans.Action
@Blackhole
public class CreateQRImageAction extends BlueplanetLoggerAction {
    public static final Logger log = LoggerFactory.getLogger(CreateQRImageAction.class);

    //二维码大小
    private static final int size = 200;

    //input
    /**
     * 要生成的String
     */
    private String qrString;

    /**
     * 图片上添加的文字
     */
    private String qrFront;

    //output
    /**
     * 图片输出流
     */
    private InputStream bigInputStream;

    /**
     * 图片对象
     */
    private BufferedImage imag;


    /**
     * 创建二维码
     */
    @Route(value = "/blackhole/zone/createQRImage", params = {"qrString", "qrFront"})
    public String create() throws IOException {
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        try {
            // qrString要生成二维码的字符串
            String qrCodeText = qrString;
            //返回画出的二维码图片流 qrFront为二维码下面的文字
            bigInputStream = getInputStream(bs, qrCodeText, size, qrFront);
            log("区域管理", "生成二维码");
            return Results.stream().inputName("bigInputStream").done();
        } catch (Exception e) {
            log.error("区域管理", e);
            e.printStackTrace();
            return null;
        } finally {
            bs.close();
        }
    }

    /**
     * 下载二维码
     */
    @Route(value = "/blackhole/zone/downloadQRImage", params = {"qrString", "qrFront"})
    public String downloadImage() throws IOException {
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        try {
            //要生成二维码的字符串
            String qrCodeText = qrString;
            //图片流  qrFront为二维码下面的文字
            bigInputStream = getInputStream(bs, qrCodeText, size, qrFront);
            log("区域管理", "下载二维码");
            return Results.stream()
                    .inputName("bigInputStream")
                    .contentType("application/octet-stream; charset=UTF-8")
                    .contentDisposition("attachment;filename=" + qrFront + ".png")
                    .done();
        } catch (Exception e) {
            log.error("区域管理", e);
            e.printStackTrace();
            return null;
        } finally {
            bs.close();
        }
    }

    /**
     * 返回图片流
     *
     * @param bs
     * @param qrCodeText
     * @param size
     * @param qrFrontText
     * @return bigInputStream图片流
     * @throws WriterException
     * @throws IOException
     */
    private InputStream getInputStream(ByteArrayOutputStream bs, String qrCodeText, int size, String qrFrontText) throws WriterException, IOException {
        imag = createQRImage(qrCodeText, size, qrFrontText);
        ImageIO.write(imag, "png", bs);
        return bigInputStream = new ByteArrayInputStream(bs.toByteArray());
    }


    /**
     * 生成二维码
     *
     * @param qrCodeText
     * @param size
     * @param qrFront
     * @return BufferedImage
     * @throws WriterException
     * @throws IOException
     */
    private BufferedImage createQRImage(String qrCodeText, int size, String qrFront
    ) throws WriterException, IOException {
        // Create the ByteMatrix for the QR-Code that encodes the given String
        Hashtable hintMap = new Hashtable();
        //设定容错率和二维码边距
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hintMap.put(EncodeHintType.MARGIN, 1);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText,
                BarcodeFormat.QR_CODE, size, size, hintMap);
        // Make the BufferedImage that are to hold the QRCode
        int matrixWidth = byteMatrix.getWidth();
        int rows = qrFront.length() / 15 + 1; //图片下字的行数
        int addHeight = rows * 15; //根据字的行数决定图片下空白
        //创建图片对象
        BufferedImage image = new BufferedImage(matrixWidth, matrixWidth + addHeight,
                BufferedImage.TYPE_INT_RGB);
        //开始绘制
        image.createGraphics();
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth + addHeight);
        // Paint and save the image using the ByteMatrix
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        //图片下的字换行
        ArrayList<String> fontsList = new ArrayList<String>();
        //每十五个字换一行
        if (qrFront.length() > 15) {
            //qrFront.length() / 15 + 1行数
            for (int i = 0; i < qrFront.length() / 15 + 1; i++) {
                //总长度大于本行的最后一个字的位置数
                if (qrFront.length() > (i + 1) * 15) {
                    //截取本行应显示的字符串添加进list
                    fontsList.add(qrFront.substring(i * 15, (i + 1) * 15));
                } else {
                    fontsList.add(qrFront.substring(i * 15, qrFront.length() - 1));
                }
            }
        } else {
            fontsList.add(qrFront);
        }
        //绘制文字
        for (int i = 0; i < fontsList.size(); i++) {
            //如果不是最后一行，则不居中
            if (i < fontsList.size() - 1) {
                graphics.drawString(fontsList.get(i), 10, size + 4 + i * 13);
            } else {
                //最后一行居中显示
                graphics.drawString(fontsList.get(i), (size - fontsList.get(i).length() * 12) / 2, size + 4 + i * 13);
            }
        }
        return image;
    }

    public String getQrString() {
        return qrString;
    }

    public void setQrString(String qrString) {
        this.qrString = qrString;
    }

    public InputStream getBigInputStream() {
        return bigInputStream;
    }

    public void setBigInputStream(InputStream bigInputStream) {
        this.bigInputStream = bigInputStream;
    }

    public String getQrFront() {
        return qrFront;
    }

    public void setQrFront(String qrFront) {
        this.qrFront = qrFront;
    }

}
