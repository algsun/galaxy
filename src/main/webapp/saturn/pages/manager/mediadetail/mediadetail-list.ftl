<#assign title="展示内容">
<#-- 当前权限标识 -->

<#macro head>
<style type="text/css">
    .label {
        width: 80px;
        text-align: center;
    }
</style>
</#macro>

<#macro content>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            <form class="form-inline well well-small" action="manager/findMediaDetailByConditions" method="get"
                  style="margin-top: 15px;">
                <input type="hidden" value="1" name="currentPageIndex"/>
                启用状态:
                <select name="enable" id="enableForSelect">
                    <option value="0" selected>请选择</option>
                    <option value="1" <#if enable == 1>selected="true" </#if>>启用</option>
                    <option value="2" <#if enable == 2>selected="true" </#if>>禁用</option>
                </select>
                材质:
                <select name="material" id="materialForSelect">
                    <option value="0" selected>全部</option>
                    <#list textures as texture>
                        <option value="${texture.id}"
                                <#if texture.id == material?number>selected="true"</#if>>${texture.name}</option>
                    </#list>
                </select>
                展示标题:
                <input id="" type="text" name="detailTitle" value="${detailTitle}">
                展示内容:
                <input id="" type="text" name="detailContent" value="${detailContent}">
                <input id="selectSubmit" class="btn btn-small" type="submit" value="查询">
            </form>
            <#if security.isPermitted("saturn:manager:mediaDetails:add")>
                <a class="btn f-r save" href="manager/toSaveMediaDetail">新增</a>
            </#if>
            <table id="" class="table table-striped">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>启用</th>
                    <th>展示标题</th>
                    <th>主图片</th>
                    <th>副图片</th>
                    <th>展示内容</th>
                    <th>录入人</th>
                    <th>录入时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                    <#list mediaDetails as mediaDetail>
                    <tr>
                        <td>${mediaDetail.line}</td>
                        <td>
                            <#if mediaDetail.enable==1>
                                启用
                            <#else>
                                禁用
                            </#if>
                        </td>
                        <td>${mediaDetail.detailTitle!}</td>
                        <td>
                            <img style="width: 40px;height: 40px;border: 3px;"
                                 src="${imagePath}\${mediaDetail.detailImage}"
                                 alt=""/>
                        </td>
                        <td>
                            <img style="width: 40px;height: 40px;border: 3px"
                                 src="${imagePath}\${mediaDetail.detailSubImage}" alt=""/>
                        </td>
                        <td>${mediaDetail.detailContent!}</td>
                        <td>${mediaDetail.createBy!}</td>
                        <td>${mediaDetail.createOn?string("yyyy-MM-dd")}</td>
                        <td>
                            <#if security.isPermitted("saturn:manager:mediaDetails:update")>
                                <button value="${mediaDetail.id}" class="btn btn-mini btn-primary edit">修改</button>
                            </#if>
                            <#if security.isPermitted("saturn:manager:mediaDetails:delete")>
                                <button value="${mediaDetail.id}" class="btn btn-mini btn-danger deleteMediaDetail">删除
                                </button>
                            </#if>
                        </td>
                    </tr>
                    </#list>
                </tbody>
            </table>

            <#include "/common/pages/pagging.ftl">
            <#assign url = "manager/findMediaDetailByConditions?enable=${enable!}&material=${material!}&detailTitle=${detailTitle!}&detailContent=${detailContent!}"/>
            <@pagination url, currentPageIndex, pageCount,"currentPageIndex"/>
        </div>
    </div>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/artDialog/5.0.1-7012746/artDialog.min.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript" src="../assets/bootstrap/2.3.1/js/bootstrap.min.js"></script>
<script type="text/javascript">
    $(function () {
        $(".deleteMediaDetail").click(function () {
            var id = this.value;
            art.dialog({
                id: "delete",
                fixed: true,
                title: "删除确认",
                content: "确定要删除吗？",
                okValue: "确定",
                ok: function () {
                    window.location.href = "manager/deleteMediaDetail?mediaDetailId=" + id;
                },
                cancelValue: "取消",
                cancel: function () {
                }
            });
        });

        $(".edit").click(function () {
            var id = this.value;
            window.location.href = "manager/toEditMediaDetail?mediaDetailId=" + id;
        });
    })
</script>
</#macro>
