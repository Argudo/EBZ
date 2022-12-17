package es.uca.iw.ebz.usuario.admin;

import es.uca.iw.ebz.usuario.Usuario;
import es.uca.iw.ebz.usuario.UsuarioService;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private AdminRepository repoAdmin;
    private UsuarioService servUsuario;

    public AdminService(AdminRepository clienteRepository){
        repoAdmin = clienteRepository;
    }

    public Admin save(Admin admin) {
        return repoAdmin.save(admin);
    }

    public Admin crearAdmin(Admin admin) {
        Usuario user = admin.getUsuario();
        servUsuario.CambiarContraseña(user, user.getContraseña());
        admin.setUsuario(user);

        return repoAdmin.save(admin);

    }

    public Admin findByDNI(String DNI) {
        return repoAdmin.findBysDNI(DNI);
    }

}
