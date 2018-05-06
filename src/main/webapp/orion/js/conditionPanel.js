/**
 * 藏品信息查询标签面板
 *
 * @author wang.geng
 * @date 2014-05-27
 *
 */
$(function () {

    //鼠标移到标签上标签的字体颜色
    var fontColor = "#ffffff";

    //鼠标移到标签上标签的背景颜色
    var bgColor = "#5FBCEB";

    //初始化
    init();

    //标签单击事件
    labelClick();

    //"全部"标签单击事件
    allSelectedClick();

    /**
     * init()方法，初始化条件面板的选中，状态保留
     */
    function init() {
        //初始化标签选中
        var alltds = $(".selected");
        $.each(alltds, function (index, td) {
            var firstLineId
            var enName = td.parentElement.classList[0];
            var isfirstLine = isFirstLine(td);
            var $enName = $("#" + enName);
            if (isfirstLine) {
                firstLineId = $("." + enName).attr("id");
            } else {
                firstLineId = enName;
            }
            var $hiddenValue = $("#hiddenValue" + firstLineId).val();
            var hiddenValues = $hiddenValue.split(",");
            var value;
            var attrs = td.attributes;
            for (var j = 0; j < attrs.length; j++) {
                if (attrs[j].name == 'data-value') {
                    value = attrs[j].value;
                }
            }
            for (var k = 0; k < hiddenValues.length; k++) {
                if (value.trim() == hiddenValues[k].trim()) {
                    td.firstChild.style.backgroundColor = bgColor;
                    td.firstChild.style.color = fontColor;
                    if ($enName.children().size() != 0) {
                        $("." + firstLineId).css('display', '');
                        $("." + firstLineId + "firstLine > td ").css('border-bottom', 'none');
                        $("." + firstLineId + "lastLine > td ").css('border-bottom', '');
                        $("." + firstLineId + "firstLine > td > small").text("收起");
                        visible = false;
                    }
                    $("#" + firstLineId + " .allSelected > span").css('background-color', fontColor);
                    $("#" + firstLineId + " .allSelected > span").css('color', bgColor);
                }
            }
        });
    }

    /**
     * 单击事件，css样式改变，同时根据新的条件查询结果
     */
    function labelClick() {
        $(".selected").click(function () {

            //条件英文名称
            var attrValueName;

            var enName = this.parentElement.classList[0];

            //判断当前行是第一行，还是下面的隐藏行
            var $enName = $("#" + enName);
            var isfirstLine = isFirstLine(this);
            //因为用class当做ID来取子元素是取不到的，所以判断其为第一行
            if (isfirstLine) {
                var firstLineId = $("." + enName).attr("id");
                attrValueName = firstLineId;
            }
            //使用class当做Id可以取到子元素，判断其为隐藏行
            else{
                attrValueName = enName;
            }
            $("#"+attrValueName +" .allSelected > span").css('background-color',fontColor);
            $("#"+attrValueName +" .allSelected > span").css('color',bgColor);
            this.firstChild.style.backgroundColor = bgColor;
            this.firstChild.style.color = fontColor;

            //查询操作
            var value;
            var attrs = this.attributes;
            for (var j = 0; j < attrs.length; j++) {
                if (attrs[j].name == 'data-value') {
                    value = attrs[j].value;
                }
            }
            var hiddenValue = $("#hiddenValue" + attrValueName).val();
            var hiddenValues = hiddenValue.split(",");
            var params = "";
            for (var i = 0; i < hiddenValues.length; i++) {
                if (hiddenValues[i] == value) {
                    hiddenValues[i] = "";
                    value = "";
                } else {
                    params = params + "," + hiddenValues[i];
                }
            }
            $("#hiddenValue" + attrValueName).val(params+","+value);
            $("#queryRelicFrom").submit();
        });
    }

    /**
     * "全部"标签被单击事件
     */
    function allSelectedClick() {
        $(".allSelected").click(function () {
            //点击全部，修改对应的样式
            var enName = this.parentElement.classList[0];
            var firstLineId = $("." + enName).attr("id");
            //清除当前第一行的样式
            clearBgcolor($("#" + firstLineId).children());
            //清除隐藏行的样式
            var hidtrs = $("." + firstLineId);
            $.each(hidtrs, function (index, htd) {
                var hidtrstd = htd.children;
                clearBgcolor(hidtrstd);
            });

            //点击全部，同时，设置查询条件并执行查询操作
            $("#hiddenValue" + firstLineId).val("");
            $("#queryRelicFrom").submit();
            this.firstChild.style.backgroundColor = bgColor;
            this.firstChild.style.color = fontColor;
        });
    }

    /**
     * 遍历清除子元素的backgroundColor样式
     */
    function clearBgcolor(elementList) {
        $.each(elementList, function (index, element) {
            element.style.backgroundColor = '';
        });
    }

    function isFirstLine(td){
        var attrs = td.parentElement.attributes;
        var value = "";
        $.each(attrs,function(index, attr){
            var name = attr.name;
            if(name == "line-seq"){
                value = attr.value;
            }
        });
        return value == "first";
    }
});