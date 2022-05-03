package com.cardaddy.batch.job.writer;

import com.cardaddy.batch.domain.account.DealerProfile;
import com.cardaddy.batch.domain.account.PartnerProfile;
import com.cardaddy.batch.domain.listing.VehicleListing;
import com.cardaddy.batch.domain.location.Location;
import com.cardaddy.batch.domain.lookup.*;
import com.cardaddy.batch.domain.task.imports.ImportTask;
import com.cardaddy.batch.model.FlatVehicleListing;
import com.cardaddy.batch.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class VehicleItemWriter implements ItemWriter<FlatVehicleListing> {

    public static final Long DEALER_SELLER_TYPE_ID = 1L;

    @Autowired
    private VehicleListingRepository vehicleListingRepository;
    @Autowired
    private VehicleYearRepository vehicleYearRepository;
    @Autowired
    private VehicleMakeRepository vehicleMakeRepository;
    @Autowired
    private VehicleModelRepository vehicleModelRepository;
    @Autowired
    private VehicleTrimRepository vehicleTrimRepository;
    @Autowired
    private VehicleCategoryRepository categoryRepository;
    @Autowired
    private VehicleColorRepository vehicleColorRepository;
    @Autowired
    private VehicleConditionRepository vehicleConditionRepository;
    @Autowired
    private VehicleTransmissionRepository vehicleTransmissionRepository;
    @Autowired
    private VehicleBodyTypeRepository bodyTypeRepository;
    @Autowired
    private SellerTypeRepository sellerTypeRepository;
    @Autowired
    private InventoryTypeRepository inventoryTypeRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private DealerRepository dealerRepository;
    @Autowired
    private PartnerRepository partnerRepository;
    @Autowired
    private ImportTaskRepository importTaskRepository;
    @Value("#{jobParameters['importTaskId']}")
    private Long importTaskId;

    private StepExecution stepExecution;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    @Override
    public void write(List<? extends FlatVehicleListing> flatVehicleListings) throws Exception {
        List<FlatVehicleListing> listings = (List<FlatVehicleListing>) flatVehicleListings;

        List<String> vinNumbers = listings.stream().map(vehicleListing -> vehicleListing.getVin()).collect(Collectors.toList());
        log.debug("existing vehicles start");
        List<VehicleListing> existingVehicles = vehicleListingRepository.getVehicleListingByVinIn(vinNumbers);

        var existingVehiclesMap =
                existingVehicles.stream().collect(Collectors.toMap(key -> key.getVin().toLowerCase(), vehicleListing -> vehicleListing, (a, b) -> b));
        log.debug("existing vehicles end");
        log.debug("lookup data start");
        var yearMap = createOrUpdateYear(listings);
        var makeMap = createOrUpdateMake(listings);
        var modelMap = createOrUpdateModel(listings);
        var trimMap = createOrUpdateTrim(listings);
        var inventoryTypeMap = getInventoryTypeMap();
        var sellerType = getSellerType();
        var bodyTypeMap = getBodyTypeMap();
        var transmissionMap = getTransmissionMap();
        var conditionMap = getConditionMap();
        var colorMap = getColorMap();
        var categoryMap = getCategoryMap();
        var dealerMap = getDealerMap(listings);
        var locationMap = getLocationMap(listings);
        log.debug("lookup data end");

        var importTask = getImportTask(importTaskId);

        List<VehicleListing> vehicleListings = new ArrayList<>();

        for(FlatVehicleListing flatVehicleListing : listings) {
            var dealer = dealerMap.get(flatVehicleListing.getDealerId());

            if(flatVehicleListing.getVin() != null) {
                if(dealer != null) {
                    var year = yearMap.get(flatVehicleListing.getYear());
                    var make = makeMap.get(flatVehicleListing.getMake() != null ? flatVehicleListing.getMake().toLowerCase() : null);
                    var model = modelMap.get(flatVehicleListing.getModel() != null ? flatVehicleListing.getModel().toLowerCase() : null);
                    var trim = trimMap.get(flatVehicleListing.getTrim() != null ? flatVehicleListing.getTrim().toLowerCase() : null);
                    var vinKey = flatVehicleListing.getVin().toLowerCase();
                    var vehicleListing = existingVehiclesMap.containsKey(vinKey) ? existingVehiclesMap.get(vinKey) : new VehicleListing();
                    var inventoryType = inventoryTypeMap.get(flatVehicleListing.getInventoryType() != null ? flatVehicleListing.getInventoryType().toLowerCase() : null);
                    var bodyType = bodyTypeMap.get(flatVehicleListing.getBody() != null ? flatVehicleListing.getBody().toLowerCase() : null);
                    var transmission = transmissionMap.get(flatVehicleListing.getTransmission() != null ? flatVehicleListing.getTransmission().toLowerCase() : null);
                    var condition = conditionMap.get(flatVehicleListing.getCondition() != null ? flatVehicleListing.getCondition().toLowerCase() : null);
                    var interiorColor = colorMap.get(flatVehicleListing.getInteriorColor() != null ? flatVehicleListing.getInteriorColor().toLowerCase() : null);
                    var exteriorColor = colorMap.get(flatVehicleListing.getExteriorColor() != null ? flatVehicleListing.getExteriorColor().toLowerCase() : null);
                    var category = categoryMap.get(flatVehicleListing.getCategory() != null ? flatVehicleListing.getCategory().toLowerCase() : null);
                    var location = locationMap.get(flatVehicleListing.getZipcode());

                    var updatedListing = buildVehicleListing(flatVehicleListing, vehicleListing, year, make, model, trim, inventoryType, sellerType,
                            bodyType, transmission, condition, interiorColor, exteriorColor, category, dealer, null, importTask, location);
                    vehicleListings.add(updatedListing);
                } else {
                    log.debug("Dealer {} missing", flatVehicleListing.getDealerId());
                }
            }
        }

        log.info("Saving Vehicles {}", vehicleListings.size());
        vehicleListingRepository.saveAll(vehicleListings);
        vehicleListingRepository.flush();

    }

    public VehicleListing buildVehicleListing(FlatVehicleListing flatVehicleListing,
                                              VehicleListing vehicleListing,
                                              VehicleYear year,
                                              VehicleMake make,
                                              VehicleModel model,
                                              VehicleTrim trim,
                                              InventoryType inventoryType,
                                              SellerType sellerType,
                                              VehicleBodyType bodyType,
                                              VehicleTransmission transmission,
                                              VehicleCondition condition,
                                              VehicleColor interiorColor,
                                              VehicleColor exteriorColor,
                                              VehicleCategory category,
                                              DealerProfile dealer,
                                              PartnerProfile partner,
                                              ImportTask importTask,
                                              Location location) {

        if(vehicleListing.getId() == null) {
            vehicleListing.setCreateDate(new Date());
        }

        vehicleListing.setJobExecutionId(stepExecution.getJobExecution().getJobId());
        vehicleListing.setSchedulerDate(flatVehicleListing.getSchedulerDate());
        vehicleListing.setExteriorColorCustom(flatVehicleListing.getExteriorColorCustom());
        vehicleListing.setInteriorColorCustom(flatVehicleListing.getInteriorColorCustom());
        vehicleListing.setDealerLiveId(flatVehicleListing.getDealerLiveId());
        vehicleListing.setSchedulerCount(0);
        vehicleListing.setCategorizedOptions(flatVehicleListing.getCategorizedOptions());
        vehicleListing.setOptions(flatVehicleListing.getOptions());
        vehicleListing.setActive(true);
        vehicleListing.setDealix(flatVehicleListing.isDealix());
        vehicleListing.setDetroitTrader(flatVehicleListing.isDetroitTrader());
        vehicleListing.setVehicleYear(year);
        vehicleListing.setVehicleMake(make);
        vehicleListing.setVehicleModel(model);
        vehicleListing.setVehicleTrim(trim);
        vehicleListing.setVehicleCategory(category);
        vehicleListing.setDealerProfile(dealer);
        vehicleListing.setPartnerProfile(partner);
        vehicleListing.setBodyType(bodyType);
        vehicleListing.setTransmission(transmission);
        vehicleListing.setExteriorColor(exteriorColor);
        vehicleListing.setInteriorColor(interiorColor);
        vehicleListing.setCondition(condition);
        vehicleListing.setDescription(flatVehicleListing.getDescription());

        vehicleListing.setPrice(new BigDecimal(flatVehicleListing.getSellingPrice()));
        vehicleListing.setVin(flatVehicleListing.getVin());
        vehicleListing.setMileage(Integer.valueOf(flatVehicleListing.getMileage()));
        vehicleListing.setPhone(flatVehicleListing.getPhone());
        vehicleListing.setVehicleLocation(dealer != null ? dealer.getZipDetail() : location);
        vehicleListing.setVehicleTitle(flatVehicleListing.getVehicleTitle());
        vehicleListing.setPurchased(true);
        vehicleListing.setImportVehicle(true);
        vehicleListing.setImportTask(importTask);
        vehicleListing.setSellerType(sellerType);
        vehicleListing.setCityMPG(flatVehicleListing.getCityMPG());
        vehicleListing.setHighwayMPG(flatVehicleListing.getHighwayMPG());
        vehicleListing.setStockNumber(flatVehicleListing.getStockNumber());
        vehicleListing.setPpcUrl(flatVehicleListing.getPpcUrl());
        vehicleListing.setPhoneExtension(flatVehicleListing.getPhoneExtension());
        vehicleListing.setWebsiteListingUrl(flatVehicleListing.getWebsiteListingUrl());
        vehicleListing.setImportPhotoUrls(flatVehicleListing.getPhotoURLs());

        if(flatVehicleListing.getNumberOfImages() != null) {
            vehicleListing.setNumberOfImages(Integer.valueOf(flatVehicleListing.getNumberOfImages()));
        }
        if(flatVehicleListing.getDistance() != null) {
            vehicleListing.setDistance(Integer.valueOf(flatVehicleListing.getDistance()));
        }
        if(flatVehicleListing.getPhonePayoutPrice() != null) {
            vehicleListing.setPhonePayoutPrice(new BigDecimal(flatVehicleListing.getPhonePayoutPrice()));
        }
        if(flatVehicleListing.getWebPayoutPrice() != null) {
            vehicleListing.setWebPayoutPrice(new BigDecimal(flatVehicleListing.getWebPayoutPrice()));
        }
        return  vehicleListing;
    }

    private Map<String, VehicleMake> createOrUpdateMake(List<FlatVehicleListing> list) {
        Set<String> keys = list.stream().map(vehicleListing -> vehicleListing.getMake()).collect(Collectors.toSet());

        List<VehicleMake> existingVehicles = vehicleMakeRepository.getVehicleMakeByNameIn(new ArrayList<>(keys));

        Map<String, VehicleMake> existingItemsMap =
                existingVehicles.stream().collect(Collectors.toMap(key -> key.getName().toLowerCase(), vehicleMake -> vehicleMake, (a, b) -> b));

        Map<String, VehicleMake> newItemsMap = new HashMap<>();

        list.forEach(listing -> {
            String key = listing.getMake().toLowerCase();
            if (!existingItemsMap.containsKey(key)) {
                VehicleMake make = new VehicleMake();
                make.setName(listing.getMake().trim());
                make.setVote(1);
                newItemsMap.put(key, make);
            }
        });

        if(!newItemsMap.isEmpty()){
            vehicleMakeRepository.saveAll(newItemsMap.values());
        }

        existingItemsMap.putAll(newItemsMap);

        return existingItemsMap;
    }

    private Map<String, VehicleModel> createOrUpdateModel(List<FlatVehicleListing> list) {
        Set<String> keys = list.stream().map(vehicleListing -> vehicleListing.getModel()).collect(Collectors.toSet());

        List<VehicleModel> existingVehicles = vehicleModelRepository.getVehicleModelByNameIn(new ArrayList<>(keys));

        Map<String, VehicleModel> existingItemsMap =
                existingVehicles.stream().collect(Collectors.toMap(key -> key.getName().toLowerCase(), VehicleModel -> VehicleModel, (a, b) -> b));

        Map<String, VehicleModel> newItemsMap = new HashMap<>();

        list.forEach(listing -> {
            String key = listing.getModel().toLowerCase();
            if (!existingItemsMap.containsKey(key)) {
                VehicleModel model = new VehicleModel();
                model.setName(listing.getModel().trim());
                model.setVote(1);
                newItemsMap.put(key, model);
            }
        });

        if(!newItemsMap.isEmpty()) {
            vehicleModelRepository.saveAll(newItemsMap.values());
        }

        existingItemsMap.putAll(newItemsMap);

        return existingItemsMap;
    }

    private Map<String, VehicleTrim> createOrUpdateTrim(List<FlatVehicleListing> list) {
        Set<String> keys = list.stream().map(vehicleListing -> vehicleListing.getTrim()).collect(Collectors.toSet());

        List<VehicleTrim> existingVehicles = vehicleTrimRepository.getVehicleTrimByNameIn(new ArrayList<>(keys));

        Map<String, VehicleTrim> existingItemsMap =
                existingVehicles.stream().collect(Collectors.toMap(key -> key.getName().trim().toLowerCase(), VehicleTrim -> VehicleTrim, (a, b) -> b));

        Map<String, VehicleTrim> newItemsMap = new HashMap<>();

        list.forEach(listing -> {
            if(listing.getTrim() != null) {
                String key = listing.getTrim().toLowerCase();
                if (!existingItemsMap.containsKey(key)) {
                    VehicleTrim trim = new VehicleTrim();
                    trim.setName(listing.getTrim().trim());
                    trim.setVote(1);
                    newItemsMap.put(key, trim);
                }
            }
        });

        if(!newItemsMap.isEmpty()) {
            vehicleTrimRepository.saveAll(newItemsMap.values());
        }

        existingItemsMap.putAll(newItemsMap);

        return existingItemsMap;
    }

    private Map<String, VehicleYear> createOrUpdateYear(List<FlatVehicleListing> list) {
        Set<String> keys = list.stream().map(vehicleListing -> vehicleListing.getYear()).collect(Collectors.toSet());

        List<VehicleYear> existingVehicles = vehicleYearRepository.getVehicleYearByNameIn(new ArrayList<>(keys));

        Map<String, VehicleYear> existingItemsMap =
                existingVehicles.stream().collect(Collectors.toMap(key -> key.getName().toLowerCase(), VehicleYear -> VehicleYear, (a, b) -> b));

        Map<String, VehicleYear> newItemsMap = new HashMap<>();

        list.forEach(listing -> {
            if(listing.getYear() != null) {
                String key = listing.getYear().toLowerCase();
                if (!existingItemsMap.containsKey(key)) {
                    VehicleYear year = new VehicleYear();
                    year.setName(listing.getYear().trim());
                    year.setVote(1);
                    newItemsMap.put(key, year);
                }
            }
        });

        if(!newItemsMap.isEmpty()) {
            vehicleYearRepository.saveAll(newItemsMap.values());
        }

        existingItemsMap.putAll(newItemsMap);

        return existingItemsMap;
    }

    private Map<String, Location> getLocationMap(List<FlatVehicleListing> list) {
        Set<String> keys = list.stream().map(vehicleListing -> vehicleListing.getZipcode()).collect(Collectors.toSet());
        return locationRepository.getLocationByZipIn(new ArrayList<>(keys)).stream()
                .collect(Collectors.toMap(key -> key.getZip().toLowerCase().trim(), Location -> Location, (a, b) -> b));
    }

    private Map<String, DealerProfile> getDealerMap(List<FlatVehicleListing> list) {
        Set<String> keys = list.stream().map(vehicleListing -> vehicleListing.getDealerId()).collect(Collectors.toSet());
        return dealerRepository.getDealerProfileByCustomerNumberIn(new ArrayList<>(keys)).stream()
                .collect(Collectors.toMap(key -> key.getCustomerNumber().toLowerCase().trim(), dealerProfile -> dealerProfile, (a, b) -> b));
    }

    private ImportTask getImportTask(Long importTaskId) {
        return importTaskRepository.getById(importTaskId);
    }

    private Map<String, InventoryType> getInventoryTypeMap() {
        return inventoryTypeRepository.findAll().stream()
                .collect(Collectors.toMap(key -> key.getName().toLowerCase().trim(), a -> a, (a, b) -> b));
    }

    private SellerType getSellerType() {
        return sellerTypeRepository.getById(DEALER_SELLER_TYPE_ID);
    }

    private Map<String, VehicleBodyType> getBodyTypeMap() {
        return bodyTypeRepository.findAll().stream()
                .collect(Collectors.toMap(key -> key.getName().toLowerCase().trim(), a -> a, (a, b) -> b));
    }

    private Map<String, VehicleTransmission> getTransmissionMap() {
        return vehicleTransmissionRepository.findAll().stream()
                .collect(Collectors.toMap(key -> key.getName().toLowerCase().trim(), a -> a, (a, b) -> b));
    }

    private Map<String, VehicleCondition> getConditionMap() {
        return vehicleConditionRepository.findAll().stream()
                .collect(Collectors.toMap(key -> key.getName().toLowerCase().trim(), a -> a, (a, b) -> b));
    }

    private Map<String, VehicleColor> getColorMap() {
        return vehicleColorRepository.findAll().stream()
                .collect(Collectors.toMap(key -> key.getName().toLowerCase().trim(), a -> a, (a, b) -> b));
    }

    private Map<String, VehicleCategory> getCategoryMap() {
        return categoryRepository.findAll().stream()
                .collect(Collectors.toMap(key -> key.getCode().toLowerCase().trim(), a -> a, (a, b) -> b));
    }

}
