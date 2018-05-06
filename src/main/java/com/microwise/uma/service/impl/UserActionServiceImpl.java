package com.microwise.uma.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.uma.bean.AbstractUserActionBean;
import com.microwise.uma.bean.RuleBean;
import com.microwise.uma.bean.UserActionBean;
import com.microwise.uma.bean.UserCensusActionBean;
import com.microwise.uma.dao.RuleDao;
import com.microwise.uma.dao.UserActionDao;
import com.microwise.uma.service.UserActionService;
import com.microwise.uma.sys.Uma;
import com.microwise.uma.util.DateTypeGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 用户行为规则
 *
 * @author gaohui
 * @date 13-4-26 16:12
 * @check li.jianfei  2013-5-6 #3123
 */
@Beans.Service
@Uma
public class UserActionServiceImpl implements UserActionService {

    @Autowired
    private RuleDao ruleDao;
    @Autowired
    private UserActionDao userActionDao;

    @Override
    public int findCount(int userId, Date startTime, Date endTime) {
        return userActionDao.findCount(userId, startTime, endTime);
    }

    @Override
    public int findCountByUserId(int userId, Date startTime, Date endTime) {
        return userActionDao.findCountByUserId(userId, startTime.getTime(), endTime.getTime());
    }

    @Override
    public List<AbstractUserActionBean> findUserActions(int userId, Date startTime, Date endTime, boolean order, int index, int size) {
        int start = (index - 1) * size;

        return userActionDao.findUserActions(userId, startTime.getTime(), endTime.getTime(), order, start, size);
    }

    @Override
    public int findCountByRuleId(int ruleId, Date startTime, Date endTime) {
        RuleBean rule = ruleDao.findRuleById(ruleId);

        switch (rule.getType()) {
            // 单程, 往, 返
            case RuleBean.TYPE_SINGLE:
            	return userActionDao.findSingleCountByRuleId(ruleId, startTime.getTime(), endTime.getTime());
            case RuleBean.TYPE_GO:
            case RuleBean.TYPE_BACK:
                return userActionDao.findGoOrBackCountByRuleId(ruleId, startTime.getTime(), endTime.getTime());
            // 往返
            case RuleBean.TYPE_CENSUS:
                return userActionDao.findCensusCountByRuleId(ruleId, startTime.getTime(), endTime.getTime());
            default:
                return 0;
        }
    }

    @Override
    public int findCountByRuleId(int ruleId, int dateType, Date date) {
        Date startTime = DateTypeGenerator.start(dateType, date);
        Date endTime = DateTypeGenerator.end(dateType, date);

        return this.findCountByRuleId(ruleId, startTime, endTime);
    }

    @Override
    public List<UserActionBean> findSingleActionsByRuleId(int ruleId, int dateType, Date date, int index, int size, boolean order) {
        Date startTime = DateTypeGenerator.start(dateType, date);
        Date endTime = DateTypeGenerator.end(dateType, date);
        
        RuleBean bean = ruleDao.findRuleById(ruleId);
        boolean goBack = false;
        if(bean.getType() != 1){
        	goBack = true;
        } 
        return userActionDao.findSingleActionsByRuleId(ruleId, startTime.getTime(), endTime.getTime(), (index - 1) * size, size, order , goBack);
    }

    @Override
    public List<UserActionBean> findSingleActionsByRuleId(int ruleId, Date startTime, Date endTime, int index, int size, boolean order) {
    	  RuleBean bean = ruleDao.findRuleById(ruleId);
          boolean goBack = false;
          if(bean.getType() != 1){
          	goBack = true;
          } 
        return userActionDao.findSingleActionsByRuleId(ruleId, startTime.getTime(), endTime.getTime(), (index - 1) * size, size, order , goBack);
    }

    @Override
    public List<UserCensusActionBean> findCensusActionsByRuleId(int ruleId, int dateType, Date date, int index, int size, boolean order) {
        Date startTime = DateTypeGenerator.start(dateType, date);
        Date endTime = DateTypeGenerator.end(dateType, date);

        return userActionDao.findCensusActionsByRuleId(ruleId, startTime.getTime(), endTime.getTime(), (index - 1) * size, size, order);
    }

    @Override
    public List<UserCensusActionBean> findCensusActionsByRuleId(int ruleId, Date startTime, Date endTime, int index, int size, boolean order) {
        return userActionDao.findCensusActionsByRuleId(ruleId, startTime.getTime(), endTime.getTime(), (index - 1) * size, size, order);
    }
}
