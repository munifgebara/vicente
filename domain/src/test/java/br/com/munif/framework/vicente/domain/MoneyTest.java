package br.com.munif.framework.vicente.domain;

import br.com.munif.framework.vicente.domain.typings.VicCurrencyType;
import br.com.munif.framework.vicente.domain.typings.VicMoney;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoneyTest {

    @Test
    public void nullSafe() {
        VicMoney vicMoney = new VicMoney();

        assertSame(0L, vicMoney.getAmount());
        assertEquals(VicCurrencyType.BRL, vicMoney.getType());
    }

    @Test
    public void getBRL120() {
        VicMoney vicMoney = new VicMoney();
        vicMoney.setAmount(120L);

        assertEquals(VicCurrencyType.BRL, vicMoney.getType());
        assertEquals(Double.valueOf(1.2), vicMoney.getDouble());
    }

    @Test
    public void getBRL132() {
        VicMoney vicMoney = new VicMoney();
        vicMoney.setAmount(132L);

        assertEquals(VicCurrencyType.BRL, vicMoney.getType());
        assertEquals(Double.valueOf(1.32), vicMoney.getDouble());
    }

    @Test
    public void getUSD132WithDivision2() {
        VicMoney vicMoney = new VicMoney();
        vicMoney.setAmount(132L);

        assertEquals(VicCurrencyType.BRL, vicMoney.getType());
        assertEquals(Double.valueOf(1.32 * 2), vicMoney.getDouble(VicCurrencyType.USD, 2.0));
    }

    @Test
    public void getUSD132WithDivision2Dot3() {
        VicMoney vicMoney = new VicMoney();
        vicMoney.setAmount(132L);

        assertEquals(VicCurrencyType.BRL, vicMoney.getType());
        assertEquals(Double.valueOf(1.32 * 2.3), vicMoney.getDouble(VicCurrencyType.USD, 2.3));
    }
    @Test
    public void getUSD132WithDivision2Dot33() {
        VicMoney vicMoney = new VicMoney();
        vicMoney.setAmount(132L);

        assertEquals(VicCurrencyType.BRL, vicMoney.getType());
        assertEquals(Double.valueOf(1.32 * 2.33), vicMoney.getDouble(VicCurrencyType.USD, 2.33));
    }


}
