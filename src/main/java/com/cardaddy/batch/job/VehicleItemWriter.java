package com.cardaddy.batch.job;

import com.cardaddy.batch.model.FlatVehicleListing;
import com.cardaddy.batch.repository.VehicleListingRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class VehicleItemWriter implements ItemWriter<FlatVehicleListing> {

    @Autowired
    private VehicleListingRepository respository;

    @Override
    public void write(List<? extends FlatVehicleListing> list) throws Exception {
//        respository.saveAll(list);
    }
}
