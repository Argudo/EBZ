package es.uca.iw.ebz.views.component;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.shared.Registration;
import es.uca.iw.ebz.consulta.Consulta;
import es.uca.iw.ebz.consulta.ConsultaService;
import es.uca.iw.ebz.consulta.EnumEstado;
import es.uca.iw.ebz.consulta.TipoEstado;
import es.uca.iw.ebz.mensaje.Mensaje;
import es.uca.iw.ebz.mensaje.MensajeService;
import es.uca.iw.ebz.usuario.TipoUsuario;
import es.uca.iw.ebz.usuario.admin.Admin;
import es.uca.iw.ebz.usuario.admin.AdminService;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminConsultaDialog extends Dialog {

    private Consulta _consulta;

    private Admin _admin;

    private MensajeService _mensajeService;

    private AdminService _adminService;

    private ConsultaService _consultaService;

    private ClienteService _clienteService;


    public AdminConsultaDialog(Consulta consulta,Admin admin, AdminService adminService,
                               ClienteService clienteService, MensajeService mensajeService,
                               ConsultaService consultaService) {

        //Services initialization section
        _consulta = consulta;
        _admin = admin;
        _mensajeService = mensajeService;
        _clienteService = clienteService;
        _consultaService = consultaService;
        _adminService = adminService;

        //End services initialization section

        //Dialog configuration section
        setWidth("40vw");
        setHeaderTitle(getTranslation("query.detail") + " \"" + _consulta.getTitulo() + "\"");

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
        msgInput.setSizeFull();
        msgInput.addSubmitListener( submitEvent -> {
            _consulta.set_tipoEstado(new TipoEstado(EnumEstado.Abierto));
            Mensaje lastMsg = new Mensaje(new Date(), submitEvent.getValue(), _admin.getUsuario(), _consulta);
            _consultaService.Save(_consulta);
            _mensajeService.Save(lastMsg);
            _consulta.setMensajes(lastMsg);
            List<MessageListItem> items = new ArrayList<>(msgList.getItems());
            MessageListItem msgAux = new MessageListItem(lastMsg.getTexto(), lastMsg.getFecha().toInstant(), _admin.getNombre());
            msgAux.setUserColorIndex(1);
            items.add(msgAux);
            msgList.setItems(items);

            fireEvent(new UpdateQueryEvent(this, false));
        });

        //Radio button group for query state

        //End radio button group for query state

        List<Mensaje> lista = _consulta.getMensajes();

        for(Mensaje m: lista){
            TipoUsuario tpUser = m.getAutor().getTipoUsuario();
            List<MessageListItem> items = new ArrayList<>(msgList.getItems());
            if(tpUser == TipoUsuario.Cliente){
                Cliente clAux = _clienteService.findByUsuario(m.getAutor());
                MessageListItem msgAux = new MessageListItem(m.getTexto(), m.getFecha().toInstant(), clAux.getNombre());
                msgAux.setUserColorIndex(8);
                items.add(msgAux);
                msgList.setItems(items);
            }else{
                Admin adAux = _adminService.findByUsuario(m.getAutor());
                MessageListItem msgAux = new MessageListItem(m.getTexto(), m.getFecha().toInstant(), adAux.getNombre());
                msgAux.setUserColorIndex(1);
                items.add(msgAux);
                msgList.setItems(items);
            }
        }

        vlChat.add(
                msgList,
                msgInput);

        add(vlChat);


        //End chat elements section



    }

    public Registration addUpdateListener(ComponentEventListener<UpdateQueryEvent> listener) {
        return addListener(UpdateQueryEvent.class, listener);
    }
}
