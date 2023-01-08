package es.uca.iw.ebz.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.ebz.views.layout.AdminLayout;

import javax.annotation.security.RolesAllowed;

@PageTitle("Dashboard/noticias")
@RolesAllowed({ "Empleado"})
@Route(value = "Dashboard/noticias", layout = AdminLayout.class)
public class DashBoardNoticasView extends VerticalLayout {
}
