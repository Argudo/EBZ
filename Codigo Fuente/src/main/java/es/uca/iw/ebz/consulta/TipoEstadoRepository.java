package es.uca.iw.ebz.consulta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoEstadoRepository extends JpaRepository<TipoEstado, Integer> {



}
