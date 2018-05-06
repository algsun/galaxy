package com.microwise.orion.action.relic;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.service.RelicService;
import com.microwise.orion.sys.Orion;
import com.opensymphony.xwork2.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文物总登记号验证
 *
 * @author chenyaofei
 * @date 16-3-21
 */
@Beans.Action
@Orion
public class ValidateTotalCodeAction {
    public static final Logger log = LoggerFactory.getLogger(ValidateTotalCodeAction.class);
    @Autowired
    RelicService relicService;
    /**
     * 文物总登记号
     */
    private String totalCode;
    /**
     * 返回参数
     */
    Map<String, Object> dataMap = new HashMap<String, Object>();

    public String execute() {
        try {
            boolean validate = relicService.validateByTotalCode(totalCode);
            if (validate) {
                dataMap.put("data", "true");
            } else {
                dataMap.put("data", "false");
            }
        } catch (Exception e) {
            log.error("文物总登记号验证失败", e);
            dataMap.put("data", "error");
        }

        return Action.SUCCESS;
    }

    public String getTotalCode() {
        return totalCode;
    }

    public void setTotalCode(String totalCode) {
        this.totalCode = totalCode;
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }
}
