/*
 * @author xu.yuexi
 * @date 2014-6-4
 */

$(function () {

    //编辑质地
    $("body").on("keydown", "#addTexture", function (e) {
        var curKey = e.which;
        if (curKey == 13) {
            var $this = $("#addTexture");
            $this.next().html("");
            var texture = $this.val();
            $.post("saveTexture", {textureName: texture}, function (result) {
                if (result.success) {
                    if (result.success == 'success') {
                        var textureNode = $("#editTexture :first").clone();
                        textureNode.find("span :last").empty().append(texture);
                        textureNode.insertBefore("#addTexture");
                        var option = "<option value=" + result.textureId + ">" + texture + "</option>";
                        $("#textureId").append(option);
                        $this.hide();
                    } else {
                        $this.next().html("添加出错");
                    }

                } else {
                    $this.next().html("该质地已存在");
                }
                $this.val("");
            });
        }
    });

    $("body").on("click", "i[name='removeTexture']", function () {
        var $this = $(this);
        var texture = $this.attr("data-texture-name");
        var $help = $("#addTexture").next();
        $help.html("");
        var r = confirm("确认删除？");
        if (r) {
            $.post("deleteTexture", {textureName: texture}, function (result) {
                if (result.success) {
                    if (result.success == 'success') {
                        $this.parent().remove();
                        $("#textureId option[value=" + result.textureId + "]").remove();
                    } else {
                        $help.html("删除出错");
                    }
                }
            });
        }

    });

    $("#texture").click(function () {
        var editTextureDiv = $("#editTexture").html();
        art.dialog({
            id: 'id-texture',
            title: "质地",
            width: "500px",
            content: editTextureDiv,
            cancelValue: '关闭'
        });
    });

    $("body").on("click", "#addTex", function () {
        $(this).prev().prev().show();
    });

    //编辑年代
    $("body").on("keydown", "#addEra", function (e) {
        var curKey = e.which;
        if (curKey == 13) {
            var $this = $("#addEra");
            $this.next().html("");
            var era = $this.val();
            $.post("saveEra", {eraName: era}, function (result) {
                if (result.success) {
                    if (result.success == 'success') {
                        var eraNode = $("#editEra :first").clone();
                        eraNode.find("span :last").empty().append(era);
                        eraNode.insertBefore("#addEra");
                        $this.hide();
                        var option = "<option value=" + result.eraId + ">" + era + "</option>";
                        $("#eraId").append(option);
                    } else {
                        $this.next().html("添加出错");
                    }
                } else {
                    $this.next().html("该年代已存在");
                }
                $this.val("");
            });
        }
    });

    $("body").on("click", "i[name='removeEra']", function () {
        var $this = $(this);
        var era = $this.data("eraName");
        var $help = $("#addEra").next();
        $help.html("");
        var r = confirm("确认删除？");
        if (r) {
            $.post("deleteEra", {eraName: era}, function (result) {
                if (result.success) {
                    if (result.success == 'success') {
                        $this.parent().remove();
                        $("#eraId option[value=" + result.eraId + "]").remove();
                    } else {
                        $help.html("删除出错");
                    }
                }
            });
        }

    });

    $("#era").click(function () {
        var editEraDiv = $("#editEra").html();
        art.dialog({
            id: 'id-era',
            title: "年代",
            width: "500px",
            content: editEraDiv,
            cancelValue: '关闭'
        });
    });

    $("body").on("click", "#toAddEra", function () {
        $(this).prev().prev().show();
    });

    //编辑级别
    $("body").on("keydown", "#addLevel", function (e) {
        var curKey = e.which;
        if (curKey == 13) {
            var $this = $("#addLevel");
            $this.next().html("");
            var level = $this.val();
            $.post("saveLevel", {levelName: level}, function (result) {
                if (result.success) {
                    if (result.success == 'success') {
                        var levelNode = $("#editLevel :first").clone();
                        levelNode.find("span :last").empty().append(level);
                        levelNode.insertBefore("#addLevel");
                        var option = "<option value=" + result.levelId + ">" + level + "</option>";
                        $("#levelId").append(option);
                        $this.hide();
                    } else {
                        $this.next().html("添加出错");
                    }
                } else {
                    $this.next().html("该级别已存在");
                }
                $this.val("");
            });
        }
    });

    $("body").on("click", "i[name='removeLevel']", function () {
        var $this = $(this);
        var level = $this.data("levelName");
        var $help = $("#addLevel").next();
        $help.html("");
        var r = confirm("确认删除？");
        if (r) {
            $.post("deleteLevel", {levelName: level}, function (result) {
                if (result.success) {
                    if (result.success == 'success') {
                        $this.parent().remove();
                        $("#levelId option[value=" + result.levelId + "]").remove();
                    } else {
                        $help.html("删除出错");
                    }
                }
            });
        }

    });

    $("#level").click(function () {
        var editLevelDiv = $("#editLevel").html();
        art.dialog({
            id: 'id-level',
            title: "年代",
            width: "500px",
            content: editLevelDiv,
            cancelValue: '关闭'
        });
    });

    $("body").on("click", "#addLev", function () {
        $(this).prev().prev().show();
    });

})
;
