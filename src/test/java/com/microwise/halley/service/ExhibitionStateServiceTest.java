package com.microwise.halley.service;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.halley.bean.vo.ExhibitionStateVO;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 外展状态单元测试类
 * User: xu.yuexi
 * Date: 13-10-22
 * Time: 下午2:38
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExhibitionStateServiceTest extends CleanDBTest {

    @Autowired
    private ExhibitionStateService exhibitionStateService;

    @Before
    public void before() throws Exception {
        CleanDBTest.xmlInsert2("common/dbxml/halley/ExhibitionStateServiceTest.xml");
    }

    @Test
    public void testGetAllExhibitionDetailByExhibitionId() {
//        Map<String, Object> map = exhibitionStateService.getAllExhibitionDetailByExhibitionId(1);
//        ExhibitionStatePO exhibitionStatePO = (ExhibitionStatePO) map.get("exhibitionStatePO");
//        Assert.assertEquals(1, exhibitionStatePO.getExhibitionId());
//        Assert.assertEquals(1, exhibitionStatePO.getOperator());
//        Assert.assertEquals(2, exhibitionStatePO.getState());
//        Assert.assertEquals("Mon Jan 21 14:42:57 CST 2013", exhibitionStatePO.getBeginTime().toString());
//        Assert.assertEquals("Thu Mar 21 14:42:57 CST 2013", exhibitionStatePO.getEndTime().toString());
//        ExhibitionVO exhibitionVO = (ExhibitionVO) map.get("exhibitionVO");
//        Assert.assertEquals("1", exhibitionVO.getOutEventId());
//        Assert.assertEquals("mw", exhibitionVO.getApplicant());
//        Assert.assertEquals("wz", exhibitionVO.getUseFor());
//        Assert.assertEquals("Fri Mar 22 00:00:00 CST 2013", exhibitionVO.getEstimatedEndTime().toString()); //数据库是date
    }

    //todo  @Test    和t_users相关，主键不赋值引用不上，赋值报错  h
    public void testGetHistoryExhibitionStateOperatorAndAddState() {
        List<ExhibitionStateVO> exhibitionStateVOs = exhibitionStateService.getHistoryState(1);
        Assert.assertEquals(2, exhibitionStateVOs.size());
        Assert.assertEquals("西安", exhibitionStateVOs.get(0).getPathPO().getDestinationName());
        Assert.assertEquals("上海", exhibitionStateVOs.get(1).getPathPO().getDestinationName());
        exhibitionStateService.addExhibitionState(1, 1, 1);
        exhibitionStateService.addExhibitionState(1, 2, 1);
        exhibitionStateService.addExhibitionState(1, 3, 1);
        List<ExhibitionStateVO> exhibitionStateVOs1 = exhibitionStateService.getHistoryState(1);
        Assert.assertEquals(6, exhibitionStateVOs1.size());
        Assert.assertEquals("西安", exhibitionStateVOs1.get(0).getPathPO().getDestinationName());
        Assert.assertEquals("上海", exhibitionStateVOs1.get(1).getPathPO().getDestinationName());
        Assert.assertEquals("成都", exhibitionStateVOs1.get(2).getPathPO().getDestinationName());
    }


//    @Test
//    public void testAlterHistoryItemEndTime() {
//        exhibitionStateService.alterHistoryItemEndTime(2, new Date());
//        Assert.assertEquals("Tue Jan 22 14:42:57 CST 2013",);
//    }
}
