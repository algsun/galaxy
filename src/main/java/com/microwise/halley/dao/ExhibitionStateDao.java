package com.microwise.halley.dao;

import com.microwise.blackhole.bean.User;
import com.microwise.halley.bean.po.PathPO;
import com.microwise.halley.bean.vo.ExhibitionStateVO;

import java.util.Date;
import java.util.List;

/**
 * <p/>
 * 外展状态 Dao
 *
 * @author li.jianfei
 * @date 2013-09-29
 * @check @wang.geng li.jianfei 2013-10-25 #6164
 */
public interface ExhibitionStateDao {

    /**
     * 添加外展状态
     *
     * @param exhibitionState 状态 对象
     */
    public void addState(ExhibitionStateVO exhibitionState);

    /**
     * 获取某次外展的当前状态
     *
     * @param exhibitionId 外展ID
     * @return 外展状态PO
     */
    public ExhibitionStateVO findCurrentState(int exhibitionId);


    /**
     * 获取某次外展的历史操作h
     *
     * @param exhibitionId 外展ID
     * @return 状态的历史操作集合
     */
    public List<ExhibitionStateVO> getHistoryState(int exhibitionId);

    /**
     * 获取出发地
     *
     * @param exhibitionId 外展ID
     * @return PathPO   目的地对象
     */
    public PathPO getStartDestination(int exhibitionId);


    /**
     * 修改上一条记录的结束时间
     *
     * @param exhibitionId 外展ID
     * @param endTime      外展状态结束时间
     */
    public void alterHistoryItemEndTime(int exhibitionId, Date endTime);

    /**
     * 根据id查人员
     *
     * @param id 人员 ID
     * @return User 人员对象
     */
    public User findUser(int id);

    /**
     * 查找所有路径
     *
     * @param exhibitionId 外展Id
     * @return 所有路径集合
     */
    public List<PathPO> getALLPathPO(int exhibitionId);

    /**
     * 获取外展开始时，第一次开始运输的开始时间作为开始时间
     *
     * @param exhibitionId 外展ID
     * @return 外展开始时间
     * @author wang.geng
     * @date 2013-10-25
     */
    public Date findExhibitionBeginTime(int exhibitionId);
}
