<#--
外展数据分析
@author baiweixing
@date 2016-10-08
-->
<#assign title="数据分析-外展数据分析">
<#--当前权限标识-->
<#assign currentPrivilege="phoenix:orion:outreachDataAnalysis">
<#macro head>
<style type="text/css">
    .nav-pills > li > a {
        padding-top: 8px;
        padding-bottom: 8px;
        margin-top: 2px;
        margin-bottom: 2px;
        -webkit-border-radius: 5px;
        -moz-border-radius: 5px;
        border-radius: 5px;
        background-color: #EEEEEE;
    }
</style>
</#macro>

<#macro content>
<div>
    <div class="row-fluid">
        <div class="span10">
            <div class="page-header title">
                <h3>外展数据分析</h3>
            </div>
        </div>
    </div>
    <div>
        <form class="well well-small form-inline" action="orion/exhibition-stat"
              method="post">
            <label>时间</label>
            <input class="input-medium Wdate" id="beginTime" type="text" name="beginTime"
                   value="${beginTime?string('yyyy-MM-dd')}">
            <label>到</label>
            <input class="input-medium Wdate" id="endTime" type="text" name="endTime"
                   value="${endTime?string('yyyy-MM-dd')}">
            <input type="submit" class="btn" value="查询">
        </form>
    </div>
    <div id="exhibitionStatMap" style="width: 600px;height: 400px;"></div>
    <div class="tabtable">
        <#if (outEventList?size>0)>
            <div>
                <ul class="nav nav-pills">
                    <#list outEventList as outEvent>
                        <li class="<#if outEvent_index==0>active</#if> m-l-10 m-t-5">
                            <a href="#${outEvent.id}" data-toggle="tab">
                            ${outEvent.useFor}
                            </a>
                        </li>
                    </#list>
                </ul>
            </div>
            <hr>
            <div class="tab-content">
                <#list  outEventList as outEvent>
                    <#if outEvent_index ==0>
                        <#assign eventId=outEvent.id >
                    </#if>
                    <div id="${outEvent.id}" class="tab-pane <#if outEvent.id==eventId >active</#if>">
                        <table class="table table-center table-bordered">
                            <tr>
                                <td>申请人:${outEvent.applicant!}</td>
                                <td>提用目的:${outEvent.useFor}</td>
                                <td>类型:
                                    <#if outEvent.eventType==1>
                                        外出借展
                                    <#elseif outEvent.eventType=2 >
                                        科研修复
                                    </#if>
                                </td>
                            </tr>
                            <tr>
                                <td>是否出馆:
                                    <#if outEvent.outBound >
                                        是
                                    <#else >
                                        否
                                    </#if>
                                </td>
                                <td>开始时间:${outEvent.beginDate?string("yyyy-MM-dd")}</td>
                                <td>结束时间:${outEvent.endDate?string("yyyy-MM-dd")}</td>
                            </tr>
                        </table>
                        <div class="t-a-c">
                        ${outEvent.useFor!}出库统计
                        </div>
                        <button  class="outEventStatExport btn btn-primary pull-right">
                            导出
                        </button>
                        <table class="table table-bordered table-center">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>总登记号</th>
                                <th>名称</th>
                                <th>级别</th>
                                <th>质地</th>
                                <th>件数</th>
                                <th>库存状态</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list outEvent.eventRelics as eventRelic>
                                <tr>
                                    <td>${eventRelic_index+1}</td>
                                    <td>${eventRelic.relic.totalCode!}</td>
                                    <td>
                                        <a href="javascript:void(0)" class="relicName" data-toggle="popover"
                                           data-trigger="hover" data-html="true"
                                           data-content="<strong>共出库${eventRelic.relic.roves?size}次</strong><#list eventRelic.relic.roves as r><br><br><strong>${r_index+1}.</strong>${r.roveInfo}</#list>">${eventRelic.relic.name!}</a>
                                    </td>
                                    <td>${eventRelic.relic.level.name!}</td>
                                    <td>${eventRelic.relic.texture.name!}</td>
                                    <td>${eventRelic.relic.count!0}个</td>
                                    <td>${eventRelic.relic.stateName}</td>
                                </tr>
                                </#list>
                            </tbody>
                        </table>
                    </div>

                </#list>
            </div>
        <#else>
            <h1>暂无数据</h1>
        </#if>
    </div>
