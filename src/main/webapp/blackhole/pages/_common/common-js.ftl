<#--
公共 js 资源, 基本上所有页面都会引用的 js

@author gaohui
@date 2012-11-22
-->
<#include "/common/pages/common-tag.ftl">

<script type="text/javascript" src="../assets/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript" src="../assets/bootstrap/2.3.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="../assets/bootstrap-hover-dropdown/0bbbb25790/bootstrap-hover-dropdown.min.js"></script>
<script type="text/javascript" src="../assets/ztree/3.5.12/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="../assets/ztree/3.5.12/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.4"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/TextIconOverlay/1.2/src/TextIconOverlay_min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/MarkerClusterer/1.2/src/MarkerClusterer_min.js"></script>
<#-- artDialog(页面头部站点切换) -->
<script type="text/javascript" src="../assets/artDialog/5.0.1-7012746/artDialog.min.js"></script>

<#-- 页面底部二维码 -->
<script type="text/javascript" src="../assets/jquery-qrcode/0.7.0/jquery.qrcode.min.js"></script>

<@scriptTag "../common/js/app.js"/>
<@scriptTag "../common/js/map-switch.js"/>
<@scriptTag "js/header.js"/>
<@scriptTag "../common/js/footer.js"/>

