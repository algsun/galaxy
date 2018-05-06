package com.microwise.uma.service;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.uma.bean.AbstractUserActionBean;
import com.microwise.uma.bean.RuleBean;
import com.microwise.uma.bean.UserActionBean;
import com.microwise.uma.bean.UserCensusActionBean;
import com.microwise.common.sys.Constants;
import junit.framework.Assert;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author gaohui
 * @date 13-4-26 16:13
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserActionServiceTest extends CleanDBTest {
    @Autowired
    private UserActionService userActionService;

    @Before
    public void before() throws Exception {
        CleanDBTest.xmlInsert2("common/dbxml/uma/UserActionServiceTest.xml");
    }

    @Test
    public void testFindUserActionsCount() {
        int result = userActionService.findCount(100, new Date(0), new Date(1466954081646L));
        Assert.assertEquals(2, result);
    }

    @Test
    public void testFindCountByUserId() {
        int result = userActionService.findCount(100, new Date(0), new Date(1466954081646L));
        Assert.assertEquals(2, result);
    }

    @Test
    public void testFindUserActions() throws Exception {
        // 正序查询
        List<? extends AbstractUserActionBean> userActions = userActionService.findUserActions(100, new Date(0), new Date(1466954081646L), true, 1, 100);
        Assert.assertEquals(2, userActions.size());

        AbstractUserActionBean userAction = userActions.get(0);
        AbstractUserActionBean userAction2 = userActions.get(1);
        UserActionBean uc1 = (UserActionBean) userAction;
        UserActionBean uc2 = (UserActionBean) userAction2;
        Assert.assertTrue(uc1.getOccurrenceTime().before(uc2.getOccurrenceTime()));

        // 倒序查询
        userActions = userActionService.findUserActions(100, new Date(0), new Date(1466954081646L), false, 1, 100);
        Assert.assertEquals(2, userActions.size());
        userAction = userActions.get(0);
        userAction2 = userActions.get(1);
        uc1 = (UserActionBean) userAction;
        uc2 = (UserActionBean) userAction2;
        Assert.assertTrue(uc1.getOccurrenceTime().after(uc2.getOccurrenceTime()));
    }

    @Test
    public void testFindUserActionsOfCensus() {
        List<AbstractUserActionBean> userActions = userActionService.findUserActions(101, new Date(0), new Date(1466954081646L), true, 1, 100);
        Assert.assertEquals(2, userActions.size());

        for (AbstractUserActionBean userAction : userActions) {
            Assert.assertEquals(101, (int) userAction.getPerson().getId());
        }

        Assert.assertEquals(RuleBean.TYPE_SINGLE, userActions.get(0).getType());
        Assert.assertEquals(RuleBean.TYPE_CENSUS, userActions.get(1).getType());

        UserCensusActionBean userAction = (UserCensusActionBean) userActions.get(1);
        Assert.assertNotNull(userAction);
        Assert.assertNotNull(userAction.getPerson());
        Assert.assertNotNull(userAction.getBackAction());
        Assert.assertNotNull(userAction.getGoAction());
    }

    @Test
    public void findSingleCountByRuleId() {
        int count = userActionService.findCountByRuleId(1, new Date(0), new Date(1466954081646L));
        Assert.assertEquals(2, count);
    }

    @Test
    public void findCensusCountByRuleId() {
        int count = userActionService.findCountByRuleId(4, new Date(0), new Date(1466954081646L));
        Assert.assertEquals(1, count);
    }

    @Test
    public void findSingleCountByRuleIdWithDateType() {
        Date date = DateTime.now().withYear(2013).toDate();
        int count = userActionService.findCountByRuleId(1, Constants.Uma.DATE_TYPE_YEAR, date);
        Assert.assertEquals(2, count);
    }

    @Test
    public void findCensusCountByRuleIdWithDateType() {
        Date date = DateTime.now().withYear(2013).toDate();
        int count = userActionService.findCountByRuleId(4, Constants.Uma.DATE_TYPE_YEAR, date);
        Assert.assertEquals(1, count);
    }


    @Test
    public void findSingleActionsByRuleId() {
        // 正序查询
        List<UserActionBean> userActions = userActionService.findSingleActionsByRuleId(1, new Date(0), new Date(1466954081646L), 1, 100, true);

        Assert.assertEquals(2, userActions.size());
        AbstractUserActionBean userAction = userActions.get(0);
        AbstractUserActionBean userAction2 = userActions.get(1);
        UserActionBean uc1 = (UserActionBean) userAction;
        UserActionBean uc2 = (UserActionBean) userAction2;
        Assert.assertTrue(uc1.getOccurrenceTime().before(uc2.getOccurrenceTime()));

        // 倒序查询
        userActions = userActionService.findSingleActionsByRuleId(1, new Date(0), new Date(1466954081646L), 1, 100, false);
        Assert.assertEquals(2, userActions.size());
        userAction = userActions.get(0);
        userAction2 = userActions.get(1);
        uc1 = (UserActionBean) userAction;
        uc2 = (UserActionBean) userAction2;
        Assert.assertTrue(uc1.getOccurrenceTime().after(uc2.getOccurrenceTime()));
    }

    @Test
    public void findCensusActionsByRuleId() {
        List<UserCensusActionBean> userActions = userActionService.findCensusActionsByRuleId(4, new Date(0), new Date(1466954081646L), 1, 100, true);
        Assert.assertEquals(1, userActions.size());
    }
}
