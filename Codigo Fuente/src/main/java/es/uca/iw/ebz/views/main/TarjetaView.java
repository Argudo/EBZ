package es.uca.iw.ebz.views.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.Scroller.ScrollDirection;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Cuenta.CuentaService;
import es.uca.iw.ebz.tarjeta.EnumTarjeta;
import es.uca.iw.ebz.tarjeta.Tarjeta;
import es.uca.iw.ebz.tarjeta.TarjetaService;
import es.uca.iw.ebz.tarjeta.TipoTarjeta;
import es.uca.iw.ebz.tarjeta.credito.TipoCrediticioRepository;
import es.uca.iw.ebz.tarjeta.prepago.PrepagoService;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteService;
import es.uca.iw.ebz.views.main.Security.AuthenticatedUser;
import es.uca.iw.ebz.views.main.component.TarjetaComponent;
import es.uca.iw.ebz.views.main.layout.MainLayout;

@PageTitle("Tarjetas")
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
	
	static TarjetaComponent tcSelected = null;
	static Tarjeta tarSelected = null;
	private Cliente _cliente;
	private List<Cuenta> aCuentas;
	private List<TarjetaComponent> aTarjetasComponent = new ArrayList<TarjetaComponent>();
	
	private HorizontalLayout hlInformacion = new HorizontalLayout();
	private HorizontalLayout hlTarjetas = new HorizontalLayout();
	
	VerticalLayout vlDetalleTarjetas = new VerticalLayout();	
		private H3 hNumTarjeta = new H3("Número de tarjeta");
		private H3 hTipoTarjeta = new H3("Tipo de tarjeta");
		private H3 hSaldo = new H3("Saldo");
		private H3 hCvc = new H3("CVC");
		private H3 hPin = new H3("PIN");
		private H3 hFechaCaducidad = new H3("Fecha de expiración");
		private Paragraph pNumTarjeta = new Paragraph();
		private Paragraph pTipoTarjeta = new Paragraph();
		private Paragraph pSaldo = new Paragraph();
		private Paragraph pCvc = new Paragraph();
		private Paragraph pPin = new Paragraph();
		private PasswordField textCvc = new PasswordField();
		private PasswordField textPin = new PasswordField();
		private Paragraph pFechaCaducidad = new Paragraph();
			
	VerticalLayout vlTransacciones = new VerticalLayout();
	
	private Dialog dlogNT = new Dialog();
		private RadioButtonGroup<String> rdGroup = new RadioButtonGroup<String>();
		private PasswordField txtPin = new PasswordField("Crear PIN");
		private TextField txtFechaExp = new TextField("Fecha de expiración");
		private ComboBox<String> cmbCuentas = new ComboBox<>("Seleccione la cuenta");
		private ComboBox<String> cmbTipoCredito = new ComboBox<>("Seleccione el tipo de tarjeta crediticia");
		private TextField txtTitular = new TextField("Nombre del titular");
	
	
	TarjetaView(AuthenticatedUser _authUser, ClienteService _cliService, TarjetaService _tarService, CuentaService _cuentaService, PrepagoService _prepagoService, TipoCrediticioRepository _tipoCredRepo){
		setWidthFull();
		setPadding(true);
		setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER); 
		setAlignItems(FlexComponent.Alignment.CENTER);
		_cliente = _cliService.findByUsuario(_authUser.get().get());
		List<Tarjeta> aTarjetas = _tarService.findByCliente(_cliente);
		textCvc.setReadOnly(true);
		textCvc.setClassName("padding40");
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
		tcSelected = aTarjetasComponent.get(0);
		tcSelected.seleccionarTarjeta();
		tarSelected = tcSelected.getTarjeta();
		CargarDetalles();
		TarjetaComponent tNewCard = new TarjetaComponent();
		hlTarjetas.add(tNewCard);
		
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
		FlexLayout hlInfo = new FlexLayout();
		hlInfo.setFlexWrap(FlexWrap.WRAP);
		hlInfo.setWidthFull();
		hlInfo.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
		txtPin.setMaxLength(4);
		txtPin.setMinLength(4);
		txtPin.setPattern("[0-9]{4}");
		txtFechaExp.setValue(Integer.toString(new Date().getMonth()) + "/" + Integer.toString(new Date().getYear()+4).substring(1,3));
		txtFechaExp.setReadOnly(true);
		hlInfo.add(txtPin, txtFechaExp);

		txtTitular.setValue(_cliente.getNombre());
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
			dlogNT.getFooter().add(hlOptions);
		});
		
		btnCancelar.getElement().addEventListener("click", e -> dlogNT.close());
		btnGenerar.getElement().addEventListener("click", e -> GenerarTarjeta());
		dlogNT.getHeader().add(btnCancelar);
		vlogMain.add(rdGroup, txtTitular);
		dlogNT.add(new Hr(), vlogMain);
		
		tNewCard.getElement().addEventListener("click", e -> dlogNT.open());
		
		scrllTarjetas.setContent(hlTarjetas);		
		vlTarjetas.add(hTarjeta, new Hr(), scrllTarjetas);
		add(vlTarjetas, hlInformacion, dlogNT);
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
		if(EnumTarjeta.toTipo(rdGroup.getValue()) == EnumTarjeta.Debito && !_tarService.findByCuenta(optCuenta.get()).isEmpty()) { cmbCuentas.getElement().setAttribute("invalid", ""); cmbCuentas.setErrorMessage("Ya existe una tarjeta de débito para la cuenta seleccionada"); fallo = true; } 
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
		ActualizarTarjetas(T);
		dlogNT.close();
	}
	
	private void CargarDetalles() {
		if(tcSelected.getSelected()) {
			pNumTarjeta.setText(tarSelected.getNumTarjeta());
			pTipoTarjeta.setText(tarSelected.getStringTipoTarjeta());
			pFechaCaducidad.setText(tarSelected.getFechaExpiracion().toString());
			pPin.setText(String.valueOf(tarSelected.getiPin()));
			textCvc.setValue(tarSelected.getCVC());
			textPin.setValue(String.valueOf(tarSelected.getiPin()));
			vlDetalleTarjetas.getChildren().forEach(child -> {
				if(child.getClass() != H1.class || child.getClass() != Hr.class) {
					child.setVisible(true);
				}
			});
			if(tarSelected.getTipoTarjeta() == EnumTarjeta.Prepago) {
				hSaldo.setText("Saldo");
				pSaldo.setText(String.valueOf(_prepagoService.findByTarjeta(tarSelected).getSaldo()) + "€");
			}
			else {
				hSaldo.setText("Número de cuenta");
				pSaldo.setText(tarSelected.getCuenta().getNumeroCuenta());
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
	
	private void ActualizarTarjetas(Tarjeta T) {
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
}
