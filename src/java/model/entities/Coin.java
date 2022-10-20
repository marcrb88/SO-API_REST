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
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Entity
@XmlRootElement
public class Coin implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="Coin_Gen", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Coin_Gen")
    private int id;

    //@Column(unique=true)
    @NotNull(message="Coin name can't be null")
    private String name;
    
    private String description;
    
    private float lastQuote;
    
    private Date lastQuoteTime;

    @ManyToMany
    private Collection<Client> clients;
    
    @OneToOne(mappedBy="coin")
    public Purchase purchase;
    
    public Coin(){
    }

    public Coin(String name, String description, float lastQuote, Date lastQuoteTime) {
        this.name = name;
        this.description = description;
        this.lastQuote = lastQuote;
        this.lastQuoteTime = lastQuoteTime;
        this.clients = new ArrayList<>();
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

    public float getLastQuote() {
        return lastQuote;
    }

    public void setLastQuote(float lastQuote) {
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
