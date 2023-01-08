package es.uca.iw.ebz.views.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import es.uca.iw.ebz.Cuenta.Cuenta;
import com.vaadin.flow.component.dialog.Dialog;
import es.uca.iw.ebz.usuario.cliente.Cliente;

import java.text.NumberFormat;

public class DetallesCuentaDialog extends Dialog {

    private Cliente _cliente;

    private Cuenta _cuenta;

    //Dialog elements section
    private H3 hNumCuenta = new H3("Número de cuenta");

    private Paragraph pNumCuenta = new Paragraph();

    private H3 hSaldo = new H3("Saldo");

    private Paragraph pBalance = new Paragraph();

    private H3 hFechaCreacion = new H3("Fecha de creación");

    private Paragraph pFechaCreacion = new Paragraph();

    private H3 hTitular = new H3("Titular de la cuenta");

    private Paragraph pTitular = new Paragraph();


    public DetallesCuentaDialog(Cliente cliente, Cuenta cuenta) {

        //Services initialization section
        _cliente = cliente;
        _cuenta = cuenta;
        //End services initialization section

        //Dialog elements initialization section
        pNumCuenta.setText(_cuenta.getNumeroCuenta());
        NumberFormat formatImport = NumberFormat.getCurrencyInstance();
        pBalance.setText(formatImport.format(_cuenta.getSaldo()));
        pFechaCreacion.setText(_cuenta.getFechaCreacion().toString());
        pTitular.setText(_cliente.getNombre());
        //End dialog elements initialization section

        //Dialog configuration section
        setWidth("30vw");
        setHeaderTitle("Detalles de la cuenta");

        Button btnCancelar = new Button(new Icon(VaadinIcon.CLOSE));
        btnCancelar.addThemeVariants(ButtonVariant.LUMO_ICON);
        btnCancelar.getElement().addEventListener("click", e -> close());
        getHeader().add(btnCancelar);
        //End dialog configuration section

        //Elements layout section
        VerticalLayout vlInfo = new VerticalLayout();
        vlInfo.setWidthFull();
        /*vlInfo.setPadding(true);
        vlInfo.setMargin(true);
        vlInfo.setSpacing(false);*/
        vlInfo.add(
               hNumCuenta,
               pNumCuenta,
               hSaldo,
               pBalance,
               hTitular,
               pTitular,
               hFechaCreacion,
               pFechaCreacion);

        //End elements layout section

        add(new Hr(), vlInfo);

    }


}
