package es.uca.iw.ebz.views;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Cuenta.CuentaService;
import es.uca.iw.ebz.Movimiento.DatosMovimiento;
import es.uca.iw.ebz.usuario.Usuario;
import es.uca.iw.ebz.usuario.UsuarioService;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteService;
import es.uca.iw.ebz.views.component.CreateAccountDialog;
import es.uca.iw.ebz.views.layout.AdminLayout;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Dashboard/cuentas")
@RolesAllowed({ "Empleado"})
@Route(value = "Dashboard/cuentas", layout = AdminLayout.class)
public class DashBoardCuentasView extends HorizontalLayout {
    private VerticalLayout vlGrid = new VerticalLayout();
    private VerticalLayout vlInfo = new VerticalLayout();
    private VerticalLayout vlSeparator = new VerticalLayout();

    private H1 hGrid = new H1(getTranslation("account.home"));

    private H2 hInfo = new H2(getTranslation("dashboard.search"));
    private HorizontalLayout hlBuscador = new HorizontalLayout();

    private ComboBox<String> cbUsuario = new ComboBox<>(getTranslation("account.home"));
    private Button btnBuscar = new Button();
    private Paragraph pDNI = new Paragraph(getTranslation("account.number"));
    private Grid<Cuenta> gridCuenta = new Grid<>(Cuenta.class, false);

    private Button btnAdd = new Button(getTranslation("account.add"));

    private Button btnDelete = new Button(getTranslation("account.delete"));

    private VerticalLayout hlAdd = new VerticalLayout();

    private CreateAccountDialog createAccountDialog;

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

        btnDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        vlGrid.add(hGrid,
                new Hr(),
                gridCuenta,
                btnAdd);

        vlInfo.setAlignItems(FlexComponent.Alignment.CENTER);
        hlBuscador.setAlignItems(FlexComponent.Alignment.END);
        pDNI.getStyle().set("fontWeight", "600");
        btnBuscar.getElement().appendChild(new Icon("lumo", "search").getElement());
        /*Grid cliente*/

        gridCuenta.addColumn(Cuenta::getNumeroCuenta).setHeader("Número de tarjeta").setAutoWidth(true).setSortable(true);
        gridCuenta.addColumn(Cuenta::getFechaCreacion).setHeader("Fecha de creación").setAutoWidth(true).setSortable(true);
        gridCuenta.addColumn(Cuenta::getSaldo).setHeader("Saldo").setSortable(true);
        gridCuenta.addColumn(Cuenta::getDNICliente).setHeader("DNI Cliente").setAutoWidth(true).setSortable(true);
        //actualizamos el UI
        updateUI();
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
                btnDelete);

        setHeight("100%");
        add(vlGrid,
                vlSeparator,
                vlInfo);

        btnAdd.addClickListener(e -> {
            List<String> DNIs = new ArrayList<>();
            for (Cliente cliente : clienteService.findAll()) {
                DNIs.add(cliente.getUsuario().getDNI());
            }
            createAccountDialog = new CreateAccountDialog(DNIs, cuentaService, usuarioService, clienteService);
            createAccountDialog.open();
        });

        btnDelete.addClickListener(e -> {
                ConfirmDialog dialog = new ConfirmDialog();
                dialog.setHeader(getTranslation("confirm.title"));
                dialog.setText(getTranslation("confirm.body"));

                dialog.setCancelable(true);

                dialog.setCancelText(getTranslation("confirm.no"));

                dialog.setConfirmText(getTranslation("confirm.yes"));
                dialog.addConfirmListener(event ->  {
                    if(cbUsuario.getValue() == null){
                    Notification notification = new Notification();
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

                    Div text = new Div(new Text(getTranslation("account.accountError")));

                    Button closeButton = new Button(new Icon("lumo", "cross"));
                    closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
                    closeButton.getElement().setAttribute("aria-label", "Close");
                    closeButton.addClickListener(ee -> {
                        notification.close();
                    });

                    HorizontalLayout layout = new HorizontalLayout(text, closeButton);
                    layout.setAlignItems(FlexComponent.Alignment.CENTER);

                    notification.add(layout);
                    notification.open();
                }
                else {
                    if(cuentaService.delete2(cbUsuario.getValue())){
                        Notification notification = Notification.show(getTranslation("account.deleteSuccess"));
                        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                        updateUI();
                    }else{
                        Notification notification = new Notification();
                        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

                        Div text = new Div(new Text(getTranslation("account.deleteError")));

                        Button closeButton = new Button(new Icon("lumo", "cross"));
                        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
                        closeButton.getElement().setAttribute("aria-label", "Close");
                        closeButton.addClickListener(not -> {
                            notification.close();
                        });

                        HorizontalLayout layout = new HorizontalLayout(text, closeButton);
                        layout.setAlignItems(FlexComponent.Alignment.CENTER);

                        notification.add(layout);

                        notification.open();
                    }
                }
                    ;});

            dialog.open();
            });
    }

    private void updateUI(){
        List<Cuenta> aCuenta = new ArrayList<Cuenta>();
        aCuenta = _cuentaService.loadCuentas();
        gridCuenta.setItems(aCuenta);

        gridCuenta.getDataProvider().refreshAll();
    }


}
