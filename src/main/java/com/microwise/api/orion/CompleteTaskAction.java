package com.microwise.api.orion;

import com.microwise.api.bean.ApiResult;
import com.microwise.common.sys.annotation.Beans;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Beans.Action
@Controller
public class CompleteTaskAction {

    @Autowired
    private TaskService taskService;

    private boolean success;

    /**
     * 完成任务
     */
    @RequestMapping(value = "/orion/complete", method = RequestMethod.POST)
    @ApiOperation(value = "审批", position = 5, httpMethod = "POST",
            notes = "审批"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Void.class),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @ResponseBody
    public ApiResult<String> execute(@RequestParam String taskId, @RequestParam String type,
                                     @RequestParam boolean value) {
        ApiResult<String> result = new ApiResult<String>();
        try {
            if (type == null) {
                taskService.complete(taskId);
            } else {
                taskService.complete(taskId, convertToSuitableType(type, value));
            }
            success = true;
        } catch (Exception e) {
            success = false;
        }
        String message = success ? "操作成功" : "操作失败";
        result.setSuccess(success);
        result.setMessage(message);
        return result;
    }

    /**
     * 将 var 中的变量转换为合适的类型
     */
    private Map<String, Object> convertToSuitableType(String type, boolean value) {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put(type, value);
        return variables;
    }

}
