package es.uca.iw.ebz.cliente;

import java.util.Date;
import java.util.UUID;

import javax.persistence.*;

import org.hibernate.annotations.Type;

@Entity
public class Cliente {
	@Id
	@GeneratedValue
	private UUID Id;

	//Atributos de inicio
	private String sUsuario;
	private String sContraseña;

	//Datos pertinentes
	private String sNombre;
	private Date dFechaNacimiento;
	private Date dFechaRegistro;
	private Date dFechaEliminacion;

	//Relaciones
	@OneToMany
	@JoinColumn(name = "cliente_id")
	private List<Cuenta> aCuentas;
	private TipoCliente eTipoCliente;

	public Cliente(){}
}
