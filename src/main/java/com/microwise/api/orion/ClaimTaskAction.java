package com.microwise.api.orion;

import com.microwise.api.bean.ApiResult;
import com.microwise.common.sys.annotation.Beans;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Beans.Action
@Controller
public class ClaimTaskAction {

    @Autowired
    private TaskService taskService;


    @RequestMapping(value = "/orion/claimTask/{taskId}/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "签收", position = 4, httpMethod = "GET",
            notes = "签收"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Void.class),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @ResponseBody
    public ApiResult<String> execute(@PathVariable String taskId, @PathVariable String userId) {
        ApiResult<String> result = new ApiResult<String>();
        boolean success = true;
        try {
            taskService.claim(taskId, userId);
        } catch (Exception e) {
            success = false;
        }
        String message = success ? "签收成功" : "签收失败";
        result.setSuccess(success);
        result.setMessage(message);
        return result;
    }
}
