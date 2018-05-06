package com.microwise.blackhole.action.log;

import com.google.common.base.Strings;
import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.bean.Subsystem;
import com.microwise.blackhole.bean.SysLog;
import com.microwise.blackhole.service.LogService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.blackhole.sys.Subsystems;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.opensymphony.xwork2.Action;
import org.apache.struts2.ServletActionContext;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 * 日志查询action 包括（跳转，分页查询）
 * </pre>
 *
 * @author Wang Yunlong
 * @date 12-11-19
 * @time 上午9:02
 * @check @gaohui #297  2012-11-27
 */

@Beans.Action("querySysLogAction")
@Blackhole
public class QuerySysLogAction {
    /**
     * 查询默认的开始时间
     */
    private static final String DEFAULT_START_TIME = "2012-10-30";
    /**
     * 第一页
     */
    private static final int FIRST_PAGE = 1;
    /**
     * 没有归属站点和当前站点 返回值
     */
    private static final int FAIL = -1;
    /**
     * 分页每页显示数量
     */
    private static final int MAX = Constants.SIZE_PER_PAGE;
    @Autowired
    private LogService logService;
    //input
    /**
     * 当前页
     */
    private int index;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 姓名
     */
    private String userName;
    /**
     * 操作名称
     */
    private String operateName;
    /**
     * 操作站点
     */
    private int subsystem;
    /**
     * 操作结果
     */
    private int result;
    /**
     * 页面是否显示插叙条件状态
     */
    private boolean inputView;
    /**
     * 业务系统列表
     */
    private List<Subsystem> subsystems;


    //output
    /**
     * 返回页面的消息
     */
    private String message = "";
    /**
     * 当前查询日志列表
     */
    private List<SysLog> logs;
    /**
     * 当前查询数量
     */
    private int count;

    /**
     * 分页查询日志
     *
     * @return
     */
    //暂时有问题 @gaohui 2012-12-10
    //@RequiresPermissions({"blackhole:log:query"})
    public String execute() {
        SysLog sysLog = createSysLogForInput();
        if (sysLog.getLogicGroupId() == FAIL) {
            message = "您还没有选择站点";
            return Action.SUCCESS;
        }

        if (Strings.isNullOrEmpty(startTime) && Strings.isNullOrEmpty(endTime)) {
            try {
                getCountPage(logService.findLogListCount(sysLog));
                logs = logService.findLogList(sysLog, this.index, MAX);
            } catch (Exception e) {
                message = "系统异常";
                return Action.ERROR;
            }
        } else {
            try {
                Date start = null;
                Date end = null;
                if (!Strings.isNullOrEmpty(startTime)) {
                    start = DateTime.parse(startTime).toDate();
                } else {
                    start = DateTime.parse(DEFAULT_START_TIME).toDate();
                }
                if (!Strings.isNullOrEmpty(endTime)) {
                    end = DateTime.parse(endTime).toDate();
                    end = DateTime.now().withMillis(end.getTime()).plusDays(1).toDate();
                } else {
                    end = DateTime.now().toDate();
                }
                getCountPage(logService.findLogListCount(sysLog, start, end));
                logs = logService.findLogList(sysLog, start, end, this.index, MAX);
            } catch (Exception e) {
                message = "系统出现异常,请稍后重试！";
                return Action.ERROR;
            }
        }
        setSubsystemList();
        return Action.SUCCESS;
    }

    /**
     * 跳转在日志查询页面
     *
     * @return
     */
    //暂时有问题 @gaohui 2012-12-10
    //@RequiresPermissions({"blackhole:log:query"})
    public String view() {
        SysLog sysLog = new SysLog();
        int logicGroupId = getSysLogCurrentLogicGroup();
        if (logicGroupId == FAIL) {
            message = "您还没有选择站点";
        } else {
            index = FIRST_PAGE;
            sysLog.setLogicGroupId(logicGroupId);
            getCountPage(logService.findLogListCount(sysLog));
            logs = logService.findLogList(sysLog, DateTime.now().minusMonths(1).toDate(), DateTime.now().toDate(), FIRST_PAGE, MAX);
            inputView = false;
        }
        setSubsystemList();
        return Action.SUCCESS;
    }

    /**
     * 跟据查询数量获取页数
     *
     * @param total
     */
    private void getCountPage(int total) {
        if (total % MAX == 0) {
            count = total / MAX;
        } else {
            count = total / MAX + 1;
        }
    }


    /**
     * 获取页面传递的查询参数
     *
     * @return 查询条件
     */
    private SysLog createSysLogForInput() {
        SysLog sysLog = new SysLog();
        sysLog.setUserName(Strings.emptyToNull(userName));
        sysLog.setSubsystemId(subsystem);
        sysLog.setLogName(Strings.emptyToNull(operateName));
        sysLog.setLogicGroupId(getSysLogCurrentLogicGroup());
        switch (result) {
            case 0:
                sysLog.setLogState(null);
                break;
            case 1:
                sysLog.setLogState(false);
                break;
            case 2:
                sysLog.setLogState(true);
                break;
        }
        return sysLog;
    }

    /**
     * 获取当前站点id
     *
     * @return 站点id
     */
    private int getSysLogCurrentLogicGroup() {
        LogicGroup logicGroup = new Sessions(ServletActionContext.getContext()).currentLogicGroup();
        return logicGroup == null ? FAIL : logicGroup.getId();
    }

    private void setSubsystemList() {
        subsystems = Subsystems.all();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<SysLog> getLogs() {
        return logs;
    }

    public void setLogs(List<SysLog> logs) {
        this.logs = logs;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getSubsystem() {
        return subsystem;
    }

    public void setSubsystem(int subsystem) {
        this.subsystem = subsystem;
    }

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isInputView() {
        return inputView;
    }

    public void setInputView(boolean inputView) {
        this.inputView = inputView;
    }

    public List<Subsystem> getSubsystems() {
        return subsystems;
    }

    public void setSubsystems(List<Subsystem> subsystems) {
        this.subsystems = subsystems;
    }
}
