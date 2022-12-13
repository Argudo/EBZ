package es.uca.iw.ebz.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.UUID;

@Service
public class UsuarioService {

    private UsuarioRepository repoUsuario;

    @Autowired
    public UsuarioService (UsuarioRepository usr) { repoUsuario = usr; }

    public static String codifica(String contraseña) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(contraseña.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return myHash;
    }

    public boolean inicioSesion(String usuario, String contraseña) throws UsuarioNoEncontrado, ContraseñaIncorrecta, NoSuchAlgorithmException { //sujeto a cambio
        Usuario user = repoUsuario.findBysUsuario(usuario);
        if (user == null) throw new UsuarioNoEncontrado("Este usuario no se encuentra en la base de datos: " + usuario);
        if (!Objects.equals(user.getContraseña(), codifica(contraseña))) throw  new ContraseñaIncorrecta("La contraseña no coincide con la de la base de datos " + user.getContraseña() + " " + codifica(contraseña));
        return true;
    }

    public UUID findByUser(String user){
        return repoUsuario.findBysUsuario(user).getId();
    }
    //hacer añadir usuaruio cliente y admin si hay tiempo

}
