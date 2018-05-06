/**
 * 整个系统的基础部分, 提供业务系统命名空间支持.
 *
 * 因为目前 js 没有进行模块化管理(例如 require.js, sea.js)，为了防止 js 混杂的情况下命名空间冲突。
 * 现在只能约束大家尽量不要声明"全局" 的变量和方法, 而是通过 App.require() 或者 App.put() 的方法统一交给 App 来管理。
 *
 * 例子：
 * a.js
 * <pre>
 *     // 声明 foo
 *     var foo = App.require("foo");
 *     foo.doSomething = function(){};
 * </pre>
 *
 * b.js
 * <pre>
 *     // 使用 foo
 *     var foo = App.require("foo"); // 或者 App("foo")
 *     foot.doSomething();
 * </pre>
 *
 * test.html
 * <pre>
 *     <script type="text/javascript" src="a.js"></script>
 *     <script type="text/javascript" src="b.js"></script>
 * </pre>
 * @author gaohui
 * @date 12-11-2 10:46
 */

(function () {

    var _App = {
        _namespaces:{},
        //获取；如果不存在则创建
        require:function (namespace) {
            var obj = this._namespaces[namespace];
            //如果不存在, 则创建对象
            if (obj === void 0) { //参考 underscore.isUndefined
                obj = {};
                this._namespaces[namespace] = obj;
            }
            return obj
        },

        //放值；如果存在则覆盖
        put:function (namespace, obj) {
            var oldObj = this._namespaces[namespace];
            this._namespaces[namespace] = obj;
            return oldObj;
        },

        //获取
        get:function (namespace) {
            return  this._namespaces[namespace];
        },

        //是否存在
        has:function (namespace) {
            var obj = this._namespaces[namespace];
            //如果不存在, 则创建对象
            return !(obj === void 0); //参考 underscore.isUndefined
        }
    };

    // App.require 的别名
    var App = function (namespace) {
        return _App.require(namespace);
    };

    var property;
    for (property in _App) {
        App[property] = _App[property];
    }

    //暴露到全局变量
    window.App = App;

})();
