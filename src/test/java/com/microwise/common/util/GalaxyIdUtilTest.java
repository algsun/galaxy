package com.microwise.common.util;

import junit.framework.Assert;
import org.junit.Test;

/**
 * @author gaohui
 * @date 13-9-22 15:55
 */
public class GalaxyIdUtilTest {
    @Test
    public void testGet64UUID(){
        String based64UUID = GalaxyIdUtil.get64UUID();
        Assert.assertNotNull(based64UUID);
        Assert.assertEquals(22, based64UUID.length());
    }
}
