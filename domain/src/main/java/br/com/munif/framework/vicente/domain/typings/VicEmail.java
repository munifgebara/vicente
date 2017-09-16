package br.com.munif.framework.vicente.domain.typings;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

public class VicEmail extends VicDomain {

    private String description;
    @Enumerated(EnumType.STRING)
    private SocialNetworking social;

    public VicEmail() {

    }

    public VicEmail(VicEmail other) {
        if (other != null) {
            this.description = other.description;
            this.social = other.social;
        }
    }

    public VicEmail(String description, String social) {
        this.description = description;
        this.social = SocialNetworking.valueOf(social);
    }

    public VicEmail(String description, SocialNetworking social) {
        this.description = description;
        this.social = social;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SocialNetworking getSocial() {
        return social;
    }

    public void setSocial(SocialNetworking social) {
        this.social = social;
    }

    public Boolean isValid() {
        return this.getSocial().isValid(this.getDescription());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.description);
        hash = 23 * hash + Objects.hashCode(this.social);
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
        if (!Objects.equals(this.social, other.social)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "VicEmail{" +
                "description='" + description + '\'' +
                ", social='" + social + '\'' +
                '}';
    }
}