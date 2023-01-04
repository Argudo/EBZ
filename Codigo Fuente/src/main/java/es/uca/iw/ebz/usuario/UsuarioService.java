package es.uca.iw.ebz.usuario;

import es.uca.iw.ebz.tarjeta.Tarjeta;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.views.main.Security.SecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private final UsuarioRepository repoUsuario;

    public UsuarioService (UsuarioRepository usr) { repoUsuario = usr; }

    public UUID inicioSesion(String usuario, String contraseña) throws UsuarioNoEncontrado, ContraseñaIncorrecta, NoSuchAlgorithmException { //sujeto a cambio
        Usuario user = repoUsuario.findBysDNI(usuario);
        if (user == null) throw new UsuarioNoEncontrado("Este usuario no se encuentra en la base de datos: " + usuario);
        if (user.getContraseña() != passwordEncoder.encode(contraseña)) throw  new ContraseñaIncorrecta("La contraseña no coincide con la de la base de datos " + contraseña);
        return user.getId();
    }

    public Usuario save(Usuario user){
        user.setContraseña(passwordEncoder.encode(user.getContraseña()));

        repoUsuario.save(user);
        return user;
    }

    public Usuario CambiarContraseña(Usuario user, String Contraseña){
        user.setContraseña(passwordEncoder.encode(user.getContraseña()));

        repoUsuario.save(user);
        return user;
    }

    public String Cifrar (String s){
        return passwordEncoder.encode(s);
    }

    public long count(){
        return repoUsuario.count();
    }
    //hacer añadir usuaruio cliente y admin si hay tiempo

    public Usuario findBysDNI (String use) { return repoUsuario.findBysDNI(use); }

    public Usuario EliminarUsuario(Usuario usr){
        usr.setFechaEliminaciono(new Date());
        save(usr);
        return usr;
    }

    public List<Usuario> findAll() {
        List<Usuario> noEli = new ArrayList<Usuario>();
        for(Usuario use: repoUsuario.findAll())
            if(use.getFechaEliminacion() == null) noEli.add(use);
        return noEli;
    }

    public List<Usuario> findNotEliminated() { return repoUsuario.findBydFechaEliminacionIsNull(); }

}
