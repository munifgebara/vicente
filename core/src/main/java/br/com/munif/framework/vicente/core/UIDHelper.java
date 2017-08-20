package br.com.munif.framework.vicente.core;

import java.net.NetworkInterface;
import java.util.Enumeration;

public class UIDHelper {

    private static long lastTime;

    private static String myMac;

    public static synchronized String getUID() {
        if (myMac==null){
            myMac=getMacValue();
        }
        long time = System.currentTimeMillis() * 100;
        while (time <= lastTime) {
            time++;
        }
        lastTime = time;
        return "" + Long.toHexString(time).toUpperCase()+myMac;
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

    public static void main(String args[]) {
        for (int i = 0; i < 1000; i++) {
            System.out.println("----->" + getUID());
        }

    }
}
