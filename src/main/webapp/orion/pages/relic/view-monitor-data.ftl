<!DOCTYPE HTML>
<html >
<head>
    <#include "../_common/common-head.ftl">
    <title>文物入藏 - 资产管理</title>

    <#include "../_common/common-css.ftl">
    <#include "/common/pages/common-tag.ftl">
    <link href="../assets/magnificpopup/magnific-popup.css" rel="stylesheet" type="text/css"/>
    <link type="text/css" rel="stylesheet" href="../blueplanet/css/base.css">
    <style type="text/css">
        .margin2 {
            margin-left: 30px;
        }
        .td-title {
            font-size: 16px;
            font-weight: bolder;
        }

        .td-content {
            font-size: 14px;
            font-weight: bolder;

        }
        .image-link {
            cursor: -webkit-zoom-in;
            cursor: -moz-zoom-in;
            cursor: zoom-in;
        }
        #li-box {
            position: absolute;
        }
        #li-box li {
            list-style: none;
            position: absolute;
            background: #fff;
            /*border: solid 1px #ccc;*/
            top: 0;
            width: 160px;
        }
        li {
            -webkit-transition: all .7s ease-out .1s;
            -moz-transition: all .7s ease-out;
            -o-transition: all .7s ease-out .1s;
            transition: all .7s ease-out .1s
        }
        .square{
            width: 80;
            height: 40px;
            border-radius: 5px;
            background-color: lightgrey;
            margin-bottom: 8px;
        }
    </style>
    <#include "../_common/common-js.ftl">
    <script type="text/javascript" src="../assets/magnificpopup/jquery.magnific-popup.min.js"></script>
