package es.uca.iw.ebz.mensaje;

import es.uca.iw.ebz.Movimiento.Movimiento;
import es.uca.iw.ebz.usuario.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Entity
public class Mensaje {

    @Id
    @GeneratedValue
    @Column (name = "id")
    private UUID _id;

    @Column (name = "autor")
    @ManyToOne
    private Usuario _autor;

    @Column (name = "texto")
    @NotNull
    private String _texto;

    @Column (name = "fecha")
    @NotNull
    private Date _fecha;

    public Mensaje() {}

    public Mensaje(Usuario autor, String texto){
        _autor = autor;
        _texto = texto;
        _fecha = new Date();
    }

    public static List<Mensaje> sortByFechaASC(List<Mensaje> mensajes) {
        mensajes.sort((m1, m2) -> m1.getFecha().compareTo(m2.getFecha()));
        return mensajes;
    }

    //Getters
    public UUID getId() { return _id; }
    public Usuario getAutor() { return _autor; }
    public String getTexto() { return _texto; }
    public Date getFecha() { return _fecha; }

    //Setters

    public void setId(UUID id) { _id = id; }
    public void setAutor(Usuario autor) { _autor = autor; }
    public void setTexto(String texto) { _texto = texto; }
    public void setFecha(Date fecha) { _fecha = fecha; }

}
