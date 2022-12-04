package es.uca.iw.ebz.views.main;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Movimiento")
@Route(value = "movimiento", layout = MainLayout.class)
public class MovimientoView extends VerticalLayout {
    private VerticalLayout vlDetalleMovimiento = new VerticalLayout();

    private H3 hOrigen = new H3("Origen");
    private H3 hDestino = new H3("Destino");
    private H3 hImporte = new H3("Importe");
    private H3 hConcepto = new H3("Concepto");
    private H3 hFecha = new H3("Fecha");

    private Paragraph pOrigen = new Paragraph();
    private Paragraph pDestino = new Paragraph();
    private Paragraph pImporte = new Paragraph();
    private Paragraph pConcepto = new Paragraph();
    private Paragraph pFecha = new Paragraph();
    public MovimientoView () {
        H1 hMovimiento = new H1("| Detalle del movimiento");
        hMovimiento.setClassName("title");

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
