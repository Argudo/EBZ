package es.uca.iw.ebz.Movimiento.Externo;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Movimiento.Movimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Externo> findByCuentaPropia(Cuenta cuenta) {
        return _externoRepository.findByCuentaPropia(cuenta);
    }

    public Externo findByMovimiento(Movimiento movimiento) {
        return _externoRepository.findByMovimiento(movimiento);
    }
}
