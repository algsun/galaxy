package com.microwise.orion.action.relic;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.*;
import com.microwise.orion.service.RelicService;
import com.microwise.orion.service.RoveService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionLoggerAction;
import com.opensymphony.xwork2.Action;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.InetAddress;
import java.util.Date;

/**
 * 记录信息添加Action
 * 包括文物现状，鉴定记录，修复记录，事故记录，流传经历
 *
 * @author wanggeng.
 * @date: 13-6-3 下午1:15
 * @check @gaohui #4046 2013-06-05
 */
@Beans.Action
@Orion
public class UpdateRelicRecordInfoAction extends OrionLoggerAction {

    /**
     * 档案
     */
    @Autowired
    private RelicService relicService;

    /**
     * 文物流传经历service
     */
    @Autowired
    private RoveService roveService;

    //input
    /**
     * 文物ID
     */
    private int relicId;

    /**
     * 站点
     */
    private String siteId = Sessions.createByAction().currentSiteId();

    //文物现状描述信息
    /**
     * 现状详情
     */
    private String quoInfo;
    /**
     * 现状日期
     */
    private Date quoDate;

    //文物鉴定记录
    /**
     * 鉴定意见
     */
    private String expertOpinion;
    /**
     * 鉴定人
     */
    private String examiner;
    /**
     * 鉴定时间
     */
    private Date appraisalDate;

    //文物修复记录信息
    /**
     * 修复详情
     */
    private String restoreInfo;
    /**
     * 承制人
     */
    private String restorers;
    /**
     * 修复时间
     */
    private Date restoreDate;

    //文物事故记录信息
    /**
     * 事故详情
     */
    private String accidentInfo;
    /**
     * 事故时间
     */
    private Date accidentDate;

    //流传经历记录信息
    /**
     * 流传经历信息
     */
    private String roveInfo;
    /**
     * 流传时间时间
     */
    private Date roveDate;

    //output
    /**
     * 档案
     */
    private Relic relic;
    /**
     * 页面内跳转参数
     */
    private String pageNum;

    private int index;

    /**
     * 是否已开启全文检索
     */
    private Boolean fullTextSearchEnable = Boolean.parseBoolean(ConfigFactory.getInstance().getConfig("config.properties")
            .get("orion.relic.fullTextSearch.enable"));

    /**
     * 全文检索ip
     */
    private String ip = ConfigFactory.getInstance().
            getConfig(Constants.Config.CONFIG_PROPERTIES_URL).
            get("orion.relic.fullTextSearch.ip");
    /**
     * 全文检索端口
     */
    private Integer port = Integer.parseInt(ConfigFactory.getInstance().
            getConfig(Constants.Config.CONFIG_PROPERTIES_URL).
            get("orion.relic.fullTextSearch.port"));

