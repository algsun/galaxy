package com.microwise.blackhole.service;

import com.microwise.blackhole.bean.Post;

import java.io.Serializable;
import java.util.List;

/**
 * 新闻
 *
 * @author gaohui
 * @date 13-5-8 17:27
 */
public interface PostService {
    /**
     * 修改新闻
     *
     * @param post 新闻对象
     */
    void update(Post post);

    /**
     * 删除新闻
     *
     * @param post 新闻对象
     */
    void delete(Post post);

    /**
     * 发布新闻
     *
     * @param post 新闻对象
     * @return 新闻Id
     */
    Serializable save(Post post);

    /**
     * 根据Id查找新闻
     *
     * @param id 新闻ID
     * @return
     */
    Post findById(Serializable id);

    /**
     * 查询最新的新闻
     *
     * @param scope 可见性(如果大于0, 此参数起作用)
     * @param max   最多几条
     * @return
     */
    List<Post> findLatest(int scope, int max);

    /**
     * 查询最新的新闻
     *
     * @param scope 可见性(如果大于0, 此参数起作用)
     * @param index 第几页(从 1 开始)
     * @param size  一页大小
     * @return
     */
    List<Post> findLatest(int scope, int index, int size);

    /**
     * 查询数目
     *
     * @param scope 可见性(如果大于0, 此参数起作用)
     * @return
     */
    int findCount(int scope);

}
