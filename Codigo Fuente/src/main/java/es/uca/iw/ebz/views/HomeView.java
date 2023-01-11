package es.uca.iw.ebz.views;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.*;
import es.uca.iw.ebz.consulta.Consulta;
import es.uca.iw.ebz.consulta.ConsultaService;
import es.uca.iw.ebz.usuario.TipoUsuario;
import es.uca.iw.ebz.views.component.DetallesCuentaDialog;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Cuenta.CuentaService;
import es.uca.iw.ebz.Movimiento.Movimiento;
import es.uca.iw.ebz.Movimiento.MovimientoService;
import es.uca.iw.ebz.tarjeta.Tarjeta;
import es.uca.iw.ebz.tarjeta.TarjetaService;
import es.uca.iw.ebz.usuario.UsuarioService;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteService;
import es.uca.iw.ebz.views.Security.AuthenticatedUser;
import es.uca.iw.ebz.views.component.TarjetaComponent;
import es.uca.iw.ebz.views.layout.MainLayout;



@PageTitle("EBZ")
@Route(value = "", layout = MainLayout.class)
@RouteAlias(value = "home", layout = MainLayout.class)
@PermitAll

public class HomeView extends VerticalLayout implements BeforeEnterObserver {
@Autowired
private MovimientoService _movimientoService;

@Autowired
private CuentaService _cuentaService;

@Autowired
private TarjetaService _tarjetaService;

@Autowired
private ClienteService _clienteService;

@Autowired
private UsuarioService _usuarioService;

@Autowired
private ConsultaService _consultaService;

@Autowired
private AuthenticatedUser _authenticatedUser;

//La cuenta que usaremos para ir actualizando el primer layout.
static Cuenta acSelected = null;

private Cliente _cliente;

private DetallesCuentaDialog dlogDC;

//Atributos del layout de la cuenta.
H2 _acNumber = new H2();
H3 _acBalance = new H3();

