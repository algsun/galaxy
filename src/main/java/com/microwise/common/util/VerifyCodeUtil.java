package com.microwise.common.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 公用验证码生成类
 * 
 * @author zhangpeng
 * @date 2012-11-2
 */
public class VerifyCodeUtil {

	/** 验证码图片的宽度 */
	private static final int WIDTH = 75;

	/** 验证码图片的高度 */
	private static final int HEIGHT = 28;

	/** 验证码字符个数 */
	private static final int CODE_COUNT = 4;

	/** 干扰线数量 */
	private static final int INTERFERENCE_LINES = 5;

	/** 干扰线宽度 */
	private static final float INTERFERENCE_LINES_WIDTH = 1.5f;

	/** 字体高度 */
	private static int fontHeight;

	private static int x = 0;

	private static int codeY;

	/**
	 * 随机抽取数组中的元素作为验证码，可以用String.Compare(String, String,
	 * Boolean)方法判断验证码，boolean为true时忽略大小写，若字符串匹配，返回0
	 */
	private static final String[] CODE_SEQUENCE = { "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i",
			"j", "k", "l", "m", "n", "p", "q", "r", "s", "t", "u", "v", "w",
			"x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
			"K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
			"Y", "Z" };

	/**
	 * 随机产生验证码
	 * 
	 * @return
	 */
	public static String createVerifyCode() {
		StringBuilder sb = new StringBuilder();
		Random rand = new Random();
		for (int i = 0; i < CODE_COUNT; i++) {
			int num = rand.nextInt(CODE_SEQUENCE.length);
			sb.append(CODE_SEQUENCE[num]);
		}
		return sb.toString();
	}

	/**
	 * 验证码生成类
	 * 
	 * @param out
	 *            out没有close
	 * @return 验证码HashMap
	 * @author zhangpeng
	 * @date 2012-11-2
	 */
	public static String createVerifyCode(OutputStream out) throws IOException {
		x = WIDTH / (CODE_COUNT + 1);
		fontHeight = HEIGHT - 2;
		codeY = HEIGHT - 4;

		// 定义图像buffer
		BufferedImage buffImg = new BufferedImage(WIDTH, HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();
		createImage(g);
		// 这里从StringBuffer改为StringBuilder，如果我们的程序是在单线程下运行，或者是不必考虑到线程同步问题，我们应该优先使用StringBuilder类；当然，如果要保证线程安全，自然非StringBuffer莫属了。
		StringBuilder sb = new StringBuilder();
		// 随机产生CODE_COUNT位数的验证码。
		createString(sb, g);
		createLines(g);

		// 图片output出去
		ImageIO.write(buffImg, "JPEG", out);
		// 返回四位的验证码。
		return sb.toString();
	}

	/**
	 * 给定范围获得随机颜色
	 * 
	 * @param fc
	 * @param bc
	 * @return
	 * @author he.ming
	 * @date Sep 19, 2012
	 */
	public static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	/**
	 * 生成图片底板
	 * 
	 * @param g
	 *            渲染对象
	 * @author zhangpeng
	 * @date 2012-11-2
	 */
	public static void createImage(Graphics2D g) {

		// 将图像填充为白色
		g.setColor(getRandColor(220, 250));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		// 画边框。
		g.setColor(getRandColor(50, 160));
		g.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);
	}

	/**
	 * 生成图片验证码
	 * 
	 * @param g
	 *            渲染对象
	 * @author zhangpeng
	 * @date 2012-11-2
	 */
	public static void createString(StringBuilder sb, Graphics2D g) {

		// randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
		Random rand = new Random();

		// 创建字体，字体的大小应该根据图片的高度来定。
		Font font = new Font("Fixedsys", Font.HANGING_BASELINE, fontHeight);
		// 设置字体、设置线宽度
		g.setFont(font);

		for (int i = 0; i < CODE_COUNT; i++) {
			int num = rand.nextInt(CODE_SEQUENCE.length);
			// 得到随机产生的验证码数字。
			String randStr = CODE_SEQUENCE[num].toString();
			// 用随机产生的颜色将验证码绘制到图像中。
			g.setColor(getRandColor(0, 150));
			int startOffset = 5;
			g.drawString(randStr, (i * x) + startOffset, codeY);
			// 将产生的四个随机数组合在一起。
			sb.append(randStr);
		}
	}

	/**
	 * 图片添加干扰线
	 * 
	 * @param g
	 *            渲染对象
	 * @author zhangpeng
	 * @date 2012-11-2
	 */
	public static void createLines(Graphics2D g) {

		// 创建一个随机数生成器类
		Random random = new Random();

		// 随机产生5条干扰线，使图象中的认证码不易被其它程序探测到。
		g.setColor(getRandColor(10, 20));
		for (int i = 0; i < INTERFERENCE_LINES; i++) {
			int x = random.nextInt(WIDTH);
			int y = random.nextInt(HEIGHT);
			int width = 5 + random.nextInt(15);
			int height = 5 + random.nextInt(15);
			g.setStroke(new BasicStroke(INTERFERENCE_LINES_WIDTH));
			g.drawLine(x, y, x + width, y + height);
		}
	}

}
