<#--
区域-设备 树

@author gaohui
@date 2013-01-21
-->

<div class="left-aside-container">
    <div>
        <input id="device-tree-filter" class="input-sm m-l-10 m-b-10"
               type="text" title="搜索区域、位置点、设备" placeholder="搜索区域、位置点、设备" style="width:160px;">

        <div class="f-l">
            <select id="zone-device-tree-sensorinfo-select"
                    class="m-l-10 p-0 input-sm"
                    title="过滤位置点/设备"
                    style="height:100%; line-height: 100%; width: 50%; border-radius: 0;">
                <option value="-1"></option>
            </select>
            <a id="zone-device-tree-collapse-all" class="gray-a" href="#" title="全部收起">收起</a>
            <a id="zone-device-tree-expand-all" class="gray-a" href="#" title="全部展开">展开</i></a>
        </div>
    </div>

    <div id="zone-device-tree" class="ztree"></div>
</div>