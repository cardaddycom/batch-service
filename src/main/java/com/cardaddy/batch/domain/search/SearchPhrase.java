package com.cardaddy.batch.domain.search;

import com.cardaddy.batch.domain.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 *
 * @author george
 */
@Data
@Entity
@Table(name = "search_phrase")
public class SearchPhrase extends BaseEntity {

    @Column(nullable = false)
    private String phrase;

    @Column(name = "search_origin", nullable = false)
    private String searchOrigin;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "search_date",nullable = false)
    private Date searchDate;

}
