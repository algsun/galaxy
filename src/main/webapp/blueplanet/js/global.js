/**
 * 全局配置, 常量
 *
 * @author gaohui
 * @date 2013-02-04
 */
(function (global) {

    // 业务系统
    global.BLACKHOLE = "blackhole";
    global.BLUEPLANET = "blueplanet";

    //实时数据刷新周期 单位毫秒
    global.REALTIME_REFRESH_INTERVAL = 10000;
    //设备实时数据 请求数据包树
    //第一次请求数据包树
    global.FIRST_DATA_NUMBER = 6;
    //一个汉字占的像素
    global.ONE_WORD_PX = 14;

})(this);
