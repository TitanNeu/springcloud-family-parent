package indi.nyp.properties;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class BulkProcessorProperties {
    private String bulkProcessorName = "bulk-processor-name";
    private int bulkActions = 500;
    private long bulkSize = 5L;
    private int concurrentRequests = 1;
    private long flushInterval = 5L;
    private long retryInterval = 5L;
    private int maxNumberOfRetries = 3;

}
