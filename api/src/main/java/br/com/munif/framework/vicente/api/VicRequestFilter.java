/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.api;

import br.com.munif.framework.vicente.core.VicThreadScope;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author munif
 */
public class VicRequestFilter extends HandlerInterceptorAdapter {

    private final String software;

    public VicRequestFilter(String software) {
        this.software = software;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        VicThreadScope.gi.set("G1");
        VicThreadScope.ui.set("U1");
        VicThreadScope.oi.set("1.");
        VicThreadScope.ip.set(request.getRemoteAddr());
        HandlerMethod hm;

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS,HEAD");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, authorization, Connection, userRecognition");

        if (handler instanceof HandlerMethod) {
            hm = (HandlerMethod) handler;
        } else {
            return true;
        }
        String apiName = hm.getBean().getClass().getSimpleName();
        if (apiName.contains("$$")) {
            apiName = apiName.substring(0, apiName.indexOf("$$"));
        }
        String operationKey = apiName + "_" + hm.getMethod().getName();

        return true;
    }

}
