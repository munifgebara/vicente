package br.com.munif.framework.vicente.core;

import br.com.munif.framework.vicente.core.phonetics.PortuguesePhonetic;
import org.apache.commons.codec.EncoderException;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public class PortuguesePhoneticTest {

    @Test
    public void testConsonantClusterOfHLetter() throws EncoderException {
        assertEncode("talho", "talio");
        assertEncode("banho", "banio");
        assertEncode("aché", "axe");
        assertEncode("ashé", "axe");
        assertEncode("rapha", "rafa");
    }


    @Test
    public void testSSeCemS() throws EncoderException {
        assertEncode("assa", "asa");
        assertEncode("aço", "aso");
        assertEncode("asssssa", "asa");
    }

    @Test
    public void testAOeAMemAN() throws EncoderException {
        assertEncode("kam", "kan");
        assertEncode("kão", "kan");
        assertEncode("kã", "kan");
        assertEncode("kãod", "kand");
        assertEncode("kãp", "kanp");
        assertEncode("kamt", "kant");
        assertEncode("kaum", "kan");
        assertEncode("kãum", "kan");
        assertEncode("kaun", "kan");
        assertEncode("kãun", "kan");
    }

    @Test
    public void testAMbeforeVocal() throws EncoderException {
        assertEncode("ama", "ama");
    }

    @Test
    public void testOEeOISemOIN() throws EncoderException {
        assertEncode("pões", "poins");
        assertEncode("põis", "poins");
        assertEncode("poes", "poins");
        assertEncode("pois", "poins");
    }

    @Test
    public void testEAemEIA() throws EncoderException {
        assertEncode("déa", "deia");
        assertEncode("dea", "deia");
        assertEncode("dêa", "deia");
    }

    @Test
    public void testCeCCemK() throws EncoderException {
        assertEncode("teca", "teka");
        assertEncode("tecá", "teka");
        assertEncode("tecã", "tekan");
        assertEncode("tecca", "teka");
        assertEncode("teccá", "teka");
        assertEncode("teco", "teko");
        assertEncode("tecó", "teko");
        assertEncode("tecú", "teku");
        assertEncode("teccõ", "teko");
    }

    @Test
    public void testCemSifCBeforeEI() throws EncoderException {
        assertEncode("tece", "tese");
        assertEncode("teci", "tesi");
        assertEncode("tecí", "tesi");
        assertEncode("tecê", "tese");
    }

    @Test
    public void testRRemR() throws EncoderException {
        assertEncode("terra", "tera");
        assertEncode("terrra", "tera");
        assertEncode("terrrra", "tera");
    }

    @Test
    public void testTTemT() throws EncoderException {
        assertEncode("motta", "mota");
        assertEncode("mottta", "mota");
        assertEncode("motttta", "mota");
    }

    @Test
    public void testQUBeforeAandO() throws EncoderException {
        assertEncode("qua", "kua");
        assertEncode("quá", "kua");
        assertEncode("qüa", "kua");
        assertEncode("qüá", "kua");
        assertEncode("quo", "kuo");
        assertEncode("quó", "kuo");
        assertEncode("qüo", "kuo");
        assertEncode("qüó", "kuo");
    }

    @Test
    public void testQUBeforeU() throws EncoderException {
        assertEncode("quu", "ku");
        assertEncode("qüú", "ku");
        assertEncode("qüu", "ku");
        assertEncode("quú", "ku");
    }

    @Test
    public void testQUBeforeEandI() throws EncoderException {
        assertEncode("que", "ke");
        assertEncode("qué", "ke");
        assertEncode("qui", "ki");
        assertEncode("quí", "ki");
        assertEncode("qüe", "kue");
        assertEncode("qüé", "kue");
        assertEncode("qüi", "kui");
        assertEncode("qüí", "kui");
    }

    @Test
    public void testGbeforeEandI() throws EncoderException {
        assertEncode("ge", "je");
        assertEncode("gi", "ji");
        assertEncode("gé", "je");
        assertEncode("gí", "ji");
    }

    @Test
    public void testGSound() throws EncoderException {
        assertEncode("ga", "g1a");
        assertEncode("go", "g1o");
        assertEncode("gu", "g1u");
        assertEncode("gue", "g1e");
        assertEncode("gui", "g1i");
        assertEncode("güe", "g1ue");
        assertEncode("güi", "g1ui");
    }

    @Test
    public void testSWithSoundOFZ() throws EncoderException {
        assertEncode("asa", "aza");
        assertEncode("esaú", "ezau");
    }

    @Test
    public void testLWithSoundOfU() throws EncoderException {
        assertEncode("alto", "auto");
        assertEncode("samuel", "samueu");
    }

    @Test
    public void testLAsConsonant() throws EncoderException {
        assertEncode("ela", "ela");
        assertEncode("ele", "ele");
        assertEncode("lá", "la");
    }

    @Test
    public void testHMute() throws EncoderException {
        assertEncode("home", "ome");
        assertEncode("ha", "a");
        assertEncode("hó", "o");
        assertEncode("húmido", "umido");
        assertEncode("óhtimo", "otimo");
        assertEncode("thia", "tia");
    }

    @Test
    public void testNomeY() throws EncoderException {
        assertEncode("LUIS ATZORI BATISTA", "LUIZ ATZORI BATISTA");
        assertEncode("LUIS", "LUIZ");
        assertEncode("LUIS ", "LUIZ");
        assertEncode("LUIZA ", "LUIZA");
        assertEncode("LUIZA", "LUIZA");
        assertEncode("LUIZ ", "LUIZ");
        assertEncode("LUIZ", "LUIZ");
        assertEncode("GIOVANNA YASMIN RIBEIRO", "JIOVANNA IASMIN RIBEIRO");
        assertEncode("YASMIN ATZORI BATISTA", "IASMIN ATZORI BATISTA");
        assertEncode("GIOVANNA IASMIN RIBEIRO", "JIOVANNA IASMIN RIBEIRO");
        assertEncode("IASMIN ATZORI BATISTA", "IASMIN ATZORI BATISTA");
        assertEncode("GIOVANNA", "JIOVANNA");
        assertEncode("MARCOS ANTONIO OUTLOOK", "MARKOZ ANTONIO OUTLOOK");
        assertEncode("MARCOS ", "MARKOZ");
        assertEncode("MARCOS", "MARKOZ");
        assertEncode("MARIA EMANUELLY ", "MARIA EMANUELI");
        assertEncode("MARIA EMANUELLY", "MARIA EMANUELI");
    }

    private void assertEncode(String before, String after) throws EncoderException {
        PortuguesePhonetic encoder = new PortuguesePhonetic();
        String encoded = encoder.encode(before);
        String message = before + " should encode to " + after + " but encoded to " + encoded;
        MatcherAssert.assertThat(message, encoded.equals(after.toUpperCase()));
    }


    @Test
    public void jossanaTest() throws EncoderException{
        assertTranslate("Jos", "Joz");
        assertTranslate("Jos ", "Joz");
        assertTranslate("Joss ", "Jos");
        assertTranslate("Joss", "Jos");
        assertTranslate("Jossana", "Josana");
        assertTranslate("Ales", "Alez");
        assertTranslate("Ales ", "Alez");
        assertTranslate("Aless", "Ales");
        assertTranslate("Aless ", "Ales");
        assertTranslate("Jossana Teste 03", "JOSANA TESTE 03");
        assertTranslate("Alessandra Teste 03", "ALESANDRA TESTE 03");
    }

    private void assertTranslate(String before, String after) throws EncoderException {
        PortuguesePhonetic encoder = new PortuguesePhonetic();
        String encoded = encoder.translate(before);
        String message = before + " should encode to " + after + " but encoded to " + encoded;
        MatcherAssert.assertThat(message, encoded.equals(after.toUpperCase()));
    }
}
