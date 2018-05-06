$(function () {

	var MAX_WIDTH = 300;
	var imgWidth = 0, imgHeight = 0;//原始图片大小
	var imageSrc = $("#imgLeft").attr("src");//原始图片路径
	var RECWidth = 64, RECHeight = 36;//可拖动小区域宽和高
	var multiple = 0;//原图缩放倍数
	var enlargeDivWidth = 0, enlargeDivHeight = 0;//展示区域图宽和高
	var rectLeft = null;
	var rectRight = null;
	window.count = 0;//添加矩形个数
	
	//显示上面原图位置
	var imageX  = 0, imageY = 0;
	
	var areaX, areaY, areaWidth, areaHeight;//计算画矩形区域变量
	var thumbnailsWidth, thumbnailsHeight;//缩略图宽和高
	
	Images.naturalSize(imageSrc, function (width, height) {
	 	imgWidth = width;
	 	imgHeight = height;
	 	//计算时间比例
	 	for (var i =1; i < 1000; i++) {
	 		if (imgWidth/i <= MAX_WIDTH) {
	 			multiple = i;
	 			break;
	 		}
	 	}
	 	
	 	(function(){
	     	//计算可拖动区域的宽和高
	   		RECWidth = imgWidth / (multiple + 20);
	   		RECHeight = imgHeight / (multiple + 20);
	     	
	     	//计算展示区域图片宽和高
	     	enlargeDivWidth = RECWidth * multiple;
	     	enlargeDivHeight = RECHeight * multiple;
	     	
	     	//修改展示区域的样式
	     	$("#enlargeDiv").width(enlargeDivWidth);
	   		$("#enlargeDiv").height(enlargeDivHeight);
	     	$("#enlargeDiv2").width(enlargeDivWidth);
	   		$("#enlargeDiv2").height(enlargeDivHeight);
	     	
	     	//计算缩略图的高和宽
	     	thumbnailsWidth = imgWidth / multiple;
	     	thumbnailsHeight = imgHeight / multiple;
	     	
	     	//修改缩略图的样式
	     	$("#image0").width(thumbnailsWidth);
	   		$("#image0").height(thumbnailsHeight);
	     	$("#image1").width(thumbnailsWidth);
	   		$("#image1").height(thumbnailsHeight);
		})();
		
		
		var enlargeDivLeft = null;
		var enlargeDivRight = null;
		(function(){
	        var playground = Painter.playground({
	            id:"#image0"
	        });
	
	        var playground2 = Painter.playground({
	            id:"#image1"
	        });
	
	        //说明：左右区域那个先画图那个为主区域，副区域不能画图。
	        var updateArea = function(rect){
	           var box = rect.box();
	           areaX = box.x;
	           areaY = box.y;
	           areaWidth = box.width;
	           areaHeight = box.height;
	        };
	
	        //画矩形区域
	        enlargeDivLeft = Painter.playground({
	            id:"#enlargeDiv",
	            rectComplete:function (newRect) {
	                newRect.resizable();
	                rectLeft = newRect;
	                updateArea(rectLeft);
	                //获取异常文物位置信息
	                rectLeft.onDrag(function () {
	                   updateArea(rectLeft);
	                });
	
	                enlargeDivLeft.undrawable();
	                //屏蔽右边画图功能。
	                enlargeDivRight.undrawable();
	            }
	        });
	
	        enlargeDivRight = Painter.playground({
	            id:"#enlargeDiv2",
	            rectComplete:function (newRect) {
	                newRect.resizable();
	                rectRight = newRect;
					updateArea(rectRight);
	                //获取异常文物位置信息
	                rectRight.onDrag(function () {
	                   updateArea(rectRight);
	                });
					enlargeDivRight.undrawable();
	                //屏蔽左边画矩形功能
	                enlargeDivLeft.undrawable();
	            }
	        });
	  
	        var rect = playground.paintRect(0, 0, RECWidth, RECHeight);
	        playground.undrawable();
	
	        var rect2 = playground2.paintRect(0, 0, RECWidth, RECHeight);
	        playground2.undrawable();
	
	        // 防止超过边界
	        var doBoundary = function(box){
	            if (box.x <= 0) {
	                box.x = 0;
	            }
	            if (box.y <= 0) {
	                box.y = 0;
	            }
	            if (box.x + RECWidth >= thumbnailsWidth) {
	                box.x = thumbnailsWidth - RECWidth;
	            }
	            if (box.y + RECHeight >= thumbnailsHeight) {
	                box.y = thumbnailsHeight - RECHeight;
	            }
	            return box;
	        };
	
	        var updateSourceImageOffset = function(referenceRect){
	            var newBox = doBoundary(referenceRect.box());
	            rect.setOffset(newBox.x, newBox.y);
	            rect2.setOffset(newBox.x, newBox.y);
	
	            //原图位置
	            imageX = newBox.x * multiple;
	            imageY = newBox.y * multiple;
	
	            $("div.image-background").css({
	                'background-position':(-imageX) + 'px ' + (-imageY) + 'px'
	            });
	        };
	
	
	        //左缩略图拖动事件
	        rect.onDrag(function () {
	            updateSourceImageOffset(rect);
	
	        });
	
	        //右缩略图拖动事件
	        rect2.onDrag(function () {
	            updateSourceImageOffset(rect2);
	        });
	    })();
	
	
	    //添加矩形
	    $("#add-button").click(function () {
	        var rect = null;
	        _.each([rectLeft, rectRight], function(rectEle){
	            if (rectEle != null) {
	           		rect = rectEle;
	            }
	        });
	        if (rect == null) {
	        	return;
	        }
	        //计算矩形在原图中的坐标
	        var artworkX, artworkY;
	        artworkX = areaX + imageX;
	        artworkY = areaY + imageY;
	        
	        var exceptionRegionDom = ['<li ',
	            'data-rect-x="',
	            artworkX,
	            '"',
	            'data-rect-y="',
	            artworkY,
	            '"',
	            'data-rect-width="',
	            areaWidth,
	            '"',
	            'data-rect-height="',
	            areaHeight,
	            '"',
	            ' >',
	        	++count,
	            '<button type="button" class="close" title="删除">×</button>',
	            '</li>'].join('');
	        
	         var $exceptionRegionDom = $("#add_ul").append(exceptionRegionDom).children().last();
	        _.each([rectLeft, rectRight], function(rectEle){
	            if (rectEle != null) {
	              	rectEle.remove();
	            }
	            
	        });
	        rectLeft = null;
	        rectRight = null;
	      	
	      	//清除未添加异常区域提示
	        $("#ul-input-help").empty();
	         
	         //删除异常区域
	         $exceptionRegionDom.find('button').click(function () {
	             	$exceptionRegionDom.remove();
             });
            enlargeDivLeft.drawable();
            enlargeDivRight.drawable();
	        });
	     });
	  });
       
          