package com.microwise.blueplanet.service;

import com.microwise.blueplanet.bean.po.Formula;
import com.microwise.common.sys.test.BaseTest;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author gaohui
 * @date 14-1-7 10:58
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FormulaServiceTest extends BaseTest {
    @Autowired
    private FormulaService formulaService;

    @Test
    public void testFindBySensorId(){
        Formula formula = formulaService.findBySensorId(32);
        Assert.assertNotNull(formula);
        Assert.assertEquals(2, formula.getId());
        Assert.assertNotNull(formula.getFormulaName());
        Assert.assertEquals(32, formula.getSensorId());
        Assert.assertNotNull(formula.getFormulaParams());
        Assert.assertEquals(3, formula.getFormulaParams().size());
    }

    @Test
    public void testSortedMap(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("b", "2");
        map.put("a", "1");
        map.put("c", "3");

        List<String> keys = new ArrayList<String>(new TreeMap<String, String>(map).keySet());
        Assert.assertNotNull(keys);
        Assert.assertEquals("a", keys.get(0));
        Assert.assertEquals("b", keys.get(1));
        Assert.assertEquals("c", keys.get(2));
    }
}
