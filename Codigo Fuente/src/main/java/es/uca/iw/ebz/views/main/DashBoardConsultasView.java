package es.uca.iw.ebz.views.main;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.ebz.views.main.layout.AdminLayout;

@PageTitle("Dashboard/consultas")
@Route(value = "Dashboard/consultas", layout = AdminLayout.class)
public class DashBoardConsultasView extends VerticalLayout {
}
