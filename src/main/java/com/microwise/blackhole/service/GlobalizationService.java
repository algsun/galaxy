package com.microwise.blackhole.service;

/**
 * @author chenyaofei
 * @date 16-2-29
 */
public interface GlobalizationService {
    /**
     * 根据语言参数，调用数据库语言环境
     *
     * @param language 语言参数
     * @retrun void
     * @author chenyaofei
     * @date 2016-2-29
     */
    public void changeDisplayByLanguage(String language);
}
