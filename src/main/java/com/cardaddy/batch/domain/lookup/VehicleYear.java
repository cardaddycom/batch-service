package com.cardaddy.batch.domain.lookup;

import com.cardaddy.batch.domain.base.TransitoryData;
import com.cardaddy.batch.domain.base.TransitoryLookup;
import com.cardaddy.batch.domain.base.VoteEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.search.engine.backend.types.Aggregable;
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

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
public class VehicleYear extends VoteEntity implements TransitoryLookup, TransitoryData {

    @FullTextField(projectable = Projectable.YES)
    @FullTextField(name = "abbr", projectable = Projectable.NO)
    @KeywordField(name = "name_aggregate_idx", aggregable = Aggregable.YES, sortable = Sortable.YES)
    private String name;

    @OneToMany(mappedBy = "vehicleYear", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<VehicleConfiguration> vehicleConfigurations;

    public VehicleYear(String name) {
        this.name = name;
        setActive(false);
    }

    public boolean isRemovable() {
        return !isActive() && vehicleConfigurations.isEmpty();
    }

}
