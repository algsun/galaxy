package com.microwise.blueplanet.bean;

import com.microwise.blueplanet.bean.po.Switch;
import com.microwise.blueplanet.bean.po.Switches;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

/**
 * @author gaohui
 * @date 14-2-17 10:45
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SwitchesTest {
    @Test
    public void testParseToValues(){
        Switches switches =new Switches();

        switches.setEnable(0x0F);
        switches.setOnOff(0x0C);

        switches.parseToValues();

        List<Switch> switchList = switches.getValues();
        Assert.assertNotNull(switchList);
        Assert.assertEquals(8, switchList.size());

        Switch switcH = switchList.get(0);
        Assert.assertTrue(switcH.isEnable());
        Assert.assertFalse(switcH.isOnOff());
        Assert.assertEquals(1, switcH.getRoute());

        switcH = switchList.get(1);
        Assert.assertTrue(switcH.isEnable());
        Assert.assertFalse(switcH.isOnOff());
        Assert.assertEquals(2, switcH.getRoute());

        switcH = switchList.get(2);
        Assert.assertTrue(switcH.isEnable());
        Assert.assertTrue(switcH.isOnOff());
        Assert.assertEquals(3, switcH.getRoute());
    }

}
