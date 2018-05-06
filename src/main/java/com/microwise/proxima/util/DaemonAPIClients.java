package com.microwise.proxima.util;

import com.google.common.base.Preconditions;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.Constants;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 守望者接口客户端
 * 
 * @author gaohui
 * @date 13-3-26 17:18
 */
public class DaemonAPIClients {

	/** 获取配置文件工具类 */
	private static ConfigFactory.Configs configs = ConfigFactory
			.getInstance()
			.getConfig(Constants.Config.CONFIG_PROPERTIES_URL);

	/** 获取守望者程序的基本路径 */
	private static String url = configs
			.get(DaemonAPIClient.DAEMON_API_BASE_URL);

	/** 获取守望者程序接口调用工具类 */
	private static DaemonAPIClient client = new DaemonAPIClient(url);

	/** 获取守望者程序接口调用工具类 */
	public static DaemonAPIClient getClient() {
		return client;
	}

	public static class DaemonAPIClient {

		/** 基本路径在配置文件中key */
		public static final String DAEMON_API_BASE_URL = "proxima.daemon.api.url";

		/** 摄像机状态改变后通知后台的http服务路径 */
		private static final String DVPLACE_ENABLE_CHANGED = "%sdv-place/enable.action?dvPlaceId=%s&enable=%s";
		
		/**重置摄像机图片扫描计划http 服务路径*/
		private static final String DVPLACE_FTP_CHANGED="%sdv-place/ftpChanged.action?dvPlaceId=%s&";

		/** 重置拍照计划http服务路径 */
		private static final String PHOTOGRAPH_SCHEDULE_CHANGED_URL = "%sdv-place/refreshPhotographPlan.action?dvPlaceId=%s";

		/** 添加摄像机http服务路径 */
		private static final String ADD_DVPLACE = "%sdv-place/added.action?dvPlaceId=%s";

		/** 修改ftp服务器http服务路径 */
		private static final String FTP_PROFILE_CHANGED = "%sdv-place/ftpUpdated.action?ftpProfileId=%s";

		/**
		 * 基地址
		 */
		private String baseUrl;
		private HttpClient httpClient;

		public DaemonAPIClient(String baseUrl) {
			this.baseUrl = baseUrl;
			httpClient = new DefaultHttpClient();
		}

		/**
		 * 修改设备启用/停用
		 * 
		 * @param enable
		 * @return
		 */
		public boolean dvPlaceEnableChanged(String dvPlaceId, boolean enable) {
			Preconditions.checkNotNull(dvPlaceId);
			HttpGet get = new HttpGet(String.format(DVPLACE_ENABLE_CHANGED,
					baseUrl, dvPlaceId, enable));
			JSONObject jsonObject = null;
			try {
				jsonObject = requestAndParse(get);
				return jsonObject.getBoolean("success");
			} catch (IOException e) {
				throw new DaemonInvokeException(e);
			} catch (JSONException e) {
				throw new DaemonInvokeException(e);
			}
		}

		/**
		 * 图片扫描计划重置
		 * 
		 * @param dvPlaceId
		 * @return
		 */
		public boolean dvPlaceFtpChanged(String dvPlaceId) {
			Preconditions.checkNotNull(dvPlaceId);
			HttpGet get = new HttpGet(String.format(DVPLACE_FTP_CHANGED,
					baseUrl, dvPlaceId));
			JSONObject jsonObject = null;
			try {
				jsonObject = requestAndParse(get);
				return jsonObject.getBoolean("success");
			} catch (IOException e) {
				throw new DaemonInvokeException(e);
			} catch (JSONException e) {
				throw new DaemonInvokeException(e);
			}
		}

		/**
		 * 拍照计划更改
		 * 
		 * @param dvPlaceId
		 * @return
		 */
		public boolean photographScheduleChanged(String dvPlaceId) {
			Preconditions.checkNotNull(dvPlaceId);
			HttpGet get = new HttpGet(String.format(
					PHOTOGRAPH_SCHEDULE_CHANGED_URL, baseUrl, dvPlaceId));
			try {
				JSONObject jsonObject = requestAndParse(get);
				return jsonObject.getBoolean("success");
			} catch (IOException e) {
				throw new DaemonInvokeException(e);
			} catch (JSONException e) {
				throw new DaemonInvokeException(e);
			}
		}

		/**
		 * 添加摄像机点位
		 * 
		 * @param dvPlaceId
		 * @return
		 */
		public boolean addDVPlace(String dvPlaceId) {
			Preconditions.checkNotNull(dvPlaceId);
			HttpGet get = new HttpGet(String.format(ADD_DVPLACE, baseUrl,
					dvPlaceId));
			try {
				JSONObject jsonObject = requestAndParse(get);
				return jsonObject.getBoolean("success");
			} catch (IOException e) {
				throw new DaemonInvokeException(e);
			} catch (JSONException e) {
				throw new DaemonInvokeException(e);
			}
		}

		/**
		 * ftpProfileId 更改
		 * 
		 * @param ftpProfileId
		 * @return
		 */
		public boolean ftpProfileChanged(String ftpProfileId) {
			Preconditions.checkNotNull(ftpProfileId);
			HttpGet get = new HttpGet(String.format(FTP_PROFILE_CHANGED,
					baseUrl, ftpProfileId));
			try {
				JSONObject jsonObject = requestAndParse(get);
				return jsonObject.getBoolean("success");
			} catch (IOException e) {
				throw new DaemonInvokeException(e);
			} catch (JSONException e) {
				throw new DaemonInvokeException(e);
			}
		}

		private JSONObject requestAndParse(HttpGet get) throws IOException,
				JSONException {
			InputStream input = null;
			try {
				HttpResponse response = httpClient.execute(get);
				input = response.getEntity().getContent();
				InputStreamReader reader = new InputStreamReader(response
						.getEntity().getContent(), "utf-8");
				String resultText = CharStreams.toString(reader);
				return new JSONObject(resultText);
			} finally {
				Closeables.close(input, true);
			}
		}
	}

}
