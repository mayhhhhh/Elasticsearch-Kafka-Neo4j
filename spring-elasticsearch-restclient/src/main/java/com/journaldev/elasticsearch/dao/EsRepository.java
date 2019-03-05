package com.journaldev.elasticsearch.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Repository;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EsRepository {

    private final String INDEX_ASSET = "asset-v2.0";
    private final String INDEX_FINDING = "finding-v2.0";
    private final String INDEX_EVENT = "";

    //private final String TYPE = "device";

    private RestHighLevelClient restHighLevelClient;
    private ObjectMapper objectMapper;

    public EsRepository( ObjectMapper objectMapper, RestHighLevelClient restHighLevelClient) {
        this.objectMapper = objectMapper;
        this.restHighLevelClient = restHighLevelClient;
    }

    public List<SearchHit> getByIndex(String index) throws IOException {
        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
        SearchRequest searchRequest = null;
        System.out.println("index: " + index);
        if(index.equals("asset")) {
            searchRequest = new SearchRequest(INDEX_ASSET);
            System.out.println("set asset");
        }
        if(index.equals("finding")) {
            searchRequest = new SearchRequest(INDEX_FINDING);
            System.out.println("set finding");
        }
        searchRequest.scroll(scroll);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //searchSourceBuilder.query(matchQuery("title", "Elasticsearch"));
        if(index.equals("finding")){
            searchSourceBuilder.size(10000);
        }
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
        String scrollId = searchResponse.getScrollId();
        SearchHit[] searchHits = searchResponse.getHits().getHits();

        //
        List<SearchHit> allHits = new ArrayList<>();

        int count = 0;
        System.out.println("start time:" + System.currentTimeMillis());
        while (searchHits != null && searchHits.length > 0) {
            for(int i = 0; i < searchHits.length; i++){
                allHits.add(searchHits[i]);
                count++;
            }

            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(scroll);
            searchResponse = restHighLevelClient.searchScroll(scrollRequest);
            scrollId = searchResponse.getScrollId();
            searchHits = searchResponse.getHits().getHits();

            System.out.println("searchHits: " + count);
        }
        System.out.println("end time: " + System.currentTimeMillis());
        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.addScrollId(scrollId);
        ClearScrollResponse clearScrollResponse = restHighLevelClient.clearScroll(clearScrollRequest);
        boolean succeeded = clearScrollResponse.isSucceeded();

        //Map<String, Object> sourceAsMap = searchHits.getSourceAsMap();
        int length = allHits.size();
        System.out.println(length);
        return allHits;
    }

}
