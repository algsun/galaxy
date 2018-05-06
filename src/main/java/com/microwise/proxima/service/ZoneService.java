package com.microwise.proxima.service;

import com.microwise.proxima.bean.Zone;

import java.util.List;

/**
 * 区域Service
 *
 * @author zhangpeng
 * @date 2013-3-22
 */
public interface ZoneService {

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
     * 查询站点下所有区域带有 父区域
     *
     * @param siteId 站点编号
     * @return List<Zone> 区域列表
     * @author 许保吉
     * @date 2013-5-21
     */
    public List<Zone> findAllZone(String siteId);

    /**
     * <pre>
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
     * 查询站点下有红外摄像机的区域
     *
     * @param siteId 站点id
     * @return List<Zone> 区域列表
     * @author xu.yuexi
     * @date 2014-4-2
     */
    public List<Zone> findHasInfrareds(String siteId);

    /**
     * <pre>
     * 根据区域id查询区域
     *
     * @param zoneId 区域id
     * @return Zone 区域列表
     *         </pre>
     * @author zhangpeng
     * @date 2013-3-22
     */
    public Zone findById(String zoneId);

    /**
     * <pre>
     * 判断区域下是否有摄像机
     *
     * @param zoneId 区域id
     * @return boolean true有/false没有
     *         </pre>
     * @author zhangpeng
     * @date 2013-3-28
     */
    public boolean hasDV(String zoneId);

}
