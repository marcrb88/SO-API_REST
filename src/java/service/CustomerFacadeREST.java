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
import model.entities.Customer;
import authn.Secured;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.GenericEntity;
import java.util.ArrayList;

@Stateless
@Path("customer")
public class CustomerFacadeREST extends AbstractFacade<Customer> {
    
    @PersistenceContext(unitName = "Homework1PU")
    private EntityManager em;

    public CustomerFacadeREST() {
        super(Customer.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Customer entity) {
        Credentials credentials = new Credentials();
        credentials.setUsername(entity.getEmail());
        credentials.setPassword(entity.getPassword());
        em.persist(credentials);
        super.create(entity);
    }

    @PUT
    @Secured
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, Customer entity) {
        entity.setId(id);
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findWithoutPasswd(@PathParam("id") Integer id) {
        Customer customer = super.find(id);
        
        Customer customerWithoutPasswd = new Customer(customer.getId(), customer.getName(), customer.getEmail(), customer.getPhone());
        
        return Response.ok().entity(customerWithoutPasswd).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findAllWithoutPasswd() {
        List<Customer> customers = super.findAll();
        
        List<Customer> customersWithoutPasswd = new ArrayList();
        Customer customerWithoutPasswd;
        for (Customer customer : customers) {
            customerWithoutPasswd = new Customer(customer.getId(), customer.getName(), customer.getEmail(), customer.getPhone());
            customersWithoutPasswd.add(customerWithoutPasswd);
        }
        final GenericEntity<List<Customer>> gCustomers = new GenericEntity<List<Customer>>(customersWithoutPasswd) {};
        return Response.ok().entity(gCustomers).build();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Customer> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
