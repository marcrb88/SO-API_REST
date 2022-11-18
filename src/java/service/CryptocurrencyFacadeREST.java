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
import model.entities.Cryptocurrency;
import authn.Secured;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.Response;

@Stateless
@Path("cryptocurrency")
public class CryptocurrencyFacadeREST extends AbstractFacade<Cryptocurrency> {

    @PersistenceContext(unitName = "Homework1PU")
    private EntityManager em;

    public CryptocurrencyFacadeREST() {
        super(Cryptocurrency.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Cryptocurrency entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Cryptocurrency entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    //@Secured
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Integer id) {
        return Response.ok().entity(super.find(id)).build();
    }
   

    @GET
    @Produces({MediaType.TEXT_HTML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findAll(@QueryParam("order") String order) {
        if (order == null){
            order = "desc";
        }
        if (order.equalsIgnoreCase("asc") || order.equalsIgnoreCase("desc")){
            List<Cryptocurrency> c = em.createNamedQuery("Cryptocurrency.findAll").setParameter("order", order).getResultList();
            final GenericEntity<List<Cryptocurrency>> c2 = new GenericEntity<List<Cryptocurrency>>(c) {};
            return Response.ok().entity(c2).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("<html><body><h1>El valor del parametre order es invalid</h1></body></html>").build();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cryptocurrency> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
