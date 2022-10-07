package br.com.munif.framework.vicente.domain.typings;

import java.util.Objects;

public class VicFile extends VicDomain {

    private String id;
    private String bucket;

    public VicFile() {
    }

    public VicFile(VicFile other) {
        if (other != null) {
            this.id = other.id;
            this.bucket = other.bucket;
        }
    }

    public VicFile(String id, String bucket) {
        this.id = id;
        this.bucket = bucket;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.id);
        hash = 23 * hash + Objects.hashCode(this.bucket);
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
        final VicFile other = (VicFile) obj;
        if (!Objects.equals(this.id, other.id)) {
            if (!Objects.equals(this.bucket, other.bucket)) {
                return false;
            }
            return true;
        }
        return false;
    }
}