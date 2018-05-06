package com.microwise.cybertron.service;

import com.microwise.cybertron.bean.Volume;

import java.util.List;

/**
 * 卷 Service
 *
 * @author li.jianfei
 * @date 2014-07-16
 */
public interface VolumeService {

    /**
     * 查询卷
     *
     * @return volume集合
     * @author liuzhu
     * @date 2014-7-16
     */
    public List<Volume> findVolumeList();

    /**
     * 查询卷
     *
     * @return volume
     * @author xuyuexi
     * @date 2014-7-21
     */
    public Volume findVolume(int volumeCode);
}
