/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.security.api;

import br.com.munif.framework.vicente.core.VicThreadScope;
import br.com.munif.framework.vicente.domain.BaseEntity;
import br.com.munif.framework.vicente.security.domain.Grupo;
import br.com.munif.framework.vicente.security.domain.Token;
import br.com.munif.framework.vicente.security.domain.Usuario;
import br.com.munif.framework.vicente.security.service.TokenService;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        VicThreadScope.ip.set(request.getRemoteAddr());
        String tokenValue = request.getHeader("Authorization");
        System.out.println("---->" + tokenValue);
        if (tokenValue != null) {
            Token token = tokenService.findUserByToken(tokenValue);
            if (token != null) {
                Usuario u = token.getUsuario();
                VicThreadScope.gi.set(u.stringGrupos());
                VicThreadScope.ui.set(u.getId());
                VicThreadScope.oi.set(u.getStringOrganizacao());
                System.out.println("-----> Autorizado " + u);
            }
        } else {
            VicThreadScope.gi.set(null);
            VicThreadScope.ui.set(null);
            VicThreadScope.oi.set(null);
            System.out.println("-----> Nao Autorizado ");
        }

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