package com.cardaddy.batch.domain.listing;

import com.cardaddy.batch.domain.base.StatefulEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.client.utils.URIBuilder;

import javax.persistence.*;
import java.io.File;
import java.util.Date;

/**
 *
 * @author George
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "photo")
public class Photo extends StatefulEntity {

    private static final String FILE_EXTENSION_JPEG = ".jpg";
    private static final String PHOTO_SIZE_1000 = "_1000";
    private static final String PHOTO_SIZE_640 = "_640";
    private static final String PHOTO_SIZE_300 = "_300";
    private static final String PHOTO_SIZE_175 = "_175";
    private static final String PHOTO_SIZE_150 = "_150";
    private static final String PHOTO_SIZE_100 = "_100";
    private static final String PHOTO_SIZE_50 = "_50";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_listing_id", nullable = false)
    private VehicleListing vehicleListing;

    @Column(name = "file_name", length = 12, unique = true)
    private String fileName;

    @Column(nullable = false, columnDefinition = "BIT")
    private boolean temp;

    @Column(name = "order_by", nullable = false)
    private int orderBy;

    @Column(name = "date_uploaded")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUploaded;

    @Column(name = "expiration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    @Column(name = "external_photo_url", length = 2500,columnDefinition="TEXT")
    private String externalPhotoUrl;

    public Photo(String externalPhotoUrl, VehicleListing vehicleListing) {
        this.vehicleListing = vehicleListing;
        this.temp = true;
        this.dateUploaded = new Date();
        this.externalPhotoUrl = externalPhotoUrl;
    }

    public Photo(VehicleListing vehicleListing, String fileName) {
        this.vehicleListing = vehicleListing;
        this.fileName = fileName;
        this.temp = true;
        this.orderBy = vehicleListing.getPhotos().size() + 1;
        this.dateUploaded = new Date();
    }

    public Photo(VehicleListing vehicleListing, String fileName, String externalPhotoUrl) {
        this.vehicleListing = vehicleListing;
        this.fileName = fileName;
        this.temp = true;
        this.orderBy = vehicleListing.getPhotos().size() + 1;
        this.dateUploaded = new Date();
        this.externalPhotoUrl = externalPhotoUrl;
    }

    public String getExternalPhotoUrl() {
        if(externalPhotoUrl != null && !externalPhotoUrl.contains("http")) {
            URIBuilder uriBuilder = new URIBuilder();
            uriBuilder.setScheme("http");
            uriBuilder.setHost("img.leaddelivery.net");
            uriBuilder.setPath(String.format("/images/%s/%s/%s.jpg", vehicleListing.getVin(), "Supersized", externalPhotoUrl));
            uriBuilder.addParameter("s", "0000-2674");
            return uriBuilder.toString();
        }
        return externalPhotoUrl;
    }

    public String getPhotoSize1000() {
        return generatePhotoURL(PHOTO_SIZE_1000);
    }

    public String getPhotoSize800() {
        return generatePhotoURL(PHOTO_SIZE_640);
    }

    public String getPhotoSize300() {
        return generatePhotoURL(PHOTO_SIZE_300);
    }

    public String getPhotoSize150() {
        return generatePhotoURL(PHOTO_SIZE_150);
    }

    public String generatePhotoURL(String photoSize) {
        if (getFileName() != null) {
            StringBuilder sb = new StringBuilder(File.separator);
            sb.append(getFileName());
            sb.append(photoSize);
            sb.append(FILE_EXTENSION_JPEG);
            return sb.toString();
        }
        return externalPhotoUrl;
    }

}
