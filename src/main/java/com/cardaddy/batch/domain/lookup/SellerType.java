package com.cardaddy.batch.domain.lookup;

import com.cardaddy.batch.domain.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import javax.persistence.Entity;

/**
 *
 * @author george
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SellerType extends BaseEntity {

    @FullTextField(projectable = Projectable.YES)
    @KeywordField(name = "name_lowercase_idx", normalizer  = "lowercase")
    private String name;

}
