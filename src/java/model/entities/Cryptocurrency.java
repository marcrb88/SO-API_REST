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
import java.util.Date;

@Entity
@NamedQuery(name = "Cryptocurrency.find",
        query = "SELECT c FROM Cryptocurrency c WHERE c.id = :id_cryptocurrency")
@NamedQuery(name = "Cryptocurrency.findAllASC",
        query = "SELECT c FROM Cryptocurrency c ORDER BY c.lastQuote ASC")
@NamedQuery(name = "Cryptocurrency.findAllDESC",
        query = "SELECT c FROM Cryptocurrency c ORDER BY c.lastQuote DESC")
@XmlRootElement
public class Cryptocurrency implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="Cryptocurrency_Gen", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Cryptocurrency_Gen")
    private int id;

    @Column(unique=true)
    @NotNull(message="Coin name can't be null")
    private String name;
    
    private String description;
    
    @NotNull(message="Coin last quote can't be null")
    private float lastQuote;
    
    @NotNull(message="Coin last quote time can't be null")
    private Date lastQuoteTime;
    
    @OneToMany(mappedBy="cryptocurrency")
    private Collection<Order> orders;
    
    
    public Cryptocurrency() {}

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

}
