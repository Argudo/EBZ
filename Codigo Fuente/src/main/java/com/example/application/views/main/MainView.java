package com.example.application.views.main;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Inicio Sesión")
@Route(value = "")

public class MainView extends VerticalLayout {

    private TextField name;
    private Button sayHello;

    public MainView() {
    	setHeight("100vh");
    	setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
    	setAlignItems(FlexComponent.Alignment.CENTER);
    	
    	VerticalLayout vlCuadroInicio = new VerticalLayout();
    	vlCuadroInicio.setWidth("30vw");
    	vlCuadroInicio.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
    	vlCuadroInicio.setClassName("box");
    	vlCuadroInicio.setPadding(true);


    	Image imgLogo = new Image("images/brand.png", "Logo");
    	imgLogo.setWidth("40%");
    	VerticalLayout vlImage = new VerticalLayout();
    	vlImage.setMargin(false);
    	vlImage.setPadding(false);
    	vlImage.setSpacing(false);
    	vlImage.add(imgLogo);
    	vlImage.setAlignItems(FlexComponent.Alignment.CENTER);
    	
    	Hr hrLogIn = new Hr();
    	Paragraph pUser = new Paragraph();
    	TextField tboxUser = new TextField("DNI");
    	tboxUser.setWidth("80%");
    	Paragraph pPass = new Paragraph();
    	PasswordField tboxPass = new PasswordField("Contraseña");
    	tboxPass.setWidth("80%");
    	
    	Button btnLogIn = new Button("Iniciar Sesión");
    	VerticalLayout vlMid = new VerticalLayout();
    	vlMid.add(tboxUser,
    			  tboxPass,
    			  btnLogIn
    			);
    	vlMid.setAlignItems(FlexComponent.Alignment.CENTER);
    	btnLogIn.addClickListener( click -> {
    		btnLogIn.getUI().ifPresent(ui ->
            ui.navigate("home"));
    	});
    	setMargin(true);
    	setPadding(true);
    	
    	vlCuadroInicio.add(
    			vlImage,
    			hrLogIn,
    			vlMid	
    	);
    	
    	add(vlCuadroInicio);
    }
}
