package es.uca.iw.ebz.views.main;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.*;
import es.uca.iw.ebz.usuario.TipoUsuario;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
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
import es.uca.iw.ebz.views.main.Security.AuthenticatedUser;
import es.uca.iw.ebz.views.main.component.TarjetaComponent;
import es.uca.iw.ebz.views.main.layout.MainLayout;



@PageTitle("")
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
private AuthenticatedUser _authenticatedUser;

//La cuenta que usaremos para ir actualizando el primer layout.
static Cuenta acSelected = null;

private Cliente _cliente;

//Atributos del layout de la cuenta.
H2 _acNumber = new H2();
H3 _acBalance = new H3();

	public HomeView(MovimientoService _movimientoService, CuentaService _cuentaService,
					TarjetaService _tarjetaService, ClienteService _clienteService,
					UsuarioService _usuarioService, AuthenticatedUser _authenticatedUser) {

		//Services initialization section
		this._movimientoService = _movimientoService;
		this._cuentaService = _cuentaService;
		this._tarjetaService = _tarjetaService;
		this._clienteService = _clienteService;
		this._usuarioService = _usuarioService;
		this._authenticatedUser = _authenticatedUser;
		//End services initialization section


			//UI.getCurrent().navigate(DashBoardView.class);

		//Client asignation
		_cliente = _clienteService.findByUsuario(_authenticatedUser.get().get()); // es un optional, por eso el get()

		setMargin(false);
		setPadding(false);
		setSpacing(true);
		setWidthFull();
		setAlignItems(FlexComponent.Alignment.CENTER);

		//First layout section
		HorizontalLayout hlMain = new HorizontalLayout();
		hlMain.setAlignItems(Alignment.CENTER);
		hlMain.setWidth("80vw");
		hlMain.setHeight("30%");
		hlMain.setPadding(false);
		hlMain.setSpacing(true);
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

		if(accountList.size() > 1){

			VerticalLayout vlAccount = new VerticalLayout();
			vlAccount.setWidth("70%");
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

			Component cTarjeta = CreateButton("Tarjetas", VaadinIcon.CREDIT_CARD);
			Component cEstadisticas = CreateButton("Estadísticas", VaadinIcon.BAR_CHART_H);
			Component cMovimientos = CreateButton("Movimientos", VaadinIcon.EXCHANGE);
			Component cTransferencias = CreateButton("Transferencias", VaadinIcon.MONEY_EXCHANGE);
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
			accountScroller.setWidth("28%");
			//accountScroller.setHeight("95%");


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

			hlMain.add(
					vlAccount,
					accountScroller);

		}else{

			VerticalLayout vlNoAccounts = new VerticalLayout();
			vlNoAccounts.setWidthFull();
			vlNoAccounts.setPadding(true);
			vlNoAccounts.setSpacing(false);
			vlNoAccounts.setMargin(false);
			vlNoAccounts.setAlignItems(Alignment.CENTER);

			H1 sNoAccounts = new H1("No tienes cuentas asociadas actualmente.");


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

			H2 mvMessage = new H2("No tienes movimientos actualmente.");
			flAccountMovements.setAlignItems(Alignment.CENTER);
			flAccountMovements.add(mvMessage);

		}else{
			int cont = 0;
			List<Component> mvComponentList = new ArrayList<>();
			for(Movimiento m: mvList){
				if(cont < 3){
					mvComponentList.add(CreateMovement(m));
					cont++;
				}
				if(cont >= 3) break;
			}

			for(Component c: mvComponentList){
				flAccountMovements.add(c);
			}
		}

		Component cNotification1 = CreateNotification();
		Component cNotification2 = CreateNotification();
		Component cNotification3 = CreateNotification();

		flAccountNotifications.add(
				cNotification1,
				cNotification2,
				cNotification3);

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

		H1 tarjetaTitle = new H1("Tarjetas");
		tarjetaTitle.setClassName("title");

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
			H2 tarMessage = new H2("No hay tarjetas asociadas.");
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

		H1 _userName = new H1("Bienvenido, " + userName);

		vlMain.add(_userName);

		return vlMain;

	}

	private Component CreateMovement(Movimiento mv){
		VerticalLayout vlMain = new VerticalLayout();
		vlMain.setAlignItems(Alignment.CENTER);
		vlMain.setSpacing(false);
		vlMain.setWidth("min-width");

		Paragraph _mv1 = new Paragraph(mv.getsConcpeto() + "  " + _movimientoService.datosMovimiento(mv).get("Importe"));

		//_mv1.addClickListener();

		vlMain.add(
				_mv1);

		return vlMain;

	}

	private Component CreateAccountListElement(Cuenta ac){
		VerticalLayout vlMain = new VerticalLayout();
		vlMain.setAlignItems(Alignment.CENTER);
		vlMain.setSpacing(false);
		vlMain.setWidth("min-width");

		H3 _ae1 = new H3(ac.getNumeroCuenta() + "\t" + ac.getSaldo());

		_ae1.addClickListener(e -> {
			acSelected = ac;
			updateAccountInfo();
		});

		vlMain.add(
				_ae1);

		return vlMain;

	}

	private Component CreateNotification(){
		VerticalLayout vlMain = new VerticalLayout();
		vlMain.setAlignItems(Alignment.CENTER);
		vlMain.setSpacing(false);
		vlMain.setWidth("min-width");

		Paragraph _nt1 = new Paragraph("Y la gente lo sabe");

		//_nt1.addClickListener();

		vlMain.add(_nt1);

		return vlMain;

	}

	/*private Component AccountHorizontal(Cuenta account){

		VerticalLayout vlMain = new VerticalLayout();
		vlMain.setAlignItems(Alignment.CENTER);
		vlMain.setSpacing(false);
		vlMain.setWidth("min-width");

		Paragraph _ac = new Paragraph(account.getNumeroCuenta());

		//_ac.addClickListener();

		vlMain.add(_ac);

		return vlMain;


	}*/

	//Función para actualizar la info de la cuenta que mostramos en el primer layout
	private void updateAccountInfo() {
		_acNumber.setText(acSelected.getNumeroCuenta());

		NumberFormat formatImport = NumberFormat.getCurrencyInstance();
		_acBalance.setText(formatImport.format(acSelected.getSaldo()));

	}

	/*@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if(_authenticatedUser.get().isPresent()) _cliente = _clienteService.findByUsuario(_authenticatedUser.get().get());
		else event.rerouteTo(LoginView.class);

	}*/

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if(_authenticatedUser.get().isPresent()){
			if(_authenticatedUser.get().get().getTipoUsuario() == TipoUsuario.Empleado) event.rerouteTo(DashBoardView.class);
		}
	}
}
