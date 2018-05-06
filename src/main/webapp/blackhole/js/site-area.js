/**
 *
 *<pre>
 *
 *</pre>
 * @author: Wang yunlong
 * @date: 12-12-4 上午10:49
 */
$(function () {
    var $province = $("#province");
    var $city = $("#city");
    var $zone = $("#zone");
    $province.change(function () {
        var $this = $(this);
        if ($this.val() != "0") {
            getArea($this.val(), $city)
            getSite($this.val());
            $zone.children().remove();
        }
    });
    $city.change(function () {
        var $this = $(this);
        if ($this.val() == "0") {
            getSite($province.val());
        } else {
            getArea($this.val(), $zone)
            getSite($this.val());
        }
    });
    $zone.change(function () {
        var $this = $(this);
        if ($this.val() == "0") {
            getSite($city.val());
        } else {
            getSite($this.val());
        }
    });
});
//区域选择后  重新设置下级区域和基层站点的select
function getArea(areaCode, $select) {
    var option = "<option value='0'>请选择</option>";
    if ($select != null) {
        $.getJSON("getArea.action", {areaCode:parseInt(areaCode)}, function (areas) {
            $select.children().remove();
            var options = option;
            $.each(areas, function (index, area) {
                options += "<option value=" + area.areaCode + ">" + area.areaName + "</option>"
            });
            $select.append(options);
        });
    }
}

function getSite(areaCode) {
    var option = "<option value='0'>请选择</option>";
    $.getJSON("getSite.action", {areaCode:parseInt(areaCode)}, function (sites) {
        var $siteSelect = $("#site").find("select");
        $siteSelect.children().remove();
        $("#site-name").find("input").val("");
        var options = option;
        $.each(sites, function (index, site) {
            options += "<option value=" + site.siteId + ">" + site.siteName + "</option>"
        });
        $siteSelect.append(options);
    });
}


