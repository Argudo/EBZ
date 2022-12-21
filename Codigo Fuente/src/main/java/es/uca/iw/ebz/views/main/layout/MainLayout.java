package es.uca.iw.ebz.views.main.layout;

import javax.swing.text.html.ListView;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.RouterLink;
import es.uca.iw.ebz.views.main.HomeView;
import es.uca.iw.ebz.views.main.Security.AuthenticatedUser;
import es.uca.iw.ebz.views.main.TarjetaView;
import org.springframework.beans.factory.annotation.Autowired;

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
    	Icon icon = new Icon(VaadinIcon.SIGN_OUT);
    	btnSignOut.getElement().appendChild(icon.getElement());
		btnSignOut.addClickListener(e -> {
			_authenticatedUser.logout();
		});
        H1 logo = new H1("EBZ");
        logo.addClassNames("text-l", "m-m");
        hlContent.setAlignItems(FlexComponent.Alignment.CENTER);
        hlContent.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        hlContent.setWidthFull();
        hlContent.add(logo,
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
	    return new Tab[] { createTab("Inicio", HomeView.class),
	            createTab("Transferencias", HomeView.class),
	            createTab("Tarjetas", TarjetaView.class)};
	}

	private static Tab createTab(String text,
	        Class<? extends Component> navigationTarget) {
	    final Tab tab = new Tab();
	    tab.add(new RouterLink(text, navigationTarget));
	    ComponentUtil.setData(tab, Class.class, navigationTarget);
	    return tab;
	}
}
