/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.domain.VicTemporalEntity;

import br.com.munif.framework.vicente.core.VicThreadScope;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author munif
 */
public class VicTemporalEntityTest {
    
    public VicTemporalEntityTest() {
    }
    
    @Test
    public void effectiveTimeIsNull(){
        VicThreadScope.effectiveTime.set(null);
        ContratoTestEntity contrato=new ContratoTestEntity();
        assertNotNull(contrato.startTime);
        assertNotNull(contrato.endTime);
    }
    @Test
    public void isValid(){
        VicThreadScope.effectiveTime.set(60135793200000l);
        ContratoTestEntity contrato=new ContratoTestEntity();
        contrato.setStartTime(60135793200000l-10000);
        contrato.setEndTime(60135793200000l+10000);
        assertTrue(contrato.valid());
    }
    public void isExactValid(){
        VicThreadScope.effectiveTime.set(60135793200000l);
        ContratoTestEntity contrato=new ContratoTestEntity();
        contrato.setStartTime(60135793200000l);
        contrato.setEndTime(60135793200000l);
        assertTrue(contrato.valid());
    }

    @Test
    public void isInvalid(){
        VicThreadScope.effectiveTime.set(60135793200000l);
        ContratoTestEntity contrato=new ContratoTestEntity();
        contrato.setStartTime(60135793200000l-20000);
        contrato.setEndTime(60135793200000l-10000);
        assertFalse(contrato.valid());
    }
    @Test
    public void hasInvalidDefinition(){
        VicThreadScope.effectiveTime.set(60135793200000l);
        ContratoTestEntity contrato=new ContratoTestEntity();
        contrato.setStartTime(60135793200000l+10000);
        contrato.setEndTime(60135793200000l-10000);
        assertFalse(contrato.valid());
    }
    @Test
    public void hasInvalidDefinition2(){
        VicThreadScope.effectiveTime.set(160135793200000l);
        ContratoTestEntity contrato=new ContratoTestEntity();
        contrato.setStartTime(60135793200000l+10000);
        contrato.setEndTime(60135793200000l-10000);
        assertFalse(contrato.valid());
    }
    
}
