package es.uca.iw.ebz.Movimiento.Externo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExternoRepository extends JpaRepository<Externo, UUID> {
}
