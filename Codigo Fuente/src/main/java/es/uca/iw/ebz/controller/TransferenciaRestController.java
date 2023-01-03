package es.uca.iw.ebz.controller;

import es.uca.iw.ebz.Cuenta.Cuenta;
import es.uca.iw.ebz.Cuenta.CuentaService;
import es.uca.iw.ebz.Movimiento.Movimiento;
import es.uca.iw.ebz.Movimiento.MovimientoService;
import es.uca.iw.ebz.Movimiento.TipoMovimiento;
import es.uca.iw.ebz.tarjeta.Tarjeta;
import es.uca.iw.ebz.tarjeta.TarjetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RestController
public class TransferenciaRestController {

    @Autowired
    private TarjetaService _tarjetaService;

    @Autowired
    private MovimientoService _movimientoService;

    @Autowired
    private CuentaService _cuentaService;


    @PostMapping("/api/transactions")
    public TransaccionMovimiento transferencia(@RequestBody final TransaccionMovimiento requestMov){
        Movimiento movimiento = new Movimiento(new Date(), requestMov.getConcept(), TipoMovimiento.RECIBO);
        Optional<Cuenta> cuenta = _cuentaService.findByNumeroCuenta(requestMov.getIban());
        if(cuenta.isPresent()){
            if(requestMov.getTransactionType().equals("DEPOSIT")){
                if(_movimientoService.añadirRecibo(movimiento, cuenta.get(), requestMov.getValue()) == null){
                    requestMov.setTransactionStatus("REJECTED");
                }
                else{
                    requestMov.setTransactionStatus("ACCEPTED");
                    requestMov.setId(UUID.randomUUID().toString());
                }
            }else{
                if(_movimientoService.añadirRecibo(movimiento, cuenta.get(), -requestMov.getValue()) == null){ //lo ponemos en negativo
                    requestMov.setTransactionStatus("REJECTED");
                }
                else{
                    requestMov.setTransactionStatus("ACCEPTED");
                    requestMov.setId(UUID.randomUUID().toString());
                }
            }

        }else {
            requestMov.setTransactionStatus("REJECTED");
        }

        return requestMov;
    }

    @PostMapping("/api/payments")
    public TransaccionTarjeta compraTarjeta(@RequestBody final TransaccionTarjeta requestTarjeta) {
        Movimiento movimiento = new Movimiento(new Date(), "Compra " + requestTarjeta.getType() + "en " + requestTarjeta.getShop(), TipoMovimiento.COMPRATARJETA);
        Tarjeta tarjeta = _tarjetaService.findByNumCuenta(requestTarjeta.getCardNumber());
        if (tarjeta != null) {
            if (_movimientoService.compraTarjeta(movimiento, tarjeta, requestTarjeta.getShop(), requestTarjeta.getValue()) == null) {
                requestTarjeta.setPaymentStatus("REJECTED");
            } else {
                requestTarjeta.setPaymentStatus("ACCEPTED");
                requestTarjeta.setId(UUID.randomUUID().toString());
                requestTarjeta.setSecurityToken(999);
            }
        }else{
            requestTarjeta.setPaymentStatus("REJECTED");
        }

        return requestTarjeta;
    }
}
//crear clase intermedia para recibir los datos