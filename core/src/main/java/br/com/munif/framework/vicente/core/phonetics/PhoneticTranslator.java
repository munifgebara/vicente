package br.com.munif.framework.vicente.core.phonetics;

import org.apache.commons.codec.StringEncoder;

public interface PhoneticTranslator extends StringEncoder {
    String translate(String str);
}
