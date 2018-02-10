package br.com.munif.framework.vicente.core;

import br.com.munif.framework.vicente.core.vquery.ComparisonOperator;
import br.com.munif.framework.vicente.core.vquery.Criteria;
import br.com.munif.framework.vicente.core.vquery.LogicalOperator;
import br.com.munif.framework.vicente.core.vquery.VQuery;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class VQueryTest {

    VQuery vQuery;

    public VQueryTest() {
        vQuery = new VQuery();
    }

    @Test
    public void simpleTest() {
        assertEquals("(1 = 1)", vQuery.toString());
        assertEquals(Boolean.FALSE, vQuery.getUseDistinct());
        assertEquals("", vQuery.getJoins());
        assertEquals(0, vQuery.getSubQuerys().size());
        assertEquals(LogicalOperator.SIMPLE, vQuery.getLogicalOperator());
    }

    @Test
    public void constructorLogicalOperatorCriteriaSubQuerys() {
        vQuery = new VQuery(LogicalOperator.AND, new Criteria("cpf", ComparisonOperator.EQUAL, "000.000.000-00"), Arrays.asList(new VQuery()));
        assertEquals(LogicalOperator.AND, vQuery.getLogicalOperator());
        assertEquals("(cpf = '000.000.000-00') AND ((1 = 1))", vQuery.toString());
        vQuery = new VQuery(LogicalOperator.AND, new Criteria(), Arrays.asList(new VQuery(new Criteria("nome", ComparisonOperator.EQUAL, "Willian"))));
        assertEquals("(1 = 1) AND ((nome = 'Willian'))", vQuery.toString());
        vQuery = vQuery.and(new Criteria("idade", ComparisonOperator.GREATER, 20));
        assertEquals("(1 = 1) AND ((nome = 'Willian') AND (idade > 20))", vQuery.toString());
    }

    @Test
    public void constructorLogicalOperatorCriteria() {
        vQuery = new VQuery(LogicalOperator.AND, new Criteria());
        assertEquals(LogicalOperator.AND, vQuery.getLogicalOperator());
        assertEquals("1 = 1", vQuery.toString());
        vQuery = new VQuery(LogicalOperator.AND, new Criteria("nome", ComparisonOperator.EQUAL, "Willian"));
        assertEquals("nome = 'Willian'", vQuery.toString());
        vQuery = new VQuery(LogicalOperator.AND, new Criteria("nome", ComparisonOperator.EQUAL, "Willian"))
            .and(new Criteria("idade", ComparisonOperator.GREATER, 80))
            .or(new Criteria("sexo", ComparisonOperator.EQUAL, "MASCULINO"));
        assertEquals("((nome = 'Willian') AND ((idade > 80)) OR (sexo = 'MASCULINO'))", vQuery.toString());
    }


    @Test
    public void constructorCriteria() {

        vQuery = new VQuery(new Criteria("nome", ComparisonOperator.EQUAL, "Willian"))
                .and(new Criteria("idade", ComparisonOperator.GREATER, 80))
                .or(new Criteria("sexo", ComparisonOperator.EQUAL, "MASCULINO"));

        assertEquals("(((nome = 'Willian') AND (idade > 80)) OR (sexo = 'MASCULINO'))", vQuery.toString());vQuery = new VQuery(new Criteria("nome", ComparisonOperator.EQUAL, "Willian"))
                .and(new Criteria("idade", ComparisonOperator.GREATER, 80))
                .or(new Criteria("sexo", ComparisonOperator.EQUAL, "MASCULINO"))
                .or(new Criteria("sexo", ComparisonOperator.EQUAL, "FEMININO"))
                .or(new Criteria("sexo", ComparisonOperator.EQUAL, "OUTRO"))
                .or(new Criteria("sexo", ComparisonOperator.EQUAL, "TESTE"));
        assertEquals("(((nome = 'Willian') AND (idade > 80)) OR (sexo = 'MASCULINO') OR (sexo = 'FEMININO') OR (sexo = 'OUTRO') OR (sexo = 'TESTE'))", vQuery.toString());

       vQuery = new VQuery(new Criteria("nome", ComparisonOperator.EQUAL, "Willian"))
                .and(new Criteria("idade", ComparisonOperator.GREATER, 80))
                .and(new Criteria("idade", ComparisonOperator.LOWER, 70))
                .or(new Criteria("sexo", ComparisonOperator.EQUAL, "MASCULINO"))
                .or(new Criteria("sexo", ComparisonOperator.EQUAL, "FEMININO"))
                .or(new Criteria("sexo", ComparisonOperator.EQUAL, "OUTRO"))
                .or(new Criteria("sexo", ComparisonOperator.EQUAL, "TESTE"));
        assertEquals("(((nome = 'Willian') AND (idade > 80) AND (idade < 70)) OR (sexo = 'MASCULINO') OR (sexo = 'FEMININO') OR (sexo = 'OUTRO') OR (sexo = 'TESTE'))", vQuery.toString());
    }

    @Test
    public void testEMPTY() {
        VQuery gQuery = new VQuery();
        assertEquals("(1 = 1)", gQuery.toString());
    }

    @Test
    public void testSIMPLE() {
        VQuery gQuery = new VQuery(new Criteria("name", ComparisonOperator.EQUAL, "munif"));
        assertEquals("(name = 'munif')", gQuery.toString());
    }

    @Test
    public void testNOT_EQUAL() {
        VQuery gQuery = new VQuery(LogicalOperator.NOT, new Criteria("name", ComparisonOperator.GREATER, "munif"));
        assertEquals("(!name > 'munif')", gQuery.toString());
    }

    @Test
    public void testAND() {
        VQuery gQuery = new VQuery(LogicalOperator.AND, Arrays.asList(new VQuery[]{
                new VQuery(new Criteria("name", ComparisonOperator.STARTS_WITH, "munif")),
                new VQuery(new Criteria("age", ComparisonOperator.GREATER_EQUAL, "18")),
                new VQuery(new Criteria("name", ComparisonOperator.CONTAINS, "gebara"))
        }));
        assertEquals("((name like 'munif%') AND (age >= '18') AND (name like '%gebara%'))", gQuery.toString());
    }

    @Test
    public void testOR() {
        VQuery gQuery = new VQuery(LogicalOperator.OR, Arrays.asList(new VQuery[]{
                new VQuery(new Criteria("name", ComparisonOperator.STARTS_WITH, "munif")),
                new VQuery(new Criteria("name", ComparisonOperator.STARTS_WITH, "vicente")),
                new VQuery(new Criteria("name", ComparisonOperator.STARTS_WITH, "duda"))
        }));
        assertEquals("((name like 'munif%') OR (name like 'vicente%') OR (name like 'duda%'))", gQuery.toString());
    }

    @Test
    public void testCOMPLEX1() {
        VQuery gQuery1 = new VQuery(LogicalOperator.OR, Arrays.asList(new VQuery[]{
                new VQuery(new Criteria("name", ComparisonOperator.STARTS_WITH, "munif")),
                new VQuery(new Criteria("name", ComparisonOperator.STARTS_WITH, "vicente")),
                new VQuery(new Criteria("name", ComparisonOperator.STARTS_WITH, "duda"))
        }));
        VQuery gQuery2 = new VQuery(new Criteria("name", ComparisonOperator.CONTAINS, "gebara"));
        VQuery gQuery = new VQuery(LogicalOperator.AND, Arrays.asList(new VQuery[]{gQuery1, gQuery2}));

        assertEquals("(((name like 'munif%') OR (name like 'vicente%') OR (name like 'duda%')) AND (name like '%gebara%'))", gQuery.toString());
    }

    @Test
    public void testFluent1() {
        VQuery gQuery1 = new VQuery(LogicalOperator.OR, Arrays.asList(new VQuery[]{
                new VQuery(new Criteria("name", ComparisonOperator.STARTS_WITH, "munif")),
                new VQuery(new Criteria("name", ComparisonOperator.STARTS_WITH, "vicente")),
                new VQuery(new Criteria("name", ComparisonOperator.STARTS_WITH, "duda"))
        }));
        VQuery gQuery2 = new VQuery(new Criteria("name", ComparisonOperator.CONTAINS, "gebara"));
        VQuery gQuery = gQuery1.and(gQuery2);
        assertEquals("(((name like 'munif%') OR (name like 'vicente%') OR (name like 'duda%')) AND (name like '%gebara%'))", gQuery.toString());
    }

    @Test
    public void testFluent2() {
        VQuery gQuery1 = new VQuery(LogicalOperator.OR, Arrays.asList(new VQuery[]{
                new VQuery(new Criteria("name", ComparisonOperator.STARTS_WITH, "munif")),
                new VQuery(new Criteria("name", ComparisonOperator.STARTS_WITH, "vicente")),
                new VQuery(new Criteria("name", ComparisonOperator.STARTS_WITH, "duda"))
        }));
        VQuery gQuery2 = new VQuery(new Criteria("name", ComparisonOperator.CONTAINS, "gebara"));
        VQuery gQuery = gQuery1.or(gQuery2);
        assertEquals("((name like 'munif%') OR (name like 'vicente%') OR (name like 'duda%') OR (name like '%gebara%'))", gQuery.toString());
    }

    @Test
    public void testFluent3() {
        VQuery gQuery = new VQuery(new Criteria("name", ComparisonOperator.STARTS_WITH, "munif"))
                .or(new Criteria("name", ComparisonOperator.STARTS_WITH, "vicente"))
                .or(new Criteria("name", ComparisonOperator.STARTS_WITH, "duda"))
                .or(new Criteria("name", ComparisonOperator.CONTAINS, "gebara"));
        assertEquals("((name like 'munif%') OR (name like 'vicente%') OR (name like 'duda%') OR (name like '%gebara%'))", gQuery.toString());
    }

    @Test
    public void testFluent4() {
        VQuery gQuery = new VQuery(new Criteria("name", ComparisonOperator.STARTS_WITH, "munif"))
                .or(new Criteria("name", ComparisonOperator.STARTS_WITH, "vicente"))
                .or(new Criteria("name", ComparisonOperator.STARTS_WITH, "duda"))
                .and(new Criteria("name", ComparisonOperator.CONTAINS, "gebara"));
        assertEquals("(((name like 'munif%') OR (name like 'vicente%') OR (name like 'duda%')) AND (name like '%gebara%'))", gQuery.toString());
    }


}
