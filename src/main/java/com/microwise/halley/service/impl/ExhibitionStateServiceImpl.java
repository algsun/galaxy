package com.microwise.halley.service.impl;

import com.microwise.blackhole.bean.User;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.halley.bean.po.PathPO;
import com.microwise.halley.bean.vo.ExhibitionStateVO;
import com.microwise.halley.dao.ExhibitionDao;
import com.microwise.halley.dao.ExhibitionStateDao;
import com.microwise.halley.service.ExhibitionStateService;
import com.microwise.halley.sys.Halley;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 外展状态Service实现
 *
 * @author xu.yuexi
 * @date 2013-10-21
 * @check @wang.geng li.jianfei 2013-10-25 #6164
 */
@Beans.Service
@Transactional
@Halley
public class ExhibitionStateServiceImpl implements ExhibitionStateService {

    @Autowired
    private ExhibitionDao exhibitionDao;

    @Autowired
    private ExhibitionStateDao exhibitionStateDao;

    @Override
    public ExhibitionStateVO findCurrentState(int exhibitionId) {
        return exhibitionStateDao.findCurrentState(exhibitionId);
    }

    @Override
    public List<ExhibitionStateVO> getHistoryState(int exhibitionId) {

        // 获取历史状态
        List<ExhibitionStateVO> exhibitionStateVOs = exhibitionStateDao.getHistoryState(exhibitionId);
        // 获取预设线路
        List<PathPO> pathPOList = exhibitionStateDao.getALLPathPO(exhibitionId);

        // 匹配历史状态目的地、操作人
        List<PathPO> pathPOListTemp = new ArrayList<PathPO>();
        for (int i = 1; i < pathPOList.size(); i++) {
            pathPOListTemp.add(pathPOList.get(i));
            pathPOListTemp.add(pathPOList.get(i));
        }

        for (int i = 0; i < exhibitionStateVOs.size(); i++) {
            ExhibitionStateVO exhibitionStateVO = exhibitionStateVOs.get(i);
            if (i < pathPOListTemp.size()) {
                exhibitionStateVO.setPathPO(pathPOListTemp.get(i));
            }

            // 设置状态操作人
            int operatorId = exhibitionStateVO.getOperator();
            User user = findUser(operatorId);
            exhibitionStateVO.setUser(user);
        }
        return exhibitionStateVOs;
    }

    @Override
    public void addExhibitionState(int exhibitionId, int state, int operator) {

        ExhibitionStateVO exhibitionState = new ExhibitionStateVO();
        exhibitionState.setExhibitionId(exhibitionId);
        exhibitionState.setState(state);
        exhibitionState.setOperator(operator);
        exhibitionState.setBeginTime(new Date());

        // 状态为 开始外展 时，设置开始时间和结束时间，并更新外展记录开始时间
        if (state == 1) {
            exhibitionState.setEndTime(exhibitionState.getBeginTime());
            exhibitionDao.updateBeginTime(exhibitionId, exhibitionState.getBeginTime());
        }// 状态为 结束外展 时，更新外展记录结束时间
        if (state == 4) {
            exhibitionDao.updateEndTime(exhibitionId, exhibitionState.getBeginTime());
        }
        exhibitionStateDao.addState(exhibitionState);

        // 处理外展状态结束时间
        alterHistoryItemEndTime(exhibitionId, exhibitionState.getBeginTime());
    }

    @Override
    public void alterHistoryItemEndTime(int exhibitionId, Date endTime) {
        exhibitionStateDao.alterHistoryItemEndTime(exhibitionId, endTime);
    }

    @Override
    public PathPO getStartDestination(int exhibitionId) {
        return exhibitionStateDao.getStartDestination(exhibitionId);
    }

    @Override
    public User findUser(int id) {
        return exhibitionStateDao.findUser(id);
    }


    @Override
    public List<PathPO> getALLPathPO(int exhibitionId) {
        return exhibitionStateDao.getALLPathPO(exhibitionId);
    }

    @Override
    public Date findExhibitionBeginTime(int exhibitionId) {
        return exhibitionStateDao.findExhibitionBeginTime(exhibitionId);
    }

}
