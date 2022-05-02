package com.cardaddy.batch.domain.account;

import com.cardaddy.batch.domain.base.StatefulEntity;
import com.cardaddy.batch.repository.listing.VehicleListing;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 *
 * @author george
 */
@Data
@Entity
@Table(name = "partner_profile")
public class PartnerProfile extends StatefulEntity {

    @Column(nullable = false)
    private String partnerName;

    @Column(nullable = false)
    private String leadEmail;

    @Column(nullable = false)
    private String websiteUrl;

    @Column(unique = true, nullable = false)
    private String profileDomain;

    @Column(length = 10)
    private String phone;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @OneToMany(mappedBy = "partnerProfile")
    private List<UserProfile> userProfiles;

    @OneToMany(mappedBy = "partnerProfile")
    private List<VehicleListing> vehicleListings;

    @Column(columnDefinition = "LONGTEXT", length = 25000)
    private String descriptionTemplate;

    public PartnerProfile() {
        createDate = new Date();
    }

}
