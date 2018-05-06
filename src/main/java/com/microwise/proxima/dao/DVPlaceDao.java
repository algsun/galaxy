package com.microwise.proxima.dao;

import com.microwise.proxima.bean.DVPlaceBean;
import com.microwise.proxima.dao.base.BaseDao;

import java.util.List;

/**
 * 摄像机点位 dao
 *
 * @author gaohui
 * @date 2012-7-6
 * @check @li.jianfei 2013.09.02 #5293
 */
public interface DVPlaceDao extends BaseDao<DVPlaceBean> {

    /**
     * <p>
     * TODO 未验证
     * </p>
     * 根据IO模块发送的信息修改摄像机的IP地址
     *
     * @param dvPlaceId 点位ID
     * @param dvIp      摄像机IP
     * @author zhang.licong
     * @date 2012-8-15
     */
    public void updateDvIP(int dvPlaceId, String dvIp);

    /**
     * <pre>
     * 更新摄像机启用/停用状态
     *
     * @param dvPlaceId 摄像机点位id
     * @param enable    摄像机启用禁用状态
     * @return void
     *         </pre>
     * @author zhangpeng
     * @date 2013-3-22
     */
    public void updateDvEnable(String dvPlaceId, boolean enable);

    /**
     * <pre>
     * 获取摄像机启用/停用状态
     *
     * @param dvPlaceId 摄像机点位id
     * @return boolean true启用，false禁用
     *         </pre>
     * @author zhangpeng
     * @date 2013-3-27
     */
    public boolean findDvEnable(String dvPlaceId);

    /**
     * <p>
     * TODO 未验证
     * </p>
     *
     * @param monitorPointId
     * @return List<DVPlaceBean> 返回类型
     * @Title: findByMonitorPointId
     * @Description: 函数功能说明 根据监测点id查询出绑定在监测点上的摄像机的集合
     * @author JinGang
     * @date 2012-9-14 下午01:25:07
     */
    public List<DVPlaceBean> findByMonitorPointId(int monitorPointId);

    /**
     * <p>
     * TODO 未验证
     * </p>
     * 查询出所有摄像机点位 GuoTian 2012-9-11 下午16:13:08
     *
     * @参数： @param 无
     * @参数： @return 摄像机点位信息
     */
    public List<DVPlaceBean> findAllDVPlace();

    /**
     * <p>
     * TODO 未验证
     * </p>
     * 根据摄像机点位名称查询摄像机点位
     *
     * @param dvPlaceName 摄像机点位名称
     * @return
     */
    public DVPlaceBean findByName(String dvPlaceName);

    /**
     * <pre>
     * 添加业务使用：判断区域下是否拥有同名摄像机
     *
     * @param zoneId      区域id
     * @param dvPlaceName 摄像机点位名称
     * @return boolean true有/false无
     *         </pre>
     */
    public boolean hasSameNameByAdd(String zoneId, String dvPlaceName);

    /**
     * <pre>
     * 修改业务使用：判断区域下是否拥有同名摄像机
     *
     * @param zoneId      区域id
     * @param dvPlaceName 摄像机点位名称
     * @param dvPlaceId   摄像机id
     * @return boolean true有/false无
     *         </pre>
     */
    public boolean hasSameNameByUpdate(String zoneId, String dvPlaceName,
                                       String dvPlaceId);

    /**
     * 修改 摄像机 实景图
     *
     * @param dvId    摄像机id
     * @param realmap 实景图 名称
     * @author xu.baoji
     * @date 2013-8-22
     */
    public void updateRealmap(String dvId, String realmap);


    /**
     * 返回某个区域下的摄像机点位.
     * <p/>
     * 注意：如果无数据返回空集合
     *
     * @param zoneId
     * @return
     */
    public List<DVPlaceBean> findDvPlacesByZoneId(String zoneId);


    /**
     * <pre>
     * 查询站点下所有的摄像机，要携带区域信息，分页
     *
     * @param siteId   站点id
     * @param pageNmb  当前页码
     * @param pageSize 每页最大条数
     * @return List<DVPlaceBean> 查询出的摄像机点位列表
     *         </pre>
     * @author zhangpeng
     * @date 2012-7-11
     */
    public List<DVPlaceBean> findAll(String siteId, int pageNmb,
                                     int pageSize);

    /**
     * 返回某个区域下的摄像机点位.，分页
     * <p/>
     * 注意：如果无数据返回空集合
     *
     * @param zoneId
     * @return
     */
    public List<DVPlaceBean> findDvPlacesByZoneId(String zoneId, int pageNmb,
                                                  int pageSize);

    /**
     * <pre>
     * 查询站点下所有的摄像机，要携带区域信息，不分页
     *
     * @param siteId 站点id
     *
     * @author zhangpeng
     * @date 2012-7-11
     *
     * @return List<DVPlaceBean> 查询出的摄像机点位列表
     * </pre>
     */
    public List<DVPlaceBean> findAll(String siteId);
}
