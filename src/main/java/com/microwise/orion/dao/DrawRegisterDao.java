package com.microwise.orion.dao;

import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.DrawRegister;

import java.util.List;

/**
 * 绘图登记 dao
 *
 * @author liuzhu
 * @date 2015-11-10
 */
public interface DrawRegisterDao extends BaseDao<DrawRegister>{

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
     * 查找最近的一条记录
     *
     * @return drawRegister实体
     * @author liuzhu
     * @date 2015-11-10
     */
    public DrawRegister findNewDrawRegister(int repairRecordId);

    /**
     * 携带属性
     * @param id
     * @return
     */
    public DrawRegister findDrawRegister(int id);
}
