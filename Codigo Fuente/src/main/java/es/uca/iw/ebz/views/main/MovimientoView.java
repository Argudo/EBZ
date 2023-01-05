package es.uca.iw.ebz.views.main;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
@Component
@Route(value = "movimiento", layout = MainLayout.class)
@RolesAllowed({ "Cliente" })
public class MovimientoView extends VerticalLayout {
    private VerticalLayout vlDetalleMovimiento = new VerticalLayout();

    private H3 hOrigen = new H3("Origen");
    private H3 hDestino = new H3("Destino");
    private H3 hImporte = new H3("Importe");
    private H3 hConcepto = new H3("Concepto");
    private H3 hFecha = new H3("Fecha");

    public Paragraph pOrigen = new Paragraph();
    public Paragraph pDestino = new Paragraph();
    public Paragraph pImporte = new Paragraph();
    public Paragraph pConcepto = new Paragraph();
    public Paragraph pFecha = new Paragraph();

    @Autowired
    private AuthenticatedUser authenticatedUser;
    private MovimientoService movimientoService;
    private ClienteService clienteService;

    private Grid<DatosMovimiento> gridMovimientos = new Grid<>(DatosMovimiento.class, false);
    public MovimientoView (AuthenticatedUser user, MovimientoService movimientoService, ClienteService clienteService) {
        authenticatedUser = user;
        this.movimientoService = movimientoService;
        this.clienteService = clienteService;
        H1 hMovimiento = new H1("Mis movimientos");
        hMovimiento.setClassName("title");

        gridMovimientos.addColumn(DatosMovimiento::getOrigen).setWidth("33%");
        gridMovimientos.addColumn(DatosMovimiento::getDestino).setWidth("33%");
        gridMovimientos.addColumn(DatosMovimiento::getImporte).setWidth("33%");
        gridMovimientos.addColumn(DatosMovimiento::getFecha).setWidth("33%");

        //cliente
        //Cliente cliente = clienteService.findByUsuario(authenticatedUser.get().get());
        //movimientos
       /* List<Movimiento> movimientos = movimientoService.findByClienteByFechaASC(cliente);
        List<DatosMovimiento> datosMovimientos = new ArrayList<>();
        for(Movimiento movimiento : movimientos) {
            DatosMovimiento datosMovimiento = new DatosMovimiento();
            datosMovimiento = movimientoService.datosMovimientoClass(movimiento);
            datosMovimientos.add(datosMovimiento);
        }

        gridMovimientos.setItems(datosMovimientos);
        vlDetalleMovimiento.add(gridMovimientos);*/

        vlDetalleMovimiento.setWidth("50%");
        vlDetalleMovimiento.setPadding(true);
        vlDetalleMovimiento.setMargin(true);
        vlDetalleMovimiento.setSpacing(false);
        vlDetalleMovimiento.setClassName("show Movimiento");

        //ejemplo
        pOrigen.setText("Cuenta 1");
        pDestino.setText("Cuenta 2");
        pImporte.setText("1000");
        pConcepto.setText("Pago de la luz");
        pFecha.setText("01/01/2020");
        vlDetalleMovimiento.add(hMovimiento,
                new Hr(),
                hOrigen,
                pOrigen,
                hDestino,
                pDestino,
                hImporte,
                pImporte,
                hConcepto,
                pConcepto,
                hFecha,
                pFecha);
        add(vlDetalleMovimiento);
    }

}
