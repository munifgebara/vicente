package br.com.munif.framework.test.vicente.domain;

import br.com.munif.framework.test.vicente.domain.model.Pessoa;
import br.com.munif.framework.vicente.core.VicThreadScope;
import br.com.munif.framework.vicente.domain.BaseEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class BaseEntityTest {

    private Pessoa entity;

    @Before
    public void setUp() {
        BaseEntity.useSimpleId = true;
        VicThreadScope.ui.set("ZZ");
        VicThreadScope.gi.set("ZZ");
        this.entity = new Pessoa();
    }

    @Test
    public void oiOnNewNotNull() {
        assertNotNull(entity.getOi());
    }


    @Test
    public void UIDHelperOnNewNotNull() {
        assertNotNull(entity.getId());
    }

    @Test
    public void RighstMainGIOnNewNotNull() {
        assertNotNull(entity.getGi());
    }

    @Test
    public void extraOnNewNotNull() {
        assertNotNull(entity.getExtra());
    }

    @Test
    public void cdOnNewNotNull() {
        assertNotNull(entity.getCd());
    }

    @Test
    public void udOnNewNotNull() {
        assertNotNull(entity.getUd());
    }

    @Test
    public void idActiveOnNewNotNull() {
        assertTrue(entity.getActive());
    }


}
