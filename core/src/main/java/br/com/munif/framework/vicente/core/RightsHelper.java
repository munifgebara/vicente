/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.core;

/**
 *
 * @author munif
 */
public class RightsHelper {
    
    //                                     876543210
    public static final int OWNER_READ=  0b100000000;
    public static final int OWNER_UPDATE=0b010000000;
    public static final int OWNER_DELETE=0b001000000;

    public static final int GROUP_READ=  0b000100000;
    public static final int GROUP_UPDATE=0b000010000;
    public static final int GROUP_DELETE=0b000001000;

    public static final int OTHER_READ=  0b000000100;
    public static final int OTHER_UPDATE=0b000000010;
    public static final int OTHER_DELETE=0b000000001;
    
    public static int getDefault(){
        if (VicThreadScope.defaultRights.get()!=null){
            return VicThreadScope.defaultRights.get();
        }
        return OWNER_READ+OWNER_UPDATE+OWNER_DELETE+GROUP_READ;
    }

    public static int getNOT_READT(){
        return OWNER_UPDATE+OWNER_DELETE;
    }

    public static String getMainGi() {
        String get = VicThreadScope.gi.get();
        if (get==null){
            return null;
        }
        if (get.contains(",")){
            return get.substring(0, get.indexOf(','));
        }
        return get;
    }
    
    public static void main(String []args){
        System.out.println("ALL---->"+0b111111111);
    }

    
}