	public HomeView(MovimientoService movimientoService, CuentaService cuentaService,
					TarjetaService tarjetaService, ClienteService clienteService,
					UsuarioService usuarioService, ConsultaService consultaService,
					AuthenticatedUser authenticatedUser) {

		//Services initialization section
		_movimientoService = movimientoService;
		_cuentaService = cuentaService;
		_tarjetaService = tarjetaService;
		_clienteService = clienteService;
		_usuarioService = usuarioService;
		_consultaService = consultaService;
		_authenticatedUser = authenticatedUser;
		//End services initialization section


			//UI.getCurrent().navigate(DashBoardView.class);

		//Client asignation
		_cliente = _clienteService.findByUsuario(_authenticatedUser.get().get()); // es un optional, por eso el get()

		//Account

		setMargin(false);
		setPadding(false);
		setSpacing(true);
		setWidthFull();
		setAlignItems(FlexComponent.Alignment.CENTER);

		//First layout section
		HorizontalLayout hlMain = new HorizontalLayout();
		hlMain.setAlignItems(Alignment.CENTER);
		hlMain.setWidth("80vw");
		hlMain.setHeight("450px");
		hlMain.setPadding(false);
		hlMain.setSpacing(false);
		hlMain.setMargin(true);
		hlMain.setClassName("box");
		//End first layout section


		//Account information and buttons section
		Component userName = new H1("Bienvenido de nuevo");
		//Username section
		if(_cliente != null) userName = CreateUserNameBanner(_cliente.getNombre());
		//Component userName = CreateUserNameBanner(_authenticatedUser.get().get().getUsuario());
		//End username section

		//Account gallery section
		List<Cuenta> accountList = _cuentaService.findByCliente(_cliente);

		if(accountList.size() >= 1){

			VerticalLayout vlAccount = new VerticalLayout();
			vlAccount.setPadding(false);
			vlAccount.setSpacing(false);
			vlAccount.setMargin(false);

			acSelected = accountList.get(0);
			updateAccountInfo();
			VerticalLayout vlShowAccount = new VerticalLayout();
			vlShowAccount.setAlignItems(Alignment.CENTER);
			vlShowAccount.setSpacing(false);
			vlShowAccount.setWidth("100%");

			vlShowAccount.add(
					_acNumber,
					_acBalance);

			vlAccount.add(userName);
			vlAccount.add(vlShowAccount);

			//Button section
			FlexLayout flAccountButtons = new FlexLayout();
			flAccountButtons.setWidthFull();
			flAccountButtons.setFlexDirection(FlexDirection.ROW);
			flAccountButtons.setFlexWrap(FlexWrap.WRAP);
			flAccountButtons.setJustifyContentMode(JustifyContentMode.EVENLY);

			Component cTarjeta = CreateButton(getTranslation("mainLayout.cards"), VaadinIcon.CREDIT_CARD);
			Component cEstadisticas = CreateButton(getTranslation("home.info"), VaadinIcon.INFO);
			Component cMovimientos = CreateButton(getTranslation("mainLayout.movement"), VaadinIcon.EXCHANGE);
			Component cTransferencias = CreateButton(getTranslation("mainLayout.transfer"), VaadinIcon.MONEY_EXCHANGE);
			flAccountButtons.add(
					cTarjeta,
					cEstadisticas,
					cMovimientos,
					cTransferencias
			);
			//End button section

			//Including name, account information and buttons to the first layout
			vlAccount.add(flAccountButtons);
			//End of including...

			//Account list layout section

			Scroller accountScroller = new Scroller();
			accountScroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
			accountScroller.setWidth("min-width");
			accountScroller.setHeightFull();


			VerticalLayout vlAccountList = new VerticalLayout();
			vlAccountList.setWidthFull();
			vlAccountList.setPadding(false);
			vlAccountList.setMargin(false);
			vlAccountList.setSpacing(false);
			vlAccountList.setAlignItems(Alignment.CENTER);

			//Pendiente de arreglar

			List<Component> accountListComponent = new ArrayList<Component>();
			for(Cuenta c: accountList) {
				accountListComponent.add(CreateAccountListElement(c));
			}
			for(Component c: accountListComponent){
				vlAccountList.add(c);
			}
			accountScroller.setContent(vlAccountList);
			//End account list layout section

			VerticalLayout vlSeparator = new VerticalLayout();
			vlSeparator.setWidth("2px");
			vlSeparator.setHeightFull();
			vlSeparator.getStyle().set("background-color", "var(--lumo-contrast-10pct)");
			vlSeparator.getStyle().set("padding", "0");
			hlMain.add(
					vlAccount,
					vlSeparator,
					accountScroller);

		}else{

			VerticalLayout vlNoAccounts = new VerticalLayout();
			vlNoAccounts.setWidthFull();
			vlNoAccounts.setPadding(true);
			vlNoAccounts.setSpacing(false);
			vlNoAccounts.setMargin(false);
			vlNoAccounts.setAlignItems(Alignment.CENTER);

			H1 sNoAccounts = new H1(getTranslation("home.noaccount"));


			vlNoAccounts.add(
					userName,
					sNoAccounts);

			hlMain.add(vlNoAccounts);

		}


		//End account gallery section

		//End first layout section

		//Movements and notifications section

		HorizontalLayout hlAccountMove = new HorizontalLayout();
		hlAccountMove.setWidthFull();
		hlAccountMove.setSpacing(true);
		hlAccountMove.setPadding(false);
		hlAccountMove.setMargin(true);
		hlAccountMove.setWidth("80vw");

		FlexLayout flAccountMovements = new FlexLayout();
		flAccountMovements.setWidthFull();
		flAccountMovements.setFlexDirection(FlexDirection.COLUMN);
		flAccountMovements.setFlexWrap(FlexWrap.WRAP);
		flAccountMovements.setJustifyContentMode(JustifyContentMode.EVENLY);
		flAccountMovements.setClassName("box");

		FlexLayout flAccountNotifications = new FlexLayout();
		flAccountNotifications.setWidthFull();
		flAccountNotifications.setFlexDirection(FlexDirection.COLUMN);
		flAccountNotifications.setFlexWrap(FlexWrap.WRAP);
		flAccountNotifications.setJustifyContentMode(JustifyContentMode.EVENLY);
		flAccountNotifications.setClassName("box");

		List<Movimiento> mvList = _movimientoService.findByClienteByFechaASC(_cliente);

		if(mvList.size() < 1){
			H2 mvMessage = new H2(getTranslation("movement.nomov"));
			flAccountMovements.setAlignItems(Alignment.CENTER);
			flAccountMovements.add(mvMessage);

		}else{
			H2 mvTitle = new H2(getTranslation("home.lastmove"));
			mvTitle.setClassName("subtitle");
			flAccountMovements.add(mvTitle);
			int cont = 0;
			List<Component> mvComponentList = new ArrayList<>();
			for(Movimiento m: mvList){
				if(cont < 3){
					mvComponentList.add(CreateMovement(m));
					cont++;
				}else break;
			}

			for(Component c: mvComponentList){
				flAccountMovements.add(c);
			}
		}

		List<Consulta> cnList = _consultaService.findByCliente(_authenticatedUser.get().get());

		if(cnList.size() < 1){
			H2 cnMessage = new H2(getTranslation("home.noquery"));
			flAccountNotifications.setAlignItems(Alignment.CENTER);
			flAccountNotifications.add(cnMessage);

		}else{
			H2 ntTitle = new H2(getTranslation("home.lastquery"));
			ntTitle.setClassName("subtitle");
			flAccountNotifications.add(ntTitle);
			int cont = 0;
			List<Component> cnComponentList = new ArrayList<>();
			for(Consulta c: cnList){
				if(cont < 3){
					cnComponentList.add(CreateNotification(c));
					cont++;
				}
				if(cont >= 3) break;
			}

			for(Component c: cnComponentList){
				flAccountNotifications.add(c);
			}
		}

		hlAccountMove.add(
				flAccountMovements,
				flAccountNotifications);

		//End movements and notifications section

		//Credit cards section
		List<Tarjeta> aTarjetas = _tarjetaService.findByCliente(_cliente);

		VerticalLayout vlTarjeta = new VerticalLayout();
		vlTarjeta.setWidth("80vw");
		vlTarjeta.setPadding(true);
		vlTarjeta.setMargin(true);
		vlTarjeta.setClassName("box");

		H2 tarjetaTitle = new H2(getTranslation("mainLayout.cards"));
		tarjetaTitle.setClassName("subtitle");

		if(aTarjetas.size() > 0){
			Scroller scrollTarjeta = new Scroller();
			scrollTarjeta.setScrollDirection(Scroller.ScrollDirection.HORIZONTAL);
			scrollTarjeta.setWidth("78vw");
			scrollTarjeta.setHeight("100%");

			HorizontalLayout hlTarjeta = new HorizontalLayout();
			hlTarjeta.setWidthFull();
			hlTarjeta.setHeight("100%");
			hlTarjeta.setPadding(true);
			hlTarjeta.setMargin(true);

			List<TarjetaComponent> aTarjetasComponent = new ArrayList<TarjetaComponent>();

			for(Tarjeta t: aTarjetas){
				aTarjetasComponent.add(new TarjetaComponent(t));
			}

			//Falta clickListener para las tarjetas con su vista detallada ¿?
			for(TarjetaComponent tc: aTarjetasComponent) {
				hlTarjeta.add(tc);
			}

			scrollTarjeta.setContent(hlTarjeta);
			vlTarjeta.add(tarjetaTitle, scrollTarjeta);

		}else{
			H2 tarMessage = new H2(getTranslation("home.nocards"));
			vlTarjeta.add(tarjetaTitle, tarMessage);
		}
		//End credit cards section

		add(hlMain);
		add(hlAccountMove);
		add(vlTarjeta);

	}

