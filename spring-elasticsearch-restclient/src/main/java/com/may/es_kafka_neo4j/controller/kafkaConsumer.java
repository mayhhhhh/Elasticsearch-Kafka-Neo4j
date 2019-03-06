package com.may.es_kafka_neo4j.controller;

import com.may.es_kafka_neo4j.Service.NeoService;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class kafkaConsumer {

    @Autowired
    private NeoService neoService;

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
        String filePath = "/users/chenyu.sun/Desktop/ES_result/es" + type + "result.txt";
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
        //trial
        SearchHit hit = searchHits.get(0);
        Map<String, Object> map = hit.getSourceAsMap();
        if(type.equals("Assets")) {
            String ip = map.get("ips").toString();
            String alias = map.get("aliases").toString();
            String org = map.get("org_id").toString();
            String asset = map.get("type").toString();
            String host = map.get("hosts").toString();

            neoService.saveAssetData(ip, host, org, asset, alias);
        }

        if(type.equals("Events")){
            if(hit.getType().equals("meta_event")) {
                String s_ip = map.get("s_ip").toString();
                String t_ip = map.get("t_ip").toString();
                String g_ip = map.get("g_ip").toString();
                String e_id = map.get("id").toString();

                neoService.saveEventData(e_id, s_ip, t_ip, g_ip);
            }
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
