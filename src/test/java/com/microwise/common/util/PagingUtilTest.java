package com.microwise.common.util;

import junit.framework.Assert;
import org.junit.Test;

/**
 * @author gaohui
 * @date 13-1-30 15:12
 */
public class PagingUtilTest {

    @Test
    public void testPageCount() {
        Assert.assertEquals(0, PagingUtil.pagesCount(0, 10));
        Assert.assertEquals(1, PagingUtil.pagesCount(9, 10));
        Assert.assertEquals(1, PagingUtil.pagesCount(10, 10));
        Assert.assertEquals(2, PagingUtil.pagesCount(11, 10));
    }
}
