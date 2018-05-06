package com.microwise.cybertron.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.cybertron.bean.Volume;
import com.microwise.cybertron.dao.VolumeDao;
import com.microwise.cybertron.service.VolumeService;
import com.microwise.cybertron.sys.Cybertron;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 卷 Service 实现
 *
 * @author li.jianfei
 * @date 2014-07-16
 */

@Beans.Service
@Transactional
@Cybertron
public class VolumeServiceImpl implements VolumeService {

    @Autowired
    public VolumeDao volumeDao;

    @Override
    public List<Volume> findVolumeList() {
        List<Volume> volumeList = volumeDao.findVolumeList();
        List<Volume> volumeList1 = new ArrayList<Volume>();// 主卷
        List<Volume> volumeList2 = new ArrayList<Volume>();// 副卷
        List<Volume> volumeList3 = new ArrayList<Volume>();//备考卷

        //将所有卷分类按照主卷、副卷、备考卷
        for (Volume volume : volumeList) {
            if (volume.getVolumeCode() == 1) {
                volumeList1.add(volume);
            } else if (volume.getParentCode() != null && volume.getParentCode() == 1) {
                volumeList1.add(volume);
            } else if (volume.getVolumeCode() == 2) {
                volumeList2.add(volume);
            } else if (volume.getParentCode() != null && volume.getParentCode() == 2) {
                volumeList2.add(volume);
            } else if (volume.getVolumeCode() == 3) {
                volumeList3.add(volume);
            } else if (volume.getParentCode() != null && volume.getParentCode() == 3) {
                volumeList3.add(volume);
            }
        }

        //找出各个卷中的续补卷
        List<Volume> volumes = new ArrayList<Volume>();
        Volume volume1 = new Volume();//主卷-续补卷
        Volume volume2 = new Volume();//副卷-续补卷
        Volume volume3 = new Volume();//备考卷-续补卷
        for (Volume volume : volumeList) {
            if (volume.getVolumeCode() == 10) {
                volume1 = volume;
            } else if (volume.getVolumeCode() == 20) {
                volume2 = volume;
            } else if (volume.getVolumeCode() == 30) {
                volume3 = volume;
            }
        }

        //删除各个分类卷中的续补卷
        volumeList1.remove(volume1);
        volumeList2.remove(volume2);
        volumeList3.remove(volume3);

        //重新添加，这样才能保证续补卷在list的最后一个元素
        volumeList1.add(volume1);
        volumeList2.add(volume2);
        volumeList3.add(volume3);

        //按照主卷→副卷→备考卷顺序排序
        volumes.addAll(volumeList1);
        volumes.addAll(volumeList2);
        volumes.addAll(volumeList3);
        return volumes;

    }

    @Override
    public Volume findVolume(int volumeCode) {
        return volumeDao.findVolume(volumeCode);
    }
}
