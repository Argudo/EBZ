package es.uca.iw.ebz.tarjeta;

import java.util.List;
import java.util.UUID;

import es.uca.iw.ebz.usuario.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta, UUID>{
	List<Tarjeta> findBy_clienteTitular(Cliente cliente);
}
