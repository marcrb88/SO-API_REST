package Test;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RESTClient {

    private final WebTarget webTarget;
    private final Client client;
    private static final String BASE_URI = "http://localhost:8080/SO-API_REST/rest/api/v1/";
    private final String credentials = "c29iOnNvYgo=";

    public RESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI);
    }

    public Response getCryptocurrency(Integer id) throws ClientErrorException {
        WebTarget resource = webTarget.path("cryptocurrency").path(String.valueOf(id));
        return resource.request(MediaType.APPLICATION_XML).get();
    }
    
    public Response getCryptocurrencyByOrder(String order) throws ClientErrorException {
        WebTarget resource = webTarget.path("cryptocurrency").queryParam("order",order);
        return resource.request(MediaType.APPLICATION_XML).get();
    }
    
    public Response getOrderbyId(Integer id) throws ClientErrorException {
        WebTarget resource = webTarget.path("order").path(String.valueOf(id));
        return resource.request(MediaType.APPLICATION_XML).header(HttpHeaders.AUTHORIZATION, "Basic " + credentials).get();
    }
    
    //Com es podrien pasar les credencials sense el Header Authorization??
    public Response postCrypto(Integer idCrypto, Integer amount, Integer idOrder) throws ClientErrorException {
         WebTarget resource = webTarget.path("order").queryParam("cryptocurrency", idCrypto);
         String postOrder = "<order>"
                 + "<amount>"+amount+"</amount>"
                 + "<id>"+idOrder+"</id>"
                 + "</order>";
         return resource.request().header(HttpHeaders.AUTHORIZATION, "Basic " + credentials).post(Entity.entity(postOrder, MediaType.APPLICATION_XML), Response.class);
    }
    
    public Response getCustomers() throws ClientErrorException {
        WebTarget resource = webTarget.path("customer");
        return resource.request(MediaType.APPLICATION_XML).get();
    }
    
     public Response getCustomerById(Integer id) throws ClientErrorException {
        WebTarget resource = webTarget.path("customer").path(String.valueOf(id));
        return resource.request(MediaType.APPLICATION_XML).get();
    }
     
     public Response updateCustomerById(Integer id, String email, Integer newId, String name, String phone) throws ClientErrorException {
         WebTarget resource = webTarget.path("customer").path(String.valueOf(id));
         String updateCustomer = "<customer>"
                 + "<email>"+email+"</email>"
                 + "<id>"+newId+"</id>"
                 + "<name>"+name+"</name>"
                 + "<phone>"+phone+"</phone>"
                 + "</customer>";
                 
         return resource.request().header(HttpHeaders.AUTHORIZATION, "Basic " + credentials).put(Entity.entity(updateCustomer, MediaType.APPLICATION_XML), Response.class);
    }
     

    public void close() {
        client.close();
    }
    
    public static void main(String[] args) {
        RESTClient client = new RESTClient();
        
        //GET /rest/api/v1/cryptocurrency/${id}
        Response responseGetCrypto = client.getCryptocurrency(1);
        System.out.println("Get Cryptocurrency by Id: " + responseGetCrypto.readEntity(String.class) + ",  Status: " + responseGetCrypto.getStatus());
     
        //GET /rest/api/v1/cryptocurrency?order=${order}
        Response responseGetCryptoByOrder = client.getCryptocurrencyByOrder("DESC");
        System.out.println("Get cryptocurrency by order: " + responseGetCryptoByOrder.readEntity(String.class) + ",  Status: " + responseGetCryptoByOrder.getStatus());
        
        //POST /rest/api/v1/order?cryptocurrency=${id}
        Response responsePostCrypto = client.postCrypto(1,500, 70);
        System.out.println("Post crypto: " + responsePostCrypto.readEntity(String.class) + ",  Status: " + responsePostCrypto.getStatus());
        
        //GET /rest/api/v1/order/${id}
        Response responseGetOrderbyId = client.getOrderbyId(1);
        System.out.println("Get order by Id: " + responseGetOrderbyId.readEntity(String.class) + ",  Status: " + responseGetOrderbyId.getStatus());
        
        
        // GET /rest/api/v1/customer
        Response responseGetCustomers = client.getCustomers();
        System.out.println("Get customers: " + responseGetCustomers.readEntity(String.class) + ",  Status: " + responseGetCustomers.getStatus());
        
        // GET /rest/api/v1/customer/${id}
        Response responseGetCustomerById = client.getCustomerById(1);
        System.out.println("Get customer by Id: " + responseGetCustomerById.readEntity(String.class) + ",  Status: " + responseGetCustomerById.getStatus());
        
        
        //PUT /rest/api/v1/customer/${id}
        Response responseUpdateCustomerById = client.updateCustomerById(1, "marcTESTING@gmail.com",1, "sob","564232");
        System.out.println("Update customer by Id: " + responseUpdateCustomerById.readEntity(String.class) + ",  Status: " + responseUpdateCustomerById.getStatus());
        
        //GET /rest/api/v1/customer/${id}
        Response responseTestingCustomerUpdate = client.getCustomerById(1);
        System.out.println("Testing customer update: " + responseTestingCustomerUpdate.readEntity(String.class) + ",  Status: " + responseTestingCustomerUpdate.getStatus());
        
        
        
        client.close();
    }
}
