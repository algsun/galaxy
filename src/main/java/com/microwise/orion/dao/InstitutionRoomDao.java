package com.microwise.orion.dao;

import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.InstitutionRoom;

import java.util.List;

/**
 * Created by Administrator on 15-9-10.
 */
public interface InstitutionRoomDao extends BaseDao<InstitutionRoom> {

    /**
     * 添加单位
     *
     * @param institutionRoom
     */
    public void add(InstitutionRoom institutionRoom);

    /**
     * 删除
     *
     * @param institutionRoom
     */
    public void deleteInstitutionRoom(InstitutionRoom institutionRoom);


    /**
     * 查询一个对象
     *
     * @param id
     * @return
     */
    public List<InstitutionRoom> findByInstitutionId(int id);

    /**
     * 根据单位id删除库房
     *
     * @param id
     */
    public void deleteByInstitutionId(int id);

}
