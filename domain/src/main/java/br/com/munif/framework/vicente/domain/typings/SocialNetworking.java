package br.com.munif.framework.vicente.domain.typings;

public enum SocialNetworking {
    EMAIL,
    FACEBOOK,
    INSTAGRAM,
    GMAIL,
    TWITTER,
    PINTEREST;

    public boolean isValid(String value) {
        if (this == SocialNetworking.EMAIL) {
            return value.matches(RegexTyping.EMAIL.getValue());
        }
        return true;
    }
}
