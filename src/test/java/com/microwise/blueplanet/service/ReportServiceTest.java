package com.microwise.blueplanet.service;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.common.util.DateTimeUtil;
import com.microwise.blueplanet.bean.vo.ReportVO;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.test.BaseTest;

/**
 * 报表测试
 * 
 * @author xubaoji
 * @date 2013-6-13
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReportServiceTest extends BaseTest {

	/**
	 * 报表service
	 */
	@Autowired
	private  ReportService reportService;

	/*@BeforeClass
	public static void before() throws Exception {
		CleanDBTest.xmlInsert2("common/dbxml/blueplanet/ReportServiceTest.xml");
	}*/

	/** 查询日报表测试 */
	@Test
	public  void testFindReportInfo() {
		List<ReportVO> reportVOs = reportService.findReportInfo("31010101",
                DateTimeUtil.parseUncheck(DateTimeUtil.YYYY_MM_DD, "2013-03-07 00:00:"),
				Constants.FIND_TYPE_DAY);
		for (ReportVO reportVO2 : reportVOs) {
			System.out.println(reportVO2.getMaValue() + reportVO2.getMinValue()
					+ reportVO2.getSensorPhysicalId()
					+ reportVO2.getSensorPhysicalName()
					+ reportVO2.getWaveValue());
		}
		reportVOs = reportService.findReportInfo("31010101",
                DateTimeUtil.parseUncheck(DateTimeUtil.YYYY_MM_DD, "2013-03-07"),
				Constants.FIND_TYPE_WEEK);

		reportVOs = reportService.findReportInfo("31010101",
                DateTimeUtil.parseUncheck(DateTimeUtil.YYYY_MM_DD, "2013-01-03"),
				Constants.FIND_TYPE_MONTH);

		reportVOs = reportService.findReportInfo("31010101",
                DateTimeUtil.parseUncheck(DateTimeUtil.YYYY_MM_DD, "2013-01-03"),
				Constants.FIND_TYPE_YEAR);

	}

}
