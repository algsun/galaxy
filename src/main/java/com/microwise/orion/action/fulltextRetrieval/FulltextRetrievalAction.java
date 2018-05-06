package com.microwise.orion.action.fulltextRetrieval;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.PagingUtil;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.service.RelicService;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.vo.FullTextSearchRelicVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.util.*;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * 全文检索
 *
 * @author li.jianfei
 * @date 2014-04-16
 * @check 2014-4-22 xuyuexi liuzhu #8456
 */
@Beans.Action
@Orion
public class FulltextRetrievalAction {


    public static final Logger log = LoggerFactory.getLogger(FulltextRetrievalAction.class);

    /**
     * 检索关键字
     */
    private String key;

    /**
     * 分页第几页
     */
    private Integer index = 1;

    /**
     * 分页页数
     */
    private int pageCount;

    /**
     * 数据条数
     */
    private int dataCount;

    /**
     * 耗时时间
     */
    private double consumTime;

    /**
     * 档案
     */
    @Autowired
    private RelicService relicService;

    /**
     * 查询显示结果
     */
    private List<FullTextSearchRelicVo> fullTextSearchRelicVos;

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

    @Route("/orion/fulltextRetrieval")
    public String search() {
        String siteId = Sessions.createByAction().currentSiteId();
        long startTime = System.currentTimeMillis();
        Client client = null;
        try {
            if (StringUtils.isBlank(key)) {
                return Results.ftl("/orion/pages/relic/search-relic");
            }

            //查询的开始下标
            int startIndex = (index - 1) * Constants.SIZE_PER_PAGE;

            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), port));

            boolean hasIndex = client.admin().indices().exists(new IndicesExistsRequest("galaxy")).actionGet().isExists();
            if (!hasIndex) {
                client.admin().indices().prepareCreate("galaxy").get();
            }
            String indices[] = new String[]{"galaxy"};
            hasIndex = client.admin().indices().typesExists(new TypesExistsRequest(indices, siteId)).actionGet().isExists();
            if (!hasIndex) {


                // 设置日期格式
                XContentBuilder mapping = jsonBuilder()
                        .startObject()
                        .startObject(siteId)
                        .startObject("properties")
                        .startObject("制作日期")
                        .field("type", "date")
                        .field("format", "yyyy-MM-dd||YYYY/MM/DD||MM/DD/YYYY||MMM-yy")
                        .field("store", true)
                        .endObject()
                        .startObject("入藏时间")
                        .field("type", "date")
                        .field("format", "yyyy-MM-dd||YYYY/MM/DD||MM/DD/YYYY||MMM-yy")
                        .field("store", true)
                        .endObject()
                        .startObject("出土时间").
                                field("type", "date")
                        .field("format", "yyyy-MM-dd||YYYY/MM/DD||MM/DD/YYYY||MMM-yy")
                        .field("store", true)
                        .endObject()
                        .startObject("制卡日期")
                        .field("type", "date")
                        .field("format", "yyyy-MM-dd||YYYY/MM/DD||MM/DD/YYYY||MMM-yy")
                        .field("store", true)
                        .endObject()
                        .endObject()
                        .endObject()
                        .endObject();

                PutMappingRequest mappingRequest = Requests.putMappingRequest("galaxy").type(siteId).source(mapping);
                client.admin().indices().putMapping(mappingRequest).get();

                int count = relicService.findRelicsCount(siteId);
                int pageCount = PagingUtil.pagesCount(count, 500);
                for (int i = 1; i <= pageCount; i++) {
                    List<Relic> relicList = relicService.findRelics(siteId, i, 500);
                    for (Relic relic : relicList) {
                        IndexResponse response = client.prepareIndex("galaxy", siteId, "" + relic.getId())
                                .setSource(relicService.prepareRelicJson(relic)).get();
                    }

                }
            }


            //获取总条数
            CountResponse response = client.prepareCount("galaxy")
                    .setTypes(siteId)
                    .setQuery(QueryBuilders.queryStringQuery(key))
                    .execute()
                    .actionGet();
            int count = (int) response.getCount();
            if (count == 0) {
                return Results.ftl("/orion/pages/relic/search-relic");
            }
            dataCount = count;
            pageCount = PagingUtil.pagesCount(count, Constants.SIZE_PER_PAGE);

            SearchResponse searchResponse = client.prepareSearch("galaxy").setTypes(siteId)
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setHighlighterQuery(QueryBuilders.queryStringQuery(key))
                    .setPostFilter(QueryBuilders.queryStringQuery(key))
                    .addHighlightedField("ID编号").addHighlightedField("总登记号").addHighlightedField("编目号")
                    .addHighlightedField("分类号").addHighlightedField("名称").addHighlightedField("时代")
                    .addHighlightedField("级别").addHighlightedField("质地").addHighlightedField("件数")
                    .addHighlightedField("区域").addHighlightedField("站点").addHighlightedField("文物状态")
                    .addHighlightedField("是否有电子标签").addHighlightedField("是否注销").addHighlightedField("重量")
                    .addHighlightedField("尺寸").addHighlightedField("来源").addHighlightedField("照片号")
                    .addHighlightedField("拓片号").addHighlightedField("简述").addHighlightedField("著录和文献")
                    .addHighlightedField("出土地或产地").addHighlightedField("档案编号").addHighlightedField("原名")
                    .addHighlightedField("作者").addHighlightedField("色泽").addHighlightedField("制作时间")
                    .addHighlightedField("用途").addHighlightedField("作者小传").addHighlightedField("附属物")
                    .addHighlightedField("入藏时间").addHighlightedField("入馆凭证号").addHighlightedField("形状内容描述")
                    .addHighlightedField("征集经过").addHighlightedField("鉴藏印记").addHighlightedField("备注")
                    .addHighlightedField("附录").addHighlightedField("制卡人").addHighlightedField("制卡日期")
                    .addHighlightedField("附件").addHighlightedField("藏品卡编号").addHighlightedField("出土时间")
                    .addHighlightedField("铭文题跋.info").addHighlightedField("铭文题跋.path")
                    .addHighlightedField("流传经历.roveInfo").addHighlightedField("流传经历.roveDate")
                    .addHighlightedField("鉴定意见.expertOpinion").addHighlightedField("鉴定意见.examiner")
                    .addHighlightedField("鉴定意见.appraisalDate")
                    .addHighlightedField("修复装裱复制记录.restorers").addHighlightedField("修复装裱复制记录.restoreDate")
                    .addHighlightedField("修复装裱复制记录.restoreInfo")
                    .addHighlightedField("现状记录.quoInfo").addHighlightedField("现状记录.quoDate")
                    .addHighlightedField("绘图(或拓片).rubbingCode").addHighlightedField("绘图(或拓片).producer")
                    .addHighlightedField("绘图(或拓片).rubbingDate").addHighlightedField("绘图(或拓片).ratio")
                    .addHighlightedField("绘图(或拓片).path")
                    .addHighlightedField("挂接文档.attachmentDate").addHighlightedField("挂接文档.path")
                    .addHighlightedField("照片.path").addHighlightedField("标签.name")
                    .addHighlightedField("事故记录.accidentInfo").addHighlightedField("事故记录.accidentDate")
                    .setHighlighterPreTags("<span style=\"color:red\">")
                    .setHighlighterPostTags("</span>")
                    .setExplain(true)
                    .setFrom(startIndex)
                    .setSize(Constants.SIZE_PER_PAGE)
                    .execute()
                    .actionGet();
            this.fullTextSearchRelicVos = convertSearchResult(searchResponse);
            setTotalCodes(this.fullTextSearchRelicVos);
        } catch (Exception e) {
            log.error("藏品信息全文检索异常", e);
        } finally {
            if (client != null) {
                client.close();
            }
        }
        long endTime = System.currentTimeMillis();
        consumTime = ((double) (endTime - startTime)) / 1000;
        return Results.ftl("/orion/pages/relic/search-relic");
    }

    private void setTotalCodes(List<FullTextSearchRelicVo> fullTextSearchRelicVos) {
        List<String> totalCodes = new ArrayList<String>();
        for (FullTextSearchRelicVo fullTextSearchRelicVo : fullTextSearchRelicVos) {
            totalCodes.add(fullTextSearchRelicVo.getTotalCode());
        }
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.setAttribute("totalCodes", totalCodes);
    }

    private List<FullTextSearchRelicVo> convertSearchResult(SearchResponse searchResponse) {
        List<FullTextSearchRelicVo> fullTextSearchRelicVos = new ArrayList<FullTextSearchRelicVo>();
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        for (SearchHit searchHit : searchHits) {
            String str = searchHit.getSourceAsString();
            if (!StringUtils.isNotBlank(str)) {
                return fullTextSearchRelicVos;
            }
            Map<String, Object> properties = new Gson().fromJson(str, new TypeToken<Map<String, Object>>() {
            }.getType());
            FullTextSearchRelicVo fullTextSearchRelicVo = new FullTextSearchRelicVo();
            fullTextSearchRelicVo.setId(properties.get("ID编号").toString());
            fullTextSearchRelicVo.setTotalCode(properties.get("总登记号").toString());
            fullTextSearchRelicVo.setName(properties.get("名称").toString());
            fullTextSearchRelicVo.setLevel(properties.get("级别").toString());
            fullTextSearchRelicVo.setTexture(properties.get("质地").toString());
            fullTextSearchRelicVo.setEra(properties.get("时代").toString());
            fullTextSearchRelicVo.setCatalogCode(properties.get("编目号").toString());
            fullTextSearchRelicVo.setTypeCode(properties.get("分类号").toString());
            fullTextSearchRelicVo.setStatus(properties.get("文物状态").toString());
            fullTextSearchRelicVo.setRelicLocation(StringUtils.isBlank(properties.get("区域").toString()) ?
                    "暂无" : properties.get("区域").toString());
            ArrayList<Map<String, String>> photoPath = (ArrayList<Map<String, String>>) properties.get("照片");
            if (photoPath != null && !photoPath.isEmpty()) {
                Map<String, String> path = photoPath.get(0);
                fullTextSearchRelicVo.setPhoto(Sessions.createByAction().getGalaxyResourceURL() + "/orion/images"
                        + "/" + properties.get("站点").toString()
                        + "/" + properties.get("ID编号").toString()
                        + "/" + path.get("path"));
            }
            Map<String, String> hightlightTexts = new HashMap<String, String>();
            Iterator highlightFieldIterator = searchHit.getHighlightFields().entrySet().iterator();
            while (highlightFieldIterator.hasNext()) {
                Map.Entry entry = (Map.Entry) highlightFieldIterator.next();
                String key = entry.getKey().toString();
                int index = key.lastIndexOf(".");
                if (index > 0) {
                    key = key.substring(0, index);
                }
                HighlightField highlightField = (HighlightField) entry.getValue();
                String value = highlightField.getFragments()[0].toString();
                hightlightTexts.put(key, value);
            }
            fullTextSearchRelicVo.setHighlightTexts(hightlightTexts);
            fullTextSearchRelicVos.add(fullTextSearchRelicVo);
        }
        return fullTextSearchRelicVos;
    }

    public List<FullTextSearchRelicVo> getFullTextSearchRelicVos() {
        return fullTextSearchRelicVos;
    }

    public static Logger getLog() {
        return log;
    }

    public void setFullTextSearchRelicVos(List<FullTextSearchRelicVo> fullTextSearchRelicVos) {
        this.fullTextSearchRelicVos = fullTextSearchRelicVos;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public double getConsumTime() {
        return consumTime;
    }

    public void setConsumTime(double consumTime) {
        this.consumTime = consumTime;
    }
}
