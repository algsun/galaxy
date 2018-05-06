<#--
藏品卡

@author Wang rensong
@time  13-5-11
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>藏品卡 - 资产管理</title>
<#include "../_common/common-css.ftl">


<#include  "/common/pages/common-tag.ftl">
    <link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/magnificpopup/magnific-popup.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet"
          href="../assets/jquery-ui/1.9.2/css/jquery-ui.css"
          type="text/css"/>
    <style type="text/css">
        .td-title {
            width: 120px;
            height: 40px;
            text-align: center;
            /*font-weight: bold;*/
        }

        .td-content {
            width: 140px;
            height: 40px;
            padding-left: 10px;

        }
        .image-link {
            cursor: -webkit-zoom-in;
            cursor: -moz-zoom-in;
            cursor: zoom-in;
        }
    </style>

</head>
<body>
<#--页面以及标题-->
<#assign currentTopPrivilege = "orion:culturalRelic">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">

<#--your content-->

<fieldset>
    <legend>
        <a class="go-back" href="queryRelic.action?index=${index}&totalCodeAsCondition=${totalCode!}&name=${name!}&catalogCode=${catalogCode!}&typeCode=${typeCode!}&labelId=${labelId!}&zoneId=${zoneId!}&eraId=${eraId!}&levelId=${levelId!}&textureId=${textureId!}&state=${state!}" title="返回">
            <i class="mw-icon-prev"></i>藏品卡
        </a>
    </legend>
</fieldset>
<div id="alert"></div>
<div class="controls" style="background-color:whitesmoke;padding:10px;">
<#list relicLabels as relicLabel>
    <button class="btn btn-small m-r-20 m-t-10 m-b-10" name="${relicLabel.name}">
    ${relicLabel.name}
    <#if security.isPermitted("orion:culturalRelic:label:delete")>
        <i data-action-id="1" data-action-type="0" class="icon-remove"></i>
    </#if>
    </button>
</#list>
<#if security.isPermitted("orion:culturalRelic:label:add")>
    <input id='relicTagText' name='relic.tag' class='m-r-20 m-t-10 m-b-10'/>
</#if>
</div>

<div class="row m-t-10">
    <div class="span12">
        <div class="form-inline m-b-10">
            <div class="btn-group m-r-10">
                <input id="hiddenCon" type="hidden" value="pre=3&index=${index}&totalCodeAsCondition=${totalCodeAsCondition!}&name=${name!}&catalogCode=${catalogCode!}&typeCode=${typeCode!}&labelId=${labelId!}&zoneId=${zoneId!}&eraId=${eraId!}&levelId=${levelId!}&textureId=${textureId!}&state=${state!}&"/>
            <#if preCode?? && preCode != "">
                <a title="上一个" class="btn"
                   href="relicCard.action?pre=1&index=${index}&totalCode=${preCode}&totalCodeAsCondition=${totalCodeAsCondition!}&name=${name!}&catalogCode=${catalogCode!}&typeCode=${typeCode!}&labelId=${labelId!}&zoneId=${zoneId!}&eraId=${eraId!}&levelId=${levelId!}&textureId=${textureId!}&state=${state!}"><i
                        class="icon-arrow-left"></i></a>
            <#else>
                <a title="上一个" class="btn disabled"><i class="icon-arrow-left"></i></a>
            </#if>

            <#if nextCode?? && nextCode != "">
                <a title="下一个" class="btn"
                   href="relicCard.action?pre=2&index=${index}&totalCode=${nextCode}&totalCodeAsCondition=${totalCodeAsCondition!}&name=${name!}&catalogCode=${catalogCode!}&typeCode=${typeCode!}&labelId=${labelId!}&zoneId=${zoneId!}&eraId=${eraId!}&levelId=${levelId!}&textureId=${textureId!}&state=${state!}"><i
                        class="icon-arrow-right"></i></a>
            <#else>
                <a title="下一个" class="btn disabled"><i class="icon-arrow-right"></i></a>
            </#if>
            </div>

            <label class="inline-block">总登记号</label>

            <div id="total-code-input" class="input-append">
                <input class="input-small" id="appendedInputButton" type="text" value="${relicCard.totalCode!}"
                       onchange="keyEnter('pre=3&index=${index}&totalCodeAsCondition=${totalCodeAsCondition!}&name=${name!}&catalogCode=${catalogCode!}&typeCode=${typeCode!}&labelId=${labelId!}&zoneId=${zoneId!}&eraId=${eraId!}&levelId=${levelId!}&textureId=${textureId!}&state=${state!}&')">

                <div class="btn-group">
                    <button id="total-code-go-button" class="btn" type="button">跳到</button>
                    <button class="btn dropdown-toggle" data-toggle="dropdown">
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu">
                        <li><a href="relicArchive.action?totalCode=${relicCard.totalCode}&pre=0&index=${index}&totalCodeAsCondition=${totalCodeAsCondition!}&name=${name!}&catalogCode=${catalogCode!}&typeCode=${typeCode!}&labelId=${labelId!}&zoneId=${zoneId!}&eraId=${eraId!}&levelId=${levelId!}&textureId=${textureId!}&state=${state!}"><i
                                class="icon-list-alt"></i> 档案</a></li>
                    </ul>
                </div>
            </div>

            <label class="m-l-10">制卡人:</label>
            <span style="margin-right: 20px;width: 100px;padding-right: 10px;">  ${(relicCard.relicPropertyMap.maker.propertyValue)!}</span>
            <label class="m-l-10">制卡时间:</label>
            <span> ${(relicCard.relicPropertyMap.makeDate.propertyValue)!}</span>

            <div class="f-r" style="margin-top: 5px;">
            <#if security.isPermitted("orion:culturalRelic:update")>
                <a href="toUpdateRelic.action?relicId=${relicCard.id?c}">
                    <i class="icon-edit"></i> 编辑
                </a>
            </#if>
            </div>
        </div>
    </div>
