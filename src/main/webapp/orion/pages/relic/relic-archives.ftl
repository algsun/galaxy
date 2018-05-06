<#--
档案

@author Wang.geng
@time  13-5-9
@check @gaohui #4040 2013-06-04
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>藏品信息 - 资产管理</title>

<#include "../_common/common-css.ftl">

    <link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="../assets/jquery-ui/1.9.2/css/jquery-ui.css"/>
    <link href="../assets/magnificpopup/magnific-popup.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">

        .row-edit span {
            font-size: 14px;
        }

        .list ul {
            margin: 0;
        }

        .list ul li {
            font-size: 14px;
            margin-top: 5px;
        }

        a {
            cursor: pointer;
        }

        .row-name a {
            color: #000000;
            cursor: default;
            text-decoration: none;
        }

        textarea {
            margin: 4px 0;
            padding: 0;
        }

        .archives-ele span {
            display: block;
            margin-top: 10px;
        }

        .double .double-one span {
            margin-top: 20px;
        }

        /* table */
        td {
            width: 40px;
            height: 40px;
            text-align: center;
            vertical-align: middle;
        }

        table {
            width: 960px;
        }

        .b > td {
            height: 0;
            margin: 0;
            padding: 0;
        }

        .col1 {
            padding: 20px 0;
            width: 20px;
            margin: 0 auto;
        }

        .careful {
            width: 30px;
            margin: 0 auto;
            font-size: 30%;
            letter-spacing: 0;
        }

        .image-ele {
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


<fieldset>
    <legend>
        <a class="go-back"
           href="queryRelic.action?index=${index}&totalCodeAsCondition=${totalCodeAsCondition!}&name=${name!}&catalogCode=${catalogCode!}&typeCode=${typeCode!}&labelId=${labelId!}&zoneId=${zoneId!}&eraId=${eraId!}&levelId=${levelId!}&textureId=${textureId!}&state=${state!}"
           title="返回">
            <i class="mw-icon-prev"></i>档案
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
                <input id="hiddenCon" type="hidden"
                       value="pre=3&index=${index}&totalCodeAsCondition=${totalCodeAsCondition!}&name=${name!}&catalogCode=${catalogCode!}&typeCode=${typeCode!}&labelId=${labelId!}&zoneId=${zoneId!}&eraId=${eraId!}&levelId=${levelId!}&textureId=${textureId!}&state=${state!}&"/>
            <#if preCode?? && preCode != "">
                <a title="上一个" class="btn"
                   href="relicArchive.action?totalCode=${preCode}&pre=1&index=${index}&totalCodeAsCondition=${totalCodeAsCondition!}&name=${name!}&catalogCode=${catalogCode!}&typeCode=${typeCode!}&labelId=${labelId!}&zoneId=${zoneId!}&eraId=${eraId!}&levelId=${levelId!}&textureId=${textureId!}&state=${state!}"><i
                        class="icon-arrow-left"></i></a>
            <#else>
                <a title="上一个" class="btn disabled"><i class="icon-arrow-left"></i></a>
            </#if>

            <#if nextCode?? && nextCode != "">
                <a title="下一个" class="btn"
                   href="relicArchive.action?totalCode=${nextCode}&pre=2&index=${index}&totalCodeAsCondition=${totalCodeAsCondition!}&name=${name!}&catalogCode=${catalogCode!}&typeCode=${typeCode!}&labelId=${labelId!}&zoneId=${zoneId!}&eraId=${eraId!}&levelId=${levelId!}&textureId=${textureId!}&state=${state!}"><i
                        class="icon-arrow-right"></i></a>
            <#else>
                <a title="下一个" class="btn disabled"><i class="icon-arrow-right"></i></a>
            </#if>
            </div>

            <label class="inline-block">总登记号</label>

            <div id="total-code-input" class="input-append">
                <input class="input-small" id="appendedInputButton" type="text" value="${relicArchives.totalCode}">

                <div class="btn-group">
                    <button id="total-code-go-button" class="btn" type="button">跳到</button>
                    <button class="btn dropdown-toggle" data-toggle="dropdown">
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="relicCard.action?totalCode=${relicArchives.totalCode}&pre=0&index=${index}&totalCodeAsCondition=${totalCodeAsCondition!}&name=${name!}&catalogCode=${catalogCode!}&typeCode=${typeCode!}&labelId=${labelId!}&zoneId=${zoneId!}&eraId=${eraId!}&levelId=${levelId!}&textureId=${textureId!}&state=${state!}"><i
                                    class="icon-comment"></i>
                                藏品卡</a></li>
                    </ul>
                </div>
            </div>

            <label class="inline-block  m-l-20">藏品名称:</label>
            <span class="inline-block p-5 ">${(relicArchives.name)!}</span>

            <label class="il-blk  m-l-20">藏品级别:</label>
            <span class="il-bl p-5 ">${(relicArchives.level.name)!}</span>

            <label class="inline-block m-l-20">分类号:</label>
            <span class="inline-block p-5 ">${(relicArchives.typeCode)!}</span>

            <label class="inline-block m-l-20">档案编号:</label>
            <span class="inline-block p-5 ">${(relicArchives.relicPropertyMap["recordCode"].name)!}</span>

            <div class="f-r">
            <#if security.isPermitted("orion:culturalRelic:update")>
                <a href="toUpdateRelic.action?relicId=${relicArchives.id?c}">
                    <i class="icon-edit"></i> 编辑
                </a>
            </#if>
            </div>
        </div>
    </div>
</div>

<div class="row m-t-0">
<#assign someProperties = relicArchives.relicPropertyMap/>
<div class="span12">

<table border="1px" style="border:2px #000 solid;">
<tr class="b">
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
</tr>
<tr>
    <td colspan="2">名称</td>
    <td colspan="10">${(relicArchives.name)!}</td>
    <td colspan="2">原名</td>
    <td colspan="10">${(someProperties.odleName.propertyValue)!}</td>
</tr>
<tr>
    <td colspan="2">时代</td>
    <td colspan="10">${(relicArchives.era.name)!}</td>
    <td colspan="2">作者</td>
    <td colspan="10">${(someProperties.author.propertyValue)!}</td>
</tr>
<tr>
    <td colspan="2">制作时间</td>
    <td colspan="10">${(someProperties.productionDate.propertyValue)!}</td>
    <td colspan="2">件数</td>
    <td colspan="10">${(relicArchives.count)!}</td>
</tr>
<tr>
    <td colspan="2">质地</td>
    <td colspan="6">${(relicArchives.texture.name)!}</td>
    <td colspan="2">色泽</td>
    <td colspan="6">${(someProperties.color.propertyValue)!}</td>
    <td colspan="2">用途</td>
    <td colspan="6">${(someProperties.use.propertyValue)!}</td>
</tr>
<tr>
    <td colspan="2">来源</td>
    <td colspan="6">${(someProperties.source.propertyValue)!}</td>
    <td colspan="2">重量</td>
    <td colspan="6">${(someProperties.weight.propertyValue)!}</td>
    <td colspan="2">尺寸</td>
    <td colspan="6">${(someProperties.sizes.propertyValue)!}</td>
</tr>
<tr>
    <td>
        <div class="col1">作者小传</div>
    </td>
    <td colspan="23">${(someProperties.authorInfo.propertyValue)!}</td>
</tr>
<tr>
    <td colspan="2">附属物</td>
    <td colspan="10">${(someProperties.appendant.propertyValue)!}</td>
    <td colspan="2">附件</td>
    <td colspan="10">${(someProperties.annex.propertyValue)!}</td>
</tr>
<tr>
    <td colspan="2">入藏日期</td>
    <td colspan="10">${(someProperties.accessionDate.propertyValue)!}</td>
    <td colspan="2">入馆凭证号</td>
    <td colspan="10">${(someProperties.accessionCode.propertyValue)!}</td>
</tr>
<tr>
    <td>
        <div class="col1">形状内容描述</div>
    </td>
    <td colspan="23" class="t-a-l">${(someProperties.describe.propertyValue)!}</td>
</tr>
<tr>
    <td>
        <div class="col1"> 征集经过</div>
    </td>
    <td colspan="23" class="t-a-l">${(someProperties.collectInfo.propertyValue)!}</td>
</tr>
<tr>
    <td>
        <div class="col1">铭记提拔</div>
    </td>
    <td colspan="23" class="t-a-l">
        <ul style="list-style-type: none;">
        <#list relicArchives.inscriptions as inscription>
            <li>
                <blockquote>
                    <p style="font-size:16px;">${inscription.info!}</p>
                </blockquote>
            </li>
        </#list>
        </ul>
    </td>
</tr>
<tr>
    <td>
        <div class="col1">鉴藏印记</div>
    </td>
    <td colspan="23" class="t-a-l">${(someProperties.impress.propertyValue)!}</td>
</tr>
<tr>
    <td>
        <div class="col1">著录及有关资料书目</div>
    </td>
    <td colspan="23" class="t-a-l">${(someProperties.literature.propertyValue)!}</td>
</tr>
<tr>
    <td>
        <div class="col1">流传经历</div>
    </td>
    <td colspan="23" class="t-a-l">
        <ul style="list-style-type: none;">
        <#list relicArchives.roves as rove>
            <li>
                <blockquote>
                    <p style="font-size:16px;">${rove.roveInfo}</p>
                    <small>
                        <cite title="流传日期">${rove.roveDate?string("yyyy-MM-dd HH:mm")}</cite>
                    </small>
                </blockquote>
            </li>
        </#list>
        </ul>
    </td>
</tr>
<tr>
    <td>
        <div class="col1">鉴定意见
        </div>
        ︵
        <div class="careful">注明历次鉴定时间及鉴定人</div>
        ︶
        <div class="m-t-20"></div>
    </td>
    <td colspan="23" class="t-a-l">
        <ul style="list-style-type: none;">
        <#list relicArchives.appraisals as appraisal>
            <li>
                <blockquote>
                    <p style="font-size:16px;">${appraisal.expertOpinion}</p>
                    <small><cite title="鉴定人">${appraisal.examiner}</cite> <cite
                            title="鉴定时间">${appraisal.appraisalDate?string("yyyy-MM-dd")}</cite></small>
                </blockquote>
            </li>
        </#list>
        </ul>
    </td>
</tr>
<tr>
    <td>
        <div class="col1">
            修复装裱复制记录
        </div>
        ︵
        <div class="careful">注明承制单位时间及制作人</div>
        ︶
        <div class="m-t-20"></div>
    </td>
    <td colspan="23" class="t-a-l">
        <ul style="list-style-type: none;">
        <#list relicArchives.restores as restore>
            <li>
                <blockquote>
                    <p style="font-size:16px;">${restore.restoreInfo!}</p>
                    <small>
                        <cite title="承制单位/承制人">${restore.restorers!}</cite>
                        <cite title="修复日期">${restore.restoreDate?string("yyyy-MM-dd")}</cite>
                    </small>
                </blockquote>
            </li>
        </#list>
        </ul>
    </td>
</tr>
<tr>
    <td>
        <div class="col1"> 现状记录</div>
        ︵
        <div class="careful">注明年月日</div>
        ︶
        <div class="m-t-20"></div>
    </td>
    <td colspan="23" class="t-a-l">
        <ul style="list-style-type: none;">
        <#list relicArchives.statusQuos as statusQuo>
            <li>
                <blockquote>
                    <p style="font-size:16px;">${statusQuo.quoInfo!}</p>
                    <small>
                        <cite title="现状日期">${statusQuo.quoDate?string("yyyy-MM-dd")}</cite>
                    </small>
                </blockquote>
            </li>
        </#list>
        </ul>
    </td>
</tr>
<tr>
    <td>
        <div class="col1">备注</div>
    </td>
    <td colspan="23" class="t-a-l">${(someProperties.remark.propertyValue)!}</td>
</tr>
<tr>
    <td>
        <div class="col1">附录</div>
    </td>
    <td colspan="23">${(someProperties.appendix.propertyValue)!}</td>
</tr>
<tr>
    <td colspan="24">绘图（或拓片）</td>
</tr>
<tr>
    <td colspan="24" style="text-align: center; height: 350px;">
    <#if relicArchives.rubbings?? && (relicArchives.rubbings?size>0)>
        <@imageEleView relicArchives.rubbings "rubbing"/>
    <#else>
        无
    </#if>
    </td>
</tr>
<tr>
    <td colspan="24">照片</td>
</tr>
<tr>
    <td colspan="24" style="text-align: center; height: 350px;">
    <#if relicArchives.photos?? && (relicArchives.photos?size>0)>
        <@imageEleView relicArchives.photos "photo"/>
    <#else>
        无
    </#if>
    </td>
</tr>
</table>
<label for="" class="m-t-20">挂接文档</label>


<#list relicArchives.attachments as attachment>
<ul>
    <li>
        <span class="m-r-30">${attachment.attachmentDate?string("yyyy-MM-dd")}</span>
        <a href="downloadFile.action?relicId=${relicArchives.id}&downFileId=${attachment.id}">
            <span style="text-align: left;width:70%">${attachment.path}</span>
        </a>
    </li>
</ul>
</#list>

</div>
</div>
</div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">

<#include "/common/pages/common-tag.ftl">
<@scriptTag "../common/js/util.js"/>
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
                    return '<a class="pin-it" href="'+pinItURL+'" target="_blank"><img src="https://assets.pinterest.com/images/pidgets/pin_it_button.png" /></a>';
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

        editRelicLabel(${relicArchives.id}, availableLabels);

        var conditions = $("#hiddenCon").val();
        (function () {
            // 总登记号跳转
            $("#total-code-go-button").click(function () {
                redirectToRelicArchive(conditions);
            });

            $('#total-code-input > input').keypress(function (e) {
                if (e.keyCode == 13) { // 13 == enter
                    redirectToRelicArchive(conditions);
                }
            });
        })();

        // 不知道什么意思？ @gaohui 2013-06-04
        var $base = $("base");
        $base.attr("href", $base.attr("href") + "relicArchive.action" + conditions);
        $("#myCarousel").carousel();
    });

    // 跳转到对应档案
    function redirectToRelicArchive(conditions) {
        location.href = "relicArchive.action?" + conditions + "totalCode=" + $("#total-code-input > input").val();
    }

