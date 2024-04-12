package com.company.shoes.web.screens.product;

import com.company.shoes.entity.Product;
import com.company.shoes.entity.Sale;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;

@UiController("screen1")
@UiDescriptor("new-screen.xml")
public class NewScreen extends Screen {
    @Inject
    private CollectionLoader<Product> productsDl;
    @Inject
    private CollectionLoader<Sale> salesDl;
    @Inject
    private CollectionContainer<Sale> salesDc;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        productsDl.load();
    }

    @Subscribe("productsTable")
    public void onProductsTableSelection(Table.SelectionEvent<Product> event) {
        if (event.getSelected().isEmpty()) {
            salesDl.removeParameter("product");
            salesDc.setItems(null);
        } else {
            salesDl.setParameter("product", event.getSelected().toArray()[0]);
            salesDl.load();
        }
    }

}