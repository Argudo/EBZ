package es.uca.iw.ebz.cliente;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import es.uca.iw.ebz.Cuenta.Cuenta;

import org.hibernate.annotations.Type;

@Entity
public class Cliente {
	@Id
	@GeneratedValue
	private UUID Id;

	//Atributos de inicio
	@NotNull
	@Column(name = "usuario")
	private String sUsuario;
	public String getUsuario(){ return this.sUsuario; }
	public void setUsuario (String usuario){ this.sUsuario = usuario; }
	@NotNull
	@Column(name = "password")
	private String sContraseña;
	public String getContraseña (){ return this.sContraseña; }
	public void setContraseña (String contraseña){ this.sContraseña = contraseña; }

	//Datos pertinentes
	@NotNull
	@Column(name = "nombre")
	private String sNombre;
	@NotNull
	@Column(name = "fecha_nacimiento")
	private Date dFechaNacimiento;
	@NotNull
	@Column(name = "fecha_registro")
	private Date dFechaRegistro;
	@Column(name = "fecha_eliminacion")
	private Date dFechaEliminacion;

	//Relaciones
	@OneToMany
	@JoinColumn(name = "cliente_id")
	@Column(name = "cuentas")
	private List<Cuenta> aCuentas;
	@NotNull
	@Column(name = "tipo_cliente")
	private TipoCliente eTipoCliente;


	public Cliente(){}
}
