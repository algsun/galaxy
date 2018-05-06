function writeBaseInfo(result, type) {
    var data = result.repairRecord;
    var picturesBasePath = result.picturesBasePath;
    var actionName = result.actionName;
    $("#" + type + "HiddenActionName").val(actionName);
    $("#" + type + "RelicName").html(data.relic.name);
    $("#" + type + "RelicNum").html(data.relic.totalCode);
    $("#" + type + "Era").html(data.relic.era.name);
    $("#" + type + "Level").html(data.relic.level.name);
    $("#" + type + "Texture").html(data.relic.texture.name);
    $("#" + type + "RepairReason").html(data.repairReason.reason);
    $("#" + type + "RepairContent").html(data.repairInfo);
    $("#" + type + "CollectionUnit").html(data.collectionUnit);
    $("#" + type + "CollectionRoom").html(data.collectionRoom);
    var exDate = new Date(data.extractDate);
    var reDate = new Date(data.returnDate);
    $("#" + type + "ExtractionTime").html(exDate.getFullYear() + '-' + (exDate.getMonth() + 1) + '-' + exDate.getDate());
    $("#" + type + "ReturnTime").html(reDate.getFullYear() + '-' + (reDate.getMonth() + 1) + '-' + reDate.getDate());
    if (data.relic.photos.length > 0) {
        var html = createHtml(data.relic, picturesBasePath);
        $("#" + type + "PhotoPath").html(html);
        var imageList = createBaseInfoImagesList(data, picturesBasePath, actionName);
        $(".baseInfoImagesRow").remove();
        $("#" + type + "ImageListupload").after(imageList);
    }
    var relicPropertyMap = data.relic.relicPropertyMap;
    for (var key in relicPropertyMap) {
        var value = relicPropertyMap[key].propertyValue;
        if (key == "source") {
            $("#" + type + "Source").html(value);
        } else if (key == "describe") {
            $("#" + type + "Description").val(value);
        } else if (key == "literature") {
            $("#" + type + "Document").val(value);
        } else if (key == "remark") {
            $("#" + type + "Remark").val(value);
        }
    }
}

function writeUnitInfoTable(data) {
    $("#schemeNameAndNum").html(data.scheme.name + "(" + data.scheme.schemeId + ")");
    var conTime = new Date(data.scheme.confirmTime);
    $("#confirmUnitAndDate").html(conTime.getFullYear() + '-' + (conTime.getMonth() + 1) + '-' + conTime.getDate());//data.confirmUnit,批准单位暂无
    $("#confirmNum").html(data.scheme.confirmNum);
    $("#institutionName_design").html(data.scheme.institution.name);
    $("#institutionSeat_design").html(data.scheme.institution.seat);
    $("#institutionMailing_design").html(data.scheme.institution.mailing);
    $("#institutionZipcode_design").html(data.scheme.institution.zipcode);
    $("#institutionQualification_design").html(data.scheme.institution.qualification);
    $("#institutionCode_design").html(data.scheme.institution.code);
    $("#institutionName_repair").html(data.institution.name);
    $("#institutionSeat_repair").html(data.institution.seat);
    $("#institutionMailing_repair").html(data.institution.mailing);
    $("#institutionZipcode_repair").html(data.institution.zipcode);
    $("#institutionQualification_repair").html(data.institution.qualification);
    $("#institutionCode_repair").html(data.institution.code);
    var exDate = new Date(data.extractDate);
    var reDate = new Date(data.returnDate);
    $("#extractDate").html(exDate.getFullYear() + '-' + (exDate.getMonth() + 1) + '-' + exDate.getDate());
    $("#extractPerson").html(data.extractPerson);//暂无
    $("#returnDate").html(reDate.getFullYear() + '-' + (reDate.getMonth() + 1) + '-' + reDate.getDate());
    $("#returnPerson").html(data.returnPerson);//暂无
}

