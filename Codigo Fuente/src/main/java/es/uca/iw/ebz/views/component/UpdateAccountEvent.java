package es.uca.iw.ebz.views.component;

import com.vaadin.flow.component.ComponentEvent;

public class UpdateAccountEvent extends ComponentEvent<CreateAccountDialog> {
    public UpdateAccountEvent(CreateAccountDialog acLog, boolean bDesdeCliente){
        super(acLog, bDesdeCliente);
    }
}
