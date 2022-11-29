package es.uca.iw.ebz.Movimiento.RecargaTarjeta;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Movimiento.Movimiento;
import es.uca.iw.ebz.tarjeta.Tarjeta;
import es.uca.iw.ebz.tarjeta.TarjetaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.RecursiveAction;

@Service
public class RecargaTarjetaService {
    RecargaTarjetaRepository _recargaTarjetaRepository;
    TarjetaService _tarjetaService;

    public RecargaTarjetaService(RecargaTarjetaRepository recargaTarjetaRepository, TarjetaService tarjetaService) {
        _recargaTarjetaRepository = recargaTarjetaRepository;
        _tarjetaService = tarjetaService;
    }

    public RecargaTarjeta añadirRecargaTarjeta(RecargaTarjeta recargaTarjeta) {
        return _recargaTarjetaRepository.save(recargaTarjeta);
    }

    public RecargaTarjeta findByMovimiento(Movimiento movimiento) {
        return _recargaTarjetaRepository.findByMovimiento(movimiento);
    }

    public List<RecargaTarjeta> findByCuenta(Cuenta cuenta) {
        return _recargaTarjetaRepository.findByCuenta(cuenta);
    }

    public List<RecargaTarjeta> findByTarjeta(Tarjeta tarjeta) {
        return _recargaTarjetaRepository.findByTarjeta(tarjeta);
    }
    
}
