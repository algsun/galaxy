<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/util-tags" prefix="app"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<link type="text/css" href="css/pager.css"   rel="stylesheet"/>
<%--
	文件名称：分页页面
	文件说明：该分页可应用于带有查询条件的分页，不适用于查询条件和展示页面分开的框架页面。
			使用带有查询条件的分页时，Form表单的action需填写查询地址。
--%>
<c:set var="totalPage" value='${empty pageBean.totalPage ? "1" : pageBean.totalPage}' />
<c:set var="currentPage" value='${empty pageBean.currentPage ? "1" : pageBean.currentPage}' />
<!--<span style="display:none">每页显示<input type="text" name="pageBean.pageSize" value="${pageBean.pageSize}" size="5"/>条</span>
<span>共${totalPage}页</span>
<span>第${currentPage}页</span>
<c:if test="${totalPage > 1}">
	<span>&nbsp;跳到第
	<select id="select01" onchange="change();">
		<c:forEach begin="1" step="1" end="${totalPage}" var="i">
		<option value="${i}" ${i == currentPage ? 'selected="selected"' : ''}>${i}</option>
		</c:forEach>
		</select>
		页
	</span>
</c:if>
<!-- 首页 -->
<!--<span>
	<c:choose>
		<c:when test="${totalPage == 1 || currentPage == 1}">
			<img src="images/pageImages/index_newPage01_1.gif" border="0" align="bottom" />
		</c:when>
		<c:when test="${totalPage != 1 && !(currentPage == 1)}">
			<a onclick="nextPage(1)" style="cursor: hand">
				<img src="images/pageImages/index_newPage01.gif" border="0" align="bottom" />
			</a>
		</c:when>
	</c:choose>
</span>
<!-- 上一页 -->
<!--<span>
	<c:choose>
		<c:when test="${currentPage == 1 || (totalPage == 0)}">
			<img src="images/pageImages/index_newpage02_1.gif" border="0" align="bottom" />
		</c:when>
		<c:otherwise>
			<c:set var="prov1" value="${currentPage - 1}" />
			<a onclick="nextPage(${prov1 == 0 ? 1 : prov1})" style="cursor: hand">
				<img src="images/pageImages/index_newpage02.gif" border="0" align="bottom" />
			</a>
		</c:otherwise>
	</c:choose>
</span>
<!-- 下一页 -->
<!--<span>
	<c:choose>
		<c:when test = "${currentPage == totalPage || currentPage >= totalPage}">
			<img src="images/pageImages/index_newPage03_1.gif" border="0" align="bottom" />
		</c:when>
		<c:otherwise>
			<c:set var="prov2" value="${currentPage + 1}" />
			<a onclick="nextPage(${prov2 == totalPage ? totalPage : prov2})" style="cursor: hand;">
				<img src="images/pageImages/index_newPage03.gif" border="0" align="bottom" />
			</a>
		</c:otherwise>
	</c:choose>
</span>
<!-- 尾页 -->
<!--<span>
	<c:choose>
		<c:when test="${currentPage == totalPage || currentPage >= totalPage}">
			<img src="images/pageImages/index_newPage04_1.gif" border="0" align="bottom" />
		</c:when>
		<c:otherwise>
			<a onclick="nextPage(${totalPage})" style="cursor: hand">
				<img src="images/pageImages/index_newPage04.gif" border="0" align="bottom" />
			</a>
		</c:otherwise>
	</c:choose>
</span>-->
  
        <app:spitPageTag  pageCount="${totalPage}" currentPage="${currentPage}" action="queryOpticsDVPlace.action" />
 
<script type="text/javascript">

	function change() {
		var selectObj=document.getElementById("select01");
		//下拉框选中项的文本
		var selectOptionText= selectObj.options[selectObj.selectedIndex].innerText;
		//下拉框选中项的值
		var selectOptionValue=selectObj.options[selectObj.selectedIndex].value;
		nextPage(selectOptionText);
	}
	
	function nextPage(currentPage){
		var input = document.createElement("input");
		input.type = "hidden";
		input.name = "pageBean.currentPage";
		input.value = currentPage;
		document.forms[0].appendChild(input);
		document.forms[0].submit();
	}
</script>