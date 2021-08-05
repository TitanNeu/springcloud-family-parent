import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ElascticDocTest
 * @Description TODO
 * @Author niuyp
 * @Date 2021/7/19 16:01
 * @Version 1.0
 **/
@Slf4j
public class ElascticDocTest {
    private String indexName = "java_api_test";
    HttpHost httpHost = new HttpHost("localhost",9200);
    RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient.builder(httpHost));
    @Test
    public void addDic() throws IOException {
        IndexRequest indexReq = new IndexRequest();
        indexReq.index(indexName).id("1002");

        Map<String, Object> map = new HashMap<>();
        map.put("name", "dick");
        map.put("age", 16);
        map.put("gender", "男");

        indexReq.source(map);

        //使用客户端发起文档新增的请求
        IndexResponse indexResponse = restHighLevelClient.index(indexReq, RequestOptions.DEFAULT);

        log.info(indexResponse.getIndex());
        log.info(indexResponse.getId());
        log.info(String.valueOf(indexResponse.getResult()));


        restHighLevelClient.close();


    }
}