	private Component CreateButton(String sName, VaadinIcon vI) {
		VerticalLayout vlMain = new VerticalLayout();
		vlMain.setAlignItems(FlexComponent.Alignment.CENTER);
		vlMain.setSpacing(false);
		vlMain.setWidth("min-width");
		Button btnFunc = new Button();
		Icon icon = new Icon(vI);
		btnFunc.getElement().appendChild(icon.getElement());
		Paragraph pDescription = new Paragraph(sName);

		if(vI == VaadinIcon.INFO){
			btnFunc.addClickListener( e -> {
				dlogDC.open();
			});
		}


		vlMain.add(
				btnFunc,
				pDescription
				);

		return vlMain;
	}

	//Componente para mostrar el nombre del usuario en la vista principal.
	//Falta arreglar la situación del nombre
	private Component CreateUserNameBanner(String userName){
		VerticalLayout vlMain = new VerticalLayout();
		vlMain.setAlignItems(Alignment.CENTER);
		vlMain.setSpacing(false);
		vlMain.setPadding(true);
		vlMain.setWidthFull();

		H1 _userName = new H1(getTranslation("home.welcome") + userName);

		vlMain.add(_userName);

		return vlMain;

	}

	private Component CreateMovement(Movimiento mv){
		VerticalLayout vlMain = new VerticalLayout();
		vlMain.setAlignItems(Alignment.CENTER);
		vlMain.setSpacing(false);
		vlMain.setWidth("min-width");

		NumberFormat formatImport = NumberFormat.getCurrencyInstance();
		String sBalance = new String(formatImport.format(_movimientoService.datosMovimiento(mv).get("Importe")));

		Paragraph _mv1 = new Paragraph(mv.getConcepto() + " - " + sBalance);

		//_mv1.addClickListener();

		vlMain.add(
				_mv1);

		return vlMain;

	}

