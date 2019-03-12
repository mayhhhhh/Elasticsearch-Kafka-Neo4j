package com.may.es_kafka_neo4j.Service;


import com.may.es_kafka_neo4j.Repository.assetRepository.*;
import com.may.es_kafka_neo4j.Repository.eventRepository.*;
import com.may.es_kafka_neo4j.Repository.eventRepository.IpRepository;
import com.google.common.collect.Lists;
import com.may.es_kafka_neo4j.model.eventModel.*;
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
                              String object_name, String object_type){
//    public void saveEventData(String e_id, String s_ip, String s_hostname, String s_port, String s_zone_id, String s_zone_name,
//            String t_ip, String t_hostname, String t_port, String t_zone_id, String t_zone_name,
//            String g_ip, String g_hostname, String g_port, String g_zone_id, String g_zone_name,
//            String device_name, String device_id, String sourceSystem, String object_name, String object_type){

        Port sPort, tPort, gPort;
        sPort = savePort(s_port);
        tPort = savePort(t_port);
        gPort = savePort(g_port);

        if(sPort.getPorts() != null) {
            sPort.getPorts().add(tPort);
        }
        else{
            sPort.setPorts(Lists.newArrayList(tPort));
        }
        sPort.getPorts().add(tPort);

        Host sHost, tHost, gHost;
        sHost = saveHost(s_hostname, sPort);
        tHost = saveHost(t_hostname, tPort);
        gHost = saveHost(g_hostname, gPort);
        if(!object_name.contains("Empty")){
            Descriptors descriptors;
            if(descriptorsRepository.findByObjectname(object_name).isPresent()){
                descriptors = descriptorsRepository.findByObjectname(object_name).get();
            }
            else{
                descriptors = Descriptors.of(object_name, object_type);
                descriptorsRepository.save(descriptors);
            }
            if(tHost.getObjects() == null){
                tHost.setObjects(Lists.newArrayList(descriptors));
            }
            else{
                tHost.getObjects().add(descriptors);
            }
        }

        Ip sIp, tIp, gIp;
        sIp = saveIp(s_ip, sHost);
        tIp = saveIp(t_ip, tHost);
        gIp = saveIp(g_ip, gHost);

        Zone sZone, tZone, gZone;
        sZone = saveIpToZone(s_zone_id, s_zone_name, sIp);
        tZone = saveIpToZone(t_zone_id, t_zone_name, tIp);
        gZone = saveIpToZone(g_zone_id, g_zone_name, gIp);


//        TargetField targetField = TargetField.of(t_hostname, tIp, tPort, tZone);
//        GeneratorField generatorField = GeneratorField.of(g_hostname, gIp, gPort, gZone);
//        SourceField sourceField = SourceField.of(s_hostname, sIp, sPort, sZone);
//        targetFieldRepository.save(targetField);
//        generatorFieldRepository.save(generatorField);
//        sourceFieldRepository.save(sourceField);

//        Event event = Event.of(e_id, sourceField, targetField, generatorField, descriptors);
//        eventRepository.save(event);
//
//        SourceSystem source_System;
//        if(sourceSystemRepository.findBySourcesystem(sourceSystem).isPresent()){
//            source_System = sourceSystemRepository.findBySourcesystem(sourceSystem).get();
//            source_System.getEvents().add(event);
//        }
//        else{
//            source_System = SourceSystem.of(sourceSystem, Lists.newArrayList(event));
//            sourceSystemRepository.save(source_System);
//        }
//
//        Device device;
//        if(deviceRepository.findByDevicename(device_name).isPresent()){
//            device = deviceRepository.findByDevicename(device_name).get();
//            device.getSourceSystem().add(source_System);
//        }
//        else{
//            device = Device.of(device_name, Lists.newArrayList(source_System));
//            deviceRepository.save(device);
//        }

    }

    private Host saveHost(String hostname, Port port) {
        Host retHost;
        if(hostRepository.findByHost(hostname).isPresent()){
            retHost = hostRepository.findByHost(hostname).get();
            retHost.getPorts().add(port);
        }
        else{
            retHost = Host.of(hostname, Lists.newArrayList(port));
            hostRepository.save(retHost);
        }
        return retHost;
    }

    private Ip saveIp(String ip, Host host) {
        Ip retIp;
        if (ipRepository.findByIp(ip).isPresent()) {
            retIp = ipRepository.findByIp(ip).get();
            retIp.getHosts().add(host);
        } else {
            retIp = Ip.of(ip, Lists.newArrayList(host));
            ipRepository.save(retIp);
        }
        return retIp;
    }

    private Port savePort(String port) {
        Port retPort;
        if (portRepository.findByPort(port).isPresent()) {
            retPort = portRepository.findByPort(port).get();
        } else {
            retPort = Port.of(port);
            portRepository.save(retPort);
        }
        return retPort;
    }

    private Zone saveIpToZone(String zone_id, String zone_name, Ip ip) {
        Zone zone;
        if(zoneRepository.findByZonename(zone_name).isPresent()){
            zone = zoneRepository.findByZonename(zone_name).get();
            zone.getIps().add(ip);
        }
        else{
            zone = Zone.of(zone_name, zone_id, Lists.newArrayList(ip));
            zoneRepository.save(zone);
        }
        return zone;
    }
}
