package com.microwise.blueplanet.quartz;

import com.microwise.blueplanet.bean.po.DeviceFormulaPO;
import com.microwise.blueplanet.bean.vo.ProductStateVO;
import com.microwise.blueplanet.bean.vo.ProductVO;
import com.microwise.blueplanet.bean.vo.SiteVO;
import com.microwise.blueplanet.service.DeviceService;
import com.microwise.blueplanet.service.FormulaService;
import com.microwise.blueplanet.service.SiteService;
import com.microwise.blueplanet.util.BPHttpApiClient;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 上传状态、获取已变更设备公式 Job
 *
 * @author liuzhu
 * @date 15-1-21
 */
 @Deprecated
public class UploadStateJob implements Job {

    /**
     * 日志
     */
    public static final Logger log = LoggerFactory.getLogger(UploadStateJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ApplicationContext appContext = (ApplicationContext) jobExecutionContext.getTrigger().getJobDataMap().get("appContext");

        //spring获取相关
        DeviceService deviceService = appContext.getBean(DeviceService.class);
        SiteService siteService = appContext.getBean(SiteService.class);
        FormulaService formulaService = appContext.getBean(FormulaService.class);

        //获取所有站点
        List<SiteVO> sites = siteService.findAllSite();
        BPHttpApiClient bpHttpApiClient = new BPHttpApiClient();


        //上传状态
        for (SiteVO siteVO : sites) {
            //根据站点获取有产品序列号并且没有超时的设备状态
            List<ProductStateVO> productStateList = deviceService.findProductStateVO(siteVO.getSiteId());
            if (!productStateList.isEmpty()) {
                //上传至云端(galaxy_online)
                boolean result = bpHttpApiClient.uploadState(productStateList);
                if (result) {
                    log.info("上传状态成功,站点名称为：" + siteVO.getSiteName());
                } else {
                    log.error("上传状态失败,站点名称为：" + siteVO.getSiteName());
                }
            }

            //无产品序列号
            List<ProductVO> productVOs = new ArrayList<ProductVO>();
            List<ProductStateVO> productStateVOListNoSn = deviceService.findProductStateVONoSn(siteVO.getSiteId());
            for (ProductStateVO productStateVO : productStateVOListNoSn) {
                ProductVO productVO = new ProductVO();
                productVO.setSiteId(siteVO.getSiteId());
                productVO.setSiteName(siteVO.getSiteName());
                productVO.setSerialNumber(productStateVO.getSerialNumber());
                productVO.setType(productStateVO.getNodeType());
                productVO.setState(productStateVO);
                productVOs.add(productVO);
            }

            //无产品序列号，状态上传
            if (!productVOs.isEmpty()) {
                boolean result = bpHttpApiClient.uploadStateNOSn(productVOs);
                if (result) {
                    log.info("上传状态成功,站点名称为(无产品序列号)：" + siteVO.getSiteName());
                } else {
                    log.error("上传状态失败,站点名称为(无产品序列号)：" + siteVO.getSiteName());
                }
            }
        }

        //获取已变更公式
        for (SiteVO siteVO : sites) {
            DeviceFormulaPO[] deviceFormulaPOs = bpHttpApiClient.getOnlineFormulasUpdateToLocalTable(siteVO.getSiteId());
            if (deviceFormulaPOs != null) {
                for (DeviceFormulaPO deviceFormulaPO : deviceFormulaPOs) {
                    //根据产品序列号获取设备id
                    String deviceId = deviceService.findDeviceId(deviceFormulaPO.getSerialNumber());
                    //根据相关参数修改设备公式
                    formulaService.saveOrUpdateParamsByDeviceId(deviceId, deviceFormulaPO.getSensorId(), deviceFormulaPO.getFormulaParams());
                }
            }
            log.info("下载公式成功,站点名称为：" + siteVO.getSiteName());
        }
    }
}
