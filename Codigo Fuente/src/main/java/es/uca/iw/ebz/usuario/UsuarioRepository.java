package es.uca.iw.ebz.usuario;

import es.uca.iw.ebz.Cuenta.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Usuario findBysDNI (String sUsuario);

    List<Usuario> findBydFechaEliminacionIsNull();
}
