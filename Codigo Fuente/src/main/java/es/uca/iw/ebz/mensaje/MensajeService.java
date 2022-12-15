package es.uca.iw.ebz.mensaje;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.Optional

@Service
public class MensajeService {

    private MensajeRepository _menRepository;

    @Autowired
    public MensajeService(MensajeRepository menRepository) { _menRepository = menRepository; }

    public void Save(Mensaje m) { _menRepository.save(m); }

    public Mensaje findById(UUID Id) throws Exception {
        Optional<Mensaje> optM = _menRepository.findById(Id);
        if(optM.isPresent()) return optM.get();
        throw new Exception("No se encontró el mensaje para el id dado");

    }



}
