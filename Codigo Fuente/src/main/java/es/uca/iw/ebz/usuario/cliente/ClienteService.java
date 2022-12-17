package es.uca.iw.ebz.usuario.cliente;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uca.iw.ebz.usuario.Usuario;
import es.uca.iw.ebz.usuario.UsuarioService;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository repoCliente;
	@Autowired
	private UsuarioService servUsuario;

	public ClienteService(ClienteRepository clienteRepository){
		repoCliente = clienteRepository;
	}

	public Cliente save(Cliente cliente) {
		return repoCliente.save(cliente);
	}
	
	public Cliente findByUsuario(String usuario) {
		return repoCliente.findByusuario(servUsuario.findBysUsuario(usuario));
	}

	public Cliente findByDNI(String DNI) {
		return repoCliente.findByusuario(servUsuario.findBysUsuario(DNI));
	}

	public Cliente findByNIF(String NIF) {
		return repoCliente.findByusuario(servUsuario.findBysUsuario(NIF));
	}

}
