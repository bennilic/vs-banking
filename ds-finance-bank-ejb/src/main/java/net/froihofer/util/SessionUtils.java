package net.froihofer.util;

import jakarta.annotation.Resource;
import jakarta.ejb.SessionContext;

public class SessionUtils {

    @Resource
    private SessionContext sessionContext;

    public String getLoggedInUser() {
        return sessionContext.getCallerPrincipal().getName();
    }

    public boolean isEmployee() {
        return sessionContext.isCallerInRole("employee");
    }
}
