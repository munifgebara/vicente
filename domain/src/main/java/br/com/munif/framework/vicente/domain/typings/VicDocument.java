package br.com.munif.framework.vicente.domain.typings;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

public class VicDocument extends VicDomain {

    private String description;
    @Enumerated(EnumType.STRING)
    private DocumentType type;

    public VicDocument() {

    }

    public VicDocument(VicDocument other) {
        if (other != null) {
            this.description = other.description;
            this.type = other.type;
        }
    }

    public VicDocument(String description, DocumentType type) {
        this.description = description;
        this.type = type;
    }


    public VicDocument(String description, String type) {
        this.description = description;
        this.type = DocumentType.valueOf(type);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DocumentType getType() {
        return type;
    }

    public void setType(DocumentType type) {
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
        final VicDocument other = (VicDocument) obj;
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
        return "VicDocument{" +
                "description='" + description + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}