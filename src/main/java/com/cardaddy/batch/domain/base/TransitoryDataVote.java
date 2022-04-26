
package com.cardaddy.batch.domain.base;

import java.util.Date;

/**
 *
 * @author George
 */
public interface TransitoryDataVote {

    boolean isActive();

    void setActive(boolean active);

    int getVote();

    void setVote(int vote);

    Long getId();

    Date getVoteExpiration();

    void setVoteExpiration(Date voteExpiration);

}
