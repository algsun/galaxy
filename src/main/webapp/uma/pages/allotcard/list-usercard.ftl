<#--
  - 电子卡管理页面
  -@author li.jianfei
  -@time  2013-4-11 13:16
  -->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title><#if queryType==0>未发卡人员<#elseif queryType==1>已发卡人员</#if> - 人员行为</title>

<#include "../_common/common-css.ftl">
    <link rel="stylesheet" href="../assets/select2/3.3.1/select2.css">

</head>
<body>
<#--页面以及标题-->
<#assign currentTopPrivilege = "uma:allotcard">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">
<#-- TODO 注释掉的代码，删掉 -->
<#--二级菜单-->
<#--<#include "../_common/sub-navs.ftl">-->
<#--<@subNavs "uma:allotcard:no"></@subNavs>-->

<#--自定义二级菜单-->
    <div class="row m-t-10">
        <div class="span12">
            <ul class="nav nav-pills">
            <#if security.isPermitted("uma:allotcard:haveCardUser")>
                <li <#if queryType==1>class="active"</#if>>
                    <a href="haveCardUser.action?queryType=1">已发卡人员</a>
                </li>
            </#if>
            <#if security.isPermitted("uma:allotcard:noCardUser")>
                <li <#if queryType==0>class="active"</#if>>
                    <a href="noCardUser.action?queryType=0">未发卡人员</a>
                </li>
            </#if>
            </ul>
        </div>
    </div>
<#-- TODO 消息提示，统一风格 -->
<#-- 消息提示 -->
<#include "../_common/message.ftl">
<@messageTooltip></@messageTooltip>


<#--your content-->
<#--过滤条件-->
    <div class="row">
        <div class="span12">

            <form class="form-inline well well-small"
                  action="<#if queryType==0>noCardUser.action<#elseif queryType==1>haveCardUser.action</#if>"
                  method="post">
                <label for="userName">姓名</label>
                <input type="text" name="userName" id="userName" value="${userName!}">
            <#if queryType==1>
                <input type="hidden" name="lowPower" value="<#if lowPower>1<#else>0</#if>"/>
                <label class="checkbox">
                    <input id="lowPower" type="checkbox"
                           <#if lowPower>checked="checked"</#if> />只显示低电压卡
                </label>
            </#if>
                <button type="submit" class="btn">查询</button>
            </form>

        </div>
    </div>


<#--数据列表-->
    <div class="row">
        <div class="span12">
            <table class="table">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>姓名</th>
                    <th>性别</th>
                <#if queryType==1>
                    <th>卡号</th>
                    <th>卡电量</th>
                    <th>最后活动时间</th>
                </#if>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <#list personList as person>
                <tr>
                    <td>${person_index+1}</td>
                    <td>
                        <a id="userName" data-toggle="popover" title='人员信息'
                           data-container="#container" data-trigger="hover"
                           data-html="true" data-content="
                            <img id='showImage'
                            <#if person.photo??>
                                 src='${blackholeResourcesUrl}/user/${person.photo}'
                            <#else>
                                 src='../blackhole/images/head_portrait.png'
                            </#if>
                                 style='width: 120px;height: 120px;clear: both;display: block;margin: auto;'>
                            <br/>
                            <strong>邮箱：</strong><span>${blackhole.obscuredEmail(person.email!)}</a>
                            ">
                        ${person.personName!}
                        </a>
                    </td>
                    <#if person.sex == 1 >
                        <td>女</td>
                    </#if>
                    <#if person.sex == 2 >
                        <td>男</td>
                    </#if>
                    <#if queryType==1>
                        <td>${person.cardSn}</td>
                        <#assign vol=(person.voltage/15)*100/>
                        <td>${vol?string("0")}%</td>
                        <td>
                            <#if person.lastTime??>
                                ${person.lastTime?string("yyyy-MM-dd HH:mm:ss")}
                            </#if>
                        </td>
                    </#if>
                    <td>

                        <#if queryType==0>
                            <#if security.isPermitted("uma:allotcard:sendCard")>

                                <span id="allot" href="sendCard.action?personId=${person.id}&cardSn="
                                      class="btn btn-mini"><i class="icon-barcode"></i> 发卡</span>
                            </#if>
                        </#if>

                        <#if queryType==1>
                            <#if security.isPermitted("uma:allotcard:changeCard")>
                                <span id="changeCard" href="changeCard.action?personId=${person.id}&cardSn="
                                      class="btn btn-mini" )><i
                                    class="icon-repeat"></i> 换卡</span>
                            </#if>
                            <#if security.isPermitted("uma:allotcard:recedeCard")>
                                <span id="recedeCard" href="recedeCard.action?personId=${person.id}"
                                      class="btn btn-mini btn-danger"><i
                                        class="icon-trash icon-white"></i> 退卡</span>
                            </#if>
                        </#if>
                    </td>
                </tr>
                </#list>
                </tbody>
            </table>

        </div>
    </div>
