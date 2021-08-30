package indi.nyp.controller;

import indi.nyp.service.KafkaUsingCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {
    @Autowired
    KafkaUsingCase kfkCase;

    @GetMapping(value = "/kafka/consumer")
    public void testConsumer() {
        kfkCase.consumerFunction();
    }

    @GetMapping(value = "/kafka/producer")
    public void testProducer() {
        kfkCase.producerFunction();
    }
}
