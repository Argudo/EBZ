package es.uca.iw.ebz.cliente;

import java.util.Date;
import java.util.UUID;

import javax.persistence.*;

import org.hibernate.annotations.Type;

@Entity
public class Cliente {
	@Id
	@GeneratedValue
	@Type(type = "uuid-char")
	UUID Id;

	String sUsuario;
	String Contraseña;

	String sNombre;
	Date dFechaNacimiento;
	Date dFechaRegistro;
	Date dFechaEliminacion;

	public Cliente() {
		
	}
}
