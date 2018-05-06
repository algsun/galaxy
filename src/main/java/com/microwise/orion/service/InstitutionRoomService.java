package com.microwise.orion.service;

import com.microwise.orion.bean.InstitutionRoom;

import java.util.List;

/**
 * Created by Administrator on 15-9-10.
 */
public interface InstitutionRoomService {
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
     * 根据ID查询库房信息
     *
     * @param id
     * @return
     */
    public InstitutionRoom findById(int id);


    /**
     * 查询库房列表
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
