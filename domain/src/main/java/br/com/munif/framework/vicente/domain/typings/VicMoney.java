package br.com.munif.framework.vicente.domain.typings;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;

public class VicMoney extends VicDomain {

    private BigDecimal amount;
    private String code;
    private VicRecurring recurring;

    public VicMoney() {
        this.amount = BigDecimal.ZERO;
        this.code = "BRL";
        this.recurring = VicRecurring.NONE;
    }

    public VicMoney(VicMoney other) {
        if (other != null) {
            this.amount = other.amount;
            this.code = other.code;
            this.recurring = other.recurring;
        }
    }

    public VicMoney(Double amount) {
        this();
        this.setAmount(BigDecimal.valueOf(amount));
    }

    public VicMoney(BigDecimal amount, String code, VicRecurring recurring) {
        this.amount = amount;
        this.code = code;
        this.recurring = recurring;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @JsonSetter
    public void setType(String type) {
        if (type != null)
            try {
                this.code = getCurrencyInstance(type).getCurrencyCode();
            } catch (RuntimeException ignored) {}
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.amount);
        hash = 23 * hash + Objects.hashCode(this.code);
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
        if (!Objects.equals(this.code, other.code)) {
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
    @JsonIgnore
    public void setCents(Long cents) {
        this.amount = BigDecimal.valueOf(cents / 100);
    }

    @JsonIgnore
    public String getFormatted() {
        if (code == null) return new VicMoney().getFormatted();
        return NumberFormat.getCurrencyInstance().format(amount.doubleValue());
    }

    @JsonGetter
    public String getSymbol() {
        if (code == null) return "R$";
        return Currency.getInstance(code).getSymbol();
    }

    private Currency getCurrencyInstance(String type) {
        try {
            return Currency.getInstance(Locale.forLanguageTag(type));
        } catch (RuntimeException exception) {
            return Currency.getInstance(Locale.forLanguageTag("pt-BR"));
        }
    }
}