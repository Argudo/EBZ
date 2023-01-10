package es.uca.iw.ebz.tarjeta.prepago;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import es.uca.iw.ebz.tarjeta.Tarjeta;
import org.springframework.data.annotation.Version;

@Entity
public class Prepago {
	@Id
	@GeneratedValue
	@Column(length=16, name = "id")
	private UUID _iId;
	
	@OneToOne
	@NotNull
	Tarjeta _tarjeta;
	
	@Column(name = "saldo")
	@NotNull
	float _fSaldo;

	@Version
	private Integer version;
	
	public Prepago() {}
	
	public Prepago(Tarjeta tarjeta) {
		_tarjeta = tarjeta;
		_fSaldo = 0;
	}

	public UUID getId() { return _iId; }
	public Tarjeta getTarjeta() { return _tarjeta; }
	public float getSaldo() { return _fSaldo; }
	public void setSaldo(float fSaldo) { _fSaldo = fSaldo; }
	public void setVersion(Integer version) {this.version = version;}
	public Integer getVersion() {return version;}
}
