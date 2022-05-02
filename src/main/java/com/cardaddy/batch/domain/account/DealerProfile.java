package com.cardaddy.batch.domain.account;

import com.cardaddy.batch.domain.base.BaseEntity;
import com.cardaddy.batch.domain.listing.VehicleListing;
import com.cardaddy.batch.domain.location.Location;
import com.cardaddy.batch.domain.lookup.FranchiseType;
import com.cardaddy.batch.domain.lookup.VehicleCategory;
import com.cardaddy.batch.domain.task.imports.ImportTaskDealer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.mapper.pojo.automaticindexing.ReindexOnUpdate;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 *
 * @author george
 */
@Getter
@Setter
@Entity
@Indexed
@ToString(exclude = {"userProfiles", "vehicleListings", "vehicleCategory", "importTaskDealers"})
@Table(name = "dealer_profile")
public class DealerProfile extends BaseEntity {

    private static final int ACTIVATE_KEY_LENGTH = 16;

    @FullTextField
    @KeywordField(name = "dealer_name_lowercase_idx", normalizer  = "lowercase", projectable = Projectable.YES)
    @Column(name = "dealer_name", nullable = false)
    private String dealerName;

    @GenericField
    @Column(nullable = false, columnDefinition = "BIT")
    private boolean active = true;

    @Column(name = "lead_email", nullable = false)
    private String leadEmail;

    @Column(nullable = true)
    private String billingEmail;

    private String financeEmail;

    @Column(nullable = false)
    private String ownerFirstName;

    @Column(nullable = false)
    private String ownerLastName;

    @Column(length = 10)
    private String phoneCell;

    @Column(length = 10)
    private String phoneFax;

    @Column(length = 10)
    private String extension;

    @Column(unique = true)
    private String customerNumber;

    private String pricingTier;

    private String salesName;

    private String websiteUrl;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @OneToMany(mappedBy = "dealerProfile")
    private List<UserProfile> userProfiles;

    @IndexedEmbedded(includePaths = {"active", "vehicleMake.name", "vehicleMake.name_lowercase_idx", "vehicleCategory.dealer_code_lowercase_idx"})
    @OneToMany(mappedBy = "dealerProfile")
    private List<VehicleListing> vehicleListings;

    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
    @IndexedEmbedded
    @OneToMany(mappedBy = "dealerProfile", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<DealerInventoryType> dealerInventoryTypes;

    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
    @IndexedEmbedded
    @ManyToOne
    @JoinColumn(name = "vehicle_category_id", nullable = false)
    private VehicleCategory vehicleCategory;

    @OneToMany(mappedBy = "dealerProfile", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ImportTaskDealer> importTaskDealers;

    private String streetAddress1;

    private String streetAddress2;

    @Column(length = 10)
    private String phone;

    private String crmEmail;

    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
    @IndexedEmbedded
    @ManyToOne
    @JoinColumn(name = "zip_detail_id", nullable = false)
    private Location zipDetail;

    private String salesNotes;

    @Column(unique = true, nullable = false)
    private String profileDomain;

    @Column(columnDefinition = "LONGTEXT", length = 25000)
    private String descriptionTemplate;

    @OneToMany(mappedBy = "dealerProfile", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<FranchiseType> franchiseTypes;


}
