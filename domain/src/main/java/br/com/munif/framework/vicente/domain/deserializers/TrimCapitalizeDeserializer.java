package br.com.munif.framework.vicente.domain.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TrimCapitalizeDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return Arrays.stream(jp.getValueAsString().trim().split(" "))
                .map(StringUtils::capitalize).collect(Collectors.joining(" "));
    }
}
