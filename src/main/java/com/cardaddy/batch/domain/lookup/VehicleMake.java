package com.cardaddy.batch.domain.lookup;

import com.cardaddy.batch.domain.base.LookupBaseEntity;
import com.cardaddy.batch.domain.base.TransitoryLookup;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 *
 * @author George
 */
@Data
@Entity
@ToString(exclude = "vehicleConfigurations")
@NoArgsConstructor
public class VehicleMake extends LookupBaseEntity implements TransitoryLookup {

    @OneToMany(mappedBy = "vehicleMake", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<VehicleConfiguration> vehicleConfigurations;

    private String code;

    private boolean display;

    public VehicleMake(String value) {
        super.setName(value);
        super.setActive(false);
    }

    public boolean isRemovable() {
        return !isActive() && vehicleConfigurations.isEmpty();
    }

}
