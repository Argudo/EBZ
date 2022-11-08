package com.example.application.views.main;

import com.vaadin.flow.component.Component;
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
@Route(value = "home")

public class HomeView extends VerticalLayout{
	 private TextField name;
	    private Button sayHello;

	    public HomeView() {
	        setMargin(false);
	        setPadding(false);
	        setSpacing(false);

	        Component cHeader = CreateHeader();
	        
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
	        Component cTarjeta = CreateFunctionality("Tarjetas", "icons/tarjetas.svg", "Crédito, débito y prepago");
	        Component cEstadisticas = CreateFunctionality("Estadísticas", "icons/estadisticas.svg", "Ingresos, gastos y cuentas");
	        Component cDomiciliacion = CreateFunctionality("Domiciliación", "icons/domicialicion.svg", "Información sobre tus gastos periódicos");
	        Component cTransferencias = CreateFunctionality("Transferencias", "icons/transferencias.svg", "Reciba y ejecute transferencias");
	        flFunctionalities.add(
	        		cTarjeta,
	        		cEstadisticas,
	        		cDomiciliacion,
	        		cTransferencias
	        		);
	        //End functionalities section
	        
	        vlMain.add(imgBanner);
	        vlMain.add(flFunctionalities);
	        
	        add(cHeader);
	        add(vlMain);
	       
	    }
	    
	    private static Component CreateHeader() {
	    	HorizontalLayout hLayout = new HorizontalLayout();
	    	HorizontalLayout hlBrand = new HorizontalLayout();
	    	HorizontalLayout hlLogIn = new HorizontalLayout();
	    	
	    	
	    	hLayout.setId("header");
	    	hLayout.setWidthFull();
	    	hLayout.getThemeList().set("dark", true);
	        hLayout.setSpacing(true);
	        hLayout.setPadding(true);
	        hLayout.setAlignItems(FlexComponent.Alignment.CENTER);
	        hLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
	        hLayout.setHeight("10vh");
	        hLayout.setClassName("navBarColor");
	    	
	        Icon vaadinIcon = new Icon(VaadinIcon.PHONE);
	        Image imgLogo = new Image("images/brand.png", "Logo");
	        imgLogo.setMaxHeight("10vh");
	        imgLogo.setWidth("18vh");
	        Button btnLogIn = new Button("Iniciar Sesión");
	        
	        hlBrand.add(imgLogo);
	        hlLogIn.add(btnLogIn);
	        
	        hLayout.add(
	        		hlBrand,
	        		hlLogIn
	        		);
	        
	        hlBrand.setAlignItems(FlexComponent.Alignment.CENTER);
	        hlLogIn.setAlignItems(FlexComponent.Alignment.CENTER);
	        
	        return hLayout;
	    }
	    
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
