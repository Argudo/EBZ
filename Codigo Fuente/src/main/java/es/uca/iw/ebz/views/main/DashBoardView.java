package es.uca.iw.ebz.views.main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.ebz.views.main.layout.AdminLayout;


@PageTitle("Dashboard")
@Route(value = "Dashboard", layout = AdminLayout.class)
public class DashBoardView extends VerticalLayout {
    private VerticalLayout vlDashboard = new VerticalLayout();
    public DashBoardView() {
       vlDashboard.setWidthFull();
        vlDashboard.setPadding(false);
        vlDashboard.setMargin(false);

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

        Button btnUsuario = new Button ("Gestión Usuario");
        Button btnCuenta = new Button ("Gestion de Cuentas");
        Button btnNoticia = new Button ("Gestion Noticias");
        Button btnConsulta = new Button ("Gestion Consultas");
        Button btnTarjeta = new Button("Gestión de tarjetas");

        flFunctionalities.add(
                btnUsuario,
                btnCuenta,
                btnNoticia,
                btnConsulta,
                btnTarjeta
        );

        vlDashboard.add(flFunctionalities);

        add(vlDashboard);

        btnUsuario.addClickListener(e -> {
            Button btnAñadir = new Button ("Añadir Usuario");
            add(btnAñadir);
        });

        btnCuenta.addClickListener(e -> {
            Button btnAñadir = new Button ("Añadir Cuenta");
            Button btnDelete = new Button ("Eliminar Cuenta");
            flListen.add(btnAñadir, btnDelete);
            add(flListen);
        });
    }
}
