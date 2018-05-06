/**
 *<pre>
 *
 *</pre>
 * @author: Wang yunlong
 * @date: 12-12-4 上午10:47
 */
$(function () {
    //页面交互效果处理
    (function () {
        var $baseSite = $("#base-site").find("input");
        $baseSite.live("click", onBaseSiteClick);
        //选择站点是否是基层站点 页面效果处理
        function onBaseSiteClick() {
            var $this = $(this);
            var baseSite = $this.val();
            var $addressArea = $("#address-area");
            var $siteName = $("#site-name").find("input");
            if (baseSite == "true") {
                $addressArea.slideDown("slow");
                $siteName.attr("readonly", true).val("");
                var $province = $("#province");
                getArea(0, $province);
                $("#city").children().remove();
                $("#zone").children().remove();
            } else if (baseSite == "false") {
                $addressArea.slideUp("slow");
                $siteName.attr("readonly", false).val("");
            }
        }
    }(),


        //表单验证
        function () {
            var siteFlag = false;
            var managerEmailFlag = false;
            var flag = true;
            var $siteName = $("#site-name").find("input");
            var $siteManagerEmail = $("#site-manager-email").find("input");
            //基层站点
            var $site = $("#site").find("select");
            var $baseSite = $("#base-site").find("input[checked='checked']");
            $siteName.bind("focusout", onSiteNameValue);
            $siteManagerEmail.bind("focusout", onManagerEmailValue);
            $site.bind("change", onSite);
            $siteName.bind("focusin", onFocus);
            $siteManagerEmail.bind("focusin", onFocus);
            $site.bind("focusin", onFocus);
            $("#logicgroup-submit").click(function () {
                onSiteNameValue();
                onManagerEmailValue();
                if ($baseSite.val() == "false") {
                    return siteFlag && managerEmailFlag;
                } else {
                    onSite();
                    return siteFlag && managerEmailFlag && flag;
                }
            });
            //站点名称验证
            function onSiteNameValue() {
                var siteName = $.trim($siteName.val());
                if (siteName.length < 1) {
                    $siteName.siblings(".msg").text("站点名称不能为空");
                    siteFlag = false;
                } else if (siteName.length > 25) {
                    $siteName.siblings(".msg").text("站点名称长度不能大于25");
                    siteFlag = false;
                } else {
                    siteFlag = true;
                }
            }

            //邮箱验证
            function onManagerEmailValue() {
                var email = $.trim($siteManagerEmail.val());
                if (email.length < 1) {
                    $siteManagerEmail.siblings(".msg").text("管理员邮箱不能为空");
                    managerEmailFlag = false;
                } else {
                    var filter = /^[a-zA-Z0-9_\-\.]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
                    if (email.match(filter)) {
                        $.post("emailEnable.action", {email: email}, function (result) {
                            managerEmailFlag = result.enable;
                            if (!result.enable) {
                                $siteManagerEmail.siblings(".msg").text("该邮箱已经被使用");
                            }
                        });
                    } else {
                        managerEmailFlag = false;
                        $siteManagerEmail.siblings(".msg").text("您的电子邮件格式不正确");
                    }
                }
            }

            //若是绑定基层站点 对基层站点选择验证
            function onSite() {
                var site = $site.val();
                if (site == "0") {
                    $site.siblings(".msg").text("添加绑定基层站点时,该项不能为空");
                    flag = false;
                } else {
                    var $siteNameSelect = $site.find("option:selected");
                    if ($siteNameSelect.val() != "0") {
                        $siteName.val($siteNameSelect.text());
                        $siteName.siblings(".msg").text("");
                    }
                    flag = true;
                }
            }

            //输入框是去焦点
            function onFocus() {
                $(this).siblings(".msg").text("");
            }
        }());
});

