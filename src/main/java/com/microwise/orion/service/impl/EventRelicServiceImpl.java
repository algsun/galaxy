package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.orion.bean.EventRelic;
import com.microwise.orion.dao.EventRelicDao;
import com.microwise.orion.service.EventRelicService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.vo.OutOrInRelicVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 出库文物 service 实现
 * 
 * @author xubaoji
 * @date 2013-5-29
 *
 * @check 2013-06-05 zhangpeng svn:3965
 * TODO 暂未审核xiedeng代码
 */
@Service 
@Orion
@Transactional
public class EventRelicServiceImpl implements EventRelicService {
	
	/**出库文物dao*/
	@Autowired
	private  EventRelicDao eventRelicDao;
	
	@Override
	public void updateInRelicForHttp(List<OutOrInRelicVo> inRelics) {
		if (inRelics!= null && inRelics.size()>0) {
			eventRelicDao.updateInRelicForHttp(inRelics);
		}
	}
	
	@Override
	public void updateOutRelicForHttp(List<OutOrInRelicVo> outRelics) {
		if (outRelics !=null && outRelics.size()>0) {
			eventRelicDao.updateOutRelicForHttp(outRelics);
		}
	}

    @Override
    public void updateStateById(int id, int state) {
        eventRelicDao.updateStateById(id, state);
    }

    @Override
    public void updateOutDateById(int id,Date date) {
        eventRelicDao.updateOutDateById(id);
    }

    @Override
    public EventRelic findById(int id) {
        return eventRelicDao.findById(id);
    }
}
