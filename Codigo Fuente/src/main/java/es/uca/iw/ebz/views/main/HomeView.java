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
import com.vaadin.flow.theme.lumo.LumoUtility;
import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Movimiento.MovimientoService;
import es.uca.iw.ebz.cliente.Cliente;
import es.uca.iw.ebz.tarjeta.EnumTarjeta;
import es.uca.iw.ebz.tarjeta.Tarjeta;
import es.uca.iw.ebz.tarjeta.TipoTarjeta;
import es.uca.iw.ebz.views.main.component.TarjetaComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.text.NumberFormat;

@PageTitle("Home")
@Route(value = "home", layout = MainLayout.class)


public class HomeView extends VerticalLayout{
@Autowired
private MovimientoService _movimientoService;

//La cuenta que usaremos para ir actualizando el primer layout.
static Cuenta mainAccount = null;

private Cliente _cliente;

	public HomeView(MovimientoService _movimientoService) {

		//Services initialization section
		this._movimientoService = _movimientoService;

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
		hlMain.setHeight("24vw");
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
		Component userName = CreateUserNameBanner("Ángel Subiela");
		//End username section

		//Account gallery section
		//Title accountNumber = new Title("Título de prueba");
		Component acGallery = ShowAccount("XXXX XXXX XXXX XXXX", (float) 100000.45);
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
		vlAccount.add(acGallery);
		vlAccount.add(flAccountButtons);
		//End of including...

		//Account list layout section

		Scroller accountScroller = new Scroller();
		accountScroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
		accountScroller.setWidth("28%");
		accountScroller.setHeight("95%");


		VerticalLayout vlAccountList = new VerticalLayout();
		vlAccountList.setWidthFull();
		vlAccountList.setPadding(false);
		vlAccountList.setMargin(false);
		vlAccountList.setSpacing(false);
		vlAccountList.setAlignItems(Alignment.CENTER);

		List<Component> accountList = Arrays.asList(CreateAccountListElement(), CreateAccountListElement(), CreateAccountListElement(), CreateAccountListElement(), CreateAccountListElement(), CreateAccountListElement(), CreateAccountListElement(), CreateAccountListElement(), CreateAccountListElement(), CreateAccountListElement());
		for(Component c: accountList){
			vlAccountList.add(c);
		}

		//List<Cuenta> lAccount

		//mainAccount = lAccount.get(0);

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

		Component cMovement1 = CreateMovement();
		Component cMovement2 = CreateMovement();
		Component cMovement3 = CreateMovement();

		Component cNotification1 = CreateNotification();
		Component cNotification2 = CreateNotification();
		Component cNotification3 = CreateNotification();

		flAccountMovements.add(
				cMovement1,
				cMovement2,
				cMovement3);

		flAccountNotifications.add(
				cNotification1,
				cNotification2,
				cNotification3);

		hlAccountMove.add(
				flAccountMovements,
				flAccountNotifications);

		//End movements and notifications section

		//Credit cards section

		VerticalLayout vlTarjeta = new VerticalLayout();
		vlTarjeta.setWidth("80vw");
		vlTarjeta.setPadding(true);
		vlTarjeta.setMargin(true);
		vlTarjeta.setClassName("box");

		Scroller scrollTarjeta = new Scroller();
		scrollTarjeta.setScrollDirection(Scroller.ScrollDirection.HORIZONTAL);
		scrollTarjeta.setWidth("78vw");
		scrollTarjeta.setHeight("100%");

		HorizontalLayout hlTarjeta = new HorizontalLayout();
		hlTarjeta.setWidthFull();
		hlTarjeta.setHeight("100%");
		hlTarjeta.setPadding(true);
		hlTarjeta.setMargin(true);

		//Creamos lista de tarjetas, lista de tarjetaComponent y metemos los elementos a través de un foreach
		TipoTarjeta tpTarjeta = new TipoTarjeta(EnumTarjeta.Debito);
		Tarjeta tarjeta = new Tarjeta(5000, tpTarjeta);
		Tarjeta tarjeta2 = new Tarjeta(7000, tpTarjeta);
		java.util.List<Tarjeta> aTarjetas = Arrays.asList(tarjeta2, tarjeta, tarjeta, tarjeta2, tarjeta, tarjeta2, tarjeta);
		List<TarjetaComponent> aTarjetasComponent = new ArrayList<TarjetaComponent>();

		for(Tarjeta t: aTarjetas){
			aTarjetasComponent.add(new TarjetaComponent(t));
		}

		//Falta clickListener para las tarjetas con su vista detallada ¿?
		for(TarjetaComponent tc: aTarjetasComponent) {
			hlTarjeta.add(tc);
		}

		H1 tarjetaTitle = new H1("Tarjetas");
		tarjetaTitle.setClassName("title");

		scrollTarjeta.setContent(hlTarjeta);
		vlTarjeta.add(tarjetaTitle, scrollTarjeta);
		//End credit cards section

		add(hlMain);
		add(hlAccountMove);
		add(vlTarjeta);

	}

/*Button btnLogIn = new Button("Iniciar Sesión");
VerticalLayout vlMid = new VerticalLayout();
	vlMid.add(tboxUser,
tboxPass,
btnLogIn
			);
	vlMid.setAlignItems(FlexComponent.Alignment.CENTER);
	btnLogIn.addClickListener( click -> { btnLogIn.getUI().ifPresent(ui ->ui.navigate("home")); });
			*/

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
	private Component ShowAccount(String acNumber, float acBalance) {
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

	private Component CreateMovement(){
		VerticalLayout vlMain = new VerticalLayout();
		vlMain.setAlignItems(Alignment.CENTER);
		vlMain.setSpacing(false);
		vlMain.setWidth("min-width");

		Paragraph _mv1 = new Paragraph("Un farzerío total y absoluto");

		//_mv1.addClickListener();

		vlMain.add(
				_mv1);

		return vlMain;

	}

	private Component CreateAccountListElement(){
		VerticalLayout vlMain = new VerticalLayout();
		vlMain.setAlignItems(Alignment.CENTER);
		vlMain.setSpacing(false);
		vlMain.setWidth("min-width");

		Paragraph _ae1 = new Paragraph("XXXX XXXX XXXX XXXX   100.000,45€");

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

	private Component AccountHorizontal(Cuenta account){

		VerticalLayout vlMain = new VerticalLayout();
		vlMain.setAlignItems(Alignment.CENTER);
		vlMain.setSpacing(false);
		vlMain.setWidth("min-width");

		Paragraph _ac = new Paragraph(account.getNumeroCuenta());

		//_ac.addClickListener();

		vlMain.add(_ac);

		return vlMain;


	}

}
