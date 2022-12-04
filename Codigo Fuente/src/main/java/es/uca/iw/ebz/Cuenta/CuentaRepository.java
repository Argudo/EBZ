package es.uca.iw.ebz.Cuenta;

import es.uca.iw.ebz.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, UUID> {
    Cuenta findBysNumeroCuenta(String sNumeroCuenta);
    List<Cuenta> findByFechaEliminacionIsNull();

    List<Cuenta> findByCliente(Cliente cliente);

}