<#include "../_common/pagging.ftl">
<#--分页-->
<#if queryType==0>
    <#assign url ="noCardUser.action?userName=${userName!}"/>
<#elseif queryType==1>
    <#assign url ="haveCardUser.action?userName=${userName!}">
</#if>
<@pagination url index pageCount "index"></@pagination>

<#if personList?size == 0>
    <h4>暂无数据</h4>
</#if>
</div>

<div id="container"></div>
<div class="hide">
    <div id="allotcard">
        <div class="container-fluid">
            <div class="row-fluid">
                <div class="span12">
                    <div class="control-group">
                        <label class="control-label">电子卡号</label>

                        <div class="controls">
                            <input style="width: 300px" type="text" name="cardSN" id="cardSN" value=""/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<#--your js-->

<script type="text/javascript" src="../assets/select2/3.3.1/select2.min.js"></script>
<script type="text/javascript">
    $(function () {
        var select2Data = initCard();
        bdSelect2(select2Data);

    <#--为发卡按钮绑定事件-->
        $("[id=allot]").on("click", function () {
            var url = $(this).attr("href");
            cardDialog(url);
        });

    <#--为换卡按钮绑定事件-->
        $("[id=changeCard]").on("click", function () {
            var url = $(this).attr("href");
            cardDialog(url);
        });
    <#--为退卡按钮绑定事件-->
        $("[id=recedeCard]").click(function () {
            var $this = $(this);
            art.dialog({
                id: 'info',
                title: '提示',
                content: '确定退卡?',
                okValue: '退卡',
                ok: function () {
                    window.location.href = $this.attr("href");
                },
                cancelValue: '取消',
                cancel: function () {
                }
            });
            return false;
        });


        /*获取的本来就是数组，你要获取数组的数值就需要遍历*/
        $("a[id='userName']").mouseover(function () {
            $(this).popover('show');
            return false;
        }).mouseout(function () {
                    $(this).popover('hide');
                });

        /* 记录低电压选中状态 */
        $("#lowPower").click(function () {
            var $this = $(this);
            $("input[name=lowPower]").val($this.attr("checked") == "checked" ? 1 : 0);
        });
    });

    // 初始化激发器 设备 信息
    function initCard() {
        var cardList;
        $.ajax({
            async: false,
            type: "POST",
            url: "initCard.action",
            success: function (result) {
                cardList = result.deviceList;
            }
        });
        var data = new Array(cardList.length);
        for (var i = 0; i < cardList.length; i++) {
            data[i] = {id: cardList[i].sn, text: cardList[i].sn};
        }
        return data;
    }

    function bdSelect2(select2Data) {
        $("#cardSN").select2({
            data: select2Data,
            formatNoMatches: function () {
                return "未找到匹配的卡号";
            },
            placeholder: "请选择卡号",
            allowClear: true
        });
    }
    function cardDialog(url) {
        art.dialog({
            title: "请输入电子卡号",
            content: $("#allotcard")[0],
            fixed: true,
            locked: true,
            okValue: "确定",
            ok: function () {
                $("#cardSN").select2("close");
                if ($("#cardSN").select2("val") == "") {
                    return false;
                } else {
                    window.location = url + $("#cardSN").select2("val");
                }
            },
            cancelValue: "取消",
            cancel: function () {
                $("#cardSN").select2("close");
            }
        });

    }
</script>

<#--其他公共资源-->
<#include "../_common/last-resources.ftl">
</body>
</html>
