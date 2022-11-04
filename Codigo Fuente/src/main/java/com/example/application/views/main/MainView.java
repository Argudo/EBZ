package com.example.application.views.main;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Main")
@Route(value = "")
public class MainView extends VerticalLayout {

    private TextField name;
    private Button sayHello;

    public MainView() {
        setMargin(false);
        setPadding(false);

        Component cHeader = CreateHeader();
        
        VerticalLayout vlMain = new VerticalLayout();
        vlMain.setWidthFull();
        vlMain.setPadding(false);
        vlMain.setMargin(false);
        
        Image imgBanner = new Image("images/banner.jpg", "Banner");
        imgBanner.setWidth("100%");
        imgBanner.setHeight("70vh");
        imgBanner.setClassName("opacity");
        
        vlMain.add(imgBanner);
        add(cHeader);
        add(vlMain);
       
    }
    
    private Component CreateHeader() {
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
}