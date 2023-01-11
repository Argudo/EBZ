package es.uca.iw.ebz.views.layout;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.server.VaadinSession;
import es.uca.iw.ebz.views.*;
import es.uca.iw.ebz.views.Security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.RouterLink;



import java.util.List;
import java.util.Locale;


public class MainLayout extends AppLayout{
	@Autowired
	private AuthenticatedUser _authenticatedUser;


	public MainLayout() {
		CreateHeader();
		
		Tabs menu = CreateMenu();
		addToDrawer(CreateDrawerContent(menu));
	}
	
	
	private void CreateHeader() {
    	HorizontalLayout hLayout = new HorizontalLayout();
    	HorizontalLayout hlContent = new HorizontalLayout();
    	
    	Button btnSignOut = new Button();
		Button btnUser = new Button();
		Icon iconUser = new Icon(VaadinIcon.USER);
		btnUser.getElement().appendChild(iconUser.getElement());
    	Icon icon = new Icon(VaadinIcon.SIGN_OUT);
    	btnSignOut.getElement().appendChild(icon.getElement());
		btnSignOut.addClickListener(e -> {
			_authenticatedUser.logout();
		});
		btnUser.addClickListener(e -> {
			UI.getCurrent().navigate("perfil");
		});
        H1 logo = new H1("EBZ");
        logo.addClassNames("text-l", "m-m");
		logo.addClickListener(e -> {
			logo.getUI().ifPresent((ui -> ui.navigate("")));
		});
        hlContent.setAlignItems(FlexComponent.Alignment.CENTER);
        hlContent.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        hlContent.setWidthFull();

		Select<String> language = new Select<>();
		language.setItems(List.of("es", "en"));
		language.setValue(VaadinSession.getCurrent().getLocale().getLanguage());
		language.setWidth("5%");
		language.addValueChangeListener(e -> {
			VaadinSession.getCurrent().setLocale(Locale.forLanguageTag((language.getValue())));
			UI.getCurrent().getPage().reload();
		});

		Div dMain= new Div();
		dMain.setWidth("75%");
		
        hlContent.add(logo,
				dMain,
				language,
				btnUser,
        		btnSignOut);
    	
    	hLayout.setId("header");
    	hLayout.setWidthFull();
        hLayout.setSpacing(true);
        hLayout.setPadding(true);
        hLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        hLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        hLayout.setHeight("7vh");
        hLayout.expand(logo);
        hLayout.setWidth("100%");
        hLayout.addClassNames("py-0", "px-m");        
        hLayout.add(
        		new DrawerToggle(),
        		hlContent
        		);

        addToNavbar(hLayout);
    }
	
	private Component CreateDrawerContent(Tabs menu) {
	    VerticalLayout layout = new VerticalLayout();

	    // Configure styling for the drawer
	    layout.setSizeFull();
	    layout.setPadding(false);
	    layout.setSpacing(false);
	    layout.getThemeList().set("spacing-s", true);
	    layout.setAlignItems(FlexComponent.Alignment.STRETCH);

	    // Have a drawer header with an application logo
	    HorizontalLayout logoLayout = new HorizontalLayout();
	    logoLayout.setId("logo");
	    logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);

	    // Display the logo and the menu in the drawer
	    layout.add(logoLayout, menu);
	    return layout;
	}
	
	private Tabs CreateMenu() {
	    final Tabs tabs = new Tabs();
	    tabs.setOrientation(Tabs.Orientation.VERTICAL);
	    tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
	    tabs.setId("tabs");
	    tabs.add(createMenuItems());
	    return tabs;
	}

	private Component[] createMenuItems() {
	    return new Tab[] { createTab(getTranslation("mainLayout.home"), HomeView.class, new Icon(VaadinIcon.HOME)),
	            createTab(getTranslation("mainLayout.transfer"), TransferenciaView.class, new Icon(VaadinIcon.MONEY_EXCHANGE)),
	            createTab(getTranslation("mainLayout.cards"), TarjetaView.class, new Icon(VaadinIcon.CREDIT_CARD)),
				createTab(getTranslation("mainLayout.movement"), MovimientoView.class, new Icon(VaadinIcon.MONEY)),
				createTab(getTranslation("mainLayout.notices"), NoticiasView.class, new Icon(VaadinIcon.NEWSPAPER)),
				createTab(getTranslation("mainLayout.query"), ConsultaView.class, new Icon(VaadinIcon.ENVELOPE_O))};
	}

	private static Tab createTab(String text,
	        Class<? extends Component> navigationTarget, Icon icono) {
		final Tab tab = new Tab();
		HorizontalLayout hlTab = new HorizontalLayout();
		hlTab.add(icono, new RouterLink(text, navigationTarget));
		tab.add(hlTab);
		ComponentUtil.setData(tab, Class.class, navigationTarget);
		return tab;
	}
}
