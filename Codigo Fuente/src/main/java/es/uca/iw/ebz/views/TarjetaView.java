package es.uca.iw.ebz.views;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import com.vaadin.componentfactory.ToggleButton;
import es.uca.iw.ebz.views.Security.AuthenticatedUser;
import es.uca.iw.ebz.views.component.TarjetaComponent;
import es.uca.iw.ebz.views.component.MovimientosComponent.TipoGrid;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.Scroller.ScrollDirection;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoIcon;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Cuenta.CuentaService;
import es.uca.iw.ebz.Movimiento.Movimiento;
import es.uca.iw.ebz.Movimiento.MovimientoService;
import es.uca.iw.ebz.Movimiento.TipoMovimiento;
import es.uca.iw.ebz.tarjeta.EnumTarjeta;
import es.uca.iw.ebz.tarjeta.Tarjeta;
import es.uca.iw.ebz.tarjeta.TarjetaService;
import es.uca.iw.ebz.tarjeta.credito.TipoCrediticioRepository;
import es.uca.iw.ebz.tarjeta.prepago.PrepagoService;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteService;
import es.uca.iw.ebz.views.component.MovimientosComponent;
import es.uca.iw.ebz.views.component.NuevaTarjetaDialog;
import es.uca.iw.ebz.views.layout.MainLayout;

@PageTitle("Tarjetas | EBZ")
@Route(value = "tarjetas", layout = MainLayout.class)
@RolesAllowed({ "Cliente" })
public class TarjetaView extends VerticalLayout{
	@Autowired
	private TarjetaService _tarService;
	@Autowired
	private AuthenticatedUser _authUser;
	@Autowired
	private ClienteService _cliService;
	@Autowired
	private CuentaService _cuentaService;
	@Autowired
	private PrepagoService _prepagoService;
	@Autowired
	private TipoCrediticioRepository _tipoCredRepo;
	@Autowired
	private MovimientoService _movService;
	
	static TarjetaComponent tcSelected = null;
	static Tarjeta tarSelected = null;
	private Cliente _cliente;
	private List<Cuenta> aCuentas;
	private List<TarjetaComponent> aTarjetasComponent = new ArrayList<TarjetaComponent>();
	
	private HorizontalLayout hlInformacion = new HorizontalLayout();
		
	private HorizontalLayout hlDetalleTarjetas = new HorizontalLayout();
		private VerticalLayout vlAccionTarjetas = new VerticalLayout();
			private Button btnRecarga = new Button(getTranslation("card.charge"), VaadinIcon.MONEY_DEPOSIT.create());
				private Dialog dlogRecarga = new Dialog();
				private VerticalLayout vlRecarga = new VerticalLayout();
				private ComboBox<String> cbCuentas = new ComboBox<String>(getTranslation("card.selectAccount"));
				private NumberField txtCantidad = new NumberField(getTranslation("card.amount"));
				private Button btnRecargaDlog = new Button(getTranslation("card.deposit"));
				private Button btnCancelarDlog = new Button(LumoIcon.CROSS.create());
			private Button btnCancelarTarjeta = new Button(getTranslation("card.delete"), LumoIcon.CROSS.create());
				private ConfirmDialog dlogEliminarTarjeta = new ConfirmDialog();
			private Button btnCambiarPin = new Button(getTranslation("card.newPin"), LumoIcon.RELOAD.create());
			private HorizontalLayout hlActivarTarjeta = new HorizontalLayout();
				private ToggleButton tgbtnActivacionTarjeta = new ToggleButton();
				private Span spanToggle = new Span();
		private HorizontalLayout hlTarjetas = new HorizontalLayout();
		
