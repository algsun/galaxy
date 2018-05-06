package com.microwise.uma.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.microwise.api.v1.JsonDateValueProcessor;

import java.util.Date;

/**
 * 1.单程 3.往 4.返
 *
 * @author gaohui
 * @date 13-4-26 15:39
 */
public class UserActionBean extends AbstractUserActionBean {

    // 发生时间
    private Date occurrenceTime;

    @JsonSerialize(using = JsonDateValueProcessor.class)
    public Date getOccurrenceTime() {
        return occurrenceTime;
    }

    public void setOccurrenceTime(Date occurrenceTime) {
        this.occurrenceTime = occurrenceTime;
    }
}
