package br.com.munif.framework.vicente.domain.typings;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class VicMoney extends VicDomain {

    private BigDecimal amount;
    private String type;
    private VicRecurring recurring;

    public VicMoney() {
        this.amount = BigDecimal.ZERO;
        this.type = "pt-BR";
        this.recurring = VicRecurring.NONE;
    }

    public VicMoney(VicMoney other) {
        if (other != null) {
            this.amount = other.amount;
            this.type = other.type;
            this.recurring = other.recurring;
        }
    }

    public VicMoney(Double amount) {
        this();
        this.setAmount(BigDecimal.valueOf(amount));
    }

    public VicMoney(BigDecimal amount, String type, VicRecurring recurring) {
        this.amount = amount;
        this.type = type;
        this.recurring = recurring;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.amount);
        hash = 23 * hash + Objects.hashCode(this.type);
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
        if (!Objects.equals(this.type, other.type)) {
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
    public String getFormatted() {
        if (type == null) return new VicMoney().getFormatted();
        String[] split = type.split("-");
        if (split.length > 1)
            return NumberFormat.getCurrencyInstance(new Locale(split[0], split[1])).format(amount.doubleValue());
        else if (split.length == 1)
            return NumberFormat.getCurrencyInstance(new Locale(split[0])).format(amount.doubleValue());
        return new VicMoney().getFormatted();
    }
}