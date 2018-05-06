package com.microwise.common.util;

import java.math.BigDecimal;

/**
 * 对数据处理工具类
 * 
 * @author xu.baoji
 * @date 2013-8-28
 */
public class MathUtil {

	/***
	 * 获得一个 float 数值 保留 bit 位后的四舍五入值
	 * 
	 * @author xu.baoji
	 * @date 2013-8-28
	 * 
	 * @param f
	 *            要四舍五入的 float 值
	 * @param bit
	 *            要保留的小数点后的位数
	 * @return float 四舍五入后的值
	 */
	public static float roundFloat(float f, int bit) {
		BigDecimal bd = new BigDecimal(f);
		return bd.setScale(bit, BigDecimal.ROUND_HALF_UP).floatValue();
	}

}
