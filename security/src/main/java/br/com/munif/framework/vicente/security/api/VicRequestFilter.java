/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.security.api;

import br.com.munif.framework.vicente.api.VicenteErrorOnRequestException;
import br.com.munif.framework.vicente.api.VicenteRightsException;
import br.com.munif.framework.vicente.core.RightsHelper;
import br.com.munif.framework.vicente.core.VicThreadScope;
import br.com.munif.framework.vicente.core.VicThreadScopeOptions;
import br.com.munif.framework.vicente.security.domain.Token;
import br.com.munif.framework.vicente.security.domain.User;
import br.com.munif.framework.vicente.security.domain.profile.ForwardRequest;
import br.com.munif.framework.vicente.security.domain.profile.OperationFilter;
import br.com.munif.framework.vicente.security.domain.profile.OperationType;
import br.com.munif.framework.vicente.security.service.TokenService;
import br.com.munif.framework.vicente.security.service.profile.OperationFilterService;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author munif
 */
public class VicRequestFilter extends HandlerInterceptorAdapter {

    private TokenService tokenService;
    private OperationFilterService operationFilterService;
    private final Logger log = Logger.getLogger(VicRequestFilter.class.getSimpleName());

    public VicRequestFilter(TokenService tokenService, OperationFilterService operationFilterService) {
        this.tokenService = tokenService;
        this.operationFilterService = operationFilterService;
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
        Token token = tokenService.findUserByToken(tokenValue);
        if (token != null) {
            User u = token.getUser();
            VicThreadScope.token.set(tokenValue);
            VicThreadScope.gi.set(u.stringGroups());
            VicThreadScope.ui.set(u.getLogin());
            VicThreadScope.oi.set(u.stringOrganization());
            if (request.getHeader("group") != null)
                VicThreadScope.cg.set("" + request.getHeader("group"));
            else
                VicThreadScope.cg.set(null);
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
        return filterRequest(request, handler, tokenValue, token);
    }

    public boolean filterRequest(HttpServletRequest request, Object handler, String tokenValue, Token token) throws IOException {
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
        OperationFilter operationFilter = operationFilterService.findByOperationKeyAndToken(apiName, hm.getMethod().getName(), token);
        for (ForwardRequest forwardRequest : operationFilter.getForwardRequests()) {
            HttpHeaders headers = new HttpHeaders();
            headers.set(forwardRequest.getAuthorizationHeaderName(), tokenValue);
            if (forwardRequest.getDefaultAuthorizationHeader() != null) {
                headers.set(forwardRequest.getAuthorizationHeaderName(), forwardRequest.getDefaultAuthorizationHeader());
            }
            String s = IOUtils.toString(request.getInputStream(), Charset.defaultCharset());
            try {
                restTemplate().exchange(forwardRequest.getUrl(), forwardRequest.getMethod(), new HttpEntity<>(s, headers), Map.class);
            } catch (Exception ex) {
                if (VicThreadScopeOptions.ENABLE_FORWARD_REQUEST_EXCEPTION.getValue()) {
                    throw new VicenteErrorOnRequestException("Was not possible to request the " + forwardRequest.getUrl());
                }
                log.info("Error on request:" + forwardRequest);
            }
        }
        if (OperationType.NOT_ALLOW.equals(operationFilter.getOperationType())) {
            throw new VicenteRightsException("You do not have the rights to request this resource.");
        }
        return true;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    private String getAuthorization(HttpServletRequest request) {
        String tokenValue = "" + request.getHeader("Authorization");
        if (tokenValue.equals("null")) tokenValue = "" + request.getParameter("Authorization");
        return tokenValue;
    }

}
