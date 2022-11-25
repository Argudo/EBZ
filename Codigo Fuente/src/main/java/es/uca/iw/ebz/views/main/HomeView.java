package es.uca.iw.ebz.views.main;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
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
	        setSpacing(false);
	        
	        VerticalLayout vlMain = new VerticalLayout();
	        vlMain.setWidthFull();
	        vlMain.setPadding(false);
	        vlMain.setMargin(false);

	        
	        //Banner section
	        //End Banner section

			//Username section
			Component userName = CreateUserNameBanner("Nombre y apellidos");
			//End username section

			//Account gallery section
			//Title accountNumber = new Title("Título de prueba");
			Component acGallery = ShowAccount("XXXX XXXX XXXX XXXX","100.000,45€");
			//End account gallery section
	        
	        //Functionalities section
	        FlexLayout flAccount = new FlexLayout();
	        flAccount.setWidthFull();
	        flAccount.setFlexDirection(FlexDirection.ROW);
	        flAccount.setFlexWrap(FlexWrap.WRAP);
	        flAccount.setJustifyContentMode(JustifyContentMode.EVENLY);

	        Component cTarjeta = CreateFunctionality("Tarjetas", VaadinIcon.CREDIT_CARD);
			Component cEstadisticas = CreateFunctionality("Estadísticas", VaadinIcon.BAR_CHART_H);
	        Component cMovimientos = CreateFunctionality("Movimientos", VaadinIcon.EXCHANGE);
	        Component cTransferencias = CreateFunctionality("Transferencias", VaadinIcon.MONEY_EXCHANGE);
	        flAccount.add(
					cTarjeta,
					cEstadisticas,
					cMovimientos,
					cTransferencias
	        		);
	        //End functionalities section

			FlexLayout fl;

			vlMain.add(userName);
			vlMain.add(acGallery);
	        vlMain.add(flAccount);
	        
	        add(vlMain);
	       
	    }

	/*Button btnLogIn = new Button("Iniciar Sesión");
	VerticalLayout vlMid = new VerticalLayout();
    	vlMid.add(tboxUser,
	tboxPass,
	btnLogIn
    			);
    	vlMid.setAlignItems(FlexComponent.Alignment.CENTER);
    	btnLogIn.addClickListener( click -> {
		btnLogIn.getUI().ifPresent(ui ->
				ui.navigate("home"));
	});*/
	    
	    private Component CreateFunctionality(String sName, VaadinIcon vI) {
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
		private Component ShowAccount(String acNumber, String acBalance) {
			VerticalLayout vlMain = new VerticalLayout();
			vlMain.setAlignItems(Alignment.CENTER);
			vlMain.setSpacing(false);
			vlMain.setWidth("100%");

			H2 _acNumber = new H2(acNumber);
			H3 _acBalance = new H3(acBalance);
			vlMain.add(
					_acNumber,
					_acBalance
			);

			return vlMain;

		}

		//Componente para mostrar el nombre del usuario en la vista principal.
		private Component CreateUserNameBanner(String userName){
			VerticalLayout vlMain = new VerticalLayout();
			vlMain.setAlignItems(Alignment.CENTER);
			vlMain.setSpacing(false);
			vlMain.setWidth("50%");

			H1 _userName = new H1("Bienvenido, " + userName);

			vlMain.add(_userName);

			return vlMain;

		}

}
