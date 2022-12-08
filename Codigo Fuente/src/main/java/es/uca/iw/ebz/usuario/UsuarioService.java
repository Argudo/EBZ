package es.uca.iw.ebz.usuario;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class UsuarioService {

    private UsuarioRepository repoUsuario;

    public UsuarioService (UsuarioRepository usr) { repoUsuario = usr; }

    public static String codifica(String contraseña) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(contraseña.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return myHash;
    }

    public UUID inicioSesion(String usuario, String contraseña) throws UsuarioNoEncontrado, ContraseñaIncorrecta, NoSuchAlgorithmException { //sujeto a cambio
        Usuario user = repoUsuario.findBysUsuario(usuario);
        if (user == null) throw new UsuarioNoEncontrado("Este usuario no se encuentra en la base de datos: " + usuario);
        if (user.getContraseña() != codifica(contraseña)) throw  new ContraseñaIncorrecta("La contraseña no coincide con la de la base de datos " + contraseña);
        return user.getId();
    }
    //hacer añadir usuaruio cliente y admin si hay tiempo

}
