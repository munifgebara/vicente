/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.domain;

import br.com.munif.framework.vicente.core.VicThreadScope;
import br.com.munif.framework.vicente.core.RightsHelper;
import br.com.munif.framework.vicente.core.UIDHelper;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.Version;

import br.com.munif.framework.vicente.domain.typings.*;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.envers.Audited;

/**
 *
 * @author munif
 */
@MappedSuperclass
@TypeDefs({
        @TypeDef(name = "vicaddress", defaultForType = VicAddress.class, typeClass = VicAddressUserType.class),
        @TypeDef(name = "vicemail", defaultForType = VicEmail.class, typeClass = VicEmailUserType.class),
        @TypeDef(name = "vicphone", defaultForType = VicPhone.class, typeClass = VicPhoneUserType.class)
})
public class BaseEntity {

    @Id
    protected String id;

    protected String gi;

    protected String ui;

    protected Integer rights;

    protected String extra;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    protected Date cd;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    protected Date ud;

    protected Boolean active;

    @Version
    private Integer version;

    public BaseEntity() {
        this.id = UIDHelper.getUID();
        this.gi = RightsHelper.getMainGi();
        this.ui = VicThreadScope.ui.get();
        
        this.rights = RightsHelper.getDefault();
        this.extra = "Framework";
        this.cd = new Date();
        this.ud = new Date();
        active=true;
        //version=0; TODO Pesquisar
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGi() {
        return gi;
    }

    public void setGi(String gi) {
        this.gi = gi;
    }

    public String getUi() {
        return ui;
    }

    public void setUi(String ui) {
        this.ui = ui;
    }

    public Integer getRights() {
        return rights;
    }

    public void setRights(Integer rights) {
        this.rights = rights;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Date getCd() {
        return cd;
    }

    public void setCd(Date cd) {
        this.cd = cd;
    }

    public Date getUd() {
        return ud;
    }

    public void setUd(Date ud) {
        this.ud = ud;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.version);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BaseEntity other = (BaseEntity) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.version, other.version)) {
            return false;
        }
        return true;
    }
    
    public String getClassName(){
        return this.getClass().getSimpleName();
    }
    
    public String getStringRights(){
        if (rights==null){
            return "--- --- ---";
        }
        String toReturn="";
        toReturn+=(RightsHelper.OWNER_READ & rights)>0?"R":"-";
        toReturn+=(RightsHelper.OWNER_UPDATE & rights)>0?"U":"-";
        toReturn+=(RightsHelper.OWNER_DELETE & rights)>0?"D":"-";
        toReturn+=" ";
        toReturn+=(RightsHelper.GROUP_READ & rights)>0?"R":"-";
        toReturn+=(RightsHelper.GROUP_UPDATE & rights)>0?"U":"-";
        toReturn+=(RightsHelper.GROUP_DELETE & rights)>0?"D":"-";
        toReturn+=" ";
        toReturn+=(RightsHelper.OTHER_READ & rights)>0?"R":"-";
        toReturn+=(RightsHelper.OTHER_UPDATE & rights)>0?"U":"-";
        toReturn+=(RightsHelper.OTHER_DELETE & rights)>0?"D":"-";
        
        return toReturn;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()+"{" + "id=" + id + ", gi=" + gi + ", ui=" + ui + ", rights=" + rights + ", extra=" + extra + ", cd=" + cd + ", ud=" + ud + ", active=" + active + ", version=" + version + '}';
    }


}
