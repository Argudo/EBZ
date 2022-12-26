package es.uca.iw.ebz.views.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
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
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Cuenta.CuentaService;
import es.uca.iw.ebz.tarjeta.EnumTarjeta;
import es.uca.iw.ebz.tarjeta.Tarjeta;
import es.uca.iw.ebz.tarjeta.TarjetaService;
import es.uca.iw.ebz.tarjeta.TipoTarjeta;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteService;
import es.uca.iw.ebz.views.main.component.TarjetaComponent;
import es.uca.iw.ebz.views.main.layout.AdminLayout;


@PageTitle("Gestión de tarjetas")
@Route(value = "Dashboard/tarjetas", layout = AdminLayout.class)
@RolesAllowed({ "Empleado"})
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
	
	private Dialog dlogNT = new Dialog();
		private RadioButtonGroup<String> rdGroup = new RadioButtonGroup<String>();
		private PasswordField txtPin = new PasswordField("Crear PIN");
		private TextField txtFechaExp = new TextField("Fecha de expiración");
		private ComboBox<String> cmbCuentas = new ComboBox<>("Seleccione la cuenta");
		private TextField txtTitular = new TextField("Nombre del titular");
	
	private Cliente _cliente;
	private List<Cuenta> aCuentas;
    private List<Tarjeta> aTarjeta = new ArrayList<Tarjeta>();
		
	@Autowired 
	private ClienteService _clienteService;
	@Autowired
	private TarjetaService _tarService;
	@Autowired
	private CuentaService _cuentaService;
	
	
	public DashBoardTarjetasView(CuentaService _cuentaService, ClienteService _clienteService, TarjetaService _tarService	) {
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
        /*Dialogo Nueva Tarjeta*/
        dlogNT.setWidth("30vw");
		dlogNT.setHeaderTitle("Solicitar nueva tarjeta");
		
		Button btnGenerar = new Button("Solicitar");
		Button btnCancelar = new Button(new Icon(VaadinIcon.CLOSE));
		btnCancelar.addThemeVariants(ButtonVariant.LUMO_ICON);
		btnCancelar.addThemeVariants(ButtonVariant.LUMO_ERROR);
		btnGenerar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		HorizontalLayout hlOptions = new HorizontalLayout();
		hlOptions.setWidthFull();
		hlOptions.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
		hlOptions.add(btnGenerar);
		
		
		VerticalLayout vlogMain = new VerticalLayout();
		HorizontalLayout hlInfo = new HorizontalLayout();
		hlInfo.setWidthFull();
		hlInfo.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
		txtPin.setMaxLength(4);
		txtPin.setMinLength(4);
		txtPin.setPattern("[0-9]{4}");
		txtFechaExp.setValue(Integer.toString(new Date().getMonth()) + "/" + Integer.toString(new Date().getYear()+4).substring(1,3));
		txtFechaExp.setReadOnly(true);
		hlInfo.add(txtPin, txtFechaExp);

		txtTitular.setReadOnly(true);
		txtTitular.setWidthFull();
		rdGroup.setLabel("Tipo de tarjeta");
		rdGroup.setItems("Débito", "Crédito", "Prepago");
		rdGroup.addValueChangeListener(e -> {			
			vlogMain.removeAll();
			vlogMain.add(rdGroup, txtTitular);
			if(rdGroup.getValue() == "Débito") {
				cmbCuentas.setWidthFull();
				aCuentas = _cuentaService.findByCliente(_cliente);
				List<String> aNumCuentas = new ArrayList();
				aCuentas.forEach(c -> {
					aNumCuentas.add(c.getNumeroCuenta());
				});
				cmbCuentas.setItems(aNumCuentas);
				vlogMain.add(cmbCuentas, hlInfo);
				dlogNT.getFooter().add(hlOptions);
			}
		});
		
		btnCancelar.getElement().addEventListener("click", e -> dlogNT.close());
		btnGenerar.getElement().addEventListener("click", e -> GenerarTarjeta());
		dlogNT.getHeader().add(btnCancelar);
		vlogMain.add(rdGroup, txtTitular);
		dlogNT.add(new Hr(), vlogMain);
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
					hlAviso.add(new Icon(VaadinIcon.WARNING), new Paragraph("No se ha encontrado ningún cliente con los datos introducido"));
				}
			}
		});
	}
	
	private Renderer<Cliente> VerTarjetasRenderer(Grid<Cliente> gridCliente, List<Tarjeta> aTarjetas, Grid<Tarjeta> gridTarjeta) {
	    return LitRenderer.<Cliente> of(
	            "<vaadin-button style=\"padding: 0; margin: 4px\" theme=\"secondary\" @click=\"${handleClick}\"><vaadin-icon icon=\"vaadin:eye\"></vaadin-icon></vaadin-button>")
	            .withFunction("handleClick",
	                    cliente -> {	 
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
	                    	txtTitular.setValue(_cliente.getNombre());
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
	 
		private void GenerarTarjeta() {
			TipoTarjeta tp;
			Cuenta cuenta;
			Optional<Cuenta> optCuenta;
			int iPin;
			Boolean fallo = false;
			//Precondiciones
			if(rdGroup.getValue() == null) { rdGroup.getElement().setAttribute("invalid", ""); rdGroup.setErrorMessage("Debe elegir uno de los tipos de tarjeta disponible"); fallo = true; }
			if(cmbCuentas.getValue() == null) {	cmbCuentas.getElement().setAttribute("invalid", ""); cmbCuentas.setErrorMessage("Debe seleccionar una cuenta"); fallo = true; }
			optCuenta = _cuentaService.findByNumeroCuenta(cmbCuentas.getValue());
			if(optCuenta.isEmpty() || aCuentas.indexOf(optCuenta.get()) != -1) { cmbCuentas.getElement().setAttribute("invalid", ""); cmbCuentas.setErrorMessage("No se encuentra la cuenta seleccionada"); fallo = true; }
			if(txtPin.getValue().length() != 4) { txtPin.getElement().setAttribute("invalid", ""); txtPin.setErrorMessage("El pin debe tener 4 caracteres"); fallo = true; }
			if(!txtPin.getValue().matches("\\d{4}")) { txtPin.getElement().setAttribute("invalid", ""); txtPin.setErrorMessage("El pin debe ser númerico"); fallo = true; }
			if(fallo) return;
			
			cuenta = optCuenta.get();
			tp = new TipoTarjeta(EnumTarjeta.toTipo(rdGroup.getValue()));
			iPin = Integer.parseInt(txtPin.getValue());
			Tarjeta T = new Tarjeta(iPin, tp, cuenta, _cliente);
			
			try {
				_tarService.Save(T); 
			}
			catch(Exception e) {
				Notification notification = Notification.show("Se ha encontrado un error en la solicitud de tu nueva tarjeta");
				notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
				System.out.println(e.getMessage()); 
			}
			
			Notification notification = Notification.show("Tu nueva tarjeta " + T.getNumTarjeta() + " ha sido creada correctamente");
			notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        	aTarjeta.removeAll(aTarjeta);
        	aTarjeta.addAll((Collection<Tarjeta>) _tarService.findByCliente(_cliente));
        	gridTarjeta.setItems(aTarjeta);
        	hGrid.setText("| Tarjetas de " + _cliente.getNombre());
			dlogNT.close();
		}
}
