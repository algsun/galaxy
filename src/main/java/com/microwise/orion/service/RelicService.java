package com.microwise.orion.service;

import com.microwise.blueplanet.bean.po.LocationPO;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.bean.RelicCard;
import com.microwise.orion.vo.RelicVo;

import java.util.List;

/**
 * 文物service 接口 文物信息（基本信息、藏品卡、档案）查询，添加，修改有关的功能
 *
 * @author xubaoji
 * @date 2013-5-13
 * @check 2013-06-05 zhangpeng svn:4076
 */
public interface RelicService {

    /**
     * 查询文物的所属位置点id
     * @param relicId 文物id
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
     * @param iscanceled  是否注销 false未注销  true 注销  null 为全部
     * @return List<Relic> 文物基本信息列表（不携带relic 实体中set集合属性值）
     * @author 许保吉
     * @date 2013-5-15
     */
    public List<Relic> findRelics(String totalCode, String name, String zoneId,
                                  String catalogCode, String typeCode, Integer eraId,
                                  Integer levelId, Integer textureId, String siteId, Integer state,
                                  Integer index, Integer size, Boolean iscanceled);

    /**
     * 查询文物基本信息(导出excle)
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
                                  Integer levelId, Integer textureId, String siteId, Integer state, Boolean iscanceled);

    /**
     * 查询文物基本信息(导出excle)
     *
     * @param totalCode   文物总登记号（模糊）
     * @param name        文物名称（模糊）
     * @param zoneIds     文物区域id
     * @param labelIds    文物自定义标签编号
     * @param catalogCode 文物编目号（模糊）
     * @param typeCode    文物分类号（模糊）
     * @param eraIds      文物年代编号
     * @param levelIds    文物级别编号
     * @param textureIds  文物质地编号
     * @param siteId      站点编号
     * @param states      库房状态:null、全部;0、在库;1、待出库2、出库
     * @return List<Relic> 文物基本信息列表（携带relic 实体中set集合属性值）
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
     * @param siteId 站点id
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
     * @param siteId 站点id
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
     * @param iscanceled  是否注销  false未 注销 true 注销  null全部
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
     * @param siteId      站点编号
     * @param catalogCode 文物编目号（模糊）
     * @param typeCode    文物分类号（模糊）
     * @param eraIds      文物年代编号
     * @param levelIds    文物级别编号
     * @param textureIds  文物质地编号
     * @param siteId      站点编号
     * @param states      库房状态:null、全部;0、在库;1、待出库2、出库
     * @param isCanceled  是否注销  false未 注销 true 注销  null全部
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
     * @param iscanceled  是否注销 false 未注销  true 注销 null 全部
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
     * @param isCanceled  是否注销 false 未注销  true 注销 null 全部
     * @return Integer 文物件数
     */
    public Integer findRelicAllCount(String totalCode, String name,
                                     String[] zoneIds, Integer[] labelIds, String catalogCode, String typeCode, Integer[] eraIds,
                                     Integer[] levelIds, Integer[] textureIds, String siteId, Integer[] states, Boolean isCanceled);

    /**
     * 通过 文物总登记号及站点id 查询文物所有信息
     *
     * @param relicId 文物ID
     * @param siteId  站点id
     * @return relic 文物信息（基本信息，所有属性信息，所有集合信息）
     * @author 许保吉
     * @date 2013-5-17
     */
    public Relic findRelicByRelicId(int relicId, String siteId);

    /**
     * 根据文物总登记号及站点id查询文物档案信息
     *
     * @param totalCode 文物总登记
     * @param siteId    站点id
     * @return Relic 文物档案对象（携带 relic 实体中set 集合属性的值）
     * @author 许保吉
     * @date 2013-5-15
     */
    public Relic findRelicArchives(String totalCode, String siteId);

    /**
     * 根据文物总登记号及站点id查询文物档案信息
     *
     * @param relicId 文物id
     * @param siteId  站点id
     * @return Relic 文物档案对象（携带 relic 实体中set 集合属性的值）
     * @author liuzhu
     * @date 2015-09-08
     */
    public Relic findRelicArchives(int relicId, String siteId);

    /**
     * 根据文物总登记号及站点id查询文物藏品卡信息
     *
     * @param totalCode 文物总登记号
     * @param siteId    站点id
     * @return RelicCard 文物藏品卡信息
     * @author 许保吉
     * @date 2013-5-15
     */
    public RelicCard findRelicCard(String totalCode, String siteId);

