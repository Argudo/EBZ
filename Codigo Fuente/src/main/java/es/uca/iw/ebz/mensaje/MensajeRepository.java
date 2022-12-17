package es.uca.iw.ebz.mensaje;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MensajeRepository extends JpaRepository<Mensaje, UUID> {
}
