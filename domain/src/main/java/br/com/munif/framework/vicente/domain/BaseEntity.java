/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.domain;

import br.com.munif.framework.vicente.core.VicThreadScope;
import static br.com.munif.framework.vicente.core.RightsHelper.*;
import br.com.munif.framework.vicente.core.UIDHelper;
import br.com.munif.framework.vicente.core.VicTenancyPolicy;
import br.com.munif.framework.vicente.core.VicTenancyType;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.Version;

import br.com.munif.framework.vicente.domain.typings.*;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.text.SimpleDateFormat;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

/**
 *
 * @author munif
 */
@MappedSuperclass
@TypeDefs({
    @TypeDef(name = "vicaddress", defaultForType = VicAddress.class, typeClass = VicAddressUserType.class)
    ,
        @TypeDef(name = "vicemail", defaultForType = VicEmail.class, typeClass = VicEmailUserType.class)
    ,
        @TypeDef(name = "vicphone", defaultForType = VicPhone.class, typeClass = VicPhoneUserType.class)
})
public class BaseEntity {

    public static boolean simpleId = false;

    @Id
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
        this.id = (simpleId ? UIDHelper.getSimpleID(this.getClass()) : UIDHelper.getUID());
        this.gi = getMainGi();
        this.ui = VicThreadScope.ui.get();
        this.oi = VicThreadScope.oi.get() != null ? VicThreadScope.oi.get() : "";
        this.rights = getDefault();
        this.extra = "Framework";
        this.cd = new Date();
        this.ud = new Date();
        active = true;
        //version=0; TODO Pesquisar

        //track();
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

    public String getStringRights() {
        Integer rights = this.rights != null ? this.rights : 0;
        String toReturn = "";
        toReturn += "ui:" + ui + "(";
        toReturn += (OWNER_READ & rights) > 0 ? "R" : "-";
        toReturn += (OWNER_UPDATE & rights) > 0 ? "U" : "-";
        toReturn += (OWNER_DELETE & rights) > 0 ? "D" : "-";
        toReturn += ") ";
        toReturn += "gi:" + gi + "(";
        toReturn += (GROUP_READ & rights) > 0 ? "R" : "-";
        toReturn += (GROUP_UPDATE & rights) > 0 ? "U" : "-";
        toReturn += (GROUP_DELETE & rights) > 0 ? "D" : "-";
        toReturn += ") ";
        toReturn += "o(";
        toReturn += (OTHER_READ & rights) > 0 ? "R" : "-";
        toReturn += (OTHER_UPDATE & rights) > 0 ? "U" : "-";
        toReturn += (OTHER_DELETE & rights) > 0 ? "D" : "-";
        toReturn += ") ";
        toReturn += "cd:" + format(cd) + " ";
        toReturn += "up:" + format(ud);

        return toReturn;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "id=" + id + ", oi=" + oi + ", gi=" + gi + ", ui=" + ui + ", rights=" + rights + ", extra=" + extra + ", cd=" + cd + ", ud=" + ud + ", active=" + active + ", version=" + version + '}';
    }

    public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

    public String format(Date d) {
        if (d == null) {
            return "null";
        } else {
            return sdf.format(d);
        }
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
        return ((OTHER_READ | (commonGroup() ? GROUP_READ : 0) | (isOwner() ? OWNER_READ : 0)) & rights) > 0;
    }
    
    @JsonGetter
    public String r(){
        return ""+(isOwner()?'O':'_')+(commonGroup()?'G':'_')+(canRead()?'R':'_')+(canUpdate()?'U':'_')+(canDelete()?'D':'_');
    }

    /**
     * Sobrescreve apenas o atributos com json ignore, optei por copiar na "mão"
     * pis são poucos.
     */
    public void overwriteJsonIgnoreFields(BaseEntity old) {
        if (old == null) {
            return;
        }
        this.oi = old.oi;
        this.gi = old.gi;
        this.ui = old.ui;
        this.rights = old.rights;
        this.cd = old.cd;
        this.ud = new Date();
        this.active = old.active;
    }

    public VicTenancyType getTencyPolicy() {
        VicTenancyPolicy vtp = this.getClass().getAnnotation(VicTenancyPolicy.class);

        if (vtp == null) {
            return VicTenancyType.GROUPS;
        }

        return vtp.value();
    }

//    public static Map<String, Long> instances = new HashMap<>();
//
//    public static boolean trackBaseEntities = true;
//
//    @Override
//    protected void finalize() throws Throwable {
//        Long l = instances.get(this.getClass().getCanonicalName());
//        instances.put(this.getClass().getCanonicalName(), new Long(l - 1));
//    }
//
//    private void track() {
//        Long l = instances.get(this.getClass().getCanonicalName());
//        if (l == null) {
//            l = new Long(0);
//        }
//        instances.put(this.getClass().getCanonicalName(), new Long(l+1));
//    }
//
//    public static void printStatistics() {
//        System.out.println("\n\n--------------------------------------");
//        System.out.println("   VICStatistics  " + System.currentTimeMillis());
//        System.out.println("--------------------------------------");
//
//        for (String k : instances.keySet()) {
//            System.out.println(k + "" + instances.get(k));
//        }
//        System.out.println("--------------------------------------\n\n");
//    }
//    private static Timer t;
//
//    public synchronized static void printStatisticsInInterval() {
//        if (t != null) {
//            return;
//        }
//        Timer t = new Timer();
//        t.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                printStatistics();
//            }
//        }, 60000, 60000);
//    }
}
