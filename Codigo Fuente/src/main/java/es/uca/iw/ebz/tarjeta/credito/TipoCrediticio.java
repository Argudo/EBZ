package es.uca.iw.ebz.tarjeta.credito;

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

	public TipoCrediticio(){}
	
	public TipoCrediticio(int iId, String sNombre, float fLimite) {
		_iId = iId;
		_sNombre = sNombre;
		_fLimite = fLimite;
	}

	public Integer getId() { return _iId; }
	public String getNombre() { return _sNombre; }
	public float getLimite() { return _fLimite; }
}
