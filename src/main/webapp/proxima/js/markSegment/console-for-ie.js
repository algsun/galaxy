
//修复IE没有console日志问题
(function () {
    window.console = {log:function () {
    }};
})();

