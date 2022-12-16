package es.uca.iw.ebz.views.main;

import java.math.BigDecimal;

import com.flowingcode.vaadin.addons.simpletimer.SimpleTimer;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import es.uca.iw.ebz.views.main.layout.AdminLayout;

@PageTitle("Gestión de tarjetas")
@Route(value = "Dashboard/tarjetas", layout = AdminLayout.class)
public class DashBoardTarjetasView extends HorizontalLayout{
	private VerticalLayout vlGrid = new VerticalLayout();
	private VerticalLayout vlInfo = new VerticalLayout();
	private VerticalLayout vlSeparator = new VerticalLayout();
	
	private H2 hGrid = new H2("Tarjetas");
	
	private H2 hInfo = new H2("Buscar usuario");
	private HorizontalLayout hlBuscador = new HorizontalLayout();
	private TextField txtDNI = new TextField();
	private Button btnBuscar = new Button();
	private Paragraph pDNI = new Paragraph("DNI");
	private HorizontalLayout hlAviso = new HorizontalLayout();
	private Boolean estadoBusqueda = true;
	
	
	public DashBoardTarjetasView() {
		vlGrid.setWidth("70%");
		vlSeparator.setWidth("2px");
		vlInfo.setWidth("30%");
		
		vlSeparator.getStyle().set("background-color", "var(--lumo-contrast-10pct)");
		vlSeparator.getStyle().set("padding", "0");
		
		vlGrid.add(hGrid,
				   new Hr());
		
		vlInfo.setAlignItems(FlexComponent.Alignment.CENTER);
		hlBuscador.setAlignItems(FlexComponent.Alignment.END);
		pDNI.getStyle().set("fontWeight", "600");
		btnBuscar.getElement().appendChild(new Icon("lumo", "search").getElement());
		hlAviso.setAlignItems(FlexComponent.Alignment.CENTER);
		hlAviso.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
		hlAviso.setWidth("100%");
		hlBuscador.add(pDNI,
					   txtDNI,
				       btnBuscar);
		vlInfo.add(hInfo,
				   new Hr(),
				   hlBuscador,
				   hlAviso);
		
		setHeight("100%");
		add(vlGrid,
			vlSeparator,
			vlInfo);
		
		btnBuscar.addClickListener(e -> {
			estadoBusqueda = !estadoBusqueda;
			if(estadoBusqueda != null) {
				hlAviso.removeAll();
				if(estadoBusqueda) {
					hlAviso.getStyle().set("font-size", "14px");
					hlAviso.getStyle().set("background-color", "hsla(145, 76%, 44%, 0.22)");
					hlAviso.getStyle().set("border-radius", "var(--lumo-border-radius-m)");
					hlAviso.add(new Icon(VaadinIcon.CHECK), new Paragraph("Se ha encontrado el siguiente cliente"));
				}
				else{
					hlAviso.getStyle().set("font-size", "14px");
					hlAviso.getStyle().set("background-color", "hsla(3, 100%, 60%, 0.2)");
					hlAviso.getStyle().set("border-radius", "var(--lumo-border-radius-m)");
					hlAviso.add(new Icon(VaadinIcon.WARNING), new Paragraph("No se ha encontrado ningún cliente con los datos introducido"));
				}
			}
		});
	}
	
}