</head>
<body>
<#--页面以及标题-->
<#assign currentTopPrivilege = "orion:culturalRelic">
<#include "../_common/header.ftl">
<div id="gcontent" class="container m-t-50">
    <fieldset>
        <legend>
            <a class="go-back" href="queryRelic.action" title="返回">
                <i class="mw-icon-prev"></i>监测数据
            </a>
        </legend>
    </fieldset>
    <div class="row">
        <div class="span8">
            <div class="m-v-10">
                <span class="td-title">${relic.name}</span>
                <span class="m-l-20 td-content">总登记号：${relic.totalCode}</span>
                <span class="m-l-20 td-content">时代：${relic.era.name}</span>
                <span class="m-l-20 td-content">级别：${relic.level.name}</span>
                <span class="m-l-20 td-content">质地：${relic.texture.name}</span>
                <input type="hidden" id="relicId" value="${relicId}">
                <input type="hidden" id="locationBasePath" value="${locationBasePath}">
            </div>
        </div>
    </div>
    <div class="row">
        <div class="span12" style="margin-bottom: 20px">
            <table>
                <tr>
                    <td style="text-align: center; height: 400px;width: 942px; border: 1px solid;">
                        <div id="carousel-example-generic" class="carousel slide" align="center">
                            <div class="carousel-inner">
                            <#list relic.photos! as photo>
                                <div class="item <#if photo_index==0>active</#if>">
                                    <a href="${picturesBasePath}/${siteId!}/${relic.id?c}/${photo.path!}" class="with-caption image-link">
                                        <img src="${picturesBasePath}/${siteId!}/${relic.id?c}/${photo.path!}"
                                             style="max-width:175px;max-height:350px;"/>
                                    </a>
                                </div>
                            </#list>
                            </div>
                            <a class="left carousel-control" href="#carousel-example-generic"
                               data-slide="prev" style="top:50%;">‹</a>
                            <a class="right carousel-control" href="#carousel-example-generic"
                               data-slide="next" style="top:50%;">›</a>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div class="row">
        <div class="span12">
            <ul id="li-box"></ul>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function(){
        //图片全屏
        $('.with-caption').magnificPopup({
            type: 'image',
            loseBtnInside: false,
            mainClass: 'mfp-with-zoom mfp-img-mobile',
            image : {
                verticalFit: true,
                titleSrc: function(item){
                    var pinItURL = "https://pinterest.com/pin/create/button/";
                    pinItURL += '?url=' + 'http://dimsemenov.com/plugins/magnific-popup/';
                    pinItURL += '&media=' + item.el.attr('href');
                    return '<a class="pin-it" href="'+pinItURL+'" target="_blank"><img src="https://assets.pinterest.com/images/pidgets/pin_it_button.png"/></a>';
                }
            },
            gallery:{enabled:true}
        });

        //实时监测数据显示
        var $ul=$("#li-box");
        var locationBasePath=$("#locationBasePath").val();
        function getJson() {
            $ul.empty();
            $.ajaxSetup({
                async : false
            });
            $.get("getMonitorData",{"relicId" : $("#relicId").val()},function (monitorData,textstatus) {
                $.each(monitorData,function (locationId,location) {
                    var $location=$("<li><a class='square' href='"+locationBasePath+"blueplanet/location/"+locationId+"'>"+location.locationName+"</a></li>");
                    console.log($location);
                    $.each(location.locationSensorInfoMap,function (sensorPhysicalid,locationDataVO) {
                        var $sensor;
                        if(locationDataVO.state==1){
                            $sensor=$("<p class='m-l-5'><i class='mw-icon-sensor sensor-"+sensorPhysicalid+"'></i>"+locationDataVO.cnName+":"+locationDataVO.sensorPhysicalValue+locationDataVO.units+"</p>");
                        }else if(locationDataVO.state==2){
                            $sensor=$("<p class='m-l-5'><i class='mw-icon-sensor sensor-"+sensorPhysicalid+"'></i>"+locationDataVO.cnName+":"+locationDataVO.sensorPhysicalValue+locationDataVO.units+"<span class='icon-arrow-down margin2'></span></p>");
                        }else if(locationDataVO.state==3){
                            $sensor=$("<p class='m-l-5'><i class='mw-icon-sensor sensor-"+sensorPhysicalid+"'></i>"+locationDataVO.cnName+":"+locationDataVO.sensorPhysicalValue+locationDataVO.units+"<span class='icon-arrow-up margin2'></span></p>");
                        }
                        $location.append($sensor);
                    });
                    $ul.append($location);
                });
            });
        };

        //先显示一次，之后每隔15分钟刷新一次
        getJson();
//        setInterval(getJson,150000);


        var margin_top = 8;
        var margin_left = 140;//这里设置间距
        var li = $("#li-box li");//这里是所有li元素

        if (li.length > 0) {
            var li_W = li[0].offsetWidth + margin_left;//取区块的实际宽度（包含间距，这里使用源生的offsetWidth函数，不适用jQuery的width()函数是因为它不能取得实际宽度，例如元素内有pandding就不行了）
        }

        function liBoxStream() {//定义成函数便于调用

            var h = [];//记录区块高度的数组
            var n = 3;//窗口的宽度除以区块宽度就是一行能放几个区块
            for (var i = 0; i < li.length; i++) {//有多少个li就循环多少次
                li_H = li[i].offsetHeight;//获取每个li的高度
                if (i < n) {//n是一行最多的li，所以小于n就是第一行了
                    h[i] = li_H;//把每个li放到数组里面
                    li.eq(i).css("top", 0);//第一行的Li的top值为0
                    li.eq(i).css("left", i * li_W);//第i个li的左坐标就是i*li的宽度
                }
                else {
                    min_H = Math.min.apply(null, h);//取得数组中的最小值，区块中高度值最小的那个
                    minKey = getarraykey(h, min_H);//最小的值对应的指针
                    h[minKey] += li_H + margin_top;//加上新高度后更新高度值
                    li.eq(i).css("top", min_H + margin_top);//先得到高度最小的Li，然后把接下来的li放到它的下面
                    li.eq(i).css("left", minKey * li_W);	//第i个li的左坐标就是i*li的宽度
                }
            }
        };

        /* 使用for in运算返回数组中某一值的对应项数(比如算出最小的高度值是数组里面的第几个) */
        function getarraykey(s, v) {
            for (k in s) {
                if (s[k] == v) {
                    return k;
                }
            }
        };

        liBoxStream();
    });
</script>
<#--<#include "../_common/footer.ftl">-->
<#include "../_common/last-resources.ftl">
</body>
</html>