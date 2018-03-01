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
 *
 * @author munif
 */
public class RightsHelper {

    //                                     876543210
    public static final int ALL_READ =   0b100100100;
    public static final int OWNER_READ = 0b100000000;
    public static final int OWNER_UPDATE = 0b010000000;
    public static final int OWNER_DELETE = 0b001000000;
    public static final int OWNER_ALL = 0b111000000;
    public static final int OWNER_READ_UPDATE = 0b110000000;

    public static final int GROUP_READ = 0b000100000;
    public static final int GROUP_UPDATE = 0b000010000;
    public static final int GROUP_DELETE = 0b000001000;
    public static final int GROUP_ALL = 0b000111000;
    public static final int GROUP_READ_UPDATE = 0b000110000;

    public static final int OTHER_READ = 0b000000100;
    public static final int OTHER_UPDATE = 0b000000010;
    public static final int OTHER_DELETE = 0b000000001;

    public static int getDefault() {
        if (VicThreadScope.defaultRights.get() != null) {
            return VicThreadScope.defaultRights.get();
        }
        return OWNER_READ + OWNER_UPDATE + OWNER_DELETE + GROUP_READ;
    }

    public static int getNOT_READT() {
        return OWNER_UPDATE + OWNER_DELETE;
    }

    public static String getMainGi() {
        String get = VicThreadScope.gi.get();
        if (get == null) {
            return null;
        }
        if (get.contains(",")) {
            return get.substring(0, get.indexOf(','));
        }
        return get;
    }

}

