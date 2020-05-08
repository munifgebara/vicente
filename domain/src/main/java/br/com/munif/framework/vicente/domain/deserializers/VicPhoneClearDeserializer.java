package br.com.munif.framework.vicente.domain.deserializers;

import br.com.munif.framework.vicente.domain.typings.VicPhone;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class VicPhoneClearDeserializer extends JsonDeserializer<VicPhone> {
    @Override
    public VicPhone deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        VicPhone number = jp.getCodec().readValue(jp, VicPhone.class);
        if (number != null && number.getDescription() != null) {
            return new VicPhone(number.getDescription().replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("-", "").replaceAll(" ", "").trim(), number.getType());
        }
        return null;
    }
}
