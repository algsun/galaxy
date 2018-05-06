/**
 * @Data 2012-12-23
 * @Time 10:51
 * @Author Wang Yunlong
 */
$(function () {
    var $allCheckbox = $(".all-checkbox");
    var $alertCheckbox = $(".alert-checkbox");
    var $savePrivileges = $(".save-privileges");
    var $switchRoleLi = $(".switch-role");
    var $allModuleCheckbox = $(".all-module-checkbox");
    $allCheckbox.live("click", allCheckboxOnclick);
    $alertCheckbox.live("click", checkboxOnclick);
    $savePrivileges.live("click", saveBtnOnclick);
    $switchRoleLi.live("click", switchRole);
    $allModuleCheckbox.live("click", moduleAllCheckboxOnclick);
});
//功能全选按钮点击相应时间
function allCheckboxOnclick() {
    var $this = $(this);
    var value = $this.find("input[type=checkbox]").attr("checked");
    if (value == undefined) {
        $this.parent().parent().parent().parent().parent().find(".all-module-checkbox").find("input[type=checkbox]").attr("checked", false);
        value = false;
    }
    $this.parent().parent().find("input[type=checkbox]").attr("checked", value);
}
//单项选择checkbox点击事件
function checkboxOnclick() {
    var $this = $(this);
    if ($this.find("input[type=checkbox]").attr("checked") == undefined) {
        $this.parent().parent().find(".all-checkbox").find("input[type=checkbox]").attr("checked", false);
        $this.parent().parent().parent().parent().parent().find(".all-module-checkbox").find("input[type=checkbox]").attr("checked", false);
    }
}
//保存按钮的消息响应事件
function saveBtnOnclick() {
    var $this = $(this);
    var $msgArea = $(".msg-area");
    $msgArea.html("<div class='alert alert-success' style='display: none'>" +
        " <a class='close' data-dismiss='alert'>×</a>" +
        "保存成功" +
        "</div>");
    $(".alert").slideDown();
}
//切换角色
function switchRole() {
    var $this = $(this);
    var currentRoleName = $this.text();
    $(".current-role").text(currentRoleName);
}
//选择模块全部权限
function moduleAllCheckboxOnclick() {
    var $this = $(this);
    var value = $this.find("input[type=checkbox]").attr("checked");
    if (value == undefined) {
        value = false;
    }
    $this.parent().parent().find("input[type=checkbox]").attr("checked", value);
}