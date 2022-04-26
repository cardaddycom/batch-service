package com.cardaddy.batch.domain.base;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 *
 * @author George
 */
@Data
@MappedSuperclass
public class VoteEntity extends StatefulEntity implements TransitoryDataVote {

    private int vote;

    @Column(name = "vote_expiration", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date voteExpiration;

}
