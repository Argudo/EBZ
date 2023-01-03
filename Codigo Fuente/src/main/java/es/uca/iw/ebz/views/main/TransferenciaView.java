package es.uca.iw.ebz.views.main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Cuenta.CuentaService;
import es.uca.iw.ebz.Movimiento.Movimiento;
import es.uca.iw.ebz.Movimiento.MovimientoService;
import es.uca.iw.ebz.Movimiento.TipoMovimiento;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteService;
import es.uca.iw.ebz.views.main.Security.AuthenticatedUser;
import es.uca.iw.ebz.views.main.layout.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@PageTitle("Transferencias")
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

    private ComboBox<String> cbAccount1;

    private ComboBox<String> cbAccount2;

    private TextField tfConcept;

    private NumberField nfBalance;

    private TextField tfDestinyAccount;

    private FormLayout frmTransfer;

    private RadioButtonGroup<String> rdGroup;

    private Button btnTrans;


    public TransferenciaView(MovimientoService _movimientoService, CuentaService _cuentaService,
                             ClienteService _clienteService, AuthenticatedUser _authenticatedUser){

        //Services initialization section
        this._movimientoService = _movimientoService;
        this._cuentaService = _cuentaService;
        this._clienteService = _clienteService;
        this._authenticatedUser = _authenticatedUser;
        //End services initialization section

        //Main layout section
        setMargin(false);
        setPadding(false);
        setSpacing(true);
        setWidthFull();
        setAlignItems(FlexComponent.Alignment.CENTER);
        //End main layout section

        //Title section
        VerticalLayout vlTitle = new VerticalLayout();
        vlTitle.setWidth("70%");
        vlTitle.setSpacing(true);
        vlTitle.setPadding(true);
        vlTitle.setMargin(true);
        vlTitle.setClassName("box");

        H1 hTitle = new H1("| Transferencia");
        hTitle.setClassName("title");
        vlTitle.add(hTitle);

        //End title section

        //Separator section
        HorizontalLayout hlSeparator = new HorizontalLayout();
        hlSeparator.setWidth("2px");
        hlSeparator.getStyle().set("background-color", "var(--lumo-contrast-10pct)");
        hlSeparator.getStyle().set("padding", "0");
        //End separator section

        //Client asignation
        _cliente = _clienteService.findByUsuario(_authenticatedUser.get().get());

        //Transference form section
        VerticalLayout vlForm = new VerticalLayout();
        vlForm.setWidth("70%");
        vlForm.setAlignItems(Alignment.CENTER);
        vlForm.setSpacing(true);
        vlForm.setPadding(true);
        vlForm.setMargin(true);
        vlForm.setClassName("box");

        frmTransfer = new FormLayout();
        frmTransfer.setWidthFull();
        frmTransfer.setResponsiveSteps(

                new FormLayout.ResponsiveStep("0",1)

        );

        //Transference form fields section
        List<String>  asAccounts = new ArrayList<>();
        List<Cuenta> lstAccounts = _cuentaService.findByCliente(_cliente);

        for(Cuenta c: lstAccounts){
            asAccounts.add(c.getNumeroCuenta());
        }

        cbAccount1 = new ComboBox<String>("Cuenta origen");
        cbAccount1.setItems(asAccounts);
        cbAccount1.setRequiredIndicatorVisible(true);
        cbAccount1.setErrorMessage("La cuenta origen es obligatoria");

        cbAccount2 = new ComboBox<String>("Cuenta destino");
        cbAccount2.setItems(asAccounts);
        cbAccount2.setRequiredIndicatorVisible(true);
        cbAccount2.setErrorMessage("La cuenta destino es obligatoria");

        tfDestinyAccount = new TextField("Cuenta destino");
        tfDestinyAccount.setRequiredIndicatorVisible(true);
        tfDestinyAccount.setMinLength(20);
        tfDestinyAccount.setErrorMessage("La cuenta destino es obligatoria");

        tfConcept = new TextField("Concepto (mínimo 10 caracteres)");
        tfConcept.setRequiredIndicatorVisible(true);
        tfConcept.setMinLength(10);
        tfConcept.setErrorMessage("El concepto no cumple con el mínimo de caracteres.");

        nfBalance = new NumberField("Cantidad a transferir");
        Div euroSuffix = new Div();
        euroSuffix.setText("€");
        nfBalance.setSuffixComponent(euroSuffix);
        nfBalance.setRequiredIndicatorVisible(true);
        nfBalance.setMin(0.01);
        nfBalance.setErrorMessage("El importe es obligatorio");

        btnTrans = new Button("Transferir");

        //End transference form fields section

        rdGroup = new RadioButtonGroup<>();
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
                        nfBalance,
                        btnTrans);

                btnTrans.addClickListener( ev -> {

                    /*if(cbAccount1.getValue() == cbAccount1.getEmptyValue()){
                        CargarFormulario(true);
                        frmTransfer.add(new Paragraph("La cuenta origen no está seleccionada."));
                    }else if(cbAccount2.getValue() == cbAccount2.getEmptyValue()){
                        CargarFormulario(true);
                        frmTransfer.add(new Paragraph("La cuenta destino no está seleccionada."));
                    }else if(tfConcept.getValue() == tfConcept.getEmptyValue()){
                        CargarFormulario(true);
                        frmTransfer.add(new Paragraph("El concepto no cumple con el mínimo de caracteres o no es obligatorio."));
                    }else if(nfBalance.getValue() == nfBalance.getEmptyValue()){
                        CargarFormulario(true);
                        frmTransfer.add(new Paragraph("El importe es obligatorio."));
                    }else{*/
                        Movimiento mv = new Movimiento(new Date(),tfConcept.getValue(), TipoMovimiento.INTERNO);
                        _movimientoService.añadirMovimientoCuenta(mv, _cuentaService.findByNumeroCuenta(cbAccount1.getValue()).get(), cbAccount2.getValue(), nfBalance.getValue().floatValue());
                        CargarFormulario(true);
                        frmTransfer.add(new Paragraph("El traspaso se ha realizado correctamente."));
                    //}

                });



            }else{

                frmTransfer.add(
                        rdGroup,
                        cbAccount1,
                        tfDestinyAccount,
                        tfConcept,
                        nfBalance,
                        btnTrans);

                btnTrans.addClickListener( ev -> {

                    /*if(cbAccount1.getValue() == cbAccount1.getEmptyValue()){
                        CargarFormulario(false);
                        frmTransfer.add(new Paragraph("La cuenta origen no está seleccionada."));
                    }else if(tfDestinyAccount.getValue() == tfDestinyAccount.getEmptyValue()){
                        CargarFormulario(false);
                        frmTransfer.add(new Paragraph("La cuenta destino no está seleccionada."));
                    }else if(tfConcept.getValue() == tfConcept.getEmptyValue()){
                        CargarFormulario(false);
                        frmTransfer.add(new Paragraph("El concepto no cumple con el mínimo de caracteres."));
                    }else if(nfBalance.getValue() == nfBalance.getEmptyValue()){
                        CargarFormulario(false);
                        frmTransfer.add(new Paragraph("El importe es obligatorio."));
                    }else{*/
                        Movimiento mv = new Movimiento(new Date(),tfConcept.getValue(), TipoMovimiento.EXTERNO);
                        _movimientoService.añadirMovimientoCuenta(mv, _cuentaService.findByNumeroCuenta(cbAccount1.getValue()).get(), tfDestinyAccount.getValue(), nfBalance.getValue().floatValue());
                        CargarFormulario(false);
                        frmTransfer.add(new Paragraph("La transferencia se ha realizado correctamente."));
                   // }

                });

            }


        });
        frmTransfer.add(rdGroup);
        vlTitle.add(hlSeparator, frmTransfer);
        add(vlTitle);

    }

    private void CargarFormulario(boolean b){
        if(b){ //Si es traspaso
            frmTransfer.removeAll();
            cbAccount1.clear();
            cbAccount2.clear();
            tfConcept.clear();
            nfBalance.clear();
            frmTransfer.add(rdGroup, cbAccount1, cbAccount2, tfConcept, nfBalance, btnTrans);
        }else{ //Si es transferencia
            frmTransfer.removeAll();
            cbAccount1.clear();
            tfDestinyAccount.clear();
            tfConcept.clear();
            nfBalance.clear();
            frmTransfer.add(rdGroup, cbAccount1, tfDestinyAccount, tfConcept, nfBalance, btnTrans);
        }
    }


}