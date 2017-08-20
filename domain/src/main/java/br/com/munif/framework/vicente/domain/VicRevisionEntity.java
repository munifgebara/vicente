package br.com.munif.framework.vicente.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;


@Entity
@RevisionEntity(VicRevisionListener.class)
public class VicRevisionEntity implements Serializable{
    
    @RevisionNumber
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @RevisionTimestamp
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date momento;
    private String ip;
    private String user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getMomento() {
        return momento;
    }

    public void setMomento(Date momento) {
        this.momento = momento;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
    
    
}