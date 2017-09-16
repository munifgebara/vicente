package br.com.munif.framework.vicente.domain.typings;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

public class VicPhone extends VicDomain {

    private String description;
    @Enumerated(EnumType.STRING)
    private PhoneType type;

    public VicPhone() {

    }

    public VicPhone(VicPhone other) {
        if (other != null) {
            this.description = other.description;
            this.type = other.type;
        }
    }

    public VicPhone(String description, PhoneType type) {
        this.description = description;
        this.type = type;
    }


    public VicPhone(String description, String type) {
        this.description = description;
        this.type = PhoneType.valueOf(type);
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.description);
        hash = 23 * hash + Objects.hashCode(this.type);
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
        return true;
    }

    @Override
    public String toString() {
        return "VicEmail{" +
                "description='" + description + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}