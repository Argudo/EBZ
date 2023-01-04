package es.uca.iw.ebz.consulta;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

//¿Faltan añadir los mensajes? Sí

import es.uca.iw.ebz.mensaje.Mensaje;
import es.uca.iw.ebz.usuario.Usuario;

@Entity
public class Consulta {

    @Id
    @GeneratedValue
    @Column (name = "id")
    private UUID _id;

    @Column (name = "titulo")
    @NotNull
    private String _titulo;

    @Column (name = "descripcion")
    @NotNull
    private String _descripcion;

    @Column (name = "fechaCreacion")
    @NotNull
    private Date _fechaCreacion;

    @Column (name = "fechaEliminacion")
    private Date _fechaEliminacion;

    @ManyToOne
    private TipoEstado _tipoEstado;

    @ManyToOne
    private Usuario _cliente;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mensajes")
    private List<Mensaje> _mensajes;


    //Añadir lista de mensajes (composición)

    public Consulta() {}

    public Consulta(String titulo, String descripcion, Date fechaCreacion, TipoEstado tipoEstado, Usuario cliente){

        _titulo = titulo;
        _descripcion = descripcion;
        _fechaCreacion = fechaCreacion;
        _tipoEstado = tipoEstado;
        _cliente = cliente;

    }

    //Getters
    public UUID getId() { return _id; }

    public Date getFechaCreacion() { return _fechaCreacion; }

    public Date getFechaEliminacion() { return _fechaEliminacion; }

    public String getTitulo() { return _titulo; }

    public String getDescripcion() { return _descripcion; }

    public TipoEstado getTipoEstado() { return _tipoEstado; }

    public Usuario getCliente() { return _cliente; }

    public List<Mensaje> getMensajes() { return _mensajes; }

    //Setters

    public void setId(UUID id) { _id = id; }

    public void setFechaEliminacion(Date fecha) { _fechaEliminacion = fecha; }

    public void setTitulo(String titulo) { _titulo = titulo; }

    public void setDescripcion(String descripcion) { _descripcion = descripcion; }

    public void set_tipoEstado(TipoEstado tipoEstado) { _tipoEstado = tipoEstado; }

    public void setMensajes(Mensaje mensaje) { _mensajes.add(mensaje); }





}
