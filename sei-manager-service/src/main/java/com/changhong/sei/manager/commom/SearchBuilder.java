package com.changhong.sei.manager.commom;

import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.util.JsonUtils;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 实现功能：ES查询Builder
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-10-27 01:15
 */
public class SearchBuilder {
    /**
     * 高亮前缀
     */
    private static final String HIGHLIGHTER_PRE_TAGS = "<mark>";
    /**
     * 高亮后缀
     */
    private static final String HIGHLIGHTER_POST_TAGS = "</mark>";

    private SearchRequest searchRequest;
    private SearchSourceBuilder searchBuilder;
    private RestHighLevelClient client;

    private SearchBuilder(SearchRequest searchRequest, SearchSourceBuilder searchBuilder, RestHighLevelClient client) {
        this.searchRequest = searchRequest;
        this.searchBuilder = searchBuilder;
        this.client = client;
    }

    public SearchRequest getSearchRequest() {
        return searchRequest;
    }

    public void setSearchRequest(SearchRequest searchRequest) {
        this.searchRequest = searchRequest;
    }

    public SearchSourceBuilder getSearchBuilder() {
        return searchBuilder;
    }

    public void setSearchBuilder(SearchSourceBuilder searchBuilder) {
        this.searchBuilder = searchBuilder;
    }

    public RestHighLevelClient getClient() {
        return client;
    }

    public void setClient(RestHighLevelClient client) {
        this.client = client;
    }

