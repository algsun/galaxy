package com.microwise.proxima.service;

import com.microwise.proxima.bean.InfraredMarkRegionBean;
import org.json.JSONException;

import java.util.List;

/**
 * 红外标记区域 service
 *
 * @author gaohui
 * @date 2012-9-5
 * @check li.jianfei liu.zhu 2014-4-15 #8322
 */
public interface InfraredMarkRegionService {

	/**
	 * 
	 * 保存
	 * 
	 * @param markRegion
	 *            新的标记区域
	 * @return 标记区域ID
	 */
	public String save(InfraredMarkRegionBean markRegion);

	/**
	 * 删除标记区域和标记区域数据
	 * 
	 * @param markRegionId
	 *            标记区域ID
	 */
	public void deleteWithMarkRegionDatas(String markRegionId);

    /**
     * <pre>
     * TODO 未验证
     * </pre>
     * 查询所有标记区域
     *
     * @return 所有标记区域列表
     * @author li.jianfei
     * @date 2012-09-10
     */
    public List<InfraredMarkRegionBean> findAll();

    /**
     * 返回摄像机点位下的所有标记区域
     *
     * @param dvPlaceId 摄像机点位ID
     * @return
     */
    public List<InfraredMarkRegionBean> findAllByDVPlaceId(String  dvPlaceId);

	/**
	 * 批量更新标记区域的位置和宽高
	 * 
	 * @param markRegionsJson
	 * @throws JSONException
	 */
	public void updateMarkRegions(String markRegionsJson) throws JSONException;

}
