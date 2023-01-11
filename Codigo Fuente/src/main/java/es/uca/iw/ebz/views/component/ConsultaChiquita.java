package es.uca.iw.ebz.views.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import es.uca.iw.ebz.consulta.Consulta;
import es.uca.iw.ebz.consulta.ConsultaService;
import es.uca.iw.ebz.mensaje.Mensaje;
import es.uca.iw.ebz.mensaje.MensajeService;
import es.uca.iw.ebz.usuario.admin.AdminService;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteService;

public class ConsultaChiquita extends Grid<Consulta> {

    private ConsultaService _consultaService;

    private MensajeService _mensajeService;

    private ClienteService _clienteService;

    private AdminService _adminService;

    private Cliente _cliente;

    public ConsultaChiquita(ConsultaService consultaService, MensajeService mensajeService,
                            ClienteService clienteService, AdminService adminService, Cliente cliente) {

        //Services initialization section

        _consultaService = consultaService;
        _mensajeService = mensajeService;
        _clienteService = clienteService;
        _adminService = adminService;
        _cliente = cliente;

        //End services initialization section

        setWidthFull();
        addColumn(Consulta::getTitulo).setHeader(getTranslation("query.title")).setAutoWidth(true);
        addColumn(Consulta::getTipoEstadoString).setHeader(getTranslation("query.state")).setAutoWidth(true).setTextAlign(ColumnTextAlign.CENTER);
        addComponentColumn(consulta -> {
            Button btnQuery = new Button(VaadinIcon.ENVELOPE_O.create());
            ClienteConsultaDialog ccLog = new ClienteConsultaDialog(consulta, _cliente, _adminService, _clienteService, _mensajeService, _consultaService);
            btnQuery.addClickListener( e-> {
                ccLog.open();
            });
            return btnQuery;
        }).setHeader("Chat").setAutoWidth(true).setTextAlign(ColumnTextAlign.CENTER);

        setItems(_consultaService.findByClienteOrderByFechaDESC(_cliente.getUsuario()));



    }

}
