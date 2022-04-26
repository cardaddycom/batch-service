package com.cardaddy.batch.domain.task.imports;

import com.cardaddy.batch.domain.account.DealerProfile;
import com.cardaddy.batch.domain.base.StatefulEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 *
 * @author george
 */
@Data
@Entity
@NoArgsConstructor
@Table(name ="import_task_dealer", uniqueConstraints
        = @UniqueConstraint(columnNames = {"dealer_profile_id", "import_task_id"}))
public class ImportTaskDealer extends StatefulEntity {

    @ManyToOne
    @JoinColumn(name = "import_task_id", nullable = false)
    private ImportTask importTask;

    @ManyToOne
    @JoinColumn(name = "dealer_profile_id", nullable = false)
    private DealerProfile dealerProfile;

    @Column(nullable = false)
    private String dmsId;

    @Override
    public String toString() {
        return "ImportTaskDealer{" + "importTask=" + importTask  + '}';
    }

}
