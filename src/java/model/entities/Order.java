package model.entities;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Entity
@NamedQuery(name = "Order.findByCryptocurrency",
        query = "SELECT o FROM Order o WHERE o.cryptocurrency.id = :cryptocurrency_id ORDER BY o.id DESC")
@XmlRootElement
@Table(name="PURCHASE")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="Order_Gen", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Order_Gen")
    private int id;

    @NotNull(message="Date can't be null")
    private Date datePurchase;
    
    @NotNull(message="Amount can't be null")
    private float amount;
    
    private float euros;
    
    @NotNull(message="Client can't be null")
    @ManyToOne
    private Customer customer;
    
    @NotNull(message="Coin can't be null")
    @ManyToOne
    private Cryptocurrency cryptocurrency;
    
    
    public Order() {}

    public Order(int id, Date datePurchase, float amount, Customer customer, Cryptocurrency cryptocurrency) {
        this.id = id;
        this.datePurchase = datePurchase;
        this.amount = amount;
        this.customer = customer;
        this.cryptocurrency = cryptocurrency;
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

    public float getEuros() {
        return euros;
    }

    public void setEuros(float euros) {
        this.euros = euros;
    }
    
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Cryptocurrency getCryptocurrency() {
        return cryptocurrency;
    }

    public void setCryptocurrency(Cryptocurrency cryptocurrency) {
        this.cryptocurrency = cryptocurrency;
    }
    
}
