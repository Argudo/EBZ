package es.uca.iw.ebz.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Cuenta.CuentaService;
import es.uca.iw.ebz.tarjeta.Tarjeta;
import es.uca.iw.ebz.tarjeta.TarjetaService;
import es.uca.iw.ebz.tarjeta.credito.TipoCrediticioRepository;
import es.uca.iw.ebz.tarjeta.prepago.PrepagoService;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteService;
import es.uca.iw.ebz.views.component.NuevaTarjetaDialog;
import es.uca.iw.ebz.views.layout.AdminLayout;


@PageTitle("Gesti??n de tarjetas")
@Route(value = "Dashboard/tarjetas", layout = AdminLayout.class)
@RolesAllowed({ "Empleado" })
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
	Notification notCambios = new Notification("Tiene a??n cambios pendientes, presione 'Guardar'");
	
	
	private Cliente _cliente;
	private List<Cuenta> aCuentas;
    private List<Tarjeta> aTarjeta = new ArrayList<Tarjeta>();
		
	@Autowired 
	private ClienteService _clienteService;
	@Autowired
	private TarjetaService _tarService;
	@Autowired
	private CuentaService _cuentaService;
	@Autowired
	private TipoCrediticioRepository _tipoCredRepo;
	@Autowired
	private PrepagoService _prepagoService;
	
	private NuevaTarjetaDialog dlogNT;
	
	public DashBoardTarjetasView(CuentaService _cuentaService, ClienteService _clienteService, TarjetaService _tarService, TipoCrediticioRepository _tipoCredRepo) {
		dlogNT = new NuevaTarjetaDialog(_cuentaService, _clienteService, _tarService, _tipoCredRepo, _prepagoService);
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
        gridCliente.addColumn(Cliente::getNombre).setWidth("33%");
        gridCliente.addColumn(NuevaTarjetaRenderer(gridCliente, aTarjeta, gridTarjeta)).setTextAlign(ColumnTextAlign.END).setFlexGrow(0);
		gridCliente.addColumn(VerTarjetasRenderer(gridCliente, aTarjeta, gridTarjeta)).setTextAlign(ColumnTextAlign.END).setFlexGrow(0);
        gridCliente.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        gridCliente.setAllRowsVisible(true);
        gridCliente.setItemDetailsRenderer(CrearDetallesClienteRenderer());
        
        List<Cliente> aClientes = new ArrayList<Cliente>();
        gridCliente.setItems(aClientes);
        /*Grid Tarjeta*/
        notCambios.setDuration(0);
    	notCambios.setPosition(Position.TOP_STRETCH);
    	notCambios.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
        gridTarjeta.addColumn(Tarjeta::getStringTipoTarjeta).setHeader("Tipo de tarjeta").setSortable(true).setTextAlign(ColumnTextAlign.CENTER);
        gridTarjeta.addColumn(Tarjeta::getFechaExpiracion).setHeader("Fecha Exp.").setSortable(true).setWidth("100px").setTextAlign(ColumnTextAlign.CENTER);
        gridTarjeta.addColumn(Tarjeta::getNumTarjeta).setHeader("N??mero de tarjeta").setTextAlign(ColumnTextAlign.CENTER);
        gridTarjeta.addColumn(new ComponentRenderer<>(tarjeta -> {
            TextField pinField = new TextField();
            pinField.setValue(tarjeta.getiPin());
            pinField.setWidth("100px");
            pinField.addValueChangeListener(event -> {  
            	if(!notCambios.isOpened()) notCambios.open();
            	tarjeta.setiPin(event.getValue());
            });
            
            return pinField;
        })).setHeader("PIN").setWidth("100px").setTextAlign(ColumnTextAlign.CENTER);
        gridTarjeta.addColumn(new ComponentRenderer<>(tarjeta -> {
        	 HorizontalLayout layout = new HorizontalLayout();
        	    TextField cvcField = new TextField();
        	    cvcField.setWidth("100px");
        	    cvcField.setValue(tarjeta.getCVC());
        	    cvcField.setReadOnly(true);
        	    Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        	    refreshButton.addClickListener(event -> {
        	        // generate a new CVC and set it in the tarjeta object
        	    	if(!notCambios.isOpened()) notCambios.open();
        	        String newCvc = tarjeta.GenerarCVC();
        	        tarjeta.setCVC(newCvc);
        	        cvcField.setValue(newCvc);
        	    });
        	    layout.add(cvcField, refreshButton);
        	    return layout;
        })).setHeader("CVC").setWidth("100px").setTextAlign(ColumnTextAlign.CENTER);
        gridTarjeta.addColumn(new ComponentRenderer<>(tarjeta -> {
            Button saveButton = new Button("Guardar");
            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            saveButton.addClickListener(event -> {
                try {
                	notCambios.close();
					_tarService.Update(tarjeta);
					Notification not = Notification.show("Se realizaron los cambios correctamente");
	    	    	not.setPosition(Position.MIDDLE);
	    	    	not.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                	aTarjeta.removeAll(aTarjeta);
                	aTarjeta.addAll((Collection<Tarjeta>) _tarService.findByCliente(_cliente));
                	gridTarjeta.setItems(aTarjeta);
				} catch (Exception e1) {
					Notification not = Notification.show("Se ha encontrado el siguiente error: " + e1.getMessage());
	    	    	not.setPosition(Position.TOP_STRETCH);
	    	    	not.addThemeVariants(NotificationVariant.LUMO_ERROR);
				}
            });
            return saveButton;
        })).setHeader("Cambios").setTextAlign(ColumnTextAlign.CENTER);
        
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
			String dniCliente = txtDNI.getValue();
			Cliente cliBusqueda = _clienteService.findByDNI(dniCliente);
			estadoBusqueda = cliBusqueda != null;
			if(aClientes.stream().filter(cliente -> cliente != null && dniCliente.equalsIgnoreCase(cliente.getUsuario().getDNI())).count() == 0)
				aClientes.add(0, cliBusqueda);
			if(estadoBusqueda != null) {
				hlAviso.removeAll();
				if(estadoBusqueda) {
					hlAviso.getStyle().set("font-size", "14px");
					hlAviso.getStyle().set("background-color", "hsla(145, 76%, 44%, 0.22)");
					hlAviso.getStyle().set("border-radius", "var(--lumo-border-radius-m)");
					hlAviso.add(new Icon(VaadinIcon.CHECK), new Paragraph("Se ha encontrado el siguiente cliente"));
					aClientes.removeAll(Collections.singletonList(null));
					gridCliente.getDataProvider().refreshAll();

				}
				else{
					hlAviso.getStyle().set("font-size", "14px");
					hlAviso.getStyle().set("background-color", "hsla(3, 100%, 60%, 0.2)");
					hlAviso.getStyle().set("border-radius", "var(--lumo-border-radius-m)");
					hlAviso.add(new Icon(VaadinIcon.WARNING), new Paragraph("No se ha encontrado ning??n cliente con los datos introducido"));
				}
			}
		});
		
		dlogNT.addUpdateListener(e -> {
			aTarjeta.add(e.getTarjeta());
			gridTarjeta.setItems(aTarjeta);
		});
	}
	
	private Renderer<Cliente> VerTarjetasRenderer(Grid<Cliente> gridCliente, List<Tarjeta> aTarjetas, Grid<Tarjeta> gridTarjeta) {
	    return LitRenderer.<Cliente> of(
	            "<vaadin-button style=\"padding: 0; margin: 4px\" theme=\"secondary\" @click=\"${handleClick}\"><vaadin-icon icon=\"vaadin:eye\"></vaadin-icon></vaadin-button>")
	            .withFunction("handleClick",
	                    cliente -> {	 
	                    	_cliente = cliente;
	                    	aTarjetas.removeAll(aTarjetas);
	                    	aTarjetas.addAll((Collection<Tarjeta>) _tarService.findByCliente(cliente));
	                    	gridTarjeta.setItems(aTarjetas);
	                    	hGrid.setText("| Tarjetas de " + cliente.getNombre());
	                    });
	}
	
	private Renderer<Cliente> NuevaTarjetaRenderer(Grid<Cliente> gridCliente, List<Tarjeta> aTarjetas, Grid<Tarjeta> gridTarjeta) {
	    return LitRenderer.<Cliente> of(
"<vaadin-button style=\"padding: 0; margin: 4px\" theme=\"secondary\" @click=\"${handleClick}\"><vaadin-icon icon=\"vaadin:plus\"></vaadin-icon></vaadin-button>")
	            .withFunction("handleClick",
	                    cliente -> {	 
	                    	_cliente = cliente;
	                    	dlogNT.setTitular(_cliente);
	                    	dlogNT.open();
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
