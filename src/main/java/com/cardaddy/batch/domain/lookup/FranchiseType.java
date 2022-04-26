package com.cardaddy.batch.domain.lookup;

import com.cardaddy.batch.domain.account.DealerProfile;
import com.cardaddy.batch.domain.base.BaseEntity;
import lombok.Data;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import javax.persistence.*;

/**
 *
 * @author george
 */
@Data
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"dealer_profile_id", "make_id"})})
public class FranchiseType extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dealer_profile_id", nullable = false)
    private DealerProfile dealerProfile;

    @IndexedEmbedded
    @ManyToOne
    @JoinColumn(name = "make_id", nullable = false)
    private VehicleMake vehicleMake;

}
