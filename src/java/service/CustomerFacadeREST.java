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
import com.sun.xml.messaging.saaj.util.Base64;
import jakarta.persistence.NoResultException;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.GenericEntity;
import java.util.StringTokenizer;
import model.entities.ErrorMessage;
import model.entities.Login;
import model.entities.Register;

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
        super.create(entity);
    }

    @PUT
    @Secured
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response edit(@PathParam("id") Integer id, Customer entity, @HeaderParam("Authorization") String credentials) {
        String decode = Base64.base64Decode(credentials.replace("Basic ", ""));
        StringTokenizer tokenizer = new StringTokenizer(decode, ":");
        String username = tokenizer.nextToken();
        
        Customer customer = (Customer) em.createNamedQuery("Customer.findByUsername")
                .setParameter("username", username)
                .getSingleResult();
        if (id != customer.getId()) {
            return Response.status(Response.Status.FORBIDDEN).entity(new ErrorMessage("No pots editar un altre usuari")).build();
        }
        entity.setId(id);
        super.edit(entity);
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response find(@PathParam("id") Integer id) {
        Customer customer = super.find(id);
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage("No existeix l'usuari amb id " + id)).build();
        }
        return Response.ok().entity(customer).build();
    }
    
    @POST
    @Path("login")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response login(Login login) {
        
        Customer customer = em.createNamedQuery("Customer.findByUsername", Customer.class)
                .setParameter("username", login.getUsername())
                .getSingleResult();
        Credentials credentials = (Credentials) em.createNamedQuery("Credentials.findByCustomer")
                .setParameter("customer_id", customer.getId())
                .getSingleResult();
        if(!credentials.getPassword().equals(login.getPassword())) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
 
        return Response.ok().entity(customer).build();
    }
    
    @POST
    @Path("register")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response register(Register register) {
                
        try {
            em.createNamedQuery("Customer.findByUsername", Customer.class)
                .setParameter("username", register.getUsername())
                .getSingleResult();
        
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage("Ja existeix aquest usuari")).build();
        } catch (NoResultException e) {
            Customer customer = new Customer();
            customer.setName(register.getName());
            customer.setEmail(register.getUsername());
            customer.setPhone(register.getPhone());
            
            super.create(customer);
            
            Credentials credentials = new Credentials();
            credentials.setCustomer(customer);
            credentials.setPassword(register.getPassword());
            
            getEntityManager().persist(credentials);
                        
            return Response.ok().entity(customer).build();
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findAllWithoutPasswd() {
        List<Customer> resultList = super.findAll();
        if (resultList.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage("No existeixen usuaris")).build();
        }
        return Response.ok().entity(new GenericEntity<List<Customer>>(resultList) {}).build();
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
