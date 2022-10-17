package model.entities;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.*;

@Entity
@Table(name = "CLIENT")
public class Client implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String name;
    private String email;
    private String password;
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
