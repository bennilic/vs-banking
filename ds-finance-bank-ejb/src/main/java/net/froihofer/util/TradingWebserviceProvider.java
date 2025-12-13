package net.froihofer.util;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import jakarta.xml.ws.BindingProvider;
import net.froihofer.dsfinance.ws.trading.api.TradingWebServiceService;
import net.froihofer.dsfinance.ws.trading.api.TradingWebService;

@Stateless
public class TradingWebserviceProvider {

    //@Inject
    private TradingWebServiceService tradingWebServiceService;

    @RolesAllowed({"employee", "customer"})
    public TradingWebService getTradingWebService() {
        TradingWebService tradingWebService = tradingWebServiceService.getTradingWebServicePort();
        BindingProvider bindingProvider = (BindingProvider) tradingWebService;
        bindingProvider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "csdc26bb_03"); // TODO move to env
        bindingProvider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "Voak5eesei"); // TODO move to env
        return tradingWebService;
    }
}
