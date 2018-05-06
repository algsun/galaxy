<#--
外展管理，装箱单

@author li.jianfei
@date 2013-09-27
-->
<#assign title="线路预设 - 外展管理">
<#-- 当前权限标识 -->

<#include "/common/pages/common-tag.ftl">
<#include  "../_common/helper.ftl">

<#macro head>
<#--<meta name="viewport" content="initial-scale=1.1,user-scalable=no">-->
<style type="text/css">
    /* 解决 google map  https://mts0.googleapis.com/vt load fail 问题 */
    body {
        margin: 0;
        height: 100%;
        width: 100%;
        position: absolute;
    }

    #map {
        height: 100%;
        width: 100%;
    }
</style>
</#macro>


<#macro content>
<div class="row">
    <div class="span12">
        <fieldset>
            <legend>
                <a class="go-back" href="queryExhibitionStateList/exhibition/${exhibitionId}" title="返回">
                    <i class="mw-icon-prev"></i>线路预设
                </a>
            </legend>
        </fieldset>
    </div>
</div>

<div class="row-fluid">
    <#assign siteLng=Session["currentLogicGroup"].site.lngAmap />
    <#assign siteLat=Session["currentLogicGroup"].site.latAmap />
    <div class="span3">
        <div class="row-fluid">
            <div class="span12">
                <div class="control-group">

                    <input id="editable" type="hidden" value="${editable?string('true','false')}"/>
                    <input id="exhibitionId" type="hidden" value="${exhibitionId}"/>
                    <input id="path" type="hidden" value='${path!}'>
                    <input id="point" type="hidden" value='${point!}'>
                    <input id="Lat" type="hidden" value="${siteLat}"/>
                    <input id="Lng" type="hidden" value="${siteLng}"/>

                    <input type="text" name="destination" data-lng="${siteLng}" data-lat="${siteLat}" class="span10"
                           disabled
                           value='${Session["currentLogicGroup"].site.siteName}'/>
                </div>

                <div id="destinations">
                    <#if (pathList?size>0)>
                        <#list pathList as path>
                        <#if path.destinationName!="">
                            <@destinationInput path_index,path.destinationName, path.longitude, path.latitude/>
                        </#if>
                    </#list>
                    <#else>
                        <@destinationInput/>
                    </#if>
                </div>
                <div class="control-group">
                    <input type="text" name="destination" data-lng="${siteLng}" data-lat="${siteLat}" class="span10"
                           disabled
                           value='${Session["currentLogicGroup"].site.siteName}'/>
                </div>

                <div class="control-group">
                    <select name="pathType" id="pathType" class="span10">
                        <option <#if pathType==0>selected="selected"</#if> value="0">最快捷模式</option>
                        <option <#if pathType==1>selected="selected"</#if> value="1">最经济模式</option>
                        <option <#if pathType==2>selected="selected"</#if> value="2">最短距离模式</option>
                    </select>
                </div>

                <div class="span10 control-group">
                    <a id="addDestination" class="text-info">
                        <small>添加更多目的地</small>
                    </a>
                    <button id="btnQuery" class="btn btn-mini pull-right" style="margin-right: 25px;">查询线路
                    </button>
                </div>
                <div class="span10 control-group">
                    <button id="btnSave" class="btn btn-mini btn-success pull-right" style="margin-right: 25px;">保存线路
                    </button>
                </div>
            </div>
        </div>
        <div class="row-fluid">
            <div id="total" class="span12"></div>
        </div>
    </div>
    <div class="span9">
        <div id="map"></div>
    </div>

</div>

<div id="destinationLayout" class="hide">
    <div id="destination" class="control-group">
        <input name="destination" type="text" class="span10" placeholder="请输入目的地"/><span
            class="span2 pull-right"
            id="deleteDestination"
            style="cursor: pointer;"><i
            class="icon-remove"></i></span>

        <div id="result1"></div>
    </div>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=9016240f787e72ac5d8fed77b3b5cabc">
