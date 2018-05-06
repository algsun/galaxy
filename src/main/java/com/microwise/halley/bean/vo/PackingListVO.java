package com.microwise.halley.bean.vo;

import java.util.List;

/**
 * 外展装箱单实体类
 *
 * @author wanggeng
 * @date 2013-09-26
 */
public class PackingListVO {

    /**
     * 序列号id
     */
    private int id;

    /**
     * 装箱单序号
     */
    private int sequence;

    /**
     * 关联的车辆id h_car.id
     */
    private int carId;

    /**
     * 文物列表
     */
    private List<PackingRelicVO> relicList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public List<PackingRelicVO> getRelicList() {
        return relicList;
    }

    public void setRelicList(List<PackingRelicVO> relicList) {
        this.relicList = relicList;
    }
}
