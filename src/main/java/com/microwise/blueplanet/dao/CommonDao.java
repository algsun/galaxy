package com.microwise.blueplanet.dao;

/**
 * 公共Dao部分（主用于数据同步业务相关数据库表版本号更新等问题）
 *
 * @author zhangpeng
 * @date 2013-1-25
 * @check 2013-02-25 xubaoji svn:1750
 */
public interface CommonDao {

    /**
     * 通过表名获取该表对应的数据版本,与设备挂钩操作时，需要对数据版本进行更新，
     * 如果设备表无对应数据版本号或对应号码为0，不更新，如果不为0，更新为当前版本号+1
     *
     * @param tableName 根据表名获取对应的版本号
     * @return 数据版本号 返回值可能为null/数字
     */
    public Integer findDataVersion(String tableName);

}