    /**
     * 增加记录信息，文物现状记录
     *
     * @return
     */
    public String addStatusquoRecordInfo() {
        Client client = null;
        try {
            relic = relicService.findRelicByRelicId(relicId, siteId);
            if (quoInfo == null) {
                ActionMessage.createByAction().fail("文物现状信息不能为空");
                return Action.SUCCESS;
            }
            if (quoDate == null) {
                ActionMessage.createByAction().fail("文物现状日期不能为空");
                return Action.SUCCESS;
            }
            if (relic == null) {
                ActionMessage.createByAction().fail("文物信息有误");
                return Action.SUCCESS;
            }

            StatusQuo sta = new StatusQuo();
            sta.setQuoInfo(quoInfo);
            sta.setQuoDate(quoDate);
            sta.setRelic(relic);
            relicService.addRelicRecord(sta);


            if (fullTextSearchEnable) {
                client = TransportClient.builder().build()
                        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), port));
                client.prepareUpdate("galaxy", siteId, "" + relic.getId()).setDoc(relicService.prepareRelicJson(relic)).get();
            }

            ActionMessage.createByAction().success("文物现状保存成功");
            log("藏品信息", "编辑文物现状，文物名称：" + relic.getName());
        } catch (Exception e) {
            ActionMessage.createByAction().fail("文物现状保存失败");
            logFailed("文物现状保存失败", "文物现状保存失败");
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.close();
            }
        }

        return Action.SUCCESS;
    }

    /**
     * 增加记录信息，鉴定记录
     *
     * @return
     */
    public String addAppraisalRecordInfo() {
        Client client = null;
        try {
            relic = relicService.findRelicByRelicId(relicId, siteId);
            if (expertOpinion == null) {
                ActionMessage.createByAction().fail("鉴定记录详情不能为空");
                return Action.SUCCESS;
            }
            if (examiner == null) {
                ActionMessage.createByAction().fail("鉴定人不能空");
                return Action.SUCCESS;
            }
            if (appraisalDate == null) {
                ActionMessage.createByAction().fail("鉴定日期不能为空");
                return Action.SUCCESS;
            }
            Appraisal appr = new Appraisal();
            appr.setExpertOpinion(expertOpinion);
            appr.setExaminer(examiner);
            appr.setAppraisalDate(appraisalDate);
            appr.setRelic(relic);
            relicService.addRelicRecord(appr);

            if (fullTextSearchEnable) {
                client = TransportClient.builder().build()
                        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), port));
                client.prepareUpdate("galaxy", siteId, "" + relic.getId()).setDoc(relicService.prepareRelicJson(relic)).get();
            }

            ActionMessage.createByAction().success("鉴定记录保存成功");
            log("藏品信息", "文物名称：" + relic.getName());
        } catch (Exception e) {
            ActionMessage.createByAction().fail("鉴定记录保存失败");
            logFailed("鉴定记录保存失败", "鉴定记录保存失败");
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.close();
            }
        }
        return Action.SUCCESS;
    }

    /**
     * 增加记录信息，修复记录
     *
     * @return
     */
    public String addRestoreRecordInfo() {
        Client client = null;
        try {
            relic = relicService.findRelicByRelicId(relicId, siteId);
            if (restoreInfo == null) {
                ActionMessage.createByAction().fail("修复记录详情不能为空");
                return Action.SUCCESS;
            }
            if (restorers == null) {
                ActionMessage.createByAction().fail("承制人不能为空");
                return Action.SUCCESS;
            }
            if (restoreDate == null) {
                ActionMessage.createByAction().fail("修复日期不能为空");
                return Action.SUCCESS;
            }
            Restore rest = new Restore();
            rest.setRestoreInfo(restoreInfo);
            rest.setRestorers(restorers);
            rest.setRestoreDate(restoreDate);
            rest.setRelic(relic);
            relicService.addRelicRecord(rest);

            if (fullTextSearchEnable) {
                client = TransportClient.builder().build()
                        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), port));
                client.prepareUpdate("galaxy", siteId, "" + relic.getId()).setDoc(relicService.prepareRelicJson(relic)).get();
            }

            ActionMessage.createByAction().success("修复记录保存成功");
            log("藏品信息", "文物名称：" + relic.getName());
        } catch (Exception e) {
            ActionMessage.createByAction().success("修复记录保存失败");
            logFailed("修复记录保存失败", "修复记录保存失败");
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.close();
            }
        }

        return Action.SUCCESS;
    }

    /**
     * 增加记录信息，事故记录
     *
     * @return
     */
    public String addAccidentRecordInfo() {
        Client client = null;
        try {
            relic = relicService.findRelicByRelicId(relicId, siteId);
            if (accidentInfo == null) {
                ActionMessage.createByAction().fail("事故详情不能为空");
                return Action.SUCCESS;
            }
            if (accidentDate == null) {
                ActionMessage.createByAction().fail("事故日期不能为空");
                return Action.SUCCESS;
            }
            Accident acc = new Accident();
            acc.setAccidentInfo(accidentInfo);
            acc.setAccidentDate(accidentDate);
            acc.setRelic(relic);
            relicService.addRelicRecord(acc);

            if (fullTextSearchEnable) {
                client = TransportClient.builder().build()
                        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), port));
                // 重建索引
                client.prepareUpdate("galaxy", siteId, "" + relic.getId()).setDoc(relicService.prepareRelicJson(relic)).get();
            }

            ActionMessage.createByAction().success("事故记录保存成功");
            log("藏品信息", "文物名称：" + relic.getName());
        } catch (Exception e) {
            ActionMessage.createByAction().fail("是不记录保存失败");
            logFailed("事故记录保存失败", "事故记录保存失败");
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.close();
            }
        }

        return Action.SUCCESS;
    }

    /**
     * 增加流传经历记录信息
     *
     * @return
     */
    public String addRoveInfo() {

        Client client = null;
        try {
            relic = relicService.findRelicByRelicId(relicId, siteId);
            if (roveInfo == null) {
                ActionMessage.createByAction().fail("流传经历信息不能为空");
                return Action.SUCCESS;
            }
            if (roveDate == null) {
                ActionMessage.createByAction().fail("流传日期不能为空");
                return Action.SUCCESS;
            }
            Rove rove = new Rove();
            rove.setRoveInfo(roveInfo);
            rove.setRoveDate(roveDate);
            rove.setRelic(relic);
            roveService.addRelicRove(rove);

            if (fullTextSearchEnable) {
                client = TransportClient.builder().build()
                        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), port));
                // 重建索引
                client.prepareUpdate("galaxy", siteId, "" + relic.getId()).setDoc(relicService.prepareRelicJson(relic)).get();
            }

            ActionMessage.createByAction().success("流传经历保存成功");
            log("藏品信息", "文物名称：" + relic.getName());
        } catch (Exception e) {
            ActionMessage.createByAction().fail("流传经历保存失败");
            logFailed("流传经历保存失败", "流传经历保存失败");
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.close();
            }
        }
        return Action.SUCCESS;
    }

    public int getRelicId() {
        return relicId;
    }

    public void setRelicId(int relicId) {
        this.relicId = relicId;
    }

    public String getQuoInfo() {
        return quoInfo;
    }

    public void setQuoInfo(String quoInfo) {
        this.quoInfo = quoInfo;
    }

    public Date getQuoDate() {
        return quoDate;
    }

    public void setQuoDate(Date quoDate) {
        this.quoDate = quoDate;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public Relic getRelic() {
        return relic;
    }

    public void setRelic(Relic relic) {
        this.relic = relic;
    }

    public String getExpertOpinion() {
        return expertOpinion;
    }

    public void setExpertOpinion(String expertOpinion) {
        this.expertOpinion = expertOpinion;
    }

    public String getExaminer() {
        return examiner;
    }

    public void setExaminer(String examiner) {
        this.examiner = examiner;
    }

    public Date getAppraisalDate() {
        return appraisalDate;
    }

    public void setAppraisalDate(Date appraisalDate) {
        this.appraisalDate = appraisalDate;
    }

    public String getRestoreInfo() {
        return restoreInfo;
    }

    public void setRestoreInfo(String restoreInfo) {
        this.restoreInfo = restoreInfo;
    }

    public String getRestorers() {
        return restorers;
    }

    public void setRestorers(String restorers) {
        this.restorers = restorers;
    }

    public Date getRestoreDate() {
        return restoreDate;
    }

    public void setRestoreDate(Date restoreDate) {
        this.restoreDate = restoreDate;
    }

    public String getAccidentInfo() {
        return accidentInfo;
    }

    public void setAccidentInfo(String accidentInfo) {
        this.accidentInfo = accidentInfo;
    }

    public Date getAccidentDate() {
        return accidentDate;
    }

    public void setAccidentDate(Date accidentDate) {
        this.accidentDate = accidentDate;
    }

    public String getRoveInfo() {
        return roveInfo;
    }

    public void setRoveInfo(String roveInfo) {
        this.roveInfo = roveInfo;
    }

    public Date getRoveDate() {
        return roveDate;
    }

    public void setRoveDate(Date roveDate) {
        this.roveDate = roveDate;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
