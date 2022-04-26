
package com.cardaddy.batch.domain.account;

import com.cardaddy.batch.domain.base.StatefulEntity;
import com.cardaddy.batch.domain.listing.VehicleListing;
import com.cardaddy.batch.domain.location.Location;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 *
 * @author George
 */
@Data
@Entity
public class UserProfile extends StatefulEntity {

    public static final int SALT_LENGTH = 16;

    @Column(length = 44)
    private String password;

    @Column(length = 50)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    @Column(nullable = false, length = 62, unique = true)
    private String email;

    @Column(length = 95)
    private String streetAddress;

    @Column(length = 95)
    private String streetAddress2;

    @Column(length = SALT_LENGTH)
    private String salt;

    @OneToMany(mappedBy = "userProfile", orphanRemoval = true)
    private List<VehicleListing> vehicleListings;

    @OneToMany(mappedBy = "userProfile", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<UserRole> userRoles;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name="last_logged_in", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoggedIn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dealer_profile_id", nullable = true)
    private DealerProfile dealerProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_profile_id", nullable = true)
    private PartnerProfile partnerProfile;

    @Column(length = 20)
    private String phone;

    @Column(columnDefinition = "LONGTEXT", length = 25000)
    private String descriptionTemplate;

    @Column(nullable = false)
    private boolean passwordReset;

    @Column(nullable = false)
    private boolean trustedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = true)
    private Location location;

}
