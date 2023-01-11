package es.uca.iw.ebz.Cuenta;

import es.uca.iw.ebz.usuario.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, UUID> {
    Optional<Cuenta> findBysNumeroCuenta(String sNumeroCuenta);
    List<Cuenta> findByFechaEliminacionIsNull();

    List<Cuenta> findByCliente(Cliente cliente);

    List<Cuenta> findByClienteAndFechaEliminacionIsNull(Cliente cliente);

}

