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
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
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
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(@QueryParam("cryptocurrency") int id_cryptocurency, Order entity, @CookieParam("token") int id_customer) {
        Customer c = (Customer) em.createQuery("SELECT c FROM Customer c WHERE c.id = " + id_customer).getSingleResult();
        Cryptocurrency c2 = (Cryptocurrency) em.createQuery("SELECT c FROM Cryptocurrency c WHERE c.id = " + id_cryptocurency).getSingleResult();
        entity.setCustomer(c);
        entity.setCryptocurrency(c2);
        super.create(entity);
        float preu = entity.getAmount() * c2.getLastQuote();
        return Response.ok().entity("<html><body><h1>" + preu + "</h1></body></html>").build();
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
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Long id) {
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
