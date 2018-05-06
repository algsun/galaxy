package com.microwise.blueplanet.bean.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据对象
 * 
 * @since 2013-01-07
 */
public class WindrosePO implements Serializable {

	private static final long serialVersionUID = 135754419185905331L;

	/**
	 * column m_windrose.id 唯一标识uuid
	 */
	private String id;

	/**
	 * column m_windrose.nodeid 产品入网唯一标识
	 */
	private String nodeId;

	/**
	 * column m_windrose.O_N 北风风向统计值
	 */
	private double oN;

	/**
	 * column m_windrose.S_N 北风风速平均值
	 */
	private double sN;

	/**
	 * column m_windrose.O_NNE 东北偏北风风向统计值
	 */
	private double oNne;

	/**
	 * column m_windrose.S_NNE 东北偏北风风速平均值
	 */
	private double sNne;

	/**
	 * column m_windrose.O_NE 东北风风向统计值
	 */
	private double oNe;

	/**
	 * column m_windrose.S_NE 东北风风速平均值
	 */
	private double sNe;

	/**
	 * column m_windrose.O_ENE 东北偏东风风向统计值
	 */
	private double oEne;

	/**
	 * column m_windrose.S_ENE 东北偏东风风速平均值
	 */
	private double sEne;

	/**
	 * column m_windrose.O_E 东风风向统计值
	 */
	private double oE;

	/**
	 * column m_windrose.S_E 东风风速平均值
	 */
	private double sE;

	/**
	 * column m_windrose.O_ESE 东南偏东风风向统计值
	 */
	private double oEse;

	/**
	 * column m_windrose.S_ESE 东南偏东风风速平均值
	 */
	private double sEse;

	/**
	 * column m_windrose.O_SE 东南风风向统计值
	 */
	private double oSe;

	/**
	 * column m_windrose.S_SE 东南风风速平均值
	 */
	private double sSe;

	/**
	 * column m_windrose.O_SSE 东南偏南风风向统计值
	 */
	private double oSse;

	/**
	 * column m_windrose.S_SSE 东南偏南风风速平均值
	 */
	private double sSse;

	/**
	 * column m_windrose.O_S 南风风向统计值
	 */
	private double oS;

	/**
	 * column m_windrose.S_S 南风风速平均值
	 */
	private double sS;

	/**
	 * column m_windrose.O_SSW 西南偏南风风向统计值
	 */
	private double oSsw;

	/**
	 * column m_windrose.S_SSW 西南偏南风风速平均值
	 */
	private double sSsw;

	/**
	 * column m_windrose.O_SW 西南风风向统计值
	 */
	private double oSw;

	/**
	 * column m_windrose.S_SW 西南风风速平均值
	 */
	private double sSw;

	/**
	 * column m_windrose.O_WSW 西南偏西风风向统计值
	 */
	private double oWsw;

	/**
	 * column m_windrose.S_WSW 西南偏西风风速平均值
	 */
	private double sWsw;

	/**
	 * column m_windrose.O_W 西风风向统计值
	 */
	private double oW;

	/**
	 * column m_windrose.S_W 西风风速平均值
	 */
	private double sW;

	/**
	 * column m_windrose.O_WNW 西北偏西风风向统计值
	 */
	private double oWnw;

	/**
	 * column m_windrose.S_WNW 西北偏西风风速平均值
	 */
	private double sWnw;

	/**
	 * column m_windrose.O_NW 西北风风向统计值
	 */
	private double oNw;

	/**
	 * column m_windrose.S_NW 西北风风速平均值
	 */
	private double sNw;

	/**
	 * column m_windrose.O_NNW 西北偏北风风向统计值
	 */
	private double oNnw;

	/**
	 * column m_windrose.S_NNW 西北偏北风风速平均值
	 */
	private double sNnw;

	/**
	 * column m_windrose.windcalm 风速小于0.2m/s时为静风
	 */
	private double windcalm;

