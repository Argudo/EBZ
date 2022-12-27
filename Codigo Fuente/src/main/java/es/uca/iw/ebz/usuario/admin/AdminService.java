package es.uca.iw.ebz.usuario.admin;

import es.uca.iw.ebz.usuario.Usuario;
import es.uca.iw.ebz.usuario.UsuarioService;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AdminService {
    private AdminRepository repoAdmin;
    @Autowired
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

        return repoAdmin.findByusuario(servUsuario.findBysDNI(DNI));
    }

    public List<Admin> findAll (){
        return repoAdmin.findAll();
    }

    public Admin EliminarAdmin(Admin adm){
        Usuario usr = adm.getUsuario();
        usr.setFechaEliminaciono(new Date());
        servUsuario.save(usr);
        return adm;
    }

    public List<Admin> findNotEliminated(){
        List<Admin> result = this.findAll();
        for (Admin adm:result){
            if (adm.getUsuario().getFechaEliminacion() == null){
                result.remove(adm);
            }
        }
        return result;
    }

}
