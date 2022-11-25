package es.uca.iw.ebz.Movimiento.Interno;

import es.uca.iw.ebz.Cuenta.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InternoService {
    private InternoRepository _internoRepository;
    private CuentaRepository _cuentaRepository;
    @Autowired
    public InternoService(InternoRepository internoRepository, CuentaRepository cuentaRepository) {
        _internoRepository = internoRepository;
        _cuentaRepository = cuentaRepository;
    }

    public Interno añadirInterno(Interno interno) {
        return _internoRepository.save(interno);
    }
    public List<Interno> findByCuentaOrigen(Interno interno) {
        return _internoRepository.findByCuentaOrigen(interno.getCuentaOrigen());
    }
    public List<Interno> findByCuentaDestino(Interno interno) {
        return _internoRepository.findByCuentaDestino(interno.getCuentaDestino());
    }
    public List<Interno> findByCuentaOrigenAndAndCuentaDestino(Interno interno) {
        return _internoRepository.findByCuentaOrigenAndAndCuentaDestino(interno.getCuentaOrigen(), interno.getCuentaDestino());
    }
}
