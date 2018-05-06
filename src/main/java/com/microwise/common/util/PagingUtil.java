package com.microwise.common.util;

/**
 * 分页工具类
 *
 * @author gaohui
 * @date 13-1-30 15:03
 */
public class PagingUtil {

    /**
     * 根据数据条数, 计算总页数
     *
     * @param count       数据条数
     * @param sizePerPage 每页的条数
     * @return
     */
    public static int pagesCount(int count, int sizePerPage) {
        int pageCount = count / sizePerPage;
        if (count % sizePerPage > 0) {
            ++pageCount;
        }
        return pageCount;
    }
}
