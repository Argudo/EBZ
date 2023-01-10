package es.uca.iw.ebz.usuario.cliente;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uca.iw.ebz.usuario.Usuario;
import es.uca.iw.ebz.usuario.UsuarioService;

import java.util.Date;

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
		return repoCliente.findByusuario(servUsuario.findBysDNI(DNI));
	}

	public Cliente findByUsuario(Usuario user) {
		return repoCliente.findByusuario(user);
	}

	public List<Cliente> findAll() {
		List<Cliente> noEli = new ArrayList<Cliente>();
		for(Cliente cli: repoCliente.findAll())
			if(cli.getUsuario().getFechaEliminacion() == null) noEli.add(cli);
		return noEli;
	}

	public Cliente EliminarCuenta(Cliente cli){
		Usuario usr = cli.getUsuario();
		usr.setFechaEliminaciono(new Date());
		servUsuario.save(usr);
		return cli;
	}

	public List<Cliente> findNotEliminated(){
		List<Cliente> result = this.findAll();
		for (Cliente cli:result){
			if (cli.getUsuario().getFechaEliminacion() == null){
				result.remove(cli);
			}
		}
		return result;
	}

}