function createHtml(relic, picturesBasePath) {

    var photoes = relic.photos;
    var siteId = relic.siteId;
    //var totalCode = relic.totalCode;
    var relicId = relic.id;

    var html1 = "<div id='myCarousel' class='carousel slide' style='margin-bottom: 0'>" +
        "<div class='carousel-inner'>";

    var html2 = "";
    for (var i = 0; i < photoes.length; i++) {
        var photo = photoes[i];
        var path = photo.path;

        var temp1;
        if (i == 0) {
            temp1 = "<div class='item active' align='center'>";
        } else {
            temp1 = "<div class='item' align='center'>";
        }
        var temp2 = "<img src='" + picturesBasePath + "/" + siteId + "/" + relicId + "/" + path + "' style='max-width: 350px;max-height: 350px; text-align: center'/>" +
            "</div>";
        html2 = html2 + temp1 + temp2;
    }

    var html3 = "</div>" +
        "<a class='left carousel-control' href='?#myCarousel' data-slide='prev' style='top:50%;'>‹</a>" +
        "<a class='right carousel-control' href='?#myCarousel' data-slide='next' style='top:50%;'>›</a>" +
        "</div>";
    return html1 + html2 + html3;
}

function writeStatusQuoTable(result) {
    var data = result.statusQuo;
    var relicId = result.relicId;
    var repairRecordId = result.repairRecordId;
    var actionName = result.actionName;
    $("#hiddenRepairRecordId").val(repairRecordId);
    $("#hiddenRelicId").val(relicId);
    $("#hiddenActionName").val(actionName);
    if (data == null || data.length == 0) {
        return;
    }


    $("#hiddenStatusQuoId").val(data.id);
    $("#hiddenKeepStatusId").val(data.id);
    $("#sqConserve").val(data.conserve);
    $("#sqRepairCases").val(data.repairCases);
    $("#sqQuoInfo").val(data.quoInfo);
    $("#sqRemark").val(data.remark);

    showStatusQuoPhotos(result, "statusQuoPhotoShow");

    var imageList = createStatusQuoImagesList(result);
    $(".statusQuoImagesRow").remove();
    $("#keepStatusImageListupload").after(imageList);
}

function writeDetectionAnalysisTable(data, repairRecordId, relicId) {
    if (data.length == 0) {
        $("#detectionAnalysisTable").children().remove()
    } else {
        $(".detectionAnalysisTr").remove();
        $.each(data, function (index, item) {
            var d;
            if (item.analysisTime == null) {
                d = "";
            } else {
                var anaTime = new Date(item.analysisTime);
                d = anaTime.getFullYear() + '-' + (anaTime.getMonth() + 1) + '-' + anaTime.getDate();
            }
            var html =
                "<tr class='detectionAnalysisTr'> " +
                "    <td colspan='1'>" + (index + 1) + "</td> " +
                "    <td colspan='2'>" + item.sampleName + "</td> " +
                "    <td colspan='3'>" + item.sampleDescription + "</td> " +
                "    <td colspan='2'>" + item.analysisPurpose + "</td> " +
                "    <td colspan='3'>" + item.analysisMethod + "</td> " +
                "    <td colspan='2'>" + item.analysisResult + "</td> " +
                "    <td colspan='2'>" + item.reportCode + "</td> " +
                "    <td colspan='2'>" + d + "</td> " +
                "    <td colspan='2'>" + item.remark + "</td> " +
                "    <td colspan='1'><a href='javascript:void(0);' onclick='delImage($(this))' link='archives/deleteDetectionAnalysis?relicId=" + relicId + "&repairRecordId=" + repairRecordId + "&analysisNum=" + item.analysisNum + "'>删除</a></td>" +
                "</tr>";
            $("#detectionAnalysisTable").append(html);
        });
    }
}

