package es.uca.iw.ebz.usuario.admin;

import es.uca.iw.ebz.usuario.Usuario;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {

    Admin findByusuario (Usuario user);
}
