package es.uca.iw.ebz.views.main;

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
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Cuenta.CuentaService;
import es.uca.iw.ebz.usuario.ContraseñaIncorrecta;
import es.uca.iw.ebz.usuario.Usuario;
import es.uca.iw.ebz.usuario.UsuarioNoEncontrado;
import es.uca.iw.ebz.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import java.security.NoSuchAlgorithmException;

@PageTitle("Inicio Sesión")
@Route(value = "aa")
@RouteAlias("aa")
public class MainView extends VerticalLayout {

    private TextField name;
    private Button sayHello;
	@Autowired
	private UsuarioService usuarioService;

    public MainView(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
    }

	//Componentes esteticos
		private VerticalLayout vlCuadroInicio = new VerticalLayout();
		private VerticalLayout vlImage = new VerticalLayout();
		private Image imgLogo = new Image("images/brand.png", "Logo");
		private Hr hrLogIn = new Hr();
	
		//Componentes campos usario y contraña
		private VerticalLayout vlMid = new VerticalLayout();
		private TextField tboxUser = new TextField("DNI");
		private PasswordField tboxPass = new PasswordField("Contraseña");
		
		//Componente boton de login
		private Button btnLogIn = new Button("Iniciar Sesión");

    public MainView() {

    	//setSizeFull();
    	//setMargin(false);
    	//setSpacing(false);
    	//setPadding(false);
    	setClassName("host");
    	//setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
    	//setAlignItems(FlexComponent.Alignment.CENTER);
    	
    	
    	vlCuadroInicio.setWidth("30vw");
    	vlCuadroInicio.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
    	vlCuadroInicio.setClassName("box");
    	vlCuadroInicio.setPadding(true);


    	imgLogo.setWidth("20%");
    	vlImage.setMargin(false);
    	vlImage.setPadding(false);
    	vlImage.setSpacing(false);
    	vlImage.add(imgLogo);
    	vlImage.setAlignItems(FlexComponent.Alignment.CENTER);
    	

    	tboxUser.setWidth("80%");
    	tboxPass.setWidth("80%");
    	
    	vlMid.add(tboxUser,
    			  tboxPass,
    			  btnLogIn
    			);
    	vlMid.setAlignItems(FlexComponent.Alignment.CENTER);
    	//setMargin(true);
    	//setPadding(true);
    	
    	vlCuadroInicio.add(
    			vlImage,
    			hrLogIn,
    			vlMid	
    	);
    	//add(vlCuadroInicio);
    	
    	
    	btnLogIn.addClickListener(event -> {
    		String sUsername = tboxUser.getValue();
    		String sPassword = tboxPass.getValue();
		});
    }
}
