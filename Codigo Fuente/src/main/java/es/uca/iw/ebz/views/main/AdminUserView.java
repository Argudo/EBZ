package es.uca.iw.ebz.views.main;

import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.ebz.views.main.layout.AdminLayout;

@PageTitle("User-Detail")
@Route(value = "usuarios", layout = AdminLayout.class)
@Uses(Icon.class)
public class AdminUserView extends VerticalLayout {

}
