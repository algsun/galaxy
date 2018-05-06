package com.microwise.blackhole.sys;

import com.microwise.blackhole.bean.AreaCodeCN;
import com.microwise.blackhole.service.AreaCodeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用来解析 areaCode 的工具类
 *
 * @author xubaoji
 * @date 2012-11-29
 * @check 2012-11-29 zhangpeng svn:493
 */
public class AreaCodeUtil {

    /**
     * 注入 行政地区 service
     */
    @Autowired
    private AreaCodeService areaCodeService;

    /**
     * 用来缓存行政地区关系的map 集合
     */
    private static Map<Integer, List<Integer>> maps;

    /**
     * 解析当前地区编号 组装 当前地区的所有下级地区列表
     */
    private static void resolvingAreacode(Integer areaCode,
                                          List<Integer> allSubordinates) {
        List<Integer> sonList = maps.get(areaCode);
        if (sonList != null) {
            for (Integer i : sonList) {
                allSubordinates.add(i);
                resolvingAreacode(i, allSubordinates);
            }
        }
    }

    /**
     * 获得给定地区行政编号的地区的所有下级地区编号列表
     */
    public static List<Integer> getAllSubordinates(Integer areaCode) {
        List<Integer> allSubordinates = new ArrayList<Integer>();
        resolvingAreacode(areaCode, allSubordinates);
        return allSubordinates;
    }

    /**
     * 初始化所有areaCode
     */
    public void init() {
        maps = new HashMap<Integer, List<Integer>>();
        List<AreaCodeCN> areaCodeCNs = areaCodeService.findAllArea();

        for (AreaCodeCN areaCodeCN : areaCodeCNs) {
            List<Integer> sonList = new ArrayList<Integer>();
            for (AreaCodeCN areaCodeCN1 : areaCodeCNs) {
                if (areaCodeCN1.getParentAreaCodeCN() != null
                        && areaCodeCN.getAreaCode() == areaCodeCN1
                        .getParentAreaCodeCN().getAreaCode()) {
                    sonList.add(areaCodeCN1.getAreaCode());
                }
            }
            maps.put(areaCodeCN.getAreaCode(), sonList);
        }
    }

}
