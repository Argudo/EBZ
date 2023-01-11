package es.uca.iw.ebz.views.component;

import com.vaadin.flow.component.ComponentEvent;

public class UpdateNoticeEvent extends ComponentEvent<CreateNoticeDialog> {
    public UpdateNoticeEvent(CreateNoticeDialog acLog, boolean bDesdeCliente){
        super(acLog, bDesdeCliente);
    }
}
