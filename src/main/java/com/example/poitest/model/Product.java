package com.example.poitest.model;

import org.springframework.data.domain.Persistable;
import org.springframework.data.util.ProxyUtils;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Access(AccessType.FIELD)
public class Product implements Persistable<Integer> {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "vendor_code")
    @Size(min = 2, max = 128)
    @NotBlank
    private String vendorCode;

    @Column(name = "name")
    @Size(min = 2, max = 128)
    @NotBlank
    private String name;

    @Column(name = "description")
    @Size(min = 2, max = 128)
    @NotBlank
    private String description;

    @Column(name = "price")
//    https://www.baeldung.com/javax-bigdecimal-validation
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=4, fraction=2)
    private BigDecimal price;


    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public boolean isNew() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(ProxyUtils.getUserClass(o))) {
            return false;
        }
        Product that = (Product) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id;
    }
}
