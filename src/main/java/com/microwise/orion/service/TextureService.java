package com.microwise.orion.service;

import com.microwise.orion.bean.Texture;

import java.util.List;

/**
 * 质地service
 * 
 * @author xubaoji
 * @date  2013-5-17
 *
 * @check 2013-06-04 zhangpeng svn:3510
 */
public interface TextureService {

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

    Integer delete(String name);

    Integer save(Texture texture);
}
