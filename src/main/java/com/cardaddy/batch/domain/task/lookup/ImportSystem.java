package com.cardaddy.batch.domain.task.lookup;

import com.cardaddy.batch.domain.base.BaseEntity;
import com.cardaddy.batch.domain.task.imports.ImportTask;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class ImportSystem extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "importSystem", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ImportConfiguration> importConfigurations;

    @OneToMany(mappedBy = "importSystem", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ImportTask> importTasks;

}
