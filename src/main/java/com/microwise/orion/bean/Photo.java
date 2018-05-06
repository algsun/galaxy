package com.microwise.orion.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * 文物相片实体对象
 *
 * @author xubaoji
 * @date 2013-5-13
 * @check 2013-06-04 zhangpeng svn:3499
 */
public class Photo {

    /**
     * 类型标识
     */
    public static final String PHOTO = "photo";

    /**
     * 相片id编号
     */
    private Integer id;

    /**
     * 文物信息
     */
    @JsonIgnore
    private Relic relic;

    /**
     * 底片号
     */
    private String filmCode;

    /**
     * 拍照人
     */
    private String photographer;

    /**
     * 相片拍照日期
     */
    private Date photoDate;

    /**
     * 相片比例
     */
    private String ratio;

    /**
     * 相片存储路径
     */
    @Expose
    private String path;

    // base64
    private String base64;

    // 显示宽度和高度，需要计算，非数据库字段
    private double width;
    private double height;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Relic getRelic() {
        return relic;
    }

    public void setRelic(Relic relic) {
        this.relic = relic;
    }

    public String getFilmCode() {
        return filmCode;
    }

    public void setFilmCode(String filmCode) {
        this.filmCode = filmCode;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public Date getPhotoDate() {
        return photoDate;
    }

    public void setPhotoDate(Date photoDate) {
        this.photoDate = photoDate;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
