package br.com.munif.framework.vicente.core.phonetics;

public enum PhoneticValues implements PhoneticMethods {
    PT_BR {
        @Override
        public PhoneticTranslator getTranslator() {
            return new PortuguesePhonetic();
        }
    }
}
