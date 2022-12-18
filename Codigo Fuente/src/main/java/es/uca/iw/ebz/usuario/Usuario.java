package es.uca.iw.ebz.usuario;

import net.bytebuddy.build.ToStringPlugin;
import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class Usuario {

    @Autowired
    @Transient
    private PasswordEncoder passwordEncoder;

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID Id;

    public UUID getId(){ return this.Id; }

    //Atributos de inicio
    @NotNull
    @Unique
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
        this.sContraseña = contraseña;//hablar con pedro si sacarlo fuero o que hacer
    }

    public Usuario() {
    }
}
