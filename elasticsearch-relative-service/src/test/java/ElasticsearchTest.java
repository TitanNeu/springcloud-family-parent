import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.AliasMetadata;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.common.settings.Settings;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
public class ElasticsearchTest {
    private RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200)));
    private String indexName = "java_api_test";
    private String delIndexName = "java_api_test2";
    @Test
    public void getClient() throws IOException {

        System.out.println(restHighLevelClient); //测试获取http连接
        restHighLevelClient.close();
    }

    @Test
    public void createIndex() throws IOException { //创建索引
        CreateIndexRequest createIdxReq = new CreateIndexRequest(indexName);

        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIdxReq, RequestOptions.DEFAULT);

        boolean acknowledged = createIndexResponse.isAcknowledged();
        System.out.println(acknowledged);


        restHighLevelClient.close();

    }

    @Test
    public void DelIndex() throws IOException {
        DeleteIndexRequest del = new DeleteIndexRequest(indexName); //delete请求

        AcknowledgedResponse delete = restHighLevelClient.indices().delete(del, RequestOptions.DEFAULT);

        System.out.println("删除状态"+delete.isAcknowledged());

        restHighLevelClient.close();
    }

    @Test
    public void searchIndex() throws IOException { //搜索索引信息
        GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
        GetIndexResponse getIndexResponse = restHighLevelClient.indices().get(getIndexRequest, RequestOptions.DEFAULT);

        Map<String, List<AliasMetadata>> aliases = getIndexResponse.getAliases();
        Map<String, MappingMetadata> mappings = getIndexResponse.getMappings();
        Map<String, Settings> settings = getIndexResponse.getSettings();

        log.info(aliases.toString());
        log.info(mappings.toString());
        log.info(settings.toString());

        restHighLevelClient.close();
    }

    @Test
    public void createAndDelIndex() throws IOException { //创建并删除索引
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(delIndexName);

        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(delIndexName);

        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);//创建索引
        if(createIndexResponse.isAcknowledged()) {
            log.info("索引创建成功");
            AcknowledgedResponse delete = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            log.info("删除状态"+ delete.isAcknowledged());
        }
    }
}