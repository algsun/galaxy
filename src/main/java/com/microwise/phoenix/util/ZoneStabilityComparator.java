package com.microwise.phoenix.util;

import com.microwise.phoenix.bean.vo.ZoneStability;

import java.util.Comparator;

/**
 * 比较器，根据ZoneStability对象的stability比较大小
 *
 * @author zhangpeng
 * @date 13-8-8
 * @check @wang.geng 2013年8月14日 #4949
 */
public class ZoneStabilityComparator implements Comparator<ZoneStability> {

    /**
     * 是否倒序比较
     */
    private boolean isDesc = false;

    /**
     * 区域稳定性比较器构造函数，需要传参，是正序还是倒叙比较
     */
    public ZoneStabilityComparator(boolean isDesc) {
        this.isDesc = isDesc;
    }

    /**
     * 比较器实现，如果两个对象stability相等，返回０；小于关系，返回负数；大于关系，返回正数。
     */
    @Override
    public int compare(ZoneStability z1, ZoneStability z2) {
        float stability1 = z1.getStability();
        float stability2 = z2.getStability();
        if (isDesc) {
            return new Float(stability1).compareTo(stability2);
        } else {
            return new Float(stability2).compareTo(stability1);
        }
    }
}
