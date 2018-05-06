package com.microwise.blueplanet.bean.vo;

import com.microwise.blueplanet.bean.po.DimensionalLocationPO;

/**
 * @author 王耕
 * @date 15-6-16
 */
public class DimensionalLocationVO extends DimensionalLocationPO {

    private LocationDataVO locationDataVO;

    public LocationDataVO getLocationDataVO() {
        return locationDataVO;
    }

    public void setLocationDataVO(LocationDataVO locationDataVO) {
        this.locationDataVO = locationDataVO;
    }
}
