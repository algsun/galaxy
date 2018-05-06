package com.microwise.blackhole.util;

import com.google.common.base.Strings;
import com.microwise.blackhole.bean.User;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * 系统管理相关工具类
 *
 * @author wanggeng
 * @date 13-8-19 上午10:14
 */
public class BlackholeUtil {

    /**
     * 根据userList中的user部分进行分组，组装Map
     *
     * @param userList 用户List
     * @return map
     * @author wang.geng
     * @date 2013年8月19日
     */
    public static Map<String, List<User>> getSelectionList(List<User> userList) {

        //获取userList中所有的部门
        List<String> departmentNames = new ArrayList<String>();
        for (User user : userList) {
            if (user.getDepartment() != null) {
                if (departmentNames.size() == 0) {
                    departmentNames.add(user.getDepartment().getName());
                } else {
                    if (!departmentNames.contains(user.getDepartment().getName())) {
                        departmentNames.add(user.getDepartment().getName());
                    }
                }
            }
        }

        Map<String, List<User>> userDepartmentMap = new LinkedHashMap<String, List<User>>();

        for (String depName : departmentNames) {
            List<User> users = new ArrayList<User>();
            for (User u : userList) {
                if (u.getDepartment() != null) {
                    if (depName.equals(u.getDepartment().getName())) {
                        users.add(u);
                    }
                }
            }
            userDepartmentMap.put(depName, users);
        }

        List<User> nullDepartmentUser = new ArrayList<User>();
        for (User user : userList) {
            if (user.getDepartment() == null) {
                nullDepartmentUser.add(user);
            }
        }
        userDepartmentMap.put("其它", nullDepartmentUser);
        return userDepartmentMap;
    }


    /**
     * 获得传入Action的所有任务指派人的id
     *
     * @param userIds 用户id
     * @return 任务指派人ID集合
     * @author wang.geng
     * @date 2013年8月19日
     */
    public static List<Integer> getDesigneeIdList(String userIds) {
        List<Integer> designeeIds = new ArrayList<Integer>();
        String ids[] = userIds.split(",");
        for (String id : ids) {
            designeeIds.add(Integer.parseInt(id.trim()));
        }
        return designeeIds;
    }
}
