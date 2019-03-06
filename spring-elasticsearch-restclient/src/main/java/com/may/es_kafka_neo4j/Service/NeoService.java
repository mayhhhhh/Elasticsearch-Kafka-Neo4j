package com.may.es_kafka_neo4j.Service;


import com.may.es_kafka_neo4j.Repository.assetRepository.*;
import com.may.es_kafka_neo4j.Repository.eventRepository.EventRepository;
import com.may.es_kafka_neo4j.Repository.eventRepository.GeneratorFieldRepository;
import com.may.es_kafka_neo4j.Repository.eventRepository.SourceFieldRepository;
import com.may.es_kafka_neo4j.Repository.eventRepository.TargetFieldRepository;
import com.may.es_kafka_neo4j.model.assetModel.*;
import com.google.common.collect.Lists;
import com.may.es_kafka_neo4j.model.eventModel.Event;
import com.may.es_kafka_neo4j.model.eventModel.GeneratorField;
import com.may.es_kafka_neo4j.model.eventModel.SourceField;
import com.may.es_kafka_neo4j.model.eventModel.TargetField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class NeoService {
    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private IpRepository ipRepository;

    @Autowired
    private AliasRepository aliasRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SourceFieldRepository sourceFieldRepository;

    @Autowired
    private TargetFieldRepository targetFieldRepository;

    @Autowired
    private GeneratorFieldRepository generatorFieldRepository;

    @Transactional
    public void initData(){
        Ip ip_1 = Ip.of("204.99.128.20");
        Ip ip_2 = Ip.of("136.145.1.223");
        Ip ip_3 = Ip.of("103.25.61.44");
        ipRepository.save(ip_1);
        ipRepository.save(ip_2);
        ipRepository.save(ip_3);


        Host host_1 = Host.of("xs10-213.paratemps.com", ip_1);
        Host host_2 = Host.of("test-hn22.paratemps.com", ip_2);
        Host host_3 = Host.of("utm-inc-05.tw-test.net", ip_3);
        hostRepository.save(host_1);
        hostRepository.save(host_2);
        hostRepository.save(host_3);

        Asset asset_1 = Asset.of("device", ip_1);
        Asset asset_2 = Asset.of("person", ip_3);
        assetRepository.save(asset_1);
        assetRepository.save(asset_2);


        Alias alias_1 = Alias.of("dbprod2$", Lists.newArrayList(asset_1, asset_2));
        Alias alias_2 = Alias.of("root2", Lists.newArrayList(asset_1, asset_2));
        Alias alias_3 = Alias.of("nobody (mapped)",Lists.newArrayList(asset_1));
        aliasRepository.save(alias_1);
        aliasRepository.save(alias_2);
        aliasRepository.save(alias_3);


        Organization organization_1 = Organization.of("siem_asset_service", Lists.newArrayList(asset_1, asset_2));
        organizationRepository.save(organization_1);
    }

    @Transactional
    public void saveAssetData(String ip, String host, String organization, String asset, String alias){
        Ip curr_ip = Ip.of(ip);
        ipRepository.save(curr_ip);

        Host curr_host = Host.of(host, curr_ip);
        hostRepository.save(curr_host);

        Asset curr_asset = Asset.of(asset, curr_ip);
        assetRepository.save(curr_asset);



        if(organizationRepository.findByOrganization(organization).isPresent()){
            organizationRepository.findByOrganization(organization).get().getAssets().add(curr_asset);
        }
        else{
            Organization curr_organization = Organization.of(organization, Lists.newArrayList(curr_asset));
            organizationRepository.save(curr_organization);
        }

        if(aliasRepository.findByAlias(alias).isPresent()){
            aliasRepository.findByAlias(alias).get().getAssets().add(curr_asset);
        }
        else{
            Alias curr_alias = Alias.of(alias, Lists.newArrayList(curr_asset));
            aliasRepository.save(curr_alias);
        }
    }

    @Transactional
    public void saveEventData(String e_id, String s_ip, String t_ip, String g_ip){
        TargetField targetField;
        GeneratorField generatorField;
        SourceField sourceField;
        if(targetFieldRepository.findByTargetField(t_ip).isPresent()){
            targetField = targetFieldRepository.findByTargetField(t_ip).get();
        }
        else{
            targetField = TargetField.of(t_ip);
            targetFieldRepository.save(targetField);
        }
        if(sourceFieldRepository.findBySourceField(t_ip).isPresent()){
            sourceField = sourceFieldRepository.findBySourceField(s_ip).get();
        }
        else{
            sourceField = SourceField.of(s_ip);
            sourceFieldRepository.save(sourceField);
        }
        if(generatorFieldRepository.findByGeneratorField(g_ip).isPresent()){
            generatorField = generatorFieldRepository.findByGeneratorField(g_ip).get();
        }
        else{
            generatorField = GeneratorField.of(g_ip);
            generatorFieldRepository.save(generatorField);
        }
        Event event = Event.of(e_id, sourceField, targetField, generatorField);
        eventRepository.save(event);

    }
}
