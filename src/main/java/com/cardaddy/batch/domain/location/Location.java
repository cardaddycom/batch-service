package com.cardaddy.batch.domain.location;

import lombok.Data;
import lombok.ToString;
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.mapper.pojo.bridge.builtin.annotation.Latitude;
import org.hibernate.search.mapper.pojo.bridge.builtin.annotation.Longitude;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author george
 */
@Data
@Entity
@ToString
@Table(name = "zip_detail")
public class Location implements Serializable {

    @Id
    @KeywordField
    @Column(length = 5)
    private String zip;

    @FullTextField(projectable = Projectable.YES)
    private String city;

    @IndexedEmbedded
    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private State state;

    @Latitude
    private Double latitude;

    @Longitude
    private Double longitude;

    private boolean displayCity;

}
