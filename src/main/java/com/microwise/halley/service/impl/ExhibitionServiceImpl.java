package com.microwise.halley.service.impl;

import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.halley.bean.vo.ExhibitionVO;
import com.microwise.halley.dao.ExhibitionDao;
import com.microwise.halley.service.ExhibitionService;
import com.microwise.halley.sys.Halley;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 外展 Service 实现
 *
 * @author li.jianfei
 * @date 2013-10-08
 * @check @wang.geng #5847 2013-10-10
 */
@Service
@Transactional
@Halley
public class ExhibitionServiceImpl implements ExhibitionService {

    @Autowired
    private ExhibitionDao exhibitionDao;

    @Override
    public List<ExhibitionVO> findExhibitionList(String siteId, int state) {
        // 同步外展记录
        exhibitionDao.syncExhibitions(siteId);
        return exhibitionDao.findExhibitionList(siteId, state);
    }

    @Override
    public List<ExhibitionVO> findExhibitionListNotEnd(String siteId) {
        exhibitionDao.syncExhibitions(siteId);
        return exhibitionDao.findExhibitionListNotEnd(siteId);
    }

    @Override
    public List<ExhibitionVO> findExhibitionList(String siteId, int state, int page, int pageSize, Date startDate, Date endDate) {
        // 同步外展记录
        exhibitionDao.syncExhibitions(siteId);
        // 查询外展记录
        List<ExhibitionVO> exhibitionVOList = exhibitionDao.findExhibitionList(siteId, state);
        //按照状态和时间筛选，如果为准备中就按照预计开始时间查询如果是已开始按照实际开始时间查询
        // todo 数据库分页
        removeListByTime(startDate, endDate, exhibitionVOList);
        if (exhibitionVOList.size() > page * pageSize - 1) {
            exhibitionVOList = exhibitionVOList.subList((page - 1) * pageSize, page * pageSize);
        } else {
            exhibitionVOList = exhibitionVOList.subList((page - 1) * pageSize, exhibitionVOList.size());
        }
        return exhibitionVOList;
    }

    @Override
    public int findExhibitionListCount(String siteId, int state, Date startDate, Date endDate) {
        // 同步外展记录
        exhibitionDao.syncExhibitions(siteId);
        // 查询外展记录
        List<ExhibitionVO> exhibitionVOList = exhibitionDao.findExhibitionListCount(siteId, state);
        //按照状态和时间筛选，如果为准备中就按照预计开始时间查询如果是已开始按照实际开始时间查询
        removeListByTime(startDate, endDate, exhibitionVOList);
        return exhibitionVOList.size();
    }

    @Override
    public ExhibitionVO findExhibition(int exhibitionId) {
        return exhibitionDao.findExhibition(exhibitionId);
    }

    @Override
    public void finishExhibition(int exhibitionId, int operatorId) {
        // 更新结束时间
        exhibitionDao.updateEndTime(exhibitionId);
    }

    private void removeListByTime(Date startDate, Date endDate, List<ExhibitionVO> exhibitionVOList) {
        Iterator iterator = exhibitionVOList.iterator();
        while (iterator.hasNext()) {
            ExhibitionVO exhibition = (ExhibitionVO) iterator.next();
            if (exhibition.getState() == 0) {
                if (exhibition.getEstimatedBeginTime().before(startDate) ||
                        exhibition.getEstimatedBeginTime().after(endDate)) {
                    iterator.remove();
                }
            } else if (exhibition.getBeginTime().before(startDate) ||
                    exhibition.getBeginTime().after(endDate)) {
                iterator.remove();
            }
        }
    }
}