</div>


<div class="row m-t-0">
    <div class="span12">
        <table border="1" style="border: 2px solid #000;">
            <tr>
                <td class="td-title">总登记号</td>
                <td class="td-content">${relicCard.totalCode!}</td>
                <td class="td-title">编目号</td>
                <td class="td-content">${relicCard.catalogCode!}</td>
                <td class="td-title">分类号</td>
                <td class="td-content">${relicCard.typeCode!}</td>
                <td class="td-title">库房位次</td>
                <td class="td-content">
                ${(relicCard.zone.name)!}
                </td>
            </tr>

            <tr>
                <td class="td-title">时代</td>
                <td class="td-content">${relicCard.era.name!}</td>
                <td class="td-title">名称</td>
                <td colspan="5" class="td-content">${relicCard.name}</td>
            </tr>

            <tr>
                <td class="td-title">件数</td>
                <td class="td-content">${relicCard.count!}</td>
                <td class="td-title">级别</td>
                <td class="td-content">${(relicCard.level.name)!}</td>

                <td class="td-title">附件</td>
                <td colspan="3" class="td-content">
                ${(relicCard.relicPropertyMap.annex.propertyValue)!}
                </td>
            </tr>

            <tr>
                <td class="td-title">质地</td>
                <td class="td-content">${(relicCard.texture.name)!}</td>
                <td class="td-title">重量</td>
                <td colspan="2" class="td-content">
                ${(relicCard.relicPropertyMap.weight.propertyValue)!}
                </td>

                <td colspan="3" rowspan="6" style="text-align: center;">
                <#if relicCard.photos?size != 0 >
                <div id="myCarousel" class="carousel slide" style="width: 421px;margin-bottom: 0px;">
                    <div class="carousel-inner">
                        <#list relicCard.photos! as photo>
                            <div class="item <#if photo_index==0>active</#if>" align="center">
                                <a href="${picturesBasePath}/${siteId!}/${relicCard.id?c}/${photo.path!}" class="with-caption image-link">
                                    <img src="${picturesBasePath}/${siteId!}/${relicCard.id?c}/${photo.path!}"
                                         style="max-width:421px;max-height:300px"/>
                                </a>
                            </div>
                        </#list>
                    </div>
                <#-- 调低左右切换按钮位置 -->
                    <a class="left carousel-control" href="?totalCode=${totalCode}#myCarousel"
                       data-slide="prev" style="top:50%;">‹</a>
                    <a class="right carousel-control" href="?totalCode=${totalCode}#myCarousel"
                       data-slide="next" style="top:50%;">›</a>

                <#elseif relicCard.photos?size==0>
                    <h4> 暂无图片 </h4>
                </div>
                </#if>
                </td>
            </tr>

            <tr>
                <td class="td-title">尺寸</td>
                <td colspan="4" class="td-content">
                ${(relicCard.relicPropertyMap.sizes.propertyValue)!}
                </td>
            </tr>
            <tr>
                <td class="td-title">现状</td>
                <td colspan="4" class="td-content">
                ${(relicCard.statusQuo.quoInfo)!}
                </td>
            </tr>
            <tr>
                <td class="td-title">来源</td>
                <td colspan="4" class="td-content">
                ${(relicCard.relicPropertyMap.source.propertyValue)!}
                </td>
            </tr>

            <tr>
                <td class="td-title">照片号</td>
                <td class="td-content">
                ${(relicCard.relicPropertyMap.photoCode.propertyValue)!}
                </td>
                <td class="td-title">拓片号</td>
                <td colspan="2" class="td-content">
                ${(relicCard.relicPropertyMap.rubbingCode.propertyValue)!}
                </td>
            </tr>

            <tr style="height: 150px">
                <td class="td-title">简述</td>
                <td colspan="4" class="td-content">
           <span style="padding-top: 10px;">
           ${(relicCard.relicPropertyMap.brief.propertyValue)!}
           </span>
                </td>
            </tr>

            <tr class="tr">
                <td class="td-title">著录及文献</td>
                <td colspan="7" class="td-content">
                ${(relicCard.relicPropertyMap.literature.propertyValue)!}
                </td>
            </tr>
            <tr class="tr">
                <td class="td-title">铭文题跋</td>
                <td colspan="7" class="td-content">
                <#list relicCard.inscriptions! as inscription>
                ${inscription.info!}     </br>
                </#list>
                </td>
            </tr>
            <tr>
                <td class="td-title">出土地点或产地</td>
                <td colspan="5" class="td-content">
                ${(relicCard.relicPropertyMap.origin.propertyValue)!}
                </td>
                <td class="td-title">出土时间</td>
                <td colspan="2" class="td-content">
                ${(relicCard.relicPropertyMap.originDate.propertyValue)!}
                </td>
            </tr>
            <tr class="tr">
                <td class="td-title">鉴定意见</td>
                <td colspan="7" class="td-content">
                ${(relicCard.appraisal.expertOpinion)!}
                </td>
            </tr>
            <tr>
                <td class="td-title">鉴定人</td>
                <td colspan="5" class="td-content">
                ${(relicCard.appraisal.examiner)!}
                </td>
                <td class="td-title">鉴定时间</td>
                <td colspan="2" class="td-content">
                <#if relicCard.appraisal??>
                      ${relicCard.appraisal.appraisalDate?string("yyyy-MM-dd")}
                    </#if>
                </td>
            </tr>

            <tr style="height: 100px">
                <td class="td-title">修复情况</td>
                <td colspan="7" class="td-content">
                ${(relicCard.restore.restoreInfo)!}
                </td>
            </tr>
            <tr style="height: 100px">
                <td class="td-title">事故记录</td>
                <td colspan="7" class="td-content">
                ${(relicCard.accident.accidentInfo)!}
                </td>
            </tr>
        </table>
    </div>
