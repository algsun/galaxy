package com.microwise.blueplanet.service;

import com.microwise.blueplanet.bean.po.DimensionalLocationPO;
import com.microwise.blueplanet.bean.po.ThreeDimensionalPO;

import java.util.List;

/**
 * @author 王耕
 * @date 15-6-10
 */
public interface ThreeDimensionalService {

    /**
     * 将模型文件上传路径存入数据库
     *
     * @param threeDimensionalPO 模型文件实体类
     */
    public void saveDimensional(ThreeDimensionalPO threeDimensionalPO);

    /**
     * 查询3d模型列表
     *
     * @param siteId 站点编号
     * @return 模型列表集合
     */
    public List<ThreeDimensionalPO> findThreeDimensionals(String siteId);

    /**
     * 根据ID查询模型文件实体类
     * @param dimensionalId 模型文件ID
     * @return 模型文件实体类
     */
    public ThreeDimensionalPO findThreeDimenById(int dimensionalId);

    /**
     * 根据上传的文件的名称查询模型
     * @param path 路径，也是上传文件的模型名称
     * @return 模型实体类
     */
    public ThreeDimensionalPO findThreeDimenByPath(String path);

    /**
     * 根据ID删除3d模型文件
     * @param dimensionalId 模型文件id
     * @param fileName 根据名称删除服务器上的文件
     */
    public void deleteDimensionalById(int dimensionalId,String fileName);

    /**
     * 修改模型描述
     * @param remark 描述内容
     */
    public void updateDimensionalRemark(String remark,int dimensionalId);

    /**
     * 保存模型与位置点的关系，模型与位置点为一对多的关系
     * @param siteId 站点编号
     * @param dimensionalId 模型的id
     * @param locationIds 位置点的id集合
     */
    public void saveDimensionalLocationRelation(String siteId,int dimensionalId,String[] locationIds);

    /**
     * 查询某模型的所有关联的位置点
     * @param siteId 站点编号
     * @param dimensionalId 模型id
     * @return 集合
     */
    public List<DimensionalLocationPO> findDimensionalLocationRelations(String siteId, int dimensionalId);


    /**
     * 修改模型关联位置点
     *@param  dimensionalId 模型的Id
     *@param  locationIds   位置点的ID集合
     *@param  siteId 站点编号
     * */
    public void updateDimensionalLocationRelation(String siteId,int dimensionalId,String[] locationIds);
 }