</div>
<input type="hidden" id="outEventNames" value="[
        <#list  outEventList as outEvent>
            <#if outEvent_has_next>
            ${outEvent.useFor},
            <#else>
            ${outEvent.useFor}
            </#if>
        </#list>
        ]"/>
</#macro>
<#macro script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/echarts/2.2.7/echarts-all.js"></script>
<script type="text/javascript">
    $(function () {
        $(".outEventStatExport.btn.btn-primary.pull-right").click(function () {
            var url =$("base").attr("href")+ "orion/exhibition-stat-export?beginTime=" + $('#beginTime').val() + "&endTime=" + $('#endTime').val();
            window.location.href = url;
        });


        $(".relicName").mouseover(function () {
            $(this).popover('show');
        }).mouseout(function () {
            $(this).popover('hide');
        });
        //时间选择器
        $("#beginTime").click(function () {
            WdatePicker({
                dateFmt: 'yyyy-MM-dd',
                maxDate: '#F{$dp.$D(\'endTime\')||\'%y-%M-{%d-1}\'}'
            });
        });

        //时间选择器
        $("#endTime").click(function () {
            WdatePicker({
                dateFmt: 'yyyy-MM-dd',
                maxDate: '%y-%M-{%d-1}',
                minDate: '#F{$dp.$D(\'beginTime\')}'
            });
        });

        /*世界地图国家集合*/
        var countrys = [
            "阿富汗",
            "安哥拉",
            "阿尔巴尼亚",
            "阿联酋",
            "阿根廷",
            "亚美尼亚",
            "法属南半球和南极领地",
            "澳大利亚",
            "奥地利",
            "阿塞拜疆",
            "布隆迪",
            "比利时",
            "贝宁",
            "布基纳法索",
            "孟加拉国",
            "保加利亚",
            "巴哈马",
            "波斯尼亚和黑塞哥维那",
            "白俄罗斯",
            "伯利兹",
            "百慕大",
            "玻利维亚",
            "巴西",
            "文莱",
            "不丹",
            "博茨瓦纳",
            "中非共和国",
            "加拿大",
            "瑞士",
            "智利",
            "中国",
            "象牙海岸",
            "喀麦隆",
            "刚果民主共和国",
            "刚果共和国",
            "哥伦比亚",
            "哥斯达黎加",
            "古巴",
            "北塞浦路斯",
            "塞浦路斯",
            "捷克共和国",
            "德国",
            "吉布提",
            "丹麦",
            "多明尼加共和国",
            "阿尔及利亚",
            "厄瓜多尔",
            "埃及",
            "厄立特里亚",
            "西班牙",
            "爱沙尼亚",
            "埃塞俄比亚",
            "芬兰",
            "斐",
            "福克兰群岛",
            "法国",
            "加蓬",
            "英国",
            "格鲁吉亚",
            "加纳",
            "几内亚",
            "冈比亚",
            "几内亚比绍",
            "赤道几内亚",
            "希腊",
            "格陵兰",
            "危地马拉",
            "法属圭亚那",
            "圭亚那",
            "洪都拉斯",
            "克罗地亚",
            "海地",
            "匈牙利",
            "印尼",
            "印度",
            "爱尔兰",
            "伊朗",
            "伊拉克",
            "冰岛",
            "以色列",
            "意大利",
            "牙买加",
            "约旦",
            "日本",
            "哈萨克斯坦",
            "肯尼亚",
            "吉尔吉斯斯坦",
            "柬埔寨",
            "韩国",
            "科索沃",
            "科威特",
            "老挝",
            "黎巴嫩",
            "利比里亚",
            "利比亚",
            "斯里兰卡",
            "莱索托",
            "立陶宛",
            "卢森堡",
            "拉脱维亚",
            "摩洛哥",
            "摩尔多瓦",
            "马达加斯加",
            "墨西哥",
            "马其顿",
            "马里",
            "缅甸",
            "黑山",
            "蒙古",
            "莫桑比克",
            "毛里塔尼亚",
            "马拉维",
            "马来西亚",
            "纳米比亚",
            "新喀里多尼亚",
            "尼日尔",
            "尼日利亚",
            "尼加拉瓜",
            "荷兰",
            "挪威",
            "尼泊尔",
            "新西兰",
            "阿曼",
            "巴基斯坦",
            "巴拿马",
            "秘鲁",
            "菲律宾",
            "巴布亚新几内亚",
            "波兰",
            "波多黎各",
            "北朝鲜",
            "葡萄牙",
            "巴拉圭",
            "卡塔尔",
            "罗马尼亚",
            "俄罗斯",
            "卢旺达",
            "西撒哈拉",
            "沙特阿拉伯",
            "苏丹",
            "南苏丹",
            "塞内加尔",
            "所罗门群岛",
            "塞拉利昂",
            "萨尔瓦多",
            "索马里兰",
            "索马里",
            "塞尔维亚共和国",
            "苏里南",
            "斯洛伐克",
            "斯洛文尼亚",
            "瑞典",
            "斯威士兰",
            "叙利亚",
            "乍得",
            "多哥",
            "泰国",
            "塔吉克斯坦",
            "土库曼斯坦",
            "东帝汶",
            "特里尼达和多巴哥",
            "突尼斯",
            "土耳其",
            "坦桑尼亚联合共和国",
            "乌干达",
            "乌克兰",
            "乌拉圭",
            "美国",
            "乌兹别克斯坦",
            "委内瑞拉",
            "越南",
            "瓦努阿图",
            "西岸",
            "也门",
            "南非",
            "赞比亚",
            "津巴布韦"];
        /*统计各个国家的参展次数顺序和countrys一致*/
        var count = new Array(countrys.length);
        for (var i = 0; i < count.length; i++) {
            count[i] = 0;
        }
        var $eventNames = $("#outEventNames").val();
        var eventNamesArray = $eventNames.split(",");
        /*遍历外展事件，统计各个国家的外展次数（通过包含国家名统计）*/
        for (var i = 0; i < eventNamesArray.length; i++) {
            for (var j = 0; j < countrys.length; j++) {
                if (eventNamesArray[i].indexOf(countrys[j]) != -1) {
                    count[j]++;
                }
            }
        }

        var data = [];
        /* 组装地图数据*/
        for (var i = 0; i < countrys.length; i++) {
            if (count[i] == 0) {
                continue;
            }
            data.push({name: countrys[i], value: count[i]});
        }
        /*初始化echarts对象*/
        var myChart = echarts.init(document.getElementById('exhibitionStatMap'));
        option = {
            title: {
                text: '参展分布',
                subtext: '',
                sublink: '',
                x: 'center'
            },
            toolbox: {
                show: true,
                orient: 'vertical',
                x: 'right',
                y: 'center',
                feature: {
                    dataView: {show: true, readOnly: false},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            series: [
                {
                    name: '',
                    type: 'map',
                    mapType: 'world',
                    hoverable: false,
                    roam: true,
                    data: [],
                    nameMap: {
                        'Afghanistan': '阿富汗',
                        'Angola': '安哥拉',
                        'Albania': '阿尔巴尼亚',
                        'United Arab Emirates': '阿联酋',
                        'Argentina': '阿根廷',
                        'Armenia': '亚美尼亚',
                        'French Southern and Antarctic Lands': '法属南半球和南极领地',
                        'Australia': '澳大利亚',
                        'Austria': '奥地利',
                        'Azerbaijan': '阿塞拜疆',
                        'Burundi': '布隆迪',
                        'Belgium': '比利时',
                        'Benin': '贝宁',
                        'Burkina Faso': '布基纳法索',
                        'Bangladesh': '孟加拉国',
                        'Bulgaria': '保加利亚',
                        'The Bahamas': '巴哈马',
                        'Bosnia and Herzegovina': '波斯尼亚和黑塞哥维那',
                        'Belarus': '白俄罗斯',
                        'Belize': '伯利兹',
                        'Bermuda': '百慕大',
                        'Bolivia': '玻利维亚',
                        'Brazil': '巴西',
                        'Brunei': '文莱',
                        'Bhutan': '不丹',
                        'Botswana': '博茨瓦纳',
                        'Central African Republic': '中非共和国',
                        'Canada': '加拿大',
                        'Switzerland': '瑞士',
                        'Chile': '智利',
                        'China': '中国',
                        'Ivory Coast': '象牙海岸',
                        'Cameroon': '喀麦隆',
                        'Democratic Republic of the Congo': '刚果民主共和国',
                        'Republic of the Congo': '刚果共和国',
                        'Colombia': '哥伦比亚',
                        'Costa Rica': '哥斯达黎加',
                        'Cuba': '古巴',
                        'Northern Cyprus': '北塞浦路斯',
                        'Cyprus': '塞浦路斯',
                        'Czech Republic': '捷克共和国',
                        'Germany': '德国',
                        'Djibouti': '吉布提',
                        'Denmark': '丹麦',
                        'Dominican Republic': '多明尼加共和国',
                        'Algeria': '阿尔及利亚',
                        'Ecuador': '厄瓜多尔',
                        'Egypt': '埃及',
                        'Eritrea': '厄立特里亚',
                        'Spain': '西班牙',
                        'Estonia': '爱沙尼亚',
                        'Ethiopia': '埃塞俄比亚',
                        'Finland': '芬兰',
                        'Fiji': '斐',
                        'Falkland Islands': '福克兰群岛',
                        'France': '法国',
                        'Gabon': '加蓬',
                        'United Kingdom': '英国',
                        'Georgia': '格鲁吉亚',
                        'Ghana': '加纳',
                        'Guinea': '几内亚',
                        'Gambia': '冈比亚',
                        'Guinea Bissau': '几内亚比绍',
                        'Equatorial Guinea': '赤道几内亚',
                        'Greece': '希腊',
                        'Greenland': '格陵兰',
                        'Guatemala': '危地马拉',
                        'French Guiana': '法属圭亚那',
                        'Guyana': '圭亚那',
                        'Honduras': '洪都拉斯',
                        'Croatia': '克罗地亚',
                        'Haiti': '海地',
                        'Hungary': '匈牙利',
                        'Indonesia': '印尼',
                        'India': '印度',
                        'Ireland': '爱尔兰',
                        'Iran': '伊朗',
                        'Iraq': '伊拉克',
                        'Iceland': '冰岛',
                        'Israel': '以色列',
                        'Italy': '意大利',
                        'Jamaica': '牙买加',
                        'Jordan': '约旦',
                        'Japan': '日本',
                        'Kazakhstan': '哈萨克斯坦',
                        'Kenya': '肯尼亚',
                        'Kyrgyzstan': '吉尔吉斯斯坦',
                        'Cambodia': '柬埔寨',
                        'South Korea': '韩国',
                        'Kosovo': '科索沃',
                        'Kuwait': '科威特',
                        'Laos': '老挝',
                        'Lebanon': '黎巴嫩',
                        'Liberia': '利比里亚',
                        'Libya': '利比亚',
                        'Sri Lanka': '斯里兰卡',
                        'Lesotho': '莱索托',
                        'Lithuania': '立陶宛',
                        'Luxembourg': '卢森堡',
                        'Latvia': '拉脱维亚',
                        'Morocco': '摩洛哥',
                        'Moldova': '摩尔多瓦',
                        'Madagascar': '马达加斯加',
                        'Mexico': '墨西哥',
                        'Macedonia': '马其顿',
                        'Mali': '马里',
                        'Myanmar': '缅甸',
                        'Montenegro': '黑山',
                        'Mongolia': '蒙古',
                        'Mozambique': '莫桑比克',
                        'Mauritania': '毛里塔尼亚',
                        'Malawi': '马拉维',
                        'Malaysia': '马来西亚',
                        'Namibia': '纳米比亚',
                        'New Caledonia': '新喀里多尼亚',
                        'Niger': '尼日尔',
                        'Nigeria': '尼日利亚',
                        'Nicaragua': '尼加拉瓜',
                        'Netherlands': '荷兰',
                        'Norway': '挪威',
                        'Nepal': '尼泊尔',
                        'New Zealand': '新西兰',
                        'Oman': '阿曼',
                        'Pakistan': '巴基斯坦',
                        'Panama': '巴拿马',
                        'Peru': '秘鲁',
                        'Philippines': '菲律宾',
                        'Papua New Guinea': '巴布亚新几内亚',
                        'Poland': '波兰',
                        'Puerto Rico': '波多黎各',
                        'North Korea': '北朝鲜',
                        'Portugal': '葡萄牙',
                        'Paraguay': '巴拉圭',
                        'Qatar': '卡塔尔',
                        'Romania': '罗马尼亚',
                        'Russia': '俄罗斯',
                        'Rwanda': '卢旺达',
                        'Western Sahara': '西撒哈拉',
                        'Saudi Arabia': '沙特阿拉伯',
                        'Sudan': '苏丹',
                        'South Sudan': '南苏丹',
                        'Senegal': '塞内加尔',
                        'Solomon Islands': '所罗门群岛',
                        'Sierra Leone': '塞拉利昂',
                        'El Salvador': '萨尔瓦多',
                        'Somaliland': '索马里兰',
                        'Somalia': '索马里',
                        'Republic of Serbia': '塞尔维亚共和国',
                        'Suriname': '苏里南',
                        'Slovakia': '斯洛伐克',
                        'Slovenia': '斯洛文尼亚',
                        'Sweden': '瑞典',
                        'Swaziland': '斯威士兰',
                        'Syria': '叙利亚',
                        'Chad': '乍得',
                        'Togo': '多哥',
                        'Thailand': '泰国',
                        'Tajikistan': '塔吉克斯坦',
                        'Turkmenistan': '土库曼斯坦',
                        'East Timor': '东帝汶',
                        'Trinidad and Tobago': '特里尼达和多巴哥',
                        'Tunisia': '突尼斯',
                        'Turkey': '土耳其',
                        'United Republic of Tanzania': '坦桑尼亚联合共和国',
                        'Uganda': '乌干达',
                        'Ukraine': '乌克兰',
                        'Uruguay': '乌拉圭',
                        'United States of America': '美国',
                        'Uzbekistan': '乌兹别克斯坦',
                        'Venezuela': '委内瑞拉',
                        'Vietnam': '越南',
                        'Vanuatu': '瓦努阿图',
                        'West Bank': '西岸',
                        'Yemen': '也门',
                        'South Africa': '南非',
                        'Zambia': '赞比亚',
                        'Zimbabwe': '津巴布韦'
                    },
                    markPoint: {
                        symbolSize: 0,       // 标注大小，半宽（半径）参数，当图形为方向或菱形则总宽度为symbolSize * 2
                        itemStyle: {
                            normal: {
                                label: {
                                    show: true,
                                    formatter: function (b) {
                                        return b.name + " " + b.value + "次";
                                    },
                                    textStyle: {fontSize: 10, fontWeight: 'bold', color: '#080808'}
                                }
                            },
                            emphasis: {
                                borderWidth: 0,
                                label: {
                                    show: false
                                }
                            }
                        },
                        data: data
                    },
                    geoCoord: {
                        '阿富汗': [67.709953, 33.93911],
                        '安哥拉': [17.873887, -11.202692],
                        '阿尔巴尼亚': [20.168331, 41.153332],
                        '阿联酋': [53.847818, 23.424076],
                        '阿根廷': [-63.61667199999999, -38.416097],
                        '亚美尼亚': [45.038189, 40.069099],
                        '法属南半球和南极领地': [69.348557, -49.280366],
                        '澳大利亚': [133.775136, -25.274398],
                        '奥地利': [14.550072, 47.516231],
                        '阿塞拜疆': [47.576927, 40.143105],
                        '布隆迪': [29.918886, -3.373056],
                        '比利时': [4.469936, 50.503887],
                        '贝宁': [2.315834, 9.30769],
                        '布基纳法索': [-1.561593, 12.238333],
                        '孟加拉国': [90.356331, 23.684994],
                        '保加利亚': [25.48583, 42.733883],
                        '巴哈马': [-77.39627999999999, 25.03428],
                        '波斯尼亚和黑塞哥维那': [17.679076, 43.915886],
                        '白俄罗斯': [27.953389, 53.709807],
                        '伯利兹': [-88.49765, 17.189877],
                        '百慕大': [-64.7505, 32.3078],
                        '玻利维亚': [-63.58865299999999, -16.290154],
                        '巴西': [-51.92528, -14.235004],
                        '文莱': [114.727669, 4.535277],
                        '不丹': [90.433601, 27.514162],
                        '博茨瓦纳': [24.684866, -22.328474],
                        '中非共和国': [20.939444, 6.611110999999999],
                        '加拿大': [-106.346771, 56.130366],
                        '瑞士': [8.227511999999999, 46.818188],
                        '智利': [-71.542969, -35.675147],
                        '中国': [104.195397, 35.86166],
                        '象牙海岸': [-5.547079999999999, 7.539988999999999],
                        '喀麦隆': [12.354722, 7.369721999999999],
                        '刚果民主共和国': [21.758664, -4.038333],
                        '刚果共和国': [15.827659, -0.228021],
                        '哥伦比亚': [-74.297333, 4.570868],
                        '哥斯达黎加': [-83.753428, 9.748916999999999],
                        '古巴': [-77.781167, 21.521757],
                        '北塞浦路斯': [33.429859, 35.126413],
                        '塞浦路斯': [33.429859, 35.126413],
                        '捷克共和国': [15.472962, 49.81749199999999],
                        '德国': [10.451526, 51.165691],
                        '吉布提': [42.590275, 11.825138],
                        '丹麦': [9.501785, 56.26392],
                        '多明尼加共和国': [-70.162651, 18.735693],
                        '阿尔及利亚': [1.659626, 28.033886],
                        '厄瓜多尔': [-78.18340599999999, -1.831239],
                        '埃及': [30.802498, 26.820553],
                        '厄立特里亚': [39.782334, 15.179384],
                        '西班牙': [-3.74922, 40.46366700000001],
                        '爱沙尼亚': [25.013607, 58.595272],
                        '埃塞俄比亚': [40.489673, 9.145000000000001],
                        '芬兰': [25.748151, 61.92410999999999],
                        '斐': [178.065032, -17.713371],
                        '福克兰群岛': [-59.523613, -51.796253],
                        '法国': [2.213749, 46.227638],
                        '加蓬': [11.609444, -0.803689],
                        '英国': [-3.435973, 55.378051],
                        '格鲁吉亚': [-82.9000751, 32.1656221],
                        '加纳': [-1.023194, 7.946527],
                        '几内亚': [-9.696645, 9.945587],
                        '冈比亚': [-15.310139, 13.443182],
                        '几内亚比绍': [-15.180413, 11.803749],
                        '赤道几内亚': [10.267895, 1.650801],
                        '希腊': [21.824312, 39.074208],
                        '格陵兰': [-42.604303, 71.706936],
                        '危地马拉': [-90.23075899999999, 15.783471],
                        '法属圭亚那': [-53.125782, 3.933889],
                        '圭亚那': [-58.93018, 4.860416],
                        '洪都拉斯': [-86.241905, 15.199999],
                        '克罗地亚': [15.2, 45.1],
                        '海地': [-72.285215, 18.971187],
                        '匈牙利': [19.503304, 47.162494],
                        '印尼': [113.921327, -0.789275],
                        '印度': [78.96288, 20.593684],
                        '爱尔兰': [-8.24389, 53.41291],
                        '伊朗': [53.688046, 32.427908],
                        '伊拉克': [43.679291, 33.223191],
                        '冰岛': [-19.020835, 64.963051],
                        '以色列': [34.851612, 31.046051],
                        '意大利': [12.56738, 41.87194],
                        '牙买加': [-77.297508, 18.109581],
                        '约旦': [36.238414, 30.585164],
                        '日本': [138.252924, 36.204824],
                        '哈萨克斯坦': [66.923684, 48.019573],
                        '肯尼亚': [37.906193, -0.023559],
                        '吉尔吉斯斯坦': [74.766098, 41.20438],
                        '柬埔寨': [104.990963, 12.565679],
                        '韩国': [127.766922, 35.907757],
                        '科索沃': [20.902977, 42.6026359],
                        '科威特': [47.481766, 29.31166],
                        '老挝': [102.495496, 19.85627],
                        '黎巴嫩': [35.862285, 33.854721],
                        '利比里亚': [-9.429499000000002, 6.428055],
                        '利比亚': [17.228331, 26.3351],
                        '斯里兰卡': [80.77179699999999, 7.873053999999999],
                        '莱索托': [28.233608, -29.609988],
                        '立陶宛': [23.881275, 55.169438],
                        '卢森堡': [6.129582999999999, 49.815273],
                        '拉脱维亚': [24.603189, 56.879635],
                        '摩洛哥': [-7.092619999999999, 31.791702],
                        '摩尔多瓦': [28.369885, 47.411631],
                        '马达加斯加': [46.869107, -18.766947],
                        '墨西哥': [-102.552784, 23.634501],
                        '马其顿': [21.745275, 41.608635],
                        '马里': [-3.996166, 17.570692],
                        '缅甸': [95.956223, 21.913965],
                        '黑山': [19.37439, 42.708678],
                        '蒙古': [103.846656, 46.862496],
                        '莫桑比克': [35.529562, -18.665695],
                        '毛里塔尼亚': [-10.940835, 21.00789],
                        '马拉维': [34.301525, -13.254308],
                        '马来西亚': [101.975766, 4.210484],
                        '纳米比亚': [18.49041, -22.95764],
                        '新喀里多尼亚': [165.618042, -20.904305],
                        '尼日尔': [8.081666, 17.607789],
                        '尼日利亚': [8.675277, 9.081999],
                        '尼加拉瓜': [-85.207229, 12.865416],
                        '荷兰': [5.291265999999999, 52.132633],
                        '挪威': [8.468945999999999, 60.47202399999999],
                        '尼泊尔': [84.12400799999999, 28.394857],
                        '新西兰': [174.885971, -40.900557],
                        '阿曼': [55.923255, 21.512583],
                        '巴基斯坦': [69.34511599999999, 30.375321],
                        '巴拿马': [-80.782127, 8.537981],
                        '秘鲁': [-75.015152, -9.189967],
                        '菲律宾': [121.774017, 12.879721],
                        '巴布亚新几内亚': [143.95555, -6.314992999999999],
                        '波兰': [19.145136, 51.919438],
                        '波多黎各': [-66.590149, 18.220833],
                        '北朝鲜': [127.510093, 40.339852],
                        '葡萄牙': [-8.224454, 39.39987199999999],
                        '巴拉圭': [-58.443832, -23.442503],
                        '卡塔尔': [51.183884, 25.354826],
                        '罗马尼亚': [24.96676, 45.943161],
                        '俄罗斯': [105.318756, 61.52401],
                        '卢旺达': [29.873888, -1.940278],
                        '西撒哈拉': [-12.885834, 24.215527],
                        '沙特阿拉伯': [45.079162, 23.885942],
                        '苏丹': [30.217636, 12.862807],
                        '南苏丹': [31.3069788, 6.876991899999999],
                        '塞内加尔': [-14.452362, 14.497401],
                        '所罗门群岛': [160.156194, -9.64571],
                        '塞拉利昂': [-11.779889, 8.460555],
                        '萨尔瓦多': [-88.89653, 13.794185],
                        '索马里兰': [46.8252838, 9.411743399999999],
                        '索马里': [46.199616, 5.152149],
                        '塞尔维亚共和国': [21.005859, 44.016521],
                        '苏里南': [-56.027783, 3.919305],
                        '斯洛伐克': [19.699024, 48.669026],
                        '斯洛文尼亚': [14.995463, 46.151241],
                        '瑞典': [18.643501, 60.12816100000001],
                        '斯威士兰': [31.465866, -26.522503],
                        '叙利亚': [38.996815, 34.80207499999999],
                        '乍得': [18.732207, 15.454166],
                        '多哥': [0.824782, 8.619543],
                        '泰国': [100.992541, 15.870032],
                        '塔吉克斯坦': [71.276093, 38.861034],
                        '土库曼斯坦': [59.556278, 38.969719],
                        '东帝汶': [125.727539, -8.874217],
                        '特里尼达和多巴哥': [-61.222503, 10.691803],
                        '突尼斯': [9.537499, 33.886917],
                        '土耳其': [35.243322, 38.963745],
                        '坦桑尼亚联合共和国': [34.888822, -6.369028],
                        '乌干达': [32.290275, 1.373333],
                        '乌克兰': [31.16558, 48.379433],
                        '乌拉圭': [-55.765835, -32.522779],
                        '美国': [-95.712891, 37.09024],
                        '乌兹别克斯坦': [64.585262, 41.377491],
                        '委内瑞拉': [-66.58973, 6.42375],
                        '越南': [108.277199, 14.058324],
                        '瓦努阿图': [166.959158, -15.376706],
                        '西岸': [35.3027226, 31.9465703],
                        '也门': [48.516388, 15.552727],
                        '南非': [22.937506, -30.559482],
                        '赞比亚': [27.849332, -13.133897],
                        '津巴布韦': [29.154857, -19.015438]
                    }
                },
                {
                    name: '参展次数',
                    type: 'map',
                    mapType: 'world',
                    data: data
                }
            ]
        };
        myChart.setOption(option);
    });
</script>
</#macro>