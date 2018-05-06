package com.microwise.orion.dao;

import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.Inscription;

import java.util.List;

/**
 * 铭文题跋dao
 * 
 * @author xubaoji
 * @date 2013-5-21
 *
 * @check 2013-06-04 zhangpeng svn:3590
 */
public interface InscriptionDao extends BaseDao<Inscription> {

	/**
	 * 通过id 删除铭文题跋信息
	 * 
	 * @param id
	 *            铭文题跋id编号
	 * 
	 * @author 许保吉
	 * @date 2013-5-21
	 * 
	 * @return void
	 */
	public void deleteInscription(Integer id);

    /**
     * 通过relicId删除铭文提拔
     *
     *
     * @param relicId 文物ID
     * @author 王耕
     * @date 2014-10-20
     */
    public void deleteInscriptionByRelicId(int relicId);
}
