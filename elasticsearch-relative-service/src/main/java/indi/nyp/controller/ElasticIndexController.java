package indi.nyp.controller;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.AliasMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @program: springcloud-family-parent
 * @description:
 * @author: Mr.Niu
 * @create: 2021-08-19 22:32
 **/
@RestController
@Slf4j
public class ElasticIndexController {
    @Autowired
    @Qualifier(value = "dick es client")
    RestHighLevelClient client;

    @GetMapping(value = "/createIndex")
    public void createIndex(@RequestParam String indexName) {
        CreateIndexRequest createIdxReq = new CreateIndexRequest(indexName);
        CreateIndexResponse createIndexResponse = null;
        try {
            createIndexResponse = client.indices().create(createIdxReq, RequestOptions.DEFAULT);
            boolean acknowledged = createIndexResponse.isAcknowledged();
            System.out.println(acknowledged);
            //如果是spring创建的bean不需要关闭，关闭就没了
//            client.close();
        } catch (IOException e) {
            log.error("create index error", e);
        }


    }

    @GetMapping(value = "/getIndexInfo")
    public void getIndexInfo(@RequestParam String indexName) {
        GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
        GetIndexResponse getIndexResponse = null;
        try {
            getIndexResponse = client.indices().get(getIndexRequest, RequestOptions.DEFAULT);
            Map<String, List<AliasMetadata>> aliases = getIndexResponse.getAliases();

            log.info(aliases.toString());
            client.close();
        } catch (IOException e) {
            log.error("get indexInfo failed", e);
        }


    }

    @GetMapping(value = "/deleteIndex")
    public void deleteIndex(@RequestParam String indexName) {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
        AcknowledgedResponse delete = null;
        try {
            delete = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            boolean acknowledged = delete.isAcknowledged();
            System.out.println(acknowledged);
            client.close();
        } catch (IOException e) {
            log.error("delete index error", e);
        }


    }


}