	private VerticalLayout vlDetalleTarjetas = new VerticalLayout();	
		private H3 hNumTarjeta = new H3(getTranslation("card.number"));
		private H3 hTipoTarjeta = new H3(getTranslation("card.type"));
		private H3 hSaldo = new H3(getTranslation("card.balance"));
		private H3 hCvc = new H3("CVC");
		private H3 hPin = new H3("PIN");
		private H3 hFechaCaducidad = new H3(getTranslation("card.dateExpiration"));
		private Paragraph pNumTarjeta = new Paragraph();
		private Paragraph pTipoTarjeta = new Paragraph();
		private Paragraph pSaldo = new Paragraph();
		private Paragraph pCvc = new Paragraph();
		private Paragraph pPin = new Paragraph();
		private PasswordField textCvc = new PasswordField();
		private PasswordField textPin = new PasswordField();
		private Paragraph pFechaCaducidad = new Paragraph();
			
	VerticalLayout vlTransacciones = new VerticalLayout();
		H1 hTransacciones = new H1(getTranslation("tarjeta.transacciones"));
	
	private NuevaTarjetaDialog dlogNT;
	private Dialog dlogPin = new Dialog();
		private PasswordField txtPinDlog = new PasswordField(getTranslation("card.pin"));
		private Button btnCerrarDlog = new Button(LumoIcon.CROSS.create());
		private Button btnPinDlog = new Button(getTranslation("card.save"));
	
	TarjetaView(AuthenticatedUser _authUser, ClienteService _cliService, TarjetaService _tarService, CuentaService _cuentaService, PrepagoService _prepagoService, TipoCrediticioRepository _tipoCredRepo, MovimientoService _movService){
		_cliente = _cliService.findByUsuario(_authUser.get().get());
		dlogNT = new NuevaTarjetaDialog(_cuentaService, _cliService, _tarService, _tipoCredRepo, _prepagoService);
		
		dlogPin.setHeaderTitle(getTranslation("card.changePin"));
		dlogPin.add(txtPinDlog);
		btnCerrarDlog.addThemeVariants(ButtonVariant.LUMO_ICON);
		btnCerrarDlog.addThemeVariants(ButtonVariant.LUMO_ERROR);
		btnPinDlog.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		dlogPin.getHeader().add(btnCerrarDlog);
		dlogPin.getFooter().add(btnPinDlog);
		txtPinDlog.setMaxLength(4);
		txtPinDlog.setMinLength(4);
		
		btnPinDlog.addClickListener(ev -> {
			try {
				if(txtPinDlog.getValue().length() != 4 || !txtPinDlog.getValue().matches("\\d{4}")) { txtPinDlog.getElement().setAttribute("invalid", ""); txtPinDlog.setErrorMessage(getTranslation("card.error4number"));}
				else {
					tarSelected.setiPin(txtPinDlog.getValue()); 
					_tarService.Update(tarSelected); 
					textPin.setValue(txtPinDlog.getValue()); 
					Notification not = Notification.show(getTranslation("card.PinSuccess"));
					not.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
					dlogPin.close(); 
					}				
			}catch(Exception e) { txtPinDlog.getElement().setAttribute("invalid", ""); txtPinDlog.setErrorMessage(e.getMessage()); e.printStackTrace();}
		});
		
		btnCerrarDlog.addClickListener(e -> dlogPin.close());
		
		dlogRecarga.setHeaderTitle(getTranslation("card.reload"));
		btnCancelarDlog.addThemeVariants(ButtonVariant.LUMO_ICON);
		btnCancelarDlog.addThemeVariants(ButtonVariant.LUMO_ERROR);
		btnCancelarDlog.addClickListener(e -> dlogRecarga.close());
		btnRecargaDlog.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		dlogRecarga.getHeader().add(btnCancelarDlog);
		dlogRecarga.getFooter().add(btnRecargaDlog);
		dlogRecarga.setWidth("20vw");
		List<String> aCuentas = new ArrayList<String>();
		_cuentaService.findByCliente(_cliente).forEach(c -> aCuentas.add(c.getNumeroCuenta()));
		cbCuentas.setItems(aCuentas);
		cbCuentas.setWidthFull();
		Div euroSuffix = new Div(); euroSuffix.setText("€");
		txtCantidad.setSuffixComponent(euroSuffix);
		txtCantidad.setWidthFull();
		vlRecarga.add(new Hr(), cbCuentas, txtCantidad);
		dlogRecarga.add(vlRecarga);
		btnRecargaDlog.addClickListener(ev -> {
			Movimiento movRecarga = new Movimiento(new Date(), getTranslation("tarjeta.recargatarjeta") + tarSelected.getNumTarjeta(), TipoMovimiento.RECARGATARJETA);
			try {				
				_movService.recargaTarjeta(movRecarga, _cuentaService.findByNumeroCuenta(cbCuentas.getValue()).get(), tarSelected, txtCantidad.getValue().floatValue());
				pSaldo.setText(String.valueOf(_prepagoService.findByTarjeta(tarSelected).getSaldo()) + "€");
				Notification notification = Notification.show(getTranslation("tarjeta.depositof") + txtCantidad.getValue() + "€" + getTranslation("tarjeta.from") + cbCuentas.getValue() + getTranslation("tarjeta.success"));
				notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
				dlogRecarga.close();
			}
			catch(Exception e) {
				if(e.getMessage().contains("Cuenta")) { cbCuentas.getElement().setAttribute("invalid", ""); cbCuentas.setErrorMessage(e.getMessage()); }
				else txtCantidad.getElement().setAttribute("invalid", ""); txtCantidad.setErrorMessage(e.getMessage());
			}
		});
		
		dlogEliminarTarjeta.setHeader(getTranslation("card.deleteCard"));
		dlogEliminarTarjeta.setCancelable(true);
		dlogEliminarTarjeta.setCancelText(getTranslation("card.discard"));
		dlogEliminarTarjeta.addCancelListener(event -> dlogEliminarTarjeta.close());
		dlogEliminarTarjeta.setConfirmText(getTranslation("card.delete"));
		dlogEliminarTarjeta.setConfirmButtonTheme("error primary");
		dlogEliminarTarjeta.addConfirmListener(event -> {
			tarSelected.setFechaCancelacion(new Date());
			try {
				_tarService.Update(tarSelected);
			} catch (Exception e) { e.printStackTrace(); }
			ActualizarTarjetas(tarSelected, false);
		});
		
		setWidthFull();
		setPadding(true);
		setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER); 
		setAlignItems(FlexComponent.Alignment.CENTER);
		List<Tarjeta> aTarjetas = _tarService.findByCliente(_cliente);
		textCvc.setReadOnly(true);
		textCvc.setClassName("padding40");
		textPin.setReadOnly(true);
		textPin.setClassName("padding40");
		
