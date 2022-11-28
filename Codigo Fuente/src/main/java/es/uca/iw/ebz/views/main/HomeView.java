package es.uca.iw.ebz.views.main;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.awt.*;

@PageTitle("Home")
@Route(value = "home", layout = MainLayout.class)

public class HomeView extends VerticalLayout{
	 private TextField name;
	    private Button sayHello;

	    public HomeView() {
	        setMargin(false);
	        setPadding(false);
	        setSpacing(true);
			setWidthFull();
			setAlignItems(FlexComponent.Alignment.CENTER);

	        VerticalLayout vlMain = new VerticalLayout();
	        vlMain.setWidth("80vw");
	        vlMain.setPadding(false);
			vlMain.setSpacing(false);
	        vlMain.setMargin(true);
			vlMain.setClassName("box");

	        
	        //Banner section
	        //End Banner section

			//Username section
			Component userName = CreateUserNameBanner("Faliyo de San Roque");
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
			vlMain.add(userName);
			vlMain.add(acGallery);
			vlMain.add(flAccountButtons);

			//Movements and notifications section
			HorizontalLayout hlAccountMove = new HorizontalLayout();
			hlAccountMove.setWidthFull();
			hlAccountMove.setSpacing(true);
			hlAccountMove.setPadding(false);
			hlAccountMove.setMargin(true);
			hlAccountMove.setWidth("80vw");

			//Tengo que crear dos layouts verticales, uno con los primeros movimientos y otro con las primeras notificaciones.

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
	        
	        add(vlMain);
			add(hlAccountMove);

	       
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

			String _sAux = acBalance + "€";

			H2 _acNumber = new H2(acNumber);
			H3 _acBalance = new H3(_sAux);
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
			vlMain.setWidth("50%");

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

		private Component CreateNotification(){
			VerticalLayout vlMain = new VerticalLayout();
			vlMain.setAlignItems(Alignment.CENTER);
			vlMain.setSpacing(false);
			vlMain.setWidth("min-width");

			Paragraph _mv1 = new Paragraph("Y la gente lo sabe");

			vlMain.add(_mv1);

			return vlMain;


		}

}
