package es.uca.iw.ebz.consulta;

//¿Faltan añadir los mensajes?

import es.uca.iw.ebz.usuario.Usuario;
import es.uca.iw.ebz.usuario.cliente.Cliente;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    @ManyToOne()
    private Usuario _cliente;

    @ManyToOne()
    private Usuario _admin;

    public Consulta() {}

    //Falta añadir el cliente y el admin
    public Consulta(String titulo, String descripcion, Date fechaCreacion, TipoEstado tipoEstado, Usuario cliente, Usuario admin){

        _titulo = titulo;
        _descripcion = descripcion;
        _fechaCreacion = fechaCreacion;
        _tipoEstado = tipoEstado;
        _cliente = cliente;
        _admin = admin;

    }

    public static List<Consulta> sortByFechaASC(List<Consulta> consultas){
        consultas.sort((c1, c2) -> c1.getFechaCreacion().compareTo(c2.getFechaCreacion()));
        return consultas;
    }

    //Getters
    public UUID getId() { return _id; }

    public Date getFechaCreacion() { return _fechaCreacion; }

    public Date getFechaEliminacion() { return _fechaEliminacion; }

    public String getTitulo() { return _titulo; }

    public String getDescripcion() { return _descripcion; }

    public TipoEstado getTipoEstado() { return _tipoEstado; }

    public Usuario getCliente() { return _cliente; }

    public Usuario getAdmin() { return _admin; }

    //Setters

    public void setId(UUID id) { _id = id; }

    public void setFechaEliminacion(Date fecha) { _fechaEliminacion = fecha; }

    public void setTitulo(String titulo) { _titulo = titulo; }

    public void setDescripcion(String descripcion) { _descripcion = descripcion; }

    public void set_tipoEstado(TipoEstado tipoEstado) { _tipoEstado = tipoEstado; }





}
