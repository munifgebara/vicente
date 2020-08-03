package br.com.munif.framework.vicente.domain.typings;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

public class VicMoney extends VicDomain {

    private Long amount;
    @Enumerated(EnumType.STRING)
    private VicCurrencyType type;

    public VicMoney() {
        this.amount = 0L;
        this.type = VicCurrencyType.BRL;
    }

    public VicMoney(VicMoney other) {
        if (other != null) {
            this.amount = other.amount;
            this.type = other.type;
        }
    }

    public VicMoney(Long amount, VicCurrencyType type) {
        this.amount = amount;
        this.type = type;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
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

    public Double getDouble() {
        return type.getDouble(this);
    }

    public Double getDouble(VicCurrencyType currencyType) {
        return currencyType.getDouble(this);
    }

    public Double getDouble(VicCurrencyType currencyType, Double unitValue) {
        return currencyType.getDouble(this, unitValue);
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