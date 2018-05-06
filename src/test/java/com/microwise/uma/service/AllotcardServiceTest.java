package com.microwise.uma.service;

import java.util.List;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.uma.bean.PersonBean;

/**
 * 区域 Service 测试
 *
 * @author li.jianfei
 * @date 2013-04-18
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AllotcardServiceTest extends CleanDBTest {

    /**
     * 区域 Service
     */
    @Autowired
    private AllotcardService allotcardService;

    @Before
    public void before() throws Exception {
        CleanDBTest.xmlInsert2("common/dbxml/uma/AllotcardServiceTest.xml");
    }

    /**
     * 查询人员测试
     */
    @Test
    public void testFindPersons() {

        List<PersonBean> personList = allotcardService.findPerson(null, false, false,
                3, 1, "aa");
        Assert.assertNotNull(personList);
        Assert.assertEquals(personList.size(), 3);

        personList = allotcardService.findPerson(null, true, false, 3, 1, "aa");
        Assert.assertNotNull(personList);
        Assert.assertEquals(personList.size(),2);
    }

    /**
     * 查询人员数量测试
     */
    @Test
    public void testFindPersonCount() {
        int count = allotcardService.findPersonCount(null, false, false , "aa");
        Assert.assertEquals(count,4);
        count = allotcardService.findPersonCount("大", false, false , "aa");
        Assert.assertEquals(count, 2);
        count = allotcardService.findPersonCount(null, true, false , "aa");
        Assert.assertEquals(count, 2);
        count = allotcardService.findPersonCount("大", true, false , "aa");
        Assert.assertEquals(count, 1);
    }

    @Test
    public void testFindAllPersons() {
        List<PersonBean> persons = allotcardService.findAllPersons(1, true);
        Assert.assertEquals(2, persons.size());
    }

    @Test
    public void testFindAllPersonsNoCard() {
        List<PersonBean> persons = allotcardService.findAllPersons(1, false);
        Assert.assertEquals(4, persons.size());
    }

    /**
     * 换卡测试
     */
    @Test
    public void testChangeCard() {
        allotcardService.changeCard(100, "22222222");
        List<PersonBean> personList = allotcardService.findPerson(null, true, false, 3, 1, "aa");
        for (PersonBean personBean : personList) {
            if (personBean.getId() == 100) {
                Assert.assertTrue(personBean.getCardSn().equals("22222222"));
            }
        }
    }

    /**
     * 退卡测试
     */
    @Test
    public void testRecedeCard() {
        allotcardService.recedeCard(100);
        List<PersonBean> personList = allotcardService.findPerson(null, true, false, 3, 1, "aa");
        Assert.assertTrue(personList.size() == 1);
        allotcardService.sendCardForPerson(100, "12345678");
    }

    /**
     * 发卡测试
     */
    @Test
    public void testSendCardForPersn() {
        allotcardService.sendCardForPerson(102, "22222222");
        List<PersonBean> personList = allotcardService.findPerson(null, true, false, 3, 1, "aa");
        Assert.assertTrue(personList.size() == 3);
    }
}
