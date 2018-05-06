package com.microwise.orion.dao;

import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.vo.RelicVo;

import java.util.List;

/**
 * 文物dao 接口 （与文物信息查询，修改，添加有关的功能）
 *
 * @author xubaoji
 * @date 2013-5-13
 * @check 2013-06-05 zhangpeng svn:4076 TODO 未审xiedeng代码
 */
public interface RelicDao extends BaseDao<Relic> {


    /**
     * 查询
     */
    public List<String> findLocationsIdByRelicId(Integer relicId);

    /**
     * 分页查询文物基本信息
     *
     * @param totalCode   文物总登记号（模糊）
     * @param name        文物名称（模糊）
     * @param zoneId      文物区域id
     * @param catalogCode 文物编目号（模糊）
     * @param typeCode    文物分类号（模糊）
     * @param eraId       文物年代编号
     * @param levelId     文物级别编号
     * @param textureId   文物质地编号
     * @param siteId      站点编号
     * @param state       库房状态:null、全部;0、在库;1、待出库2、出库
     * @param index       当前页
     * @param size        分页单位
     * @param iscanceled  是否注销  flase 未注销  true 注销
     * @return List<Relic> 文物基本信息列表（不携带relic 实体中set集合属性值）
     * @author 许保吉
     * @date 2013-5-15
     */
    public List<Relic> findRelics(String totalCode, String name, String zoneId,
                                  String catalogCode, String typeCode, Integer eraId,
                                  Integer levelId, Integer textureId, String siteId, Integer state,
                                  Integer index, Integer size, Boolean iscanceled);

    /**
     * 查询文物基本信息导出excle
     *
     * @param totalCode   文物总登记号（模糊）
     * @param name        文物名称（模糊）
     * @param zoneId      文物区域id
     * @param catalogCode 文物编目号（模糊）
     * @param typeCode    文物分类号（模糊）
     * @param eraId       文物年代编号
     * @param levelId     文物级别编号
     * @param textureId   文物质地编号
     * @param siteId      站点编号
     * @param state       库房状态:null、全部;0、在库;1、待出库2、出库
     * @return List<Relic> 文物基本信息列表（携带relic 实体中set集合属性值）
     * @author liuzhu
     * @date 2013-07-30
     */
    public List<Relic> findRelics(String totalCode, String name, String zoneId,
                                  String catalogCode, String typeCode, Integer eraId,
                                  Integer levelId, Integer textureId, String siteId, Integer state, Boolean isanceled);

    /**
     * 查询文物基本信息导出excle
     *
     * @param totalCode   文物总登记号（模糊）
     * @param name        文物名称（模糊）
     * @param zoneIds     文物区域id
     * @param catalogCode 文物编目号（模糊）
     * @param typeCode    文物分类号（模糊）
     * @param eraIds      文物年代编号
     * @param levelIds    文物级别编号
     * @param textureIds  文物质地编号
     * @param siteId      站点编号
     * @param states      库房状态:null、全部;0、在库;1、待出库2、出库
     * @return List<Relic> 文物基本信息列表（携带relic 实体中set集合属性值）
     * @author liuzhu
     * @date 2013-07-30
     */
    public List<Relic> findRelics(String totalCode, String name, String[] zoneIds, Integer[] labelIds, String catalogCode,
                                  String typeCode, Integer[] eraIds, Integer[] levelIds, Integer[] textureIds,
                                  String siteId, Integer[] states, Integer index, Integer size, Boolean isCanceled);

    /**
     * 查询当前站点下文物总数
     *
     * @param siteId 站点ID
     * @return 返回站点文物总数
     */
    public int findRelicsCount(String siteId);

    /**
     * 查询所有文物信息
     *
     * @param siteId      站点ID
     * @param index       当前页面
     * @param sizePerPage 每页显示记录数
     * @return 文物列表
     * @author li.jianfei
     * @date 2014-04-16
     */
    public List<Relic> findRelics(String siteId, int index, int sizePerPage);


    /**
     * 根据文物名称、总登记号查找文物个数
     *
     * @param siteId             站点id
     * @param relicNameTotalCode 文物名称、总登记号
     * @param ids                需要过滤的文物id,不过滤传null或空list即可
     * @return 文物总数量
     * @author liuzhu
     * @date 2016-6-29
     */
    public int findRelicsCount(String siteId, String relicNameTotalCode, List<Integer> ids);

