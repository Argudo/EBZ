package es.uca.iw.ebz.tarjeta.credito;

import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;

import es.uca.iw.ebz.tarjeta.Tarjeta;
import es.uca.iw.ebz.tarjeta.TarjetaService;

@Entity
public class Credito {
	@Id
	@GeneratedValue
	@Column(length=16, name = "id")
	private UUID _iId;
	
	@OneToOne
	@NotNull
	Tarjeta _tarjeta;
	
	@Column(name = "Deuda")
	@NotNull
	float _fDeuda;
	
	@OneToOne
	@NotNull
	TipoCrediticio _tipo;

	@Version
	private Integer version;
	
	public Credito() {}
	
	public Credito(Tarjeta tarjeta, TipoCrediticio tipo) {
		_tarjeta = tarjeta;
		_tipo = tipo;
		_fDeuda = 0;
	}

	//Getters and setters
	public UUID getId() { return _iId; }
	public Tarjeta getTarjeta() { return _tarjeta; }
	public float getDeuda() { return _fDeuda; }
	public void setDeuda(float fDeuda) { _fDeuda = fDeuda; }
	public TipoCrediticio getTipo() { return _tipo; }
	public void setTipo(TipoCrediticio tipo) { _tipo = tipo; }

	public void setVersion(Integer version) {this.version = version;}
	public Integer getVersion() {return version;}
}
