package com.microwise.uma.sys;

import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.Constants;
import com.microwise.common.util.HttpApiClient;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 提供后台本地接口，可通过此类与 uma-daemon 进行交互。
 *
 * @author gaohui
 * @date 13-4-26 09:55
 * @check wanggeng 13-05-06 #3174
 */
public class UmaDaemonApiClients {
    /**
     * uma-daemon 后台接口访问地址
     */
    public static final String DAEMON_API_BASE_URL = "uma.daemon.api.url";
    private static DaemonApiClient client;

    static {
        ConfigFactory.Configs config = ConfigFactory.getInstance().getConfig(Constants.Config.CONFIG_PROPERTIES_URL);
        client = new DaemonApiClient(config.get(DAEMON_API_BASE_URL));
    }

    public static DaemonApiClient getClient() {
        return client;
    }


    /**
     * uma-daemon 的客户端本地接口
     * <p/>
     * uma-daemon 典型的接口调用。
     * http://192.168.0.198:8080/uma-daemon/ServiceServlet?requestCode=updReg
     * <p/>
     * 在以下情况发生时，对 uma-daemon 进行交互：
     * <ul>
     * <li>updReg：规则修改</li>
     * <li>updUserCard： 人卡绑定修改</li>
     * <li>updExciter：激发器状态更改</li>
     * <li>updCardReader：读卡器状态更改</li>
     * </ul>
     */
    public static class DaemonApiClient extends HttpApiClient {
        public static final Logger log = LoggerFactory.getLogger(DaemonApiClient.class);

        // 操作枚举

        public static final String OPERATION_UPD_REG = "updReg";
        public static final String OPERATION_UPD_USER_CARD = "updUserCard";
        public static final String OPERATION_UPD_EXCITER = "updExciter";
        public static final String OPERATION_UPD_CARD_READER = "updCardReader";

        // 基地址
        private String baseUrl;
        private HttpClient httpClient;

        public DaemonApiClient(String baseUrl) {
            this.baseUrl = baseUrl;
            this.httpClient = new DefaultHttpClient();
        }

        @Override
        public HttpClient getHttpClient() {
            return httpClient;
        }

        /**
         * 执行调用请求
         *
         * @param operation 执行的操作
         * @return 返回调用成功与否
         * @throws IOException
         * @throws JSONException
         */
        private boolean doDefaultChanged(String operation) throws IOException, JSONException {
            HttpGet get = new HttpGet(baseUrl + "ServiceServlet?requestCode=" + operation);
            JSONObject jsonObject = requestAndParse(get);
            return jsonObject.getBoolean("success");
        }

        /**
         * 执行请求，如果失败会再尝试一次
         *
         * @param operation 执行的操作
         * @return 返回调用成功与否
         */
        private boolean defaultChanged(String operation) {
            int tryTimes = 0; // 尝试次数
            do {
                try {
                    if (doDefaultChanged(operation)) {
                        return true;
                    }
                } catch (IOException e) { // do nothing
                    log.error("访问 uma-daemon", e);
                } catch (JSONException e) { // do nothing
                    log.error("访问 uma-daemon", e);
                }
                tryTimes++;
            } while (tryTimes < 2); //默认尝试两次

            return false;
        }

        /**
         * 规则修改
         *
         * @return 返回调用成功与否
         */
        public boolean ruleChanged() {
            return defaultChanged(OPERATION_UPD_REG);
        }

        /**
         * 人卡绑定修改
         *
         * @return 返回调用成功与否
         */
        public boolean userCardChanged() {
            return defaultChanged(OPERATION_UPD_USER_CARD);
        }

        /**
         * 激发起修改
         *
         * @return 返回调用成功与否
         */
        public boolean exciterChanged() {
            return defaultChanged(OPERATION_UPD_EXCITER);
        }

        /**
         * 读卡器修改
         *
         * @return 返回调用成功与否
         */
        public boolean cardReaderChanged() {
            return defaultChanged(OPERATION_UPD_CARD_READER);
        }

    }

}
