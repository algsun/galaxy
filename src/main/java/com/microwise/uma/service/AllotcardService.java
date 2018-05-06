package com.microwise.uma.service;

import com.microwise.uma.bean.PersonBean;

import java.util.List;

/**
 * 发卡 service 接口
 *
 * @author xubaoji
 * @date 2013-4-22
 */
public interface AllotcardService {

	/**
	 * 查询人员信息
	 *
	 * @param personName
	 *            人员名称
	 * @param isHasCard
	 *            是否有卡 true 有卡人员， false 无卡人员
	 * @param isLowPower
	 *            是否只查询低电量电子卡：默认为查询所有电子卡，默认值为false;如果为true,则查询电压小于10%的电子卡
	 * @param pageSize
	 *            分页单位
	 * @param pageNumber
	 *            当前页码
	 * @param siteId
	 *            站点id
	 *
	 * @author 许保吉
	 * @date 2013-4-22
	 *
	 * @return List<PersonBean> 人员信息列表
	 */
	public List<PersonBean> findPerson(String personName, boolean isHasCard,
			boolean isLowPower, Integer pageSize, Integer pageNumber,
			String siteId);

	/**
	 * 查询人员 数量
	 *
	 * @param personName
	 *            人员名称
	 * @param isHasCard
	 *            是否有卡
	 * @param isLowPower
	 *            是否只查询低电量电子卡：默认为查询所有电子卡，默认值为false;如果为true,则查询电压小于10%的电子卡
	 * @param siteId
	 *            站点编号
	 *
	 * @author 许保吉
	 * @date 2013-4-22
	 *
	 * @return count 人员数量
	 */
	public Integer findPersonCount(String personName, boolean isHasCard,
			boolean isLowPower, String siteId);

	/**
	 * 为人员发卡
	 *
	 * @param personId
	 *            人员id
	 * @param cardSn
	 *            电子卡编号
	 * @return void
	 * @author 许保吉
	 * @date 2013-4-22
	 */
	public void sendCardForPerson(Integer personId, String cardSn);

	/**
	 * 退卡
	 *
	 * @param personId
	 *            人员编号
	 * @return void
	 * @author 许保吉
	 * @date 2013-4-22
	 */
	public void recedeCard(Integer personId);

	/**
	 * 为人员换卡
	 *
	 * @param personId
	 *            人员id
	 * @param cardSn
	 *            新电子卡sn
	 * @return void
	 * @author 许保吉
	 * @date 2013-4-22
	 */
	public void changeCard(Integer personId, String cardSn);

	/**
	 * 查询所有的人员
	 *
	 * @param logicGroupId
	 * @param hasCard
	 *            是否有卡
	 * @return
	 */
	List<PersonBean> findAllPersons(int logicGroupId, boolean hasCard);
}