		H1 hTarjeta = new H1(getTranslation("card.home"));
		hTarjeta.setClassName("title");
		H1 hDetalleTarjeta = new H1(getTranslation("card.details"));
		hDetalleTarjeta.setClassName("title");
		H1 hTransacciones = new H1(getTranslation("card.transactions"));
		hTransacciones.setClassName("title");
		
		
		Scroller scrllTarjetas = new Scroller();
		scrllTarjetas.setScrollDirection(ScrollDirection.HORIZONTAL);
		scrllTarjetas.setWidth("100%");
		scrllTarjetas.setHeight("100%");
		
		VerticalLayout vlTarjetas = new VerticalLayout();
		hlDetalleTarjetas.setWidth("70vw");
		hlDetalleTarjetas.setPadding(true);
		hlDetalleTarjetas.setMargin(true);
		hlDetalleTarjetas.setClassName("box");
		
		vlDetalleTarjetas.setWidth("50%");
		vlDetalleTarjetas.setPadding(true);
		vlDetalleTarjetas.setMargin(true);
		vlDetalleTarjetas.setSpacing(false);
		vlDetalleTarjetas.setClassName("detalleTarjeta");
		vlDetalleTarjetas.add(hDetalleTarjeta,
							  new Hr(),
							  hNumTarjeta,
							  pNumTarjeta,
							  hTipoTarjeta,
							  pTipoTarjeta,
							  hSaldo,
							  pSaldo,
							  hCvc,
							  textCvc,
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
		
		hlTarjetas.setWidth("100%");
		hlTarjetas.setHeight("100%");
		hlTarjetas.setPadding(true);
		hlTarjetas.setMargin(true);
		
		for(Tarjeta t: aTarjetas) {
			aTarjetasComponent.add(new TarjetaComponent(t));
		}
		if(aTarjetasComponent.size() > 0) {				
			tcSelected = aTarjetasComponent.get(0);
			tcSelected.seleccionarTarjeta();
			tarSelected = tcSelected.getTarjeta();
			CargarDetalles();
		}
		
		TarjetaComponent tNewCard = new TarjetaComponent();
		hlTarjetas.add(tNewCard); 
		
		for(TarjetaComponent tc: aTarjetasComponent) {
			hlTarjetas.add(tc);
		}
		
		hlTarjetas.getChildren().forEach(child -> {
			if(child != tNewCard) {				
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
			}
		});
		
		tNewCard.getElement().addEventListener("click", e ->{			
			dlogNT.setTitular(_cliente); 
			dlogNT.open();
		});
		
		dlogNT.addUpdateListener(e -> {
			ActualizarTarjetas(e.getTarjeta(), true);
		});
		
		btnRecarga.addClickListener(e -> {
			if(btnRecarga.isEnabled()) {
				dlogRecarga.open();
			}
		});
		
		btnCambiarPin.addClickListener(e -> { dlogPin.open(); });
		
		tgbtnActivacionTarjeta.addValueChangeListener(ev -> {
			spanToggle.setText(ev.getValue()? getTranslation("card.active") : getTranslation("card.inactive"));
			if(tcSelected.getSelected()) {				
				tarSelected.setActiva(ev.getValue());
				try {
					_tarService.Update(tarSelected);
				} catch (Exception e) { e.printStackTrace(); }
			}
		});
		
		btnCancelarTarjeta.addClickListener(e -> {
			dlogEliminarTarjeta.setText(getTranslation("card.confirm") + tarSelected.getNumTarjeta() + getTranslation("card.confirm2"));
			dlogEliminarTarjeta.open();
		});
		
		VerticalLayout vlSeparator = new VerticalLayout();
		vlSeparator.setWidth("2px");
		vlSeparator.getStyle().set("background-color", "var(--lumo-contrast-10pct)");
		vlSeparator.getStyle().set("padding", "0");
		
		scrllTarjetas.setContent(hlTarjetas);
		vlTarjetas.setWidth("calc(100% - 250px)");
		hlActivarTarjeta.add(tgbtnActivacionTarjeta, spanToggle);
		hlActivarTarjeta.addClassName("button");
		hlActivarTarjeta.setAlignItems(FlexComponent.Alignment.CENTER);
		hlActivarTarjeta.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
		hlActivarTarjeta.setWidthFull();
		btnRecarga.setSizeFull();
		btnRecarga.addThemeVariants(ButtonVariant.LUMO_LARGE);
		btnCancelarTarjeta.setWidthFull();
		btnCancelarTarjeta.addThemeVariants(ButtonVariant.LUMO_LARGE);
		btnCancelarTarjeta.addThemeVariants(ButtonVariant.LUMO_ERROR);
		btnCambiarPin.setWidthFull();
		btnCambiarPin.addThemeVariants(ButtonVariant.LUMO_LARGE);

		vlAccionTarjetas.setWidth("250px");
		vlAccionTarjetas.setPadding(false);
		vlAccionTarjetas.add(btnRecarga, hlActivarTarjeta, btnCambiarPin, btnCancelarTarjeta);
		
		vlTarjetas.add(hTarjeta, new Hr(), scrllTarjetas);
		hlDetalleTarjetas.add(vlTarjetas, vlSeparator, vlAccionTarjetas);
		add(hlDetalleTarjetas, hlInformacion, dlogNT);
	}
	
