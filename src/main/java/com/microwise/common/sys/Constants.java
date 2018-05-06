package com.microwise.common.sys;

import java.util.HashMap;
import java.util.Map;

/**
 * galaxy常量类
 *
 * @author zhangpeng
 * @date 2012-10-23
 */
public class Constants {

    private Constants() {
    }

    /**
     * 这里用的java的BCRYPT加密salt参数，不得小于10，决定加密复杂度
     * 这里用11的原因：salt数值每加1，耗时翻一倍，11加密一次用时约为320毫秒，这样用户登录不会明显感觉到延迟
     */
    public static final int BCRYPT_SALT = 11;

    /**
     * 页面每页最大条数(公用)
     */
    public static final int SIZE_PER_PAGE = 10;

    /**
     * 查询类型：按年查询
     */
    public static final int FIND_TYPE_YEAR = 1;
    /**
     * 查询类型：按月查询
     */
    public static final int FIND_TYPE_MONTH = 2;
    /**
     * 查询类型：按天查询
     */
    public static final int FIND_TYPE_DAY = 3;
    /**
     * 查询类型：按周查询
     */
    public static final int FIND_TYPE_WEEK = 4;

    /**
     * 系统管理相关常量
     *
     * @author wwang.geng
     * @date 2013-9-12
     */
    public static final class Blackhole {
        /**
         * 用户激活 EventAction
         */
        public static final String USER_ACTIVATE_ACTION = "blackhole/activeUser.action";
    }

    /**
     * 环境监控常量
     *
     * @author wang.geng
     * @date 2013-9-12
     */
    public static final class Blueplanet {

        /**
         * 数据库区域表名称
         */
        public static final String TABLE_ZONE_NAME = "t_zone";
        /**
         * 数据库设备表名称
         */
        public static final String TABLE_DEVICE_NAME = "m_nodeinfo";
        /**
         * 监测指标激活状态:激活的
         */
        public static final Integer SENSORINFO_ACTIVE_STATE_TRUE = 1;
        /**
         * 监测指标风向类型标识
         */
        public static final Integer SENSORINFO_SHOWTYPE_IS_WIND_DIRECTION = 1;
        /**
         * map 集合 中最小时间 key 标识常量
         */
        public static final String MAP_KEY_MIN_TIME = "minTime";
        /**
         * map 集合 中最大时间 key 标识常量
         */
        public static final String MAP_KEY_MAX_TIME = "maxTime";

        /**
         * 默认第一个位置点编号
         */
        public static final String DEFAULT_FIRST_LOCATIONID = "100267";
    }

    /**
     * Proxima 业务系统常量
     *
     * @author wang.geng
     * @date 2013-9-12
     */
    public static final class Proxima {

        /**
         * 每天周期
         */
        public static final String RADIO_TYPE_DAY = "day";
        /**
         * 每天时间点
         */
        public static final String RADIO_TYPE_DAY_POINT = "point";
        /**
         * 7天周期
         */
        public static final String RADIO_TYPE_7DAY = "7day";
        /**
         * 7天时间点
         */
        public static final String RADIO_TYPE_7DAY_PONIT = "7day_point";
        /**
         * 站点名称在session的key
         */
        public static final String SESSION_SITE_NAME = "sitename";
        /**
         * 站点id在session的key
         */
        public static final String SESSION_SITE_ID = "siteId";
        /**
         * 用户id在session的key
         */
        public static final String SESSION_USER_ID = "userId";
        /**
         * 用户名称在session的key
         */
        public static final String SESSION_USER_NAME = "userName";
        /**
         * 角色名称在session的key
         */
        public static final String SESSION_USER_ROLE_NAME = "roleName";
        /**
         * 角色id在session的key
         */
        public static final String SESSION_USER_ROLE_ID = "roleId";
        /**
         * 权限
         */
        public static final String SESSION_POPEDOMS = "popedomsOfUser";
        /**
         * 可以使用摄像机点位管理的角色
         */
        public static final String ROLES_COULD_USE_DV_PALCE_MANAGE = "roles.could.use.dvPlaceManage";
        /**
         * 可以使用摄像机点位管理的角色 在 application 中的 key
         */
        public static final String APPLICATION_ROLES_COULD_USE_DV_PALCE_MANAGE = "rolesCouldUseDvPlaceManage";
        /**
         * 图片浏览查询默认天数
         */
        public static final String KEY_VIEW_IMAGE_QUERY_DEFAULT_DAYS = "proxima.image.query.default.days";
    }

