package com.microwise.orion.service;

import com.microwise.orion.bean.Photo;

/**
 * 文物相片service
 *
 * @author xubaoji
 * @date 2013-5-21
 * @check 2013-06-04 zhangpeng svn:3576
 */
public interface PhotoService {

    /**
     * 添加文物相片
     *
     * @param photo 相片实体
     * @return void
     * @author 许保吉
     * @date 2013-5-21
     */
    public void addPhoto(Photo photo);

    /**
     * 通过相片编号查找相片
     *
     * @param photoId 相片id 编号
     * @return Photo 相片实体
     * @author zhangpeng
     * @date 2013-6-3
     */
    public Photo findById(Integer photoId);

    /**
     * 通过相片编号删除相片
     *
     * @param photo 相片
     * @return void
     * @author 许保吉
     * @date 2013-5-21
     */
    public void delete(Photo photo);

}
