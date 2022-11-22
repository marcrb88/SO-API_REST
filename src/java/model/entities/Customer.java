package model.entities;

import java.util.Collection;
import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;

@Entity
@NamedQuery(name = "Customer.findCustomer",
        query = "SELECT c FROM Customer c WHERE c.email = :username")
@XmlRootElement
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name="Customer_Gen", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Customer_Gen")
    private int id;

    @NotNull(message="Username can't be null")
    private String name;
    
    //@Column(unique=true)
    @NotNull(message="Email can't be null")
    private String email;
    
    @NotNull(message="Password can't be null")
    private String password;
    
    //@Column(unique=true)
    private String phone;

    @OneToMany(mappedBy="customer")
    private Collection<Order> orders;
    
    
    public Customer() {}
    
    public Customer(int id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = "*******";
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
}
