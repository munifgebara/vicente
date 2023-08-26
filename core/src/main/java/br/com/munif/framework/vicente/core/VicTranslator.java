package br.com.munif.framework.vicente.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class VicTranslator {
    private static Map<String, Map<String, String>> translations = new HashMap<>();

    public static String get(String key) {
        String language = VicThreadScope.language.get();
        if (!translations.containsKey(language)) {
            return getSettingPath(language, key);
        }
        return getString(language, key);
    }

    private static String getSettingPath(String language, String key) {
        try {
            ClassPathResource staticDataResource = new ClassPathResource("i18n/" + language + ".json");
            String staticDataString = IOUtils.toString(staticDataResource.getInputStream(), StandardCharsets.UTF_8);
            translations.put(language, new ObjectMapper().readValue(staticDataString, Map.class));
            return getString(language, key);
        } catch (IOException e) {
            return getSettingPath("pt-BR", key);
        }
    }

    private static String getString(String language, String key) {
        Map<String, String> currentTranslation = translations.get(language);
        return currentTranslation.get(key);
    }

    public static String get(String key, String... values) {
        String text = get(key);
        for (int i = 1; i <= values.length; i++) {
            text = text.replace("$" + i, values[i-1]);
        }
        return text;
    }
}
