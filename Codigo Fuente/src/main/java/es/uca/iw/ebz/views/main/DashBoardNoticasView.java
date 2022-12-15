package es.uca.iw.ebz.views.main;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.ebz.views.main.layout.AdminLayout;

@PageTitle("Dashboard/noticias")
@Route(value = "Dashboard/noticias", layout = AdminLayout.class)
public class DashBoardNoticasView extends VerticalLayout {
}
