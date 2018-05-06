package com.microwise.phoenix.service.impl;

import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.phoenix.bean.po.uma.ActionStat;
import com.microwise.phoenix.dao.ActionStatDao;
import com.microwise.phoenix.service.ActionStatService;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.uma.util.DateTypeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 人员管理：规则统计service 接口实现类
 *
 * @author xu.baoji
 * @check @duan.qixin	2013-7-18 #4567
 * @date 2013-7-17
 */
@Service
@Phoenix
@Transactional
public class ActionStatServiceImpl implements ActionStatService {

    /**
     * 规则统计dao
     */
    @Autowired
    private ActionStatDao actionStatDao;

    @Override
    public List<ActionStat> findActionStat(String siteId, Date date, int type) {
        long startDate = DateTypeGenerator.start(type, date).getTime();
        long endDate = DateTypeGenerator.end(type, date).getTime();
        return actionStatDao.findActionStat(siteId, startDate, endDate);
    }

}
