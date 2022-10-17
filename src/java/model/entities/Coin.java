package model.entities;

import java.util.Collection;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "COIN")
public class Coin implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String name;
    private String description;
    private double lastQuote;
    private Date lastQuoteTime;

    @ManyToMany
    private Collection<Client> clients;
    
    @OneToOne(mappedBy="coin")
    public Purchase purchase;
    
    public Coin(){
        
    }

    public Coin(String name, String description, double lastQuote, Date lastQuoteTime) {
        this.name = name;
        this.description = description;
        this.lastQuote = lastQuote;
        this.lastQuoteTime = lastQuoteTime;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLastQuote() {
        return lastQuote;
    }

    public void setLastQuote(double lastQuote) {
        this.lastQuote = lastQuote;
    }

    public Date getLastQuoteTime() {
        return lastQuoteTime;
    }

    public void setLastQuoteTime(Date lastQuoteTime) {
        this.lastQuoteTime = lastQuoteTime;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }
    
    public void addClients(Client client){
        clients.add(client);
    }
    
    @Override
    public String toString() {
        return ("Name: " + this.name + " Desc: " + this.description + " lastQuote: " + this.lastQuote + " lastQuoteTime:" + this.lastQuoteTime);
    }

}
