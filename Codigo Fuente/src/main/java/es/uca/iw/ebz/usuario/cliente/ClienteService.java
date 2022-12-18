package es.uca.iw.ebz.usuario.cliente;

import es.uca.iw.ebz.usuario.Usuario;
import es.uca.iw.ebz.usuario.UsuarioService;
<<<<<<< Updated upstream
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteRepository;
=======
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> Stashed changes
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

	public Cliente crearCliente(Cliente cliente) {
		Usuario user = cliente.getUsuario();
		servUsuario.CambiarContraseña(user, user.getContraseña());
		cliente.setUsuario(user);

		return repoCliente.save(cliente);

	}

	public Cliente findByDNI(String DNI) {
<<<<<<< Updated upstream
		return repoCliente.findByusuario(servUsuario.findBysUsuario(DNI));
	}

	public Cliente findByNIF(String NIF) {
		return repoCliente.findByusuario(servUsuario.findBysUsuario(NIF));
=======
		return repoCliente.findByUsuario(servUsuario.findBysDNI(DNI));
	}

	public Cliente findByUsuario(Usuario user) {
		return repoCliente.findByUsuario(user);
>>>>>>> Stashed changes
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
