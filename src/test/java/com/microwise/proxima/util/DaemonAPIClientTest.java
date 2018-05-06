package com.microwise.proxima.util;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;

import com.microwise.common.sys.ConfigFactory;
import com.microwise.proxima.util.DaemonAPIClients.DaemonAPIClient;
import org.junit.runners.MethodSorters;

/**
 * @author gaohui
 * @date 13-3-27 10:01
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DaemonAPIClientTest {

	@Ignore
	@Test
	public void test() {
		ConfigFactory.Configs configs = ConfigFactory.getInstance().getConfig(
				"config.properties");
		String url = configs.get("proxima.daemon.api.url");
		DaemonAPIClient client = new DaemonAPIClient(url);
		boolean result = client.dvPlaceEnableChanged("1", false);
		System.out.println(result);
		result = client.photographScheduleChanged("1");
		System.out.println(result);
		result = client.ftpProfileChanged("1");
		System.out.println(result);
	}
}
