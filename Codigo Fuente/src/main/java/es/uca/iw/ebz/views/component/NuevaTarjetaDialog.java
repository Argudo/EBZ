package es.uca.iw.ebz.views.component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.vaadin.flow.component.ComponentEventListener;
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
import es.uca.iw.ebz.tarjeta.prepago.Prepago;
import es.uca.iw.ebz.tarjeta.prepago.PrepagoService;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteService;

public class NuevaTarjetaDialog extends Dialog {
	private RadioButtonGroup<String> rdGroup = new RadioButtonGroup<String>();
	private PasswordField txtPin = new PasswordField(getTranslation("card.newCard.createPin"));
	private TextField txtFechaExp = new TextField(getTranslation("card.dateExpiration"));
	private ComboBox<String> cmbCuentas = new ComboBox<>(getTranslation("card.selectAccount"));
	private ComboBox<String> cmbTipoCredito = new ComboBox<>(getTranslation("card.selectType"));
	private TextField txtTitular = new TextField(getTranslation("card.titular"));
	
	private List<Cuenta> aCuentas;
	private Cliente _cliente;
	
	private CuentaService _cuentaService;
	private TipoCrediticioRepository _tipoCredRepo;
	private ClienteService _clienteService;
	private TarjetaService _tarService;
	private PrepagoService _prepagoService;
	
