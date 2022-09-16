package br.com.munif.framework.vicente.core.phonetics;

public enum PhoneticValues implements PhoneticMethods {
    PT_BR {
        @Override
        public PhoneticTranslator getTranslator() {
            return new PortuguesePhonetic();
        }
    };

    public static PhoneticMethods resolve(String language) {
        return PT_BR;
    }
}
