function toogleTable(showid) {
    if (showid != undefined) {
        // alert(showid);
        $.ajax({
            type: "GET",
            url: "findMediaDetail",
            data: { id: showid},
            dataType: "json",
            success: function (data) {
                $('#title1').text(data.detailTitle);
                $('#desc1').text(CutString(data.detailContent, 110));
                $('#img1').attr("src", data.detailImage);
                $('#title2').text(data.detailSubTitle);
                $('#desc2').text(CutString(data.detailSubDesc, 110));
                $('#img2').attr("src", data.detailSubImage);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                //alert(errorThrown);
            }
        });
    }

}
function SetInterv(sec) {
    if (sec == '') return;
    offset = eval(sec) * 1000;
    if (timer) {
        clearTimeout(timer);
    }
    timer = window.setTimeout(auto, offset);

}

function CutString(src, len) {
    return src.length > len ? src.substr(0, len) + '...' : src;
}

//大图交替轮换
function slideImage(i) {
    var id = target[i];
    toogleTable(id);
}
//bind thumb a
function hookThumb() {
    $('#thumbs li a')
        .bind('click', function () {
            if (timer) {
                clearTimeout(timer);
            }
            var ids = this.id.split("_");
            index = getIndex(ids[1]);
            rechange(index);
            slideImage(index);
            timer = window.setTimeout(auto, offset);
            this.blur();
            return false;
        });
}
//bind next/prev img
function hookBtn() {

    $('#detail_context_pic_bot img').filter('#play_prev,#play_next')
        .bind('click', function () {
            if (timer) {
                clearTimeout(timer);
            }
            var id = this.id;

            if (id == 'play_prev') {

                index--;
                if (index < 0) {
                    index = 0;
                    initThumb();
                }
                else
                    ScrollThumbs(index);
            } else {

                index++;

                if (index > mcount) {
                    index = 0;
                    initThumb();
                }
                else
                    ScrollThumbs(index);

            }
            rechange(index);
            slideImage(index);
            timer = window.setTimeout(auto, offset);
        });
}
function initThumb() {
    $(".detail_picbot_mid ul li").gt(snum - 1).css("display", "none");
    $(".detail_picbot_mid ul li").lt(snum).css("display", "block")
}

//get index
function getIndex(v) {
    for (var i = 0; i < target.length; i++) {
        if (target[i] == v) return i;
    }
}
function rechange(loop) {
    var id = 'thumb_' + target[loop];
    $('#thumbs li a.current').removeClass('current');
    $('#' + id).addClass('current');
}
function auto() {

    rechange(index);
    slideImage(index);
    timer = window.setTimeout(auto, offset);
    index++;
    if (index > mcount) {
        index = 0;
    }
    else {
        ScrollThumbs(index);
    }
}

function ScrollThumbs(inx) {
    var currrentindex = inx;

    var length = $(".detail_picbot_mid ul li").length;
    if (currrentindex <= length - snum && currrentindex > 0) {
        $(".detail_picbot_mid ul li").eq(currrentindex + snum).css("display", "node");
        $(".detail_picbot_mid ul li").eq(currrentindex - 1).css("display", "block");
    }
    if (currrentindex >= snum && currrentindex < length) {
        $(".detail_picbot_mid ul li").eq(currrentindex - snum).css("display", "none");
        $(".detail_picbot_mid ul li").eq(currrentindex + 1).css("display", "block")
    }
}
function Initial() {
    $('div.word').css({ opacity: 0.85 });
    auto();
    hookThumb();
    hookBtn();

}
$(function () {
    //change opacity
    Initial();
});