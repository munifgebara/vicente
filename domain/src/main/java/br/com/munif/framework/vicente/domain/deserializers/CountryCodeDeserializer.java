package br.com.munif.framework.vicente.domain.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class CountryCodeDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String valueAsString = jp.getValueAsString().trim();
        return valueAsString.contains("+") ? valueAsString : "+" + valueAsString;
    }
}
