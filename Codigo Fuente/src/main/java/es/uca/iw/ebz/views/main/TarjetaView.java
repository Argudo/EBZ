package es.uca.iw.ebz.views.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.Scroller.ScrollDirection;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import es.uca.iw.ebz.tarjeta.EnumTarjeta;
import es.uca.iw.ebz.tarjeta.Tarjeta;
import es.uca.iw.ebz.tarjeta.TipoTarjeta;
import es.uca.iw.ebz.views.main.component.TarjetaComponent;
import es.uca.iw.ebz.views.main.layout.MainLayout;

@PageTitle("Tarjetas")
@Route(value = "tarjetas", layout = MainLayout.class)
public class TarjetaView extends VerticalLayout{
	//@Autowired
	//private TarjetaService _tarService;
	
	static TarjetaComponent tcSelected = null;
	static Tarjeta tarSelected = null;
	
	HorizontalLayout hlInformacion = new HorizontalLayout();
	
	VerticalLayout vlDetalleTarjetas = new VerticalLayout();	
		private H3 hNumCuenta = new H3("Número de cuenta");
		private H3 hSaldo = new H3("Saldo");
		private H3 hPin = new H3("PIN");
		private H3 hFechaCaducidad = new H3("Fecha de expiración");
		private Paragraph pNumCuenta = new Paragraph();
		private Paragraph pSaldo = new Paragraph();
		private Paragraph pPin = new Paragraph();
		private PasswordField textPin = new PasswordField();
		private Paragraph pFechaCaducidad = new Paragraph();
			
	VerticalLayout vlTransacciones = new VerticalLayout();
	
	
	TarjetaView(){
		setWidthFull();
		setPadding(true);
		setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER); 
		setAlignItems(FlexComponent.Alignment.CENTER);
		TipoTarjeta tpTarjeta = new TipoTarjeta(EnumTarjeta.Debito);
		Tarjeta tarjeta = new Tarjeta(5000, tpTarjeta);
		Tarjeta tarjeta2 = new Tarjeta(7000, tpTarjeta);
		List<Tarjeta> aTarjetas = Arrays.asList(tarjeta2, tarjeta, tarjeta, tarjeta2, tarjeta, tarjeta2, tarjeta);
		List<TarjetaComponent> aTarjetasComponent = new ArrayList<TarjetaComponent>();
		textPin.setReadOnly(true);
		textPin.setClassName("padding40");
		
		H1 hTarjeta = new H1("| Tarjetas");
		hTarjeta.setClassName("title");
		H1 hDetalleTarjeta = new H1("| Detalles");
		hDetalleTarjeta.setClassName("title");
		H1 hTransacciones = new H1("| Transacciones");
		hTransacciones.setClassName("title");
		
		
		Scroller scrllTarjetas = new Scroller();
		scrllTarjetas.setScrollDirection(ScrollDirection.HORIZONTAL);
		scrllTarjetas.setWidth("67vw");
		scrllTarjetas.setHeight("100%");
		
		VerticalLayout vlTarjetas = new VerticalLayout();
		vlTarjetas.setWidth("70vw");
		vlTarjetas.setPadding(true);
		vlTarjetas.setMargin(true);
		vlTarjetas.setClassName("box");
		
		vlDetalleTarjetas.setWidth("50%");
		vlDetalleTarjetas.setPadding(true);
		vlDetalleTarjetas.setMargin(true);
		vlDetalleTarjetas.setSpacing(false);
		vlDetalleTarjetas.setClassName("detalleTarjeta");
		vlDetalleTarjetas.add(hDetalleTarjeta,
							  new Hr(),
							  hNumCuenta,
							  pNumCuenta,
							  hSaldo,
							  pSaldo,
							  hPin,
							  textPin,
							  hFechaCaducidad,
							  pFechaCaducidad);
		
		vlTransacciones.setWidth("50%");
		vlTransacciones.add(hTransacciones, new Hr());
		vlTransacciones.setPadding(true);
		vlTransacciones.setMargin(true);
		vlTransacciones.setSpacing(false);
		vlTransacciones.setClassName("box");
		
		hlInformacion.add(vlDetalleTarjetas,
						  vlTransacciones);
		hlInformacion.setWidth("71vw");
		
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
		CargarDetalles();
		for(TarjetaComponent tc: aTarjetasComponent) {
			hlTarjetas.add(tc);
		}
		
		hlTarjetas.getChildren().forEach(child -> {
			child.getElement().addEventListener("click", e -> {
				if(tcSelected == null) {
					tcSelected = TarjetaComponent.class.cast(child);
					tcSelected.seleccionarTarjeta();
					tarSelected = tcSelected.getTarjeta();
					CargarDetalles();
				}
				
				if(tcSelected != TarjetaComponent.class.cast(child)) {
					tcSelected.deseleccionarTarjeta();
					tcSelected = TarjetaComponent.class.cast(child);
					tcSelected.seleccionarTarjeta();
					tarSelected = tcSelected.getTarjeta();
					CargarDetalles();
				}
				else if((tcSelected == TarjetaComponent.class.cast(child) && tcSelected.getSelected())) {
					tcSelected.deseleccionarTarjeta();
					CargarDetalles();
				}
				else {
					tcSelected = TarjetaComponent.class.cast(child);
					tcSelected.seleccionarTarjeta();
					tarSelected = tcSelected.getTarjeta();
					CargarDetalles();
				}
			});
		});
		
		scrllTarjetas.setContent(hlTarjetas);		
		vlTarjetas.add(hTarjeta, new Hr(), scrllTarjetas);
		add(vlTarjetas, hlInformacion);
	}
	
	private void CargarDetalles() {
		if(tcSelected.getSelected()) {
			pNumCuenta.setText(tarSelected.getNumTarjeta());
			pFechaCaducidad.setText(tarSelected.getFechaExpiracion().toString());
			pPin.setText(String.valueOf(tarSelected.getiPin()));
			textPin.setValue(String.valueOf(tarSelected.getiPin()));
			vlDetalleTarjetas.getChildren().forEach(child -> {
				if(child.getClass() != H1.class || child.getClass() != Hr.class) {
					child.setVisible(true);
				}
			});
			if(tarSelected.getTipoTarjeta() == EnumTarjeta.Prepago) {
				pSaldo.setText("1000€");
			}
			else {
				pSaldo.setText("Ver saldo de cuenta asociada");
			}			
		}
		else {
			vlDetalleTarjetas.getChildren().forEach(child -> {
				if(child.getClass() != H1.class && child.getClass() != Hr.class) {
					child.setVisible(false);
				}
			});
		}
	}

    public static class DashBoardCuentaView {
    }
}
