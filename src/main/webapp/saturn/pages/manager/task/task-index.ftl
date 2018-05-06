<#assign title="任务管理">
<#-- 当前权限标识 -->

<#macro head>

<link rel="stylesheet" href="../assets/artDialog/5.0.1-7012746/skins/default.css">
<link rel="stylesheet"
      href="css/dataTables.bootstrap.css">
</#macro>

<#macro content>
<div class="container-fluid">
    <div class="row-fluid m-t-20 m-b-20">
        <input type="button" id="add" class="btn btn-info" value="添加">
    </div>
    <div class="row-fluid">
        <div class="span12">
            <!--Sidebar content-->
            <table class="table dataTable table-striped no-footer" id="list">
                <thead>
                <tr>
                    <th>名称</th>
                    <th>负责人</th>
                    <th>状态</th>
                    <th>更新时间</th>
                    <th>预计开始</th>
                    <th>预计结束</th>
                    <th>审核人</th>
                    <th>审核时间</th>
                    <th>创建人</th>
                    <th>创建时间</th>
                    <th>操作</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/artDialog/5.0.3-ec058ee8b2/artDialog.min.js"></script>
<script type="text/javascript" src="../assets/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>
<link href="../assets/dataTables/1.10.12/css/dataTables.foundation.min.css" rel="stylesheet">
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/dataTables.bootstrap.js"></script>

