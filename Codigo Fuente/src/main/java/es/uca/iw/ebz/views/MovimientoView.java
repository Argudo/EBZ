package es.uca.iw.ebz.views;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import es.uca.iw.ebz.Movimiento.DatosMovimiento;
import es.uca.iw.ebz.Movimiento.Movimiento;
import es.uca.iw.ebz.Movimiento.MovimientoService;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteService;
import es.uca.iw.ebz.views.Security.AuthenticatedUser;
import es.uca.iw.ebz.views.component.DetalleMovimientoDialog;
import es.uca.iw.ebz.views.component.MovimientosComponent;
import es.uca.iw.ebz.views.component.MovimientosComponent.TipoGrid;
import es.uca.iw.ebz.views.layout.MainLayout;

@PageTitle("Movimientos")
@Route(value = "movimiento", layout = MainLayout.class)
@RolesAllowed({ "Cliente" })
public class MovimientoView extends VerticalLayout {
    private VerticalLayout vlDetalleMovimiento = new VerticalLayout();
    private H1 hHeader = new H1(getTranslation("movement.home"));
    private VerticalLayout vlMain = new VerticalLayout();

    @Autowired
    private AuthenticatedUser authenticatedUser;
    private MovimientoService movimientoService;
    private ClienteService clienteService;
    private DetalleMovimientoDialog detalleMovimientoDialog;

    private Grid<DatosMovimiento> gridMovimientos = new Grid<>(DatosMovimiento.class, false);
    public MovimientoView (AuthenticatedUser user, MovimientoService movimientoService, ClienteService clienteService) {
        authenticatedUser = user;
        this.movimientoService = movimientoService;
        this.clienteService = clienteService;
        
        setJustifyContentMode(JustifyContentMode.CENTER);
        this.setAlignItems(FlexComponent.Alignment.CENTER);
        setWidthFull();
        hHeader.setClassName("title");
        gridMovimientos.addColumn(DatosMovimiento::getTipo).setHeader(getTranslation("movement.type")).setAutoWidth(true).setSortable(true);
        gridMovimientos.addColumn(DatosMovimiento::getOrigen).setHeader(getTranslation("movement.origin")).setAutoWidth(true);
        gridMovimientos.addColumn(DatosMovimiento::getDestino).setHeader(getTranslation("movement.destination")).setAutoWidth(true);
        gridMovimientos.addColumn(DatosMovimiento::getConcepto).setHeader(getTranslation("movement.concept")).setAutoWidth(true);
        gridMovimientos.addColumn(DatosMovimiento::getImporte).setHeader(getTranslation("movement.amount")).setSortable(true);
        gridMovimientos.addColumn(DatosMovimiento::getFecha).setHeader(getTranslation("movement.date")).setSortable(true).setAutoWidth(true);

        //cliente
        Cliente cliente = clienteService.findByUsuario(authenticatedUser.get().get());
        //movimientos
        List<Movimiento> movimientos = movimientoService.findByClienteByFechaASC(cliente);
        List<DatosMovimiento> datosMovimientos = new ArrayList<>();
        for(Movimiento movimiento : movimientos) {
            DatosMovimiento datosMovimiento = new DatosMovimiento();
            datosMovimiento = movimientoService.datosMovimientoClass(movimiento);
            datosMovimientos.add(datosMovimiento);
        }

        GridListDataView<DatosMovimiento> dataView = gridMovimientos.setItems(datosMovimientos);

        TextField searchField = new TextField();
        searchField.setWidth("30%");
        searchField.setPlaceholder(getTranslation("movement.search"));
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        MovimientosComponent compMovGrid = new MovimientosComponent(TipoGrid.Completo, movimientoService, cliente);
        vlMain.add(hHeader, new Hr(), searchField);
        vlMain.add(compMovGrid);
        add(vlMain);
        
        searchField.addValueChangeListener(e -> compMovGrid.getDataView().refreshAll());
        compMovGrid.getDataView().addFilter(mov -> {
            String searchTerm = searchField.getValue().trim();
            if (searchTerm.isEmpty()) return true;

            boolean matchesFullName = mov.getOrigen().contains(searchTerm);
            boolean matchesEmail = mov.getDestino().contains(searchTerm);
            boolean matchesProfession = mov.getConcepto().contains(searchTerm);
            boolean matchesTipo = mov.getTipo().contains(searchTerm);

            return matchesFullName || matchesEmail || matchesProfession || matchesTipo;
        });

        gridMovimientos.addItemClickListener(movimiento -> {
            detalleMovimientoDialog = new DetalleMovimientoDialog(movimiento.getItem());
            detalleMovimientoDialog.open();
        });
    }


}
