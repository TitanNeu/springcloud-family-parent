package indi.nyp.configurations;

import indi.nyp.properties.BulkProcessorProperties;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.function.BiConsumer;

@Configuration(proxyBeanMethods = false)
@Slf4j
public class BulkProcessorConfig {
    @Autowired
    @Qualifier(value = "dick es client")
    RestHighLevelClient client;


    @Bean(value = "default bulk processor")
    public BulkProcessor getBulkProcessor(BulkProcessorProperties properties) {

        BulkProcessor.Listener listener = new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                int numberOfActions = request.numberOfActions();
                log.debug("Executing bulk [{}] with {} requests", executionId, numberOfActions);
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                if (response.hasFailures()) {
                    log.warn("Bulk [{}] executed with failures", executionId);
                } else {
                    log.debug("Bulk [{}] completed in {} milliseconds", executionId, response.getTook().getMillis());
                }
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                log.error("Failed to execute bulk", failure);
            }

        };

        /**7.13.1的写法*/
        BulkProcessor.Builder builder = BulkProcessor.builder(
                (request, bulkListener) -> client.bulkAsync(request, RequestOptions.DEFAULT, bulkListener),
                listener, properties.getBulkProcessorName());
        builder.setBulkActions(properties.getBulkActions());
        builder.setBulkSize(new ByteSizeValue(properties.getBulkSize(), ByteSizeUnit.MB));
        builder.setConcurrentRequests(properties.getConcurrentRequests());
        builder.setFlushInterval(TimeValue.timeValueSeconds(properties.getFlushInterval()));
        builder.setBackoffPolicy(BackoffPolicy.constantBackoff(TimeValue.timeValueSeconds(properties.getRetryInterval()), properties.getMaxNumberOfRetries()));

        /**7.10.0的写法*/
//        BiConsumer<BulkRequest, ActionListener<BulkResponse>> bulkConsumer =
//                (request, bulkListener) -> client.bulkAsync(request, RequestOptions.DEFAULT, bulkListener);
//        BulkProcessor.Builder builder = BulkProcessor.builder(bulkConsumer, listener)
//                .setBulkActions(properties.getBulkActions())
//                .setBulkSize(new ByteSizeValue(properties.getBulkSize(), ByteSizeUnit.MB))
//                .setConcurrentRequests(properties.getConcurrentRequests())
//                .setFlushInterval(TimeValue.timeValueSeconds(properties.getFlushInterval()))
//                .setBackoffPolicy(BackoffPolicy.constantBackoff(TimeValue.timeValueSeconds(properties.getRetryInterval()), properties.getMaxNumberOfRetries()));

        return builder.build();

    }
}
