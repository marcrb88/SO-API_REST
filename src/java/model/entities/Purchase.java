package model.entities;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Entity
@XmlRootElement
public class Purchase implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="Purchase_Gen", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Purchase_Gen")
    private int id;

    @NotNull(message="Date can't be null")
    private Date datePurchase;
    
    @NotNull(message="Amount can't be null")
    private float amount;
    
    @NotNull(message="Client can't be null")
    @ManyToOne
    private Client client;
    
    @NotNull(message="Coin can't be null")
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
