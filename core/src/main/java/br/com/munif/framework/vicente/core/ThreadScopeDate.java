package br.com.munif.framework.vicente.core;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class ThreadScopeDate {

    public static String getLongFormat(ZonedDateTime startDateTime) {
        return startDateTime
                .withSecond(0)
                .withZoneSameInstant(getZoneId())
                .format(getDateTimeFormatter()
                        .withLocale(getLocale()))
                .replaceAll("min00s", "");
    }

    public static Locale getLocale() {
        String language = VicThreadScope.language.get();
        if (language == null || language.isEmpty()) {
            language = "pt-BR";
        }
        return Locale.forLanguageTag(language);
    }

    public static DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL);
    }

    public static ZoneId getZoneId() {
        String s = VicThreadScope.timezone.get();
        if (s == null || s.isEmpty())
            s = "America/Sao_Paulo";
        return ZoneId.of(s);
    }
}
