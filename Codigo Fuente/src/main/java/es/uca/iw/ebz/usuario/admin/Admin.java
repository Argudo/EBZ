package es.uca.iw.ebz.usuario.admin;

import es.uca.iw.ebz.usuario.Usuario;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class Admin {

    @Id
    @Type(type = "uuid-char")
    @GeneratedValue
    private UUID Id;

    public Admin() {}

    public UUID getId(){ return this.Id; }

    @NotNull
    @Column( name = "nombre")
    private String sNombre;
    public String getNombre(){ return this.sNombre; }
    public void setNombre (String nombre){ this.sNombre = nombre; }

    @NotNull
    @ManyToOne
    private Usuario usuario;
    public Usuario getUsuario(){ return this.usuario; }
    public void setUsuario (Usuario user){ this.usuario = user; }
    public Admin(String sNombre, Usuario usuario){
        this.sNombre = sNombre;
        this.usuario = usuario;
    }

    //hay que hacer noticia y hacer la referencia.
}