</script>
<script type="text/javascript">
    var map;

    $(document).on("keydown", "input[name='destination']", function (e) {
        // 这个判断是为了兼容所有浏览器，使 e 能被所有浏览器所解析
        if (!e) var e = window.event;
        // 这里就是要执行的方法体，其中 e 就是键盘对象
        // 其中 e.keyCode（Code中C一定要大写） 就是按下的键的键值
        // alert("键盘按键的 keycode 是 " + e.keyCode);
        keydown(e, this.id.replace("keyword", ""));
    });


    //输入提示
    function autoSearch(index) {
        var keywords = document.getElementById("keyword" + index).value;
        var auto;
        //加载输入提示插件
        AMap.service(["AMap.Autocomplete"], function () {
            var autoOptions = {
                city: "" //城市，默认全国
            };
            auto = new AMap.Autocomplete(autoOptions);
            //查询成功时返回查询结果
            if (keywords.length > 0) {
                auto.search(keywords, function (status, result) {
                    autocomplete_CallBack(result, index);
                });
            }
            else {
                document.getElementById("result" + index).style.display = "none";
            }
        });
    }


    //输出输入提示结果的回调函数
    function autocomplete_CallBack(data, index) {
        var resultStr = "";
        var tipArr = data.tips;
        if (tipArr && tipArr.length > 0) {
            for (var i = 0; i < tipArr.length; i++) {
                resultStr += "<div id='divid" + (i + 1) + "' onmouseover='openMarkerTipById(" + (i + 1)
                + ",this)' onclick='selectResult(" + i + "," + index + "," + tipArr[i].adcode + ",\"" + tipArr[i].district.toString() + "\",\"" + tipArr[i].id.toString() + "\",\"" + tipArr[i].name.toString() + "\"," + tipArr[i].location + ")' onmouseout='onmouseout_MarkerStyle(" + (i + 1)
                + ",this)' style=\"font-size: 13px;cursor:pointer;padding:5px 5px 5px 5px;\"" + "data=" + tipArr[i].adcode + ">" + tipArr[i].name + "<span style='color:#C1C1C1;'>" + tipArr[i].district + "</span></div>";
            }
        }
        else {
            resultStr = " π__π 亲,人家找不到结果!<br />要不试试：<br />1.请确保所有字词拼写正确<br />2.尝试不同的关键字<br />3.尝试更宽泛的关键字";
        }
        document.getElementById("result" + index).curSelect = -1;
        document.getElementById("result" + index).tipArr = tipArr;
        document.getElementById("result" + index).innerHTML = resultStr;
        document.getElementById("result" + index).style.display = "block";
    }

    //输入提示框鼠标滑过时的样式
    function openMarkerTipById(pointid, thiss) {  //根据id打开搜索结果点tip
        thiss.style.background = '#CAE1FF';
    }

    //输入提示框鼠标移出时的样式
    function onmouseout_MarkerStyle(pointid, thiss) {  //鼠标移开后点样式恢复
        thiss.style.background = "";
    }


    //从输入提示框中选择关键字并查询
    function selectResult(i, index, adcode, district, id, name, lng, lat) {
        var tip = {
            id: id,
            name: name,
            district: district,
            adcode: adcode,
            location: {
                lng: lng,
                lat: lat,
                getLng: function () {
                    return lng;
                },
                getLat: function () {
                    return lat;
                }
            }
        };
        if (i < 0) {
            return;
        }
        if (navigator.userAgent.indexOf("MSIE") > 0) {
            document.getElementById("keyword" + index).onpropertychange = null;
            document.getElementById("keyword" + index).onfocus = focus_callback;
        }
        //截取输入提示的关键字部分
        var text = document.getElementById("divid" + (i + 1)).innerHTML.replace(/<[^>].*?>.*<\/[^>].*?>/g, "");
        var cityCode = document.getElementById("divid" + (i + 1)).getAttribute('data');
        document.getElementById("keyword" + index).value = text;
        document.getElementById("result" + index).style.display = "none";

        $("#keyword" + index).data("lng", lng);
        $("#keyword" + index).data("lat", lat);

        addmarker(tip);
        map.setFitView();

    }

    //定位选择输入提示关键字
    function focus_callback(index) {
        if (navigator.userAgent.indexOf("MSIE") > 0) {
            document.getElementById("keyword" + index).onpropertychange = autoSearch;
        }
    }


    //添加查询结果的marker&infowindow
    function addmarker(d) {
        var lngX = d.location.getLng();
        var latY = d.location.getLat();
        var markerOption = {
            map: map,
            icon: "http://webapi.amap.com/images/marker_sprite.png",
            position: new AMap.LngLat(lngX, latY)
        };
        var mar = new AMap.Marker(markerOption);
    }

    function keydown(event, id) {
        autoSearch(id);
    }


    $(function () {

        var mRoute;
        var path = [];
        var exhibitionId = $("#exhibitionId").val();
        var sitePosition = new AMap.LngLat($("#Lng").val(), $("#Lat").val());

        // 解决 amap 与 bootstrap 冲突问题
        $(window).resize(function () {
            var h = $(window).height(),
                    offsetTop = 20; // Calculate the top offset
            $('#map').css('height', (h - offsetTop));
        }).resize();


        var mapOptions = {
            //地图根据容器大小缩放
            resizeEnable: true,
            //传入view对象，即二维地图显示视口
            //center: 地图中心点
            //zoom：地图显示的缩放级别
            //layers: 设置默认图层为卫星图
            view: new AMap.View2D({
                center: sitePosition,
                zoom: 12
            })
        };
        map = new AMap.Map("map", mapOptions);
        var marker = new AMap.Marker({
            map: map,
            icon: "http://webapi.amap.com/images/marker_sprite.png",
            position: sitePosition
        });


        // 禁用保存按钮，未选择线路时不能直接保存
        $("#btnSave").attr("disabled", "disabled");
        // 处理控件可编辑状态
        function loadEditableState() {
            var editable = $("#editable").val() === 'true';

            // 停用或启用输入框
            $.each($("input[name='destination']"), function (i, input) {
                var $input = $(input);
                if (editable) {
                    $input.removeAttr("disabled");
                    $("#pathType").removeAttr("disabled");
                } else {
                    $input.attr("disabled", "disabled");
                    $("#pathType").attr("disabled","disabled");
                }
            });

            if (!editable) {
                $("#addDestination").hide();
                $("#btnQuery").hide();
                $("#btnSave").hide();
            } else {
                $("#addDestination").show();
                $("#btnQuery").show();
                $("#btnSave").show();
            }
        }

        loadEditableState();

        // 加载预设线路
        function loadPath() {
            var path = [];

            // 是否存在预设线路
            var destinations = JSON.parse($("#path").val());
            if (destinations.length != 0) {
                path.push(sitePosition);
                $.each(destinations, function (i, destination) {
                    path.push(new AMap.LngLat(destination.longitude, destination.latitude));
                });
                path.push(sitePosition);
                map.plugin("AMap.DragRoute", function () {
                    var pathType = $("#pathType").val();
                    if(pathType == 0){
                        mRoute = new AMap.DragRoute(map, path, AMap.DrivingPolicy.LEAST_TIME); //构造拖拽导航类
                    }else if(pathType == 1){
                        mRoute = new AMap.DragRoute(map, path, AMap.DrivingPolicy.LEAST_FEE); //构造拖拽导航类
                    }else if(pathType == 2){
                        mRoute = new AMap.DragRoute(map, path, AMap.DrivingPolicy.LEAST_DISTANCE); //构造拖拽导航类
                    }
                    mRoute.search(); //查询导航路径并开启拖拽导航
                });
            }
        }

        loadPath();


        addOrRemoveDelete();

        // 添加目的地
        $("#addDestination").click(function () {
            var $cloneObj = $("#destinationLayout").children().clone();
            $cloneObj.find("input[name='destination']")[0].id = "keyword" + $("#destinations").children().length;
            $cloneObj.find("#result1")[0].id = "result" + $("#destinations").children().length;
            $("#destinations").append($cloneObj);
            addOrRemoveDelete();
        });

        // 删除目的地
        $(document).on("click", "span[id='deleteDestination']", function () {
            var $this = $(this);
            $this.parent().remove();
            // 添加或隐藏目的地输入框后的删除按钮
            addOrRemoveDelete();
        });

        // 添加或隐藏目的地输入框后的删除按钮
        function addOrRemoveDelete() {
            if ($("#destinations").children().length > 1) {
                if ($("#destination").first().children().length === 1)
                    $("input[name='destination']").first().after($("#destinationLayout").find("#deleteDestination").clone());
            } else {
                $("#deleteDestination").first().remove()
            }
        }

        // 保存预设线路
        $("#btnSave").click(function () {
            var exhibitionId = $("#exhibitionId").val();
            var destinations = $("#path").val() + "";
            var gpsPoints = $("#point").val() + "";
            $.ajaxSetup({traditional: true});
            $.post("routePlanning/exhibition/" + exhibitionId + "/save", {
                path: destinations,
                point: gpsPoints
            }, function (result) {
                if (result.success) {
                    window.location.href = "queryExhibitionStateList/exhibition/" + exhibitionId;
                }
            }, "json");
        });

        // 更新 wayPoints
        $("#btnQuery").click(function () {
            var $destinations = $("input[name='destination']:visible");

            map.clearMap();

            var waypoints = [];
            $.each($destinations, function (i, destination) {
                var $destination = $(destination);
                if ($.trim(destination.value) === "") {
                    $destination.parent().addClass("error");
                    return;
                } else {
                    $destination.parent().removeClass("error");
                    waypoints.push(new AMap.LngLat($destination.data("lng"), $destination.data("lat")));
                }
            });

            map.plugin("AMap.DragRoute", function () {
                var pathType = $("#pathType").val();
                if(pathType == 0){
                    mRoute = new AMap.DragRoute(map, waypoints, AMap.DrivingPolicy.LEAST_TIME); //构造拖拽导航类
                }else if(pathType == 1){
                    mRoute = new AMap.DragRoute(map, waypoints, AMap.DrivingPolicy.LEAST_FEE); //构造拖拽导航类
                }else if(pathType == 2){
                    mRoute = new AMap.DragRoute(map, waypoints, AMap.DrivingPolicy.LEAST_DISTANCE); //构造拖拽导航类
                }
                mRoute.search(); //查询导航路径并开启拖拽导航
                AMap.event.addListener(mRoute, "complete", dragRoute_CallBack); //返回导航查询结果
            });
        });

        function dragRoute_CallBack(result) {
            var path = [];
            var gpsPoints = [];

            $("#btnSave").removeAttr("disabled");
            var pathType = $("#pathType").val();
            var origin = {
                exhibitionId: exhibitionId,
                latitude: result.data.origin.getLat(),
                longitude: result.data.origin.getLng(),
                dataType: 1,
                destinationName: "",
                pathType: pathType
            };
            path.push(origin);
            for (var i = 0; i < mRoute.getWays().length; i++) {
                var waypoint = mRoute.getWays()[i];

                waypoint = {
                    exhibitionId: exhibitionId,
                    latitude: waypoint.getLat(),
                    longitude: waypoint.getLng(),
                    dataType: 1,
                    destinationName: "",
                    pathType: pathType
                };
                path.push(waypoint);
            }
            var destination = {
                exhibitionId: exhibitionId,
                latitude: result.data.destination.getLat(),
                longitude: result.data.destination.getLng(),
                dataType: 1,
                destinationName: "",
                pathType: pathType
            };
            path.push(destination);

            // 匹配位置信息
            var $destinations = $("input[name='destination']:visible");
            $.each($destinations, function (i, destinationDiv) {
                var $destination = $(destinationDiv);
                for (var j = 0; j < path.length; j++) {
                    if (path[j].longitude === $destination.data("lng") && path[j].latitude === $destination.data("lat")) {
                        path[j].destinationName = $destination.val();
                    }
                }
            });
            $.each(mRoute.getRoute(), function (i, point) {
                var gpsPoint = {exhibitionId: exhibitionId, latitude: point.getLat(), longitude: point.getLng()};
                gpsPoints.push(gpsPoint);
            });

            $("#path").val(JSON.stringify(path));
            $("#point").val(JSON.stringify(gpsPoints));
        }

    });

</script>
</#macro>
<#macro destinationInput index=0, destinationName='', lng=0, lat=0>
<div id="destination" class="control-group">
    <input id="keyword${index}" name="destination" type="text" class="span10" placeholder="请输入目的地"
           value='${destinationName}' data-lng="${lng}" data-lat="${lat}"/><span
        class="span2 pull-right"
        id="deleteDestination"
        style="cursor: pointer;"><i
        class="icon-remove"></i></span>

    <div id="result${index}"></div>
</div>
</#macro>