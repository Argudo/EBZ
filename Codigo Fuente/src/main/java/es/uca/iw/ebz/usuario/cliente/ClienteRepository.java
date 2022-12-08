package es.uca.iw.ebz.usuario.cliente;

import java.util.UUID;

import es.uca.iw.ebz.usuario.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID>{


}
