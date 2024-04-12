package com.company.shoes.web.screens.sale;

import com.haulmont.cuba.gui.screen.*;
import com.company.shoes.entity.Sale;

@UiController("shoes$Sale.browse")
@UiDescriptor("sale-browse.xml")
@LookupComponent("salesTable")
@LoadDataBeforeShow
public class SaleBrowse extends StandardLookup<Sale> {
}