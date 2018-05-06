package com.microwise.phoenix.service;

import com.microwise.phoenix.bean.po.UserDistributionInfo;

import java.util.Date;
import java.util.Map;

public interface UserDistributionService {

    public UserDistributionInfo getUserDistributionInfo(String siteId, Date date, int dateType);

    public Map<String, Object> getUserDistributionInfo(String siteId, int dateType, Date date);

}
