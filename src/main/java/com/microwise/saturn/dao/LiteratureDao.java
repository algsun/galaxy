package com.microwise.saturn.dao;

import com.microwise.saturn.bean.Literature;

import java.util.List;

/**
 * Created by lijianfei on 15-3-16.
 */
public interface LiteratureDao {

    /**
     * 添加文献资料
     *
     * @param literature
     */
    public void save(Literature literature);

    /**
     * 更新文献资料
     *
     * @param literature
     */
    public void update(Literature literature);

    /**
     * 查询所有文献资料
     *
     * @return
     */
    public List<Literature> findAll(String siteId);

    /**
     * 查询文献资料
     *
     * @param literatureId
     * @return
     */
    public Literature findById(int literatureId);

    /**
     * 删除文献资料
     *
     * @param literatureId
     */
    public void delete(int literatureId);


    public List<Literature> findLiteratures(String siteId, String title, String keywords, int index, int pageSize);

    public int countLiteratures(String siteId, String title, String keywords);
}
