package com.cardaddy.batch.domain.lookup;

import com.cardaddy.batch.domain.base.BaseEntity;
import lombok.Data;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import javax.persistence.Entity;

/**
 *
 * @author george
 */
@Data
@Entity
public class InventoryType extends BaseEntity {

    @FullTextField
    @KeywordField(name = "name_keyword")
    private String name;

}
