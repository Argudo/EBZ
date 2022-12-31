package es.uca.iw.ebz.views.main.component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Hr;
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
import com.vaadin.flow.shared.Registration;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Cuenta.CuentaService;
import es.uca.iw.ebz.tarjeta.EnumTarjeta;
import es.uca.iw.ebz.tarjeta.Tarjeta;
import es.uca.iw.ebz.tarjeta.TarjetaService;
import es.uca.iw.ebz.tarjeta.TipoTarjeta;
import es.uca.iw.ebz.tarjeta.credito.TipoCrediticioRepository;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteService;

public class NuevaTarjetaDialog extends Dialog {
	private RadioButtonGroup<String> rdGroup = new RadioButtonGroup<String>();
	private PasswordField txtPin = new PasswordField("Crear PIN");
	private TextField txtFechaExp = new TextField("Fecha de expiración");
	private ComboBox<String> cmbCuentas = new ComboBox<>("Seleccione la cuenta");
	private ComboBox<String> cmbTipoCredito = new ComboBox<>("Seleccione el tipo de tarjeta crediticia");
	private TextField txtTitular = new TextField("Nombre del titular");
	
	private List<Cuenta> aCuentas;
	private Cliente _cliente;
	
	private CuentaService _cuentaService;
	private TipoCrediticioRepository _tipoCredRepo;
	private ClienteService _clienteService;
	private TarjetaService _tarService;
	
	public NuevaTarjetaDialog(CuentaService cuentaService, ClienteService clienteService, TarjetaService tarService, TipoCrediticioRepository tipoCredRepo) {
		
		_cuentaService = cuentaService;
		_tipoCredRepo = tipoCredRepo;
		_clienteService = clienteService;
		_tarService = tarService;

		setWidth("30vw");
		setHeaderTitle("Solicitar nueva tarjeta");
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
				vlogMain.add(cmbCuentas);
			}
			else if(rdGroup.getValue() == "Crédito") {
				cmbCuentas.setWidthFull();
				aCuentas = _cuentaService.findByCliente(_cliente);
				
				List<String> aNumCuentas = new ArrayList();
				aCuentas.forEach(c -> {
					aNumCuentas.add(c.getNumeroCuenta());
				});
				cmbCuentas.setItems(aNumCuentas);
				
				cmbTipoCredito.setWidthFull();
				List<String> aTipoCredito = new ArrayList<String>();
				_tipoCredRepo.findAll().forEach(tc -> {
					aTipoCredito.add(tc.getNombre());
				});;
				cmbTipoCredito.setItems(aTipoCredito);
				
				vlogMain.add(cmbTipoCredito, cmbCuentas);
			}
			vlogMain.add(hlInfo);
			getFooter().add(hlOptions);
		});
		
		btnCancelar.getElement().addEventListener("click", e -> close());
		btnGenerar.getElement().addEventListener("click", e -> GenerarTarjeta());
		getHeader().add(btnCancelar);
		vlogMain.add(rdGroup, txtTitular);
		add(new Hr(), vlogMain);
		
	}
	
	private Boolean GenerarTarjeta() {
		TipoTarjeta tp;
		Cuenta cuenta;
		Optional<Cuenta> optCuenta = java.util.Optional.empty();
		String sPin;
		Boolean fallo = false;

		//Precondiciones
		if(rdGroup.getValue() == null) { rdGroup.getElement().setAttribute("invalid", ""); rdGroup.setErrorMessage("Debe elegir uno de los tipos de tarjeta disponible"); fallo = true; }
		tp = new TipoTarjeta(EnumTarjeta.toTipo(rdGroup.getValue()));
		
		if(tp.getTipo() != EnumTarjeta.Prepago) {
			if(cmbCuentas.getValue() == null && tp.getTipo() != EnumTarjeta.Prepago) {	cmbCuentas.getElement().setAttribute("invalid", ""); cmbCuentas.setErrorMessage("Debe seleccionar una cuenta"); fallo = true; }
			optCuenta = _cuentaService.findByNumeroCuenta(cmbCuentas.getValue());
			if(optCuenta.isEmpty() || aCuentas.indexOf(optCuenta.get()) != -1) { cmbCuentas.getElement().setAttribute("invalid", ""); cmbCuentas.setErrorMessage("No se encuentra la cuenta seleccionada"); fallo = true; }
			if(EnumTarjeta.toTipo(rdGroup.getValue()) == EnumTarjeta.Debito && !_tarService.findByCuenta(optCuenta.get()).isEmpty()) { cmbCuentas.getElement().setAttribute("invalid", ""); cmbCuentas.setErrorMessage("Ya existe una tarjeta de débito para la cuenta seleccionada"); fallo = true; } 			
		}
		
		if(txtPin.getValue().length() != 4) { txtPin.getElement().setAttribute("invalid", ""); txtPin.setErrorMessage("El pin debe tener 4 caracteres"); fallo = true; }
		if(!txtPin.getValue().matches("\\d{4}")) { txtPin.getElement().setAttribute("invalid", ""); txtPin.setErrorMessage("El pin debe ser númerico"); fallo = true; }
		if(fallo) return false;
		
		sPin = txtPin.getValue();
		Tarjeta T;
		if(tp.getTipo() == EnumTarjeta.Prepago)
			T = new Tarjeta(sPin, _cliente);
		else {
			cuenta = optCuenta.get();
			T = new Tarjeta(sPin, tp, cuenta, _cliente);
		}
		
		System.out.println("Nueva tarjeta: " + T.getNumTarjeta());
		
		try {
			_tarService.Save(T); 
			fireEvent(new UpdateEvent(this, false, T));
		}
		catch(Exception e) {
			Notification notification = Notification.show("Se ha encontrado un error en la solicitud de tu nueva tarjeta");
			notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
			System.out.println(e.getMessage()); 
			return false;
		}
		
		Notification notification = Notification.show("Tu nueva tarjeta " + T.getNumTarjeta() + " ha sido creada correctamente");
		notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
		
		close();
		return true;
	}
	
	public void setTitular(Cliente titular) {_cliente = titular; txtTitular.setValue(titular.getNombre()); }

	public Registration addUpdateListener(ComponentEventListener<UpdateEvent> listener) {
	        return addListener(UpdateEvent.class, listener);
	    }
}
