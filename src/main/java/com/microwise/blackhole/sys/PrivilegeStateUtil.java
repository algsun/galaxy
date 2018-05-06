package com.microwise.blackhole.sys;

import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 权限状态算法util ：①权限类型：state二进制位上是1的代表拥有某个权限 ②权限状态：state字段，根据权限类型复合出的用于数据库存储的状态标识
 *
 * @author zhangpeng
 * @date 2012-12-6
 * @check 2012-12-13 xubaoji svn:805
 */
public class PrivilegeStateUtil {

    /**
     * 必选（1二进制：0001）
     */
    public static Integer PRIVILEGE_STATE_REUQIRED = 1;

    /**
     * 可选（2二进制：0010）
     */
    public static Integer PRIVILEGE_STATE_OPTIONAL = 2;

    /**
     * 超级管理员独占（4二进制：0100）
     */
    public static Integer PRIVILEGE_STATE_SUPERMAN_UNIQUE = 4;

    /**
     * 普通用户拥有（8二进制：1000）
     */
    public static Integer PRIVILEGE_STATE_USER = 8;

    /**
     * 超级管理员拥有的权限（16二进制：10000）
     */
    public static Integer PRIVILEGE_STATE_IS_SUPERMAN = 16;

    /**
     * 站点管理员拥有的权限（32二进制：100000）
     */
    public static Integer PRIVILEGE_STATE_IS_MANAGER = 32;

    /**
     * 访客拥有的权限（64二进制：1000000）
     */
    public static Integer PRIVILEGE_STATE_IS_GUEST = 64;

    /**
     * 权限类型列表
     */
    public static List<Integer> PRIVILEGE_STATE_LIST;

    static {
        PRIVILEGE_STATE_LIST = new ArrayList<Integer>();
        PRIVILEGE_STATE_LIST.add(PRIVILEGE_STATE_REUQIRED);
        PRIVILEGE_STATE_LIST.add(PRIVILEGE_STATE_OPTIONAL);
        PRIVILEGE_STATE_LIST.add(PRIVILEGE_STATE_SUPERMAN_UNIQUE);
        PRIVILEGE_STATE_LIST.add(PRIVILEGE_STATE_USER);
        PRIVILEGE_STATE_LIST.add(PRIVILEGE_STATE_IS_SUPERMAN);
        PRIVILEGE_STATE_LIST.add(PRIVILEGE_STATE_IS_MANAGER);
        PRIVILEGE_STATE_LIST.add(PRIVILEGE_STATE_IS_GUEST);
    }

    /**
     * 根据权限类型列表获取你需要的权限状态
     *
     * @param list 权限类型列表
     * @return int 权限状态
     * @author zhangpeng
     * @date 2012-12-6
     */
    public static int createState(List<Integer> list) {
        int result = 0;
        for (Integer i : list) {
            result = result | i;
        }
        return result;
    }

    /**
     * 判断一个权限状态是否包含某个权限类型
     *
     * @param state     被判断的权限状态
     * @param stateType 是否包含的权限类型
     * @return true 包含 false 不包含
     * @author zhangpeng
     * @date 2012-12-6
     */
    public static boolean checkState(int state, int stateType) {
        return (state & stateType) == stateType;
    }

    /**
     * 给指定权限状态去掉某种权限类型
     *
     * @param state 权限状态
     * @param list  要去掉的权限类型列表
     * @return 去掉指定权限类型之后的权限状态
     * @author zhangpeng
     * @date 2012-12-6
     */
    public static int removeState(int state, List<Integer> list) {
        for (int i : list) {
            state = state & (~i);
        }
        return state;
    }

    /**
     * 求指定状态与指定权限类型列表按位或的权限状态set集合
     *
     * @param state 指定状态类型
     * @param list  权限类型列表
     * @return Set<Integer> 指定状态与指定权限类型列表按位或的权限状态set集合
     * @author zhangpeng
     * @date 2012-12-7
     */
    public static Set<Integer> getCombination(int state, List<Integer> list) {
        Set<Integer> set = new HashSet<Integer>();
        for (Integer aList : list) {
            List<Integer> iList = new ArrayList<Integer>();
            iList.add(state);
            iList.add(aList);
            set.add(createState(iList));
        }
        return set;
    }

    /**
     * 根据权限类型集合获取所有可以产生的权限状态组合
     *
     * @param list 权限类型列表
     * @return List<Integer> 所有可以组合出的状态列表
     * @author zhangpeng
     * @date 2012-12-7
     */
    public static List<Integer> getStateCombinations(List<Integer> list) {
        List<Integer> resList = new ArrayList<Integer>();
        long n = (long) Math.pow(2, list.size());
        List<Integer> fList;
        for (long j = 0L; j < n; j++) {
            fList = new ArrayList<Integer>();
            for (int i = 0; i < list.size(); i++) {
                if ((j >>> i & 1) == 1) {
                    fList.add(list.get(i));
                }
            }
            resList.add(createState(fList));
        }
        resList.remove(Integer.valueOf(0));
        return resList;
    }

    /**
     * 获取指定Set<Integer>[]数组的元素交集
     *
     * @param set Set<Integer>[]数组
     * @return Set<Integer> 元素交集set
     * @author zhangpeng
     * @date 2012-12-7
     */
    public static Set<Integer> getSetIntersections(Set<Integer>... set) {
        Set<Integer> result = new HashSet<Integer>();
        for (Set<Integer> s : set) {
            result.addAll(s);
        }
        for (Set<Integer> aSet1 : set) {
            for (Set<Integer> aSet : set) {
                result = Sets.intersection(result,
                        Sets.intersection(aSet1, aSet));
            }
        }
        return result;
    }

    /**
     * 按照权限类型获取可能在数据库出现的状态Set
     *
     * @param type 权限类型
     * @return 状态Set
     * @author zhangpeng
     * @date 2012-12-10
     */
    public static Set<Integer> getDBFuzzyState(Integer type) {
        return getCombination(type, getStateCombinations(PRIVILEGE_STATE_LIST));
    }

    /**
     * 获取过滤指定权限类型的权限状态Set
     *
     * @param type 要过滤的权限类型
     * @return Set<Integer> 过滤指定类型权限之后数据库可能存在的权限类型
     * @author zhangpeng
     * @date 2013-1-22
     */
    public static Set<Integer> getStateByFilter(Integer type) {
        List<Integer> pList = new ArrayList<Integer>();
        pList.addAll(PrivilegeStateUtil.PRIVILEGE_STATE_LIST);
        pList.remove(type);
        List<Integer> rList = PrivilegeStateUtil.getStateCombinations(pList);
        return new HashSet<Integer>(rList);
    }

}