	/**
	 * column m_windrose.sum 当天风向总数
	 */
	private Integer sum;

	/**
	 * column m_windrose.ms_date 日期
	 */
	private Date msDate;

	/**
	 * column m_windrose.season 季度
	 */
	private Integer season;

	/**
	 * column m_windrose.isupdate 是否更新
	 */
	private Integer isupdate;

	/**
	 * column m_windrose.dataVersion 数据同步版本
	 */
	private Long dataversion;

	public WindrosePO() {
		super();
	}

	/**
	 * getter for Column m_windrose.id
	 */
	public String getId() {
		return id;
	}

	/**
	 * setter for Column m_windrose.id
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * getter for Column m_windrose.nodeid
	 */
	public String getNodeId() {
		return nodeId;
	}

	/**
	 * setter for Column m_windrose.nodeid
	 * 
	 * @param nodeId
	 */
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public double getoN() {
		return oN;
	}

	public void setoN(double oN) {
		this.oN = oN;
	}

	public double getsN() {
		return sN;
	}

	public void setsN(double sN) {
		this.sN = sN;
	}

	public double getoNne() {
		return oNne;
	}

	public void setoNne(double oNne) {
		this.oNne = oNne;
	}

	public double getsNne() {
		return sNne;
	}

	public void setsNne(double sNne) {
		this.sNne = sNne;
	}

	public double getoNe() {
		return oNe;
	}

	public void setoNe(double oNe) {
		this.oNe = oNe;
	}

	public double getsNe() {
		return sNe;
	}

	public void setsNe(double sNe) {
		this.sNe = sNe;
	}

	public double getoEne() {
		return oEne;
	}

	public void setoEne(double oEne) {
		this.oEne = oEne;
	}

	public double getsEne() {
		return sEne;
	}

	public void setsEne(double sEne) {
		this.sEne = sEne;
	}

	public double getoE() {
		return oE;
	}

	public void setoE(double oE) {
		this.oE = oE;
	}

	public double getsE() {
		return sE;
	}

	public void setsE(double sE) {
		this.sE = sE;
	}

	public double getoEse() {
		return oEse;
	}

	public void setoEse(double oEse) {
		this.oEse = oEse;
	}

	public double getsEse() {
		return sEse;
	}

	public void setsEse(double sEse) {
		this.sEse = sEse;
	}

	public double getoSe() {
		return oSe;
	}

	public void setoSe(double oSe) {
		this.oSe = oSe;
	}

	public double getsSe() {
		return sSe;
	}

	public void setsSe(double sSe) {
		this.sSe = sSe;
	}

	public double getoSse() {
		return oSse;
	}

	public void setoSse(double oSse) {
		this.oSse = oSse;
	}

	public double getsSse() {
		return sSse;
	}

	public void setsSse(double sSse) {
		this.sSse = sSse;
	}

	public double getoS() {
		return oS;
	}

	public void setoS(double oS) {
		this.oS = oS;
	}

	public double getsS() {
		return sS;
	}

	public void setsS(double sS) {
		this.sS = sS;
	}

	public double getoSsw() {
		return oSsw;
	}

	public void setoSsw(double oSsw) {
		this.oSsw = oSsw;
	}

	public double getsSsw() {
		return sSsw;
	}

	public void setsSsw(double sSsw) {
		this.sSsw = sSsw;
	}

	public double getoSw() {
		return oSw;
	}

	public void setoSw(double oSw) {
		this.oSw = oSw;
	}

	public double getsSw() {
		return sSw;
	}

	public void setsSw(double sSw) {
		this.sSw = sSw;
	}

	public double getoWsw() {
		return oWsw;
	}

	public void setoWsw(double oWsw) {
		this.oWsw = oWsw;
	}

	public double getsWsw() {
		return sWsw;
	}

	public void setsWsw(double sWsw) {
		this.sWsw = sWsw;
	}

	public double getoW() {
		return oW;
	}

	public void setoW(double oW) {
		this.oW = oW;
	}

	public double getsW() {
		return sW;
	}

