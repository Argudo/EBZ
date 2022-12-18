package es.uca.iw.ebz.views.main.layout;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.RouterLink;
import es.uca.iw.ebz.views.main.*;


public class AdminLayout  extends AppLayout{
    public AdminLayout() {
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
        return new Tab[] { 
    		createTab("Usuario", DashBoardUserView.class, new Icon(VaadinIcon.USER_CARD)),
            createTab("Cuentas", DashBoardCuentasView.class, new Icon(VaadinIcon.MONEY_EXCHANGE)),
            createTab("Noticias", DashBoardNoticasView.class, new Icon(VaadinIcon.NEWSPAPER)),
            createTab("Consultas", DashBoardConsultasView.class, new Icon(VaadinIcon.ENVELOPE_O)),
    		createTab("Tarjetas", DashBoardTarjetasView.class, new Icon(VaadinIcon.CREDIT_CARD))
        };
    }

    private static Tab createTab(String text, Class<? extends Component> navigationTarget, Icon icono) {
        final Tab tab = new Tab();
        HorizontalLayout hlTab = new HorizontalLayout();
        hlTab.add(icono, new RouterLink(text, navigationTarget));
        tab.add(hlTab);	
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }
}
