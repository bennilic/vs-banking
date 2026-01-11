package net.froihofer.util;

import jakarta.annotation.Resource;
import jakarta.ejb.SessionContext;

public class SessionUtils {

    @Resource
    private SessionContext sessionContext;

    public boolean isEmployee() {
        return sessionContext.isCallerInRole("employee");
    }
}
