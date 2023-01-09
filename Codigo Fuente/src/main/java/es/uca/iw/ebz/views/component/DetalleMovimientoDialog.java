package es.uca.iw.ebz.views.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import es.uca.iw.ebz.Movimiento.DatosMovimiento;

public class DetalleMovimientoDialog extends Dialog {
    private DatosMovimiento _movimiento;
    private H3 hFecha = new H3(getTranslation("movement.date"));
    private H3 hTipo = new H3(getTranslation("movement.type"));
    private H3 hOrigen = new H3(getTranslation("movement.origin"));
    private H3 hDestino = new H3(getTranslation("movement.destination"));
    private H3 hImporte = new H3(getTranslation("movement.amount"));
    private H3 hConcepto = new H3(getTranslation("movement.concept"));
    private Paragraph pFecha = new Paragraph();
    private Paragraph pTipo = new Paragraph();
    private Paragraph pOrigen = new Paragraph();
    private Paragraph pDestino = new Paragraph();
    private Paragraph pImporte = new Paragraph();
    private Paragraph pConcepto = new Paragraph();

    public DetalleMovimientoDialog(DatosMovimiento movimiento) {
        _movimiento = movimiento;
        pFecha.setText(_movimiento.getFecha().toString());
        pTipo.setText(_movimiento.getTipo());
        pOrigen.setText(_movimiento.getOrigen());
        pDestino.setText(_movimiento.getDestino());
        pImporte.setText(_movimiento.getImporte().toString());
        pConcepto.setText(_movimiento.getConcepto());

        Button btnCancelar = new Button(new Icon(VaadinIcon.CLOSE));
        btnCancelar.addThemeVariants(ButtonVariant.LUMO_ICON);
        btnCancelar.getElement().addEventListener("click", e -> close());
        getHeader().add(btnCancelar);

        setWidth("30vw");
        setHeaderTitle(getTranslation("movement.details"));

        VerticalLayout vlInfo = new VerticalLayout();
        vlInfo.setWidthFull();

        vlInfo.add(
                hFecha,
                pFecha,
                hTipo,
                pTipo,
                hOrigen,
                pOrigen,
                hDestino,
                pDestino,
                hImporte,
                pImporte,
                hConcepto,
                pConcepto
        );
        add(new Hr(), vlInfo);
    }
}
