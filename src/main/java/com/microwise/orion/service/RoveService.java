package com.microwise.orion.service;

import com.microwise.orion.bean.Rove;

import java.util.List;

/**
 * 文物流传经历service
 *
 * @author xubaoji
 * @date 2013-6-4
 * @check 2013-06-04 zhangpeng svn:4046
 */
public interface RoveService {

    /**
     * 添加文物流传经历
     *
     * @param rove 流传经历实体
     * @return void
     * @author 许保吉
     * @date 2013-5-27
     */
    public void addRelicRove(Rove rove);

    /***
     * 通过id 查询文物流传经历 信息
     *
     * @param id
     *            流传经历id 编号
     *
     * @author 许保吉
     * @date 2013-6-4
     *
     * @return Rove 流传经历实体
     */
    public Rove findById(Integer id);

}
