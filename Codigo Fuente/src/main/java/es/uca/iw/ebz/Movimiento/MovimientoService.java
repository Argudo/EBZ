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
import es.uca.iw.ebz.Movimiento.Recibo.Recibo;
import es.uca.iw.ebz.Movimiento.Recibo.ReciboService;
import es.uca.iw.ebz.tarjeta.EnumTarjeta;
import es.uca.iw.ebz.tarjeta.Tarjeta;
import es.uca.iw.ebz.tarjeta.TarjetaService;
import es.uca.iw.ebz.tarjeta.credito.Credito;
import es.uca.iw.ebz.tarjeta.credito.CreditoService;
import es.uca.iw.ebz.tarjeta.prepago.Prepago;
import es.uca.iw.ebz.tarjeta.prepago.PrepagoService;
import es.uca.iw.ebz.usuario.cliente.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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

    private ReciboService _reciboService;

    private PrepagoService _prepagoService;

    private CreditoService _creditoService;

    @Autowired
    public MovimientoService(MovimientoRepository movimientoRepository, InternoService internoService, ExternoService externoService,
                             CuentaService cuentaService, RecargaTarjetaService recargaTarjetaService,
                             CompraTarjetaService compraTarjetaService, TarjetaService tarjetaService,
                             ReciboService reciboService, PrepagoService prepagoService,
                             CreditoService creditoService) {
        _movimientoRepository = movimientoRepository;
        _internoService = internoService;
        _externoService = externoService;
        _cuentaService = cuentaService;
        _recargaTarjetaService = recargaTarjetaService;
        _compraTarjetaService = compraTarjetaService;
        _tarjetaService = tarjetaService;
        _reciboService = reciboService;
        _prepagoService = prepagoService;
        _creditoService = creditoService;
    }

    public Movimiento añadirMovimientoCuenta(Movimiento movimiento, Cuenta cuentaOrigen, String cuentaDestino, float fimporte) throws Exception {
        if(cuentaOrigen.getSaldo().floatValue() < fimporte) throw new Exception("Saldo insuficiente");
        if(cuentaOrigen.getFechaEliminacion() != null) throw new Exception("Cuenta origen eliminado");
        if(cuentaOrigen.getNumeroCuenta().equals(cuentaDestino)) throw new Exception("Cuenta origen y destino iguales");
        cuentaOrigen.setSaldo(cuentaOrigen.getSaldo().subtract(BigDecimal.valueOf(fimporte)));

        Movimiento mov = _movimientoRepository.save(movimiento);
        switch (movimiento.getTipo()) {
            case INTERNO:
                Cuenta _cuentaDestino = _cuentaService.findByNumeroCuenta(cuentaDestino).get();
                if(_cuentaDestino.getFechaEliminacion() != null){
                    _movimientoRepository.delete(mov);
                    new Exception("Cuenta destino eliminado");
                }
                _cuentaDestino.setSaldo(_cuentaDestino.getSaldo().add(BigDecimal.valueOf(fimporte)));
                _cuentaService.save(cuentaOrigen);
                _cuentaService.save(_cuentaDestino);

                Interno interno = new Interno(fimporte, cuentaOrigen, _cuentaDestino, mov);
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

    public Movimiento recargaTarjeta(Movimiento movimiento, Cuenta cuentaOrigen, Tarjeta tarjeta, float fimporte) throws Exception {
        if(cuentaOrigen.getSaldo().floatValue() < fimporte) throw new Exception("Saldo insuficiente");
        if(cuentaOrigen.getFechaEliminacion() != null) throw new Exception("Cuenta origen eliminado");
        if(tarjeta.getTipoTarjeta() != EnumTarjeta.Prepago) throw new Exception("Tarjeta no es de tipo prepago");
        cuentaOrigen.setSaldo(cuentaOrigen.getSaldo().subtract(BigDecimal.valueOf(fimporte)));
        _cuentaService.save(cuentaOrigen);
        //Añadimos la recarga de la tarjeta
        Prepago prepago = _prepagoService.findByTarjeta(tarjeta);
        prepago.setSaldo(prepago.getSaldo() + fimporte);
        _prepagoService.Save(prepago);
        Movimiento mov = _movimientoRepository.save(movimiento);
        RecargaTarjeta recargaTarjeta = new RecargaTarjeta(cuentaOrigen, tarjeta, fimporte, mov);
        _recargaTarjetaService.añadirRecargaTarjeta(recargaTarjeta);
        return mov;
    }

    public Movimiento compraTarjeta(Movimiento movimiento, Tarjeta tarjeta, String sDestino, float fimporte,
                                    int month, int year, String cvv) throws Exception {
        String expiracion = month + "/" + year;
        if(!expiracion.equals(tarjeta.getFechaExpiracion())) throw new Exception("Fecha de expiración incorrecta");
        if(!cvv.equals(tarjeta.getCVC())) throw new Exception("CVV incorrecto");
        switch(tarjeta.getTipoTarjeta()) {
            case Prepago:
                Prepago prepago = _prepagoService.findByTarjeta(tarjeta);
                if(prepago.getSaldo() < fimporte) throw new Exception("Saldo insuficiente");
                prepago.setSaldo(prepago.getSaldo() - fimporte);
                _prepagoService.Save(prepago);
                break;
            case Debito:
                if(tarjeta.getCuenta().getSaldo().floatValue() < fimporte) throw new Exception("Saldo insuficiente");
                tarjeta.getCuenta().setSaldo(tarjeta.getCuenta().getSaldo().add(BigDecimal.valueOf(fimporte)));
                _cuentaService.save(tarjeta.getCuenta());
                break;
            case Credito:
                Credito credito = _creditoService.findByTarjeta(tarjeta);
                credito.setDeuda(credito.getDeuda() + fimporte);
                _creditoService.Save(credito);
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + tarjeta.getTipoTarjeta());
        }
        Movimiento mov = _movimientoRepository.save(movimiento);
        CompraTarjeta compraTarjeta = new CompraTarjeta(tarjeta, sDestino, fimporte, mov);
        _compraTarjetaService.añadirCompraTarjeta(compraTarjeta);
        return mov;
    }

    public Movimiento añadirRecibo(Movimiento movimiento, Cuenta cuenta, float fimporte) throws Exception {
        if(cuenta.getFechaEliminacion() != null) throw new Exception("Cuenta origen eliminado");
        cuenta.setSaldo(cuenta.getSaldo().add(BigDecimal.valueOf(fimporte)));
        _cuentaService.save(cuenta);
        Movimiento mov = _movimientoRepository.save(movimiento);
        Recibo recibo = new Recibo();
        recibo.setCuenta(cuenta);
        recibo.setImporte(fimporte);
        recibo.setMovimiento(mov);
        _reciboService.añadirRecibo(recibo);
        return mov;
    }

    public List<Movimiento> findByCuentaOrderByFechaASC(Cuenta cuenta) {
        List<Interno> movInternos = _internoService.findByCuentaOrigenOrCuentaDestino(cuenta);
        List<Externo> movExternos = _externoService.findByCuentaPropia(cuenta);
        List<RecargaTarjeta> movRecargaTarjeta = _recargaTarjetaService.findByCuenta(cuenta);
        List<Recibo> movRecibos = _reciboService.findByCuenta(cuenta);
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

        for(Recibo movRecibo : movRecibos) {
            movimientos.add(movRecibo.getMovimiento());
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
            case RECIBO:
                Recibo recibo = _reciboService.findByMovimiento(movimiento);
                datos.put("Origen", recibo.getCuenta().getNumeroCuenta());
                datos.put("Importe", recibo.getImporte());
                break;
        }
        return datos;
    }

    public DatosMovimiento datosMovimientoClass(Movimiento movimiento){
        DatosMovimiento datos = new DatosMovimiento();
        datos.setId(movimiento.getId().toString());
        datos.setFecha(movimiento.getFecha());
        datos.setTipo(movimiento.getTipo().toString());
        datos.setConcepto(movimiento.getConcepto());

        switch (movimiento.getTipo()) {
            case INTERNO:
                Interno interno = _internoService.findByMovimiento(movimiento);
                datos.setOrigen(interno.getCuentaOrigen().getNumeroCuenta());
                datos.setDestino(interno.getCuentaDestino().getNumeroCuenta());
                datos.setImporte(Float.toString(interno.getImporte()));
                break;
            case EXTERNO:
                Externo externo = _externoService.findByMovimiento(movimiento);
                datos.setOrigen(externo.getCuentaPropia().getNumeroCuenta());
                datos.setDestino(externo.getNumCuentaAjena());
                datos.setImporte(Float.toString(externo.getImporte()));
                break;
            case COMPRATARJETA:
                CompraTarjeta compraTarjeta = _compraTarjetaService.findByMovimiento(movimiento);
                datos.setOrigen(compraTarjeta.getTarjeta().getNumTarjeta());
                datos.setDestino( compraTarjeta.getDestino());
                datos.setImporte(Float.toString(-compraTarjeta.getImporte()));
                break;
            case RECARGATARJETA:
                RecargaTarjeta recargaTarjeta = _recargaTarjetaService.findByMovimiento(movimiento);
                datos.setOrigen(recargaTarjeta.getCuenta().getNumeroCuenta());
                datos.setDestino(  recargaTarjeta.getTarjeta().getNumTarjeta());
                datos.setImporte(Float.toString(recargaTarjeta.getImporte()));
                break;
            case RECIBO:
                Recibo recibo = _reciboService.findByMovimiento(movimiento);
                datos.setOrigen("-");
                datos.setDestino(recibo.getCuenta().getNumeroCuenta());
                datos.setImporte(Float.toString(recibo.getImporte()));
                break;
        }
        return datos;
    }

    public List<Movimiento> findByClienteByFechaASC(Cliente cliente) {
        List<Movimiento> movimientos = new ArrayList<Movimiento>();
        List<Cuenta> cuentas = _cuentaService.findByCliente(cliente);
        List<Tarjeta> tarjetas = _tarjetaService.findByCliente(cliente);
        //List<Tarjeta> tarjetas = cliente.getTarjetas();
        for(Cuenta cuenta : cuentas) {
            movimientos.addAll(findByCuentaOrderByFechaASC(cuenta));
        }
        for(Tarjeta tarjeta : tarjetas) {
            movimientos.addAll(findByTarjetaOrderByASC(tarjeta));
        }
        return Movimiento.sortByFechaASC(movimientos);
    }
}
