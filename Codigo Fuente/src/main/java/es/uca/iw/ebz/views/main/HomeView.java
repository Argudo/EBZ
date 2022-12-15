package es.uca.iw.ebz.views.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.theme.lumo.LumoUtility;
import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Cuenta.CuentaService;
import es.uca.iw.ebz.Movimiento.Movimiento;
import es.uca.iw.ebz.Movimiento.MovimientoService;
import es.uca.iw.ebz.tarjeta.TarjetaService;
import es.uca.iw.ebz.usuario.Usuario;
import es.uca.iw.ebz.usuario.UsuarioService;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.tarjeta.EnumTarjeta;
import es.uca.iw.ebz.tarjeta.Tarjeta;
import es.uca.iw.ebz.tarjeta.TipoTarjeta;
import es.uca.iw.ebz.usuario.cliente.ClienteRepository;
import es.uca.iw.ebz.usuario.cliente.ClienteService;
import es.uca.iw.ebz.views.main.component.TarjetaComponent;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import java.awt.*;
import java.text.NumberFormat;

@PageTitle("Home")
@Route(value = "home", layout = MainLayout.class)


public class HomeView extends VerticalLayout{
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


//La cuenta que usaremos para ir actualizando el primer layout.
static Cuenta acSelected = null;

private Cliente _cliente;

//Atributos del layout de la cuenta.
H2 _acNumber = new H2();
H3 _acBalance = new H3();


	public HomeView(MovimientoService _movimientoService, CuentaService _cuentaService,
					TarjetaService _tarjetaService, ClienteService _clienteService,
					UsuarioService _usuarioService) {

		//Services initialization section
		this._movimientoService = _movimientoService;
		this._cuentaService = _cuentaService;
		this._tarjetaService = _tarjetaService;
		this._clienteService = _clienteService;
		this._usuarioService = _usuarioService;

		Cookie myCookie = getCookieByName("user_id");

		UUID idUser = _usuarioService.findByUser("");

		//End services initialization section

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
		VerticalLayout vlAccount = new VerticalLayout();
		vlAccount.setWidth("70%");
		vlAccount.setPadding(false);
		vlAccount.setSpacing(false);
		vlAccount.setMargin(false);
		//End account information and buttons section


		//Username section
		Component userName = CreateUserNameBanner(_cliente.getnombre());
		//End username section

		//Account gallery section
		List<Cuenta> accountList = _cuentaService.findByCliente(_cliente);
		acSelected = accountList.get(0);
		updateAccountInfo();
		VerticalLayout vlShowAccount = new VerticalLayout();
		vlShowAccount.setAlignItems(Alignment.CENTER);
		vlShowAccount.setSpacing(false);
		vlShowAccount.setWidth("100%");

		vlShowAccount.add(
				_acNumber,
				_acBalance
		);

		//End account gallery section

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
		vlAccount.add(userName);
		vlAccount.add(vlShowAccount);
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

		List<Component> accountListComponent = null;
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

			Paragraph mvMessage = new Paragraph("No hay movimientos que mostrar");

		}else{
			int cont = 0;
			List<Component> mvComponentList = null;
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

	//Componente de prueba para mostrar la información de la cuenta.
	/*private Component ShowAccount(String acNumber, float acBalance) {
		VerticalLayout vlMain = new VerticalLayout();
		vlMain.setAlignItems(Alignment.CENTER);
		vlMain.setSpacing(false);
		vlMain.setWidth("100%");

		NumberFormat formatImport = NumberFormat.getCurrencyInstance();

		H2 _acNumber = new H2(acNumber);
		H3 _acBalance = new H3(formatImport.format(acBalance));
		vlMain.add(
				_acNumber,
				_acBalance
		);

		return vlMain;

	}*/

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

		Paragraph _ae1 = new Paragraph(ac.getNumeroCuenta() + "  " + ac.getSaldo());

		//_ae1.addClickListener();

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

	//Función para recibir la cookie con el id del usuario.
	private Cookie getCookieByName(String name) {
		// Fetch all cookies from the request
		Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();

		// Iterate to find cookie by its name
		for (Cookie cookie : cookies) {
			if (name.equals(cookie.getName())) {
				return cookie;
			}
		}

		return null;
	}
}
