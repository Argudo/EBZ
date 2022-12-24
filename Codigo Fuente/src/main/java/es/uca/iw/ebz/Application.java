package es.uca.iw.ebz;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Cuenta.CuentaService;
import es.uca.iw.ebz.tarjeta.EnumTarjeta;
import es.uca.iw.ebz.tarjeta.Tarjeta;
import es.uca.iw.ebz.tarjeta.TarjetaService;
import es.uca.iw.ebz.tarjeta.TipoTarjeta;
import es.uca.iw.ebz.tarjeta.TipoTarjetaRepository;
import es.uca.iw.ebz.tarjeta.credito.Credito;
import es.uca.iw.ebz.tarjeta.credito.CreditoService;
import es.uca.iw.ebz.tarjeta.credito.TipoCrediticio;
import es.uca.iw.ebz.tarjeta.credito.TipoCrediticioRepository;
import es.uca.iw.ebz.tarjeta.prepago.Prepago;
import es.uca.iw.ebz.tarjeta.prepago.PrepagoService;
import es.uca.iw.ebz.usuario.TipoUsuario;
import es.uca.iw.ebz.usuario.Usuario;
import es.uca.iw.ebz.usuario.UsuarioRepository;
import es.uca.iw.ebz.usuario.UsuarioService;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteRepository;
import es.uca.iw.ebz.usuario.cliente.ClienteService;
import es.uca.iw.ebz.usuario.cliente.TipoCliente;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */


@SpringBootApplication
@Theme(value = "ebz")
@PWA(name = "ebz", shortName = "ebz", offlineResources = {})
@NpmPackage(value = "line-awesome", version = "1.3.0")
@Push
public class Application implements AppShellConfigurator, CommandLineRunner {
	@Autowired
	TipoTarjetaRepository tipoTarRepo;
	@Autowired
	TipoCrediticioRepository tipoCredRepo;
	@Autowired
	UsuarioRepository userRepo;
	@Autowired
	ClienteRepository clienteRepo;

	@Autowired
	UsuarioService usuario;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	ClienteService clienteService;
	
	@Autowired
	TarjetaService tarService;

	@Autowired
	CuentaService cuentaService;
	
	@Autowired
	CreditoService credService;
	
	@Autowired
	PrepagoService prepagoService;
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
		if(tipoTarRepo.count() == 0) {
			tipoTarRepo.save(new TipoTarjeta(1, "Debito"));
			tipoTarRepo.save(new TipoTarjeta(2, "Crédito"));
			tipoTarRepo.save(new TipoTarjeta(3, "Prepago"));
		}
		
		if(tipoCredRepo.count() == 0) {
			tipoCredRepo.save(new TipoCrediticio(1, "Gold", 1000));
			tipoCredRepo.save(new TipoCrediticio(2, "Platinum", 10000));
			tipoCredRepo.save(new TipoCrediticio(3, "Black", 100000));
			
		}
		
		if(usuario.count() < 2){
			Usuario empleado = new Usuario("1234", "1234");
			empleado.setTipoUsuario(TipoUsuario.Empleado);
			usuario.save(empleado);
			Usuario cli = new Usuario("32093905B", "1234");
			Usuario cli2 = new Usuario("12267004T", "1234");
			cli.setTipoUsuario(TipoUsuario.Cliente);
			cli2.setTipoUsuario(TipoUsuario.Cliente);
			usuario.save(cli);
			usuario.save(cli2);
			Cliente cliente = new Cliente("Juán del Marqués", new Date(), new Date(), TipoCliente.Persona, cli);
			clienteRepo.save(cliente);
			clienteRepo.save(new Cliente("Natalia Reina", new Date(), new Date(), TipoCliente.Persona, cli2));
			Cuenta cuenta = new Cuenta();
			cuenta.setCliente(cliente);
			cuenta.setFechaEliminacion(new Date());
			cuentaService.añadirCuenta(cuenta);
		}
		
		if(credService.Count() < 1) {
			Tarjeta tarCredito = new Tarjeta(4719, new TipoTarjeta(EnumTarjeta.Credito), cuentaService.findByCliente(clienteRepo.findByusuario(usuario.findBysDNI("32093905B"))).get(0), clienteRepo.findByusuario(usuario.findBysDNI("32093905B")));
			tarService.Save(tarCredito);
			Credito cred = new Credito(tarCredito, tipoCredRepo.findById(1).get());
			credService.Save(cred);
		}
	
		if(prepagoService.Count() < 1) {
			Tarjeta tarPrepago = new Tarjeta(4719, new TipoTarjeta(EnumTarjeta.Prepago), clienteRepo.findByusuario(usuario.findBysDNI("32093905B")));
			tarService.Save(tarPrepago);
			Prepago prepago = new Prepago(tarPrepago);
			prepagoService.Save(prepago);
		}
		
	}

}
