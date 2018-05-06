<#assign title="成果展示 - 专业成果">
<#-- 当前权限标识 -->

<#macro head>
<link rel="stylesheet"
      href="http://cdn.datatables.net/plug-ins/f2c75b7247b/integration/bootstrap/2/dataTables.bootstrap.css"/>
</#macro>

<#macro content>
<div class="row-fluid">
    <div class="col-md-12 m-t-20">
        <a id="new" class="btn btn-xs btn-info" href="manager/affairs/add">添加专业成果</a>
    </div>
</div>
<div class="row">

</div>
<table id="list" class="table table-striped">
    <thead>
    <tr>
        <th>序号</th>
        <th>姓名</th>
        <th>事务类型</th>
        <th>事务名称</th>
        <th>日期</th>
        <th>发表期刊</th>
        <th>刊源</th>
        <th>项目属性</th>
        <th>项目级别</th>
        <th>进展状况</th>
        <th>获奖情况</th>
        <th>备注</th>
        <th>录入人</th>
        <th>录入时间</th>
        <th>操作</th>
    </tr>
    </thead>

</table>
</#macro>

<#macro script>
<script type="text/javascript" src="/galaxy/assets/moment/2.0.0/moment.min.js"></script>
<script type="text/javascript" src="http://cdn.datatables.net/1.10.5/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
        src="http://cdn.datatables.net/plug-ins/f2c75b7247b/integration/bootstrap/2/dataTables.bootstrap.js"></script>
<script type="text/javascript">
    $(function () {
        var t = $("#list").DataTable({
            "stateSave": true,
            "ajax": {
                "url": "manager/affairs",
                //默认为data,这里定义为空，则只需要传不带属性的数据
                "dataSrc": ""
            },
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
                    "first": "第一页",
                    "previous": "«",
                    "next": "»",
                    "last": " 最后一页 "
                }
            },
            "columns": [
                {data: null},
                {data: "incharge"},
                {
                    data: "type",
                    render: function (data, type, row) {
                        if (data == 1) {
                            return "发表论文/著作";
                        } else if (data == 2) {
                            return "负责项目";
                        } else if (data == 3) {
                            return "其他服务";
                        }
                    }
                },
                {data: "title"},
                {
                    data: "publicDate",
                    render: function (data, type, row) {
                        return moment(data).format('YYYY-MM-DD');
                    }
                },
                {data: "magazine"},
                {data: "magazineSrc"},
                {
                    data: "projectType",
                    render: function (data, type, row) {
                        if (data == 0) {
                            return "";
                        } else if (data == 1) {
                            return "科研";
                        } else if (data == 2) {
                            return "修复";
                        }
                    }
                },
                {
                    data: "projectLevel",
                    render: function (data, type, row) {
                        if (data == 0) {
                            return "";
                        } else if (data == 1) {
                            return "院级";
                        } else if (data == 2) {
                            return "省级";
                        } else if (data == 3) {
                            return "国家级";
                        }
                    }
                },
                {data: "progress"},
                {data: "prize"},
                {data: "remark"},
                {data: "createBy"},
                {
                    data: "createTime",
                    render: function (data, type, row) {
                        return moment(data).format('YYYY-MM-DD');
                    }
                },
                {
                    data: null,
                    render: function (data, type, row) {
                        return "<a class='btn btn-mini btn-success' href='manager/affairs/" + data.id + "/edit'>修改</a> "
                                + "<a class='btn btn-mini btn-danger' data-affair-id=" + data.id + ">删除</a>";
                    },
                    orderable: false
                }
            ]
        });

        t.on('order.dt search.dt', function () {
            t.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
                cell.innerHTML = i + 1;
            });

        }).draw();

        $("#list").on("click", ".btn-danger", function () {
            var affairId = $(this).data("affairId");
            var d = art.dialog({
                title: '提示',
                content: '确定要删除这条记录？',
                okValue: '确定',
                ok: function () {
                    window.location.href = "manager/affairs/" + affairId + "/delete";
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