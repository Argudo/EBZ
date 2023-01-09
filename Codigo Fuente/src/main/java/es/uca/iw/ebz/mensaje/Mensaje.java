package es.uca.iw.ebz.mensaje;

import es.uca.iw.ebz.consulta.Consulta;
import es.uca.iw.ebz.consulta.TipoEstado;
import es.uca.iw.ebz.usuario.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity
public class Mensaje {

    @Id
    @GeneratedValue
    @Column (name = "id", length = 16)
    private UUID _id;

    @Column (name = "fecha")
    @NotNull
    private Date _fecha;

    @Column (name = "fechaEliminacion")
    private Date _fechaEliminacion;

    @Column (name = "texto")
    @NotNull
    private String _texto;

    @ManyToOne
    private Usuario _autor;

    @ManyToOne
    private Consulta _consulta;

    @Version
    private Integer version;

    public Mensaje() {}

    public Mensaje(Date fecha, String texto, Usuario autor, Consulta consulta){

        _fecha = fecha;
        _texto = texto;
        _autor = autor;
        _consulta = consulta;

    }

    public UUID getId() { return _id; }

    public Date getFecha() { return _fecha; }

    public Date getFechaEliminacion() { return _fechaEliminacion; }

    public String getTexto() { return _texto; }

    public Usuario getAutor() { return _autor; }
    public Integer getVersion() { return version; }

    //Setters

    public void setId(UUID id) { _id = id; }

    public void setFecha(Date fecha) { _fecha = fecha; }

    public void setTexto(String texto) { _texto = texto; }

    public void setAutor(Usuario autor) { _autor = autor; }

    public void setFechaEliminacion(Date fecha) { _fechaEliminacion = fecha; }
    public void setVersion(Integer version) { this.version = version; }

}
