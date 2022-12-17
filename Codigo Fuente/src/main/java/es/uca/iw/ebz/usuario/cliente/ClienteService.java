package es.uca.iw.ebz.usuario.cliente;

import es.uca.iw.ebz.usuario.Usuario;
import es.uca.iw.ebz.usuario.UsuarioService;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
	private ClienteRepository repoCliente;
	private UsuarioService servUsuario;
	@Autowired
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
		return repoCliente.findByUsuario(servUsuario.findBysUsuario(DNI));
	}

	public Cliente findByNIF(String NIF) {
		return repoCliente.findByUsuario(servUsuario.findBysUsuario(NIF));
	}

	public Cliente findByUsuario(Usuario user) {
		return repoCliente.findByUsuario(user);
	}

}
