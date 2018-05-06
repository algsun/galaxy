package com.microwise.orion.dao;

import com.microwise.orion.bean.RepairPhoto;

import java.util.List;

/**
 * 文物修复图片管理DAO
 *
 * @author 王耕
 * @date 15-10-22
 */
public interface RepairPhotoDao {
    /**
     * 存图片
     *
     * @param repairPhoto 图片
     */
    public void saveRepairPhoto(RepairPhoto repairPhoto);

    /**
     * 查询修复记录的所有现状图片
     *
     * @param repairRecordId 修复记录
     * @return 图片集合
     */
    public List<RepairPhoto> findByRepairRecordId(int repairRecordId,int type);

    /**
     * 根据id删除文物修复记录图片
     *
     * @param id id
     */
    public void deleteById(int id);

    /**
     * 根据id查询修复记录图片
     *
     * @param id 图片id
     * @return 实体
     */
    public RepairPhoto findById(int id);
}
