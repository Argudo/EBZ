package es.uca.iw.ebz.usuario.admin;

import es.uca.iw.ebz.usuario.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class Admin {

    @Id
    @GeneratedValue
    private UUID Id;
    public UUID getId(){ return this.Id; }

    @NotNull
    @Column( name = "nif")
    private String sNif;
    public String getNif(){ return this.sNif; }
    public void setNif (String nif){ this.sNif = nif; }

    @NotNull
    @Column( name = "nombre")
    private String sNombre;
    public String getUsuario(){ return this.sNombre; }
    public void setUsuario (String nombre){ this.sNombre = nombre; }

    @ManyToOne
    private Usuario Usuario;

    public Admin(String sNif, String sNombre, Usuario usuario){
        this.sNombre = sNombre;
        this.sNif = sNif;
        this.Usuario = usuario;
    }

    //hay que hacer noticia y hacer la referencia.
}
