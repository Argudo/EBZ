package es.uca.iw.ebz.Movimiento.RecargaTarjeta;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Movimiento.Movimiento;
import es.uca.iw.ebz.tarjeta.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecargaTarjetaRepository extends JpaRepository<RecargaTarjeta, UUID> {

    RecargaTarjeta findByMovimiento(Movimiento movimiento);
    List<RecargaTarjeta> findByCuenta(Cuenta cuenta);
    List<RecargaTarjeta> findBytarjeta(Tarjeta tarjeta);
}
