/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.core;


import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import static java.util.Calendar.*;

/**
 *
 * @author daniel
 * @author anderson
 */
public class DataUtil {

     private static final int DIAS_NO_MES[] = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static DateFormatSymbols dfs = new DateFormatSymbols(new Locale("pt", "BR"));
    public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy_MM_dd");
    
    private static String homeFolder(){
        return System.getProperty("user.home");
    }

    public static Date getDataHoraMinutoSegundoZerado(Date valorData) {
        Calendar c = Calendar.getInstance();

        c.setTime(valorData);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTime();
    }

    public static String format(Date data) {
        return sdf.format(data);
    }

    public static String format2(Date data) {
        return sdf2.format(data);
    }

    public static List<Calendar> mesesEntreDatas(Date inicio, Date fim) {
        List<Calendar> aRetornar = new ArrayList<Calendar>();
        Calendar data = Calendar.getInstance();
        data.setTime(inicio);
        data.set(Calendar.DAY_OF_MONTH, 1);
        while (data.getTime().getTime() <= fim.getTime()) {
            aRetornar.add((Calendar) data.clone());
            data.add(Calendar.MONTH, 1);
        }
        return aRetornar;
    }

    public static String nomeDoMes(int m) {
        String month = "invalid";
        String[] months = dfs.getMonths();
        if (m >= 0 && m <= 11) {
            month = months[m];
        }
        return month;
    }

