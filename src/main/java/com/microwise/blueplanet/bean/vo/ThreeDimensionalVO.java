package com.microwise.blueplanet.bean.vo;

import com.microwise.blueplanet.bean.po.ThreeDimensionalPO;

import java.util.List;

/**
 * @author xiedeng
 * @date 15-6-15
 */
public class ThreeDimensionalVO extends ThreeDimensionalPO {
    private List<LocationVO> locationVOs;

    public List<LocationVO> getLocationVOs() {
        return locationVOs;
    }

    public void setLocationVOs(List<LocationVO> locationVOs) {
        this.locationVOs = locationVOs;
    }
}
