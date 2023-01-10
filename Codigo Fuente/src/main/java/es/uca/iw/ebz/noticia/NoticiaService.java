package es.uca.iw.ebz.noticia;

import es.uca.iw.ebz.consulta.Consulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NoticiaService {

    private NoticiaRepository _noticiaRepository;

    @Autowired
    public NoticiaService(NoticiaRepository noticiaRepository) { _noticiaRepository = noticiaRepository; }

    public Noticia Save(Noticia n) { return _noticiaRepository.save(n); }

    public void Delete(Noticia n) {
        n.setFechaEliminacion(new Date());
        Save(n);
    }

    public List<Noticia> findAll() { return _noticiaRepository.findAll(); }

    public List<Noticia> findByFechaEliminacionIsNull() { return _noticiaRepository.findBy_fechaEliminacionIsNull(); }

}
