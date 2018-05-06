package com.microwise.orion.dao;

import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.OutEventAttachment;

import java.util.Date;
import java.util.List;

/**
 * 出库事件文档记录dao
 *
 * @author xu.baoji
 * @date 2013-8-1
 */
public interface OutEventAttachmentDao extends BaseDao<OutEventAttachment> {

    /**
     * 添加一条出库事件文档记录
     *
     * @param eventId 出库事件id
     * @param userId  上传用户id
     * @param path    文档路径
     * @param date    上传日期
     * @author xu.baoji
     * @date 2013-8-1
     */
    public void addOutEventAttachment(String eventId, int userId, String path, Date date);

    /***
     * 删除 出库事件文档记录
     *
     * @author xu.baoji
     * @date 2013-8-1
     *
     * @param id 出库事件文档记录id
     */
    public void deleteOutEventAttachment(int id);

    /***
     * 通过 出库事件id 查询所有出库事件文档记录
     *
     * @author xu.baoji
     * @date 2013-8-1
     *
     * @param eventId 出库事件id
     * @return List<OutEventAttachment> 出库事件文档记录实体列表
     */
    public List<OutEventAttachment> findByEventId(String eventId);

    /**
     * 通过eventId删除挂接文档
     *
     * @param eventId
     */
    void deleteEventAttachmentByEventId(String eventId);

}
