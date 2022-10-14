package br.com.munif.framework.vicente.domain.typings;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

public class VicEmail extends VicDomain {

    private String description;
    @Enumerated(EnumType.STRING)
    private SocialNetworking type;

    private Boolean invalidEmail;

    private String invalidEmailReason;


    public VicEmail() {

    }

    public VicEmail(VicEmail other) {
        if (other != null) {
            this.description = other.description;
            this.type = other.type;
        }
    }

    public VicEmail(String description, String type) {
        this.description = description;
        this.type = type != null ? SocialNetworking.valueOf(type) : null;
    }

    public VicEmail(String description) {
        this.description = description;
        this.type = SocialNetworking.EMAIL;
    }

    public VicEmail(String description, SocialNetworking type) {
        this.description = description;
        this.type = type;
    }

    public VicEmail(String description, String type, Boolean invalidEmail, String invalidEmailReason) {
        this.description = description;
        this.type = type != null ? SocialNetworking.valueOf(type) : null;
        this.invalidEmail = invalidEmail;
        this.invalidEmailReason = invalidEmailReason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SocialNetworking getType() {
        return type;
    }

    public void setType(SocialNetworking type) {
        this.type = type;
    }

    public Boolean getInvalidEmail() {
        return invalidEmail;
    }

    public void setInvalidEmail(Boolean invalidEmail) {
        this.invalidEmail = invalidEmail;
    }

    public String getInvalidEmailReason() {
        return invalidEmailReason;
    }

    public void setInvalidEmailReason(String invalidEmailReason) {
        this.invalidEmailReason = invalidEmailReason;
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
        final VicEmail other = (VicEmail) obj;
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
                ", social='" + type + '\'' +
                '}';
    }
}