package br.com.munif.framework.vicente.security.domain;

import br.com.munif.framework.vicente.api.VicAutoSeed;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PasswordGeneratorTest {
    @Test
    public void generateAndValidate() {
        for (int i = 5; i < 50; i++) {
            String randomString = VicAutoSeed.getRandomString(i);
            String generated = PasswordGenerator.generate(randomString);
            assertTrue(PasswordGenerator.validate(randomString, generated));
        }
        for (int i = 5; i < 20; i++) {
            String randomString = VicAutoSeed.getRandomString(i) + VicAutoSeed.getRandomInteger(0, 1000);
            String generated = PasswordGenerator.generate(randomString);
            assertTrue(PasswordGenerator.validate(randomString, generated));
        }
    }
}
