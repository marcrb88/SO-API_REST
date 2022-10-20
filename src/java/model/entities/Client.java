package model.entities;

import jakarta.persistence.Column;
import java.util.ArrayList;
import java.util.Collection;
import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name="Client_Gen", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Client_Gen")
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

    @OneToMany(mappedBy="client")
    final private Collection<Purchase> purchases;
    
    @ManyToMany(mappedBy="clients")
    final private Collection<Coin> coins;

    public Client() {
        this.purchases = new ArrayList<>();
        this.coins = new ArrayList<>();
    }

    public Client(String name, String mail, String passwd, String phone) {
        this.name = name;
        this.email = mail;
        this.password = passwd;
        this.phone = phone;
        this.purchases = new ArrayList<>();
        this.coins = new ArrayList<>();
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
    
    public void addPurchase(Purchase purchase){
        purchases.add(purchase);
    }
    
    public void addCoin(Coin coin){
        coins.add(coin);
    }

    @Override
    public String toString() {
        return ("Name: " + this.name + " Mail: " + this.email + " Password: " + this.password + " Phone:" + this.phone);
    }
}
