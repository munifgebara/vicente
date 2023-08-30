/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.domain;

import br.com.munif.framework.vicente.core.VicThreadScope;

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
    @Column(name = "language")
    private String language;
    public static final ThreadLocal<Object> current = new ThreadLocal<>();

    public BaseConfiguration() {
        this.countryCode = "BR";
        this.timezone = "America/Sao_Paulo";
        this.language = "pt-BR";
    }

    public static <E extends BaseConfiguration> void setCurrent(E config) {
        if (VicThreadScope.language.get() == null)
            VicThreadScope.language.set(config.getLanguage());
        if (VicThreadScope.timezone.get() == null)
            VicThreadScope.timezone.set(config.getTimezone());
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

    public String getLanguage() {
        return language != null ? language : "pt-BR";
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BaseConfiguration that = (BaseConfiguration) o;
        return Objects.equals(getCountryCode(), that.getCountryCode()) && Objects.equals(getTimezone(), that.getTimezone())
                && Objects.equals(getLanguage(), that.getLanguage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCountryCode(), getTimezone(), getLanguage());
    }
}
