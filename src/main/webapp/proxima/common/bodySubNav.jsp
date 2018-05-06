<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%!int id = 0;%>
<div class="row">
	<div class="span12 m-t-20">
		<ul class="nav nav-pills">
			<s:iterator id="popedom" value="#session.popedomsOfUser"> 
				<s:if test="#currentSubNav==#popedom.popedomId">
					<s:set name="parentID" value="#popedom.parent.id" />
				</s:if>
			</s:iterator>			
			
			<s:iterator id="popedom"
				value="@com.microwise.util.PopedomUtil@getChildren(#session.popedomsOfUser,#parentID)">
				<li
					<s:if test="#currentSubNav == #popedom.popedomId"> class="active"</s:if>>
					<a href="<s:property value="#popedom.url"/>"><s:property
							value="#popedom.popedomName" /> </a>
				</li>
			</s:iterator>
		</ul>
	</div>
</div>
