package com.company.shoes.entity;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.chile.core.annotations.NumberFormat;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.*;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Date;

@PublishEntityChangedEvents
@Table(name = "shoes_sale")
@Entity(name = "shoes$Sale")
@NamePattern("%s %s|product,total")
@Listeners("shoes_SaleChangedListener")
public class Sale extends StandardEntity {
    private static final long serialVersionUID = -1850882974557772090L;

    @NotNull
    @JoinColumn(name = "product_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDeleteInverse(DeletePolicy.CASCADE)
    protected Product product;

    @NotNull
    @Column(name = "date", nullable = false)
    protected Date date;

    @NotNull
    @Positive
    @Column(name = "price", nullable = false)
    @CurrencyValue(currency = "₽", labelPosition = CurrencyLabelPosition.RIGHT)
    @NumberFormat(pattern = "#,###,##0.00", decimalSeparator = ",")
    protected BigDecimal price;

    @NotNull
    @Positive
    @Column(name = "amount", nullable = false)
    protected Integer amount;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price == null ? (getProduct() == null ? null : getProduct().getPrice()) : price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @MetaProperty(related = {"amount", "price"})
    @NumberFormat(pattern = "#,###,###,##0.00", decimalSeparator = ",")
    public BigDecimal getTotal() {
        return amount != null && price != null ? new BigDecimal(amount).multiply(price) : null;
    }
}