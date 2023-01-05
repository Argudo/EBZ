package es.uca.iw.ebz.noticia;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NoticiaRepository extends JpaRepository<Noticia, UUID> {
}