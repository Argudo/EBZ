package es.uca.iw.ebz.usuario.cliente;

import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteRepository;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
	private ClienteRepository repoCliente;

	public ClienteService(ClienteRepository clienteRepository){
		repoCliente = clienteRepository;
	}

	public Cliente save(Cliente cliente) {
		return repoCliente.save(cliente);
	}






}
