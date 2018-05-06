/**
 * 用于新闻添加,编辑页面:
 * 1. 初始化编辑器
 * 2. 表单验证
 *
 * @author @gaohui
 * @date 2013-05-11
 */

var editor;
(function(){
    var KIND_EDITOR_CONFIG = {
        resizeType : 1,
        allowPreviewEmoticons : false,
        allowImageUpload : true,
        items : [
            'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
            'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
            'insertunorderedlist', '|', 'emoticons', 'image', 'link','media']
    };
    KindEditor.ready(function (K) {
        editor = K.create('#content', KIND_EDITOR_CONFIG);
    });
})();

$(function () {
    // 表彰验证
    (function () {
        // 表单提交时
        $("#post-form").submit(function () {
            // 检查日期
            var $createDate=$("input[name='createDate']");
            if ($("#createDate").val().length<1) {
                App.helpError($createDate.next(), "请输入日期");
                return false;
            }else{
                App.helpOk($createDate.next(), '');
            }

            var $title = $("input[name='title']");
            if (!$.trim($title.val())) {
                App.helpError($title.next(), "请输入标题");
                return false;
            }else{
                App.helpOk($title.next(), '');
            }

            var $content = $("textarea[name='content']");
            // 统计字数
            if (editor.count('text') <= 0) {
                App.helpError($content.next(), "请输入内容");
                return false;
            }else{
                App.helpOk($content.next(), '');
            }

            return true;
        });
    })();
});
