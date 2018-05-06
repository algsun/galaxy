<#assign title="展示类别">
<#-- 当前权限标识 -->

<#macro head>
</#macro>

<#macro content>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            <form class="form-inline well well-small" action="manager/findMediaShowByConditions" method="get"
                  style="margin-top: 15px;">
                <input type="hidden" value="1" name="currentPageIndex"/>
                标题:<input id="mediaShowTitle" type="text" name="mediaShowTitle" value="${mediaShowTitle!}">
                摘要:<input id="mediaShowRemark" type="text" name="remark" value="${remark!}">
                <input id="selectSubmit" class="btn btn-small" type="submit" value="查询">
            </form>
            <#if security.isPermitted("saturn:manager:mediaShows:add")>
                <button class="btn save f-r">新增</button>
            </#if>
            <table id="mediaShows" class="table table-striped">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>展示标题</th>
                    <th>展示摘要</th>
                    <th>录入人</th>
                    <th>录入时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                    <#list mediaShows as mediaShow>
                    <tr>
                        <td>${mediaShow_index+1}</td>
                        <td>${mediaShow.title}</td>
                        <td>${mediaShow.remark}</td>
                        <td>${mediaShow.createBy}</td>
                        <td>${mediaShow.createOn?string("yyyy-MM-dd")}</td>
                        <td>
                            <#if security.isPermitted("saturn:manager:mediaShows:update")>
                                <button value="${mediaShow.id}" data-mediaShowTitle="${mediaShow.title}"
                                        data-mediaShowRemark="${mediaShow.remark}"
                                        class="btn btn-mini btn-primary edit">修改
                                </button>
                            </#if>
                            <#if security.isPermitted("saturn:manager:mediaShows:delete")>
                                <button value="${mediaShow.id}" class="btn btn-mini btn-danger deleteMediaShow">删除
                                </button>
                            </#if>
                        </td>
                    </tr>

                    </#list>
                </tbody>
            </table>
            <#include "/common/pages/pagging.ftl">
            <#assign url = "manager/findMediaShowByConditions?mediaShowTitle=${mediaShowTitle!}&remark=${remark!}"/>
            <@pagination url, currentPageIndex, pageCount,"currentPageIndex"/>
        </div>
    </div>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/artDialog/5.0.1-7012746/artDialog.min.js"></script>
<script type="text/javascript" src="../assets/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript" src="../assets/bootstrap/2.3.1/js/bootstrap.min.js"></script>
<script type="text/javascript">
    $(function () {
        $(".deleteMediaShow").click(function () {
            var id = this.value;
            art.dialog({
                id: "delete",
                fixed: true,
                title: "删除确认",
                content: "确定要删除吗？",
                okValue: "确定",
                ok: function () {
                    window.location.href = "manager/deleteMediaShow?mediaShowId=" + id;
                },
                cancelValue: "取消",
                cancel: function () {
                }
            });
        });
        $(".save").click(function () {
            art.dialog({
                button: [
                    {
                        value: '保存',
                        callback: function () {
                            var valueTitle = $("#saveMediaShowTitle").val();
                            var valueremark = $("#saveMediaShowRemark").val();
                            var flag = false;
                            if (!valueTitle) {
                                $("#save_title_label").text("标题不能为空,请输入标题！");
                            } else if (!valueremark) {
                                $("#save_remark_label").text("摘要不能为空，请输入摘要！");
                            } else {
                                $("#mediaShowFormSave").submit();
                                flag = true;
                            }
                            return flag;
                        },
                        focus: true
                    }
                ],
                title: '新增',
                content: '<form id="mediaShowFormSave" action="manager/saveMediaShow" method="post">' +
                        '    <div class="control-group">' +
                        '        <label class="control-label">展示标题</label>'+
                        '        <div class="controls">'+
                        '            <input id="saveMediaShowTitle" type="text" name="title" value=""/>' +
                        '            <label id="save_title_label" style="color:red"></label>' +
                        '        </div> '+
                        '    </div>' +
                        '    <div class="control-group">' +
                        '        <label class="control-label">展示摘要</label>'+
                        '        <div class="controls">'+
                        '            <textarea id="saveMediaShowRemark" name="remark" cols="30" rows="5"></textarea>' +
                        '            <label id="save_remark_label" style="color:red"></label>' +
                        '        </div> '+
                        '    </div>' +
                        '</form>'
            });
        });
        $(".edit").click(function () {
            var id = this.value;
            var mediaShowTitle = $(this).attr("data-mediaShowTitle");
            var mediaShowRemark = $(this).attr("data-mediaShowRemark");
            art.dialog({
                button: [
                    {
                        value: '保存',
                        callback: function () {
                            var valueTitle = $("#editMediaShowTitle").val();
                            var valueremark = $("#editMediaShowRemark").val();
                            var flag = false
                            if (!valueTitle) {
                                $("#edit_title_label").text("标题不能为空,请输入标题！");
                            } else if (!valueremark) {
                                $("#edit_remark_label").text("摘要不能为空，请输入摘要！");
                            } else {
                                $("#mediaShowFormEdit").submit();
                                flag = true;
                            }
                            return flag;
                        },
                        focus: true
                    }
                ],
                title: '编辑',
                content: '<form id="mediaShowFormEdit" action="manager/editMediaShow" method="post">' +
                        '    <input id="editId" type="hidden" name="mediaShowId" value="' + id + '"/>' +
                        '    <div class="control-group">' +
                        '        <input id="editMediaShowTitle" type="text" name="title" value="' + mediaShowTitle + '"/>' +
                        '        <label id="edit_title_label" style="color:red"></label>' +
                        '    </div>' +
                        '    <div class="control-group">' +
                        '        <textarea id="editMediaShowRemark" name="remark" cols="30" rows="5">' + mediaShowRemark + '</textarea>' +
                        '        <label id="edit_remark_label" style="color:red"></label>' +
                        '    </div>' +
                        '</form>'
            });
        });
    });
</script>

</#macro>