package com.microwise.halley.bean.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据对象
 *
 * @author wang.geng
 * @date 2013-10-10
 */
public class NodeInfoPO implements Serializable {

	private static final long serialVersionUID = 135754419031288743L;

	/**
	 * column m_nodeinfo.nodeid 产品入网唯一标识
	 */
	protected String nodeId;

	/**
	 * column m_nodeinfo.nodeType 1：节点 2：中继 3:节点-主模块(可控) 4:节点-从模块(可控) 7：网关
	 */
	protected Integer nodeType;

	/**
	 * column m_nodeinfo.nodeName 节点名称
	 */
	protected String nodeName;

	/**
	 * column m_nodeinfo.createTime 节点创建时间或更新时间，与原add_time字段合并 （记录生成后不可修改）
	 */
	protected Date createTime;

	/**
	 * column m_nodeinfo.X X轴坐标
	 */
	protected Integer x;

	/**
	 * column m_nodeinfo.Y Y轴坐标
	 */
	protected Integer y;

	/**
	 * column m_nodeinfo.Z Z轴坐标
	 */
	protected Integer z;

	/**
	 * column m_nodeinfo.roomid 监测区域(所属房间号) 默认为NULL，NULL或“”均表示未部署
	 */
	protected String roomid;

	/**
	 * column m_nodeinfo.siteId 设备对应站点
	 */
	protected String siteId;

	/**
	 * column m_nodeinfo.deviceImage 系统相对路径和名称
	 */
	protected String deviceImage;

	/**
	 * column m_nodeinfo.dataVersion 数据版本
	 */
	protected Long dataVersion;

	public NodeInfoPO() {
		super();
	}

	public NodeInfoPO(String nodeId, String roomid, Long dataVersion) {
		this.nodeId = nodeId;
		this.roomid = roomid;
		this.dataVersion = dataVersion;
	}

	public NodeInfoPO(String nodeId, String nodeName, String roomid,
                      String deviceImage, Long dataVersion) {
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.roomid = roomid;
		this.deviceImage = deviceImage;
		this.dataVersion = dataVersion;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public Integer getNodeType() {
		return nodeType;
	}

	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getZ() {
		return z;
	}

	public void setZ(Integer z) {
		this.z = z;
	}

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getDeviceImage() {
		return deviceImage;
	}

	public void setDeviceImage(String deviceImage) {
		this.deviceImage = deviceImage;
	}

	public Long getDataVersion() {
		return dataVersion;
	}

	public void setDataVersion(Long dataVersion) {
		this.dataVersion = dataVersion;
	}

	@Override
	public String toString() {
		return "NodeinfoDO [nodeid=" + nodeId + ", nodeType=" + nodeType
				+ ", nodeName=" + nodeName + ", createTime=" + createTime
				+ ", x=" + x + ", y=" + y + ", z=" + z + ", roomid=" + roomid
				+ ", siteId=" + siteId + ", deviceImage=" + deviceImage
				+ ", dataVersion=" + dataVersion + "]";
	}

}