    /**
     * Phoenix 业务系统常量
     *
     * @author wang.geng
     * @date 2013-9-12
     */
    public static final class Phoenix {
        /**
         * 出库类型常量：外出借展
         */
        public static final int OUT_EVENT_TYPE_WCJZ = 1;
        /**
         * 出库类型常量：科研修复
         */
        public static final int OUT_EVENT_TYPE_KYXF = 2;
    }

    /**
     * Uma 业务系统常量
     *
     * @author wang.geng
     * @date 2013-9-12
     */
    public static final class Uma {
        // 时间条件类型
        public static final int DATE_TYPE_YEAR = 1; //年
        public static final int DATE_TYPE_MONTH = 2; //月
        public static final int DATE_TYPE_DAY = 3; //日
    }

    /**
     * 配置文件中的常量
     *
     * @author wang.geng
     * @date 2013-9-12
     */
    public static final class Config {
        /**
         * config.properties 配置文件路径常量
         */
        public static final String CONFIG_PROPERTIES_URL = "config.properties";
        /**
         * 是否是相对路径
         */
        public static final String RESOURCES_DIR_RELATIVE = "galaxy.resources.dir.relative";
        /**
         * 资源路径
         */
        public static final String RESOURCES_DIR = "galaxy.resources.dir";

        public static final String RESOURCE_IS_UPLOAD_TO_CLOUD = "galaxy.resources.isUploadToCloud";
        /**
         * 我的任务是否开启，并包括哪些权限
         */
        public static final String MY_TASK_ENABLE = "blackhole.myTask.enable";
        public static final String MY_TASK_ENABLE_PRIVILEGE = "blackhole.myTask.privilege";

        public static final String APP_LOCALE = "app.locale";
        public static final String GALAXY_MODE = "galaxy.mode";


        public static final String GALAXY_CUSTOMIZE = "galaxy.customize";
    }


    /**
     * 业务系统标识枚举
     *
     * @author gaohui
     * @date 2012-11-28
     */
    public static class Subsystems {
        /**
         * 系统管理
         */
        public static final int BLACK_HOLE = 1;
        /**
         * 环境监控
         */
        public static final int BLUE_PLANET = 2;
        /**
         * 本体监测
         */
        public static final int PROXIMA = 3;
        /**
         * 资产管理
         */
        public static final int ORION = 4;
        /**
         * 行为追踪
         */
        public static final int UMA = 5;
        /**
         * 行为追踪
         */
        public static final int Phoenix = 6;
        /**
         * 自助导览
         */
        public static final int UMI = 7;
        /**
         * 文物外展
         */
        public static final int HALLEY = 8;
        /**
         *
         */
        public static final int BIELA = 9;
    }

    /**
     * 用户角色分类枚举
     *
     * @author gaohui
     * @date 2012-11-28
     */
    public static class Roles {
        /**
         * 超级管理员
         */
        public static final String SUPERMAN = "superman";
        /**
         * 站点管理员
         */
        public static final String SITE_MANAGER = "siteManager";
        /**
         * 普通用户
         */
        public static final String USER = "user";
        /**
         * 匿名用户
         */
        public static final String ANONYMITY = "anonymity";
    }

