package es.uca.iw.ebz.usuario;

import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity
public class Usuario {

    @Id
    //@Type(type = "uuid-char")
    @GeneratedValue
    private UUID Id;

    public UUID getId(){ return this.Id; }

    //Atributos de inicio
    @NotNull
    @Unique
    @Column(name = "DNI")
    private String sDNI;
    public String getDNI(){ return this.sDNI; }
    public void setDNI (String DNI){ this.sDNI = DNI; }
    @NotNull
    @Column(name = "password")
    private String sContraseña;
    public String getContraseña (){ return this.sContraseña; }
    public void setContraseña (String contraseña){ this.sContraseña = contraseña; }

    @Column(name = "fecha_eliminacion")
    private Date dFechaEliminacion;
    public Date getFechaEliminacion(){ return this.dFechaEliminacion; }
    public void  setFechaEliminaciono(Date fecha){ this.dFechaEliminacion = fecha; }
    @NotNull
    @Column(name = "tipo_usuario")
    private TipoUsuario tipoUsuario;

    public TipoUsuario getTipoUsuario (){ return this.tipoUsuario; }
    public void setTipoUsuario (TipoUsuario tipous){ this.tipoUsuario = tipous; }

    public Usuario(String DNI, String contraseña){
        this.sDNI = DNI;
        this.sContraseña = contraseña;//hablar con pedro si sacarlo fuero o que hacer
    }

    public Usuario() {
    }

}
