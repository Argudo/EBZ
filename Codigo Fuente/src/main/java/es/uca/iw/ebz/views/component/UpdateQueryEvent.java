package es.uca.iw.ebz.views.component;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.EventData;

public class UpdateQueryEvent extends ComponentEvent<AdminConsultaDialog> {

    public UpdateQueryEvent(AdminConsultaDialog acLog, boolean bDesdeCliente){
        super(acLog, bDesdeCliente);
    }

}
