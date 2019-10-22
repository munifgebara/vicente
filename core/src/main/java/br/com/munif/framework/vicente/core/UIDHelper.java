package br.com.munif.framework.vicente.core;

import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author munif
 */
public class UIDHelper {

    public static long lastTime;

    public static String myMac;

    public static long simpleIdInitialCount = 0;

    public static synchronized String getSimpleID(Class c) {
        String prefixo = c.getSimpleName().substring(0, 3);
        simpleIdInitialCount++;
        return String.format("%s%09d", prefixo, simpleIdInitialCount);
    }

    public static synchronized String getUID() {
        if (myMac == null) {
            myMac = getMacValue();
        }
        long time = System.currentTimeMillis() * 100;
        while (time <= lastTime) {
            time++;
        }
        lastTime = time;
        return "" + Long.toHexString(time).toUpperCase() + myMac;
    }

    public static String getMacValue() {
        String toReturn = "";
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface network = networkInterfaces.nextElement();
                byte[] mac = network.getHardwareAddress();
                if (mac == null) {
                    continue;
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X", mac[i]));
                }
                toReturn += sb.toString();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (toReturn.length() == 0) {
            throw new RuntimeException("Impossible to find NetworkInterface");
        }
        return toReturn;
    }
}
