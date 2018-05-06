package com.microwise.orion.service;

import com.microwise.orion.bean.DrawRegister;

import java.util.List;

/**
 * 绘图登记service
 *
 * @author liuzhu
 * @date 2015-11-10
 */
public interface DrawRegisterService {

    /**
     * 保存
     *
     * @param drawRegister 实体
     * @author liuzhu
     * @date 2015-11-10
     */
    public void save(DrawRegister drawRegister);

    /**
     * 根据repairId查询
     *
     * @param repairRecordId 修复记录id
     * @return drawRegisters集合
     * @author liuzhu
     * @date 2015-11-10
     */
    public List<DrawRegister> findDrawRegisters(int repairRecordId);

    /**
     * 更新
     *
     * @param drawRegister 实体
     * @author liuzhu
     * @date 2015-11-10
     */
    public void update(DrawRegister drawRegister);

    /**
     * 根据id查找
     *
     * @param id
     * @return drawRegister 实体
     * @author liuzhu
     * @date 2015-11-10
     */
    public DrawRegister findById(int id);

    /**
     * 查找最近的一条记录
     *
     * @return drawRegister实体
     * @author liuzhu
     * @date 2015-11-10
     */
    public DrawRegister findNewDrawRegister(int repairRecordId);

    /**
     * 删除
     *
     * @param drawRegister
     * @author liuzhu
     * @date 2015-11-10
     */
    public void delete(DrawRegister drawRegister);

    /**
     * 携带属性
     *
     * @param id
     * @return
     */
    public DrawRegister findDrawRegister(int id);


}
