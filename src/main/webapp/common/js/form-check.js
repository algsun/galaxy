/**
 * 表单验证提示工具方法
 * 
 * @gaohui 2012-09-20
 * 
 * dependencies: jQuery
 */
(function($){
	if (window.App === undefined || window.App === null) {
	    window.App = {};
	}
	
	//清除提示
    App.helpClear = function ($help) {
        $help.empty();
    };

    //错误提示
    App.helpError = function ($help, msg) {
        var content = "<i class='mw-icon-cancel'></i> " + msg;
        $help.empty().append(content);
    };

    //正确提示
    App.helpOk = function ($help, msg) {
        var content = "<i class='mw-icon-ok'></i> " + msg;
        $help.empty().append(content);
    };

    //提示
    App.help = function ($help, msg) {
        var content = msg;
        $help.empty().append(content);
    };
})(jQuery);