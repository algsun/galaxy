package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Appraisal;
import com.microwise.orion.dao.AppraisalDao;
import com.microwise.orion.service.AppraisalService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 鉴定信息实现类
 *
 * @author liuzhu
 * @date 2016-3-10
 */
@Beans.Service
@Orion
@Transactional
public class AppraisalServiceImpl implements AppraisalService {

    @Autowired
    private AppraisalDao appraisalDao;

    @Override
    public void deleteAppraisal(Appraisal appraisal) {
        appraisalDao.delete(appraisal);
    }
}
