package de.jonashackt.springbootvuejs.domain;

import javax.persistence.*;

@Entity
@Table(name = "price_book")
public class PriceBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String type;
    private float price;

    public PriceBook(String type,float price) {
        this.type = type;
        this.price = price;
    }
    public PriceBook() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}

