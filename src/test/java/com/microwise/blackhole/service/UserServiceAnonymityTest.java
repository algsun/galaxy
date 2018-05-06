package com.microwise.blackhole.service;

import com.microwise.common.sys.test.CleanDBTest;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author gaohui
 * @date 13-5-14 16:10
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceAnonymityTest extends CleanDBTest {
    @Autowired
    private UserService userService;

    @Test
    public void testIsAnonymityLoginEnable() throws Exception {
        CleanDBTest.xmlInsert2("common/dbxml/blackhole/UserServiceTest2.xml");

        Assert.assertTrue(userService.isAnonymityLoginEnable());

        CleanDBTest.tearDownOperation();
    }

    @Test
    public void testIsAnonymityLoginEnable2() {
        Assert.assertFalse(userService.isAnonymityLoginEnable());
    }


    @Test
    public void testEnableAnonymityLogin(){
        Assert.assertFalse(userService.isAnonymityLoginEnable());
        userService.enableAnonymityLogin(true);
        Assert.assertTrue(userService.isAnonymityLoginEnable());

        userService.enableAnonymityLogin(false);
        Assert.assertFalse(userService.isAnonymityLoginEnable());
    }
}

