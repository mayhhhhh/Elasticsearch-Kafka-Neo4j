package may.neo4j.neo4j.service;

import may.neo4j.neo4j.model.*;
import may.neo4j.neo4j.repository.*;
import com.google.common.collect.Lists;
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
    public void saveData(){
        Ip curr_ip = Ip.of()
    }
}
