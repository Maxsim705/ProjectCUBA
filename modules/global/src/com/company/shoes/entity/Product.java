package com.company.shoes.entity;

import com.company.shoes.enums.GenderAge;
import com.company.shoes.enums.Season;
import com.company.shoes.enums.ShoeType;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.chile.core.annotations.NumberFormat;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.CurrencyLabelPosition;
import com.haulmont.cuba.core.entity.annotation.CurrencyValue;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Table(name = "shoes_product")
@Entity(name = "shoes$Product")
@NamePattern("%s|name")
public class Product extends StandardEntity {
    private static final long serialVersionUID = 8119939650607820542L;

    @NotNull
    @Column(name = "name", nullable = false)
    protected String name;

    @Column(name = "type")
    protected String type;

    @Column(name = "brand")
    protected String brand;

    @NotNull
    @Min(value = 25)
    @Max(value = 50)
    @Column(name = "size", nullable = false)
    protected Integer size;

    @Column(name = "gender_age")
    protected String genderAge;

    @Column(name = "color")
    protected String color;

    @Column(name = "season")
    protected String season;

    @Min(value = 2000)
    @Max(value = 2023)
    @Column(name = "product_year")
    @NumberFormat(pattern = "#")
    protected Integer productYear;

    @PositiveOrZero
    @Max(value = 6)
    @Column(name = "guarantee")
    @NumberFormat(pattern = "#0.0", decimalSeparator = ",")
    protected Double guarantee;

    @NotNull
    @Column(name = "code", nullable = false)
    protected String code;

    @NotNull
    @Positive
    @Column(name = "price", nullable = false)
    @CurrencyValue(currency = "₽", labelPosition = CurrencyLabelPosition.RIGHT)
    @NumberFormat(pattern = "#,###,##0.00", decimalSeparator = ",", groupingSeparator = " ")
    protected BigDecimal price;

    @NotNull
    @PositiveOrZero
    @Column(name = "amount", nullable = false)
    protected Integer amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ShoeType getType() {
        return type == null ? null : ShoeType.fromId(type);
    }

    public void setType(ShoeType type) {
        this.type = type == null ? null : type.getId();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public GenderAge getGenderAge() {
        return genderAge == null ? null : GenderAge.fromId(genderAge);
    }

    public void setGenderAge(GenderAge genderAge) {
        this.genderAge = genderAge == null ? null : genderAge.getId();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Season getSeason() {
        return season == null ? null : Season.fromId(season);
    }

    public void setSeason(Season season) {
        this.season = season == null ? null : season.getId();
    }

    public Integer getProductYear() {
        return productYear;
    }

    public void setProductYear(Integer productYear) {
        this.productYear = productYear;
    }

    public Double getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(Double guarantee) {
        this.guarantee = guarantee;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}