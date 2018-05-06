<#--
文物入藏

@author Wang rensong
@time  13-5-14
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>文物入藏 - 资产管理</title>

<#include "../_common/common-css.ftl">
<#include "/common/pages/common-tag.ftl">
<#-- TODO 可能没用 @gaohui 2013-06-20 -->

    <style type="text/css">
        .query-input {
        <#if !inputView>
            display: none;
        </#if>
        }

        .open-input, .close-input {
            float: right;
            cursor: pointer;
            color: #dd4444;
        }

        /*照片上传 input file 样式*/
        #uploadImg {
            font-size: 12px;
            overflow: hidden;
            position: absolute
        }

        #imageInput {
            position: absolute;
            z-index: 100;
            margin-left: -180px;
            font-size: 60px;
            opacity: 0;
            filter: alpha(opacity=0);
            margin-top: 5px;
        }

        .removeDiv {
            border: 1px solid #808080;
            border-radius: 5px;
        }

    </style>

</head>
<body>
<#--页面以及标题-->
<#assign currentTopPrivilege = "orion:culturalRelic">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">
    <div class="row">
        <div class="span12">
            <fieldset>
                <legend>
                    <a class="go-back" href="queryRelic.action" title="返回">
                        <i class="mw-icon-prev"></i>入藏
                    </a>
                </legend>
            </fieldset>
        </div>
    </div>

    <div class="row">
        <div class="span12">
            <form id="relicForm" class="form-horizontal" action="addRelic.action"
                  method="post"
                  enctype="multipart/form-data" onsubmit="">
                <input type="hidden" name="inputView" value="${inputView?string("true","false")}">

                <div class="control-group">
                    <label class="control-label" for="">
                        <em class="required">*</em> 名称
                    </label>

                    <div class="controls">
                        <input type="text" name="relic.name" id="relicName">
                        <span id="name-input-help" class="help-inline"></span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="">
                        总登记号
                    </label>

                    <div class="controls">
                        <input type="text" name="relic.totalCode" id="totalCode">
                        <span id="totalCode-input-help" class="help-inline"></span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="">
                         编目号
                    </label>

                    <div class="controls">
                        <input type="text" name="relic.catalogCode" id="catalogCode">
                        <span id="catalogCode-input-help" class="help-inline"></span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="">
                        分类号
                    </label>

                    <div class="controls">
                        <input type="text" name="relic.typeCode" id="typeCode">
                        <span id="typeCode-input-help" class="help-inline"></span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="">
                        <em class="required">*</em>件数
                    </label>

                    <div class="controls">
                        <input type="text" name="relic.count" id="count">
                        <span id="count-input-help" class="help-inline"></span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">库房位次</label>

                    <div class="controls">
                        <input id="zoneId" type="hidden" name="relic.zone.id">
                        <input class="inline-block" id="parentZoneInput" type="text" name="zoneame">

                        <span id="zone-input-help" class="help-inline"></span>
                    </div>
                </div>

                <div class="control-group">
                    <span id="zoneSelector">
                         <label class="control-label">
                             <em class="required">*</em>质地
                         </label>
                          <div class="controls">
                          <@selectOptionTexture listTexture/>  <i class="icon-pencil" id="texture"></i>
                              <span id="texture-input-help" class="help-inline"></span>
                          </div>
                    </span>
                </div>

                <div class="control-group">
                  <span id="zoneSelector">
                    <label class="control-label">
                        <em class="required">*</em>年代
                    </label>
                      <div class="controls">
                      <@selectOptionEra listEra/>  <i class="icon-pencil" id="era"></i>
                          <span id="era-input-help" class="help-inline"></span>
                      </div>
                   </span>
                </div>

                <div class="control-group">
                      <span id="zoneSelector">
                         <label class="control-label">
                             <em class="required">*</em>级别
                         </label>
                      <div class="controls">
                      <@selectOptionLevel listLevel/> <i class="icon-pencil" id="level"></i>
                          <span id="level-input-help" class="help-inline"></span>
                      </div>
                      </span>

                </div>
                <div class="close-input m-v-10 <#if !inputView>hide</#if>" id="close" title="收起更多信息"> 收起</div>
                <div class="open-input m-v-10 <#if inputView>hide</#if>" id="open" title="展开更多信息"> 展开更多信息</div>

                <div class="query-input">

                    <div class="control-group">
                        <label class="control-label" for="imageInput">照片</label>

                        <div class="controls">

                            <div class="m-t-10">
                            <span id="uploadImg">
                              <input type="file" name="image" id="imageInput" size="1"/>
                              <a class="btn btn-mini">上传图片</a>
                            </span>
                                <span class="help-inline m-l-80" id="imageInput-help"
                                      style="margin-left:80px;"> </span>
                            </div>
                        </div>
                    </div>

                <#list proList as property>
                    <div class="control-group">
                        <label class="control-label" for="">
                        ${property.cnName!}
                        </label>

                        <div class="controls">
                            <input type="hidden" name="relicPropertyList[${property_index}].property.id"
                                   value="${property.id!}">
                            <#if property.propertyType=4>
                                <select name="relicPropertyList[${property_index}].propertyValue"
                                        id="${property.enName}">
                                </select>
                            <#elseif property.propertyType==3>
                                <textarea style="width: 205px;height:50px; "
                                          name="relicPropertyList[${property_index}].propertyValue"></textarea>
                            <#elseif property.propertyType==2>
                                <input class="input-medium Wdate" type="text"
                                       name="relicPropertyList[${property_index}].propertyValue"
                                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
                            <#elseif property.propertyType==1>
                                <input type="text" name="relicPropertyList[${property_index}].propertyValue">
                            </#if>
                            <span id="${property.enName!}-input-help" class="help-inline"></span>

                        </div>
                    </div>
                </#list>

                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary" id="relicSubmit">保存</button>
                    <a class="btn" id="reset" style="display: none">重置 </a>
                    <a href="queryRelic.action" class="btn">返回</a>
                </div>

            </form>
        </div>
    </div>
