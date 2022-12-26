package es.uca.iw.ebz.tarjeta.credito;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoCrediticioRepository extends JpaRepository<TipoCrediticio, Integer> {

}
