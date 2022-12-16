package es.uca.iw.ebz.cliente;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import es.uca.iw.ebz.Cuenta.Cuenta;

import es.uca.iw.ebz.tarjeta.Tarjeta;
import org.hibernate.annotations.Type;

@Entity
public class Cliente {
	@Id
	@GeneratedValue
	private UUID Id;
	public UUID getId(){ return this.Id; }

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
	public String getnombre(){ return this.sNombre; }

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
	@OneToMany
	@JoinColumn(name = "cliente_id")
	@Column(name = "cuentas")
	private List<Cuenta> aCuentas;
	public List<Cuenta> getCuentas (){ return aCuentas; }
	public  void setCuentas (Cuenta cuenta){ this.aCuentas.add(cuenta); }
	@NotNull
	@Column(name = "tipo_cliente")
	private TipoCliente eTipoCliente;
	public TipoCliente getTipoCliente (){ return this.eTipoCliente; }
	public void setTipoCliente (TipoCliente tip){ this.eTipoCliente = tip; }
	@OneToMany
	@JoinColumn(name = "cliente_id")
	@Column(name = "tarjeta")
	private List<Tarjeta> aTarjetas;
	public List<Tarjeta> getTarjetas (){ return aTarjetas; }
	public  void setTarjetas (Tarjeta tarjeta){ this.aTarjetas.add(tarjeta); }

	public Cliente(){}
	public Cliente(UUID uuid, String string, TipoCliente persona, Date date) {
		this.Id = uuid;
		this.setNombre(string);
		this.setTipoCliente(persona);
		this.setFechaNacimiento(date);
	}
}