function writeRepairRecordTable(result) {
    var repairLogs = result.repairLogs;
    var situation = result.situation;
    var repairPhotos = result.repairPhotos;
    var relic = situation.repairRecord.relic;
    $(".repairLogRow").remove();
    if (repairLogs.length != 0) {
        var html = "";
        $.each(repairLogs, function (index, log) {
            var repairPersonNames = log.repairPersonName;
            var repairPersonName = "";
            for (var i = 0; i < repairPersonNames.length; i++) {
                repairPersonName += repairPersonNames[i] + " ";
            }
            html = html +
                "<tr class='repairLogRow'>" +
                "<td colspan='4'>保护修复人</td>" +
                "<td colspan='6'>" + repairPersonName + "</td>" +
                "<td colspan='4'>日期</td>" +
                "<td colspan='6'>" + moment(log.stamp).format("YYYY-MM-DD") + "</td>" +
                "</tr>" +
                "<tr class='repairLogRow'>" +
                "<td colspan='4'>日志</td>" +
                "<td colspan='16'>" + log.log + "</td>" +
                "</tr>";
        });

        $.each(repairPhotos, function (index, repairPhoto) {
            html = html +
                "<tr class='repairPhotosRow'>" +
                "<td colspan='20'>" +
                "<img src='" + result.picturesBasePath + "/" + result.siteId + "/" + relic.id + "/" + repairPhoto.path + "' style='max-width: 400px;max-height: 300px; text-align: center'/>" +
                "<br/><span>" + repairPhoto.description + "</span>" +
                "</td>" +
                "</tr>";
        });
        $(".repairPhotosRow").remove();
        $("#repairRecordConfirmRow").before(html);
    }

    var html = repairRecordPhotoList(result);
    $("#repairRecordImageListupload").after(html);

    if (situation != null) {
        $("#repairProcessRecordTechChange").val(situation.techChange);
        $("#content").html(situation.summarize);
    }
}

function repairRecordPhotoList(result) {
    $(".repairRecordPhotosRow").remove();
    var repairPhotos = result.repairPhotos;
    var relic = result.situation.repairRecord.relic;
    var actionName = result.actionName;
    var picturesBasePath = result.picturesBasePath;
    var siteId = relic.siteId;
    var relicId = relic.id;
    var totalCode = relic.totalCode;
    var head =
        "<tr class='repairRecordPhotosRow'>" +
        "    <td colspan='4'>图片列表</td>" +
        "</tr>";
    var body = "";
    for (var i = 0; i < repairPhotos.length; i++) {
        var repairPhoto = repairPhotos[i];
        body = body +
            "<tr class='repairRecordPhotosRow'>" +
            "<td><img src='" + picturesBasePath + "/" + siteId + "/" + relicId + "/" + repairPhoto.path + "' style='max-width: 400px;max-height: 300px; text-align: center'/></td>" +
            "<td style='width: 30%'>" + repairPhoto.path + "</td>" +
            "<td>" + repairPhoto.description + "</td>" +
            "<td><a href='javascript:void(0);' onclick='delImage($(this))' link='archives/deleteRepairRecordPhoto?repairPhotoId=" + repairPhoto.id + "&actionName=" + actionName + "&repairRecordId=" + result.repairRecordId + "&relicId=" + relicId + "'>删除</a></td>" +
            "</tr>";
    }
    return head + body;

}

function createBaseInfoImagesList(data, picturesBasePath, actionName) {
    var relic = data.relic;
    var photos = data.relic.photos;
    var siteId = relic.siteId;
    var relicId = relic.id;
    var totalCode = relic.totalCode;
    var repairrecordId = data.id;
    var head =
        "<tr class='baseInfoImagesRow'>" +
        "    <td colspan='3'>图片列表</td>" +
        "</tr>";
    var body = "";
    for (var i = 0; i < photos.length; i++) {
        var photo = photos[i];
        body = body +
            "<tr class='baseInfoImagesRow'>" +
            "<td><img src='" + picturesBasePath + "/" + siteId + "/" + relicId + "/" + photo.path + "' style='max-width: 100px;max-height: 100px; text-align: center'/></td>" +
            "<td style='width: 70%'>" + photo.path + "</td>" +
            "<td><a href='javascript:void(0);' onclick='delImage($(this))'  link='archives/deletePhoto?actionName=" + actionName + "&repairRecordId=" + repairrecordId + "&relicId=" + relicId + "&photoId=" + photo.id + "'>删除</a></td>" +
            "</tr>";
    }
    return head + body;
}