    /**
     * 根据文物总登记号及站点id查询文物藏品卡信息
     *
     * @param relicId 文物id
     * @param siteId  站点id
     * @return RelicCard 文物藏品卡信息
     * @author liuzhu
     * @date 2015-09-08
     */
    public RelicCard findRelicCard(int relicId, String siteId);


    /**
     * 查询 上一个 和 下一个 文物总登记号
     *
     * @param totalCode 文物总登记号
     * @param siteId    站点编号
     * @return List<Stirng> list中第一个为上一个，第二个为下一个 。如果没有上一个或下一为null
     * @author 许保吉
     * @date 2013-5-17
     */
    public List<String> findPreAndNextRelicTotalCode(String totalCode,
                                                     String siteId);

    /**
     * 根据条件查询上一个文物与下一个文物的totalCode
     *
     * @param totalCode 总登记号
     * @param relics    过滤后的结果集
     * @param siteId    站点编号
     * @return 返回对象数组
     * @author 王耕
     * @date 2014-10-20
     */
    public List<String> findPreAndNextByConditions(String totalCode, List<Relic> relics,
                                                   String siteId);


    /**
     * 查询一个站点下所有有标签的文物信息 （为手持机设备提供文物数据）
     *
     * @param siteId 站点编号
     * @return List<RelicVo> 文物信息列表
     * @author 许保吉
     * @date 2013-5-22
     */
    public List<RelicVo> findHasTagRelics(String siteId);

    /**
     * 添加文物基本信息
     *
     * @param relic 文物基本信息（不需要携带 relic 实体中set集合属性的值）
     * @return void
     * @author 许保吉
     * @date 2013-5-15
     */
    public void addRelic(Relic relic);

    /**
     * 更新文物基本信息
     *
     * @param relic 文物基本信息（不需要携带 relic 实体中set集合属性的值）
     * @return void
     * @author 许保吉
     * @date 2013-5-15
     */
    public void updateRelic(Relic relic);

    /**
     * 根据站点id及总登记号，更新文物为有标签状态
     *
     * @param siteId        站点编号
     * @param totalCodeList 文物总登记号列表
     * @return void
     * @author 许保吉
     * @date 2013-5-23
     */
    public void updateRelicTag(String siteId, List<String> totalCodeList);

    /**
     * 添加文物记录信息（事故记录，现状记录，修复记录，鉴定记录）
     *
     * @param o 文物记录实体
     * @return void
     * @author 许保吉
     * @date 2013-5-20
     */
    public void addRelicRecord(Object o);


    /**
     * 判断 文物总登记号是否可用
     *
     * @param totalCode 文物总登记号
     * @param siteId    站点编号
     * @return true ：可用 fase：不可用
     * @author 许保吉
     * @date 2013-5-23
     */
    public boolean isTotalCodeCanUse(String totalCode, String siteId);


    /**
     * 根据文物ID批量更改在库状态
     * TODO 待审核
     *
     * @param relicIds   文物编号
     * @param relicState 文物状态
     * @return void
     * @author xiedeng
     * @date 2013-5-28
     */
    public void updateRelicState(List<Integer> relicIds, int relicState);

    /**
     * 根据编号查询文物信息
     * TODO 待审核
     *
     * @param id 文物编号
     * @return Relic 文物信息
     * @author xiedeng
     * @date 2013-5-28
     */
    public Relic findById(int id);

    void delete(Relic relic);

    /**
     * 修改站点下,一个文物的注销状态
     *
     * @param siteId     站点编号
     * @param relicId    文物ID
     * @param iscanceled 是否注销  false 未注销  true 注销
     * @author xu.baoji
     * @date 2013-9-3
     */
    public void updateRelicCanceledState(String siteId, int relicId, boolean iscanceled);

    /**
     * 处理文物信息 Json ，用于创建 Elasticserach 索引
     *
     * @param relic
     * @return 文物信息 json 字符串
     */
    public String prepareRelicJson(Relic relic);

    /**
     * 验证文物总登记号是否存在
     *
     * @param totalCode 文物总登记号
     * @author chen.yaofei
     * @date 2016-03-21
     */
    public boolean validateByTotalCode(String totalCode);
}
