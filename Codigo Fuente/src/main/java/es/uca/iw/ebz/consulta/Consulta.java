package es.uca.iw.ebz.consulta;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

//¿Faltan añadir los mensajes? Sí

import es.uca.iw.ebz.mensaje.Mensaje;
import es.uca.iw.ebz.usuario.Usuario;

@Entity
public class Consulta {

    @Id
    @GeneratedValue
    @Column (name = "id", length = 16)
    private UUID _id;

    @Column (name = "titulo")
    @NotNull
    private String _titulo;

    @Column (name = "fechaCreacion")
    @NotNull
    private Date _fechaCreacion;

    @Column (name = "fechaEliminacion")
    private Date _fechaEliminacion;

    @ManyToOne
    private TipoEstado _tipoEstado;

    @ManyToOne
    private Usuario _cliente;

    @OneToMany(mappedBy = "_consulta", fetch = FetchType.EAGER)
    private List<Mensaje> _mensajes;

    @Version
    private Integer version;


    //Añadir lista de mensajes (composición)

    public Consulta() {}

    public Consulta(String titulo, Date fechaCreacion, TipoEstado tipoEstado, Usuario cliente){

        _titulo = titulo;
        _fechaCreacion = fechaCreacion;
        _tipoEstado = tipoEstado;
        _cliente = cliente;
        _mensajes = new ArrayList<>();

    }

    //Getters
    public UUID getId() { return _id; }

    public Date getFechaCreacion() { return _fechaCreacion; }

    public Date getFechaEliminacion() { return _fechaEliminacion; }

    public String getTitulo() { return _titulo; }

    public TipoEstado getTipoEstado() { return _tipoEstado; }

    public Usuario getCliente() { return _cliente; }

    public List<Mensaje> getMensajes() { return _mensajes; }
    public Integer getVersion() { return version; }

    //Setters

    public void setId(UUID id) { _id = id; }

    public void setFechaEliminacion(Date fecha) { _fechaEliminacion = fecha; }

    public void setTitulo(String titulo) { _titulo = titulo; }

    public void set_tipoEstado(TipoEstado tipoEstado) { _tipoEstado = tipoEstado; }

    public void setMensajes(Mensaje mensaje) { _mensajes.add(mensaje); }
    public void setVersion(Integer version) { this.version = version; }


}
