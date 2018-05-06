package com.microwise.uma.sys;

import junit.framework.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * @author gaohui
 * @date 13-4-28 10:52
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UmaDaemonApiClientsTest {
    @Test
    public void testGetClient() throws Exception {
        UmaDaemonApiClients.DaemonApiClient client = UmaDaemonApiClients.getClient();

        Assert.assertNotNull(client);
    }

    @Ignore
    @Test
    public void testUpdReader() {
        UmaDaemonApiClients.DaemonApiClient client = UmaDaemonApiClients.getClient();

        Assert.assertTrue(client.cardReaderChanged());
    }

    @Ignore
    @Test
    public void testUpdExciter() {
        UmaDaemonApiClients.DaemonApiClient client = UmaDaemonApiClients.getClient();

        Assert.assertTrue(client.exciterChanged());
    }

    @Ignore
    @Test
    public void testUpdRule() {
        UmaDaemonApiClients.DaemonApiClient client = UmaDaemonApiClients.getClient();

        Assert.assertTrue(client.ruleChanged());
    }

    @Ignore
    @Test
    public void testUpdUserCard() {
        UmaDaemonApiClients.DaemonApiClient client = UmaDaemonApiClients.getClient();

        Assert.assertTrue(client.userCardChanged());
    }
}
