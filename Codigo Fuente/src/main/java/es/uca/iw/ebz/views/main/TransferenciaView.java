package es.uca.iw.ebz.views.main;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.ebz.Cuenta.CuentaService;
import es.uca.iw.ebz.Movimiento.MovimientoService;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteService;
import es.uca.iw.ebz.views.main.Security.AuthenticatedUser;
import es.uca.iw.ebz.views.main.layout.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.awt.*;

@PageTitle("transferencia")
@Route(value = "transferencia", layout = MainLayout.class)
@RolesAllowed({ "Cliente" })
public class TransferenciaView extends VerticalLayout {

    @Autowired
    private MovimientoService _movimientoService;

    @Autowired
    private AuthenticatedUser _authenticatedUser;

    @Autowired
    private ClienteService _clienteService;

    @Autowired
    private CuentaService _cuentaService;

    private Cliente _cliente;

    private ComboBox cbAccount1;

    private ComboBox cbAccount2;

    private TextField tfConcept;

    private NumberField nfBalance;

    private TextField tfDestinyAccount;


    public TransferenciaView(MovimientoService _movimientoService, CuentaService _cuentaService,
                             ClienteService _clienteService, AuthenticatedUser _authenticatedUser){

        //Services initialization section
        this._movimientoService = _movimientoService;
        this._cuentaService = _cuentaService;
        this._clienteService = _clienteService;
        this._authenticatedUser = _authenticatedUser;
        //End services initialization section

        //Client asignation
        _cliente = _clienteService.findByUsuario(_authenticatedUser.get().get());

        setMargin(false);
        setPadding(false);
        setSpacing(true);
        setWidthFull();
        setAlignItems(FlexComponent.Alignment.CENTER);

        //Transference form section
        VerticalLayout vlForm = new VerticalLayout();
        vlForm.setWidth("70%");
        vlForm.setAlignItems(Alignment.CENTER);
        vlForm.setSpacing(true);
        vlForm.setPadding(true);
        vlForm.setMargin(true);
        vlForm.setClassName("box");

        FormLayout frmTransfer = new FormLayout();
        frmTransfer.setWidthFull();
        frmTransfer.setResponsiveSteps(

                new FormLayout.ResponsiveStep("0",1)

        );

        //Transference form fields section
        cbAccount1 = new ComboBox<>("Cuenta origen");
        cbAccount1.setItems(_cuentaService.findByCliente(_cliente));
        cbAccount2 = new ComboBox<>("Cuenta destino");
        cbAccount2.setItems(_cuentaService.findByCliente(_cliente));

        tfDestinyAccount = new TextField("Cuenta destino");
        tfConcept = new TextField("Concepto");

        nfBalance = new NumberField("Cantidad a transferir");
        Div euroSuffix = new Div();
        euroSuffix.setText("€");
        nfBalance.setSuffixComponent(euroSuffix);

        //End transference form fields section

        RadioButtonGroup<String> rdGroup = new RadioButtonGroup<>();
        rdGroup.setLabel("Tipo de transferencia");
        rdGroup.setItems("Traspaso (entre cuentas propias)", "Transferencia (a una cuenta ajena)");
        rdGroup.addValueChangeListener(e -> {
           frmTransfer.removeAll();
           if(rdGroup.getValue() == "Traspaso (entre cuentas propias)"){
               frmTransfer.add(
                       rdGroup,
                       cbAccount1,
                       cbAccount2,
                       tfConcept,
                       nfBalance);
           }else{
               frmTransfer.add(
                       rdGroup,
                       cbAccount1,
                       tfDestinyAccount,
                       tfConcept,
                       nfBalance);
           }


        });
        frmTransfer.add(rdGroup);
        vlForm.add(frmTransfer);
        add(vlForm);

    }


}