</script>

<#include "../_common/last-resources.ftl">
</body>
</html>

<#macro imageEleView photoes type="">
<div class="image-ele">
    <div id="myCarousel-${type}" class="carousel slide" style="margin-bottom: 0">
        <div class="carousel-inner">
            <#list photoes as photo>
                <div class="item <#if photo_index==0>active</#if>" align="center">
                    <a href="${picturesBasePath}/${siteId!}/${relicArchives.id}/${photo.path!}" class="with-caption image-link">
                        <img src="${picturesBasePath}/${siteId!}/${relicArchives.id}/${photo.path!}"
                             style="max-width: 350px;max-height: 350px; text-align: center"/>
                    </a>
                    <#assign author=""/>
                    <#assign date=""/>
                    <#if photo.photoDate??>
                        <#assign date=photo.photoDate/>
                    <#else>
                        <#assign date=photo.rubbingDate/>
                    </#if>
                    <#if photo.photographer??>
                        <#assign author=photo.photographer/>
                    </#if>
                    <div class="">
                        <h4>${author!}</h4>

                        <p>日期：${date?string("yyyy-MM-dd")}&nbsp&nbsp&nbsp 照片比例：${photo.ratio!}</p>
                    </div>
                </div>
            </#list>
        </div>
        <a class="left carousel-control" href="?totalCode=${totalCode}#myCarousel-${type}" data-slide="prev"
           style="top:50%;">‹</a>
        <a class="right carousel-control" href="?totalCode=${totalCode}#myCarousel-${type}" data-slide="next"
           style="top:50%;">›</a>
    </div>
</div>
</#macro>
