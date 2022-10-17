package model.entities;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "PURCHASE")
public class Purchase implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private Date datePurchase;
    private float amount;
    
    @ManyToOne
    private Client client;
    
    @OneToOne
    private Coin coin;
    
    
    public Purchase() {
    }

    public Purchase(Date datePurchase, float amount, Client client, Coin coin) {
        this.datePurchase = datePurchase;
        this.amount = amount;
        this.client = client;
        this.coin = coin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatePurchase() {
        return datePurchase;
    }

    public void setDatePurchase(Date datePurchase) {
        this.datePurchase = datePurchase;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Coin getCoin() {
        return coin;
    }

    public void setCoin(Coin coin) {
        this.coin = coin;
    }
    
    @Override
    public String toString() {
        return ("datePurchase: " + this.datePurchase + " amount: " + this.amount + " client: " + this.client + " coin:" + this.coin);
    }
    
}
