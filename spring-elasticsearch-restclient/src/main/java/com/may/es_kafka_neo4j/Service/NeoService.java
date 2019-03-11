package com.may.es_kafka_neo4j.Service;


import com.may.es_kafka_neo4j.Repository.assetRepository.*;
import com.may.es_kafka_neo4j.Repository.eventRepository.*;
import com.may.es_kafka_neo4j.Repository.eventRepository.IpRepository;
import com.google.common.collect.Lists;
import com.may.es_kafka_neo4j.model.eventModel.*;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class NeoService {
    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private HostRepository hostRepository;

//    @Autowired
//    private IpRepository ipRepository;

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

    @Autowired
    private DescriptorsRepository descriptorsRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private IpRepository ipRepository;

    @Autowired
    private PortRepository portRepository;

    @Autowired
    private SourceSystemRepository sourceSystemRepository;

    @Autowired
    private ZoneRepository zoneRepository;

//
//    @Transactional
//    public void initData(){
//        Ip ip_1 = Ip.of("204.99.128.20");
//        Ip ip_2 = Ip.of("136.145.1.223");
//        Ip ip_3 = Ip.of("103.25.61.44");
//        ipRepository.save(ip_1);
//        ipRepository.save(ip_2);
//        ipRepository.save(ip_3);
//
//
//        Host host_1 = Host.of("xs10-213.paratemps.com", ip_1);
//        Host host_2 = Host.of("test-hn22.paratemps.com", ip_2);
//        Host host_3 = Host.of("utm-inc-05.tw-test.net", ip_3);
//        hostRepository.save(host_1);
//        hostRepository.save(host_2);
//        hostRepository.save(host_3);
//
//        Asset asset_1 = Asset.of("device", ip_1);
//        Asset asset_2 = Asset.of("person", ip_3);
//        assetRepository.save(asset_1);
//        assetRepository.save(asset_2);
//
//
//        Alias alias_1 = Alias.of("dbprod2$", Lists.newArrayList(asset_1, asset_2));
//        Alias alias_2 = Alias.of("root2", Lists.newArrayList(asset_1, asset_2));
//        Alias alias_3 = Alias.of("nobody (mapped)",Lists.newArrayList(asset_1));
//        aliasRepository.save(alias_1);
//        aliasRepository.save(alias_2);
//        aliasRepository.save(alias_3);
//
//
//        Organization organization_1 = Organization.of("siem_asset_service", Lists.newArrayList(asset_1, asset_2));
//        organizationRepository.save(organization_1);
//    }
//
//    @Transactional
//    public void saveAssetData(String ip, String host, String organization, String asset, String alias){
//        Ip curr_ip = Ip.of(ip);
//        ipRepository.save(curr_ip);
//
//        Host curr_host = Host.of(host, curr_ip);
//        hostRepository.save(curr_host);
//
//        Asset curr_asset = Asset.of(asset, curr_ip);
//        assetRepository.save(curr_asset);
//
//
//
//        if(organizationRepository.findByOrganization(organization).isPresent()){
//            organizationRepository.findByOrganization(organization).get().getAssets().add(curr_asset);
//        }
//        else{
//            Organization curr_organization = Organization.of(organization, Lists.newArrayList(curr_asset));
//            organizationRepository.save(curr_organization);
//        }
//
//        if(aliasRepository.findByAlias(alias).isPresent()){
//            aliasRepository.findByAlias(alias).get().getAssets().add(curr_asset);
//        }
//        else{
//            Alias curr_alias = Alias.of(alias, Lists.newArrayList(curr_asset));
//            aliasRepository.save(curr_alias);
//        }
//    }

    @Transactional
    public void saveEventData(String e_id, String s_ip, String s_hostname, String s_port, String s_zone_id, String s_zone_name,
                              String t_ip, String t_hostname, String t_port, String t_zone_id, String t_zone_name,
                              String g_ip, String g_hostname, String g_port, String g_zone_id, String g_zone_name,
                              String device_name, String device_id, String sourceSystem, String object_name, String object_type){
        Ip sIp, tIp, gIp;
        if(ipRepository.findByIp(s_ip).isPresent()){
            sIp = ipRepository.findByIp(s_ip).get();
        }
        else{
            sIp = Ip.of(s_ip);
            ipRepository.save(sIp);
        }
        if(ipRepository.findByIp(t_ip).isPresent()){
            tIp = ipRepository.findByIp(t_ip).get();
        }
        else{
            tIp = Ip.of(t_ip);
            ipRepository.save(tIp);
        }
        if(ipRepository.findByIp(g_ip).isPresent()){
            gIp = ipRepository.findByIp(g_ip).get();
        }
        else{
            gIp = Ip.of(g_ip);
            ipRepository.save(gIp);
        }

        Port sPort, tPort, gPort;
        if(portRepository.findByPort(s_port).isPresent()){
            sPort = portRepository.findByPort(s_port).get();
        }
        else{
            sPort = Port.of(s_port);
            portRepository.save(sPort);
        }
        if(portRepository.findByPort(t_port).isPresent()){
            tPort = portRepository.findByPort(t_port).get();
        }
        else{
            tPort = Port.of(t_port);
            portRepository.save(tPort);
        }
        if(portRepository.findByPort(g_port).isPresent()){
            gPort = portRepository.findByPort(g_port).get();
        }
        else{
            gPort = Port.of(g_port);
            portRepository.save(gPort);
        }

        Zone sZone, tZone, gZone;
        if(zoneRepository.findByZonename(s_zone_name).isPresent()){
            sZone = zoneRepository.findByZonename(s_zone_name).get();
        }
        else{
            sZone = Zone.of(s_zone_name, s_zone_id);
            zoneRepository.save(sZone);
        }
        if(zoneRepository.findByZonename(t_zone_name).isPresent()){
            tZone = zoneRepository.findByZonename(t_zone_name).get();
        }
        else{
            tZone = Zone.of(t_zone_name, t_zone_id);
            zoneRepository.save(tZone);
        }
        if(zoneRepository.findByZonename(g_zone_name).isPresent()){
            gZone = zoneRepository.findByZonename(g_zone_name).get();
        }
        else{
            gZone = Zone.of(g_zone_name, g_zone_id);
            zoneRepository.save(gZone);
        }



        TargetField targetField = TargetField.of(t_hostname, tIp, tPort, tZone);
        GeneratorField generatorField = GeneratorField.of(g_hostname, gIp, gPort, gZone);
        SourceField sourceField = SourceField.of(s_hostname, sIp, sPort, sZone);
        targetFieldRepository.save(targetField);
        generatorFieldRepository.save(generatorField);
        sourceFieldRepository.save(sourceField);

        Descriptors descriptors;
        if(descriptorsRepository.findByObjectname(object_name).isPresent()){
            descriptors = descriptorsRepository.findByObjectname(object_name).get();
        }
        else{
            descriptors = Descriptors.of(object_name, object_type);
            descriptorsRepository.save(descriptors);
        }
        Event event = Event.of(e_id, sourceField, targetField, generatorField, descriptors);
        eventRepository.save(event);

        SourceSystem source_System;
        if(sourceSystemRepository.findBySourcesystem(sourceSystem).isPresent()){
            source_System = sourceSystemRepository.findBySourcesystem(sourceSystem).get();
            source_System.getEvents().add(event);
        }
        else{
            source_System = SourceSystem.of(sourceSystem, Lists.newArrayList(event));
            sourceSystemRepository.save(source_System);
        }

        Device device;
        if(deviceRepository.findByDevicename(device_name).isPresent()){
            device = deviceRepository.findByDevicename(device_name).get();
            device.getSourceSystem().add(source_System);
        }
        else{
            device = Device.of(device_name, Lists.newArrayList(source_System));
            deviceRepository.save(device);
        }










//        if(targetFieldRepository.findByTargetField(t_ip).isPresent()){
//            targetField = targetFieldRepository.findByTargetField(t_ip).get();
//        }
//        else{
//            targetField = TargetField.of(t_ip);
//            targetFieldRepository.save(targetField);
//        }
//        if(sourceFieldRepository.findBySourceField(s_ip).isPresent()){
//            sourceField = sourceFieldRepository.findBySourceField(s_ip).get();
//        }
//        else{
//            sourceField = SourceField.of(s_ip);
//            sourceFieldRepository.save(sourceField);
//        }
//        if(generatorFieldRepository.findByGeneratorField(g_ip).isPresent()){
//            generatorField = generatorFieldRepository.findByGeneratorField(g_ip).get();
//        }
//        else{
//            generatorField = GeneratorField.of(g_ip);
//            generatorFieldRepository.save(generatorField);
//        }
//        Event event = Event.of(e_id, sourceField, targetField, generatorField);
//        eventRepository.save(event);

    }
}
