package com.microwise.proxima.bean.common;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 分页tag
 *
 * @author zhang.licong
 * @date 2012-10-23
 *
 */
public class PagerTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private static final String DEFAULT_DIV_CLASS = "fc-page"; // 翻页按钮默认样式

	private static final int SHOW_PAGE_NUM = 10;
	private static final int SHOW_NUM = 4;

	private int pageCount;
	private int currentPage;
	private String action;
	private String className;
	private String queryConditions;
	private String functionName;

	// private boolean actionIsJsFunction;

	public PagerTag() {

	}

	/**
	 * 生成分页页面内容
	 *
	 * @author zhang.licong
	 * @date 2012-10-23
	 * @return
	 * @throws JspException
	 *
	 */
	public int doStartTag() throws JspException {

		if (this.queryConditions == null) {
			this.queryConditions = "";
		}
		if (new Integer(pageCount) == null) {
			throw new JspException("PagerTag标签中缺乏rowcount属性！");
		}
		if (new Integer(currentPage) == null) {
			throw new JspException("PagerTag标签中缺乏currentPage属性！");
		}
		if (action == null) {
			throw new JspException("PagerTag标签中缺乏action属性！");
		} else if (action.length() == 0) {
			throw new JspException("PagerTag标签中的action属性无值！");
		}

		// 如果页面标签中没写className属性，则让翻页按钮应用默认的按钮样式
		if (className == null || className.length() == 0) {
			className = DEFAULT_DIV_CLASS;
		}
		// 获取总页数
		int totalpagesize = pageCount;

		int[] showPages = getShowPages(totalpagesize, currentPage);

		try {
			StringBuffer html = new StringBuffer("<div class='" + className
					+ "'> ");
			if (currentPage > 1) {
				html.append(
						"<a onclick=\"nextPage(" + (currentPage - 1)
								+ " == 0 ? 1 : " + (currentPage - 1) + ")\"")
						.append(" >").append("上一页").append("</a>");
			}
			int showPagesValue = 0;
			for (int i = 0; i < showPages.length; i++) {

				if (showPages[i] == 0) {
					showPages[i] = showPagesValue + 1;
				}
				if (showPages[i] == currentPage) {
					html.append("<b>").append(showPages[i]).append("</b>");
				} else {
					html.append(
							"<a onclick=\"nextPage(" + (showPages[i]) + ")\"")
							.append(" >").append(showPages[i]).append("</a>");
					if (i != showPages.length - 1
							&& showPages[i + 1] - showPages[i] > 1 ) {
						html.append("<span style='padding-left:10px;'>...</span>");
					}
				}
				showPagesValue = showPages[i];
			}
			if (currentPage < pageCount) {
				html.append(
						"<a onclick=\"nextPage(" + (currentPage + 1) + " == "
								+ (totalpagesize) + " ? " + (totalpagesize)
								+ " : " + (currentPage + 1) + ")\"").append(
						" >").append("下一页").append("</a>");
			}

			html.append("</div>");

			pageContext.getOut().println(html.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspException(e.getMessage());
		}
		return TagSupport.SKIP_BODY;
	}

	/**
	 * 获取分页数据
	 * 
	 *
	 * @author zhang.licong
	 * @date 2012-10-23
	 * 
	 *
	 */
	private int[] getShowPages(int totalPages, int currentPage) {

		int[] showPages;
		if (totalPages <= PagerTag.SHOW_PAGE_NUM) {
			showPages = new int[totalPages];
			for (int i = 0; i < showPages.length; i++) {
				showPages[i] = (i + 1);
			}

			return showPages;
		}

		showPages = new int[10];
		if (currentPage == 1) {
			showPages[9] = totalPages;
			for (int i = 0; i < showPages.length - 1; i++) {
				showPages[i] = (i + 1);

			}
			return showPages;
		}

		if (currentPage == totalPages) {
			showPages[0] = 1;
			for (int i = showPages.length - 1; i > 0; i--) {
				showPages[showPages.length - i] = totalPages - i + 1;
			}
			return showPages;
		}

		showPages[0] = 1;
		showPages[9] = totalPages;
		int getCurBefore = 0;
		int getCurAfter = 0;
		if (currentPage - SHOW_NUM > 0) {
			getCurBefore += SHOW_NUM;
		} else if (currentPage == 4) {
			getCurBefore += 2;
		} else if (currentPage == 3) {
			getCurBefore += 1;
		} else {
			getCurAfter += Math.abs(currentPage - SHOW_NUM);
		}

		if (currentPage + SHOW_NUM < totalPages) {
			getCurAfter += SHOW_NUM;
		} else {
			getCurBefore += currentPage + SHOW_NUM - totalPages;
		}
		int showIndex = 1;
		for (int i = getCurBefore; i > 0; i--) {
			if (currentPage == 5) {
				showPages[showIndex++] = (currentPage - i) + 1;
			} else {
				showPages[showIndex++] = (currentPage - i);
			
			}
		}
		if (currentPage == 5) {
			
			showPages[showIndex++] = currentPage + 1;
		} else {
			showPages[showIndex++] = currentPage;
		}
		for (int i = 1; i < getCurAfter; i++) {
			if (currentPage == 5) {
				showPages[showIndex++] = currentPage + i + 1;
			} else {
				showPages[showIndex++] = currentPage + i;
			}
		}

		return showPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getQueryConditions() {
		return queryConditions;
	}

	public void setQueryConditions(String queryConditions) {
		this.queryConditions = queryConditions;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public void release() {
		this.action = null;
		this.className = null;
		this.currentPage = 1;
		this.pageCount = 0;
	}

}
