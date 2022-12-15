package es.uca.iw.ebz.Movimiento.CompraTarjeta;

import es.uca.iw.ebz.Movimiento.Interno.Interno;
import es.uca.iw.ebz.Movimiento.Movimiento;
import es.uca.iw.ebz.tarjeta.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CompraTarjetaRepository extends JpaRepository<CompraTarjeta, UUID> {
    CompraTarjeta findByMovimiento(Movimiento movimiento);
    List<CompraTarjeta> findByTarjeta(Tarjeta tarjeta);
}
