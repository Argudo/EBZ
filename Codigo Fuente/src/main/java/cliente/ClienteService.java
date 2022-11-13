package cliente;

import org.springframework.stereotype.Service;

@Service
public class ClienteService {
	private ClienteRepository repoCliente;
	
	public Cliente save(Cliente cliente) {
		return repoCliente.save(cliente);
	}
}
