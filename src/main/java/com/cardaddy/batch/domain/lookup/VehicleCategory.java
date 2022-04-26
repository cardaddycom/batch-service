
package com.cardaddy.batch.domain.lookup;

import com.cardaddy.batch.domain.base.StatefulEntity;
import lombok.Data;
import org.hibernate.search.engine.backend.types.Aggregable;
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import javax.persistence.*;
import java.util.List;

/**
 *
 * @author George
 */
@Data
@Entity
public class VehicleCategory extends StatefulEntity {

    @FullTextField(projectable = Projectable.YES)
    @KeywordField(name = "name_aggregate_idx", aggregable = Aggregable.YES)
    @KeywordField(name = "name_lowercase_idx", normalizer  = "lowercase", projectable = Projectable.YES)
    private String name;

    private String buyCode;

    private String sellCode;

    @KeywordField(name = "code_aggregate_idx", aggregable = Aggregable.YES)
    @KeywordField(name = "code_lowercase_idx", normalizer  = "lowercase", projectable = Projectable.YES)
    private String code;

    @KeywordField(name = "dealer_code_lowercase_idx", normalizer  = "lowercase", projectable = Projectable.YES)
    private String dealerCode;

    private Integer orderBy;

    @Column(length = 5000,columnDefinition="TEXT")
    private String sellDescription;

    @Column(length = 5000,columnDefinition="TEXT")
    private String buyDescription;

    private String sellTitle;

    private String metaTitle;

    private String sellMetaDescription;

    private String codeTitle;

    private String dealerLabel;

    @OneToMany(mappedBy = "vehicleCategory")
    private List<VehicleBodyType> vehicleBodyTypes;

    @OneToMany(mappedBy = "vehicleCategory", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<VehicleConfiguration> vehicleConfigurations;

    @Override
    public String toString() {
        return "VehicleCategory{" + "name=" + name + '}';
    }

}
