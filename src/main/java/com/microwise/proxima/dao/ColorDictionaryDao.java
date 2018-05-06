package com.microwise.proxima.dao;

import com.microwise.proxima.bean.ColorDictionaryBean;
import com.microwise.proxima.dao.base.BaseDao;

import java.util.List;

/**
 * 
 * @author YZ-LJF
 * 
 */
public interface ColorDictionaryDao extends BaseDao<ColorDictionaryBean> {

	/**
	 * <pre>
	 * TODO 未验证
	 * </pre>
	 * 
	 * 获取所有色轮字典表数据
	 * 
	 * @return 色轮数据集合
	 * @author li.jianfei
	 * @date 2012-09-04
	 */
	public List<ColorDictionaryBean> findAll();

	/**
	 * <pre>
	 * TODO 未验证
	 * </pre>
	 * 
	 * 获取字典表记录总数
	 * 
	 * @return 数据行数
	 * @author li.jianfei
	 * @date 2012-09-04
	 */
	public int getCount();

}
