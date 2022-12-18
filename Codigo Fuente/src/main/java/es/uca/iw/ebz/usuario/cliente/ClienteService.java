
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uca.iw.ebz.usuario.Usuario;
import es.uca.iw.ebz.usuario.UsuarioService;

import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteRepository;



import java.util.Date;
import java.util.List;

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
		return repoCliente.findByUsuario(user);
	}

	public List<Cliente> findAll (){
		return repoCliente.findAll();
	}

	public Cliente EliminarCuenta(Cliente cli){
		Usuario usr = cli.getUsuario();
		usr.setFechaEliminaciono(new Date());
		servUsuario.save(usr);
		return cli;
	}

}
