package com.cardaddy.batch.domain.lookup;

import com.cardaddy.batch.domain.base.StatefulEntity;
import com.cardaddy.batch.domain.base.TransitoryLookup;
import com.cardaddy.batch.domain.listing.VehicleListing;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 *
 * @author George
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "vehicle_configuration", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"vehicle_category_id", "year_id", "make_id", "model_id", "trim_id"})})
public class VehicleConfiguration extends StatefulEntity implements TransitoryLookup {

    @ManyToOne
    @JoinColumn(name = "vehicle_category_id", nullable = false)
    private VehicleCategory vehicleCategory;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "make_model_id", nullable = true)
    private VehicleMakeModel vehicleMakeModel;

    @ManyToOne
    @JoinColumn(name = "year_id", nullable = false)
    private VehicleYear vehicleYear;

    @ManyToOne
    @JoinColumn(name = "make_id", nullable = false)
    private VehicleMake vehicleMake;

    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private VehicleModel vehicleModel;

    @ManyToOne
    @JoinColumn(name = "trim_id", nullable = true)
    private VehicleTrim vehicleTrim;

    @OneToMany(mappedBy = "vehicleConfiguration", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<VehicleListing> vehicleListings;

    public VehicleConfiguration(VehicleConfiguration vehicleConfiguration, VehicleCategory vehicleCategory) {
        this.vehicleCategory = vehicleConfiguration.getVehicleCategory() != null ? vehicleConfiguration.getVehicleCategory() : vehicleCategory;
        this.vehicleMake = vehicleConfiguration.getVehicleMake();
        this.vehicleModel = vehicleConfiguration.getVehicleModel();
        this.vehicleTrim = vehicleConfiguration.getVehicleTrim();
        this.vehicleYear = vehicleConfiguration.getVehicleYear();
    }

    public VehicleConfiguration(VehicleListing vehicleListing, VehicleCategory vehicleCategory) {
        this.vehicleCategory = vehicleListing.getVehicleCategory() != null ? vehicleListing.getVehicleCategory() : vehicleCategory;
        this.vehicleMake = vehicleListing.getVehicleMake();
        this.vehicleModel = vehicleListing.getVehicleModel();
        this.vehicleTrim = vehicleListing.getVehicleTrim();
        this.vehicleYear = vehicleListing.getVehicleYear();
    }

    public static VehicleConfiguration set(VehicleConfiguration vehicleConfiguration, VehicleCategory vehicleCategory) {
        return new VehicleConfiguration(vehicleConfiguration, vehicleCategory);
    }

    public static VehicleConfiguration set(VehicleListing vehicleListing, VehicleCategory vehicleCategory) {
        return new VehicleConfiguration(vehicleListing, vehicleCategory);
    }

    @Override
    public boolean isRemovable() {
        return !isActive() && vehicleListings.isEmpty();
    }

}
