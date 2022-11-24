package es.uca.iw.ebz.Cuenta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Integer> {
    Cuenta findBysNumeroCuenta(String sNumeroCuenta);
    List<Cuenta> findByFechaEliminacionIsNull();

}

