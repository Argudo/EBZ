package es.uca.iw.ebz.tarjeta;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.usuario.cliente.Cliente;


@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta, UUID>{
	List<Tarjeta> findBy_clienteTitular(Cliente cliente);
	List<Tarjeta> findAllBy_cuenta(Cuenta cuenta);
	List<Tarjeta> findBy_sNumTarjeta(String sNumTarjeta);
	@Query("SELECT t FROM Tarjeta t WHERE t._sNumTarjeta = :sNumTarjeta AND t._fechaCancelacion IS NULL")
	Tarjeta findByNumTarjeta(@Param("sNumTarjeta") String sNumTarjeta);
}
