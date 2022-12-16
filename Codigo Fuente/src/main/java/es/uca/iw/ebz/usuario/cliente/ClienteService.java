package es.uca.iw.ebz.usuario.cliente;

import es.uca.iw.ebz.usuario.UsuarioService;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteRepository;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
	private ClienteRepository repoCliente;
	private UsuarioService servUsuario;

	public ClienteService(ClienteRepository clienteRepository){
		repoCliente = clienteRepository;
	}

	public Cliente save(Cliente cliente) {
		return repoCliente.save(cliente);
	}

	public Cliente findByDNI(String DNI) {
		return repoCliente.findByusuario(servUsuario.findBysUsuario(DNI));
	}

	public Cliente findByNIF(String NIF) {
		return repoCliente.findByusuario(servUsuario.findBysUsuario(NIF));
	}

}
