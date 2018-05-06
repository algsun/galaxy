package com.microwise.blueplanet.service;

import com.microwise.blueplanet.bean.vo.TextureThresholdVO;

import java.util.List;

/**
 * 阈值阈值service
 */
public interface TextureThresholdService {

    /**
     * 添加质地阈值
     *
     * @param textureThresholdVO 质地阈值vo
     * @author liuzhu
     * @date 2016-7-19
     */
    public void insertTextureThreshold(TextureThresholdVO textureThresholdVO);

    /**
     * 查询单个质地阈值对象
     *
     * @param textureThresholdVO 质地阈值vo
     * @return 质地阈值vo
     * @author liuzhu
     * @date 2016-7-19
     */
    public TextureThresholdVO findTextureThreshold(TextureThresholdVO textureThresholdVO);

    /**
     * 查询质地阈值lists
     *
     * @param textureThresholdId 质地id
     * @return 质地阈值vo
     * @author liuzhu
     * @date 2016-7-19
     */
    public List<TextureThresholdVO> findTextureThresholds(int textureThresholdId);

    /**
     * 删除质地阈值
     *
     * @param id m_texture_threshold表的id
     * @author liuzhu
     * @date 2016-7-20
     */
    public void deleteTextureThreshold(int id, int textureId, int sensorId);
}
