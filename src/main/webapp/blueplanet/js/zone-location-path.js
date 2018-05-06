/**
 * 区域设备路径支持
 *
 * @author gaohui
 * @date 2012-12-29
 * @dependency jQuery, underscore
 */

(function ($) {
    (function () {

        var $breadcrumb = $("#area-location-path");

        var BluePlanet = App("blueplanet");
        BluePlanet.zoneLocationPath = {
            // node 改变时回调
            _changeNodeCallbacks: $.Callbacks(),
            // callback:Function(node)
            change: function (callback) {
                this._changeNodeCallbacks.add(callback);
            },

            /**
             * 生成路径节点
             *
             * node : {
             *     name:string(required) 显示名称,
             *     url: string(optional) 左侧点击链接,
             *     open: boolean(optional) 是否默认打开,
             *     children:[node](optional) 孩子
             *
             *     #额外属性
             *     id:string(optional) 类型为 zone, location 时存在
             *     type:string 类型 site, zone, location
             * }
             */
            createNode: function (nodeOption) {
                var that = this;
                var nodeTemplate = null;
                if (nodeOption.children && nodeOption.children.length > 0) {
                    nodeTemplate = _.template($("#nodeTemplate").html());
                } else {
                    nodeTemplate = _.template($("#singleNodeTemplate").html());
                }

                //生成节点 dom
                var node = nodeTemplate(nodeOption);
                var $node = $(node);

                //初始化子节点
                $node.find("ul.dropdown-menu").children().each(function (i) {
                    var $this = $(this);
                    // 绑定子节点下拉点击事件后生成子节点
                    $this.click(function (e) {
                        e.preventDefault();
                        var dtd = $.Deferred();
                        // 切换成功时
                        $.when(dtd).done(function () {
                            // 删除所有右侧路径
                            $node.nextAll().remove();
                            that.createNode(nodeOption.children[i]);
                        });
                        that._changeNodeCallbacks.fire(nodeOption.children[i], dtd);
                    });
                });

                $node.appendTo($breadcrumb);

                if (nodeOption.children) {
                    //如果子节点需要打开，则打开之. 但是只打开第一个为 open 为 true 的
                    var children = nodeOption.children;
                    for (var i = 0; i < children.length; i++) {
                        if (children[i].open) {
                            that.createNode(children[i]);
                            break;
                        }
                    }
                }
            }
        };
    })();
})(jQuery);
