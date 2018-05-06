package com.microwise.orion.service;

import com.microwise.orion.bean.Inscription;

/**
 * 铭文题跋 service
 * 
 * @author xubaoji
 * @date 2013-5-21
 *
 * @check 2013-06-04 zhangpeng svn:3590
 */
public interface InscriptionService {

	/**
	 * 添加 铭文题跋 信息
	 * 
	 * @param inscription
	 *            铭文题跋实体对象
	 * 
	 * @author 许保吉
	 * @date 2013-5-21
	 * 
	 * @return void
	 */
	public void addInscription(Inscription inscription);

    /**
     * 根据id查询铭文题跋
     * @param id 铭文题跋id
     *
     * @author zhangpeng
     * @date 2013-6-4
     *
     * @return Inscription 铭文题跋对象
     */
    public Inscription findById(Integer id);


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
     * @param relicId 文物ID
     * @author 王耕
     * @date 2014-10-20
     */
    public void deleteInscriptionByRelicId(int relicId);

}