    /**
     * 测试用到常量标识
     *
     * @author zhangpeng
     * @date 2012-12-13
     */
    public static class Emails {
        /**
         * Email配置文件的path
         */
        public static final String EMAIL_CONFIG_PATH = "common/email/email-config.properties";
        /**
         * Email用户激活模版path
         */
        public static final String EMAIL_USER_ACTIVATES_PATH = "common/email/email-activation.template";
        /**
         * Email密码找回模版path
         */
        public static final String EMAIL_FAND_PASSWORD_PATH = "common/email/email-fandPassword.template";
        /**
         * Email通知邮件模板path
         */
        public static final String EMAIL_NOTIFICATION_PATH = "common/email/email-notification.template";
        /**
         * Email报表订阅模版
         */
        public static final String EMAIL_SUBSCRIBE_PATH = "common/email/email-subscribe.template";
        /**
         * 邮件发送方key
         */
        public static final String SMTP_USERNAME = "smtp.username";
        /**
         * 发送批量邮件用户 key
         */
        public static final String SMTP_BATCH_USERNAME = "smtp.batch.username";
        /**
         * 邮件作者key
         */
        public static final String SMTP_AUTHOR = "smtp.author";
        /**
         * 发送邮件地址
         */
        public static final String SMTP_FROM = "smtp.from";
        /**
         * 周报表 发送邮件 日期  ： 周几
         */
        public static final String SUBSCRIBE_REPORT_DAY_OF_WEEK = "subscribeReport.dayOfWeek";
        /**
         * 周报表 发送邮件 日期  ： 几号
         */
        public static final String SUBSCRIBE_REPORT_DAY_OF_MONTH = "subscribeReport.dayOfMonth";
        /**
         * 发送 报表邮件 时间
         */
        public static final String SUBSCRIBE_REPORT_HOUR = "subscribeReport.hour";
        /**
         * 发送 报表邮件 时间
         */
        public static final String SUBSCRIBE_REPORT_MINUTE = "subscribeReport.minute";
    }

    /**
     * session 中的常量
     *
     * @author gaohui
     * @date 2012-11-28
     */
    public static class Session {
        /**
         * 用户在归属站点不同业务系统中的权限. 类型为 Map<Integer, List<Privilege>>
         * subsystemId:Integer => privileges:List<Privilege>
         * 业务系统标识 => 用户在业务系统中的权限
         */
        public static final String SUBSYSTEM_PRIVILEGES_OF_USER = "subsystemPrivileges";
        /**
         * 用户在其他站点不同业务系统中的权限. 类型为 Map<Integer, List<Privilege>>
         * subsystemId:Integer => privileges:List<Privilege>
         * 业务系统标识 =>用户在业务系统中的权限
         */
        public static final String SUBSYSTEM_PRIVILEGES_OF_GUEST = "subsystemPrivilegesOfGuest";
        /**
         * 访客在其他站点的权限. 类型为 List<Privilege>
         */
        public static final String GUEST_PRIVILEGES = "guestPrivileges";
        /**
         * 当前登录用户在 session 中的 key. 类型为 blackhole.bean.User.
         */
        public static final String USER_OF_SESSION = "currentUser";
        /**
         * 当前登录用户归属站点 在 session 中的 key. 类型为 blackhole.bean.LogicGroup
         */
        public static final String USER_LOGIC_GROUP = "userLogicGroup";
        /**
         * 当前登录用户归属站点的直接子站点 在 session 中有 key. 类型为 List<blackhole.bean.LogicGroup>
         */
        public static final String SUB_LOGIC_GROUP_OF_USER_LOGIC_GROUP = "subLogicGroupOfUserLogicGroup";
        /**
         * 当前选择站点 在 session 中的 key. 类型为 blackhole.bean.LogicGroup
         */
        public static final String CURRENT_LOGIC_GROUP = "currentLogicGroup";
        /**
         * 当前站点的直接子站点 在 session 中的 key. 类型为 List<blackhole.bean.LogicGroup>
         */
        public static final String CURRENT_SUB_LOGIC_GROUPS = "currentSubLogicGroups";
        /**
         * 用户退出日志是否被记录
         */
        public static final String IS_LOGOUT_LOGGED = "_isLogoutLogged";
        /**
         * 当前站点管理员
         */
        public static final String CURRENT_LOGICGROUP_MANAGER = "currentLogicGroupManager";

