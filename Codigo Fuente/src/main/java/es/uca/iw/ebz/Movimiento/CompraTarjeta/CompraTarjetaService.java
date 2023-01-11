package es.uca.iw.ebz.Movimiento.CompraTarjeta;

import es.uca.iw.ebz.Cuenta.CuentaRepository;
import es.uca.iw.ebz.Movimiento.Interno.InternoRepository;
import es.uca.iw.ebz.Movimiento.Movimiento;
import es.uca.iw.ebz.tarjeta.Tarjeta;
import es.uca.iw.ebz.tarjeta.TarjetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraTarjetaService {
    private CompraTarjetaRepository _compraTarjetaRepository;
    private TarjetaService _tarjetaService;

    @Autowired
    public CompraTarjetaService(CompraTarjetaRepository compraTarjetaRepository, TarjetaService tarjetaService) {
        _compraTarjetaRepository = compraTarjetaRepository;
        _tarjetaService = tarjetaService;
    }

    public CompraTarjeta añadirCompraTarjeta(CompraTarjeta compraTarjeta) {
        return _compraTarjetaRepository.save(compraTarjeta);
    }

    public List<CompraTarjeta> findByTarjeta(Tarjeta tarjeta) {
        return _compraTarjetaRepository.findBytarjeta(tarjeta);
    }

    public CompraTarjeta findByMovimiento(Movimiento movimiento) {
        return _compraTarjetaRepository.findByMovimiento(movimiento);
    }



}
