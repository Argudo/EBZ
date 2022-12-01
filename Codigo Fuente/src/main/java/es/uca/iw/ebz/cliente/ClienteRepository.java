package es.uca.iw.ebz.cliente;

import java.util.UUID;

import es.uca.iw.ebz.Cuenta.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID>{

    Cliente findBysUsuario (String sUsuario);
}