package com.microwise.proxima.service.impl;

import com.microwise.proxima.bean.InfraredMarkRegionBean;
import com.microwise.proxima.dao.InfraredMarkRegionDao;
import com.microwise.proxima.dao.InfraredMarkRegionDataDao;
import com.microwise.proxima.service.InfraredMarkRegionService;
import com.microwise.proxima.sys.Proxima;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * InfraredMarkRegionService 实现
 * 
 * @author gaohui
 * @date 2012-9-10
 */
@Service
@Transactional
@Proxima
public class InfraredMarkRegionServiceImpl implements InfraredMarkRegionService {

	@Autowired
	private InfraredMarkRegionDao markRegionDao;
	@Autowired
	private InfraredMarkRegionDataDao markRegionDataDao;


	@Override
	public String save(InfraredMarkRegionBean markRegion) {
		return (String) markRegionDao.save(markRegion);
	}

	@Override
	public void deleteWithMarkRegionDatas(String markRegionId) {
		InfraredMarkRegionBean markRegion = markRegionDao
				.findById(markRegionId);
		// 删除对应的标记区域数据
		markRegionDataDao.deleteOfMarkRegion(markRegionId);

		markRegionDao.delete(markRegion);
	}

	@Override
	public void updateMarkRegions(String markRegionsJson) throws JSONException {
		JSONArray markRegions = new JSONArray(markRegionsJson);
		for (int i = 0; i < markRegions.length(); i++) {
			JSONObject markRegion = markRegions.getJSONObject(i);
			// 更新标记区域
			markRegionDao.updateById(markRegion.getString("id"),
					markRegion.getInt("x"), markRegion.getInt("y"),
					markRegion.getInt("width"), markRegion.getInt("height"));

			// 删除标记区域数据
			markRegionDataDao.deleteOfMarkRegion(markRegion.getString("id"));
		}
	}

	
	@Override
	public List<InfraredMarkRegionBean> findAll() {
		return markRegionDao.findAll();
	}

    @Override
    public List<InfraredMarkRegionBean> findAllByDVPlaceId(String dvPlaceId) {
        return markRegionDao.findAllByDVPlaceId(dvPlaceId);
    }

}
