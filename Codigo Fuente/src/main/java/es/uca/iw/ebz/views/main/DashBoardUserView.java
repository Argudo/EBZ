package es.uca.iw.ebz.views.main;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
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


@RolesAllowed({ "Empleado"})
@PageTitle("Dashboard/usuario")
@Route(value = "Dashboard/usuario", layout = AdminLayout.class)
public class DashBoardUserView extends VerticalLayout {
	private H1 hNuevoUsuario = new H1("Nuevo usuario");
    HorizontalLayout hlAccionesNuevoUsuario = new HorizontalLayout();
	private H1 hEliminarUsuario = new H1("Eliminar usuario");
	HorizontalLayout hlAccionesEliminarUsuario = new HorizontalLayout();
	private FormLayout formNuevoUsuario = new FormLayout();
	private FormLayout formEliminarUsuario = new FormLayout();
	private VerticalLayout vlNuevoUsuario = new VerticalLayout();
	private VerticalLayout vlEliminarUsuario = new VerticalLayout();
	
    private VerticalLayout vlDashboard = new VerticalLayout();
    private TextField tfName = new TextField("Nombre");
    private TextField tfUsername = new TextField("DNI");
    private PasswordField tfPassword = new PasswordField("Contraseña");
    private DatePicker dpBirthDate = new DatePicker("Fecha de nacimiento");    

    private Button btnSave = new Button("Guardar");
    private Button btnVaciar = new Button("Vaciar");

    private ComboBox<TipoCliente> comboBox = new ComboBox<>("Tipo de cliente");
    private HorizontalLayout hlAviso = new HorizontalLayout();

    private UsuarioService usuarioService;
    private ClienteService clienteService;

    private ComboBox<String> cbUsuario = new ComboBox<>("DNI del usuario que desea eliminar");
    public DashBoardUserView(UsuarioService usuarioService, ClienteService clienteService) {
        this.usuarioService = usuarioService;
        this.clienteService = clienteService;
        vlDashboard.setWidthFull();
        vlDashboard.setPadding(false);
        vlDashboard.setMargin(false);
        
        hNuevoUsuario.setClassName("title");
        hEliminarUsuario.setClassName("title");
        vlNuevoUsuario.setClassName("box");
        vlEliminarUsuario.setClassName("box");
        vlNuevoUsuario.setWidth("70vw");
        vlEliminarUsuario.setWidth("70vw");
        vlNuevoUsuario.setPadding(true);
        vlEliminarUsuario.setPadding(true);
        
        formNuevoUsuario.setResponsiveSteps(new ResponsiveStep("0", 1), new ResponsiveStep("500px", 4));
        formNuevoUsuario.setColspan(tfName, 3);
        formNuevoUsuario.setColspan(tfUsername, 2);
        formNuevoUsuario.setColspan(dpBirthDate, 2);
        formNuevoUsuario.setColspan(tfPassword, 4);

        formEliminarUsuario.setResponsiveSteps(new ResponsiveStep("0", 1));
        
        hlAccionesNuevoUsuario.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        hlAccionesNuevoUsuario.setWidthFull();
        btnSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        comboBox.setWidth("200px");
        comboBox.setItems(TipoCliente.values());
        dpBirthDate.setPlaceholder("DD.MM.YYYY");
        
        hlAccionesNuevoUsuario.add(btnVaciar, btnSave);
        formNuevoUsuario.add(
        		comboBox,
        		tfName,
        		tfUsername,
                dpBirthDate,
                tfPassword
                );
        vlNuevoUsuario.add(hNuevoUsuario, new Hr(), formNuevoUsuario, hlAccionesNuevoUsuario);
        
        formEliminarUsuario.add();
        vlEliminarUsuario.add(hEliminarUsuario, new Hr(), formEliminarUsuario);

        hlAviso.setAlignItems(FlexComponent.Alignment.CENTER);
        hlAviso.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        hlAviso.setWidth("100%");

        Button btnUsuario = new Button("Añadir Usuario");
        Button btnDelete = new Button("Elimnar Usuario");
        
        Button btnDeleteUser = new Button("Eliminar");
        btnDeleteUser.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        hlAccionesEliminarUsuario.add(btnDeleteUser);
        hlAccionesEliminarUsuario.setWidthFull();
        hlAccionesEliminarUsuario.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        List<String> DNIs = new ArrayList<>();
        for (Usuario usuario : usuarioService.findAll()) {
            DNIs.add(usuario.getDNI());
        }
        cbUsuario.setItems(DNIs);
        cbUsuario.setWidthFull();
        vlEliminarUsuario.add(cbUsuario,
                			  hlAccionesEliminarUsuario);
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

        add(vlNuevoUsuario, vlEliminarUsuario);


        btnSave.addClickListener(e -> {
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

            vlDashboard.add(hlAviso);
        });

        btnDelete.addClickListener(e -> {
        });
    }

    private boolean eliminarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getFechaEliminacion() != null ) return false;
        usuario.setFechaEliminaciono(new Date());
        usuarioService.save(usuario);
        return true;
    }
}
