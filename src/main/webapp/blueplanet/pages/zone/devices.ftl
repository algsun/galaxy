<#assign title>${locale.getStr("blueplanet.devices.title")}</#assign>

<#macro head>
<style type="text/css">
    .device {
        border-bottom: 1px solid #ddd;
    }
</style>
</#macro>


<#assign tabIndex = 4>
<#macro content>
<div class="form-inline m-b-40">
    <label>绑定设备</label>
    <input type="text">
    <button class="btn">绑定</button>
</div>

<div class="row-fluid">
    <div class="span6">
        <ul class="m-l-0 m-b-0" style="border-top:1px solid #ddd; list-style-type: none;">
            <li class="p-v-10 device">
                <span><a href="device-index.action">设备1</a></span>
                <button class="btn btn-mini btn-danger f-r">解除绑定</button>
            </li>
            <li class="p-v-10 device"><a href="device-index.action">设备2</a>
                <button class="btn btn-mini btn-danger f-r">解除绑定</button>
            </li>
            <li class="p-v-10 device"><a href="device-index.action">设备3</a>
                <button class="btn btn-mini btn-danger f-r">解除绑定</button>
            </li>
        </ul>
    </div>
</div>
</#macro>


<#macro script>

</#macro>