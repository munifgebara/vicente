/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author munif
 */
@MappedSuperclass
public class BaseConfiguration extends BaseEntity implements Serializable {
    @Column(name = "country_code")
    private String countryCode;
    @Column(name = "timezone")
    private String timezone;
    public static final ThreadLocal<BaseConfiguration> current = ThreadLocal.withInitial(BaseConfiguration::new);

    public BaseConfiguration() {
        this.countryCode = "BR";
        this.timezone = "America/Sao_Paulo";
    }

    public static void setCurrent(BaseConfiguration config) {
        current.set(config);
    }

    public static <E extends BaseConfiguration> E getCurrent() {
        return (E) current.get();
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getTimezone() {
        return timezone != null ? timezone : "America/Sao_Paulo";
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BaseConfiguration that = (BaseConfiguration) o;
        return Objects.equals(getCountryCode(), that.getCountryCode()) && Objects.equals(getTimezone(), that.getTimezone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCountryCode(), getTimezone());
    }
}
