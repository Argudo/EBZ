package es.uca.iw.ebz.tarjeta;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Prepago implements Serializable {
	@Id
	@OneToOne
	Tarjeta _tarjeta;
	@Column(name = "saldo")
	float _fSaldo;
}
