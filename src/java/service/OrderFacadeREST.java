package service;

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
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.util.Date;
import java.util.StringTokenizer;
import model.entities.Cryptocurrency;
import model.entities.Customer;

@Stateless
@Path("order")
public class OrderFacadeREST extends AbstractFacade<Order> {

    @PersistenceContext(unitName = "Homework1PU")
    private EntityManager em;

    public OrderFacadeREST() {
        super(Order.class);
    }

    @POST
    @Secured
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.TEXT_PLAIN)
    public Response create(@QueryParam("cryptocurrency") int id_cryptocurrency, Order entity, @HeaderParam("Authorization") String credentials) {
        String decode = Base64.base64Decode(credentials.replace("Basic ", ""));
        StringTokenizer tokenizer = new StringTokenizer(decode, ":");
        String username = tokenizer.nextToken();
        
        Customer customer = (Customer) em.createNamedQuery("Customer.findByUsername")
                .setParameter("username", username)
                .getSingleResult();
        Cryptocurrency cryptocurrency = (Cryptocurrency) em.createNamedQuery("Cryptocurrency.find")
                .setParameter("id_cryptocurrency", id_cryptocurrency)
                .getSingleResult();
        
        entity.setCustomer(customer);
        entity.setCryptocurrency(cryptocurrency);
        entity.setDatePurchase(new Date());
        
        super.create(entity);
        
        float preu = entity.getAmount() * cryptocurrency.getLastQuote();
        return Response.ok().entity("Comanda amb id " + entity.getId() + " amb preu " + preu + " amb data " + entity.getDatePurchase()).build();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Order entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Secured
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response find(@PathParam("id") Integer id) {
        return Response.ok().entity(super.find(id)).build();
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Order> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Order> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
