package com.microwise.api.blackhole;

import com.microwise.api.bean.ApiResult;
import com.microwise.api.bean.UserVo;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.UserService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiedeng
 * @date 13-12-6
 */
@Controller
public class UserAction {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/blackhole/getUsers", method = RequestMethod.GET)
    @ApiOperation(value = "获取所有用户信息", position = 2, httpMethod = "GET",
            notes = "获取所有用户信息"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Void.class),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @ResponseBody
    public ApiResult<Object> getUsers() {
        List<User> users = userService.findUserList();
        List<UserVo> userVos = new ArrayList<>();
        for (User user : users) {
            userVos.add(new UserVo(user.getId(), user.getUserName(), user.getEmail()));
        }
        return getResult(true, "获取所有用户信息成功", userVos);
    }

    /**
     * 获取返回的json   数据
     *
     * @param success 是否成功 true 成功， false 失败
     * @param msg     返回的信息
     * @param data    返回的数据
     * @return json数据
     */
    private ApiResult<Object> getResult(boolean success, String msg, Object data) {
        ApiResult<Object> apiResult = new ApiResult<Object>();
        apiResult.setSuccess(success);
        apiResult.setMessage(msg);
        apiResult.setData(data);
        return apiResult;
    }

}
