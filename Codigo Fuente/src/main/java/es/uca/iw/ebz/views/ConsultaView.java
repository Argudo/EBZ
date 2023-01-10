package es.uca.iw.ebz.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.ebz.consulta.Consulta;
import es.uca.iw.ebz.consulta.ConsultaService;
import es.uca.iw.ebz.consulta.EnumEstado;
import es.uca.iw.ebz.consulta.TipoEstado;
import es.uca.iw.ebz.mensaje.Mensaje;
import es.uca.iw.ebz.mensaje.MensajeService;
import es.uca.iw.ebz.usuario.admin.AdminService;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteService;
import es.uca.iw.ebz.views.Security.AuthenticatedUser;
import es.uca.iw.ebz.views.component.ClienteConsultaDialog;
import es.uca.iw.ebz.views.layout.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.awt.*;
import java.util.Date;

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

    private Tab tabNuevo = new Tab(getTranslation("query.new"));

    private Tab tabConsultas = new Tab(getTranslation("query.see"));

    private TextField tfTitulo;

    private TextField tfDescripcion;

    private Button btnQuery;

    private Grid<Consulta> gridQuery = new Grid<>(Consulta.class,false);



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

        H1 hTitleNew = new H1(getTranslation("query.newquery"));
        hTitleNew.setClassName("title");
        vlTitleNew.add(hTitleNew);

        VerticalLayout vlTitleHistorial = new VerticalLayout();
        vlTitleHistorial.setWidth("70%");
        vlTitleHistorial.setSpacing(true);
        vlTitleHistorial.setPadding(true);
        vlTitleHistorial.setMargin(true);
        vlTitleHistorial.setClassName("box");

        H1 hTitleHistorial = new H1(getTranslation("query.history"));
        hTitleHistorial.setClassName("title");
        vlTitleHistorial.add(hTitleHistorial);
        //End title section

        //New query layout section
        FormLayout frmNewQuery = new FormLayout();
        frmNewQuery.setWidthFull();
        frmNewQuery.setResponsiveSteps(

                new FormLayout.ResponsiveStep("0",1)

        );

        tfTitulo = new TextField(getTranslation("query.title"));
        tfTitulo.setRequired(true);
        tfTitulo.setRequiredIndicatorVisible(true);
        tfTitulo.setMinLength(10);
        tfTitulo.setErrorMessage(getTranslation("query.titleob"));

        tfDescripcion = new TextField(getTranslation("notice.description"));
        tfDescripcion.setRequired(true);
        tfDescripcion.setRequiredIndicatorVisible(true);
        tfDescripcion.setMinLength(20);
        tfDescripcion.setErrorMessage(getTranslation("query.descob"));

        btnQuery = new Button(getTranslation("query.create"));

        btnQuery.addClickListener( ev -> {
            Consulta query = new Consulta(tfTitulo.getValue(),new Date(),new TipoEstado(EnumEstado.Pendiente), _cliente.getUsuario());
            query = _consultaService.Save(query);
            Mensaje msg = new Mensaje(new Date(), tfDescripcion.getValue(), _cliente.getUsuario(), query);
            msg = _mensajeService.Save(msg);
            query.setMensajes(msg);

            removeAll();
            tabs.setSelectedTab(tabConsultas);
            add(tabs, vlTitleHistorial);
        });

        frmNewQuery.add(
                tfTitulo,
                tfDescripcion,
                btnQuery);

        vlTitleNew.add(new Hr(), frmNewQuery);
        //End new query layout section

        //Query record section
        //Grid initialization section
        gridQuery.setWidthFull();
        gridQuery.addColumn(Consulta::getTitulo).setHeader(getTranslation("query.title")).setAutoWidth(true);
        gridQuery.addColumn(Consulta::getFechaCreacion).setHeader(getTranslation("query.date")).setSortable(true).setAutoWidth(true);
        gridQuery.addComponentColumn(consulta -> {
            Button btnQuery = new Button("Chat");
            ClienteConsultaDialog ccLog = new ClienteConsultaDialog(consulta, _cliente, _adminService, _clienteService, _mensajeService, _consultaService);
            btnQuery.addClickListener( e -> {
                ccLog.open();
            });

            return btnQuery;
        }).setHeader("Chat").setAutoWidth(true);

        gridQuery.setItems(_consultaService.findByCliente(_cliente.getUsuario()));

        //End grid initialization section

        vlTitleHistorial.add(new Hr(), gridQuery);
        //End query record section


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
                gridQuery.setItems(_consultaService.findByCliente(_cliente.getUsuario()));
                gridQuery.getDataProvider().refreshAll();
                add(tabs, vlTitleHistorial);
            }
        });
        //End tab section

        add(tabs, vlTitleHistorial);

    }

}
