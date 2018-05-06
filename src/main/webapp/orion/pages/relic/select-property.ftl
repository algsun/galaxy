<#--
    客户选择要导出的属性
    @author liuzhu
    @date  2013-07-29
    @cehck @gaohui #5058 2013-08-15
-->
<style type="text/css">
    #selectProperty .checkbox.inline{
        margin-left: 10px;
    }
</style>
<div id="dialog">
    <div id="myModal" class="modal hide fade" tabindex="-1" style="width: 950px;margin-left: -450px;margin-top: 100px;" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3 id="myModalLabel">请筛选藏品属性</h3>
        </div>
        <div class="modal-body">
            <div id="selectProperty">
                <div class="" style="width: 930px;">
                    <div class="f-l" style="width: 790px">
                        <div>
                            <label class="checkbox inline "><input type="checkbox" name="property" id="name" value="name"> 文物名称 </label>
                            <label class="checkbox inline "><input type="checkbox" name="property" id="totalCode" value="totalCode"> 总登记号 </label>
                            <label class="checkbox inline "><input type="checkbox" name="property" id="catalogCode" value="catalogCode"> 编目号 </label>
                            <label class="checkbox inline "><input type="checkbox" name="property" id="typeCode" value="typeCode"> 分类号 </label>
                            <label class="checkbox inline "><input type="checkbox" name="property" id="zone" value="zone"> 库房位次 </label>
                            <label class="checkbox inline "><input type="checkbox" name="property" id="era" value="era"> 时代 </label>
                            <label class="checkbox inline "><input type="checkbox" name="property" id="texture" value="texture"> 质地 </label>
                            <label class="checkbox inline "><input type="checkbox" name="property" id="state" value="state"> 状态 </label>
                            <label class="checkbox inline "><input type="checkbox" name="property" id="count" value="count"> 件数 </label>
                            <label class="checkbox inline "><input type="checkbox" name="property" id="level" value="level"> 级别 </label>
                            <label class="checkbox inline "><input type="checkbox" name="property" id="currentSituation" value="currentSituation"> 现状 </label>
                        </div>
                        <div id="queryPro" style="width: 790px;display: none; ">
                            <#list proList as pro>
                                <#if pro.cnName!="附件">
                                    <label class="checkbox inline"><input type="checkbox"  name="property2" id="${pro.enName}" value="${pro.enName}">${pro.cnName}</label>
                                </#if>
                            </#list>
                            <div class="clear"></div>
                        </div>
                    </div>
                    <div class="f-r" style="width: 130px;height: auto;" >
                        <label class="checkbox inline"><input type="checkbox" id="all"><span style="font-weight: bold">全选</span></label>
                        <span id="unfold" class="checkbox inline" style="cursor: pointer;font-weight: bold"></span>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="modal-footer m-t-20">
                    <button class="btn" onclick="javascrpt:$('#myModal').modal('hide');">关闭</button>
                    <button class="btn btn-primary" id="property-submit">导出</button>
                </div>
             </div>
        </div>
    </div>
</div>