</div>
</div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<script type="text/javascript" src="../assets/magnificpopup/jquery.magnific-popup.min.js"></script>
<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
<script type="text/javascript"
        src="../assets/jquery-ui/1.9.2/js/jquery-ui.custom.min.js"></script>
<script type="text/javascript" src="../orion/js/editRelicLabel.js"></script>
<script type="text/javascript">
    $(function () {

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
                    return ' &middot; <a class="pin-it" href="'+pinItURL+'" target="_blank"><img src="https://assets.pinterest.com/images/pidgets/pin_it_button.png" /></a>';
                }
            },
            gallery:{enabled:true}
        });

        //自动补全
        var availableLabels = [
        <#list allLabels as lab>
            "${lab.name}",
        </#list>
        ];

        editRelicLabel(${relicCard.id?c}, availableLabels);

    <#-- 幻灯片切换 -->
        $("#myCarousel").carousel();

        var conditions = $("#hiddenCon").val();
        // 总登记号跳转
        $("#total-code-go-button").click(function () {
            // TODO basedUrl @gaohui 2013-05-21
            location.href = "relicCard.action?"+conditions+"totalCode=" + $("#total-code-input > input").val();
        });

        if (event.keyCode == 13) {
            location.href = "relicCard.action?"+conditions+"totalCode=" + $("#total-code-input > input").val();
        }

    });

    function keyEnter(conditions) {
        location.href = "relicCard.action?"+conditions+"totalCode=" + $("#total-code-input > input").val();
    }

</script>

<#include "../_common/last-resources.ftl">
</body>
</html>