</div>

<div class="hide">
    <div id="zoneTreeDialog" class="span4" style="height:400px;overflow:auto">
        <div id="zoneTree" class="ztree"></div>
        <p class="help-block m-t-10 red"></p>
    </div>

</div>

<div id="editTexture" class="hide">
<#list listTexture as texture>
    <a class="btn btn-small btn-link removeDiv m-r-10 m-t-10"
       style="text-decoration: none">
        <#if texture.deleteAble == 1>
            <i class="icon-remove" name="removeTexture" data-texture-name="${texture.name}"></i>
        </#if>
        <span>
        ${texture.name}
        </span>
    </a>
</#list>
    <input class="m-t-5 hide" placeholder="添加质地，回车保存" id="addTexture"/>
    <span style="color: red"></span>
    <i class="icon-plus m-t-5" id="addTex"></i>
</div>

<div id="editEra" class="hide">
<#list listEra as era>
    <a class="btn btn-small btn-link removeDiv m-r-10 m-t-10"
       style="text-decoration: none">
        <i class="icon-remove" name="removeEra" data-era-name="${era.name}"></i>
                    <span>
                    ${era.name}
                    </span>
    </a>
</#list>
    <input class="m-t-5 hide" placeholder="添加年代，回车保存" id="addEra"/>
    <span style="color: red"></span>
    <i class="icon-plus m-t-5" id="toAddEra"></i>
</div>

<div id="editLevel" class="hide">
<#list listLevel as level>
    <a class="btn btn-small btn-link removeDiv m-r-10 m-t-10"
       style="text-decoration: none">
        <i class="icon-remove" name="removeLevel" data-level-name="${level.name}"></i>
                    <span>
                    ${level.name}
                    </span>
    </a>
</#list>
    <input class="m-t-5 hide" placeholder="添加级别，回车保存" id="addLevel"/>
    <span style="color: red"></span>
    <i class="icon-plus m-t-5" id="addLev"></i>
</div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<@scriptTag "../assets/artDialog/5.0.1-7012746/artDialog.min.js"/>
<!--表单验证-->
<@scriptTag "js/checkRelic.js"/>
<!--js-->
<@scriptTag "js/addRelic.js"/>

