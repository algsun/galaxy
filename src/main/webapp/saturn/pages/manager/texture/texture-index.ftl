<#assign title="材质管理">
<#-- 当前权限标识 -->

<#macro head>

<link rel="stylesheet" href="../assets/artDialog/5.0.1-7012746/skins/default.css">
<link rel="stylesheet" href="css/dataTables.bootstrap.css"/>
</#macro>

<#macro content>
<div class="container">
    <div class="row m-t-20 m-b-20">
        <input type="button" id="add" class="btn btn-info" value="添加">
    </div>
    <div class="row">
        <!--Sidebar content-->
        <table id="list" class="table table-striped">
            <thead>
            <tr>
                <td>名称</td>
                <td>操作</td>
            </tr>
            </thead>
        </table>
    </div>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/artDialog/5.0.3-ec058ee8b2/artDialog.min.js"></script>
<script type="text/javascript" src="../assets/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.datatables.net/1.10.5/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
        src="http://cdn.datatables.net/plug-ins/f2c75b7247b/integration/bootstrap/2/dataTables.bootstrap.js"></script>
<script type="text/javascript">
    $(function () {
        var t = $("#list").DataTable({
                    "ajax": {
                        "url": "texture/findTextures",
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
//                    "sPaginationType": "full_numbers", // 分页，一共两种样式 另一种为two_button // 是datatables默认
                    "columns": [
                        {data: "name"},
                        {
                            data: null,
                            render: function (data, type, row) {
                                return "<a class='btn btn-small btn-success' data-value=" + data.name + " data-id=" + data.id + "><i class='icon-pencil icon-white'></i>修改</a> "
                                        + "<a class='btn btn-small btn-danger' data-id=" + data.id + "><i class='icon-remove icon-white'></i>删除</a>";
                            },
                            orderable: false
                        }
                    ]
                })
                ;

        $("#add").click(function () {
            art.dialog({
                button: [
                    {
                        value: '保存',
                        callback: function () {
                            var value = $("#textureName").val();
                            var flag = false
                            if (value == "") {
                                $("#texture_label").text("请输入材质");
                            } else {
                                $.get('texture/isExits', {textureName: value}, function (result) {
                                    if (result == 'true') {
                                        $("#texture_label").text("该材质已存在，请重新输入!");
                                    } else {
                                        $("#textureForm").submit();
                                        flag = true;
                                    }
                                });
                            }
                            return flag;
                        },
                        focus: true
                    }
                ],
                title: '添加材质',
                content: '<form id="textureForm" action="texture/add" method="post" style="margin-bottom: -10px;"> 材质:<input type="text" name="textureName" id="textureName"/> <br/><label id="texture_label" style="color: red"></label></form>'
            });
        });
        $("#list").on("click", ".btn-danger", function () {
            var id = $(this).data("id");
            art.dialog({
                title: '提示',
                content: '确定要删除这条记录？',
                okValue: '确定',
                ok: function () {
                    window.location.href = "texture/delete?id=" + id;
                    return true;
                },
                cancelValue: '取消',
                cancel: function () {
                }
            });
        });

        $("#list").on("click", ".btn-success", function () {
            var id = $(this).data("id");
            var textureName = $(this).data("value");
            art.dialog({
                button: [
                    {
                        value: '保存',
                        callback: function () {
                            var value = $("#textureName").val();
                            var flag = false
                            if (value == "") {
                                $("#texture_label").text("请输入材质");
                            } else {
                                $.get('texture/isExits', {textureName: value}, function (result) {
                                    if (result == 'true') {
                                        $("#texture_label").text("该材质已存在，请重新输入!");
                                    } else {
                                        $("#textureName").val(value);
                                        $("#edit-id").val(id);
                                        $.get("texture/edit", {id: id, textureName: value}, function (result) {
                                            if (result == "true") {
                                                window.location.href = "texture/index";
                                            }
                                        });
                                    }
                                });
                            }
                            return true;
                        },
                        focus: true
                    }
                ],
                title: '编辑',
                content: '<form id="textureForm" action="texture/edit" method="post" style="margin-bottom: -10px;">' +
                        '<input id="edit-id" type="hidden" name="id" value="' + id + '"> ' +
                        '<div class="control-group">材质:<input type="text" name="textureName" value="' + textureName + '" id="textureName"/>' +
                        ' <br/><label id="texture_label" style="color: red"></label></form>'
            });
        });
    });
</script>
</#macro>
