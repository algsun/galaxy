<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>专家点评详情 - 资产管理</title>
<#include "../_common/common-css.ftl">
<#include "/common/pages/common-tag.ftl">
    <style type="text/css">

        /* table */
        td {
            width: 40px;
            height: 40px;
            text-align: center;
            vertical-align: middle;
        }

        table {
            width: 950px;
            margin: auto;
        }


        .careful {
            width: 30px;
            margin: 0 auto;
            font-size: 30%;
            letter-spacing: 0;
        }

        .none-border {
            border: none;
        }
    </style>
</head>
<body>
<#--页面以及标题-->
<#include "../_common/header.ftl">
<div id="gcontent" class="container" style="margin-top: 50px">
<#if repairRecord?? && institution??>
    <#if textureEnName == "paper" || textureEnName == "metal" || textureEnName == "ceramicPain">
        <#include "../archivesComplete/base-info-typea-browse.ftl">
    <#elseif textureEnName == "painting">
        <#include "../archivesComplete/base-info-typeb-browse.ftl">
    <#elseif textureEnName == "spinning" || textureEnName == "fabrics">
        <#include "../archivesComplete/base-info-typec-browse.ftl">
    <#elseif textureEnName == "mural">
        <#include "../archivesComplete/base-info-typed-browse.ftl">
    </#if>

<table border="1px" style="border:2px #000 solid;">
    <caption align="top"><h3>方案设计及保护修复单位信息记录表</h3></caption>
    <tr>
        <td colspan="3">方案名称和编号</td>
        <td colspan="17">${repairRecord.scheme.name!}(${repairRecord.scheme.schemeId!})</td>
    </tr>
    <tr>
        <td colspan="3">批准单位及时间</td>
        <td colspan="10">${repairRecord.scheme.confirmTime?string("yyyy-MM-dd")}</td>
        <td colspan="2">批准文号</td>
        <td colspan="5">${repairRecord.scheme.confirmNum!}</td>
    </tr>
    <tr>
        <td colspan="3" rowspan="4">方案设计单位</td>
        <td colspan="3">名称</td>
        <td colspan="14">${repairRecord.scheme.institution.name!}</td>
    </tr>
    <tr>
        <td colspan="3">单位所在地</td>
        <td colspan="14">${repairRecord.scheme.institution.seat!}</td>
    </tr>
    <tr>
        <td colspan="3">通讯地址</td>
        <td colspan="7">${repairRecord.scheme.institution.mailing!}</td>
        <td colspan="2">邮编</td>
        <td colspan="5">${repairRecord.scheme.institution.zipcode!}</td>
    </tr>
    <tr>
        <td colspan="3">资质证书</td>
        <td colspan="7">${repairRecord.scheme.institution.qualification!}</td>
        <td colspan="2">代码</td>
        <td colspan="5">${repairRecord.scheme.institution.code!}</td>
    </tr>
    <tr>
        <td colspan="3" rowspan="6">保护修复单位</td>
        <td colspan="3">名称</td>
        <td colspan="14">${repairRecord.institution.name!}</td>
    </tr>
    <tr>
        <td colspan="3">单位所在地</td>
        <td colspan="14">${repairRecord.institution.seat!}</td>
    </tr>
    <tr>
        <td colspan="3">通讯地址</td>
        <td colspan="7">${repairRecord.institution.mailing!}</td>
        <td colspan="2">邮编</td>
        <td colspan="5">${repairRecord.institution.zipcode!}</td>
    </tr>
    <tr>
        <td colspan="3">资质证书</td>
        <td colspan="7">${repairRecord.institution.qualification!}</td>
        <td colspan="2">代码</td>
        <td colspan="5">${repairRecord.institution.code!}</td>
    </tr>
    <tr>
        <td colspan="3">提取日期</td>
        <td colspan="7">${repairRecord.extractDate?string("yyyy-MM-dd")}</td>
        <td colspan="2">提取经办人</td>
        <td colspan="5"></td>
    </tr>
    <tr>
        <td colspan="3">返还日期</td>
        <td colspan="7">${repairRecord.returnDate?string("yyyy-MM-dd")}</td>
        <td colspan="2">返还经办人</td>
        <td colspan="5"></td>
    </tr>
