package com.microwise.uma.dao.impl;

import com.google.common.base.Strings;
import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.uma.bean.PersonBean;
import com.microwise.uma.dao.AllotcardDao;
import com.microwise.uma.sys.Uma;
import com.microwise.uma.sys.UmaBaseDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 人员 dao 实现
 *
 * @author xubaoji
 * @date 2013-4-15
 */
@Dao
@Uma
public class AllotcardDaoImpl extends UmaBaseDao implements AllotcardDao {

    @Override
    public List<PersonBean> findPerson(String personName, boolean isHasCard, boolean isLowPower,
                                       Integer pageSize, Integer pageNumber, String siteId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (!Strings.isNullOrEmpty(personName)) {
            paramMap.put("personName", "%" + personName + "%");
        }
        paramMap.put("isHasCard", isHasCard);
        paramMap.put("isLowPower", isLowPower);
        paramMap.put("siteId", siteId);
        paramMap.put("index", (pageNumber - 1) * pageSize);
        paramMap.put("pageSize", pageSize);
        return getSqlSession().selectList(
                "uma.mybatis.AllotcardDao.findPerson", paramMap);
    }

    @Override
    public Integer findPersonCount(String personName, boolean isHasCard, boolean isLowPower, String siteId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (!Strings.isNullOrEmpty(personName)) {
            paramMap.put("personName", "%" + personName + "%");
        }
        paramMap.put("siteId", siteId);
        paramMap.put("isHasCard", isHasCard);
        paramMap.put("isLowPower", isLowPower);
        return getSqlSession().selectOne(
                "uma.mybatis.AllotcardDao.findPersonCount", paramMap);
    }

    @Override
    public List<PersonBean> findAllPersons(int logicGroupId, boolean hasCard) {
        if (hasCard) {
            return getSqlSession().selectList("uma.mybatis.AllotcardDao.findAllPersonsHasCard", logicGroupId);
        } else {
            return getSqlSession().selectList("uma.mybatis.AllotcardDao.findAllPersonsHasNoCard", logicGroupId);
        }
    }

    @Override
    public void sendCardForPerson(Integer personId, String cardSn) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("personId", personId);
        paramMap.put("sn", cardSn);
        getSqlSession().insert("uma.mybatis.AllotcardDao.sendCardForPerson", paramMap);
    }

    @Override
    public void changeCard(Integer personId, String cardSn) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("personId", personId);
        paramMap.put("sn", cardSn);
        getSqlSession().update("uma.mybatis.AllotcardDao.exchangeCard", paramMap);
    }

    @Override
    public void recedeCard(Integer personId) {
        getSqlSession().delete("uma.mybatis.AllotcardDao.recedeCard", personId);

    }

}
