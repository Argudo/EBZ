package es.uca.iw.ebz.views.admin;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.ebz.usuario.admin.Admin;
import es.uca.iw.ebz.usuario.admin.AdminService;
import es.uca.iw.ebz.views.Security.AuthenticatedUser;
import es.uca.iw.ebz.views.layout.AdminLayout;

import javax.annotation.security.RolesAllowed;
import java.util.*;


@RolesAllowed({ "Empleado" })
@PageTitle("Inicio | EBZ")
@Route(value = "Dashboard", layout = AdminLayout.class)
public class DashBoardView extends VerticalLayout {
    private VerticalLayout vlDashboard = new VerticalLayout();

    private H2 hDay = new H2();

    private H1 dashBoard = new H1(getTranslation("dashboard.title"));

    private Date date = new Date();

    private Calendar calendar = GregorianCalendar.getInstance();

    private Admin admin;

    private AuthenticatedUser user;

    private AdminService adminService;

    public DashBoardView(AuthenticatedUser user, AdminService adminService) {
        this.user = user;
        this.adminService = adminService;
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        admin = adminService.findByDNI(user.get().get().getDNI());

        if(hour > 8 && hour < 14) hDay.setText(getTranslation("dashboard.morning") + admin.getNombre());

        if(hour > 14 && hour < 22) hDay.setText(getTranslation("dashboard.afternoon") + admin.getNombre());

        if(hour > 22 || hour < 8) hDay.setText(getTranslation("dashboard.evening") + admin.getNombre() + "?");
        vlDashboard.add(dashBoard, hDay);
        add(vlDashboard);

    }
}
