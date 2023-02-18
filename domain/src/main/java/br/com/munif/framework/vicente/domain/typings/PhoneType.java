package br.com.munif.framework.vicente.domain.typings;

public enum PhoneType {
    CELLPHONE,
    LANDLINE,
    FAX;

    public boolean isValid(String value) {
        switch (this) {
            case CELLPHONE:
                return value.matches(RegexTyping.PHONE_BR.getValue());
            default:
                return true;
        }
    }
}
