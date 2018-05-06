package com.microwise.proxima.service.impl;

import com.google.common.base.Strings;
import com.microwise.common.sys.Constants;
import com.microwise.common.util.DateTimeUtil;
import com.microwise.common.util.GalaxyIdUtil;
import com.microwise.proxima.bean.*;
import com.microwise.proxima.dao.OpticsDVPlaceDao;
import com.microwise.proxima.dao.PhotographScheduleDao;
import com.microwise.proxima.service.OpticsDVPlaceService;
import com.microwise.proxima.sys.Proxima;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

/**
 * 光学摄像机点位 service 实现
 * 
 * @author gaohui
 * @date 2012-7-10
 * 
 * @check zhang.licong 2012-07-14
 */
@Service
@Transactional
@Proxima
public class OpticsDVPlaceServiceImpl implements OpticsDVPlaceService {

	@Autowired
	private OpticsDVPlaceDao opticsDVPlaceDao;

	@Autowired
	private PhotographScheduleDao photographScheduleDao;

	@Override
	public List<OpticsDVPlaceBean> findByZoneId(String zoneId, int pageNmb,
			int pageSize) {
		return opticsDVPlaceDao.findByZoneId(zoneId, pageNmb, pageSize);
	}

	@Override
	public List<OpticsDVPlaceBean> findByZoneId(String zoneId) {
		return opticsDVPlaceDao.findByZoneId(zoneId);
	}

	@Override
	public int findByZoneIdCount(String zoneId) {
		return opticsDVPlaceDao.findByZoneIdCount(zoneId);
	}

	@Override
	public OpticsDVPlaceBean findById(String id) {
		return opticsDVPlaceDao.findById(id);
	}

	@Override
	public void update(OpticsDVPlaceBean opticsDVPlace) {
		opticsDVPlaceDao.update(opticsDVPlace);
	}

	@Override
	public void update(OpticsDVPlaceBean dvPlace, String everydayPeriod,
			String everydayPoint, String sevendayPeriod, String sevendayPoint,
			String radioType) throws Exception {
		// 修改点位信息
		opticsDVPlaceDao.update(dvPlace);
		// 删除拍照计划数据
		List<PhotographScheduleBean> psList = photographScheduleDao
				.findAllOfDVPlace(dvPlace.getId());
		for (PhotographScheduleBean psBean : psList) {
			photographScheduleDao.delete(psBean);
		}
		refreshPhotographSchecduler(dvPlace, everydayPeriod, everydayPoint,
				sevendayPeriod, sevendayPoint, radioType);
	}

	@Override
	public List<OpticsDVPlaceBean> findAll(String siteId) {
		return opticsDVPlaceDao.findAll(siteId);
	}

	@Override
	public List<OpticsDVPlaceBean> findAll(String siteId, int pageNmb,
			int pageSize) {
		return opticsDVPlaceDao.findAll(siteId, pageNmb, pageSize);
	}

	@Override
	public int findAllCount(String siteId) {
		return opticsDVPlaceDao.findAllCount(siteId);
	}

	/**
	 * @author zhang.licong
	 * @throws Exception
	 * @update 2012-8-7
	 */
	@Override
	public void save(OpticsDVPlaceBean dvPlace, String everydayPeriod,
			String everydayPoint, String sevendayPeriod, String sevendayPoint,
			String radioType) throws Exception {
		// 保存点位信息
		opticsDVPlaceDao.save(dvPlace);

		// 充值拍照计划
		refreshPhotographSchecduler(dvPlace, everydayPeriod, everydayPoint,
				sevendayPeriod, sevendayPoint, radioType);
	}

	@Override
	public void save(OpticsDVPlaceBean dvPlace) {
		opticsDVPlaceDao.save(dvPlace);
	}

	/**
	 * 判断是星期几？
	 *
	 * @param sevenDayValue
	 *            值
	 * @author zhang.licong
	 * @date 2012-8-9
	 */
	public DayType judgmentDayOfWeek(String sevenDayValue) {
		int week = Integer.parseInt(sevenDayValue);
		DayType dayType = null;
		switch (week) {
		case 1:
			dayType = DayType.MON;
			break;
		case 2:
			dayType = DayType.TUES;
			break;
		case 3:
			dayType = DayType.WED;
			break;
		case 4:
			dayType = DayType.THURS;
			break;
		case 5:
			dayType = DayType.FRI;
			break;
		case 6:
			dayType = DayType.SAT;
			break;
		case 7:
			dayType = DayType.SUN;
			break;
		}
		return dayType;
	}

	@Override
	public List<OpticsDVPlaceBean> findByMonitorPointId(int monitorPointId) {
		return opticsDVPlaceDao.findByMonitorPointId(monitorPointId);
	}

