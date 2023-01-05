package es.uca.iw.ebz.views.main.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import es.uca.iw.ebz.consulta.Consulta;
import es.uca.iw.ebz.consulta.ConsultaService;
import es.uca.iw.ebz.mensaje.Mensaje;
import es.uca.iw.ebz.mensaje.MensajeService;
import es.uca.iw.ebz.usuario.TipoUsuario;
import es.uca.iw.ebz.usuario.admin.Admin;
import es.uca.iw.ebz.usuario.admin.AdminService;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteService;
import org.checkerframework.checker.units.qual.A;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClienteConsultaDialog extends Dialog {

    private Consulta _consulta;

    private Cliente _cliente;

    private MensajeService _mensajeService;

    private MessageInput _msgInput;

    private AdminService _adminService;

    private ClienteService _clienteService;

    public ClienteConsultaDialog(Consulta consulta,Cliente cliente, AdminService adminService,
                                 ClienteService clienteService, MensajeService mensajeService) {

        //Services initialization section
        _consulta = consulta;
        _cliente = cliente;
        _mensajeService = mensajeService;
        _clienteService = clienteService;
        _adminService = adminService;

        //End services initialization section

        //Dialog configuration section
        setWidth("40vw");
        setHeaderTitle("Detalles de la consulta");

        Button btnCancelar = new Button(new Icon(VaadinIcon.CLOSE));
        btnCancelar.addThemeVariants(ButtonVariant.LUMO_ICON);
        btnCancelar.getElement().addEventListener("click", e -> close());
        getHeader().add(btnCancelar);
        //End dialog configuration section

        //Chat layout section
        VerticalLayout vlChat = new VerticalLayout();
        vlChat.setWidthFull();


        //End chat layout section


        //Chat elements section
        MessageList msgList = new MessageList();
        MessageInput msgInput = new MessageInput();
        msgInput.addSubmitListener( submitEvent -> {
            Mensaje lastMsg = new Mensaje(new Date(), submitEvent.getValue(), _cliente.getUsuario());
            _consulta.setMensajes(lastMsg);
            List<MessageListItem> items = new ArrayList<>(msgList.getItems());
            items.add(new MessageListItem(lastMsg.getTexto(), lastMsg.getFecha().toInstant(), _cliente.getNombre()));
            msgList.setItems(items);
        });

        List<Mensaje> lista = _consulta.getMensajes();

        for(Mensaje m: lista){
            TipoUsuario tpUser = m.getAutor().getTipoUsuario();
            if(tpUser == TipoUsuario.Cliente){
                Cliente clAux = _clienteService.findByUsuario(m.getAutor());
                msgList.setItems(new MessageListItem(m.getTexto(), m.getFecha().toInstant(), clAux.getNombre()));
            }else{
                msgList.setItems(new MessageListItem(m.getTexto(), m.getFecha().toInstant(), "Equipo de administración"));
            }
        }

        vlChat.add(
                msgList,
                msgInput
        );

        add(vlChat);


        //End chat elements section


    }

}
