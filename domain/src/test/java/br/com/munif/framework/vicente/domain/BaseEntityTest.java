/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.domain;

import br.com.munif.framework.vicente.core.VicTenancyType;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author munif
 */
public class BaseEntityTest {

    public BaseEntityTest() {
    }

    @Test
    public void testGetId() {
        System.out.println("getId");
        BaseEntity instance = new BaseEntity();
        assertNotNull(instance.getId());
    }

    @Test
    public void testGetGi() {
        System.out.println("getGi");
        BaseEntity instance = new BaseEntity();
        assertNotNull(instance.getGi());
    }

    @Test
    public void testGetUi() {
        System.out.println("getUi");
        BaseEntity instance = new BaseEntity();
        assertNotNull(instance.getUi());
    }

    @Test
    public void testGetOi() {
        System.out.println("getOi");
        BaseEntity instance = new BaseEntity();
        String result = instance.getOi();
        assertNotNull(result);
    }

    @Test
    public void testGetRights() {
        System.out.println("getRights");
        BaseEntity instance = new BaseEntity();
        Integer result = instance.getRights();
        assertNotNull(result);
    }

    @Test
    public void testGetExtra() {
        System.out.println("getExtra");
        BaseEntity instance = new BaseEntity();
        String result = instance.getExtra();
        assertNotNull(result);
    }

    @Test
    public void testGetActive() {
        System.out.println("getActive");
        BaseEntity instance = new BaseEntity();
        Boolean result = instance.getActive();
        assertNotNull(result);
    }

    @Test
    public void testGetVersion() {
        System.out.println("getVersion");
        BaseEntity instance = new BaseEntity();
        Integer result = instance.getVersion();
        assertNull(result);
    }

    @Test
    public void testToString() {
        System.out.println("toString");
        BaseEntity instance = new BaseEntity();
        String result = instance.toString();
        assertNotNull(result);
    }

    @Test
    public void testIsOwner() {
        System.out.println("isOwner");
        BaseEntity instance = new BaseEntity();
        boolean expResult = false;
        boolean result = instance.isOwner();
        assertEquals(expResult, result);
    }

    @Test
    public void testCanDelete() {
        System.out.println("canDelete");
        BaseEntity instance = new BaseEntity();
        boolean expResult = false;
        boolean result = instance.canDelete();
        assertEquals(expResult, result);
    }

    @Test
    public void testCanUpdate() {
        System.out.println("canUpdate");
        BaseEntity instance = new BaseEntity();
        boolean expResult = false;
        boolean result = instance.canUpdate();
        assertEquals(expResult, result);
    }

    @Test
    public void testCanRead() {
        System.out.println("canRead");
        BaseEntity instance = new BaseEntity();
        boolean expResult = false;
        boolean result = instance.canRead();
        assertEquals(expResult, result);
    }

    @Test
    public void testR() {
        System.out.println("r");
        BaseEntity instance = new BaseEntity();
        String expResult = "_____";
        String result = instance.r();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetTencyPolicy() {
        System.out.println("getTencyPolicy");
        BaseEntity instance = new BaseEntity();
        VicTenancyType expResult = VicTenancyType.GROUPS;
        VicTenancyType result = instance.getTencyPolicy();
        assertEquals(expResult, result);
    }

}