    public static Date amanhaUltimoSegundo() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    public static Date ultimoDiaDoMes() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    public static Date depoisDeAmanhaPrimeiroSegundo() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 2);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 1);
        return cal.getTime();
    }

    public static String formatddMMyyyy(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(date);
    }

    public static void main(String[] args) {

        Locale ptBr = new Locale("pt", "BR");
        Locale.setDefault(ptBr);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1975);
        cal.set(Calendar.MONTH, Calendar.AUGUST);
        cal.set(Calendar.DAY_OF_MONTH, 18);
        Date dataMenor = cal.getTime();

        cal = Calendar.getInstance();
        // cal.set(Calendar.YEAR, 2012);
        //  cal.set(Calendar.MONTH, Calendar.AUGUST);
        //  cal.set(Calendar.DAY_OF_MONTH, 31);
        Date dataMaior = cal.getTime();

        for (Calendar m : mesesEntreDatas(dataMenor, dataMaior)) {
            //System.out.println(nomeDoMes(m.get(Calendar.MONTH)));
        }
    }

    public static int diferencaMesesInteira(Date dataAnterior, Date dataPosterior) {
        if (dataAnterior.after(dataPosterior)) {
            return 0;
        }
        Calendar calMenor = Calendar.getInstance();
        calMenor.setTime(dataAnterior);
        Calendar calMaior = Calendar.getInstance();
        calMaior.setTime(dataPosterior);
        int dAno = calMaior.get(YEAR) - calMenor.get(YEAR);
        int dMes = calMaior.get(MONTH) - calMenor.get(MONTH);
        int dDia = calMaior.get(DAY_OF_MONTH) - calMenor.get(DAY_OF_MONTH);

        if (dMes < 0) {
            dMes += 12;
            dAno--;
        }
        if (dDia < 0) {
            dMes--;
        }

        return dMes + dAno * 12;
    }

    public static double diferencaMesesFracionada(Date dataAnterior, Date dataPosterior) {
        if (dataAnterior.after(dataPosterior)) {
            return 0;
        }
        Calendar calMenor = Calendar.getInstance();
        calMenor.setTime(dataAnterior);
        Calendar calMaior = Calendar.getInstance();
        calMaior.setTime(dataPosterior);
        int dAno = calMaior.get(YEAR) - calMenor.get(YEAR);
        int dMes = calMaior.get(MONTH) - calMenor.get(MONTH);
        int dDia = calMaior.get(DAY_OF_MONTH) - calMenor.get(DAY_OF_MONTH);

        if (dMes < 0) {
            dMes += 12;
            dAno--;
        }
        if (dDia < 0) {
            dDia += quantidadeDiasMesAnterior(dataPosterior);
            dMes--;
        }
        return dAno * 12 + dMes + (dDia / (double) quantidadeDiasMesAnterior(dataPosterior));
    }

    public static int getDiasNoMes(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return getDiasNoMes(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
    }

    public static int getDiasNoMes(int mes, int ano) {
        if (mes == 2 && isBissexto(ano)) {
            return 29;
        }
        return DIAS_NO_MES[mes];
    }

    public static boolean isBissexto(int ano) {
        if (ano % 400 == 0) {
            return true;
        }
        if (ano % 100 == 0) {
            return false;
        }
        if (ano % 4 == 0) {
            return true;
        }
        return false;
    }

    public static int quantidadeDiasMesAnterior(Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.set(DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, -1);
        return getDiasNoMes(cal.getTime());
    }

    public static int diferencaDiasInteira(Date dataAnterior, Date dataPosterior) {
        Calendar calAnterior = Calendar.getInstance();
        calAnterior.setTime(dataAnterior);
        calAnterior.set(HOUR_OF_DAY, 0);
        calAnterior.set(MINUTE, 0);
        calAnterior.set(SECOND, 0);
        calAnterior.set(MILLISECOND, 0);
        Date dataAnteriorSemHora = calAnterior.getTime();

        Calendar calPosterior = Calendar.getInstance();
        calPosterior.setTime(dataPosterior);
        calPosterior.set(HOUR_OF_DAY, 13);
        calPosterior.set(MINUTE, 0);
        calPosterior.set(SECOND, 0);
        calPosterior.set(MILLISECOND, 0);
        Date dataPosteriorSemHora = calPosterior.getTime();
        if (dataAnteriorSemHora.after(dataPosteriorSemHora)) {
            return 0;
        }

        Long diferenca = (dataPosteriorSemHora.getTime() - dataAnteriorSemHora.getTime()) / (24 * 60 * 60 * 1000);
        return diferenca.intValue();
    }

    public static Date dataInicial() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -3);
        return cal.getTime();
    }

    public static Date dataFinal() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 4);
        return cal.getTime();
    }

    public static List<Date> dozeMeses() {
        List<Date> aRetornar = new ArrayList<Date>();
        for (int i = 0; i < 12; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.add(Calendar.MONTH, i);
            aRetornar.add(calendar.getTime());
        }
        return aRetornar;
    }

    public static Date dataAutalComDiasDoParametro(int dias) { // se o parametro for negativo ele VOLTA a quantidade de dias a partir de hoje
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, dias);
        return cal.getTime();
    }

    // se o parametro for negativo ele VOLTA a quantidade de dias a partir de hoje
    public static Date dataAutalComMesesDoParametro(int meses) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, meses);
        return cal.getTime();
    }

    public static int horaAtual() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static String formatarData(String pattern, Date data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(data);
    }

    public static int diaDoMes(Date data) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        return c.get(c.DAY_OF_MONTH);
    }

    public static Date dataParametroComDiasDoParametro(int dias, Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.DAY_OF_MONTH, dias);
        return cal.getTime();
    }
    public static Date dataParametroComSegundosDoParametro(int segundos, Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.SECOND, segundos);
        return cal.getTime();
    }

    public static Date dataParametroComDiasEMesesDoParametro(int dias, int meses, Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.DAY_OF_MONTH, dias);
        cal.add(Calendar.MONTH, meses);
        return cal.getTime();
    }

    public static Date dataParametroComMesesDoParametro(int mes, Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.MONTH, mes);
        return cal.getTime();
    }

    public static long diferencaEntreDias(Date dataInicial, Date dataFinal) {
        Calendar calInicial = Calendar.getInstance();
        calInicial.setTime(dataInicial);
        Calendar calFinal = Calendar.getInstance();
        calFinal.setTime(dataFinal);
        return (calFinal.getTime().getTime() - calInicial.getTime().getTime()) / 86400000; //1000(milisegundo) / 60(segundo) / 60(minitos) / 24 (horas)
    }

    public static int anoAtual() {
        Calendar c = Calendar.getInstance();
        return c.get(c.YEAR);
    }

    public static int diaAtual() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static int anoAtualMenosParamentro(int valorSubtrair) {
        Calendar c = Calendar.getInstance();
        int anoAtual = c.get(c.YEAR);
        return anoAtual > valorSubtrair ? anoAtual - valorSubtrair : anoAtual - 10;
    }

    public static Date juntarMesAnoComDia(int dia, Date data) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        c.set(c.get(c.YEAR), c.get(c.MONTH), dia);
        return c.getTime();
    }

    /**
     * Verifica se as datas sã do mesmo dia; Exemplo 2015-05-05 é o mesmo dia de
     * 2015-05-05
     *
     * @param data1 primeira data a ser verificada.
     * @param data2 segunda data a ser verificada.
     *
     * @return retora true caso seja no mesmo dia e false caso nao seja mesmo
     * dia
     *
     */
    public static boolean mesmoDia(Date data1, Date data2) {
        int dif = diferencaDiasInteira(data1, data2);
        return dif == 0;
    }

    public static Integer getMes(Date data) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        return c.get(c.MONTH) + 1;
    }

    public static Integer getAno(Date data) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        return c.get(c.YEAR);
    }

    public static Date transformarStringEmData(String data) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return format.parse(data);
        } catch (ParseException ex) {
        }
        return null;
    }

    /**
     * Transforma a data que vem em string para data do tipo Date. data em
     * string deve obedecer o param patter, ou seja, a data deve estar no padrão
     * informado no outro parametro.
     *
     * @param data exemplo "01/05/2016"
     * @param pattern exemplo "dd/MM/yyyy"
     *
     * @return data do tipo DATE.
     */
    public static Date transformarStringEmData(String data, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return format.parse(data);
        } catch (ParseException ex) {
        }
        return null;
    }

    public static String mesAtualPorExtenso() {
        return mesPorExtenso(Calendar.getInstance().getTime());
    }

    public static String mesPorExtenso(Date data) {
        Calendar inicio = Calendar.getInstance();
        inicio.setTime(data);
        switch (inicio.get(inicio.MONTH)) {
            case 0:
                return "Janeiro";
            case 1:
                return "Fevereiro";
            case 2:
                return "Março";
            case 3:
                return "Abril";
            case 4:
                return "Maio";
            case 5:
                return "Junho";
            case 6:
                return "Julho";
            case 7:
                return "Agosto";
            case 8:
                return "Setembro";
            case 9:
                return "Outubro";
            case 10:
                return "Novembro";
            case 11:
                return "Dezembro";
            default:
                return "";
        }
    }

    public static Date primeiroSegundoDia(Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public static Date primeiroSegundoSeteDiasAntesDeHoje() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 7);
        return cal.getTime();
    }

    public static Date primeiroSegundoDeHojeMaisDiasDoParamentro(int dias) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + dias);
        return cal.getTime();
    }
    
    public static Date ultimoSegundoDeHojeMaisDiasDoParamentro(int dias) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(ultimoSegundoDia(new Date()));
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + dias);
        return cal.getTime();
    }

    public static Date ultimoSegundoDia(Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }
}
