package com.microwise.orion.proxy;

import com.microwise.orion.bean.Relic;

import java.util.List;

/**
 * 文物servcie代理
 *
 * @author liuzhu
 * @date 2016-6-24
 */
public interface RelicProxy {


    /**
     * 根据文物名称、总登记号查找文物个数
     *
     * @param relicNameTotalCode 文物名称、总登记号
     * @param ids                需要过滤的文物id,不过滤传null或空list即可
     * @return 文物总数量
     * @author liuzhu
     * @date 2016-6-29
     */
    public int findRelicsCount(String relicNameTotalCode, List<Integer> ids);

    /**
     * 根据文物名称、总登记号查找文物
     *
     * @param relicNameTotalCode 文物名称、总登记号
     * @param pageIndex          页码
     * @param pageSize           一页显示多少条
     * @param ids                需要过滤的文物id,不过滤传null或空list即可
     * @return 文物list
     * @author liuzhu
     * @date 2016-6-29
     */
    public List<Relic> findRelics(String relicNameTotalCode, int pageIndex, int pageSize, List<Integer> ids);

}
