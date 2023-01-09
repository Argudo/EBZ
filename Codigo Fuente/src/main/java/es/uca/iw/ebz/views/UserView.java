package es.uca.iw.ebz.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.ebz.usuario.Usuario;
import es.uca.iw.ebz.usuario.UsuarioService;
import es.uca.iw.ebz.views.Security.AuthenticatedUser;
import es.uca.iw.ebz.views.layout.MainLayout;

import javax.annotation.security.RolesAllowed;

@RolesAllowed({ "Cliente"})
@PageTitle("perfil")
@Route(value = "perfil", layout = MainLayout.class)
public class UserView extends VerticalLayout {
    private H1 hPerfil = new H1(getTranslation("user.home"));

    private H2 hPass = new H2(getTranslation("user.changePassword"));

    private Paragraph pPass= new Paragraph(getTranslation("user.newPassword"));

    private Paragraph pPassRepeat = new Paragraph(getTranslation("user.confirmPassword"));

    private TextField tfPass = new TextField();
    private TextField tfPassRepeat = new TextField();

    private HorizontalLayout hlPass = new HorizontalLayout();

    private HorizontalLayout hlPassRepeat = new HorizontalLayout();

    private Button btnChange = new Button(getTranslation("user.change"));
    private HorizontalLayout hlAviso = new HorizontalLayout();


    private UsuarioService usuarioService;

    private AuthenticatedUser authenticatedUser;

    public UserView(UsuarioService usuarioService, AuthenticatedUser authenticatedUser) {
        this.usuarioService = usuarioService;
        this.authenticatedUser = authenticatedUser;
        hlPass.add(pPass, tfPass);
        hlPassRepeat.add(pPassRepeat, tfPassRepeat);

        add(hPerfil, hPass,hlPass, hlPassRepeat, btnChange);

        btnChange.addClickListener(e -> {
            if(tfPass.getValue().equals(tfPassRepeat.getValue()) && tfPass.getValue() != null && tfPassRepeat.getValue() != null){
                Usuario usuario = authenticatedUser.get().get();
                usuarioService.CambiarContraseña(usuario, tfPass.getValue());
                hlAviso.getStyle().set("font-size", "14px");
                hlAviso.getStyle().set("background-color", "hsla(145, 76%, 44%, 0.22)");
                hlAviso.getStyle().set("border-radius", "var(--lumo-border-radius-m)");
                hlAviso.add(new Icon(VaadinIcon.CHECK), new Paragraph(getTranslation("user.success")));
            }else {
                hlAviso.getStyle().set("font-size", "14px");
                hlAviso.getStyle().set("background-color", "hsla(0, 76%, 44%, 0.22)");
                hlAviso.getStyle().set("border-radius", "var(--lumo-border-radius-m)");
                hlAviso.add(new Icon(VaadinIcon.CLOSE), new Paragraph(getTranslation("user.error")));
            }
            add(hlAviso);
        });
    }
}