<script type="text/javascript">
$(function () {

    $("#add").click(function () {
        window.location.href = "task/to_add";
    });
    var t = $("#list").DataTable({

        "ajax": {
            "url": "task/tasks",
            //默认为data,这里定义为空，则只需要传不带属性的数据
            "dataSrc": ""
        },
//        "stateSave": true,
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
        "aaSorting": [
            [2, "desc"]
        ],
//            "sPaginationType": "full_numbers", // 分页，一共两种样式 另一种为two_button // 是datatables默认
        "columns": [
//                {data: "title"},
            {
                data: null,
                render: function (data, type, row) {
                    var title = data.title;
                    if (title.length > 15) {
                        return title.substring(0, 15) + "...";
                    } else {
                        return title;
                    }
                }
            },
            {data: "responsible"},
            {
                data: null,
                render: function (data, type, row) {
                    if (data.state == 0) {
                        return "<label style='color: red'>待审核</label>";
                    } else if (data.state == 1) {
                        return "<label>进行中</label>";
                    } else if (data.state == 2) {
                        return "<label style='color: green'>已完成</label>";
                    } else if (data.state == 3) {
                        return "";
                    }
                }
            },
            {
                data: null,
                render: function (data, type, row) {
                    if (data.updateDate != null && data.updateDate != "") {
                        var date = moment(data.updateDate).format("YYYY-MM-DD");
                        return date;
                    } else {
                        return "";
                    }

                }
            },
            {
                data: null,
                render: function (data, type, row) {
                    var date = moment(data.predictStart).format("YYYY-MM-DD");
                    return date;

                }
            },
            {
                data: null,
                render: function (data, type, row) {
                    var date = moment(data.predictEnd).format("YYYY-MM-DD");
                    return date;

                }
            },
            {data: "checkName"},
            {
                data: null,
                render: function (data, type, row) {
                    if (data.checkDate != null && data.checkDate != "") {
                        var date = moment(data.updateDate).format("YYYY-MM-DD");
                        return date;
                    } else {
                        return "";
                    }

                }
            },
            {data: "createName"},
            {
                data: null,
                render: function (data, type, row) {
                    var date = moment(data.createDate).format("YYYY-MM-DD");
                    return date;

                }
            },
            {
                data: null,
                render: function (data, type, row) {
                    var operationInfo='';
                    if(data.state==2) return '';
                    if("${currentUser.userName}"==data.responsible||"${currentUser.userName}"==data.createName){
                        operationInfo ="<a class='btn btn-mini btn-primary' href='task/to_view?id=" + data.id + "'>详情</a> "
                                + "<a class='btn btn-mini btn-success' href='task/to_edit?id=" + data.id + "'>修改</a> "
                                + "<a class='btn btn-mini btn-danger' data-id='" + data.id + "'>删除</a> "
                                + "<a class='btn btn-mini btn-info' href='task_records/" + data.id +"/index'>任务记录</a> "
                                + "<#if security.isPermitted("saturn:task_records:check")>"
                                + "<a class='btn btn-mini check' data-value='1' data-state='" + data.state + "' data-id='" + data.id + "' >审核</a> "
                                + "</#if>"
                                + "<#if security.isPermitted("saturn:task_records:finish")>"
                                + "<a class='btn btn-mini check' data-value='2' data-state='" + data.state + "' data-id='" + data.id + "'>完成</a> "
                                + "</#if>";
                    }else{
                        operationInfo ="<a class='btn btn-mini btn-primary' href='task/to_view?id=" + data.id + "'>详情</a> "
                                + "<a class='btn btn-mini btn-info' href='task_records/" + data.id +"/index'>任务记录</a> "
                                + "<#if security.isPermitted("saturn:task_records:check")>"
                                + "<a class='btn btn-mini check' data-value='1' data-state='" + data.state + "' data-id='" + data.id + "' >审核</a> "
                                + "</#if>"
                                + "<#if security.isPermitted("saturn:task_records:finish")>"
                                + "<a class='btn btn-mini check' data-value='2' data-state='" + data.state + "' data-id='" + data.id + "'>完成</a> "
                                + "</#if>" ;
                    }
                    return operationInfo;
                },
                orderable: false
            }
        ]
    });

    $('#list').on('click', 'tbody tr', function () {
        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');
        }
        else {
            t.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    });

    $("#list").on("click", ".btn-danger", function () {
        var id = $(this).data("id");
        $.get("task/findTaskRecords", {id: id}, function (result) {
            var d;
            if (result > 0) {
                d = art.dialog({
                    title: '提示',
                    content: '删除任务会删除该任务的任务记录，确定要删除吗？',
                    okValue: '确定',
                    ok: function () {
                        var url = "task/delete_records/" + id +"/";
                        $.get(url, function (result) {
                            if(result){
                                window.location.href = "task/delete?id=" + id;
                                return true;
                            }
                        });
                    },
                    cancelValue: '取消',
                    cancel: function () {
                    }
                });
            } else {
                d = art.dialog({
                    title: '提示',
                    content: '确定要删除这条记录？',
                    okValue: '确定',
                    ok: function () {
                        window.location.href = "task/delete?id=" + id;
                        return true;
                    },
                    cancelValue: '取消',
                    cancel: function () {
                    }
                });
            }
            d.show();
        });
    });

    $("#list").on("click", ".check", function () {
        var $this = $(this);
        var id = $this.data("id");
        var value = $this.data("value");
        var selfState = $this.data("state");

        if (value == 1 && selfState == 1) {
            art.dialog({
                title: '温馨提示',
                content: '当前任务，正在进行！'
            });
            return;
        }

        if (value == 2 && selfState == 2) {
            art.dialog({
                title: '温馨提示',
                content: '当前任务，已完成！'
            });
            return;
        }

        if (value == 2 && selfState == 0) {
            art.dialog({
                title: '温馨提示',
                content: '当前任务未审核，请先审核！'
            });
            return;
        }

        if (selfState == 2 && value == 1) {
            art.dialog({
                title: '温馨提示',
                content: '当前任务，已完成！'
            });
            return;
        }

        var data = {id: id, state: value};
        $.get("task/updateState", data, function (result) {
            if (result == "true") {
                art.dialog({
                    button: [
                        {
                            value: '确定',
                            callback: function () {
                                window.location.href = "task/index"
                                return true;
                            },
                            focus: true
                        }
                    ],
                    title: '温馨提示',
                    content: '操作成功'
                });
            }
        });
    });
});
</script>
</#macro>
