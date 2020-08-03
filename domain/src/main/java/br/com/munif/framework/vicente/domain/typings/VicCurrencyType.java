package br.com.munif.framework.vicente.domain.typings;

public enum VicCurrencyType implements VicCurrencyTypeMethods {
    BRL {
        @Override
        public Double getDouble(VicMoney money) {
            return money.getAmount() / getDivisor();
        }

        @Override
        public Double getDouble(VicMoney money, Double unitValue) {
            return getDouble(money) * unitValue;
        }

        @Override
        public Double getDivisor() {
            return 100.0;
        }
    },
    USD {
        @Override
        public Double getDouble(VicMoney money) {
            return money.getAmount() / getDivisor();
        }

        @Override
        public Double getDouble(VicMoney money, Double unitValue) {
            return getDouble(money) * unitValue;
        }

        @Override
        public Double getDivisor() {
            return 100.0;
        }
    };
}
