package service;

import jakarta.ejb.Stateless;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Stateless
@Path("helloworld")
public class HelloWorld {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response findWithoutPasswd() {
        return Response.ok().entity("Hello World!").build();
    }
}
