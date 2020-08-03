package br.com.munif.framework.vicente.domain.typings;

public interface VicCurrencyTypeMethods {
    Double getDouble(VicMoney money);
    Double getDouble(VicMoney money, Double unitValue);
    Double getDivisor();
}
