package es.uca.iw.ebz.views.admin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.ebz.noticia.Noticia;
import es.uca.iw.ebz.noticia.NoticiaService;
import es.uca.iw.ebz.views.Security.AuthenticatedUser;
import es.uca.iw.ebz.views.component.CreateNoticeDialog;
import es.uca.iw.ebz.views.layout.AdminLayout;

import javax.annotation.security.RolesAllowed;

@PageTitle("Gestión de noticias | EBZ")
@RolesAllowed({ "Empleado"})
@Route(value = "Dashboard/noticias", layout = AdminLayout.class)
public class DashBoardNoticasView extends VerticalLayout {
        private Grid<Noticia> gridConsultas = new Grid<>(Noticia.class, false);
    private H1 hHeader = new H1(getTranslation("notice.home"));
    private AuthenticatedUser authenticatedUser;
    private NoticiaService noticiaService;
    private Button btnAdd = new Button(getTranslation("notice.add"));
    public DashBoardNoticasView(AuthenticatedUser authenticatedUser, NoticiaService noticiaService) {
        this.authenticatedUser = authenticatedUser;
        this.noticiaService = noticiaService;

        hHeader.setClassName("title");

        gridConsultas.addColumn(Noticia::getTitulo).setHeader(getTranslation("notice.title")).setAutoWidth(true).setSortable(true);
        gridConsultas.addColumn(Noticia::getDescripcion).setHeader(getTranslation("notice.description"));
        gridConsultas.addColumn(Noticia::getFecha).setHeader(getTranslation("notice.date")).setAutoWidth(true).setSortable(true);
        gridConsultas.addComponentColumn(this::buildDeleteButton);
        gridConsultas.setItems(noticiaService.findByFechaEliminacionIsNull());

        add(hHeader, gridConsultas, btnAdd);

        btnAdd.addClickListener(e -> {
            CreateNoticeDialog createNoticeDialog = new CreateNoticeDialog(noticiaService);
            createNoticeDialog.open();
            createNoticeDialog.addUpdateListener(event -> {
                UpdateGrid();
            });

        });
    }

    private Button buildDeleteButton(Noticia noticia) {
        Button button = new Button(new Icon(VaadinIcon.TRASH));
        button.addClickListener(clickedItem-> {
            ConfirmDialog dialog = new ConfirmDialog();
            dialog.setHeader(getTranslation("confirm.title"));
            dialog.setText(getTranslation("confirm.body"));

            dialog.setCancelable(true);

            dialog.setCancelText(getTranslation("confirm.no"));

            dialog.setConfirmText(getTranslation("confirm.yes"));
            dialog.addConfirmListener(event ->  {noticiaService.Delete(noticia);
            UpdateGrid();
            ;});

            dialog.open();
        });
        return button;
    }

    private void UpdateGrid(){
        gridConsultas.setItems(noticiaService.findByFechaEliminacionIsNull());
        gridConsultas.getDataProvider().refreshAll();
    }
}
