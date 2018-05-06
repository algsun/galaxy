package com.microwise.proxima.daemon.infraredImageResolution;

import com.microwise.common.util.UpLoadFileUtil;
import com.microwise.proxima.util.PositionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 红外图片解析类
 * 
 * @author li.jianfei
 * @date 2012-09-05
 * 
 * @check guo.tian li.jianfei 2012-09-19
 * @check li.jianfei liu.zhu 2014-4-15 #8344
 */
@Component
@Scope("prototype")
public class InfraredAnalyzer {

    /**
     * 保留 1 位小数
     */
    private static final int PRECISION = 1;

    /**
     * 获取色轮索引时的上下浮动范围
     */
    private static final int MAX_DELTA = 10;

    /**
     * 从数据库中加载 ColorWheel接口
     */
    @Autowired
    private ColorWheelHolder colorWheelHolder;

	/**
	 * 获取当前点的温度值
	 * 
	 * @param fileSrc
	 *            文件相对路径
	 * @param maxTemp
	 *            图片最高温
	 * @param minTemp
	 *            图片最低温
	 * @param x
	 *            当前点X坐标
	 * @param y
	 *            当前点Y坐标
	 * 
	 * @author zhang.licong
	 * @date 2012-9-20
	 * 
	 *       return double
	 */
	public double findCurrentPointValue(String fileSrc, double maxTemp,
			double minTemp, int x, int y) throws IOException {

        // 加载图片
//        String path = "F:\\apache-tomcat-7.0.52\\webapps\\galaxy-resources\\proxima\\images";
//        File file = new File(path + "/" + fileSrc);
        File file = new File(UpLoadFileUtil.getUploadPath("proxima"+File.separator+"images") + File.separator + fileSrc);
        InputStream inputStream = new FileInputStream(file);
        BufferedImage image = ImageIO.read(inputStream);
        inputStream.close();

        // 根据色轮字典创建色轮图片
        ColorWheel colorWheel = getColorWheel();

        // 获取当前点在色轮中的索引
        int rgb = image.getRGB(x, y);
        int heightOfImageWheel = colorWheel
                .closestHeightOfColor(rgb, MAX_DELTA);
        double currentValue = 0.0;
        if (heightOfImageWheel != -1) {
            // 根据当前索引获取对应的温度
            int imageWheelMaxHeight = colorWheel.getRgbSAverage().length;
            double rate = (imageWheelMaxHeight - heightOfImageWheel)
                    / (float) imageWheelMaxHeight;
            currentValue = rate * (maxTemp - minTemp) + minTemp;
        }

        return PositionUtil.round(currentValue, PRECISION);
	}


	/**
     * 根据色轮字典创建色轮图片
     *
     * @author wangg.geng
     * @date 2014-4-10
     *
     *
     */
    public ColorWheel getColorWheel() {
        return colorWheelHolder.getColorWheel();
    }

}