	/**
	 * <pre>
	 * 重置拍照计划
	 *
	 * @param dvPlace 摄像机点位对象
	 * @param everydayPeriod 每天周期
	 * @param everydayPoint 每天时间点
	 * @param sevendayPeriod 7天周期
	 * @param sevendayPoint 7天时间点
	 * @param radioType 拍照计划类型
	 *
	 * @throws ParseException
	 * @throws SchedulerException
	 * </pre>
	 */
	public void refreshPhotographSchecduler(OpticsDVPlaceBean dvPlace,
			String everydayPeriod, String everydayPoint, String sevendayPeriod,
			String sevendayPoint, String radioType) throws ParseException,
			SchedulerException {

		// 判断是否有外部控制
		if (dvPlace.isIoOn()) {
			// 判断拍照计划选择类型（每天周期、每天时间点、7天周期、7天时间点）
			if (radioType.equals(Constants.Proxima.RADIO_TYPE_DAY)
					&& !Strings.isNullOrEmpty(everydayPeriod)) {// 判断是否是每天周期，每天周期是否为空
				PhotographIntervalScheduleBean interval = new PhotographIntervalScheduleBean();
				String[] dayPeriodValue = everydayPeriod.split(",");
				interval.setId(GalaxyIdUtil.get64UUID());
				interval.setStartTime(DateTimeUtil.parseTime(dayPeriodValue[0]));
				interval.setEndTime(DateTimeUtil.parseTime(dayPeriodValue[1]));
				interval.setInterval(Integer.parseInt(dayPeriodValue[2]));
				interval.setDvPlace(dvPlace);
				interval.setDayOfWeek(DayType.ALL);
				// 保存到数据库
				photographScheduleDao.save(interval);
			} else if (radioType.equals(Constants.Proxima.RADIO_TYPE_DAY_POINT)
					&& !Strings.isNullOrEmpty(everydayPoint)) {// 判断是否是每天时间点，每天时间点是否为空
				String[] dayPointValue = everydayPoint.split(",");
				for (int i = 0; i < dayPointValue.length; i++) {
					PhotographPointScheduleBean point = new PhotographPointScheduleBean();
					point.setId(GalaxyIdUtil.get64UUID());
					point.setTimePoint(DateTimeUtil.parseTime(dayPointValue[i]));
					point.setDvPlace(dvPlace);
					point.setDayOfWeek(DayType.ALL);
					// 保存到数据库
					photographScheduleDao.save(point);
				}
			} else if (radioType.equals(Constants.Proxima.RADIO_TYPE_7DAY)
					&& !Strings.isNullOrEmpty(sevendayPeriod)) {// 判断是否是7天周期，7天周期是否为空
				// 解析7天周期
				String[] sevenDay = sevendayPeriod.split("&");
				for (int i = 0; i < sevenDay.length; i++) {
					PhotographIntervalScheduleBean interval = new PhotographIntervalScheduleBean();
					String[] sevenDayValue = sevenDay[i].split(",");
					// 判断是星期几
					interval.setDayOfWeek(judgmentDayOfWeek(sevenDayValue[0]));
					interval.setId(GalaxyIdUtil.get64UUID());
					interval.setStartTime(DateTimeUtil.parseTime(sevenDayValue[1]));
					interval.setEndTime(DateTimeUtil.parseTime(sevenDayValue[2]));
					interval.setInterval(Integer.parseInt(sevenDayValue[3]));
					interval.setDvPlace(dvPlace);
					// 保存到数据库
					photographScheduleDao.save(interval);
				}
			} else if (radioType.equals(Constants.Proxima.RADIO_TYPE_7DAY_PONIT)
					&& !Strings.isNullOrEmpty(sevendayPoint)) {// 判断是否是7天时间点,7天时间点是否为空
				// 解析7天周期
				String[] sevenDayPoint = sevendayPoint.split("&");
				for (int i = 0; i < sevenDayPoint.length; i++) {
					PhotographPointScheduleBean point = new PhotographPointScheduleBean();
					String[] sevenDayPointValue = sevenDayPoint[i].split(",");
					point.setId(GalaxyIdUtil.get64UUID());
					point.setDayOfWeek(judgmentDayOfWeek(sevenDayPointValue[0]));
					point.setTimePoint(DateTimeUtil
							.parseTime(sevenDayPointValue[1]));
					point.setDvPlace(dvPlace);
					// 保存到数据库
					photographScheduleDao.save(point);
				}
			}
		}
	}

	@Override
	public boolean isIoPortUsingByAdd(int ioPort) {
		return opticsDVPlaceDao.isIoPortUsingByAdd(ioPort);
	}

	@Override
	public boolean isIoPortUsingByUpdate(String dvPlaceId, int ioPort) {
		return opticsDVPlaceDao.isIoPortUsingByUpdate(dvPlaceId, ioPort);
	}

	@Override
	public boolean isIoPortUsing(String dvPlaceId, int ioPort) {
		if (Strings.isNullOrEmpty(dvPlaceId)) {
			return opticsDVPlaceDao.isIoPortUsingByAdd(ioPort);
		} else {
			return opticsDVPlaceDao.isIoPortUsingByUpdate(dvPlaceId, ioPort);
		}
	}

}
