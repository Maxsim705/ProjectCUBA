package com.company.shoes.web.screens.product;

import com.company.shoes.entity.Sale;
import com.company.shoes.web.screens.sale.SaleBrowse;
import com.company.shoes.web.screens.sale.SaleEdit;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.actions.list.AddAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.company.shoes.entity.Product;
import com.haulmont.cuba.gui.screen.LookupComponent;

import javax.inject.Inject;
import javax.inject.Named;

@UiController("shoes$Product.browse")
@UiDescriptor("product-browse.xml")
@LookupComponent("productsTable")
@LoadDataBeforeShow
public class ProductBrowse extends StandardLookup<Product> {

    @Inject
    protected DataManager dataManager;
    @Inject
    private UiComponents uiComponents;
    @Inject
    private Screens screens;
    @Inject
    private GroupTable<Product> productsTable;
    @Named("productsTable.createSale")
    private BaseAction productsTableCreateSale;
    @Inject
    private CollectionLoader<Product> productsDl;

    public Component generateColorCell(Product entity) {
        ColorPicker colorPicker = uiComponents.create(ColorPicker.NAME);
        colorPicker.setValue(entity.getColor());
        colorPicker.setEnabled(false);
        return colorPicker;
    }

    @Subscribe("productsTable.createSale")
    public void onProductsTableCreateSale(Action.ActionPerformedEvent event) {
        Sale sale = dataManager.create(Sale.class);
        sale.setProduct(productsTable.getSingleSelected());
        SaleEdit edit = screens.create(SaleEdit.class);
        edit.setEntityToEdit(sale);
        edit.addAfterCloseListener((e) -> {
            productsDl.load();
            if (e.closedWith(StandardOutcome.COMMIT))
                screens.create(SaleBrowse.class).show();
        });
        edit.show();
    }

    @Subscribe("productsTable")
    public void onProductsTableSelection(Table.SelectionEvent<Product> event) {
        if (productsTable.getSingleSelected() != null) {
            productsTableCreateSale.setEnabled(productsTable.getSingleSelected().getAmount() != 0);
        }
    }
}