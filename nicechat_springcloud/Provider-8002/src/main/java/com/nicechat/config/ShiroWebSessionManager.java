package com.nicechat.config;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

public class ShiroWebSessionManager extends DefaultWebSessionManager {

    public static final String LOGIN_TOKEN_KEY = "token";
    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    public ShiroWebSessionManager() {
    }

    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest)request;
        String sessionId = req.getHeader(LOGIN_TOKEN_KEY);
        System.out.println("getSessionId" + sessionId);
        if (!"".equals(sessionId)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "Stateless request");
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return sessionId;
        } else {
            return super.getSessionId(request, response);
        }
    }
}
