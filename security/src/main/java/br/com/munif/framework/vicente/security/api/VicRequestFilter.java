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
import br.com.munif.framework.vicente.security.service.interfaces.ITokenService;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author munif
 */
public class VicRequestFilter extends HandlerInterceptorAdapter {

    private final ITokenService tokenService;

    private final Logger log = Logger.getLogger(VicRequestFilter.class.getSimpleName());

    public VicRequestFilter(ITokenService tokenService) {
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
        String tokenValue = getAuthorization(request);
        if (tokenValue != null) {
            Token token = tokenService.findTokenByValue(tokenValue);
            User u = token.getUser();
            VicThreadScope.token.set(tokenValue);
            VicThreadScope.gi.set(u.stringGroups());
            VicThreadScope.ui.set(u.getLogin());
            VicThreadScope.oi.set(u.stringOrganization());
            if (request.getHeader("group") != null)
                VicThreadScope.cg.set("" + request.getHeader("group"));
            else
                VicThreadScope.cg.set(null);
            if (request.getHeader("organization") != null)
                VicThreadScope.oi.set("" + request.getHeader("organization"));
            VicThreadScope.defaultRights.set(null);
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
        return true;
    }


    private String getAuthorization(HttpServletRequest request) {
        String tokenValue = request.getHeader("Authorization");
        if (tokenValue == null) tokenValue = request.getParameter("Authorization");
        return tokenValue;
    }

}
