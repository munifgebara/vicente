/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.domain;

import br.com.munif.framework.vicente.domain.experimental.Configuration;
import br.com.munif.framework.vicente.domain.typings.VicPhone;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author munif
 */
public class VicPhoneTest {

    public VicPhoneTest() {
    }

    @Test
    public void getRegionCode() {
        Configuration configuration = new Configuration();
        configuration.setCountryCode("US");
        Configuration.setCurrent(configuration);

        VicPhone vicPhone = new VicPhone();
        assertEquals("US", vicPhone.getRegionCode());
    }
}
