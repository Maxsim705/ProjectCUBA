package com.company.shoes.web.screens.product;

import com.haulmont.cuba.gui.screen.*;
import com.company.shoes.entity.Product;

@UiController("shoes$Product.edit")
@UiDescriptor("product-edit.xml")
@EditedEntityContainer("productDc")
@LoadDataBeforeShow
public class ProductEdit extends StandardEditor<Product> {
}