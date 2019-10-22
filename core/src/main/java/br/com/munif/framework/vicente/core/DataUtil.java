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
 * @author daniel
 * @author anderson
 */
public class DataUtil {

    private static final int[] MONTH_DAYS = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static DateFormatSymbols dfs = new DateFormatSymbols(new Locale("pt", "BR"));
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy_MM_dd");

    private static String homeFolder() {
        return System.getProperty("user.home");
    }

    public static Date getDateHourMinuteSecondZero(Date valorData) {
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

    public static List<Calendar> monthsBetweenDates(Date inicio, Date fim) {
        List<Calendar> toReturn = new ArrayList<Calendar>();
        Calendar date = Calendar.getInstance();
        date.setTime(inicio);
        date.set(Calendar.DAY_OF_MONTH, 1);
        while (date.getTime().getTime() <= fim.getTime()) {
            toReturn.add((Calendar) date.clone());
            date.add(Calendar.MONTH, 1);
        }
        return toReturn;
    }

    public static String MonthName(int m) {
        String month = "invalid";
        String[] months = dfs.getMonths();
        if (m >= 0 && m <= 11) {
            month = months[m];
        }
        return month;
    }

    public static Date tomorrowLastSecond() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    public static Date lastMonthDay() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    public static Date afterTomorrowFirstSecond() {
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

    public static int entireDifferenceMonth(Date previousDate, Date dataPosterior) {
        if (previousDate.after(dataPosterior)) {
            return 0;
        }
        Calendar calMenor = Calendar.getInstance();
        calMenor.setTime(previousDate);
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

    public static double fractionalDifferenceMonth(Date previousDate, Date dataPosterior) {
        if (previousDate.after(dataPosterior)) {
            return 0;
        }
        Calendar calMenor = Calendar.getInstance();
        calMenor.setTime(previousDate);
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
            dDia += amountDaysLastMonth(dataPosterior);
            dMes--;
        }
        return dAno * 12 + dMes + (dDia / (double) amountDaysLastMonth(dataPosterior));
    }

    public static int getDaysOnMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return getDaysOnMonth(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
    }

    public static int getDaysOnMonth(int month, int year) {
        if (month == 2 && isLeapYear(year)) {
            return 29;
        }
        return MONTH_DAYS[month];
    }

    public static boolean isLeapYear(int ano) {
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

    public static int amountDaysLastMonth(Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.set(DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, -1);
        return getDaysOnMonth(cal.getTime());
    }

    public static int entireDaysDifference(Date dataAnterior, Date dataPosterior) {
        Calendar calAnterior = Calendar.getInstance();
        calAnterior.setTime(dataAnterior);
        calAnterior.set(HOUR_OF_DAY, 0);
        calAnterior.set(MINUTE, 0);
        calAnterior.set(SECOND, 0);
        calAnterior.set(MILLISECOND, 0);
        Date previousDateWithoutTime = calAnterior.getTime();

        Calendar calPosterior = Calendar.getInstance();
        calPosterior.setTime(dataPosterior);
        calPosterior.set(HOUR_OF_DAY, 13);
        calPosterior.set(MINUTE, 0);
        calPosterior.set(SECOND, 0);
        calPosterior.set(MILLISECOND, 0);
        Date laterDateWithoutTime = calPosterior.getTime();
        if (previousDateWithoutTime.after(laterDateWithoutTime)) {
            return 0;
        }

        Long diferenca = (laterDateWithoutTime.getTime() - previousDateWithoutTime.getTime()) / (24 * 60 * 60 * 1000);
        return diferenca.intValue();
    }

    public static Date inicialDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -3);
        return cal.getTime();
    }

    public static Date finalDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 4);
        return cal.getTime();
    }

    public static List<Date> twelveMonths() {
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

    public static Date actualDateWithDays(int dias) { // se o parametro for negativo ele VOLTA a quantidade de dias a partir de hoje
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, dias);
        return cal.getTime();
    }

    // se o parametro for negativo ele VOLTA a quantidade de dias a partir de hoje
    public static Date actualDateWithMonths(int meses) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, meses);
        return cal.getTime();
    }

    public static int actualHour() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static String formatDate(String pattern, Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static int monthDate(Date data) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        return c.get(c.DAY_OF_MONTH);
    }

    public static Date addDays(int dias, Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.DAY_OF_MONTH, dias);
        return cal.getTime();
    }

    public static Date addSeconds(int segundos, Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.SECOND, segundos);
        return cal.getTime();
    }

    public static Date addDaysAndMonths(int dias, int meses, Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.DAY_OF_MONTH, dias);
        cal.add(Calendar.MONTH, meses);
        return cal.getTime();
    }

    public static Date addMonths(int mes, Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.MONTH, mes);
        return cal.getTime();
    }

    public static long differenceBetween(Date dataInicial, Date dataFinal) {
        Calendar calInicial = Calendar.getInstance();
        calInicial.setTime(dataInicial);
        Calendar calFinal = Calendar.getInstance();
        calFinal.setTime(dataFinal);
        return (calFinal.getTime().getTime() - calInicial.getTime().getTime()) / 86400000; //1000(milisegundo) / 60(segundo) / 60(minitos) / 24 (horas)
    }

    public static int actualYear() {
        Calendar c = Calendar.getInstance();
        return c.get(c.YEAR);
    }

    public static int actualDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static int actualYearLess(int valorSubtrair) {
        Calendar c = Calendar.getInstance();
        int anoAtual = c.get(c.YEAR);
        return anoAtual > valorSubtrair ? anoAtual - valorSubtrair : anoAtual - 10;
    }

    public static Date JoinMonthYearWithDay(int dia, Date data) {
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
     * @return retora true caso seja no mesmo dia e false caso nao seja mesmo
     * dia
     */
    public static boolean isSameDay(Date data1, Date data2) {
        int dif = entireDaysDifference(data1, data2);
        return dif == 0;
    }

    public static Integer getMonth(Date data) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        return c.get(c.MONTH) + 1;
    }

    public static Integer getYear(Date data) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        return c.get(c.YEAR);
    }

    public static Date StringToDate(String data) {
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
     * @param data    exemplo "01/05/2016"
     * @param pattern exemplo "dd/MM/yyyy"
     * @return data do tipo DATE.
     */
    public static Date StringToDate(String data, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return format.parse(data);
        } catch (ParseException ex) {
        }
        return null;
    }

    public static String actualMonthInFull() {
        return monthInFull(Calendar.getInstance().getTime());
    }

    public static String monthInFull(Date data) {
        Calendar inicio = Calendar.getInstance();
        inicio.setTime(data);
        switch (inicio.get(inicio.MONTH)) {
            case 0:
                return "January";
            case 1:
                return "February";
            case 2:
                return "March";
            case 3:
                return "April";
            case 4:
                return "May";
            case 5:
                return "Juny";
            case 6:
                return "July";
            case 7:
                return "August";
            case 8:
                return "September";
            case 9:
                return "October";
            case 10:
                return "November";
            case 11:
                return "December";
            default:
                return "";
        }
    }

    public static Date firstSecond(Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public static Date firstSecondSevenPreviousDays() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 7);
        return cal.getTime();
    }

    public static Date firstSecondAfterTodatMorDays(int dias) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + dias);
        return cal.getTime();
    }

    public static Date lastSecondMoreDays(int dias) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(lastSecondDay(new Date()));
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + dias);
        return cal.getTime();
    }

    public static Date lastSecondDay(Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }
}
