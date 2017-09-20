package br.com.munif.framework.vicente.domain.typings;

import java.util.Objects;

public class VicAddress extends VicDomain {

    private String zipCode;
    private String premisseType;
    private String premisse;
    private String number;
    private String information;
    private String neighbourhood;
    private String localization;
    private String state;
    private String country;
    private Double latitude;
    private Double longitude;
    private String formalCode;
    private String stateCode;

    public VicAddress() {

    }

    public VicAddress(VicAddress other) {
        if (other != null) {
            this.zipCode = other.zipCode;
            this.premisseType = other.premisseType;
            this.premisse = other.premisse;
            this.number = other.number;
            this.information = other.information;
            this.neighbourhood = other.neighbourhood;
            this.localization = other.localization;
            this.state = other.state;
            this.country = other.country;
            this.latitude = other.latitude;
            this.longitude = other.longitude;
            this.formalCode = other.formalCode;
            this.stateCode = other.stateCode;
        }
    }

    public VicAddress(String zipCode, String premisseType, String premisse, String number, String information, String neighbourhood, String localization, String state, String country) {
        this.zipCode = zipCode;
        this.premisseType = premisseType;
        this.premisse = premisse;
        this.number = number;
        this.information = information;
        this.neighbourhood = neighbourhood;
        this.localization = localization;
        this.state = state;
        this.country = country;
    }

    public VicAddress(String zipCode, String premisseType, String premisse, String number, String information, String neighbourhood, String localization, String state, String country, Double latitude, Double longitude, String formalCode) {
        this.zipCode = zipCode;
        this.premisseType = premisseType;
        this.premisse = premisse;
        this.number = number;
        this.information = information;
        this.neighbourhood = neighbourhood;
        this.localization = localization;
        this.state = state;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.formalCode = formalCode;
    }

    public VicAddress(String zipCode, String premisseType, String premisse, String number, String information, String neighbourhood, String localization, String state, String country, Double latitude, Double longitude, String formalCode, String stateCode) {
        this.zipCode = zipCode;
        this.premisseType = premisseType;
        this.premisse = premisse;
        this.number = number;
        this.information = information;
        this.neighbourhood = neighbourhood;
        this.localization = localization;
        this.state = state;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.formalCode = formalCode;
        this.stateCode = stateCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPremisseType() {
        return premisseType;
    }

    public void setPremisseType(String premisseType) {
        this.premisseType = premisseType;
    }

    public String getPremisse() {
        return premisse;
    }

    public void setPremisse(String premisse) {
        this.premisse = premisse;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getFormalCode() {
        return formalCode;
    }

    public void setFormalCode(String formalCode) {
        this.formalCode = formalCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.zipCode);
        hash = 23 * hash + Objects.hashCode(this.premisseType);
        hash = 23 * hash + Objects.hashCode(this.premisse);
        hash = 23 * hash + Objects.hashCode(this.number);
        hash = 23 * hash + Objects.hashCode(this.information);
        hash = 23 * hash + Objects.hashCode(this.neighbourhood);
        hash = 23 * hash + Objects.hashCode(this.localization);
        hash = 23 * hash + Objects.hashCode(this.state);
        hash = 23 * hash + Objects.hashCode(this.country);
        hash = 23 * hash + Objects.hashCode(this.latitude);
        hash = 23 * hash + Objects.hashCode(this.longitude);
        hash = 23 * hash + Objects.hashCode(this.formalCode);
        hash = 23 * hash + Objects.hashCode(this.stateCode);
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
        final VicAddress other = (VicAddress) obj;
        if (!Objects.equals(this.zipCode, other.zipCode)) {
            return false;
        }
        if (!Objects.equals(this.premisseType, other.premisseType)) {
            return false;
        }
        if (!Objects.equals(this.premisse, other.premisse)) {
            return false;
        }
        if (!Objects.equals(this.number, other.number)) {
            return false;
        }
        if (!Objects.equals(this.information, other.information)) {
            return false;
        }
        if (!Objects.equals(this.neighbourhood, other.neighbourhood)) {
            return false;
        }
        if (!Objects.equals(this.localization, other.localization)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.country, other.country)) {
            return false;
        }
        if (!Objects.equals(this.latitude, other.latitude)) {
            return false;
        }
        if (!Objects.equals(this.longitude, other.longitude)) {
            return false;
        }
        if (!Objects.equals(this.formalCode, other.formalCode)) {
            return false;
        }
        if (!Objects.equals(this.stateCode, other.stateCode)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "VicAddress{" + "zipCode=" + zipCode + ", premisseType=" + premisseType + ", premisse=" + premisse + ", number=" + number + ", information=" + information + ", neighbourhood=" + neighbourhood + ", localization=" + localization + ", state=" + state + ", country=" + country + ", latitude=" + latitude + ", longitude=" + longitude + ", formalCode=" + formalCode + ", stateCode=" + stateCode + '}';
    }

}