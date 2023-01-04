package es.uca.iw.ebz.views.main;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.ebz.consulta.Consulta;
import es.uca.iw.ebz.consulta.ConsultaService;
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
    private AuthenticatedUser _authenticatedUser;

    private Cliente _cliente;

    public ConsultaView(ConsultaService _consultaService, ClienteService _clienteService, AuthenticatedUser _authenticatedUser){

        //Services initialization section
        this._consultaService = _consultaService;
        this._clienteService = _clienteService;
        this._authenticatedUser = _authenticatedUser;
        //End services initialization section

        //Client asignation
        _cliente = _clienteService.findByUsuario(_authenticatedUser.get().get());

    }

}
