package com.microwise.blackhole.sys;

import com.microwise.blackhole.action.app.PrivilegesBanListener;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

/**
 * @author gaohui
 * @date 14-2-26 08:56
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PrivilegesBeanListenerTest {

    @Test
    public void testInitBanPrivileges(){
        List<String> privileges = PrivilegesBanListener.getBanPrivileges();
        Assert.assertNotNull(privileges);
        Assert.assertTrue(privileges.isEmpty());
    }
}
