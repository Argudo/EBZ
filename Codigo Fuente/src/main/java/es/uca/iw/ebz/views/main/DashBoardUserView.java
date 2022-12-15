package es.uca.iw.ebz.views.main;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.ebz.views.main.layout.AdminLayout;



@PageTitle("Dashboard/usuario")
@Route(value = "Dashboard/usuario", layout = AdminLayout.class)
public class DashBoardUserView extends VerticalLayout {
    private VerticalLayout vlDashboard = new VerticalLayout();
    private Text tName = new Text("Nombre");
    private TextField tfName = new TextField();

    private Text tBirhDate = new Text("Fecha de nacimiento");
    private Text tUsername = new Text("DNI");

    private Text tPassword = new Text("Contraseña");


    public DashBoardUserView() {
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

        Button btnUsuario = new Button("Añadir Usuario");
        Button btnCuenta = new Button("Elimnar Usuario");

        flFunctionalities.add(
                btnUsuario,
                btnCuenta
        );

        vlDashboard.add(flFunctionalities);

        add(vlDashboard);

        btnUsuario.addClickListener(e -> {
           vlDashboard.add (tName,
                   tfName,
                   tBirhDate, tUsername, tPassword);
           add(vlDashboard);
        });
    }
}
