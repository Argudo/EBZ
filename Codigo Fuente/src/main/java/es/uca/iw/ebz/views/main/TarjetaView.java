package es.uca.iw.ebz.views.main;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.orderedlayout.Scroller.ScrollDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import es.uca.iw.ebz.Cuenta.CuentaService;
import es.uca.iw.ebz.tarjeta.*;
import es.uca.iw.ebz.views.main.component.TarjetaComponent;

@PageTitle("Tarjetas")
@Route(value = "tarjetas", layout = MainLayout.class)
public class TarjetaView extends VerticalLayout{
	//@Autowired
	//private TarjetaService _tarService;
	
	static TarjetaComponent tcSelected = null;
	static Tarjeta tarSelected = null;
	
	TarjetaView(){
		setWidthFull();
		setPadding(true);
		setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER); 
		setAlignItems(FlexComponent.Alignment.CENTER);
		TipoTarjeta tpTarjeta = new TipoTarjeta(EnumTarjeta.Debito);
		Tarjeta tarjeta = new Tarjeta(0, tpTarjeta);
		List<Tarjeta> aTarjetas = Arrays.asList(tarjeta, tarjeta, tarjeta, tarjeta, tarjeta, tarjeta, tarjeta);
		List<TarjetaComponent> aTarjetasComponent = new ArrayList<TarjetaComponent>();
		
		H1 hTarjeta = new H1("| Tarjetas");
		hTarjeta.setClassName("title");
		H1 hDetalleTarjeta = new H1("| Detalles");
		hDetalleTarjeta.setClassName("title");
		
		
		Scroller scrllTarjetas = new Scroller();
		scrllTarjetas.setScrollDirection(ScrollDirection.HORIZONTAL);
		scrllTarjetas.setWidth("75vw");
		scrllTarjetas.setHeight("100%");
		
		VerticalLayout vlTarjetas = new VerticalLayout();
		vlTarjetas.setWidth("80vw");
		vlTarjetas.setPadding(true);
		vlTarjetas.setMargin(true);
		vlTarjetas.setClassName("box");
		
		VerticalLayout vlDetalleTarjetas = new VerticalLayout();
		vlDetalleTarjetas.setWidth("80vw");
		vlDetalleTarjetas.setPadding(true);
		vlDetalleTarjetas.setMargin(true);
		vlDetalleTarjetas.setClassName("box");
		
		HorizontalLayout hlTarjetas = new HorizontalLayout();
		hlTarjetas.setWidth("100%");
		hlTarjetas.setHeight("100%");
		hlTarjetas.setPadding(true);
		hlTarjetas.setMargin(true);
		
		for(Tarjeta t: aTarjetas) {
			aTarjetasComponent.add(new TarjetaComponent(t));
		}
		tcSelected = aTarjetasComponent.get(0);
		tcSelected.seleccionarTarjeta();
		tarSelected = tcSelected.getTarjeta();
		for(TarjetaComponent tc: aTarjetasComponent) {
			hlTarjetas.add(tc);
		}
		
		hlTarjetas.getChildren().forEach(child -> {
			child.getElement().addEventListener("click", e -> {
				if(tcSelected == null) {
					tcSelected = TarjetaComponent.class.cast(child);
					tcSelected.seleccionarTarjeta();
					tarSelected = tcSelected.getTarjeta();
				}
				
				if(tcSelected != TarjetaComponent.class.cast(child)) {
					tcSelected.deseleccionarTarjeta();
					tcSelected = TarjetaComponent.class.cast(child);
					tcSelected.seleccionarTarjeta();
					tarSelected = tcSelected.getTarjeta();
				}
				else if((tcSelected == TarjetaComponent.class.cast(child) && tcSelected.getSelected())) {
					tcSelected.deseleccionarTarjeta();
				}
				else {
					tcSelected = TarjetaComponent.class.cast(child);
					tcSelected.seleccionarTarjeta();
					tarSelected = tcSelected.getTarjeta();
				}
			});
		});
		
		scrllTarjetas.setContent(hlTarjetas);		
		vlTarjetas.add(hTarjeta, scrllTarjetas);
		vlDetalleTarjetas.add(hDetalleTarjeta);
		add(vlTarjetas, vlDetalleTarjetas);
	}
}
