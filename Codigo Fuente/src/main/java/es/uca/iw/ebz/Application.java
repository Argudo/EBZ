package es.uca.iw.ebz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.uca.iw.ebz.consulta.EnumEstado;
import es.uca.iw.ebz.consulta.TipoEstado;
import es.uca.iw.ebz.consulta.TipoEstadoRepository;
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
import es.uca.iw.ebz.usuario.admin.Admin;
import es.uca.iw.ebz.usuario.admin.AdminService;
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

	@Autowired
	AdminService adminService;

	@Autowired
	TipoEstadoRepository tipoEstadoRepository;
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

	@Override
	public void run(String... args) throws Exception {

		if(tipoEstadoRepository.count() == 0){
			tipoEstadoRepository.save(new TipoEstado(EnumEstado.Pendiente));
			tipoEstadoRepository.save(new TipoEstado(EnumEstado.Abierto));
			tipoEstadoRepository.save(new TipoEstado(EnumEstado.Cerrado));
		}

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
		
		if(usuario.count() == 0) {
			Usuario empleado = new Usuario("12345678A", "pruebauca");
			empleado.setTipoUsuario(TipoUsuario.Empleado);
			usuario.save(empleado);
			Admin admin = new Admin();
			admin.setUsuario(empleado);
			admin.setNombre("Iván");
			adminService.save(admin);
		}

	}

}
