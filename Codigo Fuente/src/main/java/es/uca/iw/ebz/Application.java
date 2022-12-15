package es.uca.iw.ebz;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Cuenta.CuentaRepository;
import es.uca.iw.ebz.tarjeta.TipoCrediticio;
import es.uca.iw.ebz.tarjeta.TipoCrediticioRepository;
import es.uca.iw.ebz.tarjeta.TipoTarjeta;
import es.uca.iw.ebz.tarjeta.TipoTarjetaRepository;

import es.uca.iw.ebz.usuario.Usuario;
import es.uca.iw.ebz.usuario.UsuarioRepository;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import es.uca.iw.ebz.usuario.cliente.ClienteRepository;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction.COUNT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static es.uca.iw.ebz.usuario.UsuarioService.codifica;

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
	UsuarioRepository usuario;

	@Autowired
	ClienteRepository cliente;

	@Autowired
	CuentaRepository cuenta;

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

		if(usuario.count() == 0){
			Usuario _usuario = new Usuario("1234", codifica("1234"));
			usuario.save(_usuario);
			Cliente _cliente = new Cliente();
			_cliente.setUsuario(_usuario);
			_cliente.setNombre("Carlos Cortés");
			cliente.save(new Cliente());
			Cuenta _cuenta = new Cuenta();
			_cuenta.setId(_cliente.getId());
			_cuenta.setSaldo((float)50.45);
		}
	}

}
