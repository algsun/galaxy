package com.microwise.common.util;

import org.junit.Test;
import org.springframework.util.Assert;

/**
 * test for EmailUtil
 *
 * @author gaohui
 * @date 12-11-30 13:42
 */
public class EmailUtilTest {

    @Test
    public void testGetLoginUrl() {
        String email = "abc@163.com";
        String loginUrl = EmailUtil.getLoginUrl(email);
        Assert.notNull(loginUrl);
    }

    @Test
    public void testObscured() {
        Assert.isNull(EmailUtil.obscured("abc163.com"));
        Assert.notNull(EmailUtil.obscured("abc@163.com"));
        Assert.notNull(EmailUtil.obscured("123456789@163.com"));
    }
}
