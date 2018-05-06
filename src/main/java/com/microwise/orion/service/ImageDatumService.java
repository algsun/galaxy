package com.microwise.orion.service;

import com.microwise.orion.bean.ImageDatum;

import java.util.List;

/**
 * 影像资料service
 *
 * @author liuzhu
 * @date 2015-11-10
 */
public interface ImageDatumService {

    /**
     * 添加 imageDatum
     *
     * @param imageDatum 实体
     * @author liuzhu
     * @date 2015-11-10
     */
    public void save(ImageDatum imageDatum);

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
     * 根据id查找 imageDatum
     *
     * @param id
     * @return imageDatum
     * @author liuzhu
     * @date 2015-11-10
     */
    public ImageDatum findById(int id);

    /**
     * 更新
     *
     * @param imageDatum
     * @author liuzhu
     * @date 2015-11-10
     */
    public void update(ImageDatum imageDatum);

    /**
     * 删除
     *
     * @param imageDatum
     * @author liuzhu
     * @date 2015-11-10
     */
    public void delete(ImageDatum imageDatum);

    /**
     * 携带各种bean
     *
     * @param id
     * @return
     */
    public ImageDatum findImageDatum(int id);

}
