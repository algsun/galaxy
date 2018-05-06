package com.microwise.proxima.action.opticsImage;

import com.google.common.collect.LinkedListMultimap;
import com.microwise.proxima.bean.DVPlaceBean;
import com.microwise.proxima.bean.Zone;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 辅助类
 *
 * @author gaohui
 * @date 13-6-25 09:16
 */
class Helper {

    /**
     * 将摄像机列表转化为 zone(同一个区域) => dvPlaces(一个区域下的摄像机点位)
     *
     * @param dvPlaces
     * @return
     */
    public static Map<Zone, Collection<DVPlaceBean>> zoneToDvPlaces(List<? extends DVPlaceBean> dvPlaces){
        LinkedListMultimap<Zone, DVPlaceBean> zoneToDvPlaces = LinkedListMultimap.<Zone, DVPlaceBean>create();

        for(DVPlaceBean dvPlace: dvPlaces){
            zoneToDvPlaces.put(dvPlace.getZone(), dvPlace);
        }

        return zoneToDvPlaces.asMap();
    }
}
