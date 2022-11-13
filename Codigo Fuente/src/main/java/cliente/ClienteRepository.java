package cliente;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, UUID>{
	
}
