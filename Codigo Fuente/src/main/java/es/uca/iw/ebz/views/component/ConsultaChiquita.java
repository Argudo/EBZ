package es.uca.iw.ebz.views.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import es.uca.iw.ebz.consulta.Consulta;
import es.uca.iw.ebz.consulta.ConsultaService;
import es.uca.iw.ebz.mensaje.Mensaje;
import es.uca.iw.ebz.mensaje.MensajeService;
import es.uca.iw.ebz.usuario.admin.AdminService;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteService;

@Tag("ConsultaChiquita")
public class ConsultaChiquita extends Component {

    private ConsultaService _consultaService;

    private MensajeService _mensajeService;

    private ClienteService _clienteService;

    private AdminService _adminService;

    private Cliente _cliente;

    private Grid<Consulta> gridQuery = new Grid<>(Consulta.class, false);

    public ConsultaChiquita(ConsultaService consultaService, MensajeService mensajeService,
                            ClienteService clienteService, AdminService adminService, Cliente cliente) {

        //Services initialization section

        _consultaService = consultaService;
        _mensajeService = mensajeService;
        _clienteService = clienteService;
        _adminService = adminService;
        _cliente = cliente;

        //End services initialization section

        VerticalLayout vlMain = new VerticalLayout();

        gridQuery.setWidthFull();
        gridQuery.addColumn(Consulta::getTitulo).setHeader(getTranslation("query.title")).setAutoWidth(true);
        gridQuery.addColumn(Consulta::getTipoEstadoString).setHeader(getTranslation("query.state")).setAutoWidth(true);
        gridQuery.addComponentColumn(consulta -> {
            Button btnQuery = new Button("Chat");
            ClienteConsultaDialog ccLog = new ClienteConsultaDialog(consulta, _cliente, _adminService, _clienteService, _mensajeService, _consultaService);
            btnQuery.addClickListener( e-> {
                ccLog.open();
            });
            return btnQuery;
        }).setHeader("Chat").setAutoWidth(true);

        gridQuery.setItems(_consultaService.findByClienteOrderByFechaDESC(_cliente.getUsuario()));

        vlMain.add(gridQuery);
        getElement().appendChild(vlMain.getElement());


    }

}
