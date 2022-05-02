package com.cardaddy.batch.repository.listing;

import com.cardaddy.batch.domain.account.DealerProfile;
import com.cardaddy.batch.domain.account.PartnerProfile;
import com.cardaddy.batch.domain.account.UserProfile;
import com.cardaddy.batch.domain.base.BaseEntity;
import com.cardaddy.batch.domain.location.Location;
import com.cardaddy.batch.domain.lookup.*;
import com.cardaddy.batch.domain.task.imports.ImportTask;
import lombok.Data;
import org.hibernate.search.engine.backend.types.Aggregable;
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.automaticindexing.ReindexOnUpdate;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author George
 */
@Data
@Entity
@Indexed
@Table(name = "vehicle_listing")
public class VehicleListing extends BaseEntity {

    private static final int ACTIVATE_KEY_LENGTH = 16;
    private final static String EMPTY_SPACE = " ";
//
//    @Id
//    @GenericField(name = "id", sortable = Sortable.YES)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGen")
//    @SequenceGenerator(name = "seqGen", sequenceName = "seq", initialValue = 1)
//    private Long id;

    private String exteriorColorCustom;

    private String interiorColorCustom;

    private String dealerLiveId;

    private int schedulerCount;

    @Column(length = 5000, columnDefinition="TEXT")
    private String categorizedOptions;

    @Column(length = 5000,columnDefinition="TEXT")
    private String options;

    @GenericField(name = "active")
    @GenericField(name = "active_idx", aggregable = Aggregable.YES)
    @Column(nullable = false, columnDefinition = "BIT")
    private boolean active = true;

    @Column(nullable = false, columnDefinition = "BIT")
    private boolean dealix;

    @Column(nullable = false, columnDefinition = "BIT")
    private boolean  detroitTrader;

    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
    @IndexedEmbedded
    @ManyToOne
    @JoinColumn(name = "vehicle_year_id", nullable = false)
    private VehicleYear vehicleYear;

    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
    @IndexedEmbedded(includeDepth = 1)
    @ManyToOne
    @JoinColumn(name = "vehicle_make_id", nullable = false)
    private VehicleMake vehicleMake;

    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
    @IndexedEmbedded(includeDepth = 1)
    @ManyToOne
    @JoinColumn(name = "vehicle_model_id", nullable = false)
    private VehicleModel vehicleModel;

    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
    @IndexedEmbedded(includeDepth = 1)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_trim_id", nullable = true)
    private VehicleTrim vehicleTrim;

    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
    @IndexedEmbedded(includeDepth = 1)
    @ManyToOne
    @JoinColumn(name = "vehicle_category_id", nullable = false)
    private VehicleCategory vehicleCategory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_configuration_id", nullable = true)
    private VehicleConfiguration vehicleConfiguration;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_profile_id", nullable = true)
    private UserProfile userProfile;

    @IndexedEmbedded(includeDepth = 1)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dealer_profile_id", nullable = true)
    private DealerProfile dealerProfile;

    @IndexedEmbedded(includeDepth = 1)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "partner_profile_id", nullable = true)
    private PartnerProfile partnerProfile;

    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
    @IndexedEmbedded
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "body_type_id", nullable = true)
    private VehicleBodyType bodyType;

    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
    @IndexedEmbedded
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transmission_id", nullable = true)
    private VehicleTransmission transmission;

    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
    @IndexedEmbedded
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exterior_color_id", nullable = true)
    private VehicleColor exteriorColor;

    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
    @IndexedEmbedded
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "interior_color_id", nullable = true)
    private VehicleColor interiorColor;

    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
    @IndexedEmbedded
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "condition_id", nullable = true)
    private VehicleCondition condition;

    @Column(columnDefinition = "LONGTEXT", length = 15000)
    private String description;

    @GenericField(name = "price_idx", projectable = Projectable.YES, sortable = Sortable.YES)
    @ScaledNumberField(decimalScale = 2)
    @Column(nullable = false, precision = 9, scale = 2)
    private BigDecimal price;

    @Column(nullable = true, precision = 9, scale = 2)
    private BigDecimal bidPrice;

    @Column(length = 255)
    private String partnerUrl;

    @Column(length = 17)
    private String vin;

    @GenericField(name = "mileage_aggregate_idx", aggregable = Aggregable.YES, sortable = Sortable.YES)
    @Column(length = 7)
    private Integer mileage;

    @Column(length = 20)
    private String phone;

    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
    @IndexedEmbedded
    @ManyToOne
    @JoinColumn(name = "zip_detail_id", nullable = false)
    private Location vehicleLocation;

    @Column(nullable = false, length = 100)
    private String vehicleTitle;

    @GenericField
    @Column(columnDefinition = "BIT")
    private boolean purchased;

    @Column(columnDefinition = "BIT" )
    private boolean pendingPayment;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date schedulerDate;

    @Column(columnDefinition = "BIT", nullable = false)
    private boolean importVehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "import_task_id", nullable = true)
    private ImportTask importTask;

    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
    @IndexedEmbedded
    @ManyToOne
    @JoinColumn(name = "seller_type_id", nullable = false)
    private SellerType sellerType;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "city_mpg")
    private String cityMPG;

    @Column(name = "highway_mpg")
    private String highwayMPG;

    private int distance;

    private String stockNumber;

    private String ppcUrl;

    private BigDecimal phonePayoutPrice;

    private String phoneExtension;

    private BigDecimal webPayoutPrice;

    private Integer numberOfImages;

    private String websiteListingUrl;

    @Column(columnDefinition = "TEXT", length = 5000)
    private String importPhotoUrls;

    public VehicleListing() {
        createDate = new Date();
    }

    @GenericField(name = "mileagesearch")
    @IndexingDependency(derivedFrom = @ObjectPath(
            @PropertyValue(propertyName = "mileage")
    ))
    public int getMileageSearch() {
        if (mileage != null) {
            return mileage;
        }
        return 0;
    }

}
