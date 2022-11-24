package es.uca.iw.ebz.views.main;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
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
			//Button btnCard = new Button ("Tarjetas");
			//Button btnStats = new Button ("Estadísticas");
			//Button btnDomiciliation = new Button ("Domiciliaciones");
			//Button btnTransfer = new Button ("Tranferencias");

	        Component cTarjeta = CreateFunctionality("Tarjetas", VaadinIcon.CREDIT_CARD);
			Component cEstadisticas = CreateFunctionality("Estadísticas", VaadinIcon.BAR_CHART_H);
	        Component cDomiciliacion = CreateFunctionality("Movimientos", VaadinIcon.EXCHANGE);
	        Component cTransferencias = CreateFunctionality("Transferencias", VaadinIcon.MONEY_EXCHANGE);
	        flFunctionalities.add(
					cTarjeta,
					cEstadisticas,
					cDomiciliacion,
					cTransferencias
	        		//btnCard,
					//btnStats,
					//btnDomiciliation,
					//btnTransfer
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

}