<#--your js-->
<script type="text/javascript">

    /**
     * 更多文物信息显示动画效果
     */
    $(function () {
        var $queryInput = $(".query-input");
        var $open = $(".open-input");
        var $close = $(".close-input");
        var $inputView = $("input[name=inputView]");
        $open.click(function () {
            $queryInput.slideDown();
            $inputView.val("true");
            $open.hide();
            $close.show();
        });
        $close.click(function () {
            $queryInput.slideUp();
            $inputView.val("false");
            $close.hide();
            $open.show();
        });

        // 选择区域
        (function () {
            // ztree 树配置
            var setting = {
                view: {
                    showLine: false
                },
                async: {
                    enable: true,
                    url: '../blackhole/zone/children.json',
                    autoParam: ["id=zoneId"]
                }
            };

            var showDialog = function ($zoneInput, zoneTree) {
                var $help = $("#zoneTreeDialog .help-block");
                art.dialog({
                    id: "zoneTreeDialog",
                    title: "选择区域",
                    content: $("#zoneTreeDialog")[0],
                    fixed: true,
                    okValue: "确定",
                    ok: function () {
                        var nodes = zoneTree.getSelectedNodes();
                        if (nodes.length == 0) {
                            $help.empty().append("请选择区域");
                            return false;
                        }
                        var node = nodes[0];
                        $zoneInput.val(node.name);
                        $("#zoneId").val(node.id);
                    },
                    cancelValue: "取消",
                    cancel: function () {
                        $help.empty();
                    },
                    button: [
                        {
                            value: "清空",
                            callback: function () {
                                $zoneInput.val('');
                                $("#zoneId").val('');
                            }
                        }
                    ]
                });
            };

            // 区域输入框获取焦点时
            $("#parentZoneInput").focus(function () {
                var $this = $(this);
                $.getJSON("../blackhole/zone/children.json", {zoneId: ""}, function (result) {
                    // 初始化树
                    $.fn.zTree.init($('#zoneTree'), setting, result);
                    var zoneTree = $.fn.zTree.getZTreeObj("zoneTree");
                    // 初始化弹出框
                    showDialog($this, zoneTree);
                });
            });

        })();

        // 收藏单位 change
        (function () {
            // 收藏单位初始化
            (function initInstitution() {
                var options = "<option value=''>请选择</option>";
                $.post("institution/institutions.json", function (institutions) {
                    for (var i = 0; i < institutions.length; i++) {
                        options += "<option value='" + institutions[i].id + "'>" + institutions[i].name + "</option>";
                    }
                    $("#institution").html(options);
                });
            })();

            // 库房 和 收藏单位 二级联动
            $("#institution").change(function () {
                var $this = $(this);
                var institutionId = $this.val();
                var options = "<option value=''>请选择</option>";
                $.post("institution/institutions/" + institutionId + "/storehouses.json", function (storehouses) {
                    for (var i = 0; i < storehouses.length; i++) {
                        options += "<option value='" + storehouses[i].id + "'>" + storehouses[i].roomName + "</option>";
                    }
                    $("#storehouse").html(options);
                });
            });
        })();

    });
</script>
</body>
</html>

<#--加载时代-->
<#macro selectOptionEra listEra>
<select style="width: 150px" name="relic.era.id" id="eraId">
    <#if listEra?size != 0>
        <option value="0">--请选择--</option>
        <#list listEra as era>
            <option value="${era.id}">${era.name}</option>
        </#list>
    </#if>
</select>
</#macro>

<#--加载级别-->
<#macro selectOptionLevel listLevel>
<select style="width: 150px" name="relic.level.id" id="levelId">
    <#if listLevel?size != 0>
        <option value="0">--请选择--</option>
        <#list listLevel as level>
            <option value="${level.id}">${level.name}</option>
        </#list>
    </#if>
</select>
</#macro>

<#--加载质地-->
<#macro selectOptionTexture listTexture>
<select style="width: 150px" name="relic.texture.id" id="textureId">
    <#if listTexture?size != 0>
        <option value="0">--请选择--</option>
        <#list listTexture as texture>
            <option value="${texture.id}">${texture.name}</option>
        </#list>
    </#if>
</select>
</#macro>

<#--加载区域(库房位次)-->
<#macro selectOptionZone listZone>
<select style="width: 150px" name="textureName" id="zoneId">
    <#if listZone?size != 0>
        <option value="0">--请选择--</option>
        <#list listZone as zone>
            <option value="${zone.id}">${zone.name}</option>
        </#list>
    </#if>
</select>
</#macro>