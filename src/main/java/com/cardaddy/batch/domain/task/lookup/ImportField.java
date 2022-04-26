package com.cardaddy.batch.domain.task.lookup;

import com.cardaddy.batch.domain.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 *
 * @author george
 */
@Data
@Entity
@Table(name = "import_field")
public class ImportField extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "importField", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ImportConfiguration> importConfigurations;

}
