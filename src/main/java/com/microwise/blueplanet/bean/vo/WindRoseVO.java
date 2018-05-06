package com.microwise.blueplanet.bean.vo;

import com.microwise.blueplanet.bean.po.WindrosePO;

/**
 * 风向玫瑰图VO
 * 
 * @author zhangpeng
 * @date 2013-1-18
 */
public class WindRoseVO extends WindrosePO {
	
	private static final long serialVersionUID = -310182004801483863L;
	
	/** 格式化后的 时间*/
	private  String  time;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
