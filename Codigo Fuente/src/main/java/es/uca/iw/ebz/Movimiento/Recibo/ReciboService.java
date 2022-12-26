package es.uca.iw.ebz.Movimiento.Recibo;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Movimiento.Movimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReciboService {

    private ReciboRepository _reciboRepository;

    @Autowired
    public ReciboService(ReciboRepository reciboRepository) {
        _reciboRepository = reciboRepository;
    }

    public Recibo añadirRecibo(Recibo recibo) {
        return _reciboRepository.save(recibo);
    }

    public Recibo findByMovimiento(Movimiento movimiento) {
        return _reciboRepository.findByMovimiento(movimiento);
    }

    public List<Recibo> findByCuenta(Cuenta cuenta) {
        return _reciboRepository.findByCuenta(cuenta);
    }
}
