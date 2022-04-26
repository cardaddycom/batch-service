package com.cardaddy.batch.domain.task.lookup;

import com.cardaddy.batch.domain.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@ToString
public class FtpAccount extends BaseEntity {

    @Column(name = "ftp_user", length = 45, nullable = false, unique = true)
    private String ftpUser;

    @Column(name = "ftp_pass", length = 45, nullable = false)
    private String ftpPass;

    @Column(name = "ftp_host",length = 255, nullable = false, unique = true)
    private String ftpHost;

}
