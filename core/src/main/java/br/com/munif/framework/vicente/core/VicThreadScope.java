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
public class VicThreadScope {

    public static final ThreadLocal<String> gi = new ThreadLocal<>();
    
    public static final ThreadLocal<String> ui = new ThreadLocal<>();
    
    public static final ThreadLocal<String> ip = new ThreadLocal<>();
    
    public static final ThreadLocal<String> oi = new ThreadLocal<>();
    
    public static final ThreadLocal<Integer> defaultRights = new ThreadLocal<>();
}