var delImage = function ($this) {
    var link = $this.attr("link");
    art.dialog({
        id: "delete",
        fixed: true,
        title: "删除确认",
        content: "确认要删除吗？",
        okValue: "确定",
        ok: function () {
            window.location.href = link;
        },
        cancelValue: "取消",
        cancel: function () {
        }
    })
}

function showStatusQuoPhotos(result, id) {
    $(".allTableStatusQuoPhotosShow").remove();
    var repairPhotos = result.repairPhotos;
    var data = result.statusQuo;
    var relic = result.relic;
    var picturesBasePath = result.picturesBasePath;
    var siteId = relic.siteId;
    var totalCode = relic.totalCode;
    var relicId = relic.id;

    var size = Math.round(repairPhotos.length / 2);
    var s = repairPhotos.length % 2;
    var html;
    for (var j = 1; j < size + 1; j++) {
        var row = j * 2;
        if (j < size) {
            var src1 = picturesBasePath + "/" + siteId + "/" + relicId + "/" + repairPhotos[row - 2].path;
            var src2 = picturesBasePath + "/" + siteId + "/" + relicId + "/" + repairPhotos[row - 1].path;
            html = html + "<tr class='allTableStatusQuoPhotosShow'>" +
                "    <td colspan='4'></td>" +
                "    <td colspan='8'><img src='" + src1 + "'/><br/><span>" + repairPhotos[row - 2].description + "</span></td>" +
                "    <td colspan='8'><img src='" + src2 + "'/><br/><span>" + repairPhotos[row - 1].description + "</span></td>" +
                "</tr>";
        } else if (j == size && s == 1) {
            var src = picturesBasePath + "/" + siteId + "/" + relicId + "/" + repairPhotos[row - 2].path;
            html = html + "<tr class='allTableStatusQuoPhotosShow'>" +
                "    <td colspan='4'></td>" +
                "    <td colspan='8'><img src='" + src + "'/><br/><span>" + repairPhotos[row - 2].description + "</span></td>" +
                "    <td colspan='8'></td>" +
                "</tr>";
        } else if (j == size && s == 0) {
            var src1 = picturesBasePath + "/" + siteId + "/" + relicId + "/" + repairPhotos[row - 2].path;
            var src2 = picturesBasePath + "/" + siteId + "/" + relicId + "/" + repairPhotos[row - 1].path;
            html = html + "<tr class='allTableStatusQuoPhotosShow'>" +
                "    <td colspan='4'></td>" +
                "    <td colspan='8'><img src='" + src1 + "'/><br/><span>" + repairPhotos[row - 2].description + "</span></td>" +
                "    <td colspan='8'><img src='" + src2 + "'/><br/><span>" + repairPhotos[row - 1].description + "</span></td>" +
                "</tr>";
        }
    }
    $("#" + id).after(html);
}

function createStatusQuoImagesList(result) {
    var repairPhotos = result.repairPhotos;
    var data = result.statusQuo;
    var actionName = result.actionName;
    var relic = result.relic;
    var picturesBasePath = result.picturesBasePath;
    var siteId = relic.siteId;
    var relicId = relic.id;
    var totalCode = relic.totalCode;
    var repairrecordId = data.id;
    var head =
        "<tr class='statusQuoImagesRow'>" +
        "    <td colspan='4'>图片列表</td>" +
        "</tr>";
    var body = "";
    for (var i = 0; i < repairPhotos.length; i++) {
        var repairPhoto = repairPhotos[i];
        body = body +
            "<tr class='statusQuoImagesRow'>" +
            "<td><img src='" + picturesBasePath + "/" + siteId + "/" + relicId + "/" + repairPhoto.path + "' style='max-width: 400px;max-height: 300px; text-align: center'/></td>" +
            "<td style='width: 30%'>" + repairPhoto.path + "</td>" +
            "<td>" + repairPhoto.description + "</td>" +
            "<td><a onclick='delImage($(this))' link='archives/deleteStatusQuoPhoto?repairPhotoId=" + repairPhoto.id + "&actionName=" + actionName + "&repairRecordId=" + repairrecordId + "&relicId=" + relicId + "'>删除</a></td>" +
            "</tr>";
    }
    return head + body;
}





