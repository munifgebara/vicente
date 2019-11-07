/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.security.api;

import br.com.munif.framework.vicente.core.RightsHelper;
import br.com.munif.framework.vicente.core.VicThreadScope;
import br.com.munif.framework.vicente.security.domain.Token;
import br.com.munif.framework.vicente.security.domain.User;
import br.com.munif.framework.vicente.security.service.TokenService;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**
 * @author munif
 */
public class VicRequestFilter extends HandlerInterceptorAdapter {

    private TokenService tokenService;

    public VicRequestFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    private List<String> publics = Collections.singletonList("/api/token/login/bypassword");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS,HEAD");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, authorization, Connection, group");

        if ("OPTIONS".equalsIgnoreCase(method)) {
            return true;
        }
        VicThreadScope.ip.set(request.getRemoteAddr());
        String tokenValue = "" + request.getHeader("Authorization");
        Token token = tokenService.findUserByToken(tokenValue);
        if (token != null) {
            User u = token.getUser();
            VicThreadScope.token.set(tokenValue);
            VicThreadScope.gi.set(u.stringGrupos());
            VicThreadScope.ui.set(u.getId());
            VicThreadScope.oi.set(u.stringOrganizacao());
            VicThreadScope.cg.set("" + request.getHeader("group"));
        } else if (publics.contains(request.getRequestURI())) {
            VicThreadScope.gi.set("VIC_PUBLIC");
            VicThreadScope.ui.set("VIC_PUBLIC");
            VicThreadScope.oi.set("VIC_PUBLIC");
            VicThreadScope.cg.set("VIC_PUBLIC");
            VicThreadScope.defaultRights.set(RightsHelper.ALL_READ);
        } else {
            VicThreadScope.gi.set(null);
            VicThreadScope.ui.set(null);
            VicThreadScope.oi.set(null);
            VicThreadScope.cg.set(null);
        }
        HandlerMethod hm;


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
