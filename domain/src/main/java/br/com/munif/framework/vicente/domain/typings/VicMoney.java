package br.com.munif.framework.vicente.domain.typings;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Objects;

public class VicMoney extends VicDomain {

    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private String locale;
    private VicRecurring recurring;

    public VicMoney() {
        this.amount = BigDecimal.ZERO;
        this.locale = "pt-BR";
        this.recurring = VicRecurring.NONE;
    }

    public VicMoney(VicMoney other) {
        if (other != null) {
            this.amount = other.amount;
            this.locale = other.locale;
            this.recurring = other.recurring;
        }
    }

    public VicMoney(BigDecimal amount, String locale, VicRecurring recurring) {
        this.amount = amount;
        this.locale = locale;
        this.recurring = recurring;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.amount);
        hash = 23 * hash + Objects.hashCode(this.locale);
        hash = 23 * hash + Objects.hashCode(this.recurring);
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
        final VicMoney other = (VicMoney) obj;
        if (!Objects.equals(this.amount, other.amount)) {
            return false;
        }
        if (!Objects.equals(this.locale, other.locale)) {
            return false;
        }
        return Objects.equals(this.recurring, other.recurring);
    }
    public VicRecurring getRecurring() {
        return recurring;
    }

    public void setRecurring(VicRecurring recurring) {
        this.recurring = recurring;
    }

    @JsonIgnore
    public Long getCents() {
        return this.amount != null ? this.amount.multiply(BigDecimal.valueOf(100)).longValue() : 0L;
    }
}