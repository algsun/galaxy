package com.microwise.phoenix.service;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.phoenix.bean.vo.MarkSegment;
import com.microwise.phoenix.bean.vo.MarkSegmentContrast;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 标记段Service测试
 *
 * @author zhangpeng
 * @date 13-8-6
 * @check @wang.geng 2013年8月14日 #4938
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MarkSegmentServiceTest extends CleanDBTest {

    /**
     * 规则统计
     */
    @Autowired
    private MarkSegmentService markSegmentService;

    @Before
    public void before() throws Exception {
        CleanDBTest.xmlInsert2("common/dbxml/phoenix/MarkSegmentService.xml");
    }

    /**
     * 标记段对比
     */
    @Test
    @Ignore
    public void testContrast() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = sdf.parse("2013-08-02");
        List<MarkSegmentContrast> markSegmentContrastList = markSegmentService.contrast("31010101", d, 2);
        Assert.assertNotNull(markSegmentContrastList);
        Assert.assertEquals(2, markSegmentContrastList.size());
    }
    
    /**查询一个标记段年统计数据测试*/
    @Test
    public void testFindMardStat(){
    	Map<Integer, Float> mardData = markSegmentService.findMarkYearStat("mark4", 2013);
    	Assert.assertNotNull(mardData);
    	Assert.assertEquals(11, mardData.size());
    }
    
    /**查询一个站点下所以标记段信息*/
    @Test
    public void testFindAllMard(){
    	List<MarkSegment> markSegments = markSegmentService.findAllMark("31010101");
    	Assert.assertNotNull(markSegments);
    	Assert.assertEquals(3, markSegments.size());
    	
    }
}
