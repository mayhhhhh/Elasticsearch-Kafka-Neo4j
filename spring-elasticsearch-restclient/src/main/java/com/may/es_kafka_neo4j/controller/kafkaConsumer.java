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

    @KafkaListener(topics = "event_trial_311_1",
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

            //neoService.saveAssetData(ip, host, org, asset, alias);
        }

        if(type.equals("Events")){
            if(hit.getType().equals("meta_event")) {
                String s_ip = (map.get("s_ip") != null) ? map.get("s_ip").toString() : "Empty_s_ip";
                String s_hostname = (map.get("s_hostname") != null) ? map.get("s_hostname").toString() : "Empty_s_hostname";
                String s_port = (map.get("s_port") != null) ? map.get("s_port").toString() : "Empty_s_port";
                String s_zone_name = (map.get("s_zone_name") != null) ? map.get("s_zone_name").toString() : "Empty_s_zone_name";
                String s_zone_id = (map.get("s_zone_id") != null) ? map.get("s_zone_id").toString() : "Empty_s_zone_id";
                String t_ip = (map.get("t_ip") != null) ? map.get("t_ip").toString() : "Empty_t_ip";
                String t_hostname = (map.get("t_hostname") != null) ? map.get("t_hostname").toString() : "Empty_t_hostname";
                String t_port = (map.get("t_port") != null) ? map.get("t_port").toString() : "Empty_t_port";
                String t_zone_name = (map.get("t_zone_name") != null) ? map.get("t_zone_name").toString() : "Empty_t_zone_name";
                String t_zone_id = (map.get("t_zone_id") != null) ? map.get("t_zone_id").toString() : "Empty_t_zone_id";
                String g_ip = (map.get("g_ip") != null) ? map.get("g_ip").toString() : "Empty_g_ip";
                String g_hostname = (map.get("g_hostname") != null) ? map.get("g_hostname").toString() : "Empty_g_hostname";
                //String g_port = map.get("g_port").toString();
                String g_port = (map.get("g_port") != null) ? map.get("g_port").toString() : "Empty_g_port";
                //String g_zone_name = map.get("g_zone_name").toString();
                String g_zone_name = (map.get("g_zone_name") != null) ? map.get("g_zone_name").toString() : "Empty_g_zone_name";
                String g_zone_id = (map.get("g_zone_id") != null) ? map.get("g_zone_id").toString() : "Empty_g_zone_id";
                String device_id = (map.get("device_id") != null) ? map.get("device_id").toString() : "Empty_device_id";
                String device_name = (map.get("dyn_string_device_name") != null) ? map.get("dyn_string_device_name").toString() : "Empty_device_name";
                String sourceSystem = (map.get("dyn_string_source_system") != null) ? map.get("dyn_string_source_system").toString() : "Empty source_system";
                String object_name = (map.get("object_name") != null) ? map.get("object_name").toString() : "Empty_object_name";
                String object_type = (map.get("object_type") != null) ? map.get("object_type").toString() : "Empty_object_type";


                String e_id = map.get("id").toString();

                neoService.saveEventData(e_id, s_ip, s_hostname, s_port, s_zone_id, s_zone_name,
                        t_ip, t_hostname, t_port, t_zone_id, t_zone_name,
                        g_ip, g_hostname, g_port, g_zone_id, g_zone_name,
                        device_name, device_id, sourceSystem, object_name, object_type);
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
