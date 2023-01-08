package es.uca.iw.ebz.views.component;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.EventData;

import es.uca.iw.ebz.tarjeta.Tarjeta;

public class UpdateEvent extends ComponentEvent<NuevaTarjetaDialog>  {
	private Tarjeta _tarjeta;

	public UpdateEvent(NuevaTarjetaDialog dlogNT, boolean bDesdeCliente, @EventData("event.Tarjeta") Tarjeta tarjeta) {
		super(dlogNT, bDesdeCliente);
		_tarjeta = tarjeta;
	}
	
	public Tarjeta getTarjeta() {
		return _tarjeta;
	}

}
