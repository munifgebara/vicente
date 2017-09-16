package br.com.munif.framework.vicente.domain.typings;

public enum SocialNetworking {
    EMAIL,
    FACEBOOK,
    GMAIL,
    TWITTER,
    PINTEREST;

    public boolean isValid(String value) {
        switch (this) {
            case EMAIL:
                return value.matches(RegexTyping.EMAIL.getValue());
            default:
                return true;
        }
    }
}
