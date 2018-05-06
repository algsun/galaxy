// lines 在右侧的列表显示, dom 操作方法
function LinesDom() {
}
//所有标记段
LinesDom.lines = new Array();
$(function () {

    (function () {
        //鼠标进入的时候，显示自己隐匿兄弟
        $(document).on("mouseenter", "#lines li", function () {
            var $this = $(this);
            $this.addClass("selected");
            $this.find("button.close").show();
            $this.find(".buttons").show();
            $this.siblings().each(function () {
                $(this).find("button.close").hide();
                $(this).find(".buttons").hide();
                $(this).removeClass("selected");
            });
        });
    })();

    // 添加一条线
    LinesDom.addLine = function (lineName, newLine) {
        var that = this;

        LinesDom.lines.push(newLine);
        var $linesDom = $("#lines");
        var newLineDom = [
            "<li>",
            "<span class='mark-segment-name'>", lineName, "</span>",
            "<button type='button' class='close'>×</button>",
            "<div class='buttons' style='overflow: hidden;'>",
            "<span class='btn-group inline-block' data-toggle='buttons-radio' title='通过方向键移动点坐标' data-placement='right'>",
            "<button class='btn btn-mini btn-success' data-point='a'>起点</button>",
            "<button class='btn btn-mini btn-success' data-point='b'>终点</button>",
            "</span>",
            "<span class='btn-group inline-block' style='display: inline-block;margin-left:10px;'>",
            "<a data-to-graph class='btn btn-mini'", "href='trendChart.action?markId=" + newLine.markSegmentId, "'>图表</a>",
            "</span>",
            "</div>",
            "</li>"
        ].join(' ');
        $linesDom.append(newLineDom);
        var $lineDom = $linesDom.children().last();

        //添加 title 为 tooltip
        $lineDom.find(".btn-group").tooltip();
        //点击 "X" 删除 line
        $lineDom.find("button.close").click(function () {
            var $this = $(this);
            art.dialog({
                id:"remove-line",
                title:"提示",
                content:"删除后，不可恢复。确定删除吗？",
                lock:true,
                okValue:"确定",
                cancelValue:"取消",
                ok:function () {
                    var currentIndex = _.indexOf(LinesDom.lines, newLine);
                    that.removeLine(currentIndex);
                    $lineDom.remove();
                },
                cancel:function () {
                }
            });
        });
        var moveEvent = function (event) { //bind keyboard event, move points of line
            var startPoint = newLine.startPoint;
            var endPoint = newLine.endPoint;
            var newStartPoint = _.clone(startPoint);
            var newEndPoint = _.clone(endPoint);
            var pointName = $(this).attr("data-point");
            var updatePointValue = function (point, event) {
                var newPoint = _.clone(point);
                switch (event.keyCode) {
                    case 37: // left
                        newPoint.x = newPoint.x - 1;
                        break;
                    case 38: // up
                        newPoint.y = newPoint.y - 1;
                        break;
                    case 39: // right
                        newPoint.x = newPoint.x + 1;
                        break;
                    case 40: // down
                        newPoint.y = newPoint.y + 1;
                        break;
                }
                return newPoint;
            };
            //如果是 A 点
            if (pointName == 'a') {
                newStartPoint = updatePointValue(startPoint, event);
            }
            //如果是 B 点
            if (pointName == 'b') {
                newEndPoint = updatePointValue(endPoint, event);
            }
            //如果坐标有变化
            if (!_.isEqual(startPoint, newStartPoint) || !_.isEqual(endPoint, newEndPoint)) {
                newLine.updatePointsAndRender(newStartPoint, newEndPoint);
            }
        };

        //绑定 "点A/B" 点击后，鼠标移动事件
        $lineDom.find("button[data-point]").each(function () {
            var $this = $(this);
            var clickFunction = function () {
                //去掉其他按钮的选中状态
                $lineDom.siblings().each(function () {
                    $(this).find("button[data-point]").removeClass("active");
                });
                //获取焦点，这样 keydown 事件才能启作用
                $this.focus();
            };
            $this.click(clickFunction).keydown(moveEvent);
        });

        //设置 line 的选中状态
        $lineDom.click(function () {
            // TODO 暂时取消选中状态
            /**
             $lineDom.toggleClass("active");
             if ($lineDom.hasClass("active")) {
             newLine.forceSelected(true);
             } else {
             newLine.forceSelected(false);
             }
             */
        });
        // 设置 paper 上的 line 选中状态
        $lineDom.hover(function () {
            newLine.setSelected();
        }, function () {
            newLine.unsetSelected();
        });

        $linesDom.children().first().mouseenter();
    };

    // 根据 line 的索引删除 line
    LinesDom.removeLine = function (index) {
        var line = LinesDom.lines[index];
        if (_.isFunction(LinesDom.removeMarkSegmentListener)) {
            LinesDom.removeMarkSegmentListener(line);
        }
        line.remove();
        LinesDom.lines.splice(index, 1);
    };
    // 清空所有的线
    LinesDom.clear = function () {
        $("#lines").empty();
        LinesDom.lines = new Array();
    };

    // 注册 "删除标记段" 监听器
    LinesDom.registerRemoveMarkSegmentListener = function (callback) {
        LinesDom.removeMarkSegmentListener = callback;
    };
});

