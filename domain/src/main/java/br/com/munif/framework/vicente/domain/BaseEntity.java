/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.domain;

import br.com.munif.framework.vicente.core.*;
import br.com.munif.framework.vicente.domain.typings.*;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

import static br.com.munif.framework.vicente.core.RightsHelper.*;

/**
 * @author munif
 */
@MappedSuperclass
@TypeDefs({
        @TypeDef(name = "vicaddress", defaultForType = VicAddress.class, typeClass = VicAddressUserType.class),
        @TypeDef(name = "vicemail", defaultForType = VicEmail.class, typeClass = VicEmailUserType.class),
        @TypeDef(name = "vicphone", defaultForType = VicPhone.class, typeClass = VicPhoneUserType.class)
})
public class BaseEntity extends HateosBaseEntity {

    public static boolean useSimpleId = false;

    @Id
    @Column(length = 100)
    protected String id;

    @JsonIgnore
    protected String oi;

    @JsonIgnore
    protected String gi;

    @JsonIgnore
    protected String ui;

    @JsonIgnore
    protected Integer rights;

    protected String extra;

    @JsonIgnore
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    protected Date cd;

    @JsonIgnore
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    protected Date ud;

    @JsonIgnore
    protected Boolean active;

    @Version
    private Integer version;

    public BaseEntity() {
        init();
    }

    private void init() {
        if (useSimpleId) {
            id = UIDHelper.getSimpleID(this.getClass());
        } else {
            id = UIDHelper.getUID();
        }
        gi = stringNull(RightsHelper.getMainGi());
        ui = stringNull(VicThreadScope.ui.get());
        oi = VicThreadScope.oi.get() != null ? VicThreadScope.oi.get() : "";
        rights = RightsHelper.getDefault();
        extra = "Framework";
        cd = new Date();
        ud = new Date();
        active = true;
        version = null;
    }


    private String stringNull(String v) {
        if (v == null) {
            return "_NULL_";
        } else {
            return v;
        }
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

    public String getOi() {
        return oi;
    }

    public void setOi(String oi) {
        this.oi = oi;
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

    public BaseEntity extra(String e) {
        this.extra = e;
        return this;
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

    public String getClassName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "id=" + id + ", oi=" + oi + ", gi=" + gi + ", ui=" + ui + ", rights=" + rights + ", extra=" + extra + ", cd=" + cd + ", ud=" + ud + ", active=" + active + ", version=" + version + '}';
    }

    public boolean isOwner() {
        String token_ui = VicThreadScope.ui.get();
        return ui != null && token_ui != null && token_ui.equals(ui);
    }

    public boolean commonGroup() {
        String token_gi = VicThreadScope.gi.get();
        return gi != null && token_gi != null && token_gi.contains(gi + ',');
    }

    public boolean canDelete() {
        return ((OTHER_DELETE | (commonGroup() ? GROUP_DELETE : 0) | (isOwner() ? OWNER_DELETE : 0)) & rights) > 0;
    }

    public boolean canUpdate() {
        return ((OTHER_UPDATE | (commonGroup() ? GROUP_UPDATE : 0) | (isOwner() ? OWNER_UPDATE : 0)) & rights) > 0;
    }

    public boolean canRead() {
        boolean commonGroup = commonGroup();
        boolean isOwner = isOwner();
        return ((OTHER_READ | (commonGroup ? GROUP_READ : 0) | (isOwner ? OWNER_READ : 0)) & rights) > 0;
    }

    @JsonGetter
    public String r() {
        return "" + (isOwner() ? 'O' : '_') + (commonGroup() ? 'G' : '_') + (canRead() ? 'R' : '_') + (canUpdate() ? 'U' : '_') + (canDelete() ? 'D' : '_');
    }

    public VicTenancyType getTencyPolicy() {
        VicTenancyPolicy vtp = this.getClass().getAnnotation(VicTenancyPolicy.class);
        if (vtp == null) {
            return VicTenancyType.GROUPS;
        }
        return vtp.value();
    }

}