	private void CargarDetalles() {
		if(tcSelected.getSelected()) {
			hTransacciones.setClassName("title");
			vlTransacciones.removeAll(); vlTransacciones.add(hTransacciones);
			if(_movService != null) vlTransacciones.add(new MovimientosComponent(TipoGrid.Parcial, _movService, tarSelected));
			btnCancelarTarjeta.setEnabled(true);
			btnCambiarPin.setEnabled(true);
			hlActivarTarjeta.setEnabled(true);
			tgbtnActivacionTarjeta.setEnabled(true);
			spanToggle.getStyle().set("color", "var(--_lumo-button-color, var(--lumo-primary-text-color))");
			pNumTarjeta.setText(tarSelected.getNumTarjeta());	
			pTipoTarjeta.setText(tarSelected.getStringTipoTarjeta());
			pFechaCaducidad.setText(tarSelected.getFechaExpiracion().toString());
			pPin.setText(String.valueOf(tarSelected.getiPin()));
			textCvc.setValue(tarSelected.getCVC());
			textPin.setValue(String.valueOf(tarSelected.getiPin()));
			if(tarSelected.getActiva()) {
				spanToggle.setText(getTranslation("card.active"));
				tgbtnActivacionTarjeta.setValue(true);				
			}
			else {
				spanToggle.setText(getTranslation("card.inactive"));
				tgbtnActivacionTarjeta.setValue(false);
			}
			vlDetalleTarjetas.getChildren().forEach(child -> {
				if(child.getClass() != H1.class || child.getClass() != Hr.class) {
					child.setVisible(true);
				}
			});
			if(tarSelected.getTipoTarjeta() == EnumTarjeta.Prepago) {
				btnRecarga.setEnabled(true);
				hSaldo.setText(getTranslation("card.balance"));
				pSaldo.setText(String.valueOf(_prepagoService.findByTarjeta(tarSelected).getSaldo()) + "€");
			}
			else {
				btnRecarga.setEnabled(false);
				hSaldo.setText(getTranslation("card.number"));
				pSaldo.setText(tarSelected.getCuenta().getNumeroCuenta());
			}			
		}
		else {
			vlTransacciones.removeAll(); vlTransacciones.add(hTransacciones);
			btnRecarga.setEnabled(false);
			btnCancelarTarjeta.setEnabled(false);
			btnCambiarPin.setEnabled(false);
			hlActivarTarjeta.setEnabled(false);
			tgbtnActivacionTarjeta.setEnabled(false);
			tgbtnActivacionTarjeta.setValue(false);			
			spanToggle.getStyle().set("color", "var(--lumo-disabled-text-color)");
			vlDetalleTarjetas.getChildren().forEach(child -> {
				if(child.getClass() != H1.class && child.getClass() != Hr.class) {
					child.setVisible(false);
				}
			});
		}
	}
	
