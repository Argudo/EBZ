package es.uca.iw.ebz.views.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.security.RolesAllowed;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Cuenta.CuentaService;
import es.uca.iw.ebz.tarjeta.Tarjeta;
import es.uca.iw.ebz.usuario.Usuario;
import es.uca.iw.ebz.usuario.UsuarioService;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteService;
import es.uca.iw.ebz.views.main.layout.AdminLayout;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Dashboard/cuentas")
@RolesAllowed({ "Empleado"})
@Route(value = "Dashboard/cuentas", layout = AdminLayout.class)
public class DashBoardCuentasView extends HorizontalLayout {
    private VerticalLayout vlGrid = new VerticalLayout();
    private VerticalLayout vlInfo = new VerticalLayout();
    private VerticalLayout vlSeparator = new VerticalLayout();

    private H1 hGrid = new H1(getTranslation("account.home"));

    private H2 hInfo = new H2(getTranslation("account.search"));
    private HorizontalLayout hlBuscador = new HorizontalLayout();

    private ComboBox<String> cbUsuario = new ComboBox<>(getTranslation("account.home"));
    private Button btnBuscar = new Button();
    private Paragraph pDNI = new Paragraph(getTranslation("account.number"));
    private HorizontalLayout hlAviso = new HorizontalLayout();
    private Grid<Cuenta> gridCuenta = new Grid<>(Cuenta.class, false);

    private Button btnAdd = new Button(getTranslation("account.add"));

    private Button btnDelete = new Button(getTranslation("account.delete"));

    private VerticalLayout hlAdd = new VerticalLayout();

    @Autowired
    private ClienteService _clienteService;
    @Autowired
    private CuentaService _cuentaService;

    private UsuarioService _usuarioService;


    public DashBoardCuentasView(CuentaService cuentaService, ClienteService clienteService, UsuarioService usuarioService) {
        this._cuentaService = cuentaService;
        this._clienteService = clienteService;
        this._usuarioService = usuarioService;
        hGrid.setClassName("title");
        hInfo.setClassName("title");

        vlGrid.setWidth("70%");
        vlSeparator.setWidth("2px");
        vlInfo.setWidth("30%");

        vlSeparator.getStyle().set("background-color", "var(--lumo-contrast-10pct)");
        vlSeparator.getStyle().set("padding", "0");

        vlGrid.add(hGrid,
                new Hr(),
                gridCuenta,
                btnAdd);

        vlInfo.setAlignItems(FlexComponent.Alignment.CENTER);
        hlBuscador.setAlignItems(FlexComponent.Alignment.END);
        pDNI.getStyle().set("fontWeight", "600");
        btnBuscar.getElement().appendChild(new Icon("lumo", "search").getElement());
        hlAviso.setAlignItems(FlexComponent.Alignment.CENTER);
        hlAviso.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        hlAviso.setWidth("100%");
        /*Grid cliente*/
        List<Cuenta> aCuenta = new ArrayList<Cuenta>();
        /*Grid Tarjeta*/
        aCuenta = _cuentaService.loadCuentas();
        gridCuenta.addColumn(Cuenta::getNumeroCuenta).setHeader("Número de tarjeta");
        gridCuenta.addColumn(Cuenta::getFechaCreacion).setHeader("Fecha de creación");
        gridCuenta.addColumn(Cuenta::getSaldo).setHeader("Saldo");
        gridCuenta.addColumn(Cuenta::getDNICliente).setHeader("DNI Cliente");
        gridCuenta.setItems(aCuenta);

        //comboBox
        List<String> aCuentas = new ArrayList<>();
        for (Cuenta cuenta : cuentaService.loadCuentas()) {
            aCuentas.add(cuenta.getNumeroCuenta());
        }
        cbUsuario.setItems(aCuentas);

        hlBuscador.add(pDNI,
                cbUsuario);
        vlInfo.add(hInfo,
                new Hr(),
                hlBuscador,
                hlAviso,
                btnDelete);

        setHeight("100%");
        add(vlGrid,
                vlSeparator,
                vlInfo);

        btnAdd.addClickListener(e -> {
            hlAdd.removeAll();
            add(hlAdd);
            Button btnCreateCuenta = new Button(getTranslation("account.create"));
            List<String> DNIs = new ArrayList<>();
            for (Cliente cliente : clienteService.findAll()) {
                DNIs.add(cliente.getUsuario().getDNI());
            }
            cbUsuario.setItems(DNIs);
            hlAdd.add(new Text(getTranslation("account.dni")),
                    cbUsuario,
                    btnCreateCuenta);
            add(hlAdd);
            btnCreateCuenta.addClickListener(event -> {
                Usuario usuario = usuarioService.findBysDNI(cbUsuario.getValue());
                Cliente cliente = clienteService.findByUsuario(usuario);
                Cuenta cuenta = new Cuenta();
                cuenta.setCliente(cliente);
                if(cuentaService.añadirCuenta(cuenta) != null) {
                    hlAviso.getStyle().set("font-size", "14px");
                    hlAviso.getStyle().set("background-color", "hsla(145, 76%, 44%, 0.22)");
                    hlAviso.getStyle().set("border-radius", "var(--lumo-border-radius-m)");
                    hlAviso.add(new Icon(VaadinIcon.CHECK), new Paragraph(getTranslation("account.success")));
                    hlAdd.add(hlAviso);
                    add(hlAdd);
                }
                else {
                    hlAviso.getStyle().set("font-size", "14px");
                    hlAviso.getStyle().set("background-color", "hsla(145, 76%, 44%, 0.22)");
                    hlAviso.getStyle().set("border-radius", "var(--lumo-border-radius-m)");
                    hlAviso.add(new Icon(VaadinIcon.CHECK), new Paragraph(getTranslation("account.error")));
                    hlAdd.add(hlAviso);
                    add(hlAdd);
                }
            });
        });

        btnDelete.addClickListener(e -> {
            hlAviso.removeAll();
            add(vlInfo);
            if(cbUsuario.getValue() == null){
                hlAviso.getStyle().set("font-size", "14px");
                hlAviso.getStyle().set("background-color", "hsla(145, 76%, 44%, 0.22)");
                hlAviso.getStyle().set("border-radius", "var(--lumo-border-radius-m)");
                hlAviso.add(new Icon(VaadinIcon.CHECK), new Paragraph(getTranslation("account.accountError")));
                vlInfo.add(hlAviso);
                add(vlInfo);
            }
            else {
                if(cuentaService.delete2(cbUsuario.getValue())){
                    hlAviso.getStyle().set("font-size", "14px");
                    hlAviso.getStyle().set("background-color", "hsla(145, 76%, 44%, 0.22)");
                    hlAviso.getStyle().set("border-radius", "var(--lumo-border-radius-m)");
                    hlAviso.add(new Icon(VaadinIcon.CHECK), new Paragraph(getTranslation("account.deleteSuccess")));
                    add(hlAviso);
                }else{
                    hlAviso.getStyle().set("font-size", "14px");
                    hlAviso.getStyle().set("background-color", "hsla(145, 76%, 44%, 0.22)");
                    hlAviso.getStyle().set("border-radius", "var(--lumo-border-radius-m)");
                    hlAviso.add(new Icon(VaadinIcon.CHECK), new Paragraph(getTranslation("account.deleteError")));
                    add(hlAviso);
                }
            }
        });

    }
}
