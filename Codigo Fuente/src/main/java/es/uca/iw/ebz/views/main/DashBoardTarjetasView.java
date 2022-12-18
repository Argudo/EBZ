package es.uca.iw.ebz.views.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import es.uca.iw.ebz.tarjeta.Tarjeta;
import es.uca.iw.ebz.tarjeta.TarjetaService;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteService;
import es.uca.iw.ebz.views.main.layout.AdminLayout;

@PermitAll
@PageTitle("Gestión de tarjetas")
@Route(value = "Dashboard/tarjetas", layout = AdminLayout.class)
@RolesAllowed("Empleado")
public class DashBoardTarjetasView extends HorizontalLayout{
	private VerticalLayout vlGrid = new VerticalLayout();
	private VerticalLayout vlInfo = new VerticalLayout();
	private VerticalLayout vlSeparator = new VerticalLayout();
	
	private H1 hGrid = new H1("| Tarjetas");
	
	private H1 hInfo = new H1("Buscador");
	private HorizontalLayout hlBuscador = new HorizontalLayout();
	private TextField txtDNI = new TextField();
	private Button btnBuscar = new Button();
	private Paragraph pDNI = new Paragraph("DNI");
	private HorizontalLayout hlAviso = new HorizontalLayout();
	private Boolean estadoBusqueda = false;
	private Grid<Cliente> gridCliente = new Grid<>(Cliente.class, false);
	private Grid<Tarjeta> gridTarjeta = new Grid<>(Tarjeta.class, false);
	
	@Autowired 
	private ClienteService _clienteService;
	@Autowired
	private TarjetaService _tarService;
	
	
	public DashBoardTarjetasView() {
		hGrid.setClassName("title");
		hInfo.setClassName("title");
		
		vlGrid.setWidth("70%");
		vlSeparator.setWidth("2px");
		vlInfo.setWidth("30%");
		
		vlSeparator.getStyle().set("background-color", "var(--lumo-contrast-10pct)");
		vlSeparator.getStyle().set("padding", "0");
		
		vlGrid.add(hGrid,
				   new Hr(),
				   gridTarjeta);
		
		vlInfo.setAlignItems(FlexComponent.Alignment.CENTER);
		hlBuscador.setAlignItems(FlexComponent.Alignment.END);
		pDNI.getStyle().set("fontWeight", "600");
		btnBuscar.getElement().appendChild(new Icon("lumo", "search").getElement());
		hlAviso.setAlignItems(FlexComponent.Alignment.CENTER);
		hlAviso.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
		hlAviso.setWidth("100%");
		/*Grid cliente*/
        gridCliente.addColumn(Cliente::getNombre);
        gridCliente.addColumn(Cliente::getTipoCliente);
        List<Tarjeta> aTarjeta = new ArrayList<Tarjeta>();
		gridCliente.addColumn(createToggleDetailsRenderer(gridCliente, aTarjeta, gridTarjeta));
        gridCliente.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        gridCliente.setAllRowsVisible(true);
        gridCliente.setItemDetailsRenderer(CrearDetallesClienteRenderer());
        
        List<Cliente> aClientes = new ArrayList<Cliente>();
        gridCliente.setItems(aClientes);
        /*Grid Tarjeta*/
        gridTarjeta.addColumn(Tarjeta::getTipoTarjeta).setHeader("Tipo de tarjeta");
        gridTarjeta.addColumn(Tarjeta::getNumTarjeta).setHeader("Número de tarjeta");
        gridTarjeta.addColumn(Tarjeta::getFechaExpiracion).setHeader("Fecha de expiración");
        
		hlBuscador.add(pDNI,
					   txtDNI,
				       btnBuscar);
		vlInfo.add(hInfo,
				   new Hr(),
				   hlBuscador,
				   hlAviso,
				   gridCliente);
		
		setHeight("100%");
		add(vlGrid,
			vlSeparator,
			vlInfo);
		
		btnBuscar.addClickListener(e -> {
			gridCliente.setItems(aClientes);
			String dniCliente = txtDNI.getValue();
			System.out.println("Cliente: " + _clienteService.findByDNI(dniCliente) + " - Nombre: " + _clienteService.findByDNI(dniCliente).getNombre());
			Cliente cliBusqueda = _clienteService.findByDNI(dniCliente);
			estadoBusqueda = cliBusqueda == null;
			if(!aClientes.contains(cliBusqueda))
				aClientes.add(cliBusqueda);
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
	
	private Renderer<Cliente> createToggleDetailsRenderer(Grid<Cliente> gridCliente, List<Tarjeta> aTarjetas, Grid<Tarjeta> gridTarjeta) {
	    return LitRenderer.<Cliente> of(
	            "<vaadin-button theme=\"tertiary\" @click=\"${handleClick}\">Ver tarjetas</vaadin-button>")
	            .withFunction("handleClick",
	                    cliente -> {	 
	                    	aTarjetas.removeAll(aTarjetas);
	                    	aTarjetas.addAll((Collection<Tarjeta>) _tarService.findByCliente(cliente));
	                    	gridTarjeta.setItems(aTarjetas);
	                    });
	}
	
	private static ComponentRenderer<ClienteDetallesFormLayout, Cliente> CrearDetallesClienteRenderer() {
        return new ComponentRenderer<>(ClienteDetallesFormLayout::new,
                ClienteDetallesFormLayout::setCliente);
    }
	
	 private static class ClienteDetallesFormLayout extends FormLayout {
	        //private final TextField txtId = new TextField("ID");
	        private final TextField txtNombre = new TextField("Nombre");
	        private final TextField txtFechaNacimiento = new TextField("Fecha Nacimiento");
	        private final TextField txtTipoUsuario = new TextField("Tipo de usuario");

	        public ClienteDetallesFormLayout() {
	            Stream.of(txtNombre, txtFechaNacimiento, txtTipoUsuario).forEach(field -> {
	                        field.setReadOnly(true);
	                        add(field);
	                    });
	            
	            setResponsiveSteps(new ResponsiveStep("0", 3));
	            setColspan(txtNombre, 3);
	            setColspan(txtTipoUsuario, 3);
	            setColspan(txtFechaNacimiento, 3);
	        }

	        public void setCliente(Cliente cliente) {
	            //txtId.setValue(cliente.getId().toString());
	            txtNombre.setValue(cliente.getNombre());
	            txtFechaNacimiento.setValue(cliente.getFechaNacimiento().toString());
	            txtTipoUsuario.setValue(cliente.getTipoCliente().name());
	        }
	    }
}