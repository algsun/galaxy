<#assign title="成果展示">
<#assign currentTopPrivilege = "saturn:result">
<#-- 当前权限标识 -->

<#macro head>
<link rel="stylesheet" type="text/css" href="css/result.css">
</#macro>

<#macro content>
<div class="container-fluid m-t-20">
    <div class="row-fluid">
        <form class="form-inline well well-small" id="result-form" action="result" method="get">
            <input type="text" name="desc" id="desc" value="${desc!}"/>
            <input type="submit" class="btn" value="搜索"/>

            <select id="selInterv" onchange="SetInterv(this.options[this.options.selectedIndex].value)" class="m-l-20">
                <option value="8">播放时间</option>
                <option value="2">2秒</option>
                <option value="4">4秒</option>
                <option value="6">6秒</option>
                <option value="8">8秒</option>
                <option value="10">10秒</option>
                <option value="12">12秒</option>
                <option value="14">14秒</option>
                <option value="16">16秒</option>
                <option value="18">18秒</option>
                <option value="20">20秒</option>
                <option value="25">25秒</option>
                <option value="30">30秒</option>
                <option value="35">35秒</option>
                <option value="40">40秒</option>
                <option value="45">45秒</option>
                <option value="50">50秒</option>
                <option value="55">55秒</option>
                <option value="60">60秒</option>
            </select>
            <select name="year" onchange="submitDoc()" id="year" class="m-l-20">
                <option <#if year??><#if year == 2016>selected="selected"</#if></#if> value="2016">2016</option>
                <option <#if year??><#if year == 2015>selected="selected"</#if></#if> value="2015">2015</option>
                <option <#if year??><#if year == 2014>selected="selected"</#if></#if> value="2014">2014</option>
                <option <#if year??><#if year == 2013>selected="selected"</#if></#if> value="2013">2013</option>
                <option <#if year??><#if year == 2012>selected="selected"</#if></#if> value="2012">2012</option>
                <option <#if year??><#if year == 2011>selected="selected"</#if></#if> value="2011">2011</option>
                <option <#if year??><#if year == 2010>selected="selected"</#if></#if> value="2010">2010</option>
                <option <#if year??><#if year == 2009>selected="selected"</#if></#if> value="2009">2009</option>
                <option <#if year??><#if year == 2008>selected="selected"</#if></#if> value="2008">2008</option>
                <option <#if year??><#if year == 2007>selected="selected"</#if></#if> value="2007">2007</option>
                <option <#if year??><#if year == 2006>selected="selected"</#if></#if> value="2006">2006</option>

            </select>

            <select name="quarterNum" onchange="submitDoc()" id="quarterNum" class="m-l-20">
                <option value="">全年</option>
                <option <#if quarterNum??><#if quarterNum == 1>selected="selected"</#if></#if> value="1">第一季度</option>
                <option <#if quarterNum??><#if quarterNum == 2>selected="selected"</#if></#if> value="2">第二季度</option>
                <option <#if quarterNum??><#if quarterNum == 3>selected="selected"</#if></#if> value="3">第三季度</option>
                <option <#if quarterNum??><#if quarterNum == 4>selected="selected"</#if></#if> value="4">第四季度</option>

            </select>

            <select name="textureId" onchange="submitDoc()"
                    id="texture" class="m-l-20">
                <option selected="selected" value="">材质</option>
                <#list textureVOs as texture>
                    <option <#if textureId??><#if textureId == texture.id>selected="selected" </#if> </#if>value=${texture.id}>${texture.name}</option>
                </#list>
            </select>
        </form>
    </div>
    <div class="row-fluid">
        <div class="span6 row-div">
            <img id="img1" class="row-div-img" src="">

            <p id="title1" class="row-div-title"></p>

            <p id="desc1" class="row-div-p m-b-20"></p>
        </div>
        <div class="span6 row-div">
            <img src="" id="img2" class="row-div-img">

            <p id="title2" class="row-div-title"></p>

            <p id="desc2" class="row-div-p m-b-20"></p>
        </div>


    </div>
    <div class="row-fluid m-t-30">
        <div class="span12" id="detail_context_pic_bot"
             style="border-top: solid #dddddd 1px;border-bottom: solid #dddddd 1px; background-color: #ffffff">
            <a href="javascript:void(0)" id="preArrow_B" style="float: left"><img
                    id="play_prev" src="images/left1.jpg" alt="上一个"></a>

            <div class="detail_picbot_mid" id="thumbs">
                <ul id="ulThumbs">
                    <#list mediaDetailPOs as md>
                        <li class="slideshowItem"><a id="thumb_${md.id}" href="#" class="">
                            <img src="${md.detailImage}" width="90" height="60"></a>
                        </li>
                    </#list>
                </ul>
            </div>
            <a href="javascript:void(0)" id="nextArrow_B" style="float: right"><img
                    id="play_next" src="images/right1.jpg" alt="下一个"></a>
        </div>
    </div>

</div>
</#macro>

<#macro script>
<script type="text/javascript">
    var target = [];
            _ShowCount = ${mediaDetailPOs?size}
        <#list mediaDetailPOs as md>
                target.push(${md.id})
        </#list>
    var timer = null;
    var offset = 5000;
    var index = 0;
    var mcount = _ShowCount;
    var snum = 12;

    function submitDoc() {
        $("#result-form").submit();
    }
</script>
<script type="text/javascript" src="js/slide_arch.js"></script>
</#macro>