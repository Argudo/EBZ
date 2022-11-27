package es.uca.iw.ebz.Movimiento;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Cuenta.CuentaService;
import es.uca.iw.ebz.Movimiento.Externo.Externo;
import es.uca.iw.ebz.Movimiento.Externo.ExternoService;
import es.uca.iw.ebz.Movimiento.Interno.Interno;
import es.uca.iw.ebz.Movimiento.Interno.InternoService;
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

    @Autowired
    public MovimientoService(MovimientoRepository movimientoRepository, InternoService internoService, ExternoService externoService, CuentaService cuentaService) {
        _movimientoRepository = movimientoRepository;
        _internoService = internoService;
        _externoService = externoService;
        _cuentaService = cuentaService;
    }

    public Movimiento añadirMovimientoCuenta(Movimiento movimiento, Cuenta cuentaOrigen, String cuentaDestino, float fimporte) {
        Movimiento mov = _movimientoRepository.save(movimiento);
        switch (movimiento.getTipo()) {
            case INTERNO:
                Interno interno = new Interno(fimporte, cuentaOrigen, _cuentaService.findByNumeroCuenta(cuentaDestino), mov);
                break;
            case EXTERNO:
                Externo externo = new Externo(fimporte, cuentaOrigen, cuentaDestino, mov);
                break;
            default:
                break;
        }
        return mov;
    }

    public List<Movimiento> findByCuentaOrderByFechaASC(Cuenta cuenta) {
        List<Interno> movInternos = _internoService.findByCuentaOrigenOrCuentaDestino(cuenta);
        List<Externo> movExternos = _externoService.findByCuentaPropia(cuenta);
        List<Movimiento> movimientos = new ArrayList<Movimiento>();
        for(Externo movExterno : movExternos) {
            movimientos.add(movExterno.getMovimiento());
        }

        for(Interno movInterno : movInternos) {
            movimientos.add(movInterno.getMovimiento());
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
                datos.put("cuentaOrigen", interno.getCuentaOrigen().getNumeroCuenta());
                datos.put("cuentaDestino", interno.getCuentaDestino().getNumeroCuenta());
                datos.put("importe", interno.getImporte());
                break;
            case EXTERNO:
                Externo externo = _externoService.findByMovimiento(movimiento);
                datos.put("cuentaPropia", externo.getCuentaPropia().getNumeroCuenta());
                datos.put("cuentaDestino", externo.getNumCuentaAjena());
                datos.put("importe", externo.getImporte());
                break;
            case COMPRATARJETA:
                break;
            case RECARGATARJETA:
                break;
        }
        return datos;
    }
}
