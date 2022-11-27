package es.uca.iw.ebz.tarjeta;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
public class Credito implements Serializable {
	@Id
	@OneToOne
	Tarjeta _tarjeta;
	
	@Column(name = "Deuda")
	float _fDeuda;
	
	@OneToOne
	TipoCrediticio _tipo;
}
