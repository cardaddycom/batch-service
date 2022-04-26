package com.cardaddy.batch.domain.task.imports;

import com.cardaddy.batch.domain.base.StatefulEntity;
import com.cardaddy.batch.domain.listing.VehicleListing;
import com.cardaddy.batch.domain.task.lookup.FileType;
import com.cardaddy.batch.domain.task.lookup.FtpAccount;
import com.cardaddy.batch.domain.task.lookup.ImportSystem;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class ImportTask extends StatefulEntity {

    private boolean zipfile;

    private String zipFilename;

    private String filename;

    private String customerFilename;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_type_id", nullable = false)
    private FileType fileType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "import_system_id", nullable = false)
    private ImportSystem importSystem;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ftp_account_id", nullable = false)
    private FtpAccount ftpAccount;

    @Override
    public String toString() {
        return "ImportTask{" +
                "zipfile=" + zipfile +
                ", zipFilename='" + zipFilename + '\'' +
                ", filename='" + filename + '\'' +
                ", customerFilename='" + customerFilename + '\'' +
                ", fileType=" + fileType +
                ", ftpAccount=" + ftpAccount +
                '}';
    }
}
