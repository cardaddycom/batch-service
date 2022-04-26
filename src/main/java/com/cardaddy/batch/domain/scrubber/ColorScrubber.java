package com.cardaddy.batch.domain.scrubber;

import com.cardaddy.batch.domain.base.BaseEntity;
import com.cardaddy.batch.domain.base.TransitoryData;
import com.cardaddy.batch.domain.lookup.VehicleColor;
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
@Table(name = "color_scrubber")
public class ColorScrubber extends BaseEntity implements TransitoryData {

    @ManyToOne
    @JoinColumn(name = "vehicle_color_id", nullable = true)
    private VehicleColor vehicleColor;

    @Column(nullable = false, unique = true)
    private String name;

    public ColorScrubber(VehicleColor vehicleColor, String name) {
        this.vehicleColor = vehicleColor;
        this.name = name;
    }

}
