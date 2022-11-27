package es.uca.iw.ebz.views.main;

import java.util.concurrent.TimeUnit;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import de.mekaso.vaadin.addons.Carousel;

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
        
        VerticalLayout divTarjeta = new VerticalLayout();
        divTarjeta.add(new H5("EBZ"),
        		new H6("4161   8000   XXXX   XXXX"),
        		new Paragraph("04/23"));
        divTarjeta.setClassName("tarjeta-small");
        divTarjeta.setWidth("250px");
        divTarjeta.setHeight("150px");
        divTarjeta.setPadding(false);
        divTarjeta.setAlignItems(FlexComponent.Alignment.CENTER);
        divTarjeta.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        vlMain.add(divTarjeta);
        
        VerticalLayout divTarjetaMid = new VerticalLayout();
        divTarjetaMid.add(new H4("EBZ"),
        			   new H5("4161   8000   XXXX   XXXX"),
        			   new H6("04/23"));
        divTarjetaMid.setClassName("tarjeta-mid");
        divTarjetaMid.setWidth("300px");
        divTarjetaMid.setHeight("200px");
        divTarjetaMid.setPadding(false);
        divTarjetaMid.setAlignItems(FlexComponent.Alignment.CENTER);
        divTarjetaMid.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        vlMain.add(divTarjetaMid);
        
        
        add(vlMain);
       
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
