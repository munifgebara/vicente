package br.com.munif.framework.vicente.core;

import br.com.munif.framework.vicente.core.vquery.ComparisonOperator;
import br.com.munif.framework.vicente.core.vquery.Criteria;
import br.com.munif.framework.vicente.core.vquery.ParamList;
import br.com.munif.framework.vicente.core.vquery.VQuery;
import org.junit.Before;
import org.junit.Test;

public class VQueryFieldsTest {


    private Pessoa entity;

    @Before
    public void setUp() {
        this.entity = new Pessoa();
        this.entity.setNome("Will");
        this.entity.setEndereco(new Endereco(
                "String zipCode",
                10L, true,
                new Coordenadas("1l", "2l")
        ));
    }

    @Test
    public void oiOnNewNotNull() {

        VQuery will = new VQuery(new Criteria("endereco.coordenadas", ComparisonOperator.EQUAL, new Coordenadas("Ab", "Cd")));
        ParamList params = will.getParams(entity.getClass());
        System.out.println(params);

    }
}
