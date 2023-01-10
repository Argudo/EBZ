package es.uca.iw.ebz.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.ebz.views.layout.AdminLayout;

import javax.annotation.security.RolesAllowed;

@RolesAllowed({ "Empleado"})
@PageTitle("Dashboard/consultas")
@Route(value = "Dashboard/consultas", layout = AdminLayout.class)
public class DashBoardConsultasView extends VerticalLayout {
}
