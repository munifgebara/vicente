package br.com.munif.framework.vicente.core.phonetics;

import br.com.munif.framework.vicente.core.VicThreadScope;

public class PhoneticBuilder {
    public static PhoneticTranslator build() {
        String language = VicThreadScope.language.get();
        if (language == null) return PhoneticValues.PT_BR.getTranslator();
        return PhoneticValues.resolve(language).getTranslator();
    }
}
