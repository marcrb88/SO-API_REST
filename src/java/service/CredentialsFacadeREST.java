package service;

import authn.Credentials;
import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.entities.Order;
import authn.Secured;
import com.sun.xml.messaging.saaj.util.Base64;
import jakarta.persistence.NoResultException;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.util.Date;
import java.util.StringTokenizer;
import model.entities.Cryptocurrency;
import model.entities.Customer;
import model.entities.ErrorMessage;

@Stateless
@Path("credentials")
public class CredentialsFacadeREST extends AbstractFacade<Credentials> {

    @PersistenceContext(unitName = "Homework1PU")
    private EntityManager em;

    public CredentialsFacadeREST() {
        super(Credentials.class);
    }


    @GET
    //@Secured
    @Path("{username}/{password}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response checkAuthentication(@PathParam("username") String username,
            @PathParam("password") String password) {
        
        Credentials credentials = (Credentials) em.createNamedQuery("Credentials.findByCustomer")
                .setParameter("username", username).setParameter("password", password)
                .getSingleResult();
         
 
        return Response.ok().entity(credentials).build();
          
        
    }


 
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
  
 
}
