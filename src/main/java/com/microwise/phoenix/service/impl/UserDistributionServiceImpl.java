package com.microwise.phoenix.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.phoenix.bean.po.UserDistributionInfo;
import com.microwise.phoenix.dao.UserDistributionDao;
import com.microwise.phoenix.service.UserDistributionService;
import com.microwise.phoenix.sys.Phoenix;
import com.microwise.uma.util.DateTypeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Phoenix
@Service
@Transactional
public class UserDistributionServiceImpl implements UserDistributionService {

    @Autowired
    private UserDistributionDao userDistributionDao;

    @Override
    public UserDistributionInfo getUserDistributionInfo(String siteId, Date date, int dateType) {
        UserDistributionInfo udbInfo = new UserDistributionInfo();
        // 添加区域信息
        List<Map<String, Object>> districtList = userDistributionDao.getDistrictInfo(siteId);
        List<String> distriList = new ArrayList<String>();
        for (Map<String, Object> map : districtList) {
            distriList.add(map.get("zoneName").toString());
        }
        udbInfo.setDistrictList(distriList);
        // 查询图表数据信息
        long startTime = DateTypeGenerator.start(dateType, date).getTime();
        long endTime = DateTypeGenerator.end(dateType, date).getTime();
        List<List<List<Object>>> chartData = integrateChartData(startTime, endTime, districtList, siteId);
        udbInfo.setData(chartData);
        udbInfo.hasData = !chartData.isEmpty();

        //获取活动密度最高的区域及次数
        Map<String, Object> activeAreaInfo = userDistributionDao.getMaxActiveAreaAndCount(startTime, endTime, siteId);
        if (activeAreaInfo != null) {
            udbInfo.setMaxActiveArea(activeAreaInfo.get("zoneName").toString());
            udbInfo.setMaxActiveAreaCount(Integer.valueOf(activeAreaInfo.get("activeCount").toString()));
        }

        //获取活动最频繁的时段及次数
        Map<String, Object> activeTimeInfo = userDistributionDao.getMaxActiveTimeAndCount(startTime, endTime, siteId);
        if (activeTimeInfo != null) {
            String activeTime = activeTimeInfo.get("currentTime").toString();
            udbInfo.setMaxActiveTime(activeTime);
            udbInfo.setMaxActiveTimeCount(Integer.valueOf(activeTimeInfo.get("activeCount").toString()));

            //获取活动最频繁的时段的区域及次数
            Map<String, Object> activeTimeAreaInfo = userDistributionDao.getMaxActiveTimeAreaAndCount(startTime, endTime, siteId);
            if (activeTimeAreaInfo != null) {
                udbInfo.setMaxActiveTimeArea(activeTimeAreaInfo.get("zoneName").toString());
                udbInfo.setMaxActiveTimeAreaCount(Integer.valueOf(activeTimeAreaInfo.get("activeCount").toString()));
                udbInfo.setMaxActiveTimeRange(activeTimeAreaInfo.get("currentTime").toString());
            }
        }
        return udbInfo;
    }

    @Override
    public Map<String, Object> getUserDistributionInfo(String siteId, int dateType, Date date) {
        UserDistributionInfo userDistributionInfo = getUserDistributionInfo(siteId, date, dateType);
        Map<String, Object> result = Maps.newLinkedHashMap();
        List<Map<String, Object>> distributionData = Lists.newLinkedList();
        List<String> districtList = userDistributionInfo.getDistrictList();
        for (int i = 0; i < districtList.size(); i++) {
            Map<String, Object> data = Maps.newLinkedHashMap();
            String distributionName = districtList.get(i);
            List<Object> seriesData = Lists.newLinkedList();
            for (int j = 0; j < userDistributionInfo.getData().size(); j++) {
                Map<String, Object> point = Maps.newLinkedHashMap();
                point.put("x", userDistributionInfo.getData().get(j).get(i).get(0));
                point.put("y", userDistributionInfo.getData().get(j).get(i).get(1));
                seriesData.add(point);
            }
            data.put("name", distributionName);
            data.put("data", seriesData);
            distributionData.add(data);
        }
        result.put("hasData", userDistributionInfo.isHasData());
        result.put("distributionData", distributionData);
        result.put("maxActiveArea", userDistributionInfo.getMaxActiveArea());
        result.put("maxActiveAreaCount", userDistributionInfo.getMaxActiveAreaCount());
        result.put("maxActiveTime", userDistributionInfo.getMaxActiveTime());
        result.put("maxActiveTimeArea", userDistributionInfo.getMaxActiveTimeArea());
        result.put("maxActiveTimeAreaCount", userDistributionInfo.getMaxActiveTimeAreaCount());
        result.put("maxActiveTimeCount", userDistributionInfo.getMaxActiveTimeAreaCount());
        result.put("maxActiveTimeRange", userDistributionInfo.getMaxActiveTimeRange());
        return result;
    }

    /**
     * 整合图表数据
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     *                  List<List<List<Object>>> 结构，最内层List<Object> 第一个元素表示时间（0~23）
     *                  第二个元素表示统计值，中层List<List<Object>>  表示
     *                  同一时间点按区域顺序排列，最外层  List<List<List<Object>>>
     *                  表示按时间点分组（0~23）排列。
     */
    private List<List<List<Object>>> integrateChartData(long startTime, long endTime,
                                                        List<Map<String, Object>> districtList, String siteId) {
        List<List<List<Object>>> dt = new ArrayList<List<List<Object>>>();
        List<Map<String, Object>> list = userDistributionDao.getUserDistriData(
                startTime, endTime, siteId);
        if (list.isEmpty()) {
            return dt;
        }

        int[] hours = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
                14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
        for (int i : hours) {
            List<List<Object>> dataList = new ArrayList<List<Object>>();
            for (Map<String, Object> map : districtList) {
                boolean isExist = false;
                String zid = map.get("zoneId").toString();
                List<Object> chartDataList = new ArrayList<Object>();
                for (Map<String, Object> data : list) {
                    int count = Integer.valueOf(data.get("count").toString());
                    String zoneId = data.get("zoneId").toString();
                    int currentTime = Integer.parseInt(data.get("currentTime")
                            .toString());
                    if (zoneId.equals(zid) && currentTime == i) {
                        isExist = true;
                        chartDataList.add(currentTime);
                        chartDataList.add(count);
                    }
                }
                // 区域中没有数据的填写0
                if (!isExist) {
                    chartDataList.add(i);
                    chartDataList.add(0);
                }
                dataList.add(chartDataList);
            }
            dt.add(dataList);
        }
        return dt;
    }
}
