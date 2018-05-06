<#--
  <pre>
   站点信息显示
  </pre>
  @author Wang yunlong
  @time  12-12-3  下午4:35
  -->
<div class="row">
    <div class="span12 p-l-50">
        <div class="span12 m-t-20">
            <label class="m-l-50 span1 label-info1">站点名称</label>
            <label class="info span10">${logicGroup.logicGroupName!}</label>
        </div>
        <div class="span12 m-t-20">
            <label class="m-l-50 span1 label-info1">机构代码</label>
            <label class="span10 <#if logicGroup.orgCode??>info</#if>"><#if logicGroup.orgCode??>${logicGroup.orgCode}<#else>
                暂无</#if></label>
        </div>
        <div class="span12 m-t-20">
            <label class="m-l-50 span1 label-info1">地址</label>
            <label class="span10 <#if logicGroup.orgAddress??>info</#if>"><#if logicGroup.orgAddress??>${logicGroup.orgAddress}<#else>
                暂无</#if></label>
        </div>
        <div class="span12 m-t-20">
            <label class="m-l-50 span1 label-info1">邮编</label>
            <label class="span10 <#if logicGroup.orgZipcode??>info</#if>"><#if logicGroup.orgZipcode??>${logicGroup.orgZipcode}<#else>
                暂无</#if></label>
        </div>
        <div class="span12 m-t-20">
            <label class="m-l-50 span1 label-info1">网址</label>
            <label class="span10 <#if logicGroup.orgWebsite??>info</#if>"><#if logicGroup.orgWebsite??>${logicGroup.orgWebsite}<#else>
                暂无</#if></label>
        </div>
        <div class="span12 m-t-20">
            <label class="m-l-50 span1 label-info1">联系电话</label>
            <label class="span10 <#if logicGroup.orgTel??>info</#if>"><#if logicGroup.orgTel??>${logicGroup.orgTel}<#else>
                暂无</#if></label>
        </div>
        <div class="span12 m-t-20">
            <label class="m-l-50 span1 label-info1">传真</label>
            <label class="span10 <#if logicGroup.orgFax??>info</#if>"><#if logicGroup.orgFax??>${logicGroup.orgFax}<#else>
                暂无</#if></label>
        </div>
    </div>
</div>
