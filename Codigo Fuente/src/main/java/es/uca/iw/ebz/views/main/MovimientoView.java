package es.uca.iw.ebz.views.main;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
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
import es.uca.iw.ebz.views.main.Security.AuthenticatedUser;
import es.uca.iw.ebz.views.main.layout.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@PageTitle("Movimientos")
@Route(value = "movimiento", layout = MainLayout.class)
@RolesAllowed({ "Cliente" })
public class MovimientoView extends VerticalLayout {
    private VerticalLayout vlDetalleMovimiento = new VerticalLayout();
    private H1 hHeader = new H1("Mis movimientos");


    @Autowired
    private AuthenticatedUser authenticatedUser;
    private MovimientoService movimientoService;
    private ClienteService clienteService;

    private Grid<DatosMovimiento> gridMovimientos = new Grid<>(DatosMovimiento.class, false);
    public MovimientoView (AuthenticatedUser user, MovimientoService movimientoService, ClienteService clienteService) {
        authenticatedUser = user;
        this.movimientoService = movimientoService;
        this.clienteService = clienteService;
        add(hHeader);

        gridMovimientos.addColumn(DatosMovimiento::getOrigen).setHeader("Origen").setAutoWidth(true);
        gridMovimientos.addColumn(DatosMovimiento::getDestino).setHeader("Destino").setAutoWidth(true);
        gridMovimientos.addColumn(DatosMovimiento::getConcepto).setHeader("Concepto").setAutoWidth(true);
        gridMovimientos.addColumn(DatosMovimiento::getImporte).setHeader("Importe").setSortable(true);
        gridMovimientos.addColumn(DatosMovimiento::getFecha).setHeader("Fecha").setSortable(true).setAutoWidth(true);

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
        searchField.setWidth("50%");
        searchField.setPlaceholder("Search");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> dataView.refreshAll());
        add(searchField);
        if(movimientos != null){
            add(gridMovimientos);
        }else{
            add(new H2("No tienes ningún movimiento actualmente"));
        }

        dataView.addFilter(mov -> {
            String searchTerm = searchField.getValue().trim();

            if (searchTerm.isEmpty()) return true;

            boolean matchesFullName = mov.getOrigen().contains(searchTerm);
            boolean matchesEmail = mov.getDestino().contains(searchTerm);
            boolean matchesProfession = mov.getConcepto().contains(searchTerm);

            return matchesFullName || matchesEmail || matchesProfession;
        });


    }


}
