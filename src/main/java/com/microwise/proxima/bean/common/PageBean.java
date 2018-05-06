package com.microwise.proxima.bean.common;



import java.util.List;
import java.util.Map;

/**
 * @author 张俐聪
 * 名称：分页帮助类
 * 描述：封装分页参数
 */
public class PageBean<T> {
	private int totalCount = 0;     //总记录数
	private int pageSize = 10;		//每页记录数
    private int currentPage = 1;    //当前第几页
    private int totalPage = 0;      //总页数
    private String url;				//当前页的链接地址
	private List<T> resultList;		//查询结果集
	private Map<String,T> resultMap;		//查询结果集
	private Integer condition;		
	
	/**
	 * 默认构造方法
	 */
	public PageBean(){
		
	}
	
	/**
	 * 构造方法
	 * @param totalCount	//总记录数
	 * @param pageSize		//每页记录数
	 * @param currentPage	//当前第几页
	 * @param resultList	//查询结果集
	 */
	public PageBean(int totalCount, int pageSize, int currentPage, List<T> resultList) {
		super();
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.currentPage = PageBean.countCurrentPage(currentPage);
		this.totalPage = PageBean.countTotalPage(pageSize, totalCount);
		this.resultList = resultList;
	}
	
	/**
	 * 构造方法
	 * @param totalCount	//总记录数
	 * @param pageSize		//每页记录数
	 * @param currentPage	//当前第几页
	 */
	public PageBean(int totalCount, int pageSize, int currentPage, Map<String,T> resultMap) {
		super();
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.currentPage = PageBean.countCurrentPage(currentPage);
		this.totalPage = PageBean.countTotalPage(pageSize, totalCount);
		this.resultMap = resultMap;
	}

	/**
     * 计算总页数
     * @param pageSize 每页记录数
     * @param totalCount 总记录数
     * @return 总页数
     */
    public static int countTotalPage(final int pageSize,final int totalCount){
        int totalPage = totalCount % pageSize == 0 ? totalCount/pageSize : totalCount/pageSize+1;
        return totalPage;
    }
    
    /** 
     * 计算当前页开始记录
     * @param pageSize 每页记录数
     * @param currentPage 当前第几页
     * @return 当前页开始记录号
     */
    public static int getBeginIndex(final int pageSize,final int currentPage){
        final int beginIndex = pageSize * (currentPage - 1);
        return beginIndex;
    }
    
    /**
     * 计算当前页,若为0则用1代替
     * @return 当前页
     */
    public static int countCurrentPage(int currentPage){
        final int curPage = (currentPage == 0 ? 1 : currentPage);

        return curPage;
    }

    /**
     * 将传入的SQL转换为ORACLE分页语句
     * @param sql
     * @param pageSize 每页记录数
     * @param currentPage 当前第几页
     * @return
     */
    public static String toOraclePageSQL(String sql,Integer pageSize,Integer currentPage){
    	Integer beginIndex = pageSize * (currentPage - 1);
    	Integer endIndex = beginIndex + pageSize;
    	StringBuffer sbf = new StringBuffer();
    	sbf.append("select * from (")
			.append(" select row_.*, rownum rownum_ from (")
			.append(sql)
			.append(" ) row_ where rownum <= ")
			.append(endIndex)
			.append(" ) where rownum_ > ")
			.append(beginIndex);
    	return sbf.toString();
    }
    
    /**
     * 将传入的SQL转换为MYSQL分页语句
     * @param sql
     * @param pageSize 每页记录数
     * @param currentPage 当前第几页
     * @return
     */
    public static String toMysqlPageSQL(String sql,Integer pageSize,Integer currentPage){
    	Integer beginIndex = pageSize * (currentPage - 1);
    	StringBuffer sbf = new StringBuffer();
    	sbf.append("select row_.* from (")
			.append(sql)
			.append(" ) row_ limit ")
			.append(beginIndex)
			.append(" , ")
			.append(pageSize);
    	return sbf.toString();
    }
    
	public int getTotalCount() {
		return totalCount;
	}
	/**
	 * 总记录数
	 * @param totalCount
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	/**
	 * 总页数
	 * @param totalPage
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	/**
	 * 当前页
	 * @param currentPage
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * 每页记录数
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public List<T> getResultList() {
		return resultList;
	}
	/**
	 * 查询结果集
	 * @param resultList
	 */
	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
	
	public Map<String, T> getResultMap() {
		return resultMap;
	}
	/**
	 * 查询结果集
	 */
	public void setResultMap(Map<String, T> resultMap) {
		this.resultMap = resultMap;
	}

	public Integer getCondition() {
		return condition;
	}
	public void setCondition(Integer condition) {
		this.condition = condition;
	}
}
