//状态栏
function Status() {
}

$(function () {
    /**
     * init status update functions.
     *
     */
    (function () {
        Status.updateImageRealWidth = (function () {
            var $imageRealWidthInput = $("#imageRealWidth-input");
            return function (imageRealWidth) {
                $imageRealWidthInput.val(imageRealWidth);
            };
        })();

        Status.info = (function () {
            var $infoBar = $("#info-bar");
            return function (msg) {
                $infoBar.empty().append(msg);
            };
        })();

        Status.printLog = (function () {
            var $log = $("#log");
            return  function (msg) {
                $log.prepend("<div>" + msg + "</div>");
            };
        })();

        Status.updateImageCoordinate = (function () {
            var $xCoordinate = $("#coordinate-x");
            var $yCoordinate = $("#coordinate-y");
            return  function (x, y) {
                $xCoordinate.text(x);
                $yCoordinate.text(y);
            };
        })();

        Status.updateImageSrcDimension = (function () {
            var $width = $("#width");
            var $height = $("#height");
            return  function (width, height) {
                $width.text(width);
                $height.text(height);
            };
        })();

        /**
         * @deprecated
         * @type {*}
         */
        Status.updateImageNowDimension = (function () {
            var $nowWidth = $("#now-width");
            var $nowHeight = $("#now-height");
            return  function (width, height) {
                $nowWidth.text(width);
                $nowHeight.text(height);
            };
        })();

        /**
         * @deprecated
         * @type {*}
         */
        Status.updateScale = (function () {
            var $scale = $("#scale");
            var $scaleTimes = $("#scale-times");
            var $scaleIncrement = $("#scale-scaleIncrement");
            return  function (scale, scaleTimes, scaleIncrement) {
                $scale.text(scale);
                $scaleTimes.text(scaleTimes);
                $scaleIncrement.text(scaleIncrement);
            };
        })();

    })();
});