//检测图片原始大小
(function (window) {
    window.ImageViewer = {
        naturalSize:function (imgSrc, callback) {
            jQuery("<img/>").load(
                function () {
                    var naturalWidth = this.width;
                    var naturalHeight = this.height;
                    callback(naturalWidth, naturalHeight);
                }).attr("src", imgSrc);
        }};
})(window);