package com.cardaddy.batch.domain.scrubber;

import com.cardaddy.batch.domain.base.BaseEntity;
import com.cardaddy.batch.domain.base.TransitoryData;
import com.cardaddy.batch.domain.lookup.VehicleBodyType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 *
 * @author george
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "vehicle_type_scrubber")
public class VehicleTypeScrubber extends BaseEntity implements TransitoryData {

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "vehicle_type_id", nullable = true)
    private VehicleBodyType vehicleType;

    public VehicleTypeScrubber(String name) {
        this.name = name;
    }

}
