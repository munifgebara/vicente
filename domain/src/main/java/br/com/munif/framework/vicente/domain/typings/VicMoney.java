package br.com.munif.framework.vicente.domain.typings;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.Objects;

public class VicMoney extends VicDomain {

    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private VicCurrencyType type;
    private VicRecurring recurring;

    public VicMoney() {
        this.amount = BigDecimal.ZERO;
        this.type = VicCurrencyType.BRL;
        this.recurring = VicRecurring.NONE;
    }

    public VicMoney(VicMoney other) {
        if (other != null) {
            this.amount = other.amount;
            this.type = other.type;
            this.recurring = other.recurring;
        }
    }

    public VicMoney(BigDecimal amount, VicCurrencyType type, VicRecurring recurring) {
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

    public VicCurrencyType getType() {
        return type;
    }

    public void setType(VicCurrencyType type) {
        this.type = type;
    }

    public void setCurrencyType(String currencyType) {
        this.setType(VicCurrencyType.valueOf(currencyType));
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
}