    /**
     * 根据文物名称、总登记号查找文物
     *
     * @param siteId             站点id
     * @param relicNameTotalCode 文物名称、总登记号
     * @param pageIndex          页码
     * @param pageSize           一页显示多少条
     * @param ids                需要过滤的文物id,不过滤传null或空list即可
     * @return 文物list
     * @author liuzhu
     * @date 2016-6-29
     */
    public List<Relic> findRelics(String siteId, String relicNameTotalCode, int pageIndex, int pageSize, List<Integer> ids);

    /**
     * 查询文物总数量
     *
     * @param totalCode   文物总登记号（模糊）
     * @param name        文物名称（模糊）
     * @param zoneId      文物区域id
     * @param catalogCode 文物编目号（模糊）
     * @param typeCode    文物分类号（模糊）
     * @param eraId       文物年代编号
     * @param levelId     文物级别编号
     * @param textureId   文物质地编号
     * @param siteId      站点编号
     * @param state       库房状态:null、全部;0、在库;1、待出库2、出库
     * @return Integer 文物数量
     * @author 许保吉
     * @date 2013-5-15
     */
    public Integer findRelicCount(String totalCode, String name, String zoneId,
                                  String catalogCode, String typeCode, Integer eraId,
                                  Integer levelId, Integer textureId, String siteId, Integer state, Boolean iscanceled);

    /**
     * 查询文物总数量
     *
     * @param totalCode   文物总登记号（模糊）
     * @param name        文物名称（模糊）
     * @param zoneIds     文物区域id
     * @param labelIds    标签编号
     * @param catalogCode 文物编目号（模糊）
     * @param typeCode    文物分类号（模糊）
     * @param eraIds      文物年代编号
     * @param levelIds    文物级别编号
     * @param textureIds  文物质地编号
     * @param siteId      站点编号
     * @param states      库房状态:null、全部;0、在库;1、待出库2、出库
     * @return Integer 文物数量
     * @author 许保吉
     * @date 2013-5-15
     */
    public Integer findRelicCount(String totalCode, String name, String[] zoneIds, Integer[] labelIds, String catalogCode,
                                  String typeCode, Integer[] eraIds, Integer[] levelIds, Integer[] textureIds,
                                  String siteId, Integer[] states, Boolean isCanceled);

    /**
     * 查询文物总件数
     *
     * @param totalCode   文物总登记号（模糊）
     * @param name        文物名称（模糊）
     * @param zoneId      文物区域id
     * @param catalogCode 文物编目号（模糊）
     * @param typeCode    文物分类号（模糊）
     * @param eraId       文物年代编号
     * @param levelId     文物级别编号
     * @param textureId   文物质地编号
     * @param siteId      站点编号
     * @param state       库房状态:null、全部;0、在库;1、待出库2、出库
     * @return Integer 文物件数
     * @author 许保吉
     * @date 2013-5-15
     */
    public Integer findRelicAllCount(String totalCode, String name,
                                     String zoneId, String catalogCode, String typeCode, Integer eraId,
                                     Integer levelId, Integer textureId, String siteId, Integer state, Boolean iscanceled);

    /**
     * 查询文物总件数
     *
     * @param totalCode   文物总登记号（模糊）
     * @param name        文物名称（模糊）
     * @param zoneIds     文物区域id
     * @param catalogCode 文物编目号（模糊）
     * @param typeCode    文物分类号（模糊）
     * @param eraIds      文物年代编号
     * @param levelIds    文物级别编号
     * @param textureIds  文物质地编号
     * @param siteId      站点编号
     * @param states      库房状态:null、全部;0、在库;1、待出库2、出库
     * @return Integer 文物件数
     */
    public Integer findRelicAllCount(String totalCode, String name, String[] zoneIds, Integer[] labelIds,
                                     String catalogCode, String typeCode, Integer[] eraIds, Integer[] levelIds,
                                     Integer[] textureIds, String siteId, Integer[] states, Boolean isCanceled);

    /**
     * 查询文物基本信息和所有集合信息
     *
     * @param totalCode 文物总登记
     * @param siteId    站点编号
     * @return relic 文物信息对象（携带 relic 实体中set 集合属性的值）
     * @author 许保吉
     * @date 2013-5-15
     */
    public Relic findRelic(String totalCode, String siteId);

