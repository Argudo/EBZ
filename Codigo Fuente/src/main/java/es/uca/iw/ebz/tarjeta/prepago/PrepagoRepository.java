package es.uca.iw.ebz.tarjeta.prepago;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.uca.iw.ebz.tarjeta.Tarjeta;

@Repository
public interface PrepagoRepository extends JpaRepository<Prepago, UUID>{
	public Prepago findBy_tarjeta(Tarjeta tarjeta);
}