	public NuevaTarjetaDialog(CuentaService cuentaService, ClienteService clienteService, TarjetaService tarService, TipoCrediticioRepository tipoCredRepo, PrepagoService prepagoService) {
		
		_cuentaService = cuentaService;
		_tipoCredRepo = tipoCredRepo;
		_clienteService = clienteService;
		_tarService = tarService;
		_prepagoService = prepagoService;

		setWidth("30vw");
		setHeaderTitle(getTranslation("card.request"));
		Button btnGenerar = new Button(getTranslation("card.requests"));
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
		txtFechaExp.setValue(String.valueOf(new Date().getMonth()+1) + "/" + Integer.toString(new Date().getYear()+4).substring(1,3));
		txtFechaExp.setReadOnly(true);
		hlInfo.add(txtPin, txtFechaExp);
	
		txtTitular.setReadOnly(true);
		txtTitular.setWidthFull();
		rdGroup.setLabel(getTranslation("card.type"));
		rdGroup.setItems(getTranslation("card.debit"), getTranslation("card.credit"), getTranslation("card.prepaid"));
		rdGroup.addValueChangeListener(e -> {			
			vlogMain.removeAll();
			vlogMain.add(rdGroup, txtTitular);
			if(rdGroup.getValue() == getTranslation("card.debit")) {
				cmbCuentas.setWidthFull();
				aCuentas = _cuentaService.findByCliente(_cliente);
				List<String> aNumCuentas = new ArrayList();
				aCuentas.forEach(c -> {
					aNumCuentas.add(c.getNumeroCuenta());
				});
				cmbCuentas.setItems(aNumCuentas);
				vlogMain.add(cmbCuentas);
			}
			else if(rdGroup.getValue() == getTranslation("card.credit")) {
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
		if(rdGroup.getValue() == null) { rdGroup.getElement().setAttribute("invalid", ""); rdGroup.setErrorMessage(getTranslation("card.errorType")); fallo = true; }
		tp = new TipoTarjeta(EnumTarjeta.toTipo(rdGroup.getValue()));
		
		if(tp.getTipo() != EnumTarjeta.Prepago) {
			if(cmbCuentas.getValue() == null && tp.getTipo() != EnumTarjeta.Prepago) {	cmbCuentas.getElement().setAttribute("invalid", ""); cmbCuentas.setErrorMessage(getTranslation("card.errorAccount")); fallo = true; }
			optCuenta = _cuentaService.findByNumeroCuenta(cmbCuentas.getValue());
			if(optCuenta.isEmpty() || aCuentas.indexOf(optCuenta.get()) != -1) { cmbCuentas.getElement().setAttribute("invalid", ""); cmbCuentas.setErrorMessage(getTranslation("card.errorFindAccount")); fallo = true; }
			if(EnumTarjeta.toTipo(rdGroup.getValue()) == EnumTarjeta.Debito && !_tarService.findByCuenta(optCuenta.get()).isEmpty()) { cmbCuentas.getElement().setAttribute("invalid", ""); cmbCuentas.setErrorMessage(getTranslation("card.errorExist")); fallo = true; }
		}
		
		if(txtPin.getValue().length() != 4) { txtPin.getElement().setAttribute("invalid", ""); txtPin.setErrorMessage(getTranslation("card.errorPin")); fallo = true; }
		if(!txtPin.getValue().matches("\\d{4}")) { txtPin.getElement().setAttribute("invalid", ""); txtPin.setErrorMessage(getTranslation("card.errorPinNumeric")); fallo = true; }
		if(fallo) return false;
		
		sPin = txtPin.getValue();
		Tarjeta T;
		Prepago P;
		if(tp.getTipo() == EnumTarjeta.Prepago)	 T = new Tarjeta(sPin, _cliente);
		else {
			cuenta = optCuenta.get();
			T = new Tarjeta(sPin, tp, cuenta, _cliente);
		}
		
		try {
			_tarService.Save(T); 
			if(tp.getTipo() == EnumTarjeta.Prepago) {
				P = new Prepago(T);				
				_prepagoService.Save(P);
			} 
			fireEvent(new UpdateEvent(this, false, T));
		}
		catch(Exception e) {
			Notification notification = Notification.show(getTranslation("card.errorNewCard"));
			notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
			System.out.println(e.getMessage()); 
			return false;
		}
		
		Notification notification = Notification.show(getTranslation("card.createSuccess") + T.getNumTarjeta() + getTranslation("card.createSuccess2"));
		notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
		
		close();
		return true;
	}
	
	public void setTitular(Cliente titular) {_cliente = titular; txtTitular.setValue(titular.getNombre()); }
	
	public Boolean comprobarCampos() {
		Boolean fallo = false;
		TipoTarjeta tp;
		Cuenta cuenta;
		Optional<Cuenta> optCuenta = java.util.Optional.empty();
		String sPin;

		if(rdGroup.getValue() == null) { rdGroup.getElement().setAttribute("invalid", ""); rdGroup.setErrorMessage(getTranslation("card.errorType")); fallo = true; }
		tp = new TipoTarjeta(EnumTarjeta.toTipo(rdGroup.getValue()));
		
		if(tp.getTipo() != EnumTarjeta.Prepago) {
			if(cmbCuentas.getValue() == null && tp.getTipo() != EnumTarjeta.Prepago) {	cmbCuentas.getElement().setAttribute("invalid", ""); cmbCuentas.setErrorMessage(getTranslation("card.errorAccount")); fallo = true; }
			optCuenta = _cuentaService.findByNumeroCuenta(cmbCuentas.getValue());
			if(optCuenta.isEmpty() || aCuentas.indexOf(optCuenta.get()) != -1) { cmbCuentas.getElement().setAttribute("invalid", ""); cmbCuentas.setErrorMessage(getTranslation("card.errorFindAccount")); fallo = true; }
			if(EnumTarjeta.toTipo(rdGroup.getValue()) == EnumTarjeta.Debito && !_tarService.findByCuenta(optCuenta.get()).isEmpty()) { cmbCuentas.getElement().setAttribute("invalid", ""); cmbCuentas.setErrorMessage(getTranslation("card.errorExist")); fallo = true; }
		}
		
		if(txtPin.getValue().length() != 4) { txtPin.getElement().setAttribute("invalid", ""); txtPin.setErrorMessage(getTranslation("card.errorPin")); fallo = true; }
		if(!txtPin.getValue().matches("\\d{4}")) { txtPin.getElement().setAttribute("invalid", ""); txtPin.setErrorMessage(getTranslation("card.errorPinNumeric")); fallo = true; }
		return fallo;
	}

	public Registration addUpdateListener(ComponentEventListener<UpdateEvent> listener) {
	        return addListener(UpdateEvent.class, listener);
    }
}
