package br.com.munif.framework.vicente.domain.typings;

public enum VicCurrencyType implements VicCurrencyTypeMethods {
    BRL {
        @Override
        public Double getDivisor() {
            return 100.0;
        }
    },
    USD {
        @Override
        public Double getDivisor() {
            return 100.0;
        }
    };
}
