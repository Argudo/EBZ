package es.uca.iw.ebz.Movimiento;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Cuenta.CuentaService;
import es.uca.iw.ebz.Movimiento.CompraTarjeta.CompraTarjeta;
import es.uca.iw.ebz.Movimiento.CompraTarjeta.CompraTarjetaService;
import es.uca.iw.ebz.Movimiento.Externo.Externo;
import es.uca.iw.ebz.Movimiento.Externo.ExternoService;
import es.uca.iw.ebz.Movimiento.Interno.Interno;
import es.uca.iw.ebz.Movimiento.Interno.InternoService;
import es.uca.iw.ebz.Movimiento.RecargaTarjeta.RecargaTarjeta;
import es.uca.iw.ebz.Movimiento.RecargaTarjeta.RecargaTarjetaService;
import es.uca.iw.ebz.cliente.Cliente;
import es.uca.iw.ebz.tarjeta.Tarjeta;
import es.uca.iw.ebz.tarjeta.TarjetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MovimientoService {
    private MovimientoRepository _movimientoRepository;
    private InternoService _internoService;
    private ExternoService _externoService;

    private CuentaService _cuentaService;

    private RecargaTarjetaService _recargaTarjetaService;

    private CompraTarjetaService _compraTarjetaService;

    private TarjetaService _tarjetaService;

    @Autowired
    public MovimientoService(MovimientoRepository movimientoRepository, InternoService internoService, ExternoService externoService,
                             CuentaService cuentaService, RecargaTarjetaService recargaTarjetaService,
                             CompraTarjetaService compraTarjetaService, TarjetaService tarjetaService) {
        _movimientoRepository = movimientoRepository;
        _internoService = internoService;
        _externoService = externoService;
        _cuentaService = cuentaService;
        _recargaTarjetaService = recargaTarjetaService;
        _compraTarjetaService = compraTarjetaService;
        _tarjetaService = tarjetaService;
    }

    public Movimiento añadirMovimientoCuenta(Movimiento movimiento, Cuenta cuentaOrigen, String cuentaDestino, float fimporte) {
        Movimiento mov = _movimientoRepository.save(movimiento);
        switch (movimiento.getTipo()) {
            case INTERNO:
                Interno interno = new Interno(fimporte, cuentaOrigen, _cuentaService.findByNumeroCuenta(cuentaDestino).get(), mov);
                _internoService.añadirInterno(interno);
                break;
            case EXTERNO:
                Externo externo = new Externo(fimporte, cuentaOrigen, cuentaDestino, mov);
                _externoService.añadirExterno(externo);
                break;
            default:
                _movimientoRepository.delete(mov); // eliminamos el movimiento creado que es erróneo
                throw new IllegalArgumentException("Unexpected value: " + movimiento.getTipo());
        }
        return mov;
    }

    public Movimiento añadirMovimientoTarjeta(Movimiento movimiento, Tarjeta tarjeta, String sDestino, float fimporte) {
        Movimiento mov = _movimientoRepository.save(movimiento);
        switch (movimiento.getTipo()) {
            case RECARGATARJETA:
                RecargaTarjeta recargaTarjeta = new RecargaTarjeta(_cuentaService.findByNumeroCuenta(sDestino).get(), tarjeta, fimporte, mov);
                _recargaTarjetaService.añadirRecargaTarjeta(recargaTarjeta);
                break;
            case COMPRATARJETA:
                CompraTarjeta compraTarjeta = new CompraTarjeta(tarjeta, sDestino, fimporte, mov);
                _compraTarjetaService.añadirCompraTarjeta(compraTarjeta);
                break;
            default:
                _movimientoRepository.delete(mov);
                throw new IllegalArgumentException("Unexpected value: " + movimiento.getTipo());
        }
        return mov;
    }

    public List<Movimiento> findByCuentaOrderByFechaASC(Cuenta cuenta) {
        List<Interno> movInternos = _internoService.findByCuentaOrigenOrCuentaDestino(cuenta);
        List<Externo> movExternos = _externoService.findByCuentaPropia(cuenta);
        List<RecargaTarjeta> movRecargaTarjeta = _recargaTarjetaService.findByCuenta(cuenta);
        List<Movimiento> movimientos = new ArrayList<Movimiento>();

        for(Externo movExterno : movExternos) {
            movimientos.add(movExterno.getMovimiento());
        }

        for(Interno movInterno : movInternos) {
            movimientos.add(movInterno.getMovimiento());
        }

        for(RecargaTarjeta movRecarga : movRecargaTarjeta) {
            movimientos.add(movRecarga.getMovimiento());
        }

        return Movimiento.sortByFechaASC(movimientos);
    }

    public List<Movimiento> findByTarjetaOrderByASC(Tarjeta tarjeta) {
        List<CompraTarjeta> movCompraTarjeta = _compraTarjetaService.findByTarjeta(tarjeta);
        List<RecargaTarjeta> movRecargaTarjeta = _recargaTarjetaService.findByTarjeta(tarjeta);
        List<Movimiento> movimientos = new ArrayList<Movimiento>();

        for(CompraTarjeta movCompra : movCompraTarjeta) {
            movimientos.add(movCompra.getMovimiento());
        }

        for(RecargaTarjeta movRecarga : movRecargaTarjeta) {
            movimientos.add(movRecarga.getMovimiento());
        }

        return Movimiento.sortByFechaASC(movimientos);
    }

    public HashMap datosMovimiento(Movimiento movimiento) {
        HashMap datos = new HashMap<String, String>();
        datos.put("id", movimiento.getId());
        datos.put("fecha", movimiento.getFecha());
        datos.put("tipo", movimiento.getTipo());

        switch (movimiento.getTipo()) {
            case INTERNO:
                Interno interno = _internoService.findByMovimiento(movimiento);
                datos.put("Origen", interno.getCuentaOrigen().getNumeroCuenta());
                datos.put("Destino", interno.getCuentaDestino().getNumeroCuenta());
                datos.put("Importe", interno.getImporte());
                break;
            case EXTERNO:
                Externo externo = _externoService.findByMovimiento(movimiento);
                datos.put("Origen", externo.getCuentaPropia().getNumeroCuenta());
                datos.put("Destino", externo.getNumCuentaAjena());
                datos.put("Importe", externo.getImporte());
                break;
            case COMPRATARJETA:
                CompraTarjeta compraTarjeta = _compraTarjetaService.findByMovimiento(movimiento);
                datos.put("Origen", compraTarjeta.getTarjeta().getNumTarjeta());
                datos.put("Destino", compraTarjeta.getDestino());
                datos.put("Importe", compraTarjeta.getImporte());
                break;
            case RECARGATARJETA:
                RecargaTarjeta recargaTarjeta = _recargaTarjetaService.findByMovimiento(movimiento);
                datos.put("Origen", recargaTarjeta.getCuenta().getNumeroCuenta());
                datos.put("Destino", recargaTarjeta.getTarjeta().getNumTarjeta());
                datos.put("Importe", recargaTarjeta.getImporte());
                break;
        }
        return datos;
    }

    public List<Movimiento> findByClienteByFechaASC(Cliente cliente) {
        List<Movimiento> movimientos = new ArrayList<Movimiento>();
        List<Cuenta> cuentas = _cuentaService.findByCliente(cliente);
        //List<Tarjeta> tarjetas = _tarjetaService.findByCliente(cliente);
        List<Tarjeta> tarjetas = cliente.getTarjetas();
        for(Cuenta cuenta : cuentas) {
            movimientos.addAll(findByCuentaOrderByFechaASC(cuenta));
        }
        for(Tarjeta tarjeta : tarjetas) {
            movimientos.addAll(findByTarjetaOrderByASC(tarjeta));
        }
        return Movimiento.sortByFechaASC(movimientos);
    }
}
