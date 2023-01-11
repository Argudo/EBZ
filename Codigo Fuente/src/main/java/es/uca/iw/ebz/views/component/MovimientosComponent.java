package es.uca.iw.ebz.views.component;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Movimiento.DatosMovimiento;
import es.uca.iw.ebz.Movimiento.Movimiento;
import es.uca.iw.ebz.Movimiento.MovimientoService;
import es.uca.iw.ebz.tarjeta.Tarjeta;
import es.uca.iw.ebz.usuario.cliente.Cliente;

public class MovimientosComponent extends Grid<DatosMovimiento> {
	public enum TipoGrid { Parcial, Completo; }
	
	TipoGrid _tipoGrid;
	MovimientoService _movService;
	Cuenta _cuenta;
	Cliente _cliente;
	Tarjeta _tarjeta;
	List<Movimiento> _aMovimientos = new ArrayList<Movimiento>();
	List<DatosMovimiento> _aDatosMovimiento = new ArrayList<DatosMovimiento>();
	GridListDataView<DatosMovimiento> _dataView;
	
	public MovimientosComponent(TipoGrid tipoGrid, MovimientoService movService, Object objCondition){
		if(movService != null) {
			_tipoGrid = tipoGrid;
			_movService = movService;
			
			if(objCondition != null) {			
				if(objCondition.getClass() == Cuenta.class) { _cuenta = (Cuenta) objCondition; _aMovimientos = _movService.findByCuentaOrderByFechaASC(_cuenta); }
				else if(objCondition.getClass() == Cliente.class) { _cliente = (Cliente) objCondition; _aMovimientos = _movService.findByClienteByFechaASC(_cliente); }
				else if(objCondition.getClass() == Tarjeta.class) { _tarjeta = (Tarjeta) objCondition; _aMovimientos = _movService.findByTarjetaOrderByASC(_tarjeta); }
			}
			
			_aMovimientos.forEach(m -> _aDatosMovimiento.add(_movService.datosMovimientoClass(m)));
			_dataView = setItems(_aDatosMovimiento);
			
			if(_tipoGrid == TipoGrid.Completo) {
				addColumn(DatosMovimiento::getTipo).setHeader(getTranslation("movement.type")).setSortable(true).setTextAlign(ColumnTextAlign.CENTER).setAutoWidth(true);
				addColumn(DatosMovimiento::getOrigen).setHeader(getTranslation("movement.origin")).setSortable(true).setTextAlign(ColumnTextAlign.CENTER).setAutoWidth(true);
				addColumn(DatosMovimiento::getDestino).setHeader(getTranslation("movement.destination")).setSortable(true).setTextAlign(ColumnTextAlign.CENTER).setAutoWidth(true);
			}
			
			//Grid parcial
			addColumn(DatosMovimiento::getConcepto).setHeader(getTranslation("movement.concept")).setAutoWidth(true);
			addColumn(DatosMovimiento::getImporteFormat).setHeader(getTranslation("movement.amount")).setSortable(true).setTextAlign(ColumnTextAlign.CENTER).setAutoWidth(true);
			if(_tipoGrid == TipoGrid.Parcial) {
				addColumn(new ComponentRenderer<>(mov -> {
					Button btnDetalles = new Button(VaadinIcon.EYE.create());
					btnDetalles.addThemeVariants(ButtonVariant.LUMO_ICON);
					btnDetalles.addClickListener(e -> {
						new MovimientoComponent(mov).open();
					});
					return btnDetalles;
				})).setHeader(getTranslation("movement.detalles")).setTextAlign(ColumnTextAlign.CENTER).setAutoWidth(true);			
			}
			if(_tipoGrid == TipoGrid.Completo) 
				addColumn(DatosMovimiento::getFecha).setHeader(getTranslation("movement.date")).setSortable(true).setTextAlign(ColumnTextAlign.CENTER).setAutoWidth(true);
		}
	}
	
	public GridListDataView<DatosMovimiento> getDataView() { return _dataView; }
	
	public void setCondition(Object objCondition) {
		_aMovimientos.clear();
		_aDatosMovimiento.clear();
		if(objCondition != null) {			
			if(objCondition.getClass() == Cuenta.class) { _cuenta = (Cuenta) objCondition; _aMovimientos = _movService.findByCuentaOrderByFechaASC(_cuenta); }
			else if(objCondition.getClass() == Cliente.class) { _cliente = (Cliente) objCondition; _aMovimientos = _movService.findByClienteByFechaASC(_cliente); }
			else if(objCondition.getClass() == Tarjeta.class) { _tarjeta = (Tarjeta) objCondition; _aMovimientos = _movService.findByTarjetaOrderByASC(_tarjeta); }	
		}
		
		_aMovimientos.forEach(m -> _aDatosMovimiento.add(_movService.datosMovimientoClass(m)));
		_dataView = setItems(_aDatosMovimiento);
		_dataView.refreshAll();
	}
}
