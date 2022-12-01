package es.uca.iw.ebz.tarjeta;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class TarjetaService {

	private Tarjeta _tarjeta;
	
	public TarjetaService(Tarjeta tarjeta) {
		_tarjeta = tarjeta;
	}
	
	public Component GenerarTarjeta(Tarjeta t) {
		VerticalLayout vlTarjeta = new VerticalLayout();
        vlTarjeta.add(new H4("EBZ"),
        			   new H5(t.getsNumTarjeta()),
        			   new H6(t.getFechaExpiracion().toString()));
        vlTarjeta.setClassName("tarjeta-mid");
        vlTarjeta.setWidth("300px");
        vlTarjeta.setHeight("200px");
        vlTarjeta.setPadding(false);
        vlTarjeta.setAlignItems(FlexComponent.Alignment.CENTER);
        vlTarjeta.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        return vlTarjeta;
	}
}
