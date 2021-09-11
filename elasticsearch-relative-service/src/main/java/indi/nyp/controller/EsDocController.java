package indi.nyp.controller;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.index.IndexRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class EsDocController {
    @Autowired
    @Qualifier("default bulk processor")
    BulkProcessor processor;

    @PostMapping(value = "/es/bulkCreateDoc")
    public String createDoc() {

        for(int i = 0; i < 20; i++) {
            IndexRequest indexRequest = new IndexRequest();
            indexRequest.index("java_api_test");
            Map<String,Object> map = new HashMap<>();
            map.put("name", "dick"+i);
            map.put("age", i);
            map.put("length", 18+i);
            map.put("currentTime",new Date());

            processor.add(indexRequest.source(map));
        }

        return "success";
    }
}
