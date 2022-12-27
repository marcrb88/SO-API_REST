package service;

import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.Response;
import model.entities.ErrorMessage;
import model.entities.Order;

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
    @Path("{id}/order")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response find(@PathParam("id") Integer id) {
        try {
            Order order = (Order) em.createNamedQuery("Order.findByCryptocurrency")
                    .setParameter("cryptocurrency_id", id)
                    .setMaxResults(1)
                    .getSingleResult();
            return Response.ok().entity(order).build();
        } catch (NoResultException e){
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage("No existeix la cryptomoneda amb id " + id)).build();
        }
    }
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findCryptocurrency(@PathParam("id") Integer id) {
        try {
           Cryptocurrency crypto = super.find(id);
            return Response.ok().entity(crypto).build();
        } catch (NoResultException e){
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage("No existeix la cryptomoneda amb id " + id)).build();
        }
    }
    
    
 
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findAll(@QueryParam("order") String order) {
        if (order == null){
            order = "desc";
        }
        if (order.equalsIgnoreCase("asc") || order.equalsIgnoreCase("desc")){
            List<Cryptocurrency> cryptocurrencies;
            if (order.equalsIgnoreCase("asc")){
                cryptocurrencies = em.createNamedQuery("Cryptocurrency.findAllASC").getResultList();
            } else {
                cryptocurrencies = em.createNamedQuery("Cryptocurrency.findAllDESC").getResultList();
            }
            if (cryptocurrencies.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage("No existeixen cryptomonedes")).build();
            }
            final GenericEntity<List<Cryptocurrency>> gCryptocurrencies = new GenericEntity<List<Cryptocurrency>>(cryptocurrencies) {};
            return Response.ok().entity(gCryptocurrencies).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage("El valor del parametre order es invalid")).build();
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
