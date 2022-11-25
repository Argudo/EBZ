package es.uca.iw.ebz.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
	private ClienteRepository repoCliente;

	public Cliente save(Cliente cliente) {
		return repoCliente.save(cliente);
	}

	public ClienteService(ClienteRepository clienteRepository){
		repoCliente = clienteRepository;
	}
}
