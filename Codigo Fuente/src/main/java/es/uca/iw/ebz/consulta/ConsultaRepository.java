package es.uca.iw.ebz.consulta;

import es.uca.iw.ebz.usuario.Usuario;
import es.uca.iw.ebz.usuario.admin.Admin;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, UUID> {
    List<Consulta> findBy_cliente(Usuario usuario);

    List<Consulta> findAll();


}
