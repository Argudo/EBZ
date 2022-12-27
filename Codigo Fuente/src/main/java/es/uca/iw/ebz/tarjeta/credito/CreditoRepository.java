package es.uca.iw.ebz.tarjeta.credito;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.uca.iw.ebz.tarjeta.Tarjeta;

@Repository
public interface CreditoRepository extends JpaRepository<Credito, UUID> {
	public Credito findBy_tarjeta(Tarjeta tarjeta);
}
