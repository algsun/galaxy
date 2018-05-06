<#assign title="成果展示 - 文献资料">
<#-- 当前权限标识 -->

<#macro head>
<link rel="stylesheet"
      href="http://cdn.datatables.net/plug-ins/f2c75b7247b/integration/bootstrap/2/dataTables.bootstrap.css"/>
</#macro>

<#macro content>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="col-md-12 m-t-20">
            <a id="new" class="btn btn-xs btn-info" href="manager/literatures/add">添加文献资料</a>
        </div>
    </div>
    <table id="list" class="table table-striped">
        <thead>
        <tr>
            <th>序号</th>
            <th>文献类型</th>
            <th>文献标题</th>
            <th>作者</th>
            <th>日期</th>
            <th>发表期刊</th>
            <th>期别</th>
            <th>关键字</th>
            <th>录入人</th>
            <th>录入时间</th>
            <th>操作</th>
        </tr>
        </thead>

    </table>
</div>
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
                        "url": "manager/literatures",
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
                        {
                            data: "catalog",
                            render: function (data, type, row) {
                                if (data == 1) {
                                    return "论文";
                                } else if (data == 2) {
                                    return "著作";
                                } else if (data == 3) {
                                    return "其他";
                                }
                            }
                        },
                        {data: "title"},
                        {data: "author"},
                        {
                            data: "publicDate",
                            render: function (data, type, row) {
                                return moment(data).format('YYYY-MM-DD')
                            }
                        },
                        {data: "magazine"},
                        {data: "periodical"},
                        {data: "keywords"},
                        {data: "createBy"},
                        {
                            data: "createTime",
                            render: function (data, type, row) {
                                return moment(data).format('YYYY-MM-DD')
                            }
                        },
                        {
                            data: null,
                            render: function (data, type, row) {
                                return "<a class='btn btn-mini btn-success' href='manager/literatures/" + data.id + "/edit'>修改</a> "
                                        + "<a class='btn btn-mini btn-danger' data-literature-id=" + data.id + ">删除</a>";
                            },
                            orderable: false
                        }
                    ]
                })
                ;

        t.on('order.dt search.dt', function () {
            t.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
                cell.innerHTML = i + 1;
            });

        }).draw();

        $("#list").on("click", ".btn-danger", function () {
            var literatureId = $(this).data("literatureId");
            var d = art.dialog({
                title: '提示',
                content: '确定要删除这条记录？',
                okValue: '确定',
                ok: function () {
                    window.location.href = "manager/literatures/" + literatureId + "/delete";
                    return true;
                },
                cancelValue: '取消',
                cancel: function () {
                }
            });
            d.show();
        });
    })
    ;
</script>

</#macro>