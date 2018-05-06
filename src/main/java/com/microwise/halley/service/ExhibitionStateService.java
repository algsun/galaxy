package com.microwise.halley.service;

import com.microwise.blackhole.bean.User;
import com.microwise.halley.bean.po.PathPO;
import com.microwise.halley.bean.vo.ExhibitionStateVO;

import java.util.Date;
import java.util.List;

/**
 * 外展状态Service
 *
 * @author xu.yuexi
 * @date 2013-10-21
 * @check @wang.geng li.jianfei 2013-10-25 #6162
 */
public interface ExhibitionStateService {

    /**
     * 获取某次外展的当前状态
     *
     * @param exhibitionId 外展ID
     * @return 外展状态PO
     */
    public ExhibitionStateVO findCurrentState(int exhibitionId);

    /**
     * 获取某次外展的历史操作
     *
     * @param exhibitionId 当前目的地Id
     * @return 状态的历史操作集合
     */
    public List<ExhibitionStateVO> getHistoryState(int exhibitionId);

    /**
     * 添加状态
     *
     * @param exhibitionId 外展ID
     * @param state        状态
     * @param operator     操作人ID
     */
    public void addExhibitionState(int exhibitionId, int state, int operator);

    /**
     * 修改上一条记录的结束时间
     *
     * @param exhibitionId 外展ID
     * @param endTime      结束时间
     */
    public void alterHistoryItemEndTime(int exhibitionId, Date endTime);

    /**
     * 获取出发地
     *
     * @param exhibitionId 外展ID
     * @return PathPO
     */
    public PathPO getStartDestination(int exhibitionId);


    /**
     * 根据ID查找用户
     */
    public User findUser(int id);

    /**
     * 获取外展的开始时间
     *
     * @param exhibitionId 外展ID
     * @return 开始时间
     */
    public Date findExhibitionBeginTime(int exhibitionId);


    /**
     * 查找所有路径
     *
     * @param exhibitionId 外展Id
     * @return 所有路径集合
     */
    public List<PathPO> getALLPathPO(int exhibitionId);

}