</table>

<table border="1px" style="border:2px #000 solid;">
    <caption align="top"><h3>文物保存现状表</h3></caption>
    <tr style="height:50px">
        <td colspan="4">文物保存环境</td>
        <td colspan="16">${statusQuo.conserve!}</td>
    </tr>
    <tr style="height:50px">
        <td colspan="4">历次保护修复情况</td>
        <td colspan="16">${statusQuo.repairCases!}</td>
    </tr>
    <tr style="height:400px" id="allTableStatusQuoPhotos">
        <td colspan="4">现状及病害描述</td>
        <td colspan="16">${statusQuo.quoInfo!}</td>
    </tr>
    <tr style="height:80px">
        <td colspan="4">备注</td>
        <td colspan="16">${statusQuo.remark!}</td>
    </tr>
</table>

<table border="1px" style="border:2px #000 solid;">
    <caption align="top"><h3>文物检测分析表</h3></caption>
    <tr>
        <td colspan="1">编号</td>
        <td colspan="2">样品名称</td>
        <td colspan="4">样品描述</td>
        <td colspan="2">分析目的</td>
        <td colspan="3">分析方法</td>
        <td colspan="2">分析结果</td>
        <td colspan="2">报告代码</td>
        <td colspan="2">分析时间</td>
        <td colspan="2">备注</td>
    </tr>
    <#if detectionAnalysises??>
        <#list detectionAnalysises as detectionAnalysise>
            <tr>
                <td colspan="1">${detectionAnalysise.analysisNum!}</td>
                <td colspan="2">${detectionAnalysise.sampleName!}</td>
                <td colspan="4">${detectionAnalysise.sampleDescription!}</td>
                <td colspan="2">${detectionAnalysise.analysisPurpose!}</td>
                <td colspan="3">${detectionAnalysise.analysisMethod!}</td>
                <td colspan="2">${detectionAnalysise.analysisResult!}</td>
                <td colspan="2">${detectionAnalysise.reportCode!}</td>
                <td colspan="2">${detectionAnalysise.analysisTime?string("yyyy-MM-dd")}</td>
                <td colspan="2">${detectionAnalysise.remark!}</td>
            </tr>
        </#list>
    </#if>
</table>

<table border="1px" style="border:2px #000 solid;">
    <caption align="top"><h3>文物保护修复过程记录表</h3></caption>
    <tr style="height: 400px">
        <td colspan="20"><#if repairRecord.situation??>${repairRecord.situation.summarize!}</#if></td>
    </tr>
    <tr>
        <td colspan="4">技术变更</td>
        <td colspan="16"><#if repairRecord.situation??>${repairRecord.situation.techChange!}</#if></td>
    </tr>
    <tr>
        <td colspan="4">项目负责人</td>
        <td colspan="6">${repairRecord.mainUser.userName}</td>
        <td colspan="4">修复协助人</td>
        <td colspan="6">
            <#if repairRecord.secondaryUserName??>
                    <#list repairRecord.secondaryUserName as userName>
            ${userName}
            </#list>
            </#if>
        </td>
    </tr>
    <tr>
        <td colspan="4">审核日期</td>
        <td colspan="6">${repairRecord.checkDate}</td>
        <td colspan="4">审核人</td>
        <td colspan="6">${repairRecord.checkUser.userName}</td>
    </tr>
    <#if repairLogs??>
        <#list repairLogs as repairLog>
            <tr>
                <td colspan="4">保护修复人</td>
                <td colspan="6">
                    <#list repairLog.repairPersonName as repairPersonName>
                        ${repairPersonName!}
                    </#list>
                </td>
                <td colspan="4">日期</td>
                <td colspan="6">${repairLog.stamp?string("yyyy-MM-dd")}</td>
            </tr>
            <tr>
                <td colspan="4">日志</td>
                <td colspan="16">${repairLog.log!}</td>
            </tr>
        </#list>
    </#if>
    <#if repairPhotos??>
        <#list repairPhotos as repairPhoto>
            <tr>
                <td colspan="20">
                    <img src="${picturesBasePath!}/${siteId!}/${repairRecord.relic.id!}/${repairPhoto.path}"
                         style="max-width: 400px;max-height: 400px; text-align: center"/><br/>
                    <span>${repairPhoto.description!}</span>
                </td>
            </tr>
        </#list>
    </#if>