        /**
         *
         */
        public static final String GALAXY_RESOURCE_URL = "galaxyResourceURL";
    }

    public static class HalleyConstants {
        /**
         * 设备检测指标ID:经度
         */
        public static final int SENSORPHYSICALID_LONGITUDE = 12287;
        /**
         * 设备检测指标ID:纬度
         */
        public static final int SENSORPHYSICALID_LATITUDE = 12286;

        /**
         * 设备检测指标ID:开关量
         */
        public static final int SENSORPHYSICALID_SWH = 89;

        /**
         * 设备检测指标ID:震动
         */
        public static final int SENSORPHYSICALID_SHAKE = 88;

        /**
         * 设备检测指标ID:加速度
         */
        public static final int SENSORPHYSICALID_ACCL = 87;

        /**
         * 设备检测指标常量:经度
         */
        public static final String LONGITUDE = "longitude";

        /**
         * 设备检测指标常量:纬度
         */
        public static final String LATITUDE = "latitude";

        /**
         * 设备检测指标常量:开关量
         */
        public static final String SWH = "swh";

        /**
         * 设备检测指标常量:震动
         */
        public static final String SHAKE = "shake";

        /**
         * 设备检测指标常量:加速度
         */
        public static final String ACCL = "accl";
    }


    /**
     * 图表相关常量
     *
     * @author zhangpeng
     * @date 2013-2-28
     */
    public static class ChartConstants {
        /**
         * 图形显示的单位在map中的key，map为highcharts需要的格式
         */
        public static final String TOOLTIP_YSUFFIX = "ySuffix";
        /**
         * 图形显示的精度在map中的key，map为highcharts需要的格式
         */
        public static final String TOOLTIP_YDECIMALS = "yDecimals";
        /**
         * 图形类型：柱状图
         */
        public static final String CHART_TYPE_COLUMN = "column";
        /**
         * 图形类型：曲线图
         */
        public static final String CHART_TYPE_SPLINE = "spline";
        /**
         * 图表返回值Map的key：设备id
         * TODO 位置点相关功能完成后删除
         *
         * @deprecated
         */
        public static final String DEVICE_ID = "deviceId";
        /**
         * 图表返回值Map的key：设备名称
         * TODO 位置点相关功能完成后删除
         *
         * @deprecated
         */
        public static final String DEVICE_NAME = "deviceName";

        /**
         * 图表返回值Map的key：位置点ID
         */
        public static final String LOCATION_ID = "locationId";

        /**
         * 图表返回值Map的key：位置点名称
         */
        public static final String LOCATION_NAME = "locationName";
        /**
         * 图表返回值Map的key：是否拥有降雨量数据，value为boolean，true代表有
         */
        public static final String HAS_DATA_RAINFALL = "hasRainfallData";
        /**
         * 图表返回值Map的key：是否拥有降雨量数据，value为boolean，true代表有
         */
        public static final String HAS_DATA_BASIC = "hasBasicData";
        /**
         * 图表返回值Map的key：是否拥有降雨量数据，value为boolean，true代表有
         */
        public static final String HAS_DATA_AVG = "hasAvgData";
        /**
         * 图表返回值Map的key：是否拥有光照数据，value为boolean，true代表有
         */
        public static final String HAS_DATA_LIGHT = "hasLightData";
        /**
         * 图表返回值Map的key：设备的图表返回集合List<HighChartVO>
         */
        public static final String CHART_LIST = "chartList";
        /**
         * 图表返回值Map的key：设备的图表返回集合HighChartVO
         */
        public static final String CHART_DATA = "chartData";
        /**
         * 监测指标：降雨量
         */
        public static final Integer SENSORINFO_RB = 47;
        /**
         * 监测指标： 光照
         */
        public static final Integer SENSORINFO_LX = 41;

