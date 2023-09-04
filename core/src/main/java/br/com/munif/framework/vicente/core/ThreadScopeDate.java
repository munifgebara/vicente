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
        return Locale.forLanguageTag(VicThreadScope.language.get());
    }

    public static DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL);
    }

    public static ZoneId getZoneId() {
        return ZoneId.of(VicThreadScope.timezone.get());
    }
}
