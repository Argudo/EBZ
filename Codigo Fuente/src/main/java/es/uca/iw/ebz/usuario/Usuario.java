package es.uca.iw.ebz.usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class Usuario {

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

    @Column(name = "tipo_usuario")
    private TipoUsuario tipoUsuario;
    public TipoUsuario getTipoUsuario (){ return this.tipoUsuario; }
    public void setTipoUsuario (TipoUsuario tipous){ this.tipoUsuario = tipous; }

    public Usuario(String usuario, String contraseña){
        this.sUsuario = usuario;
        this.sContraseña = contraseña;
    }
}