	private void ActualizarTarjetas(Tarjeta T, Boolean esNueva) {
		if(esNueva) {			
			TarjetaComponent tarComp = new TarjetaComponent(T);
			aTarjetasComponent.add(tarComp);
			hlTarjetas.add(tarComp);
			tarComp.getElement().addEventListener("click", e -> {				
				if(tcSelected == null) {
					tcSelected = TarjetaComponent.class.cast(tarComp);
					tcSelected.seleccionarTarjeta();
					tarSelected = tcSelected.getTarjeta();
					CargarDetalles();
				}
				
				if(tcSelected != TarjetaComponent.class.cast(tarComp)) {
					tcSelected.deseleccionarTarjeta();
					tcSelected = TarjetaComponent.class.cast(tarComp);
					tcSelected.seleccionarTarjeta();
					tarSelected = tcSelected.getTarjeta();
					CargarDetalles();
				}
				else if((tcSelected == TarjetaComponent.class.cast(tarComp) && tcSelected.getSelected())) {
					tcSelected.deseleccionarTarjeta();
					CargarDetalles();
				}
				else {
					tcSelected = TarjetaComponent.class.cast(tarComp);
					tcSelected.seleccionarTarjeta();
					tarSelected = tcSelected.getTarjeta();
					CargarDetalles();
				}
			});
		}
		else {
			aTarjetasComponent.remove(tcSelected);
			hlTarjetas.remove(tcSelected);
		}
	}
}
