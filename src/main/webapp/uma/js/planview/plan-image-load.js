/**
 *
 * @author: Wang yunlong
 * @time: 13-4-18 下午2:12
 */
    // 获取图片原始大小
window.Images = {
    naturalSize: function (imgSrc, callback) {
        jQuery("<img/>").load(function () {
            var naturalWidth = this.width;
            var naturalHeight = this.height;
            callback(naturalWidth, naturalHeight);
        }).attr("src", imgSrc);
    }
};

