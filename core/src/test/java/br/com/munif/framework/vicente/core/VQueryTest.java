package br.com.munif.framework.vicente.core;

import br.com.munif.framework.vicente.core.vquery.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;

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
    public void constructorLogicalOperatorCriteriaSubQuerys1() {
        vQuery = new VQuery(LogicalOperator.AND, new Criteria("cpf", ComparisonOperator.EQUAL, "000.000.000-00"), Arrays.asList(new VQuery()));
        assertEquals(LogicalOperator.AND, vQuery.getLogicalOperator());
        assertEquals("(cpf = '000.000.000-00') AND ((1 = 1))", vQuery.toString());
    }

    @Test
    public void constructorLogicalOperatorCriteriaSubQuerys2() {
        vQuery = new VQuery(LogicalOperator.AND, new Criteria(), Arrays.asList(new VQuery(new Criteria("nome", ComparisonOperator.EQUAL, "Willian"))));
        assertEquals("((nome = 'Willian'))", vQuery.toString());
    }

    @Test
    public void constructorLogicalOperatorCriteriaSubQuerys3() {
        vQuery = new VQuery(LogicalOperator.AND, new Criteria(), Arrays.asList(new VQuery(new Criteria("nome", ComparisonOperator.EQUAL, "Willian"))));
        vQuery = vQuery.and(new Criteria("idade", ComparisonOperator.GREATER, 20));
        assertEquals("((nome = 'Willian') AND (idade > 20))", vQuery.toString());
    }

    @Test
    public void constructorLogicalOperatorCriteria1() {
        vQuery = new VQuery(LogicalOperator.AND, new Criteria());
        assertEquals(LogicalOperator.AND, vQuery.getLogicalOperator());
        assertEquals("1 = 1", vQuery.toString());
    }

    @Test
    public void constructorLogicalOperatorCriteria2() {
        vQuery = new VQuery(LogicalOperator.AND, new Criteria("nome", ComparisonOperator.EQUAL, "Willian"));
        assertEquals("nome = 'Willian'", vQuery.toString());
    }

    @Test
    public void constructorLogicalOperatorCriteria3() {
        vQuery = new VQuery(LogicalOperator.AND, new Criteria("nome", ComparisonOperator.EQUAL, "Willian"))
                .and(new Criteria("idade", ComparisonOperator.GREATER, 80))
                .or(new Criteria("sexo", ComparisonOperator.EQUAL, "MASCULINO"));
        assertEquals("((nome = 'Willian') AND ((idade > 80)) OR (sexo = 'MASCULINO'))", vQuery.toString());
    }

    @Test
    public void constructorCriteria1() {
        vQuery = new VQuery(new Criteria("nome", ComparisonOperator.EQUAL, "Willian"))
                .and(new Criteria("idade", ComparisonOperator.GREATER, 80))
                .or(new Criteria("sexo", ComparisonOperator.EQUAL, "MASCULINO"));

        assertEquals("(((nome = 'Willian') AND (idade > 80)) OR (sexo = 'MASCULINO'))", vQuery.toString());
    }


    @Test
    public void constructorCriteria2() {
        vQuery = new VQuery(new Criteria("nome", ComparisonOperator.EQUAL, "Willian"))
                .and(new Criteria("idade", ComparisonOperator.GREATER, 80))
                .or(new Criteria("sexo", ComparisonOperator.EQUAL, "MASCULINO"))
                .or(new Criteria("sexo", ComparisonOperator.EQUAL, "FEMININO"))
                .or(new Criteria("sexo", ComparisonOperator.EQUAL, "OUTRO"))
                .or(new Criteria("sexo", ComparisonOperator.EQUAL, "TESTE"));
        assertEquals("(((nome = 'Willian') AND (idade > 80)) OR (sexo = 'MASCULINO') OR (sexo = 'FEMININO') OR (sexo = 'OUTRO') OR (sexo = 'TESTE'))", vQuery.toString());
    }


    @Test
    public void constructorCriteria3() {
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
    public void testJoin1() {
        vQuery = new VQuery(new Criteria("nome", ComparisonOperator.EQUAL, "willian"))
                .join(new Join("Endereco", JoinType.INNER)
                        .on(new Criteria("pessoa.id", ComparisonOperator.EQUAL, new CriteriaField("id"))));
        assertEquals("(nome = 'willian')", vQuery.toString());
        assertEquals(" inner join Endereco on pessoa.id = id", vQuery.getJoins());
    }

    @Test
    public void testJoin2() {
        vQuery = new VQuery(new Criteria("nome", ComparisonOperator.EQUAL, "willian"))
                .join(new Join("Endereco", JoinType.INNER)
                        .on(new Criteria("pessoa.id", ComparisonOperator.EQUAL, new CriteriaField("id"))));
        vQuery = vQuery.join(new Join("Telefone", JoinType.LEFT));
        assertEquals(" inner join Endereco on pessoa.id = id left join Telefone", vQuery.getJoins());
    }

    @Test
    public void testJoin22() {
        VQuery vQuery = new VQuery(new Criteria("name", ComparisonOperator.CONTAINS, "The book"))
                .or(new Criteria("name", ComparisonOperator.CONTAINS, "books"));
        System.out.println(vQuery);
    }


    @Test
    public void testJoin3() {
        vQuery = new VQuery(new Criteria("nome", ComparisonOperator.EQUAL, "willian"))
                .join(new Join("Endereco", JoinType.INNER)
                        .on(new Criteria("pessoa.id", ComparisonOperator.EQUAL, new CriteriaField("id"))));
        vQuery = vQuery.join(new Join("Telefone", JoinType.LEFT));
        vQuery = vQuery.join(new Join("Historico", JoinType.SIMPLE)
                .on(new Criteria("id", ComparisonOperator.EQUAL, new CriteriaField("id")))
                .and(new Criteria("descricao", ComparisonOperator.CONTAINS, "teste")));
        assertEquals(" inner join Endereco on pessoa.id = id left join Telefone join Historico on id = id and descricao like '%teste%'", vQuery.getJoins());
    }


    @Test
    public void testJoin4() {
        vQuery = new VQuery(new Criteria("nome", ComparisonOperator.EQUAL, "willian"))
                .join(new Join("Endereco", JoinType.INNER)
                        .on(new Criteria("pessoa.id", ComparisonOperator.EQUAL, new CriteriaField("id"))));
        vQuery = vQuery.join(new Join("Telefone", JoinType.LEFT));
        vQuery = vQuery.join(new Join("Historico", JoinType.SIMPLE)
                .on(new Criteria("id", ComparisonOperator.EQUAL, new CriteriaField("id")))
                .and(new Criteria("descricao", ComparisonOperator.CONTAINS, "teste")));
        vQuery = vQuery.join(new Join("Historico", JoinType.SIMPLE)
                .on(new Criteria("id", ComparisonOperator.EQUAL, new CriteriaField("id")))
                .and(new Criteria("descricao", ComparisonOperator.CONTAINS, "teste"))
                .or(new Criteria("descricao", ComparisonOperator.CONTAINS, "willinha")));
        assertEquals(" inner join Endereco on pessoa.id = id left join Telefone join Historico on id = id and descricao like '%teste%' join Historico on id = id and descricao like '%teste%' or descricao like '%willinha%'", vQuery.getJoins());
    }


    @Test
    public void in() {
        vQuery = new VQuery(new Criteria("sexo", ComparisonOperator.IN, new String[]{"M", "F"}))
                .and(new Criteria("idade", ComparisonOperator.IN, new Integer[]{1, 2, 3}))
                .or(new Criteria("elementos", ComparisonOperator.IN, new CriteriaField[]{
                        new CriteriaField("ola"),
                        new CriteriaField("teste")
                }));
        assertEquals("(((sexo in ('M','F')) AND (idade in (1,2,3))) OR (elementos in (ola,teste)))", vQuery.toString());
    }

    @Test
    public void between() {
        vQuery = new VQuery(new Criteria("idade", ComparisonOperator.BETWEEN, new Integer[]{1, 2}))
                .or(new Criteria("nome", ComparisonOperator.BETWEEN, new String[]{"a", "c"}));
        assertEquals("((idade between 1 and 2) OR (nome between 'a' and 'c'))", vQuery.toString());
    }

    @Test
    public void allComparisonOperatorString() {
        vQuery = new VQuery(new Criteria())
                .and(new Criteria("campo1", ComparisonOperator.EQUAL, "texto"))
                .and(new Criteria("campo2", ComparisonOperator.LOWER, "texto"))
                .and(new Criteria("campo3", ComparisonOperator.GREATER, "texto"))
                .and(new Criteria("campo4", ComparisonOperator.GREATER_EQUAL, "texto"))
                .and(new Criteria("campo5", ComparisonOperator.LOWER_EQUAL, "texto"))
                .and(new Criteria("campo6", ComparisonOperator.STARTS_WITH, "texto"))
                .and(new Criteria("campo7", ComparisonOperator.CONTAINS, "texto"))
                .and(new Criteria("campo8", ComparisonOperator.IN, new String[]{"texto1", "texto2", "texto3"}))
                .and(new Criteria("texto", ComparisonOperator.IN_ELEMENTS, new CriteriaField("elementos")))
                .and(new Criteria("campo10", ComparisonOperator.IS, "texto"))
                .and(new Criteria("campo11", ComparisonOperator.BETWEEN, new String[]{"A", "Z"}))
                .and(new Criteria("campo12", ComparisonOperator.NOT_EQUAL, "texto"))
                .and(new Criteria("campo13", ComparisonOperator.NOT_EQUAL, "texto"))
                .and(new Criteria("campo14", ComparisonOperator.NOT_STARTS_WITH, "texto"))
                .and(new Criteria("campo15", ComparisonOperator.NOT_ENDS_WITH, "texto"))
                .and(new Criteria("campo16", ComparisonOperator.NOT_CONTAINS, "texto"))
                .and(new Criteria("campo17", ComparisonOperator.NOT_IN, new String[]{"A", "Z"}))
                .and(new Criteria("campo18", ComparisonOperator.NOT_IS, "texto"))
                .and(new Criteria("campo19", ComparisonOperator.NOT_BETWEEN, new String[]{"texto1", "texto2", "texto3"}));

        assertEquals("((1 = 1) AND (campo1 = 'texto') AND (campo2 < 'texto') AND (campo3 > 'texto') AND (campo4 >= 'texto') AND (campo5 <= 'texto') AND (campo6 like 'texto%') AND (campo7 like '%texto%') AND (campo8 in ('texto1','texto2','texto3')) AND ('texto' in elements(elementos)) AND (campo10 is 'texto') AND (campo11 between 'A' and 'Z') AND (campo12 <> 'texto') AND (campo13 <> 'texto') AND (campo14 not like 'texto%') AND (campo15 not like '%texto') AND (campo16 not like '%texto%') AND (campo17 not in ('A','Z')) AND (campo18 not is 'texto') AND (campo19 not between 'texto1' and 'texto2'))", vQuery.toString());
    }

    @Test
    public void allComparisonOperatorInt() {
        vQuery = new VQuery(new Criteria())
                .and(new Criteria("campo1", ComparisonOperator.EQUAL, 1))
                .and(new Criteria("campo2", ComparisonOperator.LOWER, 2))
                .and(new Criteria("campo3", ComparisonOperator.GREATER, 3))
                .and(new Criteria("campo4", ComparisonOperator.GREATER_EQUAL, 4))
                .and(new Criteria("campo5", ComparisonOperator.LOWER_EQUAL, 5))
                .and(new Criteria("campo6", ComparisonOperator.STARTS_WITH, 6))
                .and(new Criteria("campo7", ComparisonOperator.CONTAINS, 7))
                .and(new Criteria("campo8", ComparisonOperator.IN, new Integer[]{1, 2, 3}))
                .and(new Criteria(1, ComparisonOperator.IN_ELEMENTS, new CriteriaField("elementos")))
                .and(new Criteria("campo10", ComparisonOperator.IS, 9))
                .and(new Criteria("campo11", ComparisonOperator.BETWEEN, new Integer[]{10, 11}))
                .and(new Criteria("campo12", ComparisonOperator.NOT_EQUAL, 12))
                .and(new Criteria("campo13", ComparisonOperator.NOT_EQUAL, 13))
                .and(new Criteria("campo14", ComparisonOperator.NOT_STARTS_WITH, 14))
                .and(new Criteria("campo15", ComparisonOperator.NOT_ENDS_WITH, 15))
                .and(new Criteria("campo16", ComparisonOperator.NOT_CONTAINS, 16))
                .and(new Criteria("campo17", ComparisonOperator.NOT_IN, new Integer[]{17, 18}))
                .and(new Criteria("campo18", ComparisonOperator.NOT_IS, 19))
                .and(new Criteria("campo19", ComparisonOperator.NOT_BETWEEN, new Integer[]{20, 21, 22}));

        assertEquals("((1 = 1) AND (campo1 = 1) AND (campo2 < 2) AND (campo3 > 3) AND (campo4 >= 4) AND (campo5 <= 5) AND (campo6 like 6) AND (campo7 like 7) AND (campo8 in (1,2,3)) AND (1 in elements(elementos)) AND (campo10 is 9) AND (campo11 between 10 and 11) AND (campo12 <> 12) AND (campo13 <> 13) AND (campo14 not like 14) AND (campo15 not like 15) AND (campo16 not like 16) AND (campo17 not in (17,18)) AND (campo18 not is 19) AND (campo19 not between 20 and 21))", vQuery.toString());
    }

    @Test
    public void allComparisonOperatorDate() {
        vQuery = new VQuery(new Criteria())
                .and(new Criteria("campo1", ComparisonOperator.EQUAL, new Date(1518291786286L)))
                .and(new Criteria("campo2", ComparisonOperator.LOWER, new Date(1518291786286L)))
                .and(new Criteria("campo3", ComparisonOperator.GREATER, new Date(1518291786286L)))
                .and(new Criteria("campo4", ComparisonOperator.GREATER_EQUAL, new Date(1518291786286L)))
                .and(new Criteria("campo5", ComparisonOperator.LOWER_EQUAL, new Date(1518291786286L)))
                .and(new Criteria("campo6", ComparisonOperator.STARTS_WITH, new Date(1518291786286L)))
                .and(new Criteria("campo7", ComparisonOperator.CONTAINS, new Date(1518291786286L)))
                .and(new Criteria("campo8", ComparisonOperator.IN, new Date[]{new Date(1518291786286L), new Date(1518291786286L), new Date(1518291786286L)}))
                .and(new Criteria(new Date(1518291786286L), ComparisonOperator.IN_ELEMENTS, new CriteriaField("elementos")))
                .and(new Criteria("campo10", ComparisonOperator.IS, new Date(1518291786286L)))
                .and(new Criteria("campo11", ComparisonOperator.BETWEEN, new Date[]{new Date(1518291786286L), new Date(1519299799286L)}))
                .and(new Criteria("campo12", ComparisonOperator.NOT_EQUAL, new Date(1518291786286L)))
                .and(new Criteria("campo13", ComparisonOperator.NOT_EQUAL, new Date(1518291786286L)))
                .and(new Criteria("campo14", ComparisonOperator.NOT_STARTS_WITH, new Date(1518291786286L)))
                .and(new Criteria("campo15", ComparisonOperator.NOT_ENDS_WITH, new Date(1518291786286L)))
                .and(new Criteria("campo16", ComparisonOperator.NOT_CONTAINS, new Date(1518291786286L)))
                .and(new Criteria("campo17", ComparisonOperator.NOT_IN, new Date[]{new Date(1519291786286L), new Date(1518291786286L)}))
                .and(new Criteria("campo18", ComparisonOperator.NOT_IS, new Date(1518291786286L)))
                .and(new Criteria("campo19", ComparisonOperator.NOT_BETWEEN, new Date[]{new Date(1518291786286L), new Date(1519299799286L)}));

        assertEquals("((1 = 1) AND (campo1 = '2018/02/10 17:43:06') AND (campo2 < '2018/02/10 17:43:06') AND (campo3 > '2018/02/10 17:43:06') AND (campo4 >= '2018/02/10 17:43:06') AND (campo5 <= '2018/02/10 17:43:06') AND (campo6 like '2018/02/10 17:43:06') AND (campo7 like '2018/02/10 17:43:06') AND (campo8 in ('2018/02/10 17:43:06','2018/02/10 17:43:06','2018/02/10 17:43:06')) AND ('2018/02/10 17:43:06' in elements(elementos)) AND (campo10 is '2018/02/10 17:43:06') AND (campo11 between '2018/02/10 17:43:06' and '2018/02/22 08:43:19') AND (campo12 <> '2018/02/10 17:43:06') AND (campo13 <> '2018/02/10 17:43:06') AND (campo14 not like '2018/02/10 17:43:06') AND (campo15 not like '2018/02/10 17:43:06') AND (campo16 not like '2018/02/10 17:43:06') AND (campo17 not in ('2018/02/22 06:29:46','2018/02/10 17:43:06')) AND (campo18 not is '2018/02/10 17:43:06') AND (campo19 not between '2018/02/10 17:43:06' and '2018/02/22 08:43:19'))", vQuery.toString());
    }

    @Test
    public void allComparisonOperatorBoolean() {
        vQuery = new VQuery(new Criteria())
                .and(new Criteria("campo1", ComparisonOperator.EQUAL, Boolean.TRUE))
                .and(new Criteria("campo2", ComparisonOperator.LOWER, Boolean.FALSE))
                .and(new Criteria("campo3", ComparisonOperator.GREATER, Boolean.TRUE))
                .and(new Criteria("campo4", ComparisonOperator.GREATER_EQUAL, Boolean.FALSE))
                .and(new Criteria("campo5", ComparisonOperator.LOWER_EQUAL, Boolean.TRUE))
                .and(new Criteria("campo6", ComparisonOperator.STARTS_WITH, Boolean.TRUE))
                .and(new Criteria("campo7", ComparisonOperator.CONTAINS, Boolean.FALSE))
                .and(new Criteria("campo8", ComparisonOperator.IN, new Boolean[]{Boolean.TRUE, Boolean.FALSE}))
                .and(new Criteria(Boolean.TRUE, ComparisonOperator.IN_ELEMENTS, new CriteriaField("elementos")))
                .and(new Criteria("campo10", ComparisonOperator.IS, Boolean.FALSE))
                .and(new Criteria("campo11", ComparisonOperator.BETWEEN, new Boolean[]{Boolean.TRUE, Boolean.FALSE}))
                .and(new Criteria("campo12", ComparisonOperator.NOT_EQUAL, Boolean.TRUE))
                .and(new Criteria("campo13", ComparisonOperator.NOT_EQUAL, Boolean.FALSE))
                .and(new Criteria("campo14", ComparisonOperator.NOT_STARTS_WITH, Boolean.TRUE))
                .and(new Criteria("campo15", ComparisonOperator.NOT_ENDS_WITH, Boolean.TRUE))
                .and(new Criteria("campo16", ComparisonOperator.NOT_CONTAINS, Boolean.FALSE))
                .and(new Criteria("campo17", ComparisonOperator.NOT_IN, new Boolean[]{Boolean.TRUE, Boolean.FALSE}))
                .and(new Criteria("campo18", ComparisonOperator.NOT_IS, Boolean.TRUE))
                .and(new Criteria("campo19", ComparisonOperator.NOT_BETWEEN, new Boolean[]{Boolean.TRUE, Boolean.FALSE}));

        assertEquals("((1 = 1) AND (campo1 = true) AND (campo2 < false) AND (campo3 > true) AND (campo4 >= false) AND (campo5 <= true) AND (campo6 like true) AND (campo7 like false) AND (campo8 in (true,false)) AND (true in elements(elementos)) AND (campo10 is false) AND (campo11 between true and false) AND (campo12 <> true) AND (campo13 <> false) AND (campo14 not like true) AND (campo15 not like true) AND (campo16 not like false) AND (campo17 not in (true,false)) AND (campo18 not is true) AND (campo19 not between true and false))", vQuery.toString());
    }

    @Test
    public void testDate() {
        vQuery = new VQuery(new Criteria("dataCadastro", ComparisonOperator.BETWEEN, new Date[]{new Date(1518291786286l), new Date(1519299799286l)}))
                .or(new Criteria("nome", ComparisonOperator.BETWEEN, new String[]{"a", "c"}));
        assertEquals("((dataCadastro between '2018/02/10 17:43:06' and '2018/02/22 08:43:19') OR (nome between 'a' and 'c'))", vQuery.toString());
    }

    @Test
    public void testVEntityQuery() {
        vQuery = new VEntityQuery(Aluno.class, new Criteria("nome", ComparisonOperator.CONTAINS, "Willian"));
        assertEquals("(select obj from Aluno obj where (nome like '%Willian%'))", vQuery.toString());
    }

    @Test
    public void testVEntityQueryIn() {
        vQuery = new VQuery(new Criteria("sexo", ComparisonOperator.IN, new String[]{"M", "F"}))
                .and(new Criteria("idade", ComparisonOperator.IN, new Integer[]{1, 2, 3}))
                .or(new Criteria("subitems", ComparisonOperator.IN, new VEntityQuery("OutraTabela")))
                .or(new Criteria("id", ComparisonOperator.IN,
                                new VEntityQuery(
                                        "Historico",
                                        "hist",
                                        new Criteria("id", ComparisonOperator.EQUAL, new CriteriaField("id")),
                                        "id")
                        )
                );
        assertEquals("(((sexo in ('M','F')) AND (idade in (1,2,3))) OR (subitems in (select obj from OutraTabela obj where (1 = 1))) OR (id in (select hist.id from Historico hist where (hist.id = id))))", vQuery.toString());
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
