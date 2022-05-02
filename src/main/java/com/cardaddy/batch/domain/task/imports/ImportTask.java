package com.cardaddy.batch.domain.task.imports;

import com.cardaddy.batch.domain.base.StatefulEntity;
import com.cardaddy.batch.domain.task.lookup.FileType;
import com.cardaddy.batch.domain.task.lookup.FtpAccount;
import com.cardaddy.batch.domain.task.lookup.ImportSystem;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
public class ImportTask extends StatefulEntity {

    private boolean zipfile;

    private String zipFilename;

    private String filename;

    private String customerFilename;

    @ManyToOne
    @JoinColumn(name = "file_type_id", nullable = false)
    private FileType fileType;

    @ManyToOne
    @JoinColumn(name = "import_system_id", nullable = false)
    private ImportSystem importSystem;

    @ManyToOne(cascade = CascadeType.ALL)
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
