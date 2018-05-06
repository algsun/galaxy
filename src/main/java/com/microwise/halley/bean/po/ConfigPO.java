package com.microwise.halley.bean.po;

/**
 * 其它参数配置实体类
 *
 * @author wanggeng
 * @date 13-9-25 下午3:49
 */
public class ConfigPO {

    /** 序列号id */
    private int id;

    /** 关联的外展ID h_exhibition.id */
    private int exhibitionId;

    /** 报警范围 */
    private int alarmRange;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(int exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public int getAlarmRange() {
        return alarmRange;
    }

    public void setAlarmRange(int alarmRange) {
        this.alarmRange = alarmRange;
    }
}
