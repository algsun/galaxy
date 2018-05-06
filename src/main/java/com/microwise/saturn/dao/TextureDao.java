package com.microwise.saturn.dao;

import com.microwise.saturn.bean.TextureVO;

import java.util.List;

/**
 *
 */
public interface TextureDao {

    /**
     * 添加材质
     *
     * @param name 材质名称
     */
    public void insert(String name);

    /**
     * 查询所有材质
     *
     * @return
     */
    public List<TextureVO> findAll();

    /**
     * 删除材质
     *
     * @param id
     */
    public void deleteById(int id);

    /**
     * 修改材质
     *
     * @param id
     * @param name
     */
    public void updateById(int id, String name);

    /**
     * 是否存在
     *
     * @param name
     * @return
     */
    public boolean isExits(String name);
}
