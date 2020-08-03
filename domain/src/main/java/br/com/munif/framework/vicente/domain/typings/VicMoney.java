package br.com.munif.framework.vicente.domain.typings;


import java.util.Objects;

public class VicMoney extends VicDomain {

    private Long amount;
    private VicCurrencyType currencyType;

    public VicMoney() {
        this.amount = 0L;
        this.currencyType = VicCurrencyType.BRL;
    }

    public VicMoney(VicMoney other) {
        if (other != null) {
            this.amount = other.amount;
            this.currencyType = other.currencyType;
        }
    }

    public VicMoney(Long amount, VicCurrencyType currencyType) {
        this.amount = amount;
        this.currencyType = currencyType;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public VicCurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(VicCurrencyType currencyType) {
        this.currencyType = currencyType;
    }


    public void setCurrencyType(String currencyType) {
        this.setCurrencyType(VicCurrencyType.valueOf(currencyType));
    }

    public Double getDouble() {
        return currencyType.getDouble(this);
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
        hash = 23 * hash + Objects.hashCode(this.currencyType);
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
        if (!Objects.equals(this.currencyType, other.currencyType)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "VicEmail{" +
                "amount='" + amount + '\'' +
                ", currencyType='" + currencyType + '\'' +
                '}';
    }
}