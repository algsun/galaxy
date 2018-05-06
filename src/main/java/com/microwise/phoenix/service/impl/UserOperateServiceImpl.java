package com.microwise.phoenix.service.impl;

import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.phoenix.bean.vo.SubsystemOperate;
import com.microwise.phoenix.bean.vo.UserOperate;
import com.microwise.phoenix.dao.UserOperateDao;
import com.microwise.phoenix.service.UserOperateService;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.uma.util.DateTypeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户操作记录统计 service 实现
 *
 * @author xu.baoji
 * @date 2013-8-19
 * @check @li.jianfei 2013.09.02 #5223
 */
@Phoenix
@Service
@Transactional
public class UserOperateServiceImpl implements UserOperateService {

    /**
     * 用户操作记录统计dao
     */
    @Autowired
    private UserOperateDao userOperateDao;

    @Override
    public List<UserOperate> findUserOperates(String email, Date date, int dateType, int size) {
        Date startDate = DateTypeGenerator.start(dateType, date);
        Date endDate = DateTypeGenerator.end(dateType, date);
        List<UserOperate> userOperates = userOperateDao.findUserFunctions(email, startDate,
                endDate, size);
        for (UserOperate userOperate : userOperates) {
            userOperate.setOperates(handleUserOperates(userOperateDao.findUserOperate(email,
                    userOperate.getFunctionName(), startDate, endDate), "operate", "count"));
        }
        return userOperates;
    }

    @Override
    public List<SubsystemOperate> findSubsystemOperate(int logicGroupId, Date date, int dateType,
                                                       int size) {
        Date startDate = DateTypeGenerator.start(dateType, date);
        Date endDate = DateTypeGenerator.end(dateType, date);
        List<SubsystemOperate> subsystemOperates = userOperateDao.findSubsystem();
        for (SubsystemOperate subsystemOperate : subsystemOperates) {
            subsystemOperate.setOperates(handleUserOperates(userOperateDao.findSubsystemOperate(
                    subsystemOperate.getSubsystemId(), logicGroupId, startDate, endDate, size),
                    "userName", "number"));
        }
        return subsystemOperates;
    }

    /***
     * 处理用户 操作统计数据
     *
     * @param operates
     * @author xu.baoji
     * @date 2013-8-19
     */
    private Map<String, Integer> handleUserOperates(List<Map<String, Object>> operates, String key,
                                                    String value) {
        Map<String, Integer> operateMap = new LinkedHashMap<String, Integer>();
        for (Map<String, Object> map : operates) {
            String keyData = (String) map.get(key);
            Long valueData = (Long) map.get(value);
            operateMap.put(keyData, valueData.intValue());
        }
        return operateMap;
    }

}
