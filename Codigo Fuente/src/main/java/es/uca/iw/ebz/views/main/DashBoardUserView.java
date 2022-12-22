package es.uca.iw.ebz.views.main;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.ebz.usuario.TipoUsuario;
import es.uca.iw.ebz.usuario.Usuario;
import es.uca.iw.ebz.usuario.UsuarioService;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteService;
import es.uca.iw.ebz.usuario.cliente.TipoCliente;
import es.uca.iw.ebz.views.main.layout.AdminLayout;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RolesAllowed({ "Empleado"})
@PageTitle("Dashboard/usuario")
@Route(value = "Dashboard/usuario", layout = AdminLayout.class)
public class DashBoardUserView extends VerticalLayout {
    private VerticalLayout vlDashboard = new VerticalLayout();
    private Text tName = new Text("Nombre");
    private TextField tfName = new TextField();

    private Text tBirhDate = new Text("Fecha de nacimiento");
    private DatePicker dpBirthDate = new DatePicker();
    private Text tUsername = new Text("DNI");
    private TextField tfUsername = new TextField();

    private Text tPassword = new Text("Contraseña");
    private TextField tfPassword = new TextField();

    private Button bntSave = new Button("Guardar");

    private ComboBox<TipoCliente> comboBox = new ComboBox<>("Tipo de cliente");
    private HorizontalLayout hlAviso = new HorizontalLayout();

    private UsuarioService usuarioService;
    private ClienteService clienteService;

    private ComboBox<String> cbUsuario = new ComboBox<>("Usuarios");
    public DashBoardUserView(UsuarioService usuarioService, ClienteService clienteService) {
        this.usuarioService = usuarioService;
        this.clienteService = clienteService;
        vlDashboard.setWidthFull();
        vlDashboard.setPadding(false);
        vlDashboard.setMargin(false);

        hlAviso.setAlignItems(FlexComponent.Alignment.CENTER);
        hlAviso.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        hlAviso.setWidth("100%");

        FlexLayout flFunctionalities = new FlexLayout();
        flFunctionalities.setWidthFull();
        flFunctionalities.setFlexDirection(FlexLayout.FlexDirection.ROW);
        flFunctionalities.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        flFunctionalities.setJustifyContentMode(FlexComponent.JustifyContentMode.EVENLY);

        FlexLayout flListen = new FlexLayout();
        flFunctionalities.setWidthFull();
        flFunctionalities.setFlexDirection(FlexLayout.FlexDirection.ROW);
        flFunctionalities.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        flFunctionalities.setJustifyContentMode(FlexComponent.JustifyContentMode.EVENLY);

        Button btnUsuario = new Button("Añadir Usuario");
        Button btnDelete = new Button("Elimnar Usuario");

        flFunctionalities.add(
                btnUsuario,
                btnDelete
        );

        vlDashboard.add(flFunctionalities);

        add(vlDashboard);

        btnUsuario.addClickListener(e -> {
            comboBox.setItems(TipoCliente.values());
            dpBirthDate.setPlaceholder("DD.MM.YYYY");
            dpBirthDate.setHelperText("Format: DD.MM.YYYY");
           vlDashboard.add (tName,
                   tfName,
                   tBirhDate,
                   dpBirthDate,
                   tUsername,
                   tfUsername,
                   tPassword,
                   tfPassword,
                   comboBox,
                   bntSave);
           add(vlDashboard);
        });

        bntSave.addClickListener(e -> {
            Usuario usuario = new Usuario(tfUsername.getValue(), tfPassword.getValue());
            usuario.setTipoUsuario(TipoUsuario.Cliente);
            Cliente clienteTest;
            usuarioService.save(usuario);
            Cliente cliente = new Cliente(tfName.getValue(), java.util.Date.from(dpBirthDate.getValue().atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant()), new Date(), comboBox.getValue(), usuario);
            clienteTest = clienteService.save(cliente);
            if (clienteTest != null) {
                hlAviso.getStyle().set("font-size", "14px");
                hlAviso.getStyle().set("background-color", "hsla(145, 76%, 44%, 0.22)");
                hlAviso.getStyle().set("border-radius", "var(--lumo-border-radius-m)");
                hlAviso.add(new Icon(VaadinIcon.CHECK), new Paragraph("Cliente creado con éxito"));
            }else {
                hlAviso.getStyle().set("font-size", "14px");
                hlAviso.getStyle().set("background-color", "hsla(145, 76%, 44%, 0.22)");
                hlAviso.getStyle().set("border-radius", "var(--lumo-border-radius-m)");
                hlAviso.add(new Icon(VaadinIcon.CHECK), new Paragraph("Error: No se ha podido crear el cliente"));
            }
            vlDashboard.remove(tName,
                    tfName,
                    tBirhDate,
                    dpBirthDate,
                    tUsername,
                    tfUsername,
                    tPassword,
                    tfPassword,
                    comboBox,
                    bntSave);

            vlDashboard.add(hlAviso);
        });

        btnDelete.addClickListener(e -> {
            Button btnDeleteUser = new Button("Eliminar");
            List<String> DNIs = new ArrayList<>();
            for (Usuario usuario : usuarioService.findAll()) {
                DNIs.add(usuario.getDNI());
            }
            cbUsuario.setItems(DNIs);
            vlDashboard.add(new Text("Introduzca el DNI del usuario que desea eliminar"),
                    cbUsuario,
                    btnDeleteUser);
            add(vlDashboard);
            btnDeleteUser.addClickListener(event -> {
                Usuario usuario = usuarioService.findBysDNI(cbUsuario.getValue());
                if(eliminarUsuario(usuario)) {
                    hlAviso.getStyle().set("font-size", "14px");
                    hlAviso.getStyle().set("background-color", "hsla(145, 76%, 44%, 0.22)");
                    hlAviso.getStyle().set("border-radius", "var(--lumo-border-radius-m)");
                    hlAviso.add(new Icon(VaadinIcon.CHECK), new Paragraph("Usuario eliminado con éxito"));
                }else {
                    hlAviso.getStyle().set("font-size", "14px");
                    hlAviso.getStyle().set("background-color", "hsla(145, 76%, 44%, 0.22)");
                    hlAviso.getStyle().set("border-radius", "var(--lumo-border-radius-m)");
                    hlAviso.add(new Icon(VaadinIcon.CHECK), new Paragraph("Error: No se ha podido eliminar el usuario"));
                }
                add(hlAviso);
            });
        });
    }

    private boolean eliminarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getFechaEliminacion() != null ) return false;
        usuario.setFechaEliminaciono(new Date());
        usuarioService.save(usuario);
        return true;
    }
}