    /**
     * 生成SearchBuilder实例
     *
     * @param client
     * @param indexName
     */
    public static SearchBuilder builder(RestHighLevelClient client, String indexName) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(searchSourceBuilder);
        return new SearchBuilder(searchRequest, searchSourceBuilder, client);
    }

    /**
     * 生成SearchBuilder实例
     *
     * @param client
     */
    public static SearchBuilder builder(RestHighLevelClient client) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(searchSourceBuilder);
        return new SearchBuilder(searchRequest, searchSourceBuilder, client);
    }

    /**
     * 设置索引名
     *
     * @param indices 索引名数组
     */
    public SearchBuilder setIndices(String... indices) {
        if (ArrayUtils.isNotEmpty(indices)) {
            searchRequest.indices(indices);
        }
        return this;
    }

    /**
     * 生成queryStringQuery查询
     *
     * @param queryStr 查询关键字
     */
    public SearchBuilder setStringQuery(String queryStr) {
        QueryBuilder queryBuilder;
        if (StringUtils.isNotBlank(queryStr)) {
            queryBuilder = QueryBuilders.queryStringQuery(queryStr);
        } else {
            queryBuilder = QueryBuilders.matchAllQuery();
        }
        searchBuilder.query(queryBuilder);
        return this;
    }

    /**
     * 设置分页
     *
     * @param page  当前页数
     * @param limit 每页显示数
     */
    public SearchBuilder setPage(Integer page, Integer limit) {
        setPage(page, limit, false);
        return this;
    }

    /**
     * 设置分页
     *
     * @param page           当前页数
     * @param limit          每页显示数
     * @param trackTotalHits 分页总数是否显示所有条数，默认只显示10000
     */
    public SearchBuilder setPage(Integer page, Integer limit, boolean trackTotalHits) {
        if (page != null && limit != null) {
            searchBuilder.from((page - 1) * limit)
                    .size(limit);
            if (trackTotalHits) {
                searchBuilder.trackTotalHits(trackTotalHits);
            }

        }
        return this;
    }

    /**
     * 增加排序
     *
     * @param field 排序字段
     * @param order 顺序方向
     */
    public SearchBuilder addSort(String field, SortOrder order) {
        if (StringUtils.isNotBlank(field) && order != null) {
            searchBuilder.sort(field, order);
        }
        return this;
    }

    /**
     * 设置高亮
     *
     * @param preTags  高亮处理前缀
     * @param postTags 高亮处理后缀
     */
    public SearchBuilder setHighlight(String field, String preTags, String postTags) {
        if (StringUtils.isNotBlank(field) && StringUtils.isNotBlank(preTags) && StringUtils.isNotBlank(postTags)) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field(field)
                    .preTags(preTags)
                    .postTags(postTags);
            searchBuilder.highlighter(highlightBuilder);
        }
        return this;
    }

    /**
     * 设置是否需要高亮处理
     *
     * @param isHighlighter 是否需要高亮处理
     */
    public SearchBuilder setIsHighlight(Boolean isHighlighter) {
        if (Objects.nonNull(isHighlighter) && isHighlighter) {
            this.setHighlight("*", HIGHLIGHTER_PRE_TAGS, HIGHLIGHTER_POST_TAGS);
        }
        return this;
    }

    /**
     * 设置查询路由
     *
     * @param routing 路由数组
     */
    public SearchBuilder setRouting(String... routing) {
        if (ArrayUtils.isNotEmpty(routing)) {
            searchRequest.routing(routing);
        }
        return this;
    }

    /**
     * 返回结果 SearchResponse
     */
    public SearchResponse get() throws IOException {
        return client.search(searchRequest, RequestOptions.DEFAULT);
    }

    /**
     * 返回列表结果 List<JsonNode>
     */
    public List<HashMap<String, Object>> getList() throws IOException {
        return getList(this.get().getHits());
    }

    /**
     * 返回分页结果 PageResult<JSONObject>
     */
    public PageResult<HashMap<String, Object>> getPage() throws IOException {
        return this.getPage(null, null);
    }

    /**
     * 返回分页结果 PageResult<JSONObject>
     *
     * @param page  当前页数
     * @param limit 每页显示
     */
    public PageResult<HashMap<String, Object>> getPage(Integer page, Integer limit) throws IOException {
        this.setPage(page, limit);

        SearchResponse response = this.get();
        SearchHits searchHits = response.getHits();
        long totalCnt = searchHits.getTotalHits().value;

        List<HashMap<String, Object>> list = getList(searchHits);
        PageResult<HashMap<String, Object>> pageResult = new PageResult<>();
        pageResult.setRows(list);
        pageResult.setRecords(totalCnt);
        pageResult.setPage(page);

        // 计算总页数
        int totalPage = totalCnt % limit == 0 ? (int) (totalCnt / limit) : ((int) (totalCnt / limit) + 1);
        pageResult.setTotal(totalPage);
        return pageResult;
    }

    /**
     * 返回JSON列表数据
     */
    private List<HashMap<String, Object>> getList(SearchHits searchHits) {
        List<HashMap<String, Object>> list = new ArrayList<>();
        if (searchHits != null) {
            searchHits.forEach(item -> {
                HashMap<String, Object> jsonNode = JsonUtils.fromJson(item.getSourceAsString(), HashMap.class);
                jsonNode.put("id", item.getId());

                Map<String, HighlightField> highlightFields = item.getHighlightFields();
                if (highlightFields != null) {
                    populateHighLightedFields(jsonNode, highlightFields);
                }
                list.add(jsonNode);
            });
        }
        return list;
    }

    /**
     * 组装高亮字符
     *
     * @param result          目标对象
     * @param highlightFields 高亮配置
     */
    private <T> void populateHighLightedFields(T result, Map<String, HighlightField> highlightFields) {
        for (HighlightField field : highlightFields.values()) {
            try {
                String name = field.getName();
                if (!name.endsWith(".keyword")) {
                    if (result instanceof ObjectNode) {
                        ((ObjectNode) result).put(field.getName(), concat(field.fragments()));
                    } else {
                        PropertyUtils.setProperty(result, field.getName(), concat(field.fragments()));
                    }
                }
            } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
                throw new ElasticsearchException("failed to set highlighted value for field: " + field.getName()
                        + " with value: " + Arrays.toString(field.getFragments()), e);
            }
        }
    }

    /**
     * 拼凑数组为字符串
     */
    private String concat(Text[] texts) {
        StringBuffer sb = new StringBuffer();
        for (Text text : texts) {
            sb.append(text.toString());
        }
        return sb.toString();
    }
}
