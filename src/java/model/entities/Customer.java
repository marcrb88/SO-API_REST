package model.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;

@Entity
@NamedQuery(name = "Customer.find", query = "SELECT c.id, c.email, c.name, c.phone FROM Customer c WHERE c.id = :id")
@NamedQuery (name = "Customer.findAll", query = "SELECT c.id, c.email, c.name, c.phone FROM Customer c")
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
    final private Collection<Order> orders;
    
    @ManyToMany(mappedBy="customers")
    final private Collection<Cryptocurrency> cryptocurrencies;
    
    public Customer() {
        this.orders = new ArrayList<>();
        this.cryptocurrencies = new ArrayList<>();
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
