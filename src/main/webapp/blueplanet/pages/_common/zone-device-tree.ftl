<#--
区域-设备 树

@author gaohui
@date 2013-01-21
-->

<div class="left-aside-container">
    <div>
        <input id="device-tree-filter" class="m-l-5 m-b-10 input-medium search-query"
               type="text" title="搜索区域、位置点、设备" placeholder="搜索区域、位置点、设备" style="width:160px; margin-bottom: 10px;">

        <div>
            <div class="f-l">
                <select id="zone-device-tree-sensorinfo-select"
                        class="m-l-10 p-0 input-small"
                        title="过滤位置点/设备"
                        style="height:100%; line-height: 100%; border-radius: 0;">
                    <option value="-1"></option>
                </select>
            </div>

            <div class="f-l m-l-10">
                <a id="zone-device-tree-collapse-all" class="gray-a" href="#" title="全部收起">收起</a>
                <a id="zone-device-tree-expand-all" class="gray-a" href="#" title="全部展开">展开</i></a>
            </div>
        </div>
    </div>

    <div id="zone-device-tree" class="ztree"></div>
</div>