package es.uca.iw.ebz.Movimiento.Externo;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Movimiento.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExternoRepository extends JpaRepository<Externo, UUID> {
     List<Externo> findByCuentaPropia(Cuenta cuenta);

     Externo findByMovimiento(Movimiento movimiento);
}
