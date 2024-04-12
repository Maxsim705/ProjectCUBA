package com.company.shoes.core.listeners;

import com.company.shoes.entity.Product;
import com.company.shoes.entity.Sale;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.listener.BeforeDeleteEntityListener;
import org.springframework.stereotype.Component;

@Component("shoes_SaleChangedListener")
public class SaleChangedListener implements BeforeDeleteEntityListener<Sale> {
    @Override
    public void onBeforeDelete(Sale entity, EntityManager entityManager) {
        Sale sale = entity;
        if (sale.getProduct() == null || sale.getProduct().getAmount() == null) {
            sale = entityManager.find(Sale.class, entity.getId(), "on-delete-sale");
        }
        assert sale != null;
        Product product = sale.getProduct();
        product.setAmount(product.getAmount() + sale.getAmount());
        entityManager.merge(product);
    }
}