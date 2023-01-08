package es.uca.iw.ebz.views.component;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Cuenta.CuentaService;
import es.uca.iw.ebz.usuario.Usuario;
import es.uca.iw.ebz.usuario.UsuarioService;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


public class CreateAccountDialog extends Dialog {
    private Button _btnCreateAccount = new Button(getTranslation("account.create"));
    private ComboBox<String> _cmbDni = new ComboBox<String>(getTranslation("account.dni"));

    public CreateAccountDialog(List<String> asDni, CuentaService cuentaService, UsuarioService usuarioService, ClienteService clienteService) {
        _cmbDni.setItems(asDni);
        Button btnCancelar = new Button(new Icon(VaadinIcon.CLOSE));
        btnCancelar.addThemeVariants(ButtonVariant.LUMO_ICON);
        btnCancelar.getElement().addEventListener("click", e -> close());
        getHeader().add(btnCancelar);

        setWidth("40vw");
        setHeaderTitle(getTranslation("account.create"));
        VerticalLayout vlInfo = new VerticalLayout();
        vlInfo.setWidthFull();

        vlInfo.add(
                _cmbDni,
                _btnCreateAccount
        );
        add(new Hr(), vlInfo);

        _btnCreateAccount.addClickListener(e -> {
            Usuario usuario = usuarioService.findBysDNI(_cmbDni.getValue());
            Cliente cliente = clienteService.findByUsuario(usuario);
            Cuenta cuenta = new Cuenta();
            cuenta.setCliente(cliente);
            if(cuentaService.añadirCuenta(cuenta) != null) {
                Notification notification = Notification.show(getTranslation("account.success"));
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                close();
            }
            else {
                Notification notification = new Notification();
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

                Div text = new Div(new Text(getTranslation("account.error")));

                Button closeButton = new Button(new Icon("lumo", "cross"));
                closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
                closeButton.getElement().setAttribute("aria-label", "Close");
                closeButton.addClickListener(event -> {
                    notification.close();
                });

                HorizontalLayout layout = new HorizontalLayout(text, closeButton);
                layout.setAlignItems(FlexComponent.Alignment.CENTER);

                notification.add(layout);
                notification.open();
                close();
            }
        });
    }

}