    /**
     * 查询文物基本信息和所有集合信息
     *
     * @param relicId 文物id
     * @param siteId  站点编号
     * @return relic 文物信息对象（携带 relic 实体中set 集合属性的值）
     * @author liuzhu
     * @date 2015-09-08
     */
    public Relic findRelic(int relicId, String siteId);

    /**
     * 根据文物总登记号和站点Id查询文物 id编号
     *
     * @param totalCode 文物总登记号
     * @param siteId    站点编号
     * @return integer id编号
     * @author 许保吉
     * @date 2013-5-23
     */
    public Integer findRelicId(String totalCode, String siteId);

    /**
     * 查询文物藏品卡信息
     *
     * @param totalCode 文物总登记号
     * @param siteId    站点编号
     * @return relic 文物信息
     * @author 许保吉
     * @date 2013-5-15
     */
    public Relic findRelicCard(String totalCode, String siteId);

    /**
     * 查询文物藏品卡信息
     *
     * @param relicId 文物id
     * @param siteId  站点编号
     * @return relic 文物信息
     * @author liuzhu
     * @date 2015-09-08
     */
    public Relic findRelicCard(int relicId, String siteId);

    /**
     * 查询 上一个 文物总登记号
     *
     * @param id     文物id 编号
     * @param siteId 站点编号
     * @return totalCode 文物总登记号
     * @author 许保吉
     * @date 2013-5-17
     */
    public String findPreRelicTotalCode(Integer id, String siteId);

    /**
     * 查询 下一个 文物总登记号
     *
     * @param id     文物id 编号
     * @param siteId 站点编号
     * @return totalCode 文物总登记号
     * @author 许保吉
     * @date 2013-5-17
     */
    public String findNextRelicTotalCode(Integer id, String siteId);

    /**
     * 查询一个站点下所有文物信息 （为手持机设备提供文物数据）
     *
     * @param siteId 站点编号
     * @return List<RelicVo> 文物信息 vo
     * @author 许保吉
     * @date 2013-5-22
     */
    public List<RelicVo> findHasTagRelics(String siteId);

    /**
     * 更新文物标签状态为 有标签
     *
     * @param siteId        站点编号
     * @param totalCodeList 文物总登记号列表
     * @return void
     * @author 许保吉
     * @date 2013-5-23
     */
    public void updateRelicTag(String siteId, List<String> totalCodeList);

    /**
     * 根据文物总登记号和站点编号查询文物的总数
     *
     * @param siteId     站点编号
     * @param totalCode  总登记号
     * @param relicState 文物状态 (-1、查询所有状态 0、在库 ;1、待出库；2、出库)
     * @return Integer 文物的总数
     * @author xiedeng
     * @date 2013-5-28
     */
    public Integer findRelicCountBySiteIdAndTotalCode(String siteId,
                                                      String totalCode, int relicState);

    /**
     * 根据站点编号和总登记号查询文物
     *
     * @param siteId     站点编号
     * @param totalCode  总登记号
     * @param start      分页开始位置
     * @param size       分页每页条数
     * @param relicState 文物状态 (-1、查询所有状态 0、在库 ;1、待出库；2、出库)
     * @return 文物集合
     * @author xiedeng
     * @date 2013-5-28
     */
    public List<Relic> findRelicBySiteIdAndTotalCode(String siteId,
                                                     String totalCode, int start, int size, int relicState);

    /**
     * 根据文物ID批量更改在库状态
     *
     * @param relicIds 文物编号
     * @param state    文物状态
     * @return 是否执行成功 true 成功 false 失败
     */
    public void updateRelicState(List<Integer> relicIds, int state);

    /**
     * 查询出库单文物信息（携带有区域信息）
     *
     * @param relicIds 要查询的文物id 编号列表
     * @return List<Relic> 文物实体列表
     * @author 许保吉
     * @date 2013-6-19
     */
    public List<Relic> findRelicByIds(List<Integer> relicIds);


    /**
     * 修改站点下一个文物的注销状态
     *
     * @param siteId     站点编号
     * @param relicId    文物ID
     * @param iscanceled 是否注销  false 未注销  true 注销
     * @author xu.baoji
     * @date 2013-9-3
     */
    public void updateRelicCanceledState(String siteId, int relicId, boolean iscanceled);

    /**
     * 验证文物总登记号是否存在
     *
     * @param totalCode 文物总登记号
     * @author chen.yaofei
     * @date 2016-03-21
     */
    public List<Relic> validateByTotalCode(String totalCode);
}