        /**
         * 监测指标: 紫外
         */
        public static final Integer SENSORINFO_UV = 42;
        /**
         * 监测指标：温度
         */
        public static final Integer SENSORINFO_TMT = 33;
        /**
         * 监测指标：湿度
         */
        public static final Integer SENSORINFO_HUM = 32;
        /**
         * 监测指标:二氧化碳
         */
        public static final Integer SENSORINFO_CO2 = 36;
        /**
         * 监测指标：蒸发量
         */
        public static final Integer SENSORINFO_EVAP = 80;

        /**
         * 监测指标: VOC-高灵敏度
         */
        public static final Integer SENSORINFO_VOC_HS = 83;

        /**
         * 监测指标: 水流量
         */
        public static final Integer SENSORINFO_WATER_FLOW = 97;

        /**
         * 监测指标: 水位
         */
        public static final Integer SENSORINFO_WATER_LEVEL = 96;

        /**
         * 监测指标: 水速
         */
        public static final Integer SENSORINFO_PULSE = 90;
        /**
         * 光照图表 名称常量
         */
        public static final String LX_H_NAME = "累积光照";
        /**
         * 查询原始数据时map的key
         */
        public static final String CHARTDATA_KEY_TIME = "time";
        /**
         * 查询原始数据时map的value
         */
        public static final String CHARTDATA_KEY_DATA = "data";
        /**
         * 基本曲线图 默认取模处理常量，1表示不取模
         */
        public static final Integer MODULE_NONE = 1;
        /**
         * 风向玫瑰图：风向频率前台显示名称
         */
        public static final String WIND_FREQUENCY_TEXT = "风向频率";
        /**
         * 风向玫瑰图：平均风速前台显示名称
         */
        public static final String WIND_SPEED_TEXT = "平均风速";
        /**
         * 风向玫瑰图：风向频率前台显示单位
         */
        public static final String WIND_FREQUENCY_UNITS = "%";
        /**
         * 风向玫瑰图：平均风速前台显示名称
         */
        public static final String WIND_SPEED_UNITS = "m/s";
    }

    /**
     * 资产管理出入库申请
     */
    public static final class StockOut {
        /**
         * 出库申请流程 id(key)
         */
        public static final String PROCESS_DEFINITION_KEY = "stock-out";
        /**
         * 出库确认 task id(key)
         */
        public static final String TASK_STOCK_OUT_CONFIRM = "stockOutConfirmTask";
    }

    /**
     * action 中使用的常量
     */
    public static class Actions {
        private Actions() {
        }

        public static final String MESSAGE = "_GALAXY_MESSAGE";
        public static final String SUCCESS = "_GALAXY_SUCCESS";
    }

    /**
     * 文件类型常量
     */
    @SuppressWarnings("serial")
    public static final Map<String, String> CONTENT_TYPE_MAP = new HashMap<String, String>() {
        {
            put("image/jpeg", ".jpg");
            put("image/bmp", ".bmp");
            put("image/gif", ".gif");
            put("image/png", ".png");
        }
    };

    /**
     * 设备类型(节点)
     */
    public static final int NODE = 1;

    /**
     * 设备类型(中继)
     */
    public static final int RELAY = 2;

    /**
     * 设备类型(主模块)
     */
    public static final int MAIN_MODULE = 3;

    /**
     * 设备类型(从模块)
     */
    public static final int CHILD_MODULE = 4;

    /**
     * 设备类型(控制模块)
     */
    public static final int CONTROL_MODULE = 5;

    /**
     * 设备类型(网关)
     */
    public static final int GATEWAY = 7;

    /**
     * 元智通讯协议版本V1
     */
    public static final int PROCOTOL_VERSION_1 = 1;

    /**
     * 元智通讯协议版本V3
     */
    public static final int PROCOTOL_VERSION_3 = 3;
}
