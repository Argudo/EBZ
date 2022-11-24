package es.uca.iw.ebz.tarjeta;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TarjetaRepository extends JpaRepository<Tarjeta, UUID>{
	Tarjeta findByNumTarjeta(String sNumTarjeta);
}
