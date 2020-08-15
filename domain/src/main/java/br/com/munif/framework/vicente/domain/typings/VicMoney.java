package br.com.munif.framework.vicente.domain.typings;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.Objects;

public class VicMoney extends VicDomain {

    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private VicCurrencyType type;

    public VicMoney() {
        this.amount = BigDecimal.ZERO;
        this.type = VicCurrencyType.BRL;
    }

    public VicMoney(VicMoney other) {
        if (other != null) {
            this.amount = other.amount;
            this.type = other.type;
        }
    }

    public VicMoney(BigDecimal amount, VicCurrencyType type) {
        this.amount = amount;
        this.type = type;
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
        return true;
    }

    @Override
    public String toString() {
        return "VicEmail{" +
                "amount='" + amount + '\'' +
                ", currencyType='" + type + '\'' +
                '}';
    }
}