package com.journaldev.elasticsearch.dao;

import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.search.SearchHit;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class kafkaConsumer {

    //    @KafkaListener(topics = "assets", group = "assetsConsumer",
//            containerFactory = "assetsKafkaListenerFactory")
    @KafkaListener(topics = "asset_trial_34",
            containerFactory = "assetsKafkaListenerFactory")
    public void consumeassets(byte[] assetsmsg) {
        consume(assetsmsg, "Assets");
    }



    @KafkaListener(topics = "finding_trial_34",
            containerFactory = "findingsKafkaListenerFactory")
    public void consumefindings(byte[] findingmsg) {
        consume(findingmsg, "Findings");

    }

    @KafkaListener(topics = "event_trial_34",
            containerFactory = "eventsKafkaListenerFactory")
    public void consumeEvents(byte[] eventmsg) {
        consume(eventmsg, "Events");

    }


    public void consume(byte[] msg, String type){
        String filePath = "/users/chenyu.sun/Downloads/spring-elasticsearch-restclient/es" + type + "result.txt";
        StreamInput in = StreamInput.wrap(msg);
        SearchHit recover = null;
        List<SearchHit> searchHits = new ArrayList<>();
        try {
            while (in.available() > 0) {
                //counter += 1;
                recover = SearchHit.readSearchHit(in);
                searchHits.add(recover);
                //System.out.println("in here!!!!!!!");
            }
        } catch (IOException e) {
            System.out.println("ERROR EOF");
        }
        String text = searchHits.get(0).toString();
//        System.out.println(text);
        try(FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(text);

        } catch (IOException e) {
            System.out.println("error writing to file");
        }
    }

}
