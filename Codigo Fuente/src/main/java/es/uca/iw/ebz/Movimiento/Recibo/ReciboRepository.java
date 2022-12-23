package es.uca.iw.ebz.Movimiento.Recibo;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Movimiento.Interno.Interno;
import es.uca.iw.ebz.Movimiento.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReciboRepository extends JpaRepository<Recibo, UUID> {
    public Recibo findByMovimiento(Movimiento movimiento);

    public List<Recibo> findByCuenta(Cuenta cuenta);
}
