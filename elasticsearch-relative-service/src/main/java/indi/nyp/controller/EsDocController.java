package indi.nyp.controller;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class EsDocController {
    @Autowired
    @Qualifier("default bulk processor")
    BulkProcessor processor;

    @Autowired
    @Qualifier(value = "dick es client")
    RestHighLevelClient client;

    @PostMapping(value = "/es/bulkCreateDoc")
    public String createDoc() {
        String indexName = "java_api_test-000001";
        if (!isExistsIndex(client, indexName)) {
            CreateIndexRequest createIdxReq = new CreateIndexRequest(indexName);
            CreateIndexResponse createIndexResponse = null;
            try {
                createIndexResponse = client.indices().create(createIdxReq, RequestOptions.DEFAULT);
                boolean acknowledged = createIndexResponse.isAcknowledged();
                if(acknowledged) {
                    log.info("created new index {}", indexName);
                }
            } catch (IOException e) {
                log.error("create index {} error", indexName, e);
            }
        }

        for (int i = 0; i < 20; i++) {
            IndexRequest indexRequest = new IndexRequest();
            indexRequest.index(indexName);
            Map<String, Object> map = new HashMap<>();
            map.put("name", "dick" + i);
            map.put("age", i);
            map.put("length", 18 + i);
            map.put("currentTime", new Date());

            processor.add(indexRequest.source(map));
        }

        return "success";
    }

    // 判断该索引是否存在
    private static boolean isExistsIndex(RestHighLevelClient client, String index) {
        GetIndexRequest indexRequest = new GetIndexRequest(index);
        boolean exists = false;
        try {
            exists = client.indices().exists(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("判断索引是否存在时出现问题", e);
        }
        return exists;
    }
}
