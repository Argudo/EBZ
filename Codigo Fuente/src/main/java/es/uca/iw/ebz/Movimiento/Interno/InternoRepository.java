package es.uca.iw.ebz.Movimiento.Interno;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Movimiento.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.UUID;

@Repository
public interface InternoRepository extends JpaRepository<Interno, UUID> {
    List<Interno> findByCuentaOrigen(Cuenta cuentaOrigen);
    List<Interno> findByCuentaDestino(Cuenta cuentaDestino);
    List<Interno> findByCuentaOrigenOrCuentaDestino(Cuenta cuentaOrigen, Cuenta cuentaDestino);

    Interno findByMovimiento(Movimiento movimiento);

}
