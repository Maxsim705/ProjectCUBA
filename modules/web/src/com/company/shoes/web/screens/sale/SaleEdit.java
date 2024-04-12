package com.company.shoes.web.screens.sale;

import com.company.shoes.entity.Product;
import com.haulmont.bali.util.ParamsMap;
import com.haulmont.chile.core.datatypes.DatatypeRegistry;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.validation.AbstractValidator;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.company.shoes.entity.Sale;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import java.util.function.Consumer;

@UiController("shoes$Sale.edit")
@UiDescriptor("sale-edit.xml")
@EditedEntityContainer("saleDc")
@LoadDataBeforeShow
public class SaleEdit extends StandardEditor<Sale> {
    @Inject
    private Notifications notifications;
    @Inject
    private Messages messages;
    @Inject
    private CurrencyField<@NotNull @Positive BigDecimal> priceField;
    @Inject
    private DateField<Date> dateField;
    @Inject
    private TextField<Integer> amountField;
    private MaxAmountValidator maxAmountValidator;
    @Inject
    private DataManager dataManager;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        dateField.setRangeEnd(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    @Subscribe("productField")
    public void onProductFieldValueChange(HasValue.ValueChangeEvent<Product> event) {
        Product product = event.getValue();
        if (product == null) {
            setFieldsEditable(false);
            return;
        }
        if (product.getAmount() == null) {
            setFieldsEditable(false);
            notifications.create()
                    .withCaption(messages.getMainMessage("error.caption"))
                    .withDescription(messages.getMainMessage("error.description"))
                    .withType(Notifications.NotificationType.ERROR)
                    .show();
            return;
        }
        if (product.getAmount() == 0) {
            setFieldsEditable(false);
            event.getComponent().setValue(null);
            notifications.create()
                    .withCaption(messages.getMessage(getClass(), "saleEdit.product.zeroAmount"))
                    .withType(Notifications.NotificationType.ERROR)
                    .show();
        } else {
            setFieldsEditable(true);
            if (dataManager.load(new LoadContext<>(Sale.class).setId(getEditedEntity().getId())) == null) {
                priceField.setValue(product.getPrice());
                dateField.setValue(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                amountField.setValue(product.getAmount());
            }
        }
        updateMaxAmountValidator();
    }

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        View view = getScreenData().getContainer("saleDc").getView();
        assert view != null;
        Sale oldSale = dataManager.load(new LoadContext<>(Sale.class).setView(view).setId(getEditedEntity().getId()));
        Sale newSale = getEditedEntity();
        Set<StandardEntity> entities = new HashSet<>();
        if (oldSale != null) {
            if (oldSale.getProduct().getId() == newSale.getProduct().getId()) {
                int diff = newSale.getAmount() - oldSale.getAmount();
                newSale.getProduct().setAmount(newSale.getProduct().getAmount() - diff);
            } else {
                oldSale.getProduct().setAmount(oldSale.getProduct().getAmount() + oldSale.getAmount());
                newSale.getProduct().setAmount(newSale.getProduct().getAmount() - newSale.getAmount());
                entities.add(oldSale.getProduct());
            }
        } else {
            newSale.getProduct().setAmount(newSale.getProduct().getAmount() - newSale.getAmount());
        }
        event.getDataContext().merge(entities);
    }

    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPreCommit(DataContext.PreCommitEvent event) {
        View view = getScreenData().getContainer("saleDc").getView();
        assert view != null;
        Sale oldSale = dataManager.load(new LoadContext<>(Sale.class).setView(view).setId(getEditedEntity().getId()));
        Sale newSale = getEditedEntity();
        if (oldSale != null) {
            if (oldSale.getProduct().getId() == newSale.getProduct().getId()) {
                int diff = newSale.getAmount() - oldSale.getAmount();
                newSale.getProduct().setAmount(newSale.getProduct().getAmount() - diff);
            } else {
                oldSale.getProduct().setAmount(oldSale.getProduct().getAmount() + oldSale.getAmount());
                newSale.getProduct().setAmount(newSale.getProduct().getAmount() - newSale.getAmount());
                event.getModifiedInstances().add(oldSale.getProduct());
            }
        } else {
            newSale.getProduct().setAmount(newSale.getProduct().getAmount() - newSale.getAmount());
        }
        event.getModifiedInstances().add(newSale);
        event.getModifiedInstances().add(newSale.getProduct());
    }

    protected void updateMaxAmountValidator() {
        if (maxAmountValidator == null) {
            for (Consumer<Integer> validator : amountField.getValidators()) {
                if (validator instanceof MaxAmountValidator) {
                    maxAmountValidator = (MaxAmountValidator) validator;
                    break;
                }
            }
        }
        Product product = getEditedEntity().getProduct();
        if (product != null && product.getAmount() != null) {
            maxAmountValidator.setAmount(getEditedEntity().getProduct().getAmount());
        }
    }
    protected void setFieldsEditable(Boolean editable) {
        if (!editable) {
            priceField.setValue(null);
            dateField.setValue(null);
            amountField.setValue(null);
        }
        priceField.setEnabled(editable);
        dateField.setEnabled(editable);
        amountField.setEnabled(editable);
    }

    public static class MaxAmountValidator extends AbstractValidator<Integer> {
        Integer amount;

        public MaxAmountValidator() {
            messages = AppBeans.get(Messages.class);
            message = messages.getMainMessage("validation.constraints.max");
            datatypeRegistry = AppBeans.get(DatatypeRegistry.class);
            userSessionSource = AppBeans.get(UserSessionSource.class);
        }

        @Override
        public void accept(Integer integer) throws ValidationException {
            if (amount != null && integer != null && integer > amount) {
                throw new ValidationException(getTemplateErrorMessage(message,
                        ParamsMap.of("value", formatValue(integer),
                                "max", formatValue(amount))));
            }
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }
    }
}