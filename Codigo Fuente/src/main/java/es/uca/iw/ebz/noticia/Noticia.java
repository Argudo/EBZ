package es.uca.iw.ebz.noticia;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity
public class Noticia {

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

    @Column (name = "fecha")
    @NotNull
    private Date _fecha;

    @Column (name = "fechaEliminacion")
    private Date _fechaEliminacion;

    public Noticia() {}

    public Noticia(String titulo, String descripcion, Date fecha) {

        _titulo = titulo;
        _descripcion = descripcion;
        _fecha = fecha;

    }

    //Getters
    public UUID getId() { return _id; }

    public Date getFecha() { return _fecha; }

    public Date getFechaEliminacion() { return _fechaEliminacion; }

    public String getTitulo() { return _titulo; }

    public String getDescripcion() { return _descripcion; }

    //Getters
    public void setId(UUID id) { _id = id; }

    public void setFecha(Date fecha) { _fecha = fecha; }

    public void setFechaEliminacion(Date fecha) { _fechaEliminacion = fecha; }

    public void setTitulo(String titulo) { _titulo = titulo; }

    public void setDescripcion(String descripcion) { _descripcion = descripcion; }


}
