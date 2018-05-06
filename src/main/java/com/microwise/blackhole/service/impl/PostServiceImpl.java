package com.microwise.blackhole.service.impl;

import com.microwise.blackhole.bean.Post;
import com.microwise.blackhole.dao.PostDao;
import com.microwise.blackhole.service.PostService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.common.sys.annotation.Beans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @author gaohui
 * @date 13-5-8 17:28
 */
@Beans.Service
@Blackhole
@Transactional
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    @Override
    public void update(Post post) {
        postDao.update(post);
    }

    @Override
    public void delete(Post post) {
        postDao.delete(post);
    }

    @Override
    public Serializable save(Post post) {
        return postDao.save(post);
    }

    @Override
    public Post findById(Serializable id) {
        return postDao.findById(id);
    }

    @Override
    public List<Post> findLatest(int scope, int max) {
        return postDao.findLatest(scope, max);
    }

    @Override
    public List<Post> findLatest(int scope, int index, int size) {
        return postDao.findLatest(scope, (index - 1) * size, size);
    }

    @Override
    public int findCount(int scope) {
        return postDao.findCount(scope);
    }
}
