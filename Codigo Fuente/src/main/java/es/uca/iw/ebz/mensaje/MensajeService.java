package es.uca.iw.ebz.mensaje;

import es.uca.iw.ebz.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MensajeService {

    private MensajeRepository _mensajeRepository;

    @Autowired
    public MensajeService(MensajeRepository mensajeRepository) { _mensajeRepository = mensajeRepository; }

    public void Save(Mensaje m) { _mensajeRepository.save(m); }

    public void Delete(Mensaje m) {
        m.setFechaEliminacion(new Date());
        Save(m);
    }

    public List<Mensaje> findByAutor(Usuario usuario) {
        return _mensajeRepository.findBy_autor(usuario);
    }
    

}
