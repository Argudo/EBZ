package es.uca.iw.ebz.usuario.admin;

import es.uca.iw.ebz.usuario.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class Admin {

    @Id
    @GeneratedValue
    private UUID Id;

    public Admin() {}

    public UUID getId(){ return this.Id; }

    @NotNull
    @Column( name = "dni")
    private String sDNI;
    public String getNif(){ return this.sDNI; }
    public void setNif (String DNI){ this.sDNI = DNI; }

    @NotNull
    @Column( name = "nombre")
    private String sNombre;
    public String getNombre(){ return this.sNombre; }
    public void setNombre (String nombre){ this.sNombre = nombre; }

    @ManyToOne
    private Usuario Usuario;
    public Usuario getUsuario(){ return this.Usuario; }
    public void setUsuario (Usuario usuario){ this.Usuario = usuario; }

    public Admin(String sNif, String sNombre, Usuario usuario){
        this.sNombre = sNombre;
        this.sDNI = sNif;
        this.Usuario = usuario;
    }

    //hay que hacer noticia y hacer la referencia.
}
