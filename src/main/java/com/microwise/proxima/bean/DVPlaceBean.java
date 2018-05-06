package com.microwise.proxima.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

/**
 * 摄像机点位
 *
 * @author gaohui
 * @date 2012-7-6
 */
@JsonIgnoreProperties({"zone","ftp"})
public class DVPlaceBean {
    /**
     * 摄像机点位比较器, ID 倒序
     *
     * @author gaohui
     * @date 2012-09-17
     */
    public static class DVPlaceComparator implements Comparator<DVPlaceBean>, Serializable {
        /**
         * 实现Serializable接口,当比较器被用来构建一个有序的集合,如一个TreeMap，那么TreeMap将是可序列化的
         */
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(DVPlaceBean o1, DVPlaceBean o2) {
            return o2.getCreateTime().compareTo(o1.getCreateTime());
        }
    }

    /**
     * ID
     */
    private String id;

    /**
     * 相机类型
     */
    private int dvType;


    /**
     * 所属区域
     */
    private Zone zone;

    /**
     * 点位编号
     */
    private String placeCode;

    /**
     * 点位名称
     */
    private String placeName;

    /**
     * 摄像机添加时间
     */
    private Date createTime;

    /**
     * 摄像机IP
     */
    private String dvIp;

    /**
     * 图片实景宽度， 单位(mm 毫米)
     */
    private float imageRealWidth;

    /**
     * 图片宽度,单位像素
     */
    private int imageWidth;

    /**
     * 图片高度,单位像素
     */
    private int imageHeight;

    /**
     * FTP 信息
     */
    private FTPProfile ftp;

    /**
     * 是否启用
     */
    private boolean enable;

    /**
     * 备注
     */
    private String remark;

    /**
     * 最后一张照片 for encke
     */
    private String lastPictureUrl;

    /**
     * 实景图
     */
    private String realmap;

    /**
     * 摄像机在区域平面图x轴坐标
     */
    private Float coordinateX = new Float(-1);

    /**
     * 摄像机在区域平面图y轴坐标
     */
    private Float coordinateY = new Float(-1);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public String getPlaceCode() {
        return placeCode;
    }

    public void setPlaceCode(String placeCode) {
        this.placeCode = placeCode;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDvIp() {
        return dvIp;
    }

    public void setDvIp(String dvIp) {
        this.dvIp = dvIp;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }
    public FTPProfile getFtp() {
        return ftp;
    }

    public void setFtp(FTPProfile ftp) {
        this.ftp = ftp;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public float getImageRealWidth() {
        return imageRealWidth;
    }

    public void setImageRealWidth(float imageRealWidth) {
        this.imageRealWidth = imageRealWidth;
    }

    public String getLastPictureUrl() {
        return lastPictureUrl;
    }

    public void setLastPictureUrl(String lastPictureUrl) {
        this.lastPictureUrl = lastPictureUrl;
    }

    public String getRealmap() {
        return realmap;
    }

    public void setRealmap(String realmap) {
        this.realmap = realmap;
    }

    public Float getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(Float coordinateX) {
        this.coordinateX = coordinateX;
    }

    public Float getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(Float coordinateY) {
        this.coordinateY = coordinateY;
    }

    public int getDvType() {
        return dvType;
    }

    public void setDvType(int dvType) {
        this.dvType = dvType;
    }
}