package es.uca.iw.ebz.views.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import es.uca.iw.ebz.Movimiento.DatosMovimiento;

public class MovimientoComponent extends Dialog {
	private DatosMovimiento _movimiento;
	private FormLayout frmMain = new FormLayout();
		private TextField txtOrigen = new TextField(getTranslation("movement.origin"));
		private TextField txtDestino = new TextField(getTranslation("movement.destination"));
		private TextField txtCantidad = new TextField(getTranslation("movement.amount"));
		private Button btnCerrar = new Button(getTranslation("close"));
	
	public MovimientoComponent(DatosMovimiento movimiento) {
		_movimiento = movimiento;
		setMinWidth("700px");
		setHeaderTitle("Movimiento " + _movimiento.getConcepto());
		getHeader().add(new Paragraph("[" + movimiento.getFecha().getDate() + "/" + (movimiento.getFecha().getMonth()+1) + "/" + (movimiento.getFecha().getYear()+1900) + "]"));
		getFooter().add(btnCerrar);
		
		txtOrigen.setValue(_movimiento.getOrigen());
		txtDestino.setValue(_movimiento.getDestino());
		txtCantidad.setValue(_movimiento.getImporte());
		Div euroSuffix = new Div();
        euroSuffix.setText("€");
        txtCantidad.setSuffixComponent(euroSuffix);
		btnCerrar.addThemeVariants(ButtonVariant.LUMO_ERROR);
		btnCerrar.addClickListener(e -> close());
		frmMain.setResponsiveSteps(new ResponsiveStep("0", 9));
		frmMain.setColspan(txtOrigen, 4);
		frmMain.setColspan(txtDestino, 4);
		frmMain.add(txtOrigen, VaadinIcon.ARROW_RIGHT.create(), txtDestino);
		
		HorizontalLayout hlCantidad = new HorizontalLayout();
		hlCantidad.setWidthFull();
		hlCantidad.setJustifyContentMode(JustifyContentMode.CENTER);
		hlCantidad.add(txtCantidad);
		
		add(new Hr(), frmMain, hlCantidad);
	}
}