	private Component CreateNotification(Consulta c){
		VerticalLayout vlMain = new VerticalLayout();
		vlMain.setAlignItems(Alignment.CENTER);
		vlMain.setSpacing(false);
		vlMain.setWidth("min-width");

		Paragraph _nt1 = new Paragraph(c.getTitulo() + " - " + c.getTipoEstado().getTipo().toString());

		//_nt1.addClickListener();

		vlMain.add(_nt1);

		return vlMain;

	}

	private Component CreateAccountListElement(Cuenta ac){
		VerticalLayout vlMain = new VerticalLayout();
		vlMain.setClassName("button");
		vlMain.getStyle().set("margin", "0");
		vlMain.getStyle().set("border-radius", "0");
		vlMain.getStyle().set("border-bottom", "1px solid var(--lumo-contrast-20pct)");
		vlMain.setAlignItems(Alignment.START);
		vlMain.setSpacing(false);
		vlMain.setWidth("200px");
		vlMain.setHeightFull();

		NumberFormat formatImport = NumberFormat.getCurrencyInstance();
		String sBalance = new String(formatImport.format(ac.getSaldo()));

		String sAux = "ES*" + ac.getNumeroCuenta().substring(ac.getNumeroCuenta().length()-4, ac.getNumeroCuenta().length());

		H3 _ae1 = new H3(sAux);
		_ae1.getStyle().set("margin-left", "10px");
		H4 _ae2 = new H4("| " + sBalance);
		_ae2.getStyle().set("margin-top", "0px");
		_ae2.getStyle().set("margin-left", "10px");


		addClickListener(e -> {
			acSelected = ac;
			updateAccountInfo();
		});

		vlMain.add(
				_ae1, _ae2);

		return vlMain;

	}

	//Función para actualizar la info de la cuenta que mostramos en el primer layout
	private void updateAccountInfo() {
		_acNumber.setText(acSelected.getNumeroCuenta());

		dlogDC = new DetallesCuentaDialog(_cliente, acSelected);

		NumberFormat formatImport = NumberFormat.getCurrencyInstance();
		_acBalance.setText(formatImport.format(acSelected.getSaldo()));

	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if(_authenticatedUser.get().isPresent()){
			if(_authenticatedUser.get().get().getTipoUsuario() == TipoUsuario.Empleado) event.rerouteTo(DashBoardView.class);
		}
	}
}
