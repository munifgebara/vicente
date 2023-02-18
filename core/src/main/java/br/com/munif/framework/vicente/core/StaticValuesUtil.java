package br.com.munif.framework.vicente.core;

import java.util.Currency;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class StaticValuesUtil {
    private static Map<String, String> currencyLanguages = new TreeMap<>();


    public static Map<String, String> getCurrencyLanguages() {
        if (currencyLanguages.isEmpty()) {
            Locale[] locales = Locale.getAvailableLocales();
            for (Locale locale : locales) {
                try {
                    currencyLanguages.put(Currency.getInstance(locale).getCurrencyCode(), locale.toLanguageTag());
                } catch (Exception e) {
                    // when the locale is not supported
                }
            }
        }
        return currencyLanguages;
    }
}
