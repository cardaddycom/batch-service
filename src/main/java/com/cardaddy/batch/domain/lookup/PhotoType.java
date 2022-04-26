package com.cardaddy.batch.domain.lookup;

import com.cardaddy.batch.domain.base.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;

/**
 *
 * @author george
 */
@Data
@Entity
public class PhotoType extends BaseEntity {

    private String name;

}
