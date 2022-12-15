package es.uca.iw.ebz.views.main;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.ebz.views.main.layout.AdminLayout;

@PageTitle("Dashboard/cuentas")
@Route(value = "Dashboard/cuentas", layout = AdminLayout.class)
public class DashBoardCuentasView extends VerticalLayout {
}
