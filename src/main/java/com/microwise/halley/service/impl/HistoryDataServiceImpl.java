package com.microwise.halley.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.halley.bean.po.PathPO;
import com.microwise.halley.bean.vo.ExhibitionStateVO;
import com.microwise.halley.bean.vo.TimeIntervalVO;
import com.microwise.halley.dao.ExhibitionStateDao;
import com.microwise.halley.dao.PathDao;
import com.microwise.halley.service.HistoryDataService;
import com.microwise.halley.sys.Halley;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 历史数据 Service 实现
 *
 * @author li.jianfei
 * @date 2013-10-31
 */
@Beans.Service
@Transactional
@Halley
public class HistoryDataServiceImpl implements HistoryDataService {

    @Autowired
    private PathDao pathDao;

    @Autowired
    private ExhibitionStateDao exhibitionStateDao;

    @Override
    public List<TimeIntervalVO> getTimeInterval(int exhibitionId) {

        List<TimeIntervalVO> timeIntervalList = new ArrayList<TimeIntervalVO>();

        // 查询外展路径
        List<PathPO> pathList = exhibitionStateDao.getALLPathPO(exhibitionId);

        // 查询外展状态
        List<ExhibitionStateVO> stateList = exhibitionStateDao.getHistoryState(exhibitionId);

        // 组装时段划分
        Iterator<ExhibitionStateVO> iterator = stateList.iterator();

        // 掐头去尾
        ExhibitionStateVO state;
        while (iterator.hasNext()) {
            state = iterator.next();
            if (state.getState() == 1 || state.getState() == 4) {
                iterator.remove();
            }
        }

        // 拼状态字符串
        TimeIntervalVO timeInterval;
        for (int i = 1; i < pathList.size(); i++) {
            PathPO path = pathList.get(i);
            PathPO lastPath = pathList.get(i - 1);
            timeInterval = new TimeIntervalVO();
            timeInterval.setName(lastPath.getDestinationName() + " - " + path.getDestinationName());
            timeIntervalList.add(timeInterval);

            // 到达目的地时跳过
            if (i < pathList.size() - 1) {
                timeInterval = new TimeIntervalVO();
                timeInterval.setName(path.getDestinationName() + " 陈展中");
                timeIntervalList.add(timeInterval);
            }
        }

        // 匹配状态
        for (int i = 0; i < stateList.size() && i < timeIntervalList.size(); i++) {
            timeInterval = timeIntervalList.get(i);
            state = stateList.get(i);
            timeInterval.setBeginTime(state.getBeginTime());
            timeInterval.setEndTime(state.getEndTime());
        }
        Iterator<TimeIntervalVO> timeIntervalVOIterator = timeIntervalList.iterator();
        while (timeIntervalVOIterator.hasNext()) {
            timeInterval = timeIntervalVOIterator.next();
            if (timeInterval.getBeginTime() == null) {
                timeIntervalVOIterator.remove();
            }
        }
        return timeIntervalList;
    }
}
