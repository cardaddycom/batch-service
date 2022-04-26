package com.cardaddy.batch.domain.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author george
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "state_location")
public class State implements Serializable {

    @Id
    @Column(name = "state_id", length = 2)
    private String stateId;

    @FullTextField(projectable = Projectable.YES)
    private String name;

    public State(String stateId) {
        this.stateId = stateId;
    }

}
