package es.uca.iw.ebz.Movimiento.Externo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternoService {
    private ExternoRepository _externoRepository;

    @Autowired
    public ExternoService(ExternoRepository externoRepository) {
        _externoRepository = externoRepository;
    }

    public Externo añadirExterno(Externo externo) {
        return _externoRepository.save(externo);
    }
}
