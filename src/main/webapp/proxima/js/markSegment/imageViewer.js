/**
 * imageViewer.html 页面中的主要逻辑
 * 
 * 依赖：imageViewer-canvas.js
 * 
 * @author gaohui
 * @date 2012-06-19
 */

$(function() {
    //从 dom 加载 markSegment 数据
    var loadMarkSegmentsFromDom = (function () {
        return function () {
            var $markSegments = $("#mark-segments");
            var markSegments = new Array();
            $markSegments.children().each(function () {
                var $markSegment = $(this);
                var msId = $markSegment.attr("data-markSegmentId");
                msId = parseInt(msId);
                var msPositionId = $markSegment.attr("data-markSegmentPositionId");
                msPositionId = parseInt(msPositionId);
                var msName = $markSegment.attr("data-markName");
                var msPositionX = $markSegment.attr("data-positionX");
                msPositionX = parseInt(msPositionX);
                var msPositionY = $markSegment.attr("data-positionY");
                msPositionY = parseInt(msPositionY);
                var msPositionX2 = $markSegment.attr("data-positionX2");
                msPositionX2 = parseInt(msPositionX2);
                var msPositionY2 = $markSegment.attr("data-positionY2");
                msPositionY2 = parseInt(msPositionY2);
                var markSegment = {
                    markSegmentId:msId,
                    markSegmentName:msName,
                    markSegmentPositionId:msPositionId,
                    startPoint:{
                        x:msPositionX,
                        y:msPositionY
                    },
                    endPoint:{
                        x:msPositionX2,
                        y:msPositionY2
                    }
                };
                markSegments.push(markSegment);
            });
            return markSegments;
        };
    })();

    /**
     * 初始化页面中的画布, 按钮事件
     */
	var init = null;
	var saveMarkSegmentPosition = null;
	(function() {
		init = function(picId, imageUrl, imageSrcWidth, imageSrcHeight,
				imageRealWidth, addMarkSegmentCallback,
				removeMarkSegmentCallback) {
			var MOVE_IMAGE = 1;
			var DRAW_LINE = 2;
			var whatToDo = MOVE_IMAGE;

			var canvasContainer = new CanvasContainer(imageUrl, imageSrcWidth,
					imageSrcHeight, imageRealWidth);
			canvasContainer
					.registerAddMarkSegmentListener(addMarkSegmentCallback);
			canvasContainer
					.registerRemoveMarkSegmentListener(removeMarkSegmentCallback);
			
			//保存标记段坐标
			/**
			 * <pre>
			 * callback(result)
			 * </pre>
			 * will be call
			 */
			saveMarkSegmentPosition = function(callback){
                var lineHashes = canvasContainer.getLinesHash();
                lineHashes.positionX = Math.round(lineHashes.positionX);
                lineHashes.positionY = Math.round(lineHashes.positionY);
                lineHashes.positionX2 = Math.round(lineHashes.positionX2);
                lineHashes.positionY2 = Math.round(lineHashes.positionY2);
                var markSegmentsJSON = JSON.stringify(lineHashes);
                $.post("addOrUpdateMarkSegmentPosition.action", {picId:picId, markSegments: markSegmentsJSON}, callback);
			};

			var bindEvent = function() {
				// 点击移动
				$("#move-image-button").unbind("click").click(function() {
					if (whatToDo != MOVE_IMAGE) {
						whatToDo = MOVE_IMAGE;
						canvasContainer.undrawLine();
						canvasContainer.movePaper();
					}
				});
				// 点击画线
				$("#draw-line-button").unbind("click").click(function() {
					if (whatToDo != DRAW_LINE) {
						whatToDo = DRAW_LINE;
						canvasContainer.unmovePaper();
						canvasContainer.drawLine();
					}
				});
			};

			var resetCanvas = function() {
				canvasContainer.reset();
				canvasContainer.movePaper();
				bindEvent();
                canvasContainer.loadLines(loadMarkSegmentsFromDom());
			};

			resetCanvas();
			$("#reset-button").click(function() {
				resetCanvas();
				$("#move-image-button").trigger("click");
			});

			$("#imageRealWidth-input").focusout(function() {
                canvasContainer.updateImageRealWidth($(this).val());
				canvasContainer.clearRuler();
				canvasContainer.drawRuler();
				$(this).attr("readonly", "readonly");
			});

			$("#imageRealWidth-input").click(function() {
				console.log("dbl click");
				//取消编辑 "实景宽度"
				//$(this).removeAttr("readonly");
			});
			
			$("#save-button").click(function(){
				/************************************
				  
				 
                var lineHashes = canvasContainer.getLinesHash();
                var markSegmentsJSON = JSON.stringify(lineHashes);
                $.post("addOrUpdateMarkSegmentPosition.action", {picId:picId, markSegments: markSegmentsJSON}, function(result){
                	console.log("back");
                	if(result.success){
	                	art.dialog({time:2000, title:"提示", content:"保存成功"});
                	}else{
	                	art.dialog({time:2000, title:"提示", content:"保存失败"});
                	}
                });
                
                ***************************************/
				
				saveMarkSegmentPosition(function(result){
                	if(result.success){
	                	art.dialog({time:2000, title:"提示", content:"保存成功"});
                	}else{
	                	art.dialog({time:2000, title:"提示", content:"保存失败"});
                	}
                });
                
			});

		};
	})();
    
	// canvas
	var $canvas = $("#canvas");
	var imageUrl = $canvas.attr("data-image-url");
	var imageRealWidth = $canvas.attr("data-image-real-width");
	var picId = $canvas.attr("data-image-id");
	var dvPlaceId = $canvas.attr("data-dv-place-id");

	ImageViewer.naturalSize(imageUrl, function(width, height) {
		init(picId, imageUrl, width, height, imageRealWidth, function(wrapperLine, markSegment, onSuccess, onError) {
			markSegment = _.extend(markSegment,{picId:picId, dvPlaceId:dvPlaceId});
			markSegment.positionX = Math.round(markSegment.positionX);
			markSegment.positionY = Math.round(markSegment.positionY);
			markSegment.positionX2 = Math.round(markSegment.positionX2);
			markSegment.positionY2 = Math.round(markSegment.positionY2);
			
			//保存标记段
			$.post("addMarkSegment.action",markSegment,function(result){
				if(result.success){
		            wrapperLine.markSegmentId = result.markSegmentId;
		            wrapperLine.markSegmentPositionId = result.markPositionId;
		            //更新其他标记段坐标
					saveMarkSegmentPosition(function(savePositionResult){
						if(savePositionResult.success){
				            onSuccess();
						}else{
							onError("保存失败");
						}
					});
				}else{
					onError(result.message);
				}
			});
		}, function(wrapperLine) {
			$.post("cancelMarkSegment.action",{markSegmentId:wrapperLine.markSegmentId},function(result){
				console.log(result);
			});
		});
	});
});
