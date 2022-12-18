package es.uca.iw.ebz.usuario.cliente;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import es.uca.iw.ebz.usuario.Usuario;

@Entity
public class Cliente {
	@Id
	@GeneratedValue
	private UUID Id;

	public Cliente() {
	}

	public UUID getId(){ return this.Id; }

	//Datos pertinentes
	@NotNull
	@Column(name = "nombre")
	private String sNombre;
	public String getNombre(){ return this.sNombre; }

	public void setNombre(String nombre) { this.sNombre = nombre; }

	@NotNull
	@Column(name = "fecha_nacimiento")
	private Date dFechaNacimiento;
	public Date getFechaNacimiento(){ return this.dFechaNacimiento; }
	public void  setFechaNacimiento(Date fecha){ this.dFechaNacimiento = fecha; }
	@NotNull
	@Column(name = "fecha_registro")
	private Date dFechaRegistro;
	public Date getFechaRegistro(){ return this.dFechaRegistro; }
	public void  setFechaRegitro(Date fecha){ this.dFechaRegistro = fecha; }
	@Column(name = "fecha_eliminacion")
	private Date dFechaEliminacion;
	public Date getFechaEliminacion(){ return this.dFechaEliminacion; }
	public void  setFechaEliminaciono(Date fecha){ this.dFechaEliminacion = fecha; }

	//Relaciones
	@NotNull
	@Column(name = "id_tipo_cliente")
	private TipoCliente TipoCliente;
	public TipoCliente getTipoCliente (){ return this.TipoCliente; }
	public void setTipoCliente (TipoCliente tip){ this.TipoCliente = tip; }

	@NotNull
	//@Column(name = "id_usuario")
	@ManyToOne
	//@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	public Usuario getUsuario (){ return this.usuario; }
	public void setUsuario(Usuario user){ this.usuario = user; }

	public Cliente(String nombre, Date fechNac, Date fechReg, TipoCliente tipoCliente, Usuario usuario){
		this.sNombre = nombre;
		this.dFechaNacimiento = fechNac;
		this.dFechaRegistro = fechReg;
		this.TipoCliente = tipoCliente;
		this.usuario = usuario;
	}
}
