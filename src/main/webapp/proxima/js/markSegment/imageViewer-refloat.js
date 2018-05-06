$(function () {
    var $elasticColumn = $(".elastic");
    var $canvasWrapper = $("#canvas-wrapper");
    var refloat = function () {
        var parentWidth = $elasticColumn.width();
        var width = $canvasWrapper.width();
        //如果 width 小于 parentWidth , 那么 $canvasWrapper 右浮动; 否则消除 右浮动
        if (width < parentWidth) {
            if ($canvasWrapper.hasClass("float-right")) {
                return;
            }
            $canvasWrapper.addClass("float-right");
        } else {
            $canvasWrapper.removeClass("float-right");
        }
    };
    refloat();
    $(window).resize(function () {
        refloat();
    });
});