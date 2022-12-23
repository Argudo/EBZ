package es.uca.iw.ebz.Movimiento.Recibo;

import es.uca.iw.ebz.Movimiento.Interno.Interno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReciboRepository extends JpaRepository<Recibo, UUID> {

}
