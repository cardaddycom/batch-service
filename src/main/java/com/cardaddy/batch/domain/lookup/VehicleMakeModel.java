package com.cardaddy.batch.domain.lookup;

import com.cardaddy.batch.domain.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import javax.persistence.*;
import java.util.List;

/**
 *
 * @author george
 */
@Data
@Entity
@ToString(exclude = {"vehicleConfigurations"})
@NoArgsConstructor
@Table(name = "vehicle_make_model", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"vehicle_category_id", "make_id", "model_id"})})
public class VehicleMakeModel extends BaseEntity {

    @IndexedEmbedded
    @ManyToOne
    @JoinColumn(name = "vehicle_category_id", nullable = false)
    private VehicleCategory vehicleCategory;

    @ManyToOne
    @JoinColumn(name = "make_id", nullable = false)
    private VehicleMake vehicleMake;

    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private VehicleModel vehicleModel;

    @OneToMany(mappedBy = "vehicleMakeModel", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<VehicleConfiguration> vehicleConfigurations;

}
