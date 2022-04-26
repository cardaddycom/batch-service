package com.cardaddy.batch.domain.lookup;

import com.cardaddy.batch.domain.base.LookupBaseEntity;
import com.cardaddy.batch.domain.base.TransitoryLookup;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.List;

/**
 *
 * @author George
 */
@Data
@Entity
@NoArgsConstructor
@ToString(exclude = "vehicleConfigurations")
public class VehicleTrim extends LookupBaseEntity implements TransitoryLookup {

    @OneToMany(mappedBy = "vehicleTrim", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<VehicleConfiguration> vehicleConfigurations;

    private String code;


    @Transient
    private final Long tempId = -System.nanoTime();

    public VehicleTrim(String value) {
        super.setName(value);
        super.setActive(false);
    }

    public boolean isRemovable() {
        return !isActive() && vehicleConfigurations.isEmpty();
    }

}
