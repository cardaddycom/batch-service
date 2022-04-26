package com.cardaddy.batch.domain.account;

import com.cardaddy.batch.domain.base.BaseEntity;
import com.cardaddy.batch.domain.lookup.InventoryType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author george
 */
@Data
@Entity
@Table(name = "dealer_inventory_type")
public class DealerInventoryType extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "dealer_profile_id", nullable = false)
    private DealerProfile dealerProfile;

    @ManyToOne
    @JoinColumn(name = "inventory_type_id", nullable = false)
    private InventoryType inventoryType;

}