</table>

<table border="1px">
    <caption align="top"><h3>绘图登记表</h3></caption>
    <tr>
        <td>编号</td>
        <td>图纸类型</td>
        <td>简单描述</td>
        <td>绘图人</td>
        <td>时间</td>
        <td>备注</td>
        <td>文件名</td>
    </tr>
    <#if drawRegisterList??>
        <#list drawRegisterList as d>
            <tr>
                <td>${d_index+1}</td>
                <td>${d.drawingType}</td>
                <td>${d.description}</td>
                <td>${d.drawingPerson.userName}</td>
                <td>${d.stamp?string('yyyy-MM-dd')}</td>
                <td>${d.remark}</td>
                <td>
                    <#if d.imgPath??>
                    ${d.imgPath}
                    <#else>
                        无资源
                    </#if>
                </td>
            </tr>
        </#list>
    </#if>
</table>

<table border="1px" style="border:2px #000 solid;">
    <caption align="top"><h3>影像资料登记表</h3></caption>
    <tr>
        <td>编号</td>
        <td>提交人</td>
        <td>内容表述</td>
        <td>介质</td>
        <td>时长<span class="careful">(min)</span></td>
        <td>采集时间</td>
        <td>文件</td>
    </tr>
    <#if imageDatums??>
        <#list imageDatums as i>
            <tr>
                <td>${i_index+1}</td>
                <td>${i.submitPerson.userName}</td>
                <td>${i.content}</td>
                <td>${i.media}</td>
                <td>${i.duration?c}</td>
                <td>${i.stamp?string('yyyy-MM-dd')}</td>
                <td>
                    <#if i.filePath??>
                    ${i.filePath}
                    <#else>
                        无资源
                    </#if>
                </td>
            </tr>
        </#list>
    </#if>
</table>

    <#if repairAssessment??>
    <table border="1px" style="border:2px #000 solid;">
        <caption align="top"><h3>文物保护修复自评估与验收表</h3></caption>
        <input name="repairAssessment.repairRecord.id" value="${repairRecordId}" type="hidden"/>
        <tr style="height: 300px">
            <td colspan="20" class="none-border">
                ${repairAssessment.selfAssessment!}
            </td>
        </tr>
        <tr>
            <td colspan="14" class="none-border"></td>
            <td colspan="2" class="none-border" style="text-align: right">签章:</td>
            <td colspan="4" class="none-border"></td>
        </tr>
        <tr>
            <td colspan="14" class="none-border"></td>
            <td colspan="2" class="none-border" style="text-align: right">日期:</td>
            <td colspan="4" class="none-border" style="text-align: left;padding-left: 10px">
                 ${repairAssessment.stamp?string("yyyy-MM-dd")}
            </td>
        </tr>
    </table>
    </#if>
    <#if comment?? >
    <table border="1px" style="border:2px #000 solid;">
        <caption align="top"><h3>专家点评</h3></caption>
        <form id="comment_form">
            <input type="hidden" name="comment.repairRecordId" value="${repairRecordId}">
            <tr style="height:50px">
                <td colspan="4">组织单位</td>
                <td colspan="16">${comment.organization}</td>
            </tr>
            <tr style="height:50px">
                <td colspan="4">专家名单</td>
                <td colspan="16">${comment.expert}</td>
            </tr>
            <tr style="height:300px" >
                <td colspan="4">验收意见</td>
                <td colspan="16">${comment.advice}</td>
            </tr>
            <tr style="height:50px" >
                <td colspan="4">日期</td>
                <td colspan="16">${comment.date}</td>
            </tr>
            <tr >
                <td colspan="20" class="none-border">
                    <a class="btn btn-primary"  href="repairs/process?repairRecordId=${repairRecordId}" >下载PDF</a>
                    <a class="btn btn-default" href="repairRecords">返回</a>
                </td>
            </tr>
        </form>
    </table>
    <#else >
        <a class="btn btn-primary" style="margin: 20px auto" href="repairRecords">返回</a>
    </#if>

</#if>
</div>
<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
</body>

</html>
