package es.uca.iw.ebz.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
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
import es.uca.iw.ebz.views.Security.AuthenticatedUser;
import es.uca.iw.ebz.views.layout.MainLayout;
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

        H1 hTitle = new H1(getTranslation("transfer.title"));
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

        cbAccount1 = new ComboBox<String>(getTranslation("transfer.from"));
        cbAccount1.setItems(asAccounts);
        cbAccount1.setRequired(true);
        cbAccount1.setRequiredIndicatorVisible(true);
        cbAccount1.addValueChangeListener( e -> {
            if(cbAccount1.getValue() == null){
                cbAccount1.getElement().setAttribute("invalid","");
            }
        });

        cbAccount2 = new ComboBox<String>(getTranslation("transfer.to"));
        cbAccount2.setItems(asAccounts);
        cbAccount2.setRequired(true);
        cbAccount2.setRequiredIndicatorVisible(true);
        cbAccount2.setErrorMessage(getTranslation("transfer.obto"));
        cbAccount2.addValueChangeListener( e -> {
            if(cbAccount2.getValue() == null){
                cbAccount1.getElement().setAttribute("invalid","");
            }
        });

        tfDestinyAccount = new TextField(getTranslation("transfer.to"));
        tfDestinyAccount.setRequired(true);
        tfDestinyAccount.setRequiredIndicatorVisible(true);
        tfDestinyAccount.setMinLength(20);
        tfDestinyAccount.setErrorMessage(getTranslation("transfer.obto"));
        tfDestinyAccount.addValueChangeListener( e -> {
            if(tfDestinyAccount.getValue() == null || tfDestinyAccount.getValue().length() < tfDestinyAccount.getMinLength()){
                tfDestinyAccount.getElement().setAttribute("invalid","");
            }
        });


        tfConcept = new TextField(getTranslation("transfer.concept"));
        tfConcept.setRequired(true);
        tfConcept.setRequiredIndicatorVisible(true);
        tfConcept.setMinLength(10);
        tfConcept.setErrorMessage(getTranslation("transfer.obconcept"));
        tfConcept.addValueChangeListener( e -> {
            if(tfConcept.getValue() == null || tfConcept.getValue().length() < tfConcept.getMinLength()){
                tfConcept.getElement().setAttribute("invalid","");
            }
        });


        nfBalance = new NumberField(getTranslation("transfer.amount"));
        Div euroSuffix = new Div();
        euroSuffix.setText("€");
        nfBalance.setSuffixComponent(euroSuffix);
        nfBalance.setRequiredIndicatorVisible(true);
        nfBalance.setMin(0.01);
        nfBalance.setErrorMessage(getTranslation("transfer.noamount"));
        nfBalance.addValueChangeListener( e -> {
            if(nfBalance.getValue() == null || nfBalance.getValue() < nfBalance.getMin()){
                nfBalance.getElement().setAttribute("invalid","");
            }
        });

        btnTrans = new Button(getTranslation("transfer.button"));

        //End transference form fields section

        rdGroup = new RadioButtonGroup<>();
        rdGroup.setLabel(getTranslation("transfer.type"));
        rdGroup.setItems(getTranslation("transfer.t1"), getTranslation("transfer.t2"));
        rdGroup.addValueChangeListener(e -> {

            frmTransfer.removeAll();
            if(rdGroup.getValue() == getTranslation("transfer.t1")){

                frmTransfer.add(
                        rdGroup,
                        cbAccount1,
                        cbAccount2,
                        tfConcept,
                        nfBalance,
                        btnTrans);

                btnTrans.addClickListener( ev -> {

                    boolean fail = false;

                    if(cbAccount1.getValue() == null){
                        cbAccount1.getElement().setAttribute("invalid","");
                        fail = true;
                    }
                    if(cbAccount2.getValue() == null || cbAccount2.getValue() == cbAccount1.getValue()){
                        tfDestinyAccount.getElement().setAttribute("invalid","");
                        fail = true;
                    }
                    if(tfConcept.getValue() == null || tfConcept.getValue().length() < tfConcept.getMinLength()){
                        tfConcept.getElement().setAttribute("invalid","");
                        fail = true;
                    }
                    if(nfBalance.getValue() == null || nfBalance.getValue() < nfBalance.getMin()){
                        nfBalance.getElement().setAttribute("invalid","");
                        fail = true;
                    }
                    if(!fail){
                        Movimiento mv = new Movimiento(new Date(),tfConcept.getValue(), TipoMovimiento.INTERNO);
                        System.out.println(mv.getConcepto() + " " + mv.getTipo());
                        try {
                            _movimientoService.nuevoMovimiento(mv, _cuentaService.findByNumeroCuenta(cbAccount1.getValue()).get(), cbAccount2.getValue(), nfBalance.getValue().floatValue());
                        } catch (Exception ex) {
                            Notification notification = Notification.show("Error: " + ex.getMessage());
                            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                        }

                        CargarFormulario(true);
                        Notification notification = Notification.show(getTranslation("transfer.correct2"));
                        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

                    }

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

                    boolean fail = false;

                    if(cbAccount1.getValue() == null){
                        cbAccount1.getElement().setAttribute("invalid","");
                        fail = true;
                    }
                    if(tfDestinyAccount.getValue() == null || tfDestinyAccount.getValue().length() < tfDestinyAccount.getMinLength()){
                        tfDestinyAccount.getElement().setAttribute("invalid","");
                        fail = true;
                    }
                    if(tfConcept.getValue() == null || tfConcept.getValue().length() < tfConcept.getMinLength()){
                        tfConcept.getElement().setAttribute("invalid","");
                        fail = true;
                    }
                    if(nfBalance.getValue() == null || nfBalance.getValue() < nfBalance.getMin()){
                        nfBalance.getElement().setAttribute("invalid","");
                        fail = true;
                    }
                    if(!fail){
                        Movimiento mv = new Movimiento(new Date(),tfConcept.getValue(), TipoMovimiento.EXTERNO);
                        try {
                            _movimientoService.nuevoMovimiento(mv, _cuentaService.findByNumeroCuenta(cbAccount1.getValue()).get(), tfDestinyAccount.getValue(), nfBalance.getValue().floatValue());
                        } catch (Exception ex) {
                            Notification notification = Notification.show("Error: " + ex.getMessage());
                            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                        }
                        CargarFormulario(false);

                        btnTrans.getUI().ifPresent(ui ->
                                ui.navigate(""));

                        Notification notification = Notification.show(getTranslation("transfer.correct2"));
                        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    }



                });

            }


        });
        frmTransfer.add(rdGroup);
        vlTitle.add(hlSeparator, frmTransfer);
        add(vlTitle);

    }

    private void CargarFormulario(boolean b){
        if(b){ //Si es traspaso
            cbAccount2.clear();
            cbAccount2.getElement().removeAttribute("invalid");
        }else{ //Si es transferencia
            tfDestinyAccount.clear();
            tfDestinyAccount.getElement().removeAttribute("invalid");
        }
        cbAccount1.clear();
        cbAccount1.getElement().removeAttribute("invalid");
        tfConcept.clear();
        tfConcept.getElement().removeAttribute("invalid");
        nfBalance.clear();
        nfBalance.getElement().removeAttribute("invalid");
    }


}