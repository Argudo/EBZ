package es.uca.iw.ebz.Movimiento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovimientoService {
    private MovimientoRepository _movimientoRepository;

    @Autowired
    public MovimientoService(MovimientoRepository movimientoRepository) {
        _movimientoRepository = movimientoRepository;
    }

    public Movimiento añadirMovimiento(Movimiento movimiento) {
        return _movimientoRepository.save(movimiento);
    }


}
