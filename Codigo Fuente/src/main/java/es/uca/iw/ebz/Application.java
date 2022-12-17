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

import es.uca.iw.ebz.tarjeta.TipoCrediticio;
import es.uca.iw.ebz.tarjeta.TipoCrediticioRepository;
import es.uca.iw.ebz.tarjeta.TipoTarjeta;
import es.uca.iw.ebz.tarjeta.TipoTarjetaRepository;
import es.uca.iw.ebz.usuario.TipoUsuario;
import es.uca.iw.ebz.usuario.Usuario;
import es.uca.iw.ebz.usuario.UsuarioRepository;
import es.uca.iw.ebz.usuario.UsuarioService;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteRepository;
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
			usuario.save(new Usuario("1234", "1234"));
			Usuario cli = new Usuario("32093905", "1234");
			cli.setTipoUsuario(TipoUsuario.Cliente);
			usuario.save(cli);
			Cliente c = new Cliente("Juán del Marqués", new Date(), new Date(), null, TipoCliente.Persona, cli);
			clienteRepo.save(c);
			
		}
	}

}
