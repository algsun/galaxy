package com.microwise.phoenix.util;

import com.microwise.phoenix.bean.vo.MarkSegmentContrast;

import java.util.Comparator;

/**
 * 比较器，根据MarkSegmentContrast对象的lengthRealDelta比较大小
 *
 * @author zhangpeng
 * @date 13-8-9
 * @check @wang.geng 2013年8月14日 #4949
 */
public class MarkSegmentComparator implements Comparator<MarkSegmentContrast> {

    /**
     * 是否倒序比较
     */
    private boolean isDesc = false;

    /**
     * 标记段对比构造
     */
    public MarkSegmentComparator(boolean isDesc) {
        this.isDesc = isDesc;
    }

    /**
     * 比较器实现
     * 如果两个对象lengthRealDelta相等，返回０；小于关系，返回负数；大于关系，返回正数
     * 注意是否倒叙判断，倒叙结果相反
     */
    @Override
    public int compare(MarkSegmentContrast m1, MarkSegmentContrast m2) {
        float lengthRealDelta1 = m1.getLengthRealDelta();
        float lengthRealDelta2 = m2.getLengthRealDelta();
        if (isDesc) {
            return new Float(lengthRealDelta1).compareTo(lengthRealDelta2);
        } else {
            return new Float(lengthRealDelta2).compareTo(lengthRealDelta1);
        }
    }

}
