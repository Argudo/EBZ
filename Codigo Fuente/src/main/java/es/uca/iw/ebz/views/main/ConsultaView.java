package es.uca.iw.ebz.views.main;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.ebz.consulta.Consulta;
import es.uca.iw.ebz.consulta.ConsultaService;
import es.uca.iw.ebz.mensaje.MensajeService;
import es.uca.iw.ebz.usuario.admin.AdminService;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteService;
import es.uca.iw.ebz.views.main.Security.AuthenticatedUser;
import es.uca.iw.ebz.views.main.layout.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;

@PageTitle("Consultas")
@Route(value = "consultas", layout = MainLayout.class)
@RolesAllowed({ "Cliente" })
public class ConsultaView extends VerticalLayout {

    @Autowired
    private ConsultaService _consultaService;

    @Autowired
    private ClienteService _clienteService;

    @Autowired
    private MensajeService _mensajeService;

    @Autowired
    private AdminService _adminService;

    @Autowired
    private AuthenticatedUser _authenticatedUser;

    private Cliente _cliente;

    private Tabs tabs = new Tabs();

    private Tab tabNuevo = new Tab("Nueva consulta");

    private Tab tabConsultas = new Tab("Ver consultas");



    public ConsultaView(ConsultaService consultaService, ClienteService clienteService,
                        MensajeService mensajeService, AdminService adminService,
                        AuthenticatedUser authenticatedUser){

        //Services initialization section
        _consultaService = consultaService;
        _clienteService = clienteService;
        _authenticatedUser = authenticatedUser;
        _mensajeService = mensajeService;
        _adminService = adminService;
        //End services initialization section

        setMargin(false);
        setPadding(false);
        setSpacing(true);
        setWidthFull();
        setAlignItems(FlexComponent.Alignment.CENTER);

        //Client asignation
        _cliente = _clienteService.findByUsuario(_authenticatedUser.get().get());

        //Title section
        VerticalLayout vlTitleNew = new VerticalLayout();
        vlTitleNew.setWidth("70%");
        vlTitleNew.setSpacing(true);
        vlTitleNew.setPadding(true);
        vlTitleNew.setMargin(true);
        vlTitleNew.setClassName("box");

        H1 hTitleNew = new H1("| Nueva consulta");
        hTitleNew.setClassName("title");
        vlTitleNew.add(hTitleNew);

        VerticalLayout vlTitleHistorial = new VerticalLayout();
        vlTitleHistorial.setWidth("70%");
        vlTitleHistorial.setSpacing(true);
        vlTitleHistorial.setPadding(true);
        vlTitleHistorial.setMargin(true);
        vlTitleHistorial.setClassName("box");

        H1 hTitleHistorial = new H1("| Historial de consultas");
        hTitleHistorial.setClassName("title");
        vlTitleHistorial.add(hTitleHistorial);
        //End title section

        //New query layout section
        FormLayout frmNewQuery = new FormLayout();
        frmNewQuery.setWidth("70vw");

        vlTitleNew.add(new Hr(), frmNewQuery);
        //End new query layout section

        //Tab section
        tabs.add(tabConsultas, tabNuevo);

        tabs.setWidthFull();
        tabs.setSelectedTab(tabConsultas);
        tabs.addThemeVariants(TabsVariant.LUMO_CENTERED);
        tabs.addSelectedChangeListener( e -> {
            removeAll();
            if(tabNuevo.isSelected()){
                add(tabs, vlTitleNew);
            }else{
                add(tabs, vlTitleHistorial);
            }
        });
        //End tab section

        add(tabs, vlTitleHistorial);

    }

}
