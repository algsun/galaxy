$(function () {
    var helpClear = function ($help) {
        $help.empty();
    };

    // 错误提示
    var helpError = function ($help, msg) {
        var content = "<i class='mw-icon-cancel'></i> " + msg;
        $help.empty().append(content);
    };

    // 正确提示
    var helpOk = function ($help, msg) {
        var content = "<i class='mw-icon-ok'></i> " + msg;
        $help.empty().append(content);
    };

    // 正确提示
    var help = function ($help, msg) {
        var content = msg;
        $help.empty().append(content);
    };

    // 刷新图片列表
    var refreshImageList = function( $imageList ,pictures, leftOrRight){
        //如果没有图片直接返回
        if(pictures.length === 0){
            return ;
        }
        $imageList.empty();
        for (var i = 0 ;i < pictures.length; i++) {
            var picture = pictures[i];
            var url = picture.path;
            if(leftOrRight == 'left'){
                $imageList.append('<li> <img date-picture-id="'+ picture.id+"\" onclick=\"updateLeftImgUrl('"+url+"',"+picture.id+")\" src='" + url+"'> <span>"+ picture.date +"</span> </li>");
            }
            else if(leftOrRight == 'right'){
                $imageList.append('<li> <img date-picture-id="'+ picture.id+"\" onclick=\"updateRightImgUrl('"+url+"',"+picture.id+")\" src='" + url+"'> <span>"+ picture.date +"</span> </li>");
            }
        }
    };

    var doLoadPictures = function(startDate, endDate, $imageList, leftOrRight){
        // 根据查询条件获取图片信息
        var dvPlaceId = $("#dvPlaceId").val();
        $.post('loadPictures.action', {dvPlaceId:dvPlaceId, startDateStr:startDate, endDateStr:endDate}, function (result) {
            //如果失败，直接返回
            if(!result.success){
                return;
            }
            var pictures = result.pictures;
            refreshImageList($imageList, pictures, leftOrRight);
        });
    };

    var doLoadPicturesMax = function(endDate, $imageList, leftOrRight){
        var dvPlaceId = $("#dvPlaceId").val();
        $.post('loadPicturesMax.action',{dvPlaceId :dvPlaceId, dateStr : endDate, max: 20},function(result){
            //如果失败，直接返回
            if(!result.success){
                return;
            }

            var $startDate = $("#startDateStrRight");
            var pictures = result.pictures;
            if(pictures.length > 0){
                var lastPicture = pictures[pictures.length - 1];
                $startDate.val(lastPicture.date);
            }
            refreshImageList($imageList, result.pictures, leftOrRight);
        });
    };

    var  loadPictures = function(startDate, endDate, $help, $imageList, leftOrRight){
        var $dateStrFelt_help = $help;
        if (startDate == "") {
            helpError($dateStrFelt_help, "开始时间不能为空");
            return false;
        }
        if (endDate == "") {
            helpError($dateStrFelt_help, "结束时间不能为空");
            return false;
        }
        if (startDate > endDate){
            helpError($dateStrFelt_help, "开始时间不能大于结束时间");
            return false;
        }

        doLoadPictures(startDate, endDate, $imageList, leftOrRight);

        help($dateStrFelt_help, "");
        return true;
    };

    //快速条件选择按钮事件
    //左边
    (function(){
        var $imageList = $("#findimg");
        var $startDate = $("#startDateStrLeft");
        var $endDate = $("#endDateStrLeft");
        var format = 'YYYY-MM-DD HH:mm';
        var leftOrRight = 'left';
        var updateInputAntLoadPictures = function(dateStart, dateEnd, $imageList, leftOrRight){
            $startDate.val(dateStart);
            $endDate.val(dateEnd);

            doLoadPictures(dateStart, dateEnd, $imageList, leftOrRight);
        };
        //今天
        $("#left-today").click(function(){
            var todayStart = moment().hours(0).minutes(0).format(format);
            var todayEnd = moment().hours(23).minutes(59).format(format);
            updateInputAntLoadPictures(todayStart, todayEnd, $imageList, leftOrRight);
        });

        //最近一周
        $("#left-week").click(function(){
            var todayStart = moment().subtract('days',6).hours(0).minutes(0).format(format);
            var todayEnd = moment().hours(23).minutes(59).format(format);

            updateInputAntLoadPictures(todayStart, todayEnd, $imageList, leftOrRight);
        });

        //最近一月
        $("#left-month").click(function(){
            var todayStart = moment().subtract('days',29).hours(0).minutes(0).format(format);
            var todayEnd = moment().hours(23).minutes(59).format(format);

            updateInputAntLoadPictures(todayStart, todayEnd, $imageList, leftOrRight);
        });
    })();

    //右边快捷选择按钮
    (function(){
        var $startDate = $("#startDateStrRight");
        var $endDate = $("#endDateStrRight");
        var $imageList = $("#findimg2");
        var format = 'YYYY-MM-DD HH:mm';
        var leftOrRight = 'right';

        var updateInputAndLoadPictures = function(endDate, $imageList, leftOrRight){
            $endDate.val(endDate);
            doLoadPicturesMax(endDate, $imageList, leftOrRight);
        };

        //昨天
        $("#right-yesterday").click(function(){
            var endDate = moment().subtract('days', 1).hours(23).minutes(59).format(format);

            updateInputAndLoadPictures(endDate,$imageList, leftOrRight);
        });

        //一周前
        $("#right-week").click(function(){
            var endDate = moment().subtract('days', 7).hours(23).minutes(59).format(format);

            updateInputAndLoadPictures(endDate,$imageList, leftOrRight);
        });

        //一月前
        $("#right-month").click(function(){
            var endDate = moment().subtract('days', 30).hours(23).minutes(59).format(format);

            updateInputAndLoadPictures(endDate,$imageList, leftOrRight);
        });
    })();

    // 根据时间段获取现在时间的图片
    findRelic = function () {
        
		setTimeout(function(){
			var startDate = $("#startDateStrLeft").val();
			var endDate = $("#endDateStrLeft").val();
			var $dateStrFelt_help = $("#dateStrFelt-input-help");
			var dvPlaceId = $("#dvPlaceId").val();

            loadPictures(startDate, endDate, $dateStrFelt_help, $("#findimg"), 'left');
		},200);
        
    };

	// 根据时间段获取现在时间的图片
    findRelic2 = function () {
		setTimeout(function(){
			var startDate = $("#startDateStrRight").val();
			var endDate = $("#endDateStrRight").val();
			var $dateStrFelt_help = $("#dateStrRight-input-help");
			var dvPlaceId = $("#dvPlaceId").val();

            loadPictures(startDate,endDate, $dateStrFelt_help, $("#findimg2"), 'right');
		},200);
        
    };


	// 根据左边选择图片更换展示图片
	updateLeftImgUrl = function (url,value) {
		$("#enlargeDiv").css({
	        'background-image': 'url('+url+')'
	    });
	    $("#imgLeft").attr("src",url);
	    $("#exceptionPictureId").val(value);
	};
	 
	// 根据右边选择图片更换展示图片
	updateRightImgUrl = function (url,value) {
		$("#enlargeDiv2").css({
	        'background-image': 'url('+url+')'
	    });
	    $("#imgRight").attr("src",url);
	    $("#contrastPictureId").val(value);
	};

	//验证异常名称
	var exceptionNameCheck = null;
    (function () {
        var $exceptionName_input = $("#exceptionName-input");
        var $exceptionName_help = $("#exceptionName-input-help");

        exceptionNameCheck = function () {

            var exceptionName = $exceptionName_input.val();
            if (exceptionName == "") {
                helpError($exceptionName_help, "异常名称不能为空");
                return false;
            }

            helpOk($exceptionName_help, "");
            return true;
        };

        $exceptionName_input.focusout(function () {
        	exceptionNameCheck();
        });

        $exceptionName_input.focusin(function () {
            helpClear($exceptionName_help);
        });
    })();
    
  //验证异常描述
	var exceptionPlaceCheck = null;
    (function () {
        var $exceptionPlace_input = $("#exceptionPlace-input");
        var $exceptionPlace_help = $("#exceptionPlace-input-help");

        exceptionPlaceCheck = function () {

            var exceptionPlace = $exceptionPlace_input.val();
            if (exceptionPlace == "") {
                helpError($exceptionPlace_help, "异常描述不能为空");
                return false;
            }

            helpOk($exceptionPlace_help, "");
            return true;
        };

        $exceptionPlace_input.focusout(function () {
        	exceptionPlaceCheck();
        });

        $exceptionPlace_input.focusin(function () {
            helpClear($exceptionPlace_help);
        });
    })();
    
  
    //验证关键字是否选中
	var keywordCheck = null;
    (function () {
        var $keyword_input = $("input[name=checkboxs]");
        var $keyword_help = $("#keyword-input-help");

        keywordCheck = function () {
        	var falg = 0;
            $("input[name=checkboxs]:checkbox").each(function(){ 
            	if($(this).attr("checked")) { 
            	falg +=1; 
            	} 
            });
            if (falg == 0) {
            	 helpError($keyword_help, "关键字不能为空");
                 return false;
            }

            helpOk($keyword_help, "");
            return true;
        };

        $keyword_input.focusout(function () {
        	keywordCheck();
        });

        $keyword_input.focusin(function () {
            helpClear($keyword_help);
        });
    })();
    
  //验证文件上传
	var fileCheck = null;
    (function () {
        var $file_input = $("#file-input");
        var $file_help = $("#file-input-help");
        var fileSize = 1024;
        fileCheck = function () {

            var file = $file_input.val();
            if (file == "") {
                helpError($file_help, "上传文件不能为空");
                return false;
            }
            if(!/.(gif|jpg|jpeg|png|gif|jpg|png|doc|docx|pdf)$/.test(file.toLowerCase())){
                helpError($file_help, "文件类型必须是gif|jpg|jpeg|png|gif|jpg|png|doc|docx|pdf中的一种");
                return false;
            }

            helpOk($file_help, "");
            return true;
        };

        $file_input.focusout(function () {
        	fileCheck();
        });

        $file_input.focusin(function () {
            helpClear($file_help);
        });
    })();
   

  
    // 表单提交
    $("#submit-button").click(function () {
    	
    	//验证异常名称
    	if (!exceptionNameCheck()) {
            return false;
        }
    	//验证异常描述
    	if (!exceptionPlaceCheck()) {
            return false;
        }
    	//验证关键字
    	if (!keywordCheck()) {
            return false;
        }
    	//验证文件上传
    	if (!fileCheck()) {
            return false;
        }
    	
       
    	var exceptionRegionValues = [];
    	// 变量ul下的li的值
    	$("#add_ul").children("li").each(function () {
    		var x = $(this).attr("data-rect-x");
    		var y = $(this).attr("data-rect-y");
    		var width = $(this).attr("data-rect-width");
    		var height = $(this).attr("data-rect-height");
    		exceptionRegionValues.push({
    		    x:parseFloat(x),
    		    y:parseFloat(y),
    		    width:parseInt(width),
    		    height:parseInt(height)
    		});
    	});
    	
        var exceptionRegionsJson = JSON.stringify(exceptionRegionValues);
    	console.log(exceptionRegionsJson);
    	$("#exceptionRegionsJson").val(exceptionRegionsJson);
    	console.log($("#exceptionRegionsJson").val());
    	//验证是否画异常区域
    	if ($("#exceptionRegionsJson").val() == "[]") {
    		helpError($("#ul-input-help"), "请添加异常区域");
            return false;
    	} else {
    		 helpClear($("#ul-input-help"));
    	}
    	
        art.dialog({
            title:'确认提示',
            content:'您确定执行该操作吗?',
            okValue:'确定',
            ok:function () {
            	form1.action="savePictureAnalysis.action";
                form1.submit();
            },
            cancelValue:'取消',
            cancel:function () {
            }
        });
        return false;
    });
});