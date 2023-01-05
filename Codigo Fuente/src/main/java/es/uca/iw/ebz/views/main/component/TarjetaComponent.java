package es.uca.iw.ebz.views.main.component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import es.uca.iw.ebz.tarjeta.Tarjeta;

@Tag("TarjetaComponent")
public class TarjetaComponent extends Component {
	private Tarjeta _tarjeta;
	VerticalLayout _vlTarjeta;
	boolean _selected = false;
	
	public Tarjeta getTarjeta() { return _tarjeta; }
	public boolean getSelected() { return _selected; }
	
	public void seleccionarTarjeta() {
		//_vlTarjeta.getStyle().set("opacity", "1");
		_vlTarjeta.getStyle().set("animation", "entryOp 1s 1 forwards");
		_selected = true;
	}
	
	public void deseleccionarTarjeta() {
		_vlTarjeta.getStyle().set("animation", "outOp 1s 1 forwards");
		//_vlTarjeta.getStyle().set("opacity", "0.5");
		_selected = false;
	}
	
	public TarjetaComponent() {
		
	}
	
	public TarjetaComponent(Boolean esNuevaTarjeta) {
    	_vlTarjeta = new VerticalLayout();
    	DateFormat dateFormat = new SimpleDateFormat("mm/yy");  
        String fechaExpiracion = "00/00";  
        H5 hNueva = new H5("+");
        _vlTarjeta.getStyle().set("display", "flex").set("justify-content", "center");
        hNueva.setClassName("nueva-tarjeta-mid");
        _vlTarjeta.add(new H4("EBZ"),
        			   hNueva,
        			   new H6(fechaExpiracion)
        			 );
        _vlTarjeta.setClassName("tarjeta-mid");
        _vlTarjeta.setWidth("300px");
        _vlTarjeta.setHeight("200px");
        _vlTarjeta.setPadding(false);
        _vlTarjeta.getStyle().set("display", "-webkit-box");
        _vlTarjeta.setAlignItems(FlexComponent.Alignment.CENTER);
        _vlTarjeta.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        getElement().appendChild(_vlTarjeta.getElement());
	}
    
    public TarjetaComponent(Tarjeta tarjeta){
    	_tarjeta = tarjeta;
    	_vlTarjeta = new VerticalLayout();
    	DateFormat dateFormat = new SimpleDateFormat("mm/yy");  
        String fechaExpiracion = _tarjeta.getFechaExpiracion();
        _vlTarjeta.add(new H4("EBZ"),
        			   new H5(_tarjeta.getNumTarjeta()),
        			   new H6(fechaExpiracion)
        			 );
        _vlTarjeta.setClassName("tarjeta-mid");
        _vlTarjeta.setWidth("300px");
        _vlTarjeta.setHeight("200px");
        _vlTarjeta.setPadding(false);
        _vlTarjeta.getStyle().set("display", "-webkit-box");
        _vlTarjeta.setAlignItems(FlexComponent.Alignment.CENTER);
        _vlTarjeta.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        getElement().appendChild(_vlTarjeta.getElement());
    }
}
