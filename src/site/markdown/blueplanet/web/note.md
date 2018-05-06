## 技术栈

* controller
    + [struts2](http://struts.apache.org)
    + [struts2-freeroute](https://github.com/bastengao/struts2-freeroute)
    + [struts-json](https://cwiki.apache.org/confluence/display/WW/JSON%20Plugin)
* 模板
    + [freemarker](http://freemarker.sourceforge.net/)
* 基础
    + [Bootstrap](http://twitter.github.com/bootstrap/)
* js 库
    + [jQuery](http://jquery.com)
    + [underscore](http://underscorejs.org)
    + [moment](http://momentjs.com/)
* 图表
    + [highcharts](http://www.highcharts.com)
    + [flot](http://www.flotcharts.org/)
* 树
    + [zTree](http://www.ztree.me)
* 窗口，通知
    + [artDialog](http://aui.github.com/artDialog/)
    + [pnotify](http://pinesframework.org/pnotify/)

## 设备树接口

`webapp/bluepplanet/js/device-tree.js`

    var Blueplanet = App('blueplanet');
    Blueplanet.deviceTree = {
      // 选中设备
      selectDevice(deviceId),

     // 选中区域
      selectZone(zoneId),

      /**
       * 重新加载设备树
       *
       * @param callback:Function 可选, 加载完成后的回调
       */
      reload(),


      /**
       *单击事件
       * callback 会被传入原始 event 事件和 treeNode
       *
       * @param callback:Function
       */
      click(function(event, treeNode)),

      /**
       * 设置能否拖拽回调.
       * callback 会被传入 treeId 和 treeNodes, 返回 true 则可拖，反之亦然
       *
       * @param callback:Function
       */
      beforeDrag(function(treeId, treeNodes)),

      /**
       * 在树上绑定节点拖拽事件
       * @param id 指定拖放到目标的 dom id
       * @param callback 指定拖放后的回调. e 事件, treeId 树节点 id, treeNode 树节点
       */
      drop(id, callback),
    };

### treeNode 属性说明

treeNode 公共属性

    {
        name: String, // 节点名称
        description: String, // 节点描述，用于 tooltip
        /**
         * 类型, 分为：
         * "site" 站点
         * "zone" 区域,
         * "gateway" 网关,
         * "repeator" 中继,
         * "device" 节点,
         * "masterModule" 主模块,
         * "slaveModule" 从模块
         */
        type: String
    }

根据不同的类型, treeNode 会有自己额外的属性：

* site 站点

        {
            type: "site"
            siteId:String
        }

* zone 区域

        {
            type: "zone",
            zoneId:String, //区域id
            zoneName:String // 区域名称
        }

* gateway 网关/ repeator 中继/ masterModule 主模块

        {
            type: "gateway/reator/masterModule",
            nodeId:String, //节点id
            nodeName:String, //节点名称
            zoneId:String, // 所属区域id
        }

* device 节点设备

        {
            type: "device",
            nodeId:String, //节点id
            nodeName:String, //节点名称
            sensorPhysicalIds:int[] //设备包含的监测指标
            zoneId:String, // 所属区域id
        }

* slaveModule 从模块

        {
            type: "slaveModule",
            nodeId:String, //节点id
            nodeName:String, //节点名称
            sensorPhysicalIds:int[] //设备包含的监测指标
            zoneId:String, // 所属区域id
        }

# 站点下监测指标

`webapp/bluepplanet/js/device-tree.js`, 获取站下监测指标

    var Blueplanet = App(window.BLUEPLANET);
    var sensorinfos = Blueplanet.site.sensorinfos;
    /**
    sensorinfos = {
        sensorPhysicalId :
        // 监测指标
        {
            sensorPhysicalId:int , // 监测指标id
            cnName:String , // 中文名称
            enName:String, // 英文名称
            units:String, // 单位
            positions:int, // 显示位
        }
    };
    */

    // 取值
    var sensorinfo = sensorinfos[32];


## 概览导航

概览导航接口说明

    var Blueplanet = App(window.BLUEPLANET);
    Blueplanet.zoneDevicePath = {
        // 创建节点
        createNode: function(node)

        /**
         * 改变节点时
         *
         * @param callback 回调函数，回调会被传入 node 对象
         */
        change : function(callback),
    }


    // 示例
    Blueplanet.zoneDevicePath.change(function (node) {
        if(node.type === 'device'){
            var basePath = $('base').attr('href');
            if(basePath){
                location.href = basePath + 'device/' + node.id;
            }
        }
    });

### node 属性说明

    node : {
        name:string(required) 显示名称,
        url: string(optional) 左侧点击链接,
        open: boolean(optional) 是否默认打开,
        children:[node](optional) 孩子
        type:string 类型枚举 site, zone, device
        id:string 暂时 type 为 device 时才有此属性，表示节点对应的 id
    }


## js css 缓存引用

为了解决应用每次重新部署后，之前浏览器加载的 js css 缓存的问题，现在在 js css 的引用链接后跟随机参数。
随机参数为应用启动时间的毫秒数，即应用每次重启之前缓存的 js css 都会失效。

`webapp/common/pages/common-tag.ftl` 文件已经提供宏来方便处理：

    <#--
    之前引用
    <script type="text/javascript" src="example.js"></script>
    <link type="text/css" rel="stylesheet" href="example.css">
    -->

    <#include "/common/pages/common-tag.ftl">
    <@scriptTag "example.js"/>

     <#--
     使用宏引用，将会产生
     <script type="text/javascript" src="example.js?1361853662843"></script>
     -->

     <@linkTag "example.css"/>
     <#--
     <link type="text/css" rel="stylesheet" href="example.css?1361853662843">
     -->

