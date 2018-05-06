package com.microwise.proxima.dao;

import com.microwise.proxima.bean.Zone;
import com.microwise.proxima.dao.base.BaseDao;

import java.util.List;

/**
 * 区域Dao层
 *
 * @author zhangpeng
 * @date 2013-3-25
 */
public interface ZoneDao extends BaseDao<Zone> {

    /**
     * <pre>
     * 查询站点下的所有区域
     *
     * @param siteId 站点id
     *               </pre>
     * @return List<Zone> 区域列表
     * @author zhangpeng
     * @date 2013-3-22
     */
    public List<Zone> find(String siteId);

    /**
     * <pre>
     * 查询站点下的所有区域 带有父区域
     *
     * @param siteId 站点id
     *               </pre>
     * @return List<Zone> 区域列表
     * @author 许保吉
     * @date 2013-05-21
     */
    public List<Zone> findAllZone(String siteId);

    /**
     * <pre>
     * TODO 未实现
     * 查询站点下有光学摄像机的区域
     *
     * @param siteId 站点id
     *               </pre>
     * @return List<Zone> 区域列表
     * @author zhangpeng
     * @date 2013-3-22
     */
    public List<Zone> findHasOptics(String siteId);

    /**
     * <pre>
     * 获取区域下的摄像机数量
     *
     * @param zoneId 区域id
     * @return int 摄像机数量
     *         </pre>
     * @author zhangpeng
     * @date 2013-3-28
     */
    public int getDVCount(String zoneId);

    /**
     * 查询站点下有红外摄像机的区域
     *
     * @param siteId 站点id
     * @return List<Zone> 区域列表
     * @author xu.yuexi
     * @date 2014-4-2
     */
    public List<Zone> findHasInfrareds(String siteId);
}
