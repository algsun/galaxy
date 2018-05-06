<#assign title="任务记录">
<#-- 当前权限标识 -->

<#macro head>

<link rel="stylesheet" href="../assets/artDialog/5.0.1-7012746/skins/default.css">
<link rel="stylesheet" href="css/dataTables.bootstrap.css">
</#macro>

<#macro content>
<div class="container-fluid">
    <div class="row-fluid m-t-20 m-b-20">
        <#if (security.isPermitted("saturn:task_records:insert")
        && (task.responsible?index_of(currentUser.userName)!=-1 || currentUser.id == task.createUser))>
            <input type="button" id="add" class="btn btn-info" value="添加">
        </#if>
        <a class="btn" href="task/index">返回</a>
    </div>
    <div class="row-fluid">
        <div class="span12">
            <!--Sidebar content-->
            <table class="table dataTable table-striped no-footer" id="list">
                <thead>
                <tr>
                    <th>重要性</th>
                    <th>记录时间</th>
                    <th>记录内容</th>
                    <th>记录人</th>
                    <th>处理结果</th>
                    <th>处理时间</th>
                    <th>处理人</th>
                    <th>操作</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
<input type="hidden" value="${taskId!}" id="taskId"/>

<input type="hidden" id="update" value="<#if security.isPermitted("saturn:task_records:update")>true</#if>"/>
<input type="hidden" id="delete" value="<#if security.isPermitted("saturn:task_records:delete")>true</#if>"/>
<input type="hidden" id="handle" value="<#if security.isPermitted("saturn:task_records:handle")>true</#if>"/>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/artDialog/5.0.3-ec058ee8b2/artDialog.min.js"></script>
<script type="text/javascript" src="../assets/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/dataTables.bootstrap.js"></script>
<script type="text/javascript">
    $(function () {

        var taskId = $("#taskId").val();
        $("#add").click(function () {
            window.location.href = "task_records/" + taskId + "/to_insert";
        });


        var t = $("#list").DataTable({
            "ajax": {
                "url": "task_records/" + taskId,
                //默认为data,这里定义为空，则只需要传不带属性的数据
                "dataSrc": ""
            },
            "stateSave": true,
            language: {
                "processing": "正在加载数据...",
                "lengthMenu": "显示_MENU_条 ",
                "zeroRecords": "没有您要搜索的内容",
                "info": "从_START_ 到 _END_ 条记录——总记录数为 _TOTAL_ 条",
                "infoEmpty": "记录数为0",
                "infoFiltered": "(全部记录数 _MAX_ 条)",
                "infoPostFix": "",
                "search": "搜索",
                "url": "",
                "paginate": {
                    "sFirst": "第一页",
                    "sPrevious": " « ",
                    "sNext": " » ",
                    "sLast": " 最后一页 "
                }
            },
            "bSort": false,
//            "sPaginationType": "full_numbers", // 分页，一共两种样式 另一种为two_button // 是datatables默认
            "columns": [
                {
                    data: null,
                    render: function (data, type, row) {
                        if (data.important == 0) {
                            return "<img src='images/tick.png'> ";
                        } else if (data.important == 1) {
                            return "<img src='images/bullet_cross.png'> ";
                        }
                    },
                    orderable: false
                },
                {
                    data: null,
                    render: function (data, type, row) {
                        if (data.recordDate != null && data.recordDate != "") {
                            var date = moment(data.recordDate).format("YYYY-MM-DD");
                            return date;
                        } else {
                            return "";
                        }

                    },
                    orderable: false
                },
                {data: "recordContent"},
                {data: "recordUserName"},
                {data: "handleResult"},
                {
                    data: null,
                    render: function (data, type, row) {
                        if (data.handleDate != null && data.handleDate != "") {
                            var date = moment(data.handleDate).format("YYYY-MM-DD");
                            return date;
                        } else {
                            return "";
                        }

                    },
                    orderable: false
                },
                {data: "handleUserName"},
                {
                    data: null,
                    render: function (data, type, row) {
                        var returnVar = "";
                        if(${task.state} == 2) return '';
                        var taskUpdate = $("#update").val();
                        var taskHandle = $("#handle").val();
                        var taskDelete = $("#delete").val();
                        if ((taskUpdate == "true") && (${task.responsible?index_of(currentUser.userName)} != -1 || ${currentUser.id}==${task.createUser}))
                        {
                            returnVar += "<a class='btn btn-mini ' href='task_records/to_update/" + data.id + "'>修改</a> "
                        }
                        if ((taskHandle == "true") && (${task.responsible?index_of(currentUser.userName)} != -1 || ${currentUser.id}==${task.createUser}))
                        {
                            returnVar += "<a class='btn btn-mini btn-success' href='task_records/to_handle/" + data.id + "'>处理</a> "
                        }
                        if ((taskDelete == "true") && (${task.responsible?index_of(currentUser.userName)} != -1 || ${currentUser.id}==${task.createUser}))
                        {
                            returnVar += "<a class='btn btn-mini btn-danger' data-task-id ='" + data.taskId + "' data-id='" + data.id + "'>删除</a> "
                        }
                        return returnVar;
                    },
                    orderable: false
                }


            ]
        });
        $("#list").on("click", ".btn-danger", function () {
            var id = $(this).data("id");
            var taskId = $(this).data("task-id");
            var d = art.dialog({
                title: '提示',
                content: '确定要删除这条记录？',
                okValue: '确定',
                ok: function () {
                    window.location.href = "task_records/" + taskId + "/" + id + "/delete";
                    return true;
                },
                cancelValue: '取消',
                cancel: function () {
                }
            });
            d.show();
        });
    });
</script>
</#macro>
