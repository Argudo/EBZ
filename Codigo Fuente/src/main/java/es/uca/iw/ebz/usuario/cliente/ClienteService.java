package es.uca.iw.ebz.usuario.cliente;

import java.util.List;
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

	public Cliente crearCliente(Cliente cliente) {
		Usuario user = cliente.getUsuario();
		servUsuario.CambiarContraseña(user, user.getContraseña());
		cliente.setUsuario(user);

		return repoCliente.save(cliente);

	}

	public Cliente findByDNI(String DNI) {
		return repoCliente.findByusuario(servUsuario.findBysUsuario(DNI));
	}

	public Cliente findByUsuario(Usuario user) {
		return repoCliente.findByusuario(user);
	}
	
	public List<Cliente> findAll() {
		return repoCliente.findAll();
	}

}