	public void setsW(double sW) {
		this.sW = sW;
	}

	public double getoWnw() {
		return oWnw;
	}

	public void setoWnw(double oWnw) {
		this.oWnw = oWnw;
	}

	public double getsWnw() {
		return sWnw;
	}

	public void setsWnw(double sWnw) {
		this.sWnw = sWnw;
	}

	public double getoNw() {
		return oNw;
	}

	public void setoNw(double oNw) {
		this.oNw = oNw;
	}

	public double getsNw() {
		return sNw;
	}

	public void setsNw(double sNw) {
		this.sNw = sNw;
	}

	public double getoNnw() {
		return oNnw;
	}

	public void setoNnw(double oNnw) {
		this.oNnw = oNnw;
	}

	public double getsNnw() {
		return sNnw;
	}

	public void setsNnw(double sNnw) {
		this.sNnw = sNnw;
	}

	public double getWindcalm() {
		return windcalm;
	}

	public void setWindcalm(double windcalm) {
		this.windcalm = windcalm;
	}

	/**
	 * getter for Column m_windrose.sum
	 */
	public Integer getSum() {
		return sum;
	}

	/**
	 * setter for Column m_windrose.sum
	 * 
	 * @param sum
	 */
	public void setSum(Integer sum) {
		this.sum = sum;
	}

	/**
	 * getter for Column m_windrose.ms_date
	 */
	public Date getMsDate() {
		return msDate;
	}

	/**
	 * setter for Column m_windrose.ms_date
	 * 
	 * @param msDate
	 */
	public void setMsDate(Date msDate) {
		this.msDate = msDate;
	}

	/**
	 * getter for Column m_windrose.season
	 */
	public Integer getSeason() {
		return season;
	}

	/**
	 * setter for Column m_windrose.season
	 * 
	 * @param season
	 */
	public void setSeason(Integer season) {
		this.season = season;
	}

	/**
	 * getter for Column m_windrose.isupdate
	 */
	public Integer getIsupdate() {
		return isupdate;
	}

	/**
	 * setter for Column m_windrose.isupdate
	 * 
	 * @param isupdate
	 */
	public void setIsupdate(Integer isupdate) {
		this.isupdate = isupdate;
	}

	/**
	 * getter for Column m_windrose.dataVersion
	 */
	public Long getDataversion() {
		return dataversion;
	}

	/**
	 * setter for Column m_windrose.dataVersion
	 * 
	 * @param dataversion
	 */
	public void setDataversion(Long dataversion) {
		this.dataversion = dataversion;
	}

	@Override
	public String toString() {
		return "WindroseDO{" + "id='" + id + '\'' + ", nodeId='" + nodeId
				+ '\'' + ", oN=" + oN + ", sN=" + sN + ", oNne=" + oNne
				+ ", sNne=" + sNne + ", oNe=" + oNe + ", sNe=" + sNe
				+ ", oEne=" + oEne + ", sEne=" + sEne + ", oE=" + oE + ", sE="
				+ sE + ", oEse=" + oEse + ", sEse=" + sEse + ", oSe=" + oSe
				+ ", sSe=" + sSe + ", oSse=" + oSse + ", sSse=" + sSse
				+ ", oS=" + oS + ", sS=" + sS + ", oSsw=" + oSsw + ", sSsw="
				+ sSsw + ", oSw=" + oSw + ", sSw=" + sSw + ", oWsw=" + oWsw
				+ ", sWsw=" + sWsw + ", oW=" + oW + ", sW=" + sW + ", oWnw="
				+ oWnw + ", sWnw=" + sWnw + ", oNw=" + oNw + ", sNw=" + sNw
				+ ", oNnw=" + oNnw + ", sNnw=" + sNnw + ", windcalm="
				+ windcalm + ", sum=" + sum + ", msDate=" + msDate
				+ ", season=" + season + ", isupdate=" + isupdate
				+ ", dataversion=" + dataversion + '}';
	}
}