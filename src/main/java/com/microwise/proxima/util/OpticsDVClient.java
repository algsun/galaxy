package com.microwise.proxima.util;

import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 光学摄像机(中瀛鑫)客户端
 * 
 * @author gaohui
 * @date 2012-7-6
 */
public class OpticsDVClient {
	/**
	 * http 接口的用户名
	 */
	private static final String USERNAME = "admin";

	/**
	 * http 接口的密码
	 */
	private static final String PASSWORD = "9999";

	private HttpClient httpClient;
	private String host;
	private int port;

	public OpticsDVClient(String host, int port) {
		this.host = host;
		this.port = port;

		DefaultHttpClient client = new DefaultHttpClient();

		client.getCredentialsProvider().setCredentials(
				new AuthScope(host, port),
				new UsernamePasswordCredentials(USERNAME, PASSWORD));
		httpClient = client;
	}

	public String fetch(String command) throws IOException {

		HttpGet get = new HttpGet(String.format("http://%s:%d/vb.htm?%s", host,
				port, command));

		HttpResponse response = httpClient.execute(get);
		InputStream inputStream = null;
		try {
			inputStream = response.getEntity().getContent();
			return CharStreams.toString(new InputStreamReader(inputStream));
		} finally {
			Closeables.closeQuietly(inputStream);
		}
	}
}
