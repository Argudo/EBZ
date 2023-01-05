package es.uca.iw.ebz.views.main;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.security.RolesAllowed;

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
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
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
	private Tabs tabs = new Tabs();
	private Tab tabNuevo = new Tab("Nuevo usuario");
	private Tab tabModificar = new Tab("Modificar usuario");
	
	private H1 hNuevoUsuario = new H1("Nuevo usuario");
    HorizontalLayout hlAccionesNuevoUsuario = new HorizontalLayout();
	private H1 hEliminarUsuario = new H1("Modificar usuario");
	HorizontalLayout hlAccionesModificarUsuario = new HorizontalLayout();
	private FormLayout formNuevoUsuario = new FormLayout();
	private FormLayout formEliminarUsuario = new FormLayout();
	private VerticalLayout vlNuevoUsuario = new VerticalLayout();
	private VerticalLayout vlModificarUsuario = new VerticalLayout();
	
    private VerticalLayout vlDashboard = new VerticalLayout();
    private TextField tfName = new TextField("Nombre");
    private TextField tfUsername = new TextField("DNI");
    private PasswordField tfPassword = new PasswordField("Contraseña");
    private DatePicker dpBirthDate = new DatePicker("Fecha de nacimiento");   
    
    private TextField tfNameMod = new TextField("Nombre");
    private PasswordField tfPasswordMod = new PasswordField("Contraseña");
    private DatePicker dpBirthDateMod = new DatePicker("Fecha de nacimiento");    
    private ComboBox<TipoCliente> cbTipoClienteMod = new ComboBox<>("Tipo de cliente");
    

    private Button btnSave = new Button("Guardar");
    private Button btnVaciar = new Button("Vaciar");

    private ComboBox<TipoCliente> cbTipoCliente = new ComboBox<>("Tipo de cliente");
    private HorizontalLayout hlAviso = new HorizontalLayout();

    private UsuarioService usuarioService;
    private ClienteService clienteService;

    private ComboBox<String> cbUsuario = new ComboBox<>("DNI");
    public DashBoardUserView(UsuarioService usuarioService, ClienteService clienteService) {
        this.usuarioService = usuarioService;
        this.clienteService = clienteService;
        this.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        this.setAlignItems(FlexComponent.Alignment.CENTER);
        
        tabs.add(tabNuevo, tabModificar);
        
        vlDashboard.setWidthFull();
        vlDashboard.setPadding(false);
        vlDashboard.setMargin(false);
        
        cbTipoCliente.setPlaceholder("Tipo");
        tfName.setPlaceholder("Nombre Apellido Apellido");
        tfUsername.setPlaceholder("00000000A");
        tfPassword.setPlaceholder("***********");
        
        hNuevoUsuario.setClassName("title");
        hEliminarUsuario.setClassName("title");
        vlNuevoUsuario.setClassName("box");
        vlModificarUsuario.setClassName("box");
        vlNuevoUsuario.setWidth("70vw");
        vlModificarUsuario.setWidth("70vw");
        vlNuevoUsuario.setPadding(true);
        vlModificarUsuario.setPadding(true);
        
        formNuevoUsuario.setResponsiveSteps(new ResponsiveStep("0", 1), new ResponsiveStep("500px", 4));
        formNuevoUsuario.setColspan(tfName, 3);
        formNuevoUsuario.setColspan(tfUsername, 2);
        formNuevoUsuario.setColspan(dpBirthDate, 2);
        formNuevoUsuario.setColspan(tfPassword, 4);

        formEliminarUsuario.setResponsiveSteps(new ResponsiveStep("0", 1));
        
        hlAccionesNuevoUsuario.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        hlAccionesNuevoUsuario.setWidthFull();
        btnSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cbTipoCliente.setWidth("200px");
        cbTipoCliente.setItems(TipoCliente.values());
        dpBirthDate.setPlaceholder("DD/MM/YYYY");
        
        hlAccionesNuevoUsuario.add(btnVaciar, btnSave);
        formNuevoUsuario.add(
        		cbTipoCliente,
        		tfName,
        		tfUsername,
                dpBirthDate,
                tfPassword
                );
        vlNuevoUsuario.add(hNuevoUsuario, new Hr(), formNuevoUsuario, hlAccionesNuevoUsuario);
        
        formEliminarUsuario.add();
        vlModificarUsuario.add(hEliminarUsuario, new Hr(), formEliminarUsuario);

        hlAviso.setAlignItems(FlexComponent.Alignment.CENTER);
        hlAviso.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        hlAviso.setWidth("100%");

        Button btnUsuario = new Button("Añadir Usuario");
        Button btnDelete = new Button("Elimnar Usuario");
        
        Button btnDeleteUser = new Button("Eliminar");
        Button btnModificar = new Button("Modificar");
        btnDeleteUser.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnDeleteUser.addThemeVariants(ButtonVariant.LUMO_ERROR);
        btnModificar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        hlAccionesModificarUsuario.add(btnDeleteUser, btnModificar);
        hlAccionesModificarUsuario.setWidthFull();
        hlAccionesModificarUsuario.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        List<String> DNIs = new ArrayList<>();
        for (Usuario usuario : usuarioService.findAll()) {
            DNIs.add(usuario.getDNI());
        }
        cbUsuario.setItems(DNIs);

        FormLayout hlDatosUsuario = new FormLayout();
        hlDatosUsuario.setResponsiveSteps(new ResponsiveStep("0px", 1), new ResponsiveStep("500px", 3));
        hlDatosUsuario.add(cbTipoClienteMod, tfNameMod, dpBirthDateMod);
        tfNameMod.setReadOnly(true);
        dpBirthDateMod.setReadOnly(true);
        cbTipoClienteMod.setReadOnly(true);
        tfNameMod.setVisible(false);
        tfPasswordMod.setVisible(false);
        dpBirthDateMod.setVisible(false);
        cbTipoClienteMod.setVisible(false);
        cbTipoClienteMod.setItems(TipoCliente.values());
        vlModificarUsuario.add(cbUsuario,
        					   hlDatosUsuario,
        					   tfPasswordMod,
                			   hlAccionesModificarUsuario);
        
        cbUsuario.addValueChangeListener( e -> {
        	if(DNIs.contains(e.getValue())) {
        		Cliente cliSelected = clienteService.findByDNI(e.getValue());
        		tfNameMod.setValue(cliSelected.getNombre());
        		dpBirthDateMod.setValue(convertToLocalDateViaInstant(cliSelected.getFechaNacimiento()));
                cbTipoClienteMod.setValue(cliSelected.getTipoCliente());
                tfNameMod.setVisible(true);
                tfPasswordMod.setVisible(true);
                dpBirthDateMod.setVisible(true);
                cbTipoClienteMod.setVisible(true);
        	}
        	else {
                tfNameMod.setVisible(false);
                tfPasswordMod.setVisible(false);
                dpBirthDateMod.setVisible(false);
                cbTipoClienteMod.setVisible(false);
        	}
        });
        
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

        tabs.setWidthFull();
        tabs.addThemeVariants(TabsVariant.LUMO_CENTERED);
        tabs.addSelectedChangeListener(e -> {
        	removeAll();
        	if(tabNuevo.isSelected())
        		add(tabs, vlNuevoUsuario);
        	else
        		add(tabs, vlModificarUsuario);
        });
        
        add(tabs, vlNuevoUsuario);


        btnSave.addClickListener(e -> {
            Usuario usuario = new Usuario(tfUsername.getValue(), tfPassword.getValue());
            usuario.setTipoUsuario(TipoUsuario.Cliente);
            Cliente clienteTest;
            usuarioService.save(usuario);
            Cliente cliente = new Cliente(tfName.getValue(), java.util.Date.from(dpBirthDate.getValue().atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant()), new Date(), cbTipoCliente.getValue(), usuario);
            clienteTest = clienteService.save(cliente);
            if (clienteTest != null) {
            	Notification notification = Notification.show("Cliente creado con éxito");
            	notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            	notification.setPosition(Notification.Position.MIDDLE);
            	formNuevoUsuario.getChildren().forEach(child -> child.getElement().setProperty("value", ""));
            	
            }else {
            	Notification notification = Notification.show("Error: No se ha podido crear el cliente");
            	notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            	notification.setPosition(Notification.Position.MIDDLE);
            }

            vlDashboard.add(hlAviso);
        });
        
        btnVaciar.addClickListener(e -> {
        	formNuevoUsuario.getChildren().forEach(child -> child.getElement().setProperty("value", ""));
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
    
    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
          .atZone(ZoneId.systemDefault())
          .toLocalDate();
    }
}
