package com.microwise.orion.action.storeroom;

import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.proxy.UserProxy;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.PagingUtil;
import com.microwise.orion.service.UserZoneService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import com.microwise.orion.vo.UserZoneVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 出库盘点
 *
 * @author duan.qixin
 * @date 2013-06-13 
 * @check @gaohui #4250 2013-06-19
 */
@Beans.Action
@Orion
public class StockBindAction extends OrionLoggerAction{

    /**
     * 用户代理service
     */
    @Autowired
	private UserProxy userProxy;

    /**
     * 用户区域关系 service
     */
    @Autowired
    private UserZoneService userZoneService;
    
    /**
	 * struts OGNL参数
	 */
    //input
    private String zoneId;
    // TODO 优先使用数组  @gaohui 2013-06-19
    private String userIds;
    private String userName;
    
    //output
    private List<User> users;
    private List<UserZoneVo> uzs;
    //分页信息
  	private Integer index = 1;
  	private int pageCount;
  	private int count;
  	
    
  	/**
	 * 私有化参数
	 */
	// 获得logicgroupId 当前站点编号
	private int logicGroupId = Sessions.createByAction().currentLogicGroup().getId();
	// 获得siteId 当前站点编号
	private String siteId = Sessions.createByAction().currentSiteId();
	
    /**
     * 仓库和人员的绑定信息
     * @return
     */
	public String stockBind() {
        try{
            ActionMessage.createByAction().consume();
            uzs = userZoneService.findUserZoneList(siteId);
            log("库房管理","库房管理列表");
        }catch (Exception e){
            logFailed("库房管理","库发管理列表查询失败");
            e.printStackTrace();
        }
		return "success";
	}
	
	
	/**
	 * 人员信息列表
	 * @return
	 */
	public String userList() {
        try{
            count = userProxy.findUserCount(logicGroupId, userName);
            pageCount = PagingUtil.pagesCount(count, Constants.SIZE_PER_PAGE);
            users = userProxy.findUserList(logicGroupId, userName, index, Constants.SIZE_PER_PAGE);
        }catch (Exception e){
            e.printStackTrace();
        }
		return "success";
	}
	
	
	/**
	 * 添加管理员到仓库
	 * @return
	 */
	public String userBind() {
        try{
            String[] temp = userIds.split(",");
            int[] uids = new int[temp.length];
            for (int i = 0; i < temp.length; i++) {
                uids[i] = Integer.valueOf(temp[i]);
            }

            userZoneService.addUserZone(zoneId, uids);
        }catch (Exception e){
            e.printStackTrace();
        }
		return "success";
	}
	
	/**
	 * 删除管理员
	 * @return
	 */
	public String deleteBind() {
        try{
            int uid = Integer.valueOf(userIds);
            userZoneService.deleteUserZone(uid, zoneId);

            ActionMessage.createByAction().success("删除区域管理员成功");
            log("出库管理","删除区域管理员");
        }catch (Exception e){
            logFailed("出库管理","删除区域管理员失败");
            e.printStackTrace();
        }
		return "success";
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getLogicGroupId() {
		return logicGroupId;
	}
	public void setLogicGroupId(int logicGroupId) {
		this.logicGroupId = logicGroupId;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public String getZoneId() {
		return zoneId;
	}
	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public List<UserZoneVo> getUzs() {
		return uzs;
	}
	public void setUzs(List<UserZoneVo> uzs) {
		this.uzs = uzs;
	}
}
