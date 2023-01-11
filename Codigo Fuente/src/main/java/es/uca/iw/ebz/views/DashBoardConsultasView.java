package es.uca.iw.ebz.views;

import com.mysql.cj.xdevapi.Client;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Cuenta.CuentaService;
import es.uca.iw.ebz.Movimiento.DatosMovimiento;
import es.uca.iw.ebz.consulta.Consulta;
import es.uca.iw.ebz.consulta.ConsultaService;
import es.uca.iw.ebz.consulta.EnumEstado;
import es.uca.iw.ebz.consulta.TipoEstado;
import es.uca.iw.ebz.mensaje.MensajeService;
import es.uca.iw.ebz.usuario.admin.Admin;
import es.uca.iw.ebz.usuario.admin.AdminService;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteService;
import es.uca.iw.ebz.views.Security.AuthenticatedUser;
import es.uca.iw.ebz.views.component.AdminConsultaDialog;
import es.uca.iw.ebz.views.component.ClienteConsultaDialog;
import es.uca.iw.ebz.views.component.UpdateEvent;
import es.uca.iw.ebz.views.component.UpdateQueryEvent;
import es.uca.iw.ebz.views.layout.AdminLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@RolesAllowed({ "Empleado"})
@PageTitle("Dashboard/consultas")
@Route(value = "Dashboard/consultas", layout = AdminLayout.class)
public class DashBoardConsultasView extends HorizontalLayout {

    @Autowired
    private ConsultaService _consultaService;

    @Autowired
    private ClienteService _clienteService;

    @Autowired
    private MensajeService _mensajeService;

    @Autowired
    private AdminService _adminService;

    @Autowired
    private CuentaService _cuentaService;

    @Autowired
    private AuthenticatedUser _authenticatedUser;

    private Admin _admin;

    private H1 hGrid = new H1(getTranslation("dquery.home"));

    private H2 hInfo = new H2(getTranslation("dashboard.search"));

    private HorizontalLayout hlBuscador = new HorizontalLayout();

    private TextField tfDNI = new TextField();

    private Button btnBuscar = new Button();

    private Boolean estadoBusqueda = false;

    private Paragraph pDNI = new Paragraph("DNI");

    private Grid<Consulta> gridConsulta = new Grid<>(Consulta.class,false);

    private VerticalLayout vlGrid = new VerticalLayout();

    private VerticalLayout vlInfo = new VerticalLayout();

    private VerticalLayout vlSeparator = new VerticalLayout();

    private AdminConsultaDialog acLog;

    public DashBoardConsultasView(ConsultaService consultaService, ClienteService clienteService,
                                  MensajeService mensajeService, AdminService adminService,
                                  CuentaService cuentaService, AuthenticatedUser authenticatedUser) {

        //Services initialization section
        _consultaService = consultaService;
        _clienteService = clienteService;
        _authenticatedUser = authenticatedUser;
        _mensajeService = mensajeService;
        _adminService = adminService;
        _cuentaService = cuentaService;
        //End services initialization section

        //Admin asignation
        _admin = _adminService.findByUsuario(_authenticatedUser.get().get());

        //Layouts configuration section
        hGrid.setClassName("title");
        hInfo.setClassName("title");

        vlGrid.setWidth("70%");
        vlSeparator.setWidth("2px");
        vlInfo.setWidth("30%");

        vlSeparator.getStyle().set("background-color", "var(--lumo-contrast-10pct)");
        vlSeparator.getStyle().set("padding", "0");

        vlGrid.add(hGrid,
                new Hr(),
                gridConsulta);

        vlInfo.setAlignItems(FlexComponent.Alignment.CENTER);
        hlBuscador.setAlignItems(FlexComponent.Alignment.END);
        pDNI.getStyle().set("fontWeight", "600");
        btnBuscar.getElement().appendChild(new Icon("lumo", "search").getElement());
        //End layouts configuration section

        //Grid configuration section
        gridConsulta.addColumn(Consulta::getTitulo).setHeader(getTranslation("query.title")).setAutoWidth(true).setTextAlign(ColumnTextAlign.CENTER);
        gridConsulta.addColumn(Consulta::getFechaCreacion).setHeader(getTranslation("query.date")).setSortable(true).setAutoWidth(true).setTextAlign(ColumnTextAlign.CENTER);
        gridConsulta.addColumn(Consulta::getTipoEstadoString).setHeader(getTranslation("query.state")).setAutoWidth(true).setSortable(true).setTextAlign(ColumnTextAlign.CENTER);
        gridConsulta.addComponentColumn(consulta -> {
            Button btnQuery = new Button("Chat");
            acLog = new AdminConsultaDialog(consulta, _admin, _adminService, _clienteService, _mensajeService, _consultaService);
            acLog.addUpdateListener( e-> {
                updateUI();
            });
            btnQuery.addClickListener( e -> {
                acLog.open();
            });

            return btnQuery;
        }).setHeader("Chat").setAutoWidth(true).setTextAlign(ColumnTextAlign.CENTER);

        gridConsulta.addComponentColumn(consulta -> {
           Button btnClose = new Button(getTranslation("button.closequery"));
           btnClose.addClickListener( e-> {
              consulta.set_tipoEstado(new TipoEstado(EnumEstado.Cerrado));
              _consultaService.Save(consulta);
              updateUI();
           });
           return btnClose;
        }).setHeader(getTranslation("button.closequery")).setAutoWidth(true).setTextAlign(ColumnTextAlign.CENTER);

        GridListDataView<Consulta> dataView = gridConsulta.setItems(_consultaService.findAll());

        tfDNI.addValueChangeListener( e -> dataView.refreshAll());
        tfDNI.setValueChangeMode(ValueChangeMode.EAGER);

        dataView.addFilter(consulta -> {
            String searchTerm = tfDNI.getValue().trim();

            if (searchTerm.isEmpty()) return true;

            boolean matchesDNI = consulta.getCliente().getDNI().contains(searchTerm);

            return matchesDNI;
        });
        //End grid configuration section



        //Finder layout section

        hlBuscador.add(pDNI, tfDNI);
        vlInfo.add(hInfo,
                new Hr(),
                hlBuscador);
        //End finder layout section

        setHeight("100%");
        add(vlGrid,
                vlSeparator,
                vlInfo);

    }

    private void updateUI(){
        List<Consulta> aConsulta = new ArrayList<>();
        aConsulta = _consultaService.findAll();
        gridConsulta.setItems(aConsulta);

        gridConsulta.getDataProvider().refreshAll();
    }

}
