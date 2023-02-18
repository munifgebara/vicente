/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.domain;

import br.com.munif.framework.vicente.core.VicTenancyType;
import br.com.munif.framework.vicente.domain.experimental.Configuration;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author munif
 */
public class BaseConfigurationTest {

    public BaseConfigurationTest() {
    }

    @Test
    public void current() {
        Configuration configuration = new Configuration();
        configuration.setCountryCode("+1");
        Configuration.setCurrent(configuration);
        assertEquals(configuration, Configuration.getCurrent());
    }
}
