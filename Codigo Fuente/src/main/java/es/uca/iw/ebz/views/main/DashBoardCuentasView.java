package es.uca.iw.ebz.views.main;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;

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

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Cuenta.CuentaService;
import es.uca.iw.ebz.usuario.UsuarioService;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteService;
import es.uca.iw.ebz.usuario.cliente.TipoCliente;
import es.uca.iw.ebz.views.main.layout.AdminLayout;
import es.uca.iw.ebz.usuario.Usuario;

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
    private CuentaService cuentaService;

    private ComboBox<String> cbUsuario = new ComboBox<>("Usuarios");
    public DashBoardCuentasView(UsuarioService usuarioService, ClienteService clienteService, CuentaService cuentaService) {
        this.usuarioService = usuarioService;
        this.clienteService = clienteService;
        this.cuentaService = cuentaService;
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
        Button btnDelete = new Button("Eliminar Cuenta");

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
            vlDashboard.add(new Text("Introduzca el DNI del usuario para crear una cuenta"),
                    cbUsuario,
                    btnCreateCuenta);
            add(vlDashboard);
            btnCreateCuenta.addClickListener(event -> {
                Usuario usuario = usuarioService.findBysDNI(cbUsuario.getValue());
                Cliente cliente = clienteService.findByUsuario(usuario);
                Cuenta cuenta = new Cuenta();
                cuenta.setCliente(cliente);
                if(cuentaService.añadirCuenta(cuenta) != null) {
                    hlAviso.getStyle().set("font-size", "14px");
                    hlAviso.getStyle().set("background-color", "hsla(145, 76%, 44%, 0.22)");
                    hlAviso.getStyle().set("border-radius", "var(--lumo-border-radius-m)");
                    hlAviso.add(new Icon(VaadinIcon.CHECK), new Paragraph("Cuenta creada con éxito"));
                    vlDashboard.add(hlAviso);
                    add(vlDashboard);
                }
                else {
                    hlAviso.getStyle().set("font-size", "14px");
                    hlAviso.getStyle().set("background-color", "hsla(145, 76%, 44%, 0.22)");
                    hlAviso.getStyle().set("border-radius", "var(--lumo-border-radius-m)");
                    hlAviso.add(new Icon(VaadinIcon.CHECK), new Paragraph("Error: No se ha podido crear la cuenta"));
                    vlDashboard.add(hlAviso);
                    add(vlDashboard);
                }
            });
        });

        btnDelete.addClickListener(e -> {
            Button btnDeleteCuenta = new Button("Eliminar cuenta");
            List<String> aCuentas = new ArrayList<>();
            for (Cuenta cuenta : cuentaService.loadCuentas()) {
                aCuentas.add(cuenta.getNumeroCuenta());
            }
            cbUsuario.setItems(aCuentas);
            vlDashboard.add(new Text("Introduzca el número de cuenta para eliminarla"),
                    cbUsuario,
                    btnDeleteCuenta);
            add(vlDashboard);
            btnDeleteCuenta.addClickListener(event -> {
                Cuenta cuentaDelete = cuentaService.findByNumeroCuenta(cbUsuario.getValue()).get();
                if(cuentaService.delete2(cbUsuario.getValue())){
                    hlAviso.getStyle().set("font-size", "14px");
                    hlAviso.getStyle().set("background-color", "hsla(145, 76%, 44%, 0.22)");
                    hlAviso.getStyle().set("border-radius", "var(--lumo-border-radius-m)");
                    hlAviso.add(new Icon(VaadinIcon.CHECK), new Paragraph("Cuenta eliminada con éxito"));
                    vlDashboard.add(hlAviso);
                    add(vlDashboard);
                }else{
                    hlAviso.getStyle().set("font-size", "14px");
                    hlAviso.getStyle().set("background-color", "hsla(145, 76%, 44%, 0.22)");
                    hlAviso.getStyle().set("border-radius", "var(--lumo-border-radius-m)");
                    hlAviso.add(new Icon(VaadinIcon.CHECK), new Paragraph("Error: No se ha podido eliminar la cuenta"));
                    vlDashboard.add(hlAviso);
                    add(vlDashboard);
                }
            });
        });
    }
}
