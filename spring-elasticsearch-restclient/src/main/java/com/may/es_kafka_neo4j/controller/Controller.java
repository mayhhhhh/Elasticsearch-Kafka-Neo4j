package com.may.es_kafka_neo4j.controller;

import com.may.es_kafka_neo4j.Repository.EsRepository;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.io.stream.BytesStreamOutput;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/es")
public class Controller {

    private EsRepository esRepository;

    @Autowired
    private KafkaTemplate<String, byte[]> esKafkaTemplate;
    private static final String TOPIC_ASSET = "asset_trial_34";
    private static final String TOPIC_FINDING = "finding_trial_34";
    private static final String TOPIC_EVENT = "event_trial_34";


    public Controller(EsRepository esRepository) {
        this.esRepository = esRepository;
    }

    @GetMapping("/{index}")
    public String getBookById(@PathVariable String index) throws IOException {
        List<SearchHit> searchHits = esRepository.getByIndex(index);
        String topic = "";
        if(index.equals("asset")){
            topic = TOPIC_ASSET;
        }
        if(index.equals("finding")){
            topic = TOPIC_FINDING;
        }
        if(index.equals("event")){
            topic = TOPIC_EVENT;
        }
        for (SearchHit hit : searchHits) {
            BytesStreamOutput out = new BytesStreamOutput();
            hit.writeTo(out);
            BytesReference bytesReference = out.bytes();
            byte[] ref = BytesReference.toBytes(bytesReference);
//            for (String topic : TOPIC) {
//                ProducerRecord<String, byte[]> rec = new ProducerRecord<String, byte[]>(topic, ref);
                esKafkaTemplate.send(topic, ref);
                System.out.println("sent successfully");
//            }
        }

        return "Published successfully";
    }


}
