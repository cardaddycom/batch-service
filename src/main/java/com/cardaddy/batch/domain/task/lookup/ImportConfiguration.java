package com.cardaddy.batch.domain.task.lookup;

import com.cardaddy.batch.domain.base.BaseEntity;
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
@Table(name = "import_configuration")
public class ImportConfiguration extends BaseEntity {

    @Column(name = "file_field_name", nullable = false)
    private String fileFieldName;

    @ManyToOne
    @JoinColumn(name = "import_system_id", nullable = false)
    private ImportSystem importSystem;

    @ManyToOne
    @JoinColumn(name = "import_field_id", nullable = false)
    private ImportField importField;

    private Integer csvColumnPosition;

    @Override
    public String toString() {
        return "ImportConfiguration{" +
                "fileFieldName='" + fileFieldName + '\'' +
                ", csvColumnPosition=" + csvColumnPosition +
                '}';
    }
}
