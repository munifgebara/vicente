/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.domain;

import br.com.munif.framework.vicente.core.VicTenancyType;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author munif
 */
public class BaseEntityTest {

    public BaseEntityTest() {
    }

    @Test
    public void testGetId() {
        BaseEntity instance = new BaseEntity();
        assertNotNull(instance.getId());
    }

    @Test
    public void testGetGi() {
        BaseEntity instance = new BaseEntity();
        assertNotNull(instance.getGi());
    }

    @Test
    public void testGetUi() {
        BaseEntity instance = new BaseEntity();
        assertNotNull(instance.getUi());
    }

    @Test
    public void testGetOi() {
        BaseEntity instance = new BaseEntity();
        String result = instance.getOi();
        assertNotNull(result);
    }

    @Test
    public void testGetRights() {
        BaseEntity instance = new BaseEntity();
        Integer result = instance.getRights();
        assertNotNull(result);
    }

    @Test
    public void testGetExtra() {
        BaseEntity instance = new BaseEntity();
        assertNotNull(instance.getExtra());
    }

    @Test
    public void testGetActive() {
        BaseEntity instance = new BaseEntity();
        Boolean result = instance.getActive();
        assertNotNull(result);
    }

    @Test
    public void testGetVersion() {
        BaseEntity instance = new BaseEntity();
        Integer result = instance.getVersion();
        assertNull(result);
    }

    @Test
    public void testToString() {
        BaseEntity instance = new BaseEntity();
        String result = instance.toString();
        assertNotNull(result);
    }

    @Test
    public void testIsOwner() {
        BaseEntity instance = new BaseEntity();
        boolean expResult = false;
        boolean result = instance.isOwner();
        assertEquals(expResult, result);
    }

    @Test
    public void testCanDelete() {
        BaseEntity instance = new BaseEntity();
        boolean expResult = false;
        boolean result = instance.canDelete();
        assertEquals(expResult, result);
    }

    @Test
    public void testCanUpdate() {
        BaseEntity instance = new BaseEntity();
        boolean expResult = false;
        boolean result = instance.canUpdate();
        assertEquals(expResult, result);
    }

    @Test
    public void testCanRead() {
        BaseEntity instance = new BaseEntity();
        boolean expResult = false;
        boolean result = instance.canRead();
        assertEquals(expResult, result);
    }

    @Test
    public void testR() {
        BaseEntity instance = new BaseEntity();
        String expResult = "_____";
        String result = instance.r();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetTencyPolicy() {
        BaseEntity instance = new BaseEntity();
        VicTenancyType expResult = VicTenancyType.GROUPS;
        VicTenancyType result = instance.getTencyPolicy();
        assertEquals(expResult, result);
    }

}
