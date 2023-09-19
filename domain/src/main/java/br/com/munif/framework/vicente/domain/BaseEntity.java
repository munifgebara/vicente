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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.IOException;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Objects;

import static br.com.munif.framework.vicente.core.RightsHelper.*;

/**
 * @author munif
 */
@MappedSuperclass
@TypeDefs({
        @TypeDef(name = "vicaddress", defaultForType = VicAddress.class, typeClass = VicAddressUserType.class),
        @TypeDef(name = "vicemail", defaultForType = VicEmail.class, typeClass = VicEmailUserType.class),
        @TypeDef(name = "vicphone", defaultForType = VicPhone.class, typeClass = VicPhoneUserType.class),
        @TypeDef(name = "vicdocument", defaultForType = VicDocument.class, typeClass = VicDocumentUserType.class),
        @TypeDef(name = "vicmoney", defaultForType = VicMoney.class, typeClass = VicMoneyUserType.class),
        @TypeDef(name = "vicfile", defaultForType = VicFile.class, typeClass = VicFileUserType.class),
        @TypeDef(name = "vicsocial", defaultForType = VicSocial.class, typeClass = VicSocialUserType.class),
})
public class BaseEntity implements Serializable, IBaseEntity {

    public static boolean useSimpleId = false;

    @Id
    @Column(length = 150)
    protected String id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected String oi;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected String gi;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected String ui;

    @JsonIgnore
    protected String phonetic;

    @JsonIgnore
    protected Integer rights;

    @Column(length = 500)
    protected String extra;

    protected ZonedDateTime cd;

    protected ZonedDateTime ud;

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
        VicTenancyPolicy vtp = this.getClass().getAnnotation(VicTenancyPolicy.class);
        rights = vtp != null ? vtp.rights() : RightsHelper.getScopeDefault();
        cd = ZonedDateTime.now();
        ud = ZonedDateTime.now();
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

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
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

    public BaseEntity extra(String e) {
        this.extra = e;
        return this;
    }

    public ZonedDateTime getCd() {
        return cd;
    }

    public void setCd(ZonedDateTime cd) {
        this.cd = cd;
    }

    public ZonedDateTime getUd() {
        return ud;
    }

    public void setUd(ZonedDateTime ud) {
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

    @JsonGetter
    public String getClassName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "id=" + id + ", oi=" + oi + ", gi=" + gi + ", ui=" + ui + ", rights=" + rights + ", extra=" + extra + ", cd=" + cd + ", ud=" + ud + ", active=" + active + ", version=" + version + '}';
    }

    public boolean isOwner() {
        String token_ui = VicThreadScope.ui.get();
        return token_ui != null && token_ui.equals(ui);
    }

    public boolean commonGroup() {
        String token_gi = VicThreadScope.gi.get();
        return gi != null && token_gi != null && token_gi.contains(gi + ',');
    }

    public boolean canDelete() {
        return canChangeBeingOnlyOi() || ((OTHER_DELETE | (commonGroup() ? GROUP_DELETE : 0) | (isOwner() ? OWNER_DELETE : 0)) & rights) > 0;
    }

    public boolean canUpdate() {
        return canChangeBeingOnlyOi() || ((OTHER_UPDATE | (commonGroup() ? GROUP_UPDATE : 0) | (isOwner() ? OWNER_UPDATE : 0)) & rights) > 0;
    }

    public boolean canRead() {
        boolean commonGroup = commonGroup();
        boolean isOwner = isOwner();
        return canChangeBeingOnlyOi()
                || ((OTHER_READ | (commonGroup ? GROUP_READ : 0) | (isOwner ? OWNER_READ : 0)) & rights) > 0;
    }

    public boolean canChangeBeingOnlyOi() {
        VicTenancyPolicy annotation = this.getClass().getAnnotation(VicTenancyPolicy.class);
        VicTenancyType value = annotation != null ? annotation.value() : VicTenancyType.NONE;
        return (VicTenancyType.ONLY_HIERARCHICAL_TOP_DOWN.equals(value) && this.getOi().startsWith(VicThreadScope.oi.get()))
                || (VicTenancyType.ONLY_ORGANIZATIONAL.equals(value) && this.getOi().startsWith(VicThreadScope.getTopOi()));
    }

    @JsonGetter
    public String r() {
        try {
            return "" + (isOwner() ? 'O' : '_') + (commonGroup() ? 'G' : '_') + (canRead() ? 'R' : '_') + (canUpdate() ? 'U' : '_') + (canDelete() ? 'D' : '_');
        } catch (RuntimeException e) {
            return null;
        }
    }

    public VicTenancyType getTencyPolicy() {
        VicTenancyPolicy vtp = this.getClass().getAnnotation(VicTenancyPolicy.class);
        if (vtp == null) {
            return VicTenancyType.GROUPS;
        }
        return vtp.value();
    }

    public Map<String, Object> getExtra() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(this.extra, Map.class);
        } catch (RuntimeException | IOException e) {
            return null;
        }
    }

    public void setExtra(Map<String, Object> metadata) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.extra = objectMapper.writeValueAsString(metadata);
        } catch (JsonProcessingException ignored) {
        }
    }
    public void setOnVicThreadScope() {
        VicThreadScope.gi.set(this.gi);
        VicThreadScope.ui.set(this.ui);
        VicThreadScope.oi.set(this.oi);
    }
}
