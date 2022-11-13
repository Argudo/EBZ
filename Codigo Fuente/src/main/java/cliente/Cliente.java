package cliente;

import java.util.UUID;

import javax.persistence.*;

import org.hibernate.annotations.Type;

@Entity
public class Cliente {
	@Id
	@GeneratedValue
	@Type(type = "uuid-char")
	UUID Id;
	
	String sNombre;
	public Cliente() {
		
	}
}
