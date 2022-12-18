package es.uca.iw.ebz.views.main;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import es.uca.iw.ebz.usuario.UsuarioService;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteService;
import es.uca.iw.ebz.usuario.cliente.TipoCliente;
import es.uca.iw.ebz.views.main.layout.AdminLayout;

@PageTitle("Dashboard/cuentas")
@PermitAll
@Route(value = "Dashboard/cuentas", layout = AdminLayout.class)
public class DashBoardCuentasView extends VerticalLayout {
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
    public DashBoardCuentasView(UsuarioService usuarioService, ClienteService clienteService) {
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

        Button btnCreate= new Button("Crear Cuenta");
        Button btnDelete = new Button("Eliminar Usuario");

        flFunctionalities.add(
                btnCreate,
                btnDelete
        );

        vlDashboard.add(flFunctionalities);

        add(vlDashboard);

        btnCreate.addClickListener(e -> {
            Button btnCreateCuenta = new Button("Crear Cuenta");
            List<String> DNIs = new ArrayList<>();
            for (Cliente cliente : clienteService.findAll()) {
                DNIs.add(cliente.getUsuario().getDNI());
            }
            cbUsuario.setItems(DNIs);
            vlDashboard.add(new Text("Introduzca el DNI del usuario que desea eliminar"),
                    cbUsuario,
                    btnCreateCuenta);
            add(vlDashboard);
            btnCreateCuenta.addClickListener(event -> {

            });
        });

        bntSave.addClickListener(e -> {

            /*if (clienteTest != null) {
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
            vlDashboard.add(hlAviso);*/
        });

        btnDelete.addClickListener(e -> {
            Button btnCreateCuenta = new Button("Crear Cuenta");
            List<String> DNIs = new ArrayList<>();
            for (Cliente cliente : clienteService.findAll()) {
                DNIs.add(cliente.getUsuario().getDNI());
            }
            cbUsuario.setItems(DNIs);
            vlDashboard.add(new Text("Introduzca el DNI del usuario que desea eliminar"),
                    cbUsuario,
                    btnCreateCuenta);
            add(vlDashboard);
            btnCreateCuenta.addClickListener(event -> {

            });
        });
    }
}
