package es.uca.iw.ebz.tarjeta;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TipoTarjeta {
	@Id
	@Column(name = "id")
	Integer _iId;
	
	@Column(name = "Nombre")
	String _sNombre;
	
	public TipoTarjeta(){}
	
	public TipoTarjeta(EnumTarjeta enumTipo){
		_iId = EnumTarjeta.toInt(enumTipo);
		_sNombre = enumTipo.name();
	}
	
	public TipoTarjeta(int id, String nombre) {
		_iId = id;
		_sNombre = nombre; 
	}

	public EnumTarjeta getTipo() { return EnumTarjeta.toTipo(_iId); }
}
