package com.microwise.blackhole.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;

/**
 * logicGroup表
 *
 * @author zhangpeng
 * @date 2012-11-15
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "users"})
public class LogicGroup {

    /**
     * logicGroupId
     */
    private Integer id;

    /**
     * logicGroup名称
     */
    private String logicGroupName;

    /**
     * 物理站点
     */
    private Site site;

    /**
     * 父级logicGroupId
     */
    private LogicGroup parent;

    /**
     * 机构代码
     */
    private String orgCode;

    /**
     * 地址
     */
    private String orgAddress;

    /**
     * 邮编
     */
    private String orgZipcode;

    /**
     * 网址
     */
    private String orgWebsite;

    /**
     * 联系电话
     */
    private String orgTel;

    /**
     * 传真
     */
    private String orgFax;

    /**
     * 激活状态：1未激活；2待激活；3已激活
     */
    private int activeState;

    /**
     * logicGroup类型：1本实例创建；2别的实例同步过来的
     */
    private int logicGroupType;

    /**
     * 对logicGroup拥有查询权限的用户
     */
    private Set<User> users;

    /**
     * 是否有孩子
     */
    private boolean haveChildren;

    /**
     * 站点皮肤
     */
    private String titleImage;

    /**
     * 站点背景
     */
    private String bgImage;

    /**
     * 是否使用默认站点皮肤
     */
    private int useTitle;

    /**
     * 是否使用默认站点背景
     */
    private int useBg;

    /**
     * 空构造函数
     *
     * @author zhangpeng
     * @date 2012-11-28
     */
    public LogicGroup() {
    }

    /**
     * 有参构造函数
     *
     * @param logicGroupName 站点组名称
     * @param parent         父站组点
     * @param activeState    激活状态
     * @param logicGroupType 站点组类型
     * @author zhangpeng
     * @date 2012-11-28
     */
    public LogicGroup(String logicGroupName, LogicGroup parent,
                      int activeState, int logicGroupType) {
        this.logicGroupName = logicGroupName;
        this.parent = parent;
        this.activeState = activeState;
        this.logicGroupType = logicGroupType;
    }

    /**
     * 用来标识 logicGroup 已经被禁用的常量
     */
    public static final Integer LOGICGROUP_ISDISABLE = 1;

    /**
     * 用来标识 logicGroup 没有被禁用的常量
     */
    public static final Integer LOGICGROUP_NOTDISABLE = 0;

    /**
     * 标识 logicGroup 类型 是 本地 logicGroup 的常量
     */
    public static final Integer LOGICGROUP_TYPE_ISNATIVE = 1;

    /**
     * 标识 logicGroup 类型 为 同步上来的 的logicGroup 的常量
     */
    public static final Integer LOGICGROUP_TYPE_NOTNATIVE = 0;

    /**
     * 标识 logicGroup 为 未激活状态的常量
     */
    public static final Integer LOGICGROUP_NOT_ACTIVESTATE = 1;

    /**
     * 标识 logicGroup 为 待激活状态的常量
     */
    public static final Integer LOGICGROUP_DAI_ACTIVESTATE = 2;

    /**
     * 标识 logicGroup 为 已经激活状态的常量
     */
    public static final Integer LOGICGROUP_IS_ACTIVESTATE = 3;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogicGroupName() {
        return logicGroupName;
    }

    public void setLogicGroupName(String logicGroupName) {
        this.logicGroupName = logicGroupName;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public LogicGroup getParent() {
        return parent;
    }

    public void setParent(LogicGroup parent) {
        this.parent = parent;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public String getOrgZipcode() {
        return orgZipcode;
    }

    public void setOrgZipcode(String orgZipcode) {
        this.orgZipcode = orgZipcode;
    }

    public String getOrgWebsite() {
        return orgWebsite;
    }

    public void setOrgWebsite(String orgWebsite) {
        this.orgWebsite = orgWebsite;
    }

    public String getOrgTel() {
        return orgTel;
    }

    public void setOrgTel(String orgTel) {
        this.orgTel = orgTel;
    }

    public String getOrgFax() {
        return orgFax;
    }

    public void setOrgFax(String orgFax) {
        this.orgFax = orgFax;
    }

    public int getActiveState() {
        return activeState;
    }

    public void setActiveState(int activeState) {
        this.activeState = activeState;
    }

    public int getLogicGroupType() {
        return logicGroupType;
    }

    public void setLogicGroupType(int logicGroupType) {
        this.logicGroupType = logicGroupType;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public boolean isHaveChildren() {
        return haveChildren;
    }

    public void setHaveChildren(boolean haveChildren) {
        this.haveChildren = haveChildren;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public String getBgImage() {
        return bgImage;
    }

    public void setBgImage(String bgImage) {
        this.bgImage = bgImage;
    }

    public int getUseTitle() {
        return useTitle;
    }

    public void setUseTitle(int useTitle) {
        this.useTitle = useTitle;
    }

    public int getUseBg() {
        return useBg;
    }

    public void setUseBg(int useBg) {
        this.useBg = useBg;
    }
}
