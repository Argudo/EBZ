package es.uca.iw.ebz.views.main;


import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import es.uca.iw.ebz.views.main.layout.MainLayout;

@PageTitle("Cuenta")
@Route(value = "cuenta", layout = MainLayout.class)

public class CuentaView extends VerticalLayout {

    //@Autowired
    //Cuenta account;

    public CuentaView(){

        setMargin(false);
        setPadding(false);
        setSpacing(true);
        setWidthFull();
        setAlignItems(FlexComponent.Alignment.CENTER);

        //First layout section (Account number, balance and buttons for redirecting)

        VerticalLayout vlAccount = new VerticalLayout();
        vlAccount.setWidth("80vw");
        vlAccount.setHeight("24vw");
        vlAccount.setPadding(false);
        vlAccount.setSpacing(true);
        vlAccount.setMargin(true);
        vlAccount.setClassName("box");

        H1 hAccount = new H1("Cuenta");
        hAccount.setClassName("title");

        Component acNumBalance = ShowAccount("XXXX XXXX XXXX XXXX", (float) 100000.45);

        //Button section
        FlexLayout flAccountButtons = new FlexLayout();
        flAccountButtons.setWidthFull();
        flAccountButtons.setFlexDirection(FlexLayout.FlexDirection.ROW);
        flAccountButtons.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        flAccountButtons.setJustifyContentMode(JustifyContentMode.EVENLY);

        Component cTarjeta = CreateButton("Tarjetas", VaadinIcon.CREDIT_CARD);
        Component cEstadisticas = CreateButton("Estadísticas", VaadinIcon.BAR_CHART_H);
        Component cMovimientos = CreateButton("Movimientos", VaadinIcon.EXCHANGE);
        Component cTransferencias = CreateButton("Transferencias", VaadinIcon.MONEY_EXCHANGE);
        flAccountButtons.add(
                cTarjeta,
                cEstadisticas,
                cMovimientos,
                cTransferencias
        );
        //End button section

        vlAccount.add(
                hAccount,
                acNumBalance,
                flAccountButtons);

        //End first layout section


        //Second layout section (Account details)

        Component accountDetails = createAccountDetails();

        //End second layout section

        add(vlAccount);
        add(accountDetails);

    }

    //For button's creation
    private Component CreateButton(String sName, VaadinIcon vI) {
        VerticalLayout vlMain = new VerticalLayout();
        vlMain.setAlignItems(FlexComponent.Alignment.CENTER);
        vlMain.setSpacing(false);
        vlMain.setWidth("min-width");
        Button btnFunc = new Button();
        Icon icon = new Icon(vI);
        btnFunc.getElement().appendChild(icon.getElement());
        Paragraph pDescription = new Paragraph(sName);


        vlMain.add(
                btnFunc,
                pDescription
        );

        return vlMain;
    }

    //For account number and balance with its correct format
    private Component ShowAccount(String acNumber, float acBalance) {
        VerticalLayout vlMain = new VerticalLayout();
        vlMain.setAlignItems(Alignment.CENTER);
        vlMain.setSpacing(false);
        vlMain.setWidth("100%");

        NumberFormat formatImport = NumberFormat.getCurrencyInstance();

        H2 _acNumber = new H2(acNumber);
        H3 _acBalance = new H3(formatImport.format(acBalance));
        vlMain.add(
                _acNumber,
                _acBalance
        );

        return vlMain;

    }

    private Component createAccountDetails(){

        VerticalLayout vlAccountDetails = new VerticalLayout();
        vlAccountDetails.setWidth("80vw");
        vlAccountDetails.setPadding(true);
        vlAccountDetails.setSpacing(true);
        vlAccountDetails.setMargin(true);
        vlAccountDetails.setClassName("box");

        NumberFormat formatImport = NumberFormat.getCurrencyInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");

        H1 hDetails = new H1("Detalles de la cuenta");
        hDetails.setClassName("title");

        H3 hAccountNumber = new H3("Número de cuenta");
        Paragraph pAccountNumber = new Paragraph("XXXX XXXX XXXX XXXX");
        //Paragraph pAccountNumber = new Paragraph(account.getNumeroCuenta());
        H3 hBalance = new H3("Saldo");
        Paragraph pBalance = new Paragraph("100.000,45€");
        //Paragraph pBalance = new Paragraph(formatImport.format(account.getSaldo()));
        H3 hCreationDate = new H3("Fecha de creación");
        Paragraph pCreationDate = new Paragraph("Fecha");
        //Paragraph pCreationDate = new Paragraph(dateFormat.format(account.getFechaCreacion()));
        H3 hAccountHolder = new H3("Titular de la cuenta");
        Paragraph pAccountHolder = new Paragraph("Nombre y apellidos");

        vlAccountDetails.add(
                hDetails,
                hAccountNumber,
                pAccountNumber,
                hBalance,
                pBalance,
                hAccountHolder,
                pAccountHolder,
                hCreationDate,
                pCreationDate
        );

        return vlAccountDetails;

    }

}
