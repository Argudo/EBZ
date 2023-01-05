package es.uca.iw.ebz.views.main;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.Scroller.ScrollDirection;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoIcon;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Cuenta.CuentaService;
import es.uca.iw.ebz.tarjeta.EnumTarjeta;
import es.uca.iw.ebz.tarjeta.Tarjeta;
import es.uca.iw.ebz.tarjeta.TarjetaService;
import es.uca.iw.ebz.tarjeta.credito.TipoCrediticioRepository;
import es.uca.iw.ebz.tarjeta.prepago.PrepagoService;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteService;
import es.uca.iw.ebz.views.main.Security.AuthenticatedUser;
import es.uca.iw.ebz.views.main.component.NuevaTarjetaDialog;
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
		
	private HorizontalLayout hlDetalleTarjetas = new HorizontalLayout();
		private VerticalLayout vlAccionTarjetas = new VerticalLayout();
			private Button btnRecarga = new Button("Recargar", VaadinIcon.MONEY_DEPOSIT.create());
			private Button btnCancelarTarjeta = new Button("Eliminar", LumoIcon.CROSS.create());
		private HorizontalLayout hlTarjetas = new HorizontalLayout();
		
	private VerticalLayout vlDetalleTarjetas = new VerticalLayout();	
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
	
	private NuevaTarjetaDialog dlogNT;
	
	TarjetaView(AuthenticatedUser _authUser, ClienteService _cliService, TarjetaService _tarService, CuentaService _cuentaService, PrepagoService _prepagoService, TipoCrediticioRepository _tipoCredRepo){
		dlogNT = new NuevaTarjetaDialog(_cuentaService, _cliService, _tarService, _tipoCredRepo);
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
		
		tNewCard.getElement().addEventListener("click", e ->{			
			dlogNT.setTitular(_cliente); 
			dlogNT.open();
		});
		
		dlogNT.addUpdateListener(e -> {
			ActualizarTarjetas(e.getTarjeta());
		});
		
		VerticalLayout vlSeparator = new VerticalLayout();
		vlSeparator.setWidth("2px");
		vlSeparator.getStyle().set("background-color", "var(--lumo-contrast-10pct)");
		vlSeparator.getStyle().set("padding", "0");
		
		scrllTarjetas.setContent(hlTarjetas);
		vlTarjetas.setWidth("90%");
		btnRecarga.setHeightFull();
		btnRecarga.setEnabled(false);
		btnRecarga.addThemeVariants(ButtonVariant.LUMO_LARGE);
		btnCancelarTarjeta.setEnabled(false);
		btnCancelarTarjeta.addThemeVariants(ButtonVariant.LUMO_LARGE);
		btnCancelarTarjeta.addThemeVariants(ButtonVariant.LUMO_ERROR);

		vlAccionTarjetas.setWidth("10%");
		vlAccionTarjetas.setPadding(false);
		vlAccionTarjetas.add(btnRecarga, btnCancelarTarjeta);
		
		vlTarjetas.add(hTarjeta, new Hr(), scrllTarjetas);
		hlDetalleTarjetas.add(vlTarjetas, vlSeparator, vlAccionTarjetas);
		add(hlDetalleTarjetas, hlInformacion, dlogNT);
	}
	
	private void CargarDetalles() {
		if(tcSelected.getSelected()) {
			btnCancelarTarjeta.setEnabled(true);
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
				btnRecarga.setEnabled(true);
				hSaldo.setText("Saldo");
				pSaldo.setText(String.valueOf(_prepagoService.findByTarjeta(tarSelected).getSaldo()) + "€");
			}
			else {
				hSaldo.setText("Número de cuenta");
				pSaldo.setText(tarSelected.getCuenta().getNumeroCuenta());
			}			
		}
		else {
			btnRecarga.setEnabled(false);
			btnCancelarTarjeta.setEnabled(false);
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
