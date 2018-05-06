package com.microwise.blackhole.dao;

import com.microwise.blackhole.bean.Post;
import com.microwise.common.sys.hibernate.BaseDao;

import java.util.List;

/**
 * 新闻
 *
 * @author gaohui
 * @date 13-5-8 17:21
 */
public interface PostDao extends BaseDao<Post> {

    public List<Post> findLatest(int scope, int max);

    int findCount(int scope);

    List<Post> findLatest(int scope, int start, int max);
}
