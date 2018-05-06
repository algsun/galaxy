package com.microwise.orion.dao;

import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.ImageDatum;

import java.util.List;

/**
 * 影像资料 dao
 *
 * @author liuzhu
 * @date 2015-11-10
 */
public interface ImageDatumDao extends BaseDao<ImageDatum> {

    /**
     * 根据repairRecordId查找
     *
     * @param repairRecordId 修复记录id
     * @return imageDatums集合
     * @author liuzhu
     * @date 2015-11-10
     */
    public List<ImageDatum> findImageDatums(int repairRecordId);

    /**
     * 查找最新的imageDatum TODO 有bug为携带相应参数（repairRecordId）
     *
     * @return imageDatum 实体
     * @author liuzhu
     * @date 2015-11-10
     */
    public ImageDatum findNewImageDatum();

    /**
     * 携带各种bean
     *
     * @param id
     * @return
     */
    public ImageDatum findImageDatum(int id);
}
