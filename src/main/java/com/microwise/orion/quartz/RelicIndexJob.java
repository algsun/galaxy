package com.microwise.orion.quartz;

import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.service.LogicGroupService;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.Constants;
import com.microwise.common.util.PagingUtil;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.service.RelicService;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * 重建文物索引 job
 *
 * @author li.jianfei
 * @date 2014-04-24
 */
public class RelicIndexJob implements Job {

    /**
     * 文物 Service
     */
    @Autowired
    private RelicService relicService;

    @Autowired
    private LogicGroupService logicGroupService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Client client = null;
        try {

            // 全文检索ip
            String ip = ConfigFactory.getInstance().
                    getConfig(Constants.Config.CONFIG_PROPERTIES_URL).
                    get("orion.relic.fullTextSearch.ip");

            // 全文检索端口
            Integer port = Integer.parseInt(ConfigFactory.getInstance().
                    getConfig(Constants.Config.CONFIG_PROPERTIES_URL).
                    get("orion.relic.fullTextSearch.port"));

            relicService = (RelicService) jobExecutionContext.getTrigger().getJobDataMap()
                    .get(RelicIndexQuartz.RELIC_SERVICE);

            logicGroupService = (LogicGroupService) jobExecutionContext.getTrigger().getJobDataMap()
                    .get(RelicIndexQuartz.LOGICGROUP_SERVICE);


            // 创建索引
            List<LogicGroup> logicGroups = logicGroupService.findTopLogicGroupList();
            //  String siteId = logicGroupService.findTopLogicGroupList().get(0).getSite().getSiteId();
            for (LogicGroup logicGroup : logicGroups) {

                // 建立连接
                client = TransportClient.builder().build()
                        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), port));

                boolean hasIndex = client.admin().indices().exists(new IndicesExistsRequest("galaxy")).actionGet().isExists();
                String indices[] = new String[]{"galaxy"};
                boolean hasIndex1 = client.admin().indices().typesExists(new TypesExistsRequest(indices, logicGroup.getSite().getSiteId())).actionGet().isExists();
                if (!hasIndex1) {
                    if (!hasIndex) {
                        client.admin().indices().prepareCreate("galaxy").execute().actionGet();
                    }
                    XContentBuilder mapping = jsonBuilder()
                            .startObject()
                            .startObject(logicGroup.getSite().getSiteId())
                            .startObject("properties")
                            .startObject("制卡日期").field("type", "date").field("format", "yyyy-MM-dd||YYYY/MM/DD||MM/DD/YYYY||MMM-yy")
                            .field("store", true).endObject()
                            .startObject("入藏时间").field("type", "date").field("format", "yyyy-MM-dd||YYYY/MM/DD||MM/DD/YYYY||MMM-yy")
                            .field("store", true).endObject()
                            .startObject("出土时间").field("type", "date").field("format", "yyyy-MM-dd||YYYY/MM/DD||MM/DD/YYYY||MMM-yy")
                            .field("store", true).endObject()
                            .startObject("制卡日期").field("type", "date").field("format", "yyyy-MM-dd||YYYY/MM/DD||MM/DD/YYYY||MMM-yy")
                            .field("store", true).endObject()
                            .endObject()
                            .endObject()
                            .endObject();

                    PutMappingRequest mappingRequest = Requests.putMappingRequest("galaxy").type(logicGroup.getSite().getSiteId()).source(mapping);
                    client.admin().indices().putMapping(mappingRequest).actionGet();
                }

                // 删除所有索引
                client.prepareDelete().get();

                int count = relicService.findRelicsCount(logicGroup.getSite().getSiteId());
                int pageCount = PagingUtil.pagesCount(count, Constants.SIZE_PER_PAGE);
                for (int i = 1; i <= pageCount; i++) {
                    List<Relic> relicList = relicService.findRelics(logicGroup.getSite().getSiteId(), i, Constants.SIZE_PER_PAGE);
                    for (Relic relic : relicList) {
                        client.prepareIndex("galaxy", logicGroup.getSite().getSiteId(), "" + relic.getId()).setSource(relicService.prepareRelicJson(relic)).execute().actionGet();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.close();
            }
        }

    }
}
