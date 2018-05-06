package com.microwise.orion.dao;

import com.microwise.common.sys.hibernate.BaseDao;
import com.microwise.orion.bean.Texture;

import java.util.List;

/**
 * 文物质地dao
 * 
 * @author xubaoji
 * @date 2013-5-17
 *
 * @check 2013-06-04 zhangpeng svn:3519
 */
public interface TextureDao extends BaseDao<Texture> {

	/**
	 * 查询所有 文物 质地
	 * 
	 * @author 许保吉
	 * @date 2013-5-17
	 * 
	 * @return List<Texture> 质地列表
	 */
	public List<Texture> findAllTexture();

    /**
     * 查询本次输入质地是否存在
     */
    public  boolean isTextureExist(String name);

    /**
     * 删除质地
     */
    public void delete(String name);

    public int findIdByName(String name);
}
