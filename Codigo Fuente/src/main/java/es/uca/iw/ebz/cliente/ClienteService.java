package es.uca.iw.ebz.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class ClienteService {
	private ClienteRepository repoCliente;

	public ClienteService(ClienteRepository clienteRepository){
		repoCliente = clienteRepository;
	}

	public Cliente save(Cliente cliente) {
		return repoCliente.save(cliente);
	}

	public static String codifica(String contraseña) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(contraseña.getBytes());
		byte[] digest = md.digest();
		String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
		return myHash;
	}

	public Cliente inicioSesion(String usuario, String contraseña) throws UsuarioNoEncontrado, ContraseñaIncorrecta, NoSuchAlgorithmException { //sujeto a cambio
		Cliente cli = repoCliente.findBysUsuario(usuario);
		if (cli == null) throw new UsuarioNoEncontrado("Este usuario no se encuentra en la base de datos: " + usuario);
		if (cli.getContraseña() != codifica(contraseña)) throw  new ContraseñaIncorrecta("La contraseña no coincide con la de la base de datos " + contraseña);
		return cli;
	}

}
