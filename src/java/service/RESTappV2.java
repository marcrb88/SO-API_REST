package service;

import authn.*;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@jakarta.ws.rs.ApplicationPath("rest/api/v2")
public class RESTappV2 extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(AbstractFacade.class);
        classes.add(CryptocurrencyFacadeREST.class);
        classes.add(CustomerFacadeREST.class);
        classes.add(OrderFacadeREST.class);
        classes.add(RESTRequestFilter.class);
        classes.add(HelloWorld.class);
        return classes;
    }
}
