package br.com.munif.framework.vicente.domain.typings;

import br.com.munif.framework.vicente.domain.BaseConfiguration;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

public class VicPhone extends VicDomain {

    private String description;
    @Enumerated(EnumType.STRING)
    private PhoneType type;
    private Integer countryCode;
    private String regionCode;

    public VicPhone() {
        this.setRegionCode(BaseConfiguration.getCurrent().getCountryCode());
    }

    public VicPhone(VicPhone other) {
        if (other != null) {
            this.description = other.description;
            this.type = other.type;
            this.countryCode = other.countryCode;
            this.regionCode = other.regionCode;
        }
    }

    public VicPhone(String description, PhoneType type) {
        this.description = description;
        this.type = type;
    }


    public VicPhone(String description, String type, Integer countryCode, String regionCode) {
        this.description = description;
        if (type != null)
            this.type = PhoneType.valueOf(type);
        else this.type = PhoneType.CELLPHONE;
        this.countryCode = countryCode;
        this.regionCode = regionCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PhoneType getType() {
        return type;
    }

    public void setType(PhoneType type) {
        this.type = type;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
        if (regionCode != null)
            this.countryCode = PhoneNumberUtil.getInstance().getCountryCodeForRegion(regionCode);
    }

    public Integer getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
        if (countryCode != null)
            this.regionCode = PhoneNumberUtil.getInstance().getRegionCodeForCountryCode(countryCode);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.description);
        hash = 23 * hash + Objects.hashCode(this.type);
        hash = 23 * hash + Objects.hashCode(this.countryCode);
        hash = 23 * hash + Objects.hashCode(this.regionCode);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VicPhone other = (VicPhone) obj;
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.countryCode, other.countryCode)) {
            return false;
        }
        if (!Objects.equals(this.regionCode, other.regionCode)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "VicEmail{" +
                "description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", countryCode='" + type + '\'' +
                ", regionCode='" + type + '\'' +
                '}';
    }
}