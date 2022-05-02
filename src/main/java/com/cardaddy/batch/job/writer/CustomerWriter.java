package com.cardaddy.batch.job.writer;

import com.cardaddy.batch.domain.account.DealerProfile;
import com.cardaddy.batch.domain.location.Location;
import com.cardaddy.batch.domain.lookup.VehicleCategory;
import com.cardaddy.batch.domain.task.imports.ImportTask;
import com.cardaddy.batch.domain.task.imports.ImportTaskDealer;
import com.cardaddy.batch.model.FlatCustomer;
import com.cardaddy.batch.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class CustomerWriter implements ItemWriter<FlatCustomer> {

    private static final String DETROIT_TRADING = "Detroit Trading";

    @Autowired
    private DealerRepository dealerRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private ImportTaskRepository importTaskRepository;
    @Autowired
    private VehicleCategoryRepository vehicleCategoryRepository;
    @Autowired
    private ImportTaskDealerRepository importTaskDealerRepository;
    @Value("#{jobParameters['importTaskId']}")
    private Long importTaskId;

    @Override
    public void write(List<? extends FlatCustomer> list) throws Exception {
        List<FlatCustomer> flatCustomers = (List<FlatCustomer>) list;
        Map<String, DealerProfile> dealerProfileMap = getDealerMap(flatCustomers);
        Map<String, Location> locationMap = getLocationMap(flatCustomers);

        VehicleCategory category = getVehicleCategory("cars");
        ImportTask importTask = getImportTask(importTaskId);

        String importSystemName = importTask.getImportSystem().getName();

        List<DealerProfile> dealerProfiles = new ArrayList<>();

        list.forEach(flatCustomer -> {
            DealerProfile dealerProfile = new DealerProfile();

            if(dealerProfileMap.containsKey(flatCustomer.getCustomerNumber().toLowerCase())) {
                dealerProfile = dealerProfileMap.get(flatCustomer.getCustomerNumber().toLowerCase());
            } else {
                dealerProfile.setCreateDate(new Date());
                dealerProfile.setLeadEmail("detroittrader@cardaddy.com");
                dealerProfile.setOwnerFirstName("Car");
                dealerProfile.setOwnerLastName("Daddy");
                dealerProfile.setProfileDomain(buildProfileDomain(flatCustomer, locationMap.get(flatCustomer.getZipcode())));
            }

            dealerProfile.setCustomerNumber(flatCustomer.getCustomerNumber());
            dealerProfile.setDealerName(flatCustomer.getDealerName());
            dealerProfile.setExtension(flatCustomer.getExtension());

            if(DETROIT_TRADING.equals(importSystemName)) {
                dealerProfile.setPhone("8552111252");
            }

            dealerProfile.setPricingTier(flatCustomer.getPricingTier());
            dealerProfile.setStreetAddress1(flatCustomer.getAddress1());
            log.debug("zip {}", flatCustomer.getZipcode());
            log.debug("location {}", locationMap.get(flatCustomer.getZipcode()).getZip());
            dealerProfile.setZipDetail(locationMap.get(flatCustomer.getZipcode()));
            dealerProfile.setVehicleCategory(category);

            ImportTaskDealer importTaskDealer = getImportTaskDealer(dealerProfile, importTask);
            importTaskDealer.setDmsId(flatCustomer.getCustomerNumber());
            importTaskDealer.setActive(true);
            dealerRepository.save(dealerProfile);
            importTaskDealerRepository.save(importTaskDealer);


            dealerProfiles.add(dealerProfile);
        });

    }

    private ImportTaskDealer getImportTaskDealer(DealerProfile dealerProfile, ImportTask importTask) {
        ImportTaskDealer importTaskDealer = importTaskDealerRepository.findByDealerProfileAndImportTask(dealerProfile, importTask);

        if(importTaskDealer == null) {
            importTaskDealer = new ImportTaskDealer(importTask);
            importTaskDealer.setDealerProfile(dealerProfile);
        }

        return importTaskDealer;
    }

    private ImportTask getImportTask(Long importTaskId) {
        return importTaskRepository.getById(importTaskId);
    }

    private Map<String, DealerProfile> getDealerMap(List<FlatCustomer> flatCustomers) {
        Set<String> keys = flatCustomers.stream().map(flatCustomer -> flatCustomer.getCustomerNumber().toLowerCase()).collect(Collectors.toSet());
        return dealerRepository.getDealerProfileByCustomerNumberIn(keys).stream()
                .collect(Collectors.toMap(key -> key.getCustomerNumber().toLowerCase().trim(), dealerProfile -> dealerProfile, (a, b) -> b));
    }

    private Map<String, Location> getLocationMap(List<FlatCustomer> list) {
        Set<String> keys = list.stream().map(flatCustomer -> flatCustomer.getZipcode()).collect(Collectors.toSet());
        return locationRepository.getLocationByZipIn(keys).stream()
                .collect(Collectors.toMap(key -> key.getZip().toLowerCase().trim(), Location -> Location, (a, b) -> b));
    }

    private String buildProfileDomain(FlatCustomer flatCustomer, Location location) {
        return getURLFormatter(flatCustomer.getDealerName() + " of " + location.getCity() + " " + location.getState().getName() + " " + flatCustomer.getCustomerNumber());
    }

    public String getURLFormatter(String url) {
        return url.trim().replaceAll("[^A-Za-z0-9]+", "-").toLowerCase();
    }

    private VehicleCategory getVehicleCategory(String code) {
        return vehicleCategoryRepository.findByCode(code);
    }

}
