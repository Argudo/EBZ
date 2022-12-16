package es.uca.iw.ebz.views.main;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import es.uca.iw.ebz.views.main.layout.MainLayout;


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
	        Image imgBanner = new Image("images/banner2.jpg", "Banner");
	        imgBanner.setWidth("100%");
	        imgBanner.setHeight("40vh");
	        imgBanner.setClassName("banner");
	        //End Banner section
	        
	        //Functionalities section
	        FlexLayout flFunctionalities = new FlexLayout();
	        flFunctionalities.setWidthFull();
	        flFunctionalities.setFlexDirection(FlexDirection.ROW);
	        flFunctionalities.setFlexWrap(FlexWrap.WRAP);
	        flFunctionalities.setJustifyContentMode(FlexComponent.JustifyContentMode.EVENLY);
			Button btnCard = new Button ("Tarjetas");
			Button btnStats = new Button ("Estadísticas");
			Button btnDomiciliation = new Button ("Domiciliaciones");
			Button btnTransfer = new Button ("Tranferencias");

	        //Component cTarjeta = CreateFunctionality("Tarjetas", "icons/tarjetas.svg", "Crédito, débito y prepago");
			//Component cEstadisticas = CreateFunctionality("Estadísticas", "icons/estadisticas.svg", "Ingresos, gastos y cuentas");
	        //Component cDomiciliacion = CreateFunctionality("Domiciliación", "icons/domicialicion.svg", "Información sobre tus gastos periódicos");
	        //Component cTransferencias = CreateFunctionality("Transferencias", "icons/transferencias.svg", "Reciba y ejecute transferencias");
	        flFunctionalities.add(
	        		btnCard,
					btnStats,
					btnDomiciliation,
					btnTransfer
	        		);
	        //End functionalities section
	        
	        vlMain.add(imgBanner);
	        vlMain.add(flFunctionalities);
	        
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
	    
	    private Component CreateFunctionality(String sName, String sURL, String sDescription) {
	    	VerticalLayout vlMain = new VerticalLayout();
	    	vlMain.setAlignItems(FlexComponent.Alignment.CENTER);
	    	vlMain.setSpacing(false);
	    	vlMain.setWidth("min-width");
	    	
	    	Image imgIcon = new Image(sURL, "Icono " + sName);
	    	H4 hTitle = new H4(sName);
	    	Paragraph pDescription = new Paragraph(sDescription);
	    	
	    	
	    	vlMain.add(
	    			imgIcon,
	    			hTitle,
	    			pDescription
	    			);
	    	
	    	return vlMain;
	    }

}
