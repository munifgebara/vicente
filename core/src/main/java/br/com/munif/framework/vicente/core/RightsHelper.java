/*
OWNER_READ 256
OWNER_UPDATE 128
OWNER_DELETE 64
GROUP_READ 32
GROUP_UPDATE 16
GROUP_DELETE 8
OTHER_READ 4
OTHER_UPDATE 2
OTHER_DELETE 1

 */
package br.com.munif.framework.vicente.core;

/**
 * @author munif
 */
public class RightsHelper {

    //                                     876543210
    public static final int OTHER_DELETE = 0b000000001; //1
    public static final int OTHER_UPDATE = 0b000000010; //2
    public static final int OTHER_READ = 0b000000100;   //4

    public static final int GROUP_DELETE = 0b000001000; //8
    public static final int GROUP_UPDATE = 0b000010000; //16
    public static final int GROUP_READ = 0b000100000;  //32
    public static final int GROUP_READ_UPDATE = 0b000110000; //48
    public static final int GROUP_ALL = 0b000111000; //56

    public static final int OWNER_DELETE = 0b001000000; //64
    public static final int OWNER_UPDATE = 0b010000000; //128
    public static final int OWNER_READ = 0b100000000; //256
    public static final int ALL_READ = 0b100100100; //292
    public static final int OWNER_READ_UPDATE = 0b110000000; //384
    public static final int OWNER_ALL = 0b111000000; //448
    public static final int DEFAULT = OWNER_READ + OWNER_UPDATE + OWNER_DELETE + GROUP_READ;
    public static final int ALL_UPDATE = OWNER_UPDATE + GROUP_UPDATE + OTHER_UPDATE; //292
    public static final int ALL_DELETE = OWNER_DELETE + GROUP_DELETE + OTHER_DELETE; //292
    public static final int ALL = ALL_READ + ALL_UPDATE + ALL_DELETE;

    public static int getScopeDefault() {
        if (VicThreadScope.defaultRights.get() != null) {
            return VicThreadScope.defaultRights.get();
        }
        return getDefault();
    }

    public static int getDefault() {
        return DEFAULT;
    }

    public static int getNOT_READT() {
        return OWNER_UPDATE + OWNER_DELETE;
    }

    public static String getMainGi() {
        String cg = VicThreadScope.cg.get();
        if (cg != null && !cg.isEmpty()) {
            return cg;
        }
        String get = VicThreadScope.gi.get();
        if (get != null) {
            if (get.contains(",")) {
                return get.substring(0, get.indexOf(','));
            } else {
                return get;
            }
        }
        return null;
    }

    public static String getStringRights(Integer rights, String ui, String gi) {
        Integer r = rights != null ? rights : 0;
        String toReturn = "";
        toReturn += "ui:" + ui + "(";
        toReturn += (OWNER_READ & r) > 0 ? "R" : "-";
        toReturn += (OWNER_UPDATE & r) > 0 ? "U" : "-";
        toReturn += (OWNER_DELETE & r) > 0 ? "D" : "-";
        toReturn += ") ";
        toReturn += "gi:" + gi + "(";
        toReturn += (GROUP_READ & r) > 0 ? "R" : "-";
        toReturn += (GROUP_UPDATE & r) > 0 ? "U" : "-";
        toReturn += (GROUP_DELETE & r) > 0 ? "D" : "-";
        toReturn += ") ";
        toReturn += "o(";
        toReturn += (OTHER_READ & r) > 0 ? "R" : "-";
        toReturn += (OTHER_UPDATE & r) > 0 ? "U" : "-";
        toReturn += (OTHER_DELETE & r) > 0 ? "D" : "-";
        toReturn += ") ";
        return toReturn;
    }


}

