package es.uca.iw.ebz.tarjeta;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;

@Entity
public class TipoCrediticio {
	@Id
	@Column(name = "id")
	Integer _iId;
	
	@Column(name = "nombre")
	String _sNombre;
	
	@Column(name = "limite")
	float _fLimite;

	public TipoCrediticio(int iId, String sNombre, float fLimite) {
		_iId = iId;
		_sNombre = sNombre;
		_fLimite = fLimite;
	}
}
