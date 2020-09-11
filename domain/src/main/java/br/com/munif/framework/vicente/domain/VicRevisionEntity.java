package br.com.munif.framework.vicente.domain;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;


@Entity
@RevisionEntity(VicRevisionListener.class)
@Table(name = "vic_revision_entity")
public class VicRevisionEntity implements Serializable {

    @Id
    @RevisionNumber
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @RevisionTimestamp
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "rev_timestamp")
    private Date timestamp;
    @Column(name = "rev_ip")
    private String ip;
    @Column(name = "rev_ui")
    private String ui;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUi() {
        return ui;
    }

    public void setUi(String user) {
        this.ui = user;
    }